package ltd.rymc.form.residence.language;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.config.ConfigManager;
import space.arim.dazzleconf.annote.ConfKey;
import space.arim.dazzleconf.annote.SubSection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Language {
    private final ConfigManager<?> manager;
    private final Class<?> configClass;
    private final Map<String, String> cache = new HashMap<>();

    private Language(ConfigManager<?> configManager, Class<?> configClass){
        this.manager = configManager;
        this.configClass = configClass;
    }

    public static Language of(ConfigManager<?> configManager, Class<?> configClass){
        configClass.cast(configManager.getConfigData());
        Language language = new Language(configManager, configClass);
        language.reloadLanguage();
        return language;
    }

    public ConfigManager<?> getConfigManager(){
        return manager;
    }

    public void reloadLanguage(){
        manager.reloadConfig();
        refreshCache();
    }

    public void refreshCache(){
        cache.clear();
        Object data = manager.getConfigData();
        cache.putAll(getCacheFromData(data,"", configClass));
    }

    private Map<String, String> getCacheFromData(Object data, String parentKey, Class<?> configClass){
        if (data == null) {
            throw new IllegalStateException("Config data is null");
        }

        Method[] methods = configClass.getDeclaredMethods();
        Map<String, String> cache = new HashMap<>();

        for (Method method : methods){
            ConfKey confKey = method.getAnnotation(ConfKey.class);
            if (confKey == null) {
                ResidenceForm.getInstance().getLogger().log(Level.WARNING, "Method " + method.getName() + " in " + configClass.getName() + " has no @ConfKey annotation");
                continue;
            }
            String key = (parentKey.isEmpty() ? "" : parentKey + ".") + confKey.value();

            if (method.getAnnotation(SubSection.class) != null){
                try {
                    Map<String, String> subCache = getCacheFromData(method.invoke(data), key, method.getReturnType());
                    cache.putAll(subCache);
                } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                    throw new RuntimeException(e);
                }
                continue;
            }

            boolean isReturnString = method.getReturnType().equals(String.class);
            if (!isReturnString){
                ResidenceForm.getInstance().getLogger().log(Level.WARNING, "Method " + method.getName() + " in " + configClass.getName() + " does not return String");
                continue;
            }

            String text;
            try {
                text = (String) method.invoke(data);
            } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
                throw new RuntimeException(e);
            }

            cache.put(key, text);
        }

        return cache;
    }

    public String text(String key){
        String text = cache.get(key);
        return text == null ? key : text;
    }

    public Section section(String key){
        return new Section(key);
    }

    public class Section {

        private final String sectionKey;

        private Section(String sectionKey){
            this.sectionKey = sectionKey;
        }

        public String text(String key){
             return Language.this.text(sectionKey + "." + key);
        }

        public Section section(String key){
            return Language.this.section(sectionKey + "." + key);
        }
    }

}
