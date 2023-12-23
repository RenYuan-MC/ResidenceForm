package ltd.rymc.form.residence.forms.select.temp;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class TempSelectionForm extends RCustomForm {
    public TempSelectionForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("§8领地选区草稿保存");
        input("领地草稿会在服务器重启后消失\n一个账号最多保存5个\n\n请为此草稿设置一个名称", "不能为空");
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);
        if (!InputUtils.checkInput(input)) {
            sendPrevious();
            return;
        }

        SelectionManager.Selection selection = Residence.getInstance().getSelectionManager().getSelection(bukkitPlayer);
        TempSelection tempSelection = new TempSelection(bukkitPlayer, input, selection.getBaseLoc1(), selection.getBaseLoc2());
        TempSelection.addTempSelection(tempSelection);
        Residence.getInstance().getSelectionManager().clearSelection(bukkitPlayer);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
