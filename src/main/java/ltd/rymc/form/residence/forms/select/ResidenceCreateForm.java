package ltd.rymc.form.residence.forms.select;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import ltd.rymc.form.residence.configs.Language;
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

        Language.Forms.Create.ResCreate language = lang().forms().create().create();

        title(language.title());
        input(String.format(language.cost(), getCost()) + "\n\n" + language.input1(), language.input2());
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
