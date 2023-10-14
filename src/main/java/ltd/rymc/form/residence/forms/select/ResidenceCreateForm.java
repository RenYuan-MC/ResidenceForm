package ltd.rymc.form.residence.forms.select;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceCreateForm extends RCustomForm {
    private static final Residence residence = Residence.getInstance();
    public ResidenceCreateForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("§8领地创建");
        input("此次创建将花费: " + getCost() + " 金币\n\n请给你要创建的领地起个名字", "支持大小写英文,数字,下划线和连字符");
    }

    private double getCost(){
        PermissionGroup group = residence.getPlayerManager().getResidencePlayer(bukkitPlayer).getGroup();
        CuboidArea baseArea = residence.getSelectionManager().getSelection(bukkitPlayer).getBaseArea();

        return baseArea.getCost(group);
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(0);

        if (!InputUtils.checkInput(input)) {
            sendPrevious();
            return;
        }

        Bukkit.dispatchCommand(bukkitPlayer, "res create " + input);
        residence.getSelectionManager().clearSelection(bukkitPlayer);
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
