package ltd.rymc.form.residence.forms.select;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.permissions.PermissionGroup;
import com.bekvon.bukkit.residence.selection.SelectionManager;
import ltd.rymc.form.residence.form.RForm;
import ltd.rymc.form.residence.form.RSimpleForm;
import ltd.rymc.form.residence.forms.select.temp.TempSelectionForm;
import ltd.rymc.form.residence.forms.select.temp.TempSelectionImportForm;
import ltd.rymc.form.residence.forms.select.temp.TempSelectionRemoveForm;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.form.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import ren.rymc.residenceform.form.MainForm;

public class ResidenceCreateSelectForm extends RSimpleForm {
    private static final Residence residence = Residence.getInstance();
    public ResidenceCreateSelectForm(Player player, RForm previousForm) {
        super(player, previousForm);
        title("领地创建菜单");
        buttons(
                "开启/关闭自动选区",
                "以你为中心选区",
                "手动输入选区坐标",
                "扩展/缩小选区",
                "创建领地",
                "将此选区保存为草稿",
                "导入领地草稿",
                "删除领地草稿",
                "返回上一级菜单"
        );
    }


    private String generateContent(){
        PermissionGroup group = residence.getPlayerManager().getResidencePlayer(bukkitPlayer).getGroup();
        SelectionManager.Selection selection = residence.getSelectionManager().getSelection(bukkitPlayer);

        Location loc1 = selection.getBaseLoc1(), loc2 = selection.getBaseLoc2();
        if (loc1 == null || loc2 == null) return "\n\n当前选区:\n未创建选区\n\n";

        return  "\n\n当前选区:\n" +
                "顶点坐标1: " + blockLocToString(loc1) + "\n" +
                "顶点坐标2: " + blockLocToString(loc2) + "\n" +
                "世界: " + (loc1.getWorld() == null ? "未知" : loc1.getWorld().getName()) + "\n" +
                "长: " + selection.getBaseArea().getXSize() + "\n" +
                "宽: " + selection.getBaseArea().getZSize() + "\n" +
                "高: " + selection.getBaseArea().getYSize() + "\n" +
                "价格: " + selection.getBaseArea().getCost(group) + "\n\n";
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
        else if (id == 3) MainForm.sendResSelectExpandAndContractForm(bukkitPlayer);
        else if (id == 4) new ResidenceCreateForm(bukkitPlayer,this).send();
        else if (id == 5) new TempSelectionForm(bukkitPlayer,this).send();
        else if (id == 6) new TempSelectionImportForm(bukkitPlayer,this).send();
        else if (id == 7) new TempSelectionRemoveForm(bukkitPlayer,this).send();
        else if (id == 8) sendPrevious();
    }

    @Override
    public void refresh() {
        content(generateContent());
    }
}
