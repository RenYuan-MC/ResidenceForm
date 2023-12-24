package ltd.rymc.form.residence.utils.hook;

import com.bekvon.bukkit.residence.CommandFiller;
import com.bekvon.bukkit.residence.LocaleManager;
import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.CommandStatus;
import com.bekvon.bukkit.residence.containers.cmd;
import ltd.rymc.form.residence.ResidenceForm;
import net.Zrips.CMILib.FileHandler.ConfigReader;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.Map;

public class ResidenceHook {

    private static final Residence residence = Residence.getInstance();
    private static final LocaleManager locale = residence.getLocaleManager();
    private static boolean state;
    private static Map<String, CommandStatus> commands;

    static {
        try {
            // Since Residence only checks the commands within its package
            // It is necessary to use reflection to modify and add commands
            CommandFiller commandFiller = residence.getCommandFiller();
            Field commandListField = commandFiller.getClass().getDeclaredField("CommandList");
            commandListField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, CommandStatus> commands0 = (Map<String, CommandStatus>) commandListField.get(commandFiller);
            commands = commands0;
            state = true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            state = false;
            ResidenceForm.getInstance().getLogger().warning("An error occurred while using reflection for Residence, unable to hook Residence \n" + e.getMessage());
        }
    }

    public static boolean hook(cmd command, int priority){
        if (!state) return false;

        if (!checkCommand(command)){
            Bukkit.getLogger().warning("Residence hook failed: command package name should start with com.bekvon.bukkit.residence.commands");
            return false;
        }

        String name = command.getClass().getSimpleName();

        hookCommandList(name, priority);
        hookLanguage(command, name);
        hookResidenceHelp();

        return true;
    }

    private static boolean checkCommand(cmd command) {
        return command.getClass().getName().startsWith("com.bekvon.bukkit.residence.commands");
    }

    private static void hookCommandList(String name, int priority){
        commands.put(name, new CommandStatus(true, priority, "", new String[0]));
    }

    private static void hookLanguage(cmd command, String name) {
        ConfigReader config = locale.getLocaleConfig();

        // Change language path
        config.setFullPath(locale.path + name + ".");

        // Set locale
        command.getLocale();

        // Reset path & save
        config.resetP();
        config.save();
    }

    private static void hookResidenceHelp(){
        // Now we only implement hook by reloading this part
        residence.parseHelpEntries();
    }
}
