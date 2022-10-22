package ren.rymc.residenceform;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import ren.rymc.residenceform.form.MainForm;
import ren.rymc.residenceform.metrics.Metrics;
import ren.rymc.residenceform.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ResidenceForm extends JavaPlugin {

    private static ResidenceForm instance;

    @Override
    public void onEnable() {
        instance = this;
        loadCommands();
        Metrics metrics = new Metrics(this, 16703);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id", () -> "My value"));
    }

    private void loadCommands(){
        PluginCommand rform = getInstance().getCommand("rform");
        if (rform == null){
            getInstance().getLogger().severe("插件加载命令时发送错误,你是否加载了完整的插件?");
            getInstance().getLogger().severe("The plugin sent an error when loading commands. Did you use the complete plugin?");
            Bukkit.getPluginManager().disablePlugin(getInstance());
            return;
        }
        rform.setExecutor(getInstance());
        rform.setTabCompleter(getInstance());
    }

    public static ResidenceForm getInstance(){
        return instance;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) return true;
        Utils.toLowerCase(args);
        if(args[0].equals("ui")){
            if (!(sender instanceof Player)) return true;
            MainForm.sendMainResidenceForm((Player) sender);
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Utils.toLowerCase(args);
        if (args.length == 1) return new ArrayList<>(Collections.singletonList("ui"));
        return new ArrayList<>();
    }
}
