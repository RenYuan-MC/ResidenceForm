package ltd.rymc.form.residence.forms;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.info.PluginInfoForm;
import ltd.rymc.form.residence.forms.select.ResidenceCreateSelectForm;
import ltd.rymc.form.residence.forms.setting.ResidenceSettingSelectForm;
import ltd.rymc.form.residence.forms.teleport.ResidenceTeleportForm;
import ltd.rymc.form.residence.forms.tools.ResidenceToolsForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

public class MainResidenceForm extends RSimpleForm {
    public MainResidenceForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title(lang().mainTitle());
        content(lang().mainContent());
        buttons(
                lang().mainTeleport(),
                lang().mainManage(),
                lang().mainCreate(),
                lang().mainTool()
        );

        if (ResidenceForm.getMainConfig().displayPluginInfo()) button(lang().mainInfo());
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) new ResidenceTeleportForm(bukkitPlayer,this).send();
        else if (id == 1) new ResidenceSettingSelectForm(bukkitPlayer,this).send();
        else if (id == 2) new ResidenceCreateSelectForm(bukkitPlayer,this).send();
        else if (id == 3) new ResidenceToolsForm(bukkitPlayer,this).send();
        else if (id == 4) new PluginInfoForm(bukkitPlayer,this).send();
    }
}
