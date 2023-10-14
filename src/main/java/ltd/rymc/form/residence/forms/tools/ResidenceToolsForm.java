package ltd.rymc.form.residence.forms.tools;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceToolsForm extends RSimpleForm {
    public ResidenceToolsForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("领地菜单");
        content("§7领地基岩版菜单 ResidenceForm");
        buttons(
                "查看当前领地边界",
                "领地信息查询",
                "返回上一级菜单"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) Bukkit.dispatchCommand(bukkitPlayer, "res show");
        else if (id == 1) new ResidenceInfoForm(bukkitPlayer,this).send();
        else if (id == 2) sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
