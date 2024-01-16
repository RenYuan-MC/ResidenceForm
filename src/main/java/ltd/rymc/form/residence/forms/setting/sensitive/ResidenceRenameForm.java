package ltd.rymc.form.residence.forms.setting.sensitive;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceRenameForm extends RCustomForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceRenameForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!claimedResidence.isOwner(player) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        Language.Forms.Manage.Sensitive.Rename language = lang().forms().manage().sensitive().rename();

        title(String.format(language.title(), claimedResidence.getName()));
        input(language.input1(), language.input2());
        input(language.input3(), language.input4());
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);
        String input1 = response.asInput(1);

        if (InputUtils.checkInput(input, input1)){
            sendPrevious();
            return;
        }

        Residence.getInstance().getResidenceManager().renameResidence(bukkitPlayer, claimedResidence.getName(), input.trim(), false);
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
