package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.language.Language;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceTeleportSetForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceTeleportSetForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }


        Language.Section teleportSet = section("forms.manage.teleport-set");
        Language.Section teleportSetButtons = teleportSet.section("buttons");

        title(teleportSet.text("title"));
        buttons(
                teleportSetButtons.text("set"),
                teleportSetButtons.text("back")
        );
    }

    @Override
    public void refresh() {
        Location location = bukkitPlayer.getLocation();
        content(String.format(text("forms.manage.teleport-set.content"),
                claimedResidence.getName(),
                location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ()));
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        if (response.clickedButtonId() == 0) claimedResidence.setTpLoc(bukkitPlayer, false);
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
