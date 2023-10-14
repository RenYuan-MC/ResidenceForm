package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

public class ResidenceTeleportSetForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceTeleportSetForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        title("领地传送点设置");
        buttons(
                "将传送点设置为当前坐标",
                "返回领地管理"
        );
    }

    @Override
    public void refresh() {
        Location location = bukkitPlayer.getLocation();
        content("将你所在的位置设置为领地传送点\n" +
                "\n" +
                "领地: " + claimedResidence.getName() + "\n" +
                "你的坐标:" + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ() + "\n" +
                "\n"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        if (response.clickedButtonId() == 0) claimedResidence.setTpLoc(bukkitPlayer, false);
        sendPrevious();
    }
}
