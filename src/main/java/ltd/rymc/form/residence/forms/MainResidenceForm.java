package ltd.rymc.form.residence.forms;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.select.ResidenceCreateSelectForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import ren.rymc.residenceform.form.MainForm;

public class MainResidenceForm extends RSimpleForm {
    public MainResidenceForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("领地菜单");
        content("§7领地基岩版菜单 ResidenceForm");
        buttons(
                "领地传送",
                "领地管理",
                "领地创建",
                "领地工具",
                "插件信息"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) MainForm.sendResTeleportForm(bukkitPlayer);
        else if (id == 1) MainForm.sendResSettingForm(bukkitPlayer);
        else if (id == 2) new ResidenceCreateSelectForm(bukkitPlayer,this).send();
        else if (id == 3) MainForm.sendResToolsForm(bukkitPlayer);
        else if (id == 4) MainForm.sendPluginInfoForm(bukkitPlayer);
    }
}
