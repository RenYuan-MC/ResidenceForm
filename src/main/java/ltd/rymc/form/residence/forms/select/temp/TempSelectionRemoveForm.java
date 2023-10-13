package ltd.rymc.form.residence.forms.select.temp;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.List;

public class TempSelectionRemoveForm extends RCustomForm {
    private final List<TempSelection> selections;
    public TempSelectionRemoveForm(Player player, RForm previousForm) {
        super(player, previousForm);
        selections = TempSelection.getPlayerTempSelectionList(player);
        dropdown("领地草稿列表","请选择你要删除的领地草稿",TempSelection.translateToNameList(selections));
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        int dropdown = response.asDropdown(0);

        if (dropdown > 0) {
            TempSelection.removeTempSelection(selections.get(dropdown - 1));
        }

        sendPrevious();


    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
