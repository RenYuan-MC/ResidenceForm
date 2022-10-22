package ren.rymc.residenceform.Form;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import ren.rymc.residenceform.ResidenceForm;
import ren.rymc.residenceform.utils.Facing;
import ren.rymc.residenceform.utils.ResidenceUtils;
import ren.rymc.residenceform.utils.Utils;

import java.util.*;

public class MainForm {
    public static void sendMainResidenceForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("领地菜单")
                        .content("§7领地基岩版菜单 ResidenceForm v0.1.5")
                        .button("领地管理")
                        .button("插件信息")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                int id = response.getClickedButtonId();
                                if (id == 0) sendResSettingForm(player);
                                if (id == 1) PluginInfo.sendPluginInfoForm(player);
                            }
                        })
        );
    }

    public static void sendResSettingForm(Player player,ClaimedResidence residence){
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("领地管理菜单")
                        .content("当前管理领地: " + residence.getName())
                        .button("公共权限设置")
                        .button("玩家权限设置")
                        .button("信任玩家管理")
                        .button("领地扩展/缩小")
                        .button("返回领地选择")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                int id = response.getClickedButtonId();
                                if (id == 0) sendResSetForm(player,residence);
                                if (id == 1) sendResPSetForm(player,residence);
                                if (id == 2) sendResTrustedPlayerSettingForm(player,residence);
                                if (id == 3) sendResExtendAndContractForm(player,residence);
                                if (id == 4) sendResSettingForm(player);
                            }
                        })
        );
    }

    public static void sendResExtendAndContractForm(Player player,ClaimedResidence residence){
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() +" §r§8扩展/缩小")
                        .dropdown("你当前面对的方向: " + Facing.facing(player.getLocation().getYaw()).getName() + "\n\n扩展/缩小的方向",Facing.facingList())
                        .input("扩展范围", "数字,不填则返回上一级菜单")
                        .toggle("模式(关闭为扩展,开启为缩小)")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(1);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ")){
                                    String command = response.getToggle(2) ? "contract" : "expand";
                                    Location location = player.getLocation();
                                    player.teleport(Facing.translateLocation(location,Facing.facing(response.getDropdown(0))));
                                    Bukkit.dispatchCommand(player,"res " + command + " " + input.trim());
                                    player.teleport(location);
                                }else{
                                    sendResSettingForm(player,residence);
                                }
                            }
                        })
        );
    }




    public static void sendResTrustedPlayerSettingForm(Player player,ClaimedResidence residence){
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> trustedPlayers = ResidenceUtils.getResTrustedPlayerString(residence);
        for (int i = 0, trustedPlayersSize = trustedPlayers.size(); i < trustedPlayersSize; i++) {
            String playerName = trustedPlayers.get(i);
            stringBuilder.append(playerName).append(i == trustedPlayersSize - 1 ? "" :", ");
        }
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("领地信任玩家管理菜单")
                        .content("当前管理领地: " + residence.getName() + "\n信任玩家: " + stringBuilder)
                        .button("添加/删除信任玩家")
                        .button("返回领地管理")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                int id = response.getClickedButtonId();
                                if (id == 0) sendResTrustedPlayerAddForm(player, residence);
                                if (id == 1) sendResSettingForm(player,residence);
                            }
                        })
        );
    }

    public static void sendResTrustedPlayerAddForm(Player player,ClaimedResidence residence){
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!ResidenceUtils.hasManagePermission(player,residence) && !player.isOp()) return;
        String[] playerNameList = Utils.getOnlinePlayerNameList();
        playerNameList[0] = "请选择玩家";
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() +" §r§8信任玩家添加")
                        .dropdown("在线玩家列表",playerNameList)
                        .input("如玩家不在线或上方表格使用过于麻烦请使用下方输入框", "需要完整玩家名称(包括大小写)")
                        .toggle("模式(关闭为添加,开启为删除)")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(1);
                                String flagState = response.getToggle(2) ? "remove" : "true";
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ")){
                                    residence.getPermissions().setPlayerFlag(player,input,"trusted",flagState,false,false);
                                }else if(response.getDropdown(0) != 0){
                                    residence.getPermissions().setPlayerFlag(player,playerNameList[response.getDropdown(0)],"trusted",flagState,false,false);
                                }
                                sendResTrustedPlayerSettingForm(player,residence);
                            }
                        })
        );

    }
    public static void sendResSettingForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        HashMap<String, ClaimedResidence> residenceList = ResidenceUtils.getResidenceList(player);
        String[] resList = new String[residenceList.size() + 1];
        int i = 1;
        resList[0] = "请选择领地(此项为你所在的领地)";
        for (Map.Entry<String, ClaimedResidence> entry : residenceList.entrySet()) {
            resList[i++] = entry.getKey();
        }
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地管理选择")
                        .dropdown("领地列表",resList)
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                if (response.getDropdown(0) == 0){
                                    Residence res = Residence.getInstance();
                                    ClaimedResidence resClaim = res.getResidenceManager().getByLoc(player);
                                    if (resClaim == null || (!ResidenceUtils.hasManagePermission(player,resClaim) && !player.isOp())) {
                                        sendMainResidenceForm(player);
                                        return;
                                    }
                                    sendResSettingForm(player,resClaim);
                                }else{
                                    sendResSettingForm(player,residenceList.get(resList[response.getDropdown(0)]));
                                }
                            }
                        })
        );
    }


    public static void sendResSetForm(Player player,ClaimedResidence residence){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (residence == null) return;
        if (!ResidenceUtils.hasManagePermission(player,residence) && !player.isOp()) return;
        HashMap<String, FlagPermissions.FlagState> flags = ResidenceUtils.getResidenceFlags(player,residence);
        CustomForm.Builder builder = CustomForm.builder();
        builder.title("§8领地 §l" + residence.getName() +" §r§8权限设置");
        int i = 0;
        HashMap<Integer,String> permissionList = new HashMap<>();
        for (Map.Entry<String, FlagPermissions.FlagState> entry : flags.entrySet()) {
            int flagPermission = ResidenceUtils.flagToInt(entry.getValue());
            permissionList.put(i++,entry.getKey());
            Flags flag = Flags.getFlag(entry.getKey());
            String flagDec = flag != null ? "\n§e" + flag.getDesc() : "";
            builder.stepSlider("\n§a" + entry.getKey()+" §f权限" + flagDec + "\n§f权限状态§8",flagPermission," §6禁用"," §6未设置"," §6启用");
        }
        builder.responseHandler((f, r) -> {
            CustomFormResponse response = f.parseResponse(r);
            if (response.isCorrect()) {
                HashMap<String,FlagPermissions.FlagState> newFlags = new HashMap<>();
                for(int j = 0; j < permissionList.size(); j++){
                    newFlags.put(permissionList.get(j), ResidenceUtils.intToFlag(response.getStepSlide(j)));
                }
                for (Map.Entry<String, FlagPermissions.FlagState> entry : flags.entrySet()){
                    FlagPermissions.FlagState flagState = newFlags.get(entry.getKey());
                    if (flagState == null) ResidenceForm.getInstance().getLogger().severe("Flag state error: null");
                    if (flagState != entry.getValue()) residence.getPermissions().setFlag(entry.getKey(),flagState);
                }
                sendResSettingForm(player,residence);
            }
        });
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(builder);
    }

    public static void sendResPSetForm(Player player,ClaimedResidence residence){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (residence == null) return;
        if (!ResidenceUtils.hasManagePermission(player,residence) && !player.isOp()) return;
        String[] playerNameList = Utils.getOnlinePlayerNameList();
        playerNameList[0] = "请选择玩家";
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() +" §r§8玩家权限设置")
                        .dropdown("在线玩家列表",playerNameList)
                        .input("如玩家不在线或上方表格使用过于麻烦请使用下方输入框", "需要完整玩家名称(包括大小写)")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(1);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ")){
                                    sendResPSetForm(player,input,residence);
                                }else if(response.getDropdown(0) != 0){
                                    sendResPSetForm(player,playerNameList[response.getDropdown(0)],residence);
                                }else{
                                    sendResSettingForm(player,residence);
                                }
                            }
                        })
        );
    }
    public static void sendResPSetForm(Player player,String targetPlayer,ClaimedResidence residence){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (residence == null) return;
        if (!ResidenceUtils.hasManagePermission(player,residence) && !player.isOp()) return;
        HashMap<String, FlagPermissions.FlagState> flags = ResidenceUtils.getResidencePlayerFlags(player,targetPlayer,residence);
        CustomForm.Builder builder = CustomForm.builder();
        builder.title("§8领地 §l" + residence.getName() + " §r§8玩家 §l"  + targetPlayer +" §r§8的权限设置");
        int i = 0;
        HashMap<Integer,String> permissionList = new HashMap<>();
        for (Map.Entry<String, FlagPermissions.FlagState> entry : flags.entrySet()) {
            int flagPermission = ResidenceUtils.flagToInt(entry.getValue());
            permissionList.put(i++,entry.getKey());
            Flags flag = Flags.getFlag(entry.getKey());
            String flagDec = flag != null ? "\n§e" + flag.getDesc() : "";
            builder.stepSlider("\n§a" + entry.getKey()+" §f权限" + flagDec + "\n§f权限状态§8",flagPermission," §6禁用"," §6未设置"," §6启用");
        }
        builder.responseHandler((f, r) -> {
            CustomFormResponse response = f.parseResponse(r);
            if (response.isCorrect()) {
                HashMap<String,FlagPermissions.FlagState> newFlags = new HashMap<>();
                for(int j = 0; j < permissionList.size(); j++){
                    newFlags.put(permissionList.get(j), ResidenceUtils.intToFlag(response.getStepSlide(j)));
                }
                for (Map.Entry<String, FlagPermissions.FlagState> entry : flags.entrySet()){
                    FlagPermissions.FlagState flagState = newFlags.get(entry.getKey());
                    if (flagState == null) ResidenceForm.getInstance().getLogger().severe("Flag state error: null");
                    if (flagState != entry.getValue()) residence.getPermissions().setPlayerFlag(targetPlayer,entry.getKey(),flagState);
                }
                sendResSettingForm(player,residence);
            }
        });
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(builder);
    }
}
