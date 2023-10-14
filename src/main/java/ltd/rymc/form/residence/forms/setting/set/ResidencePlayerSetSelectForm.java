package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.InputUtils;
import ltd.rymc.form.residence.utils.PlayerUtils;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;

import java.util.ArrayList;
import java.util.List;

public class ResidencePlayerSetSelectForm extends RCustomForm {
    private final ClaimedResidence claimedResidence;
    List<Player> players;
    public ResidencePlayerSetSelectForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;
        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }
        players = new ArrayList<>(Bukkit.getOnlinePlayers());

        title("§8领地 §l" + claimedResidence.getName() + " §r§8玩家权限设置");
        dropdown("在线玩家列表", generatePlayerNameList());
        input("如玩家不在线或上方表格不便使用请使用输入框", "需要完整玩家名称(包括大小写)");
    }

    private String[] generatePlayerNameList(){
        String[] playerNameList = ArraysUtils.rotate(PlayerUtils.translateToNameList(players),1);
        playerNameList[0] = "请选择玩家";
        return playerNameList;
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(1);
        int dropdown = response.asDropdown(0);

        if (InputUtils.checkInput(input) && !input.trim().contains(" ")) {
            new ResidencePlayerSetForm(bukkitPlayer, previousForm, claimedResidence, input).send();
            return;
        }

        if (dropdown != 0) {
            new ResidencePlayerSetForm(bukkitPlayer, previousForm, claimedResidence, players.get(dropdown - 1).getName()).send();
            return;
        }

        sendPrevious();
    }
}
