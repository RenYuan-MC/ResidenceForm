package ltd.rymc.form.residence.forms.setting;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.language.Language;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceNoPermissionForm extends RSimpleForm {
    public ResidenceNoPermissionForm(Player player, RForm previousForm) {
        super(player, previousForm);


        Language.Section noPermission = section("forms.manage.no-permission");

        title(noPermission.text("title"));
        content(noPermission.text("content"));
        button(noPermission.text("button"));
    }

    @Override
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
