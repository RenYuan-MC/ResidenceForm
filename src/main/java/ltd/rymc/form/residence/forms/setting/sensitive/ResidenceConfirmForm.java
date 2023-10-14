package ltd.rymc.form.residence.forms.setting.sensitive;

import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceConfirmForm extends RSimpleForm {
    private final Runnable runnable;
    public ResidenceConfirmForm(Player player, RForm previousForm, String title, Runnable runnable) {
        super(player, previousForm);
        this.runnable = runnable;
        title(title);
        content("你确定?此操作不可撤回!\n\n");
        buttons(
                "确定",
                "再想想"
        );
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        if (response.clickedButtonId() == 0) runnable.run();
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }
}
