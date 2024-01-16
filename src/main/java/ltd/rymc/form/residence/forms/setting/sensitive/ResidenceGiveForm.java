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

public class ResidenceGiveForm extends RCustomForm {

    private final ClaimedResidence claimedResidence;
    private static final Residence residence = Residence.getInstance();

    public ResidenceGiveForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!claimedResidence.isOwner(player) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        Language.Forms.Manage.Sensitive.Give language = lang().forms().manage().sensitive().give();

        title(String.format(language.title(), claimedResidence.getName()));
        input(language.input1(), language.input2());
        input(language.input3(), language.input4());
    }


    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);
        String input1 = response.asInput(1);

        if (InputUtils.checkInput(input,input1)) {
            sendPrevious();
            return;
        }

        String title = lang().forms().manage().sensitive().give().title();

        new ResidenceConfirmForm(
                bukkitPlayer,
                previousForm,
                String.format(title, claimedResidence.getName()),
                () -> {

                    if (!claimedResidence.isOwner(bukkitPlayer) && !bukkitPlayer.isOp()) {
                        new ResidenceNoPermissionForm(bukkitPlayer,previousForm).send();
                        return;
                    }

                    residence.getResidenceManager().giveResidence(bukkitPlayer, input.trim(), claimedResidence, false, false);
                }
        );
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
