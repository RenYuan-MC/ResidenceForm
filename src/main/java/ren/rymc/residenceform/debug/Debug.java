package ren.rymc.residenceform.debug;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ren.rymc.residenceform.utils.ResidenceUtils;
import ren.rymc.residenceform.utils.Utils;

import java.util.*;

public class Debug {
    public static boolean debugCommand(CommandSender sender,String[] args){
        Utils.toLowerCase(args);
        if (!(sender instanceof Player)) return true;
        Player player = ((Player) sender);
        Residence res = Residence.getInstance();
        ClaimedResidence residence = res.getResidenceManager().getByLoc(player);
        if (residence == null) return true;
        if (args.length == 2 && args[1].equals("flags")) {
            if (!player.isOp()) return true;
            for (Map.Entry<String, FlagPermissions.FlagState> entry : ResidenceUtils.getResidenceFlags(player,residence).entrySet()) {
                sender.sendMessage("§f[§6服务器§f] " + entry.getKey() + ": " + entry.getValue().name().toLowerCase(Locale.ROOT));
            }
        } else if (args.length == 3 && args[1].equals("flags")){
            if (!player.isOp()) return true;
            FlagPermissions.FlagState flagState = ResidenceUtils.getResidenceFlags(player,residence).get(args[2]);
            if (flagState != null) sender.sendMessage("§f[§6服务器§f] " + args[2] + ": " + flagState.name().toLowerCase(Locale.ROOT));
        } else if (args.length == 4 && args[1].equals("set")) {
            ClaimedResidence resClaim = res.getResidenceManager().getByLoc(player);
            if (resClaim == null) return true;
            if (!resClaim.getPermissions().playerHas(player, Flags.admin, false) && !player.isOp()) return true;
            HashMap<String, FlagPermissions.FlagState> flagList = ResidenceUtils.getResidenceFlags(player,residence);
            if (flagList.get(args[2]) == null) return true;
            FlagPermissions.FlagState flagState;
            switch (args[3]) {
                case "true":
                    flagState = FlagPermissions.FlagState.TRUE;
                    break;
                case "false":
                    flagState = FlagPermissions.FlagState.FALSE;
                    break;
                case "neither":
                    flagState = FlagPermissions.FlagState.NEITHER;
                    break;
                default:
                    return true;
            }
            resClaim.getPermissions().setFlag(args[2], flagState);
            sender.sendMessage("§f[§6服务器§f] " + args[2] + ": "+ flagList.get(args[2]).name().toLowerCase(Locale.ROOT) + " -> " + args[3]);
        }else if (args.length == 3 && args[1].equals("pflags")){
            if (!player.isOp()) return true;
            for (Map.Entry<String, FlagPermissions.FlagState> entry : ResidenceUtils.getResidencePlayerFlags(player,args[2],residence).entrySet()) {
                sender.sendMessage("§f[§6服务器§f] " + entry.getKey() + ": " + entry.getValue().name().toLowerCase(Locale.ROOT));
            }
        }else if (args.length == 4 && args[1].equals("pflags")){
            if (!player.isOp()) return true;
            FlagPermissions.FlagState flagState = ResidenceUtils.getResidencePlayerFlags(player,args[2],residence).get(args[3]);
            if (flagState != null) sender.sendMessage("§f[§6服务器§f] " + args[3] + "("+ args[2] + "): " + flagState.name().toLowerCase(Locale.ROOT));
        } else if (args.length == 5 && args[1].equals("pset")) {
            if (!residence.getPermissions().playerHas(player, Flags.admin, false) && !player.isOp()) return true;
            HashMap<String, FlagPermissions.FlagState> flagList = ResidenceUtils.getResidencePlayerFlags(player,args[2],residence);
            if (flagList.get(args[3]) == null) return true;
            FlagPermissions.FlagState flagState;
            switch (args[4]) {
                case "true":
                    flagState = FlagPermissions.FlagState.TRUE;
                    break;
                case "false":
                    flagState = FlagPermissions.FlagState.FALSE;
                    break;
                case "neither":
                    flagState = FlagPermissions.FlagState.NEITHER;
                    break;
                default:
                    return true;
            }
            residence.getPermissions().setPlayerFlag(args[2],args[3],flagState);
        }
        return true;
    }

    public static List<String> debugTab(CommandSender sender,String[] args){
        if (args.length == 2) return new ArrayList<>(Arrays.asList("flags","set","pflags","pset"));
        if (!(sender instanceof Player)) return new ArrayList<>();
        Player player = (Player) sender;
        Residence res = Residence.getInstance();
        ClaimedResidence residence = res.getResidenceManager().getByLoc(player);
        if (args[1].equals("flags") && args.length == 3){
            return ResidenceUtils.getResidenceFlagsList(player,residence);
        }
        if (args[1].equals("pflags")){
            if (args.length == 3) return Utils.getOnlinePlayerNameArrayList();
            if (args.length != 4) return new ArrayList<>();
            return ResidenceUtils.getResidencePlayerFlagsList(player,args[2],residence);
        }
        if (args[1].equals("set")) {
            if (args.length == 3) return ResidenceUtils.getResidenceFlagsList(player,residence);
            if (args.length == 4) return new ArrayList<>(Arrays.asList("true", "false", "neither"));
        }
        if (args[1].equals("pset")) {
            if (args.length == 3) return Utils.getOnlinePlayerNameArrayList();
            if (args.length == 4) return ResidenceUtils.getResidencePlayerFlagsList(player,args[2],residence);
            if (args.length == 5) return new ArrayList<>(Arrays.asList("true", "false", "neither"));
        }
        return new ArrayList<>();
    }

}
