package com.bekvon.bukkit.residence.commands;

import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandAnnotation;
import com.bekvon.bukkit.residence.containers.cmd;
import ltd.rymc.form.residence.forms.MainResidenceForm;
import net.Zrips.CMILib.FileHandler.ConfigReader;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class form implements cmd {

    private static final Map<String,String> infoLangMap = new HashMap<>();
    private static final Map<String,String> desctiptionLangMap = new HashMap<>();
    static {
        infoLangMap.put("Chinese","&e用法: &6/res form");
        infoLangMap.put("ChineseTW","&e用法: &6/res form");
        infoLangMap.put("Czech","&ePoužití: &6/res form");
        infoLangMap.put("French","&eUsage: &6/res form");
        infoLangMap.put("Russian","&eПишите: &6/res form");
        infoLangMap.put("Spanish","&eUso: &6/res form");
        desctiptionLangMap.put("Chinese","打开领地表单(为基岩版玩家)");
        desctiptionLangMap.put("ChineseTW","打開領地表單(為基岩版玩家)");
    }
    @Override
    @CommandAnnotation(priority = 3300)
    public Boolean perform(Residence residence, CommandSender sender, String[] strings, boolean b) {
        if (!(sender instanceof Player)) {
            return false;
        }

        new MainResidenceForm((Player) sender, null).send();
        return true;
    }

    @Override
    public void getLocale() {
        ConfigReader config = Residence.getInstance().getLocaleManager().getLocaleConfig();
        config.get("Description", getFormDescription());
        config.get("Info", Collections.singletonList(getFormInfo()));
        LocaleManager.addTabCompleteMain(this, "[residence]");
    }

    public String getFormDescription(){
        String language = Residence.getInstance().getConfigManager().getLanguage();
        String text = desctiptionLangMap.get(language);
        return text == null ? "Open residence form (For bedrock player)" : text;
    }

    public String getFormInfo(){
        String language = Residence.getInstance().getConfigManager().getLanguage();
        String text = infoLangMap.get(language);
        return text == null ? "&eUsage: &6/res form" : text;

    }
}
