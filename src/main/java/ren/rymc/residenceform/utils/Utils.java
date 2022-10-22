package ren.rymc.residenceform.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Utils {
    public static void toLowerCase(String[] args){
        for (int i = 0;i < args.length;i++){
            args[i] = args[i].toLowerCase(Locale.ROOT);
        }
    }

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

    public static String[] arraysFilter(String[] arr,String fil) {
        return Arrays.stream(arr).filter(s -> !fil.equals(s)).toArray(String[]::new);
    }

    public static List<String> getOnlinePlayerNameArrayList(){
        return new ArrayList<>(Arrays.asList(arraysFilter(getOnlinePlayerNameList(), "")));
    }

    public static Player getPlayerExtract(String name){
        for (Player player : Bukkit.getOnlinePlayers()){
            if(player.getName().equalsIgnoreCase(name)) return player;
        }
        return null;
    }
}















