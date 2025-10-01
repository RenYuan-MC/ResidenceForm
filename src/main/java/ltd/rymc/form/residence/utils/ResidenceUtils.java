package ltd.rymc.form.residence.utils;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.containers.ResidencePlayer;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class ResidenceUtils {

    /**
     * 获取领地权限
     * 参考实现 com.bekvon.bukkit.residence.gui.setFlagInfo#recalculateResidence()
     *
     * @param player    操作者
     * @param residence 领地
     * @return 领地权限列表
     */
    public static HashMap<String, FlagPermissions.FlagState> getResidenceFlags(Player player, ClaimedResidence residence) {

        // TODO: 检查res admin项
        List<String> flags = residence.getPermissions().getPossibleFlags(player, true, false);

        Map<String, Boolean> resFlags = new HashMap<>();
        Map<String, FlagPermissions.FlagState> TempPermMap = new LinkedHashMap<>();

        Map<String, Boolean> globalFlags = Residence.getInstance().getPermissionManager().getAllFlags().getFlags();

        for (Map.Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey())) {
                resFlags.put(one.getKey(), one.getValue());
            }
        }

        for (Map.Entry<String, Boolean> one : globalFlags.entrySet()) {
            String fname = one.getKey();

            Flags flag = Flags.getFlag(fname);

            if (flag != null && !flag.isGlobalyEnabled())
                continue;

            if (!flags.contains(one.getKey())) {
                continue;
            }

            if (resFlags.containsKey(one.getKey()))
                TempPermMap.put(one.getKey(), resFlags.get(one.getKey()) ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            else
                TempPermMap.put(one.getKey(), FlagPermissions.FlagState.NEITHER);
        }

        TempPermMap.remove("admin");

        return sortByKeyASC(TempPermMap);
    }

    /**
     * 获取领地玩家权限
     * 参考实现 com.bekvon.bukkit.residence.gui.setFlagInfo#recalculatePlayer()
     *
     * @param player       操作者
     * @param targetPlayer 目标玩家
     * @param residence    领地
     * @return 领地玩家权限列表
     */
    public static HashMap<String, FlagPermissions.FlagState> getResidencePlayerFlags(Player player, String targetPlayer, ClaimedResidence residence) {
        Map<String, Boolean> globalFlags = new HashMap<>();
        for (Flags oneFlag : Flags.values()) {
            globalFlags.put(oneFlag.toString(), oneFlag.isEnabled());
        }

        // TODO: 检查res admin项
        List<String> flags = residence.getPermissions().getPossibleFlags(player, false, false);

        Map<String, Boolean> resFlags = new HashMap<>();

        for (Map.Entry<String, Boolean> one : residence.getPermissions().getFlags().entrySet()) {
            if (flags.contains(one.getKey()))
                resFlags.put(one.getKey(), one.getValue());
        }

        if (targetPlayer != null) {

            Set<String> possibleResPFlags = FlagPermissions.getAllPossibleFlags();
            Map<String, Boolean> temp = new HashMap<>();
            for (String one : possibleResPFlags) {
                if (globalFlags.containsKey(one)) {
                    temp.put(one, globalFlags.get(one));
                }

            }
            globalFlags = temp;

            Map<String, Boolean> pFlags = residence.getPermissions().getPlayerFlags(targetPlayer);

            if (pFlags != null) {
                resFlags.putAll(pFlags);
            }
        }

        LinkedHashMap<String, FlagPermissions.FlagState> TempPermMap = new LinkedHashMap<>();

        for (Map.Entry<String, Boolean> one : globalFlags.entrySet()) {
            if (!flags.contains(one.getKey()))
                continue;

            if (resFlags.containsKey(one.getKey()))
                TempPermMap.put(one.getKey(), resFlags.get(one.getKey()) ? FlagPermissions.FlagState.TRUE : FlagPermissions.FlagState.FALSE);
            else
                TempPermMap.put(one.getKey(), FlagPermissions.FlagState.NEITHER);
        }

        return sortByKeyASC(TempPermMap);


    }

    /**
     * 比 Residence.getInstance().getSortingManager().sortByKeyASC() 更好且泛型支持的排序
     *
     * @param <T>       值类型
     * @param unsortMap 待排序Map
     * @return 已排序Map
     */
    public static <T> LinkedHashMap<String, T> sortByKeyASC(Map<String, T> unsortMap) {
        return unsortMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static int flagToInt(FlagPermissions.FlagState flag) {
        if (flag.equals(FlagPermissions.FlagState.FALSE)) return 0;
        if (flag.equals(FlagPermissions.FlagState.NEITHER)) return 1;
        if (flag.equals(FlagPermissions.FlagState.TRUE)) return 2;
        return 1;
    }

    public static FlagPermissions.FlagState intToFlag(int flag) {
        if (flag == 0) return FlagPermissions.FlagState.FALSE;
        if (flag == 1) return FlagPermissions.FlagState.NEITHER;
        if (flag == 2) return FlagPermissions.FlagState.TRUE;
        return FlagPermissions.FlagState.NEITHER;
    }

    public static HashMap<String, ClaimedResidence> getResidenceList(Player player) {
        HashMap<String, ClaimedResidence> hashMap = new HashMap<>();
        for (Map.Entry<String, ClaimedResidence> entry : getNormalResidenceList(player).entrySet()) {
            if (hasManagePermission(player, entry.getValue())) hashMap.put(entry.getKey(), entry.getValue());
        }
        return hashMap;
    }

    public static HashMap<String, ClaimedResidence> getNormalResidenceList(Player player) {
        Residence res = Residence.getInstance();
        TreeMap<String, ClaimedResidence> ownedResidences = res.getPlayerManager().getResidencesMap(player.getName(), true, false, null);
        ownedResidences.putAll(res.getRentManager().getRentsMap(player.getName(), false, null));
        ownedResidences.putAll(res.getPlayerManager().getTrustedResidencesMap(player.getName(), true, false, null));
        return new HashMap<>(ownedResidences);
    }

    public static boolean hasManagePermission(Player player, ClaimedResidence residence) {
        return residence.isOwner(player.getUniqueId()) || residence.getPermissions().playerHas(player, Flags.admin, false);
    }

    public static List<ResidencePlayer> getResTrustedPlayer(ClaimedResidence residence) {
        return new ArrayList<>(residence.getTrustedPlayers());
    }

    public static List<String> getResTrustedPlayerString(ClaimedResidence residence) {
        List<String> list = new ArrayList<>();
        for (ResidencePlayer player : getResTrustedPlayer(residence)) {
            list.add(Bukkit.getOfflinePlayer(UUID.fromString(player.getName())).getName());
        }
        return list;
    }

    public static void kickPlayer(String targetPlayer, ClaimedResidence residence) {
        Player player = PlayerUtils.getPlayerExtract(targetPlayer);
        if (player == null) return;
        residence.kickFromResidence(player);
    }
}
