package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.Facing;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;

public class ResidenceSelectExpandAndContractForm extends RCustomForm {
    public ResidenceSelectExpandAndContractForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("§8领地选区扩展/缩小");
        dropdown("你当前面对的方向: " + Facing.facing(player.getLocation().getYaw()).getName() + "\n\n扩展/缩小的方向", Facing.facingList());
        input("扩展范围", "数字,不填则返回上一级菜单");
        toggle("模式(关闭为扩展,开启为缩小)");
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {

    }
}
