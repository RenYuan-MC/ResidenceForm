package ltd.rymc.form.residence.forms.setting.sensitive;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceSensitiveOperationForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceSensitiveOperationForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!claimedResidence.isOwner(player) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        Language.Forms.Manage.Sensitive.Main language = lang().forms().manage().sensitive().main();
        Language.Forms.Manage.Sensitive.Main.Buttons buttons = language.buttons();

        title(language.title());
        content(String.format(language.content(), claimedResidence.getName()));
        buttons(
                buttons.rename(),
                buttons.expand(),
                buttons.give(),
                buttons.remove()
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) new ResidenceRenameForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 1) new ResidenceExpandAndContractForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 2) new ResidenceGiveForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 3) new ResidenceRemoveForm(bukkitPlayer,this,claimedResidence).send();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
