package ltd.rymc.form.residence.forms.setting;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.ArraysUtils;
import ltd.rymc.form.residence.utils.ResidenceUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.HashMap;

public class ResidenceSettingSelectForm extends RCustomForm {
    private static final Residence residence = Residence.getInstance();
    HashMap<String, ClaimedResidence> residenceMap;
    String[] names;
    public ResidenceSettingSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);
        residenceMap = ResidenceUtils.getResidenceList(player);
        names = generateResidenceNames();
        title("§8领地传送");
        dropdown("领地列表", names);
    }

    private String[] generateResidenceNames(){
        String[] names = ArraysUtils.rotate(residenceMap.keySet().toArray(new String[0]),1);
        names[0] = "请选择领地(此项为你所在的领地)";
        return names;
    }

    private ClaimedResidence getResidence(CustomFormResponse response){
        if (response.asDropdown(0) != 0) {
            return residenceMap.get(names[response.asDropdown(0)]);
        }

        ClaimedResidence claimedResidence = residence.getResidenceManager().getByLoc(bukkitPlayer);
        if (claimedResidence == null || (!ResidenceUtils.hasManagePermission(bukkitPlayer, claimedResidence) && !bukkitPlayer.isOp())) {
            return null;
        }

        return claimedResidence;

    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        ClaimedResidence residence = getResidence(response);

        if (residence == null){
            sendPrevious();
            return;
        }

        new ResidenceSettingForm(bukkitPlayer, previousForm, residence).send();

    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
