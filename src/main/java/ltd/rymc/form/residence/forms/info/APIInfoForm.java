package ltd.rymc.form.residence.forms.info;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class APIInfoForm extends RSimpleForm {
    public APIInfoForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("插件API信息");
        content(
                "§7ResidenceForm开发时使用的插件版本:\n" +
                "Floodgate: 2.0-SNAPSHOT\n" +
                "Residence: 5.0.3.0\n" +
                "\n" +
                "服务器插件版本:\n" +
                "Floodgate: " + getPluginVersion("floodgate") + "\n" +
                "Residence: " + getPluginVersion("Residence") + "\n" +
                "\n"
        );
        button("返回上一级菜单");
    }

    private String getPluginVersion(String pluginName){
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin == null ? "未知" : plugin.getDescription().getVersion();
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
