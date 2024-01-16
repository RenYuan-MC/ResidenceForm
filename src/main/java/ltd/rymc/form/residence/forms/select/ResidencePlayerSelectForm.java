package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidencePlayerSelectForm extends RCustomForm {
    public ResidencePlayerSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);

        Language.Forms.Create.Select language = lang().forms().create().select();

        title(language.title());
        input(language.inputX1(), language.inputX2());
        input(language.inputZ1(), language.inputZ2());
        input(language.inputY1(), language.inputY2());
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String X = response.asInput(0);
        String Y = response.asInput(2);
        String Z = response.asInput(1);
        Bukkit.dispatchCommand(bukkitPlayer, "res select " + X + " " + Y + " " + Z);
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
