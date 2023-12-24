package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.facing.Facing;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

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
        String input = response.asInput(1);

        if (!InputUtils.checkInput(input) || input.trim().contains(" ")) {
            sendPrevious();
            return;
        }

        String command = response.asToggle(2) ? "contract" : "expand";
        Location location = bukkitPlayer.getLocation();
        bukkitPlayer.teleport(Facing.translateLocation(location, Facing.facing(response.asDropdown(0))));
        Bukkit.dispatchCommand(bukkitPlayer, "res select " + command + " " + input.trim());
        bukkitPlayer.teleport(location);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
