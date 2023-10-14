package ltd.rymc.form.residence.forms.info;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

public class PluginInfoForm extends RSimpleForm {
    public PluginInfoForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("插件信息");
        content(
                "§7领地基岩版兼容 ResidenceForm\n" +
                "作者: RENaa_FD\n" +
                "版本: 正式版 v2.0.0\n" +
                "官网: https//rymc.ltd/\n" +
                "QQ群: 1029946156\n\n"
        );
        buttons(
                "API信息",
                "BUG报告",
                "开源协议",
                "返回上一级菜单"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) new APIInfoForm(bukkitPlayer,this).send();
        else if (id == 1) new BugReportForm(bukkitPlayer,this).send();
        else if (id == 2) new LicenseForm(bukkitPlayer,this).send();
        else if (id == 3) sendPrevious();
    }
}
