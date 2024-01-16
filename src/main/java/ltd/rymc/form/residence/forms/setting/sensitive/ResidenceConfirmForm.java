package ltd.rymc.form.residence.forms.setting.sensitive;

import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceConfirmForm extends RSimpleForm {
    private final Runnable runnable;
    public ResidenceConfirmForm(Player player, RForm previousForm, String title, Runnable runnable) {
        super(player, previousForm);
        this.runnable = runnable;

        Language.Forms.Manage.Sensitive.Confirm language = lang().forms().manage().sensitive().confirm();
        Language.Forms.Manage.Sensitive.Confirm.Buttons buttons = language.buttons();

        title(title);
        content(language.content());
        buttons(
                buttons.accept(),
                buttons.deny()
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        if (response.clickedButtonId() == 0) runnable.run();
        else sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
