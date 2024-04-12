package ltd.rymc.form.residence.forms.setting.trust;

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

public class ResidenceTrustedPlayerChangeForm extends RCustomForm {
    private final ClaimedResidence claimedResidence;
    List<Player> players;
    public ResidenceTrustedPlayerChangeForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        players = new ArrayList<>(Bukkit.getOnlinePlayers());


        Language.Section trustedPlayerChange = section("forms.manage.trusted-player.change");

        title(String.format(trustedPlayerChange.text("title"), claimedResidence.getName()));
        dropdown(trustedPlayerChange.text("dropdown"), generatePlayerNameList());
        input(trustedPlayerChange.text("input1"), trustedPlayerChange.text("input2"));
        toggle(trustedPlayerChange.text("toggle"));
    }

    private String[] generatePlayerNameList(){
        String[] playerNameList = ArraysUtils.rotate(PlayerUtils.translateToNameList(players),1);
        playerNameList[0] = text("forms.manage.trusted-player.change.choose");
        return playerNameList;
    }

    private String getPlayerName(CustomFormResponse response){
        String input = response.asInput(1);
        int dropdown = response.asDropdown(0);

        if (InputUtils.checkInput(input) && !input.trim().contains(" ")) {
            return input;
        }

        if (dropdown != 0) {
            return players.get(dropdown - 1).getName();
        }

        return null;
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String playerName = getPlayerName(response);

        if (playerName == null){
            sendPrevious();
            return;
        }

        String flagState = response.asToggle(2) ? "remove" : "true";
        claimedResidence.getPermissions().setPlayerFlag(bukkitPlayer, playerName, "trusted", flagState, false, false);
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
