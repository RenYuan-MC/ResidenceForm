package ltd.rymc.form.residence.forms.select.temp;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TempSelection {

    private static final List<TempSelection> tempSelectionList = new ArrayList<>();
    private final Location loc1;
    private final Location loc2;
    private final String name;
    private final Player player;

    public TempSelection(Player player, String name, Location loc1, Location loc2) {
        this.player = player;
        this.name = name;
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public static void addTempSelection(TempSelection selection) {
        if (getPlayerTempSelectionList(selection.getPlayer()).size() >= 5) return;
        tempSelectionList.add(selection);
    }

    public static void removeTempSelection(TempSelection tempSelection) {
        tempSelectionList.remove(tempSelection);
    }

    public static List<TempSelection> getPlayerTempSelectionList(Player player) {
        List<TempSelection> list = new ArrayList<>();
        for (TempSelection tempSelection : tempSelectionList) {
            if (tempSelection.getPlayer().equals(player)) list.add(tempSelection);
        }
        return list;
    }

    public static String[] translateToNameList(List<TempSelection> list) {
        String[] selectionNameList = new String[list.size()];
        for (int i = 0, j = list.size(); i < j; i++) {
            selectionNameList[i] = list.get(i).getName();
        }
        return selectionNameList;
    }

    public String getName() {
        return name;
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public Player getPlayer() {
        return player;
    }
}
