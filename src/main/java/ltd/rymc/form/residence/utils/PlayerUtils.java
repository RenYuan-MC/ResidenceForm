package ltd.rymc.form.residence.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {

    public static String[] translateToNameList(List<Player> players) {
        String[] playerNames = new String[players.size()];
        for (int i = 0, playersSize = players.size(); i < playersSize; i++) {
            playerNames[i] = players.get(i).getName();
        }
        return playerNames;
    }

    public static Player getPlayerExtract(String name) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equalsIgnoreCase(name)) return player;
        }
        return null;
    }
}
