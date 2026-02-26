package ltd.rymc.form.residence.forms.setting.trust;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.PlayerManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
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
import java.util.function.Consumer;

public class ResidenceTrustedPlayerChangeForm extends RCustomForm {

    private static final PlayerManager playerManager = Residence.getInstance().getPlayerManager();

    private final ClaimedResidence claimedResidence;
    private final List<Player> players;

    public ResidenceTrustedPlayerChangeForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;
        this.players = new ArrayList<>(Bukkit.getOnlinePlayers());

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

    private ResidencePlayer getResidencePlayer(CustomFormResponse response) {
        String input = response.asInput(1);
        int dropdown = response.asDropdown(0);

        if (InputUtils.isValid(input) && !input.trim().contains(" ")) {
            return playerManager.getResidencePlayer(input.trim());
        }

        if (dropdown != 0) {
            return playerManager.getResidencePlayer(players.get(dropdown - 1));
        }

        return null;
    }

    @Override
    public void send() {
        if (!ResidenceUtils.hasManagePermission(bukkitPlayer, claimedResidence) && !bukkitPlayer.isOp()) {
            new ResidenceNoPermissionForm(bukkitPlayer,previousForm).send();
            return;
        }

        super.send();
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        ResidencePlayer targetPlayer = getResidencePlayer(response);

        if (targetPlayer == null){
            sendPrevious();
            return;
        }

        ResidencePermissions permissions = claimedResidence.getPermissions();
        if (response.asToggle(2)) {
            permissions.removeAllPlayerFlags(bukkitPlayer, targetPlayer.getUniqueId(), false);
        } else {
            permissions.setPlayerFlag(bukkitPlayer, targetPlayer.getUniqueId(), "trusted", FlagPermissions.FlagState.TRUE, false, false);
        }

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
