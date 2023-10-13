package ltd.rymc.form.residence.forms.select.temp;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

import java.util.List;

public class TempSelectionImportForm extends RCustomForm {
    private static final Residence residence = Residence.getInstance();
    private final List<TempSelection> selections;
    public TempSelectionImportForm(Player player, RForm previousForm) {
        super(player, previousForm);
        selections = TempSelection.getPlayerTempSelectionList(player);
        title("§8领地草稿导入");
        dropdown("领地草稿列表","请选择你要导入的领地草稿",TempSelection.translateToNameList(selections));
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        int dropdown = response.asDropdown(0);

        if (dropdown <= 0) {
            sendPrevious();
            return;
        }

        TempSelection tempSelection = selections.get(dropdown - 1);

        SelectionManager.Selection selection = residence.getSelectionManager().getSelection(bukkitPlayer);
        Location loc1 = tempSelection.getLoc1(), loc2 = tempSelection.getLoc2();

        if (loc1 == null || loc2 == null) {
            sendPrevious();
            return;
        }

        selection.setBaseLoc1(loc1);
        selection.setBaseLoc2(loc2);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
