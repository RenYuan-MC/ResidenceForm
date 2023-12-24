package ltd.rymc.form.residence;

import co.aikar.commands.PaperCommandManager;
import com.bekvon.bukkit.residence.commands.form;
import ltd.rymc.form.residence.commands.ResidenceFormCommand;
import ltd.rymc.form.residence.metrics.Metrics;
import ltd.rymc.form.residence.utils.hook.ResidenceHook;
import org.bukkit.plugin.java.JavaPlugin;

public final class ResidenceForm extends JavaPlugin {

    private static ResidenceForm instance;
    private static PaperCommandManager commandManager;

    public static ResidenceForm getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        commandManager = new PaperCommandManager(this);

        new Metrics(this, 16703);

        commandManager.registerCommand(new ResidenceFormCommand());
        if (ResidenceHook.hook(new form(), 3300)){
            getLogger().info("Residence hooked!");
        } else {
            getLogger().warning("Residence hook failed!");
        }
    }

    public PaperCommandManager getCommandManager() {
        return commandManager;
    }

}
