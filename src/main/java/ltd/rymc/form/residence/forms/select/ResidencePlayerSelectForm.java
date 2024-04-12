package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidencePlayerSelectForm extends RCustomForm {
    public ResidencePlayerSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);


        Language.Section createSelect = section("forms.create.select");

        title(createSelect.text("title"));
        input(createSelect.text("input-x1"), createSelect.text("input-x2"));
        input(createSelect.text("input-z1"), createSelect.text("input-z2"));
        input(createSelect.text("input-y1"), createSelect.text("input-y2"));
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
