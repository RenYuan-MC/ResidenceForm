package ltd.rymc.form.residence.forms.select;

import ltd.rymc.form.residence.form.RCustomForm;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.language.Language;
import ltd.rymc.form.residence.utils.InputUtils;
import ltd.rymc.form.residence.utils.facing.Facing;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.CustomForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceSelectExpandAndContractForm extends RCustomForm {
    public ResidenceSelectExpandAndContractForm(Player player, RForm previousForm) {
        super(player, previousForm);
        Language.Section createExpand = section("forms.create.expand");

        title(createExpand.text("title"));
        dropdown(String.format(createExpand.text("dropdown"), Facing.facing(player.getLocation().getYaw()).getName()), Facing.facingList());
        input(createExpand.text("input1"), createExpand.text("input2"));
        toggle(createExpand.text("toggle"));
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
