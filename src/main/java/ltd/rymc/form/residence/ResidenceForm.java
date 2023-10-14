package ltd.rymc.form.residence;

import co.aikar.commands.PaperCommandManager;
import ltd.rymc.form.residence.commands.ResidenceFormCommand;
import org.bukkit.plugin.java.JavaPlugin;
import ltd.rymc.form.residence.metrics.Metrics;

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
    }
}
