package ltd.rymc.form.residence.forms.setting;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.sensitive.ResidenceSensitiveOperationForm;
import ltd.rymc.form.residence.forms.setting.set.ResidencePlayerSetSelectForm;
import ltd.rymc.form.residence.forms.setting.set.ResidenceSetForm;
import ltd.rymc.form.residence.forms.setting.set.ResidenceTeleportSetForm;
import ltd.rymc.form.residence.forms.setting.trust.ResidenceTrustedPlayerSettingForm;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceSettingForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceSettingForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

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
        if (id == 0) new ResidenceSetForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 1) new ResidencePlayerSetSelectForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 2) new ResidenceTrustedPlayerSettingForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 3) new ResidenceTeleportSetForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 4) new ResidenceKickForm(bukkitPlayer,this, claimedResidence).send();
        else if (id == 5) new ResidenceSensitiveOperationForm(bukkitPlayer,this,claimedResidence).send();
        else if (id == 6) sendPrevious();
    }

    @Override
    public void refresh() {
        content("当前管理领地: " + claimedResidence.getName());
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
