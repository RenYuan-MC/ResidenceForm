package ren.rymc.residenceform.form;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import ltd.rymc.form.residence.forms.MainResidenceForm;
import ltd.rymc.form.residence.forms.setting.ResidenceSettingForm;
import ltd.rymc.form.residence.utils.Facing;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.CustomForm;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.CustomFormResponse;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;

import java.util.UUID;

public class MainForm {

    public static void sendResSensitiveOperationForm(Player player, ClaimedResidence residence) {
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!residence.isOwner(uuid) && !player.isOp()) {
            SimpleForm.Builder builder = SimpleForm.builder().title("领地菜单").content("你没有权限\n\n").button("返回")
                    .responseHandler((f, r) -> {
                        if (f.parseResponse(r).isCorrect()) new ResidenceSettingForm(player,new MainResidenceForm(player,null), residence).send();
                    });
            FloodgateApi.getInstance().getPlayer(uuid).sendForm(builder);
            return;
        }
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("领地管理菜单")
                        .content("当前管理领地: " + residence.getName())
                        .button("重命名领地")
                        .button("扩展/缩小领地")
                        .button("转移领地")
                        .button("删除领地")
                        .button("返回领地管理")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                int id = response.getClickedButtonId();
                                if (id == 0) sendResRenameForm(player, residence);
                                else if (id == 1) sendResExtendAndContractForm(player, residence);
                                else if (id == 2) sendResGiveForm(player, residence);
                                else if (id == 3) sendResRemoveForm(player, residence);
                                else if (id == 4) new ResidenceSettingForm(player,new MainResidenceForm(player,null), residence).send();
                            }
                        })
        );
    }

    public static void sendResRemoveForm(Player player, ClaimedResidence residence) {
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!residence.isOwner(uuid) && !player.isOp()) return;
        FloodgatePlayer fgPlayer = FloodgateApi.getInstance().getPlayer(uuid);
        fgPlayer.sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() + " §r§8删除")
                        .input("请输入领地名以确认", "完整领地名(含大小写)")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(0);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ") && input.trim().equals(residence.getName())) {
                                    fgPlayer.sendForm(
                                            SimpleForm.builder()
                                                    .title("§8领地 §l" + residence.getName() + " §r§8删除")
                                                    .content("你确定?此操作不可撤回!\n\n")
                                                    .button("确定")
                                                    .button("再想想")
                                                    .responseHandler((f1, r1) -> {
                                                        SimpleFormResponse response1 = f1.parseResponse(r1);
                                                        if (response1.isCorrect()) {
                                                            int id = response1.getClickedButtonId();
                                                            if (id == 0)
                                                                Residence.getInstance().getResidenceManager().removeResidence(residence);
                                                            else if (id == 1)
                                                                sendResSensitiveOperationForm(player, residence);
                                                        }
                                                    })
                                    );
                                    return;
                                }
                                sendResSensitiveOperationForm(player, residence);
                            }
                        })
        );
    }

    public static void sendResGiveForm(Player player, ClaimedResidence residence) {
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!residence.isOwner(uuid) && !player.isOp()) return;
        FloodgatePlayer fgPlayer = FloodgateApi.getInstance().getPlayer(uuid);
        fgPlayer.sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() + " §r§8转移")
                        .input("请输入玩家名", "完整玩家名(含大小写)")
                        .input("请再次输入玩家名以确认", "请再次输入玩家名")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(0);
                                String input1 = response.getInput(1);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ") && input1 != null && input.trim().equals(input1.trim())) {
                                    fgPlayer.sendForm(
                                            SimpleForm.builder()
                                                    .title("§8领地 §l" + residence.getName() + " §r§8转移")
                                                    .content("你确定?此操作不可撤回!\n\n")
                                                    .button("确定")
                                                    .button("再想想")
                                                    .responseHandler((f1, r1) -> {
                                                        SimpleFormResponse response1 = f1.parseResponse(r1);
                                                        if (response1.isCorrect()) {
                                                            int id = response1.getClickedButtonId();
                                                            if (id == 0)
                                                                Residence.getInstance().getResidenceManager().giveResidence(player, input.trim(), residence, false, false);
                                                            if (id == 1)
                                                                sendResSensitiveOperationForm(player, residence);
                                                        }
                                                    })
                                    );
                                    return;
                                }
                                sendResSensitiveOperationForm(player, residence);
                            }
                        })
        );
    }

    public static void sendResRenameForm(Player player, ClaimedResidence residence) {
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!residence.isOwner(uuid) && !player.isOp()) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() + " §r§8重命名")
                        .input("请输入新的领地名称", "支持大小写英文,数字,下划线和连字符")
                        .input("请再次输入以确认领地名称", "请再次输入领地名称")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(0);
                                String input1 = response.getInput(1);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ") && input1 != null && input.trim().equals(input1.trim())) {
                                    Residence.getInstance().getResidenceManager().renameResidence(player, residence.getName(), input.trim(), false);
                                }
                                sendResSensitiveOperationForm(player, residence);
                            }
                        })
        );
    }

    public static void sendResExtendAndContractForm(Player player, ClaimedResidence residence) {
        if (residence == null) return;
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        if (!residence.isOwner(uuid) && !player.isOp()) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                CustomForm.builder()
                        .title("§8领地 §l" + residence.getName() + " §r§8扩展/缩小")
                        .dropdown("你当前面对的方向: " + Facing.facing(player.getLocation().getYaw()).getName() + "\n\n扩展/缩小的方向", Facing.facingList())
                        .input("扩展范围", "数字,不填则返回上一级菜单")
                        .toggle("模式(关闭为扩展,开启为缩小)")
                        .responseHandler((f, r) -> {
                            CustomFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                String input = response.getInput(1);
                                if (input != null && !input.trim().equals("") && !input.trim().contains(" ")) {
                                    String command = response.getToggle(2) ? "contract" : "expand";
                                    Location location = player.getLocation();
                                    player.teleport(Facing.translateLocation(location, Facing.facing(response.getDropdown(0))));
                                    Bukkit.dispatchCommand(player, "res " + command + " " + residence.getName() + " " + input.trim());
                                    player.teleport(location);
                                }
                                sendResSensitiveOperationForm(player, residence);
                            }
                        })
        );
    }
}
