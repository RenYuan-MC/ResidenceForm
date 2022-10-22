package ren.rymc.residenceform.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ResidenceUtils {
    public static HashMap<String, FlagPermissions.FlagState> getResidenceFlags(Player player,ClaimedResidence residence) {
        if (residence == null) return new HashMap<>();
        List<String> flags = residence.getPermissions().getPosibleFlags(player, true, false);
        Map<String, Boolean> resFlags = new HashMap<>();
        Map<String, FlagPermissions.FlagState> TempPermMap = new LinkedHashMap<>();
        Map<String, Boolean> globalFlags = Residence.getInstance().getPermissionManager().getAllFlags().getFlags();
        for (Map.Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey())) resFlags.put(one.getKey(), one.getValue());
        }
        for (Map.Entry<String, Boolean> one : globalFlags.entrySet()) {
            String name = one.getKey();
            Flags flag = Flags.getFlag(name);
            if (flag != null && !flag.isGlobalyEnabled()) continue;
            if (!flags.contains(name)) continue;
            if (resFlags.containsKey(name)) {
                TempPermMap.put(name, resFlags.get(name) ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            } else {
                TempPermMap.put(name, FlagPermissions.FlagState.NEITHER);
            }
        }
        TempPermMap.remove("admin");
        return new HashMap<>(TempPermMap);
    }

    public static HashMap<String,FlagPermissions.FlagState> getResidencePlayerFlags(Player player,String targetPlayer,ClaimedResidence residence) {
        if (residence == null) return new HashMap<>();
        Map<String, Boolean> globalFlags = new HashMap<>();
        for (Flags oneFlag : Flags.values()) {
            globalFlags.put(oneFlag.toString(), oneFlag.isEnabled());
        }
        List<String> flags = residence.getPermissions().getPosibleFlags(player, false, false);
        Map<String, Boolean> resFlags = new HashMap<>();
        for (Map.Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey())) resFlags.put(one.getKey(), one.getValue());
        }
        Set<String> PossibleResPFlags = FlagPermissions.getAllPosibleFlags();
        Map<String, Boolean> temp = new HashMap<>();
        for (String one : PossibleResPFlags) {
            if (globalFlags.containsKey(one)) temp.put(one, globalFlags.get(one));
        }
        globalFlags = temp;
        Map<String, Boolean> pFlags = residence.getPermissions().getPlayerFlags(targetPlayer);
        if (pFlags != null) resFlags.putAll(pFlags);
        LinkedHashMap<String, FlagPermissions.FlagState> TempPermMap = new LinkedHashMap<>();
        for (Map.Entry<String, Boolean> one : globalFlags.entrySet()) {
            if (!flags.contains(one.getKey())) continue;
            if (resFlags.containsKey(one.getKey())) {
                TempPermMap.put(one.getKey(), resFlags.get(one.getKey()) ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            } else {
                TempPermMap.put(one.getKey(), FlagPermissions.FlagState.NEITHER);
            }
        }
        return new HashMap<>(TempPermMap);
    }

    public static List<String> getResidencePlayerFlagsList(Player player,String targetPlayer,ClaimedResidence residence){
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, FlagPermissions.FlagState> entry : getResidencePlayerFlags(player,targetPlayer,residence).entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    public static List<String> getResidenceFlagsList(Player player,ClaimedResidence residence){
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, FlagPermissions.FlagState> entry : getResidenceFlags(player,residence).entrySet()) {
            list.add(entry.getKey());
        }
        return list;
    }

    public static int flagToInt(FlagPermissions.FlagState flag){
        if (flag.equals(FlagPermissions.FlagState.FALSE)) return 0;
        if (flag.equals(FlagPermissions.FlagState.NEITHER)) return 1;
        if (flag.equals(FlagPermissions.FlagState.TRUE)) return 2;
        return 1;
    }
    public static FlagPermissions.FlagState intToFlag(int flag){
        if (flag == 0) return FlagPermissions.FlagState.FALSE;
        if (flag == 1) return FlagPermissions.FlagState.NEITHER;
        if (flag == 2) return FlagPermissions.FlagState.TRUE;
        return FlagPermissions.FlagState.NEITHER;
    }

    public static HashMap<String,ClaimedResidence> getResidenceList(Player player){
        Residence res = Residence.getInstance();
        TreeMap<String, ClaimedResidence> ownedResidences = res.getPlayerManager().getResidencesMap(player.getName(), true, false, null);
        ownedResidences.putAll(res.getRentManager().getRentsMap(player.getName(), false, null));
        ownedResidences.putAll(res.getPlayerManager().getTrustedResidencesMap(player.getName(), true, false, null));
        HashMap<String,ClaimedResidence> hashMap = new HashMap<>();
        for (Map.Entry<String, ClaimedResidence> entry : ownedResidences.entrySet()) {
            if (hasManagePermission(player,entry.getValue())) hashMap.put(entry.getKey(),entry.getValue());
        }
        return hashMap;
    }

    public static boolean hasManagePermission(Player player,ClaimedResidence residence){
        return residence.isOwner(player.getUniqueId()) || residence.getPermissions().playerHas(player, Flags.admin, false);
    }

    public static List<ResidencePlayer> getResTrustedPlayer(ClaimedResidence residence){
        return new ArrayList<>(residence.getTrustedPlayers());
    }

    public static List<String> getResTrustedPlayerString(ClaimedResidence residence){
        List<String> list = new ArrayList<>();
        for (ResidencePlayer player : getResTrustedPlayer(residence)){
            list.add(Bukkit.getOfflinePlayer(UUID.fromString(player.getName())).getName());
        }
        return list;
    }

    public static void kickPlayer(String targetPlayer,ClaimedResidence residence){
        Player player = Utils.getPlayerExtract(targetPlayer);
        if (player == null) return;
        if (!residence.getPlayersInResidence().contains(player)) return;
        player.closeInventory();
        player.teleport(residence.getOutsideFreeLoc(player.getLocation(), player));
    }

    public static String[] getPlayersInResidence(ClaimedResidence residence){
        List<Player> players = residence.getPlayersInResidence();
        String[] playerNames = new String[players.size()+1];
        playerNames[0] = "";
        for (int i = 0, j = players.size(); i < j; i++) {
            playerNames[i+1] = players.get(i).getName();
        }
        return playerNames;
    }

}
