package ltd.rymc.form.residence.forms.setting.sensitive;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.language.Language;
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

        Language.Section sensitiveRename = section("forms.manage.sensitive.rename");

        title(String.format(sensitiveRename.text("title"), claimedResidence.getName()));
        input(sensitiveRename.text("input1"), sensitiveRename.text("input2"));
        input(sensitiveRename.text("input3"), sensitiveRename.text("input4"));
    }

    @Override
    public void send() {
        if (!claimedResidence.isOwner(bukkitPlayer) && !bukkitPlayer.isOp()) {
            new ResidenceNoPermissionForm(bukkitPlayer,previousForm).send();
            return;
        }

        super.send();
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);
        String input1 = response.asInput(1);

        if (InputUtils.isMismatch(input, input1)){
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
