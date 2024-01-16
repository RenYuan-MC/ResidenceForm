package ltd.rymc.form.residence.forms.setting.trust;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.setting.ResidenceNoPermissionForm;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.List;

public class ResidenceTrustedPlayerSettingForm extends RSimpleForm {
    private final ClaimedResidence claimedResidence;
    public ResidenceTrustedPlayerSettingForm(Player player, RForm previousForm, ClaimedResidence claimedResidence) {
        super(player, previousForm);
        this.claimedResidence = claimedResidence;

        if (!ResidenceUtils.hasManagePermission(player, claimedResidence) && !player.isOp()) {
            new ResidenceNoPermissionForm(player,previousForm).send();
            return;
        }

        Language.Forms.Manage.TrustedPlayer.Main language = lang().forms().manage().trustedPlayer().main();

        title(language.title());
        button(language.button());
    }

    private String generateTrustedPlayerList(){
        StringBuilder stringBuilder = new StringBuilder();
        List<String> trustedPlayers = ResidenceUtils.getResTrustedPlayerString(claimedResidence);
        for (int i = 0, trustedPlayersSize = trustedPlayers.size(); i < trustedPlayersSize; i++) {
            String playerName = trustedPlayers.get(i);
            stringBuilder.append(playerName).append(i == trustedPlayersSize - 1 ? "" : ", ");
        }
        return stringBuilder.toString();
    }

    @Override
    public void refresh() {
        String content = lang().forms().manage().trustedPlayer().main().content();
        content(String.format(content, claimedResidence.getName(), generateTrustedPlayerList()));
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        if (response.clickedButtonId() == 0) {
            new ResidenceTrustedPlayerChangeForm(bukkitPlayer,this,claimedResidence).send();
            return;
        }
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
