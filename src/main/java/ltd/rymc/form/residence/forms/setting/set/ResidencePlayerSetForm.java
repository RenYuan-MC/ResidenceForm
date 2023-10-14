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

public class ResidencePlayerSetForm extends RCustomForm {
    private final String targetPlayer;
    private final ClaimedResidence claimedResidence;
    HashMap<String, FlagPermissions.FlagState> flags;
    List<String> permissionList;
    public ResidencePlayerSetForm(Player player, RForm previousForm, ClaimedResidence claimedResidence, String targetPlayer) {
        super(player, previousForm);
        this.targetPlayer = targetPlayer;
        this.claimedResidence = claimedResidence;
        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }
        title("§8领地 §l" + claimedResidence.getName() + " §r§8玩家 §l" + targetPlayer + " §r§8的权限设置");

        flags = ResidenceUtils.getResidencePlayerFlags(player, targetPlayer, claimedResidence);
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

            claimedResidence.getPermissions().setPlayerFlag(targetPlayer, flagName, flagState);
        }

        sendPrevious();
    }
}
