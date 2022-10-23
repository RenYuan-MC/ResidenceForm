package ren.rymc.residenceform.utils;

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

    public static void removeTempSelection(Player player, String name) {
        TempSelection tempSelection = null;
        for (TempSelection selection : getPlayerTempSelectionList(player)) {
            if (selection.getName().equals(name)) tempSelection = selection;
        }
        if (tempSelection == null) return;
        tempSelectionList.remove(tempSelection);
    }

    public static List<TempSelection> getPlayerTempSelectionList(Player player) {
        List<TempSelection> list = new ArrayList<>();
        for (TempSelection tempSelection : tempSelectionList) {
            if (tempSelection.getPlayer().equals(player)) list.add(tempSelection);
        }
        return list;
    }

    public static String[] getPlayerTempSelectionNameList(Player player) {
        List<TempSelection> list = getPlayerTempSelectionList(player);
        String[] selectionNameList = new String[list.size() + 1];
        selectionNameList[0] = "";
        for (int i = 0, j = list.size(); i < j; i++) {
            selectionNameList[i + 1] = list.get(i).getName();
        }
        return selectionNameList;
    }

    public static TempSelection getTempSelection(Player player, String name) {
        for (TempSelection tempSelection : getPlayerTempSelectionList(player)) {
            if (tempSelection.getName().equals(name)) return tempSelection;
        }
        return null;
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
