package ren.rymc.residenceform;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ren.rymc.residenceform.form.MainForm;
import ren.rymc.residenceform.metrics.Metrics;

public final class ResidenceForm extends JavaPlugin {

    private static ResidenceForm instance;

    public static ResidenceForm getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        loadCommands();
        Metrics metrics = new Metrics(this, 16703);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    private void loadCommands() {
        PluginCommand rform = getInstance().getCommand("rform");
        if (rform == null) {
            getInstance().getLogger().severe("插件加载命令时发送错误,你是否加载了完整的插件?");
            getInstance().getLogger().severe("The plugin sent an error when loading commands. Did you use the complete plugin?");
            Bukkit.getPluginManager().disablePlugin(getInstance());
            return;
        }
        rform.setExecutor(getInstance());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) MainForm.sendMainResidenceForm((Player) sender);
        return true;
    }
}
