package ltd.rymc.form.residence.forms.setting.trust;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
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

        Language.Forms.Manage.TrustedPlayer.Change language = lang().forms().manage().trustedPlayer().change();

        title(String.format(language.title(), claimedResidence.getName()));
        dropdown(language.dropdown(), generatePlayerNameList());
        input(language.input1(), language.input2());
        toggle(language.toggle());
    }

    private String[] generatePlayerNameList(){
        String[] playerNameList = ArraysUtils.rotate(PlayerUtils.translateToNameList(players),1);
        playerNameList[0] = lang().forms().manage().trustedPlayer().change().choose();
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
