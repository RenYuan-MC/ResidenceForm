package ltd.rymc.form.residence.forms.info;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class BugReportForm extends RSimpleForm {

    public BugReportForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("BUG报告");
        content(
                "§7如你在使用本插件时发现相关BUG请前往插件开源仓库或QQ群报告\n" +
                "\n" +
                "地址: https://github.com/RenYuan-MC/ResidenceForm\n" +
                "QQ群: 1029946156\n" +
                "\n"
        );
        button("返回上一级菜单");
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
