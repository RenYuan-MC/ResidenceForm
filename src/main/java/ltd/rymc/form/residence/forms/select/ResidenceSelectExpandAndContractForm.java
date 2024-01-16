package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.utils.facing.Facing;
import ltd.rymc.form.residence.utils.InputUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceSelectExpandAndContractForm extends RCustomForm {
    public ResidenceSelectExpandAndContractForm(Player player, RForm previousForm) {
        super(player, previousForm);
        Language.Forms.Create.Expand language = lang().forms().create().expand();

        title(language.title());
        dropdown(String.format(language.dropdown(), Facing.facing(player.getLocation().getYaw()).getName()), Facing.facingList());
        input(language.input1(), language.input2());
        toggle(language.toggle());
    }

    @Override
    public void onValidResult(CustomForm form, CustomFormResponse response) {
        String input = response.asInput(1);

        if (!InputUtils.checkInput(input) || input.trim().contains(" ")) {
            sendPrevious();
            return;
        }

        String command = response.asToggle(2) ? "contract" : "expand";
        Location location = bukkitPlayer.getLocation();
        bukkitPlayer.teleport(Facing.translateLocation(location, Facing.facing(response.asDropdown(0))));
        Bukkit.dispatchCommand(bukkitPlayer, "res select " + command + " " + input.trim());
        bukkitPlayer.teleport(location);

        sendPrevious();
    }

    @Override
    public void onClosedOrInvalidResult(CustomForm form, FormResponseResult<CustomFormResponse> response) {
        sendPrevious();
    }
}
