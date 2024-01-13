package ltd.rymc.form.residence.forms.teleport;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.InputUtils;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.HashMap;

public class ResidenceTeleportForm extends RCustomForm {
    HashMap<String, ClaimedResidence> residenceMap;
    String[] names;
    public ResidenceTeleportForm(Player player, RForm previousForm) {
        super(player, previousForm);
        residenceMap = ResidenceUtils.getNormalResidenceList(player);
        names = generateResidenceNames();
        title(lang().teleportTitle());
        dropdown(lang().formDropdown(), names);
        input(lang().formInput(), lang().formFullResName());
    }

    private String[] generateResidenceNames(){
        String[] names = ArraysUtils.rotate(residenceMap.keySet().toArray(new String[0]),1);
        names[0] = lang().formChooseRes();
        return names;
    }

    private String getResidence(CustomFormResponse response){
        String input = response.asInput(1);
        int dropdown = response.asDropdown(0);

        if (InputUtils.checkInput(input) && !input.trim().contains(" ")) {
            return input.trim();
        }

        if (dropdown > 0) {
            return names[dropdown];
        }

        return null;
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String residence = getResidence(response);

        if (residence == null) {
            sendPrevious();
            return;
        }

        Bukkit.dispatchCommand(bukkitPlayer, "res tp " + residence);

    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
