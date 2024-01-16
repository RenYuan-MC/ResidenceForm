package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
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

        Language.Forms.Manage.TeleportSet language = lang().forms().manage().teleportSet();
        Language.Forms.Manage.TeleportSet.Buttons buttons = language.buttons();

        title(language.title());
        buttons(
                buttons.set(),
                buttons.back()
        );
    }

    @Override
    public void refresh() {
        Location location = bukkitPlayer.getLocation();
        content(String.format(lang().forms().manage().teleportSet().content(),
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
