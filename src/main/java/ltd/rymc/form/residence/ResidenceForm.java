package ltd.rymc.form.residence;

import co.aikar.commands.PaperCommandManager;
import com.bekvon.bukkit.residence.CommandFiller;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.commands.form;
import com.bekvon.bukkit.residence.containers.CommandStatus;
import ltd.rymc.form.residence.commands.ResidenceFormCommand;
import ltd.rymc.form.residence.metrics.Metrics;
import net.Zrips.CMILib.FileHandler.ConfigReader;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Map;

public final class ResidenceForm extends JavaPlugin {

    private static ResidenceForm instance;

    public static ResidenceForm getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new ResidenceFormCommand());
        new Metrics(this, 16703);
        try {
            hookResidence();
            getLogger().info("Residence hooked!");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            getLogger().warning("Residence hook failed! # \n " + e.getMessage());
        }
    }

    private void hookResidence() throws NoSuchFieldException, IllegalAccessException {
        Residence plugin = Residence.getInstance();
        CommandFiller commandFiller = plugin.getCommandFiller();
        Field commandListField = commandFiller.getClass().getDeclaredField("CommandList");
        commandListField.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, CommandStatus> commandList =  (Map<String, CommandStatus>) commandListField.get(commandFiller);
        commandList.put("form", new CommandStatus(true, 3300, "", new String[0]));
        ConfigReader config = plugin.getLocaleManager().getLocaleConfig();
        config.setFullPath(plugin.getLocaleManager().path + "form.");
        new form().getLocale();
        config.resetP();
        config.save();
        plugin.parseHelpEntries();
    }

}
