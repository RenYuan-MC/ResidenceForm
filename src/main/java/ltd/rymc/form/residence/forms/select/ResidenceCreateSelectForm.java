package ltd.rymc.form.residence.forms.select;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.response.result.FormResponseResult;

public class ResidenceCreateSelectForm extends RSimpleForm {
    private static final Residence residence = Residence.getInstance();
    public ResidenceCreateSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);

        Language.Forms.Create.Main language = lang().forms().create().main();
        Language.Forms.Create.Main.Buttons buttons = language.buttons();

        title(language.title());
        buttons(
                buttons.auto(),
                buttons.select(),
                buttons.manual(),
                buttons.expend(),
                buttons.create()
        );
    }


    private String generateContent(){
        PermissionGroup group = residence.getPlayerManager().getResidencePlayer(bukkitPlayer).getGroup();
        SelectionManager.Selection selection = residence.getSelectionManager().getSelection(bukkitPlayer);

        Language.Forms.Create.Main.Content language = lang().forms().create().main().content();

        Location loc1 = selection.getBaseLoc1(), loc2 = selection.getBaseLoc2();
        if (loc1 == null || loc2 == null) return String.format("\n\n" + language.title(), "\n" + language.notCreate() + "\n\n");

        return  String.format("\n\n"+ language.title(), "\n" +
                String.format(language.coordinates1(), blockLocToString(loc1)) + "\n" +
                String.format(language.coordinates2(), blockLocToString(loc2)) + "\n" +
                String.format(language.world(), (loc1.getWorld() == null ? language.unknown() : loc1.getWorld().getName())) + "\n" +
                String.format(language.x(), selection.getBaseArea().getXSize()) + "\n" +
                String.format(language.z(), selection.getBaseArea().getZSize())  + "\n" +
                String.format(language.y(), selection.getBaseArea().getYSize())  + "\n" +
                String.format(language.cost(), selection.getBaseArea().getCost(group)) + "\n\n");
    }

    private String blockLocToString(Location location) {
        return location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }

    @Override
    @SuppressWarnings("ConstantValue")
    public void onValidResult(SimpleForm form, SimpleFormResponse response) {
        int id = response.clickedButtonId();
        if (id == 0) Bukkit.dispatchCommand(bukkitPlayer, "res select auto");
        else if (id == 1) new ResidencePlayerSelectForm(bukkitPlayer,this).send();
        else if (id == 2) new ResidenceManualSelectForm(bukkitPlayer, this).send();
        else if (id == 3) new ResidenceSelectExpandAndContractForm(bukkitPlayer, this).send();
        else if (id == 4) new ResidenceCreateForm(bukkitPlayer,this).send();
    }

    @Override
    public void onClosedOrInvalidResult(SimpleForm form, FormResponseResult<SimpleFormResponse> response) {
        sendPrevious();
    }

    @Override
    public void refresh() {
        content(generateContent());
    }
}
