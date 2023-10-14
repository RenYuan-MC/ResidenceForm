package ltd.rymc.form.residence.forms.setting.sensitive;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.Facing;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceExpandAndContractForm extends RCustomForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceExpandAndContractForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!claimedResidence.isOwner(player) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

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
        Bukkit.dispatchCommand(bukkitPlayer, "res " + command + " " + claimedResidence.getName() + " " + input.trim());
        bukkitPlayer.teleport(location);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
