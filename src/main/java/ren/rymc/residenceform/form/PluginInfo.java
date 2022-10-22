package ren.rymc.residenceform.form;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;

import java.util.UUID;

public class PluginInfo {
    public static void sendPluginInfoForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("插件信息")
                        .content("§7领地基岩版兼容 ResidenceForm\n作者: RENaa_FD\n版本: 测试版本 v0.1.9\n官网: https//rymc.ren/\nQQ群: 1029946156\n\n")
                        .button("API信息")
                        .button("BUG报告")
                        .button("开源协议")
                        .button("返回主菜单")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                int id = response.getClickedButtonId();
                                if (id == 0) sendAPIInfoForm(player);
                                if (id == 1) sendBugReportForm(player);
                                if (id == 2) sendLicenseForm(player);
                                if (id == 3) MainForm.sendMainResidenceForm(player);
                            }
                        })
        );
    }

    public static void sendAPIInfoForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        Plugin residencePlugin = Bukkit.getPluginManager().getPlugin("Residence");
        String residenceVersion = residencePlugin == null ? "未知" : residencePlugin.getDescription().getVersion();
        Plugin floodgatePlugin = Bukkit.getPluginManager().getPlugin("floodgate");
        String floodgateVersion = floodgatePlugin == null ? "未知" : floodgatePlugin.getDescription().getVersion();
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("插件API信息")
                        .content("§7ResidenceForm开发时使用的插件版本:\nFloodgate: 2.0-SNAPSHOT\nResidence: 5.0.3.0\n\n服务器插件版本:\nFloodgate: "+ floodgateVersion + "\nResidence: " + residenceVersion + "\n\n")
                        .button("返回")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                sendPluginInfoForm(player);
                            }
                        })
        );
    }

    public static void sendBugReportForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("BUG报告")
                        .content("§7如你在使用本插件时发现相关BUG请前往插件开源仓库或QQ群报告\n\n地址: https://github.com/RenYuan-MC/ResidenceForm\nQQ群: 1029946156\n\n")
                        .button("返回")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                sendPluginInfoForm(player);
                            }
                        })
        );
    }

    public static void sendLicenseForm(Player player){
        UUID uuid = player.getUniqueId();
        if (!FloodgateApi.getInstance().isFloodgatePlayer(uuid)) return;
        FloodgateApi.getInstance().getPlayer(uuid).sendForm(
                SimpleForm.builder()
                        .title("ResidenceForm 开源协议")
                        .content("§7" +
                                "MIT License\n" +
                                "\n" +
                                "Copyright (c) 2022 RenYuan-MC\n" +
                                "\n" +
                                "Permission is hereby granted, free of charge, to any person obtaining a copy " +
                                "of this software and associated documentation files (the \"Software\"), to deal " +
                                "in the Software without restriction, including without limitation the rights " +
                                "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell " +
                                "copies of the Software, and to permit persons to whom the Software is " +
                                "furnished to do so, subject to the following conditions:\n" +
                                "\n" +
                                "The above copyright notice and this permission notice shall be included in all " +
                                "copies or substantial portions of the Software.\n" +
                                "\n" +
                                "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR " +
                                "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, " +
                                "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE " +
                                "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER " +
                                "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, " +
                                "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE " +
                                "SOFTWARE.\n")
                        .button("返回")
                        .responseHandler((f, r) -> {
                            SimpleFormResponse response = f.parseResponse(r);
                            if (response.isCorrect()) {
                                sendPluginInfoForm(player);
                            }
                        })
        );
    }
}
