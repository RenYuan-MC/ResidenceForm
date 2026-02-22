package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.language.Language;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResidencePlayerSetForm extends RCustomForm {

    private final UUID targetPlayer;
    private final ClaimedResidence claimedResidence;
    private final Map<String, FlagPermissions.FlagState> flags;
    private final List<String> permissionList;

    public ResidencePlayerSetForm(Player player, RForm previousForm, ClaimedResidence claimedResidence, UUID targetPlayer) {
        super(player, previousForm);
        this.targetPlayer = targetPlayer;
        this.claimedResidence = claimedResidence;
        this.flags = ResidenceUtils.getResidencePlayerFlags(player, targetPlayer, claimedResidence);
        this.permissionList = new ArrayList<>(flags.keySet());

        title(String.format(text("forms.manage.player-set.set.title"), claimedResidence.getName(), targetPlayer));
        addPermissionList();
    }

    private void addPermissionList(){

        Language.Section permission = section("forms.permission");

        for (String flagName : permissionList) {
            int flagPermission = ResidenceUtils.flagToInt(flags.get(flagName));
            Flags flag = Flags.getFlag(flagName);

            String description = flag != null ? String.format(permission.text("description"), flag.getDesc()) : "";
            String name = String.format(permission.text("name"), flagName);

            stepSlider(
                    String.format(permission.text("state"), name + description),
                    flagPermission,
                    permission.text("disabled"),
                    permission.text("not-set"),
                    permission.text("enable")
            );
        }
    }

    @Override
    public void send() {
        if (!ResidenceUtils.hasManagePermission(bukkitPlayer, claimedResidence) && !bukkitPlayer.isOp()) {
            new ResidenceNoPermissionForm(bukkitPlayer, previousForm).send();
            return;
        }

        super.send();
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        for (int i = 0, permissionListSize = permissionList.size(); i < permissionListSize; i++) {

            String flagName = permissionList.get(i);
            FlagPermissions.FlagState flagState = ResidenceUtils.intToFlag(response.asStepSlider(i));

            if (flagState == flags.get(flagName)) {
                continue;
            }

            claimedResidence.getPermissions().setPlayerFlag(targetPlayer, flagName, flagState);
        }

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
