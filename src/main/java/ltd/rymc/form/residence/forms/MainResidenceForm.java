package ltd.rymc.form.residence.forms;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.select.ResidenceCreateSelectForm;
import ltd.rymc.form.residence.forms.setting.ResidenceSettingSelectForm;
import ltd.rymc.form.residence.forms.teleport.ResidenceTeleportForm;
import ltd.rymc.form.residence.forms.tools.ResidenceToolsForm;
import ltd.rymc.form.residence.language.Language;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

public class MainResidenceForm extends RSimpleForm {
    public MainResidenceForm(Player player, RForm previousForm) {
        super(player, previousForm);

        Language.Section main = section("forms.main");
        Language.Section buttons = main.section("buttons");

        title(main.text("title"));
        content(main.text("content"));
        buttons(
                buttons.text("teleport"),
                buttons.text("manage"),
                buttons.text("create"),
                buttons.text("tool")
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) new ResidenceTeleportForm(bukkitPlayer,this).send();
        else if (id == 1) new ResidenceSettingSelectForm(bukkitPlayer,this).send();
        else if (id == 2) new ResidenceCreateSelectForm(bukkitPlayer,this).send();
        else if (id == 3) new ResidenceToolsForm(bukkitPlayer,this).send();
    }
}
