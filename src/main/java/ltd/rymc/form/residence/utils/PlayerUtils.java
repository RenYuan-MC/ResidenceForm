package ltd.rymc.form.residence.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerUtils {

    public static String[] translateToNameList(List<Player> players) {
        return players.stream()
                .map(Player::getName)
                .toArray(String[]::new);
    }

    public static Player getPlayerExtract(String name) {
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}