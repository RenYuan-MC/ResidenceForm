package ltd.rymc.form.residence.forms.setting;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import ren.rymc.residenceform.form.MainForm;

public class ResidenceSettingForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceSettingForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;
        title("领地管理菜单");
        buttons(
                "公共权限设置",
                "玩家权限设置",
                "信任玩家管理",
                "领地传送点设置",
                "踢出领地内玩家",
                "敏感操作",
                "返回上一级菜单"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) MainForm.sendResSetForm(bukkitPlayer, claimedResidence);
        else if (id == 1) MainForm.sendResPSetForm(bukkitPlayer, claimedResidence);
        else if (id == 2) MainForm.sendResTrustedPlayerSettingForm(bukkitPlayer, claimedResidence);
        else if (id == 3) MainForm.sendResTpSetForm(bukkitPlayer, claimedResidence);
        else if (id == 4) MainForm.sendResKickForm(bukkitPlayer, claimedResidence);
        else if (id == 5) MainForm.sendResSensitiveOperationForm(bukkitPlayer, claimedResidence);
        else if (id == 6) sendPrevious();
    }

    @Override
    public void refresh() {
        content("当前管理领地: " + claimedResidence.getName());
    }
}
