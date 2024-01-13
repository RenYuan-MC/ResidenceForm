package ltd.rymc.form.residence;

import co.aikar.commands.PaperCommandManager;
import com.bekvon.bukkit.residence.commands.form;
import ltd.rymc.form.residence.commands.ResidenceFormCommand;
import ltd.rymc.form.residence.config.ConfigManager;
import ltd.rymc.form.residence.config.NormalConfigManager;
import ltd.rymc.form.residence.configs.Config;
import ltd.rymc.form.residence.configs.Language;
import ltd.rymc.form.residence.metrics.Metrics;
import ltd.rymc.form.residence.utils.LanguageUtils;
import ltd.rymc.form.residence.utils.hook.ResidenceHook;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class ResidenceForm extends JavaPlugin {

    private static ResidenceForm instance;
    private static PaperCommandManager commandManager;
    private static NormalConfigManager<Config> mainConfigManager;

    public static ResidenceForm getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        commandManager = new PaperCommandManager(this);

        loadConfig();

        Config config = mainConfigManager.getConfigData();

        loadMetrics(config.metrics());
        loadResidenceHook(config.hookResidence());

        commandManager.registerCommand(new ResidenceFormCommand());
    }

    public static PaperCommandManager getCommandManager() {
        return commandManager;
    }


    public static ConfigManager<Config> getMainConfigManager() {
        return mainConfigManager;
    }

    public static Config getMainConfig() {
        return mainConfigManager.getConfigData();
    }

    public static Language getLanguage(){
        return mainConfigManager.getConfigData().language().getConfigData();
    }

    private void loadConfig() {
        boolean firstLoad = !new File(getDataFolder(), "config.yml").exists();

        mainConfigManager = NormalConfigManager.create(this, "config", "config.yml", Config.class);
        mainConfigManager.reloadConfig();

        if (firstLoad) mainConfigManager.getRawDataHelper().setData("language", LanguageUtils.translateLanguage(LanguageUtils.getSystemLanguage()));

        ConfigManager<Language> languageConfigManager = mainConfigManager.getConfigData().language();
        getLogger().info("Language: " + languageConfigManager.getConfigName());
    }

    private void loadMetrics(boolean load) {
        if (!load) return;
        new Metrics(this, 16703);
    }

    private void loadResidenceHook(boolean load) {
        if (!load) return;
        if (ResidenceHook.hook(new form(), 3300)) {
            getLogger().info("Residence hooked!");
        } else {
            getLogger().warning("Residence hook failed!");
        }
    }

}

