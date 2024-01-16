package ltd.rymc.form.residence.forms.setting;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.sensitive.ResidenceSensitiveOperationForm;
import ltd.rymc.form.residence.forms.setting.set.ResidencePlayerSetSelectForm;
import ltd.rymc.form.residence.forms.setting.set.ResidenceSetForm;
import ltd.rymc.form.residence.forms.setting.set.ResidenceTeleportSetForm;
import ltd.rymc.form.residence.forms.setting.trust.ResidenceTrustedPlayerSettingForm;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceSettingForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceSettingForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        Language.Forms.Manage.Main language = lang().forms().manage().main();
        Language.Forms.Manage.Main.Buttons buttons = language.buttons();

        title(language.title());
        buttons(
                buttons.set(),
                buttons.playerSet(),
                buttons.trustedPlayer(),
                buttons.teleportSet(),
                buttons.kick(),
                buttons.sensitive()
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) new ResidenceSetForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 1) new ResidencePlayerSetSelectForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 2) new ResidenceTrustedPlayerSettingForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 3) new ResidenceTeleportSetForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 4) new ResidenceKickForm(bukkitPlayer,this, claimedResidence).send();
        else if (id == 5) new ResidenceSensitiveOperationForm(bukkitPlayer,this,claimedResidence).send();
    }

    @Override
    public void refresh() {
        content(String.format(lang().forms().manage().main().content(), claimedResidence.getName()));
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
