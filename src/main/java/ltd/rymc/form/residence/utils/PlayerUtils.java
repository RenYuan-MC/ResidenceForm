package ltd.rymc.form.residence.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;

public class PlayerUtils {

    public static String[] getOnlinePlayerNameList() {
        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        String[] playerNames = new String[onlinePlayers.size() + 1];
        playerNames[0] = "";
        int i = 1;
        for (Player player : onlinePlayers) {
            playerNames[i++] = player.getName();
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
