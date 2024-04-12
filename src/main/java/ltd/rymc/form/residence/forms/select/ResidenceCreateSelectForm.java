package ltd.rymc.form.residence.forms.select;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.language.Language;
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

        Language.Section createMain = section("forms.create.main");
        Language.Section createMainButtons = createMain.section("buttons");

        title(createMain.text("title"));
        buttons(
                createMainButtons.text("auto"),
                createMainButtons.text("select"),
                createMainButtons.text("manual"),
                createMainButtons.text("expend"),
                createMainButtons.text("create")
        );
    }


    private String generateContent(){
        PermissionGroup group = residence.getPlayerManager().getResidencePlayer(bukkitPlayer).getGroup();
        SelectionManager.Selection selection = residence.getSelectionManager().getSelection(bukkitPlayer);

        Language.Section content = section("forms.create.main.content");

        Location loc1 = selection.getBaseLoc1(), loc2 = selection.getBaseLoc2();
        if (loc1 == null || loc2 == null) return String.format("\n\n" + content.text("title"), "\n" + content.text("not-create") + "\n\n");

        return String.format("\n\n"+ content.text("title"), "\n" +
               String.format(content.text("coordinates1"), blockLocToString(loc1)) + "\n" +
               String.format(content.text("coordinates2"), blockLocToString(loc2)) + "\n" +
               String.format(content.text("world"), (loc1.getWorld() == null ? content.text("unknown") : loc1.getWorld().getName())) + "\n" +
               String.format(content.text("x"), selection.getBaseArea().getXSize()) + "\n" +
               String.format(content.text("z"), selection.getBaseArea().getZSize()) + "\n" +
               String.format(content.text("y"), selection.getBaseArea().getYSize()) + "\n" +
               String.format(content.text("cost"), selection.getBaseArea().getCost(group)) + "\n\n");
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
