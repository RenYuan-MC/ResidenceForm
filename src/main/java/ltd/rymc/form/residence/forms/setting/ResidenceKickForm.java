package ltd.rymc.form.residence.forms.setting;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.InputUtils;
import ltd.rymc.form.residence.utils.PlayerUtils;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.List;

public class ResidenceKickForm extends RCustomForm {
    private final ClaimedResidence claimedResidence;
    List<Player> players;
    public ResidenceKickForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;
        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }
        players = claimedResidence.getPlayersInResidence();

        Language.Forms.Manage.Kick language = lang().forms().manage().kick();

        title(String.format(language.title(),claimedResidence.getName()));
        dropdown(language.dropdown(), generatePlayerNameList());
        input(language.input1(), language.input2());
    }

    private String[] generatePlayerNameList(){
        String[] playerNameList = ArraysUtils.rotate(PlayerUtils.translateToNameList(players),1);
        playerNameList[0] = lang().forms().manage().kick().choose();
        return playerNameList;
    }

    private String getPlayerName(CustomFormResponse response){
        String input = response.asInput(1);
        int dropdown = response.asDropdown(0);

        if (InputUtils.checkInput(input) && !input.trim().contains(" ")) {
            return input;
        }

        if (dropdown > 0) {
            return players.get(dropdown - 1).getName();
        }

        return null;
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String targetPlayer = getPlayerName(response);

        if(targetPlayer == null){
            sendPrevious();
            return;
        }

        ResidenceUtils.kickPlayer(targetPlayer, claimedResidence);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
