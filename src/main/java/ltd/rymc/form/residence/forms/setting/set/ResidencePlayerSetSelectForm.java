package ltd.rymc.form.residence.forms.setting.set;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.language.Language;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.InputUtils;
import ltd.rymc.form.residence.utils.PlayerUtils;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

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

        Language.Section playerSetSelect = section("forms.manage.player-set.select");

        title(String.format(playerSetSelect.text("title"), claimedResidence.getName()));
        dropdown(playerSetSelect.text("dropdown"), generatePlayerNameList());
        input(playerSetSelect.text("input1"), playerSetSelect.text("input2"));
    }

    private String[] generatePlayerNameList(){
        String[] playerNameList = ArraysUtils.rotate(PlayerUtils.translateToNameList(players),1);
        playerNameList[0] = text("forms.manage.player-set.select.choose");
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

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
