package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidencePlayerSelectForm extends RCustomForm {
    public ResidencePlayerSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("§8以你为中心创建领地");
        input("长", "数字(整数)");
        input("宽", "数字(整数)");
        input("高", "数字(整数)");
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String X = response.asInput(0);
        String Y = response.asInput(2);
        String Z = response.asInput(1);
        Bukkit.dispatchCommand(bukkitPlayer, "res select " + X + " " + Y + " " + Z);
        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
