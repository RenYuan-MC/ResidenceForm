package ltd.rymc.form.residence.forms.teleport;

import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
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
        title("§8领地传送");
        dropdown("你可以使用此下拉框", names);
        input("或使用此输入框", "完整领地名称");
    }

    private String[] generateResidenceNames(){
        String[] names = residenceMap.keySet().toArray(new String[residenceMap.size() + 1]);
        names[0] = "选择领地或使用下方输入框";
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

        Bukkit.dispatchCommand(bukkitPlayer, "res teleport " + residence);

    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
