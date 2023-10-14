package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidenceSetForm extends RCustomForm {
    HashMap<String, FlagPermissions.FlagState> flags;
    List<String> permissionList;

    private final ClaimedResidence claimedResidence;
    public ResidenceSetForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;
        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        title("§8领地 §l" + claimedResidence.getName() + " §r§8权限设置");

        flags = ResidenceUtils.getResidenceFlags(player, claimedResidence);
        permissionList = new ArrayList<>(flags.keySet());

        addPermissionList();
    }

    private void addPermissionList(){
        for (String flagName : permissionList) {

            int flagPermission = ResidenceUtils.flagToInt(flags.get(flagName));
            Flags flag = Flags.getFlag(flagName);

            String flagDec = flag != null ? "\n§e" + flag.getDesc() : "";

            stepSlider("\n" +
                       "§a" + flagName + " §f权限" + flagDec + "\n" +
                       "§f权限状态§8",
                    flagPermission,
                    " §6禁用",
                    " §6未设置",
                    " §6启用"
            );

        }
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        for (int i = 0, permissionListSize = permissionList.size(); i < permissionListSize; i++) {

            String flagName = permissionList.get(i);
            FlagPermissions.FlagState flagState = ResidenceUtils.intToFlag(response.asStepSlider(i));

            if (flagState == flags.get(flagName)) {
                continue;
            }

            claimedResidence.getPermissions().setFlag(flagName, flagState);
        }

        sendPrevious();
    }
}
