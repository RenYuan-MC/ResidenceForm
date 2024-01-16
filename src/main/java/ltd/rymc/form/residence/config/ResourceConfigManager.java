package ltd.rymc.form.residence.config;

import org.bukkit.plugin.Plugin;
import space.arim.dazzleconf.AuxiliaryKeys;
import space.arim.dazzleconf.ConfigurationFactory;
import space.arim.dazzleconf.ConfigurationOptions;
import space.arim.dazzleconf.error.ConfigFormatSyntaxException;
import space.arim.dazzleconf.error.IllDefinedConfigException;
import space.arim.dazzleconf.error.InvalidConfigException;
import space.arim.dazzleconf.ext.snakeyaml.CommentMode;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlConfigurationFactory;
import space.arim.dazzleconf.ext.snakeyaml.SnakeYamlOptions;
import space.arim.dazzleconf.factory.CommentedWrapper;
import space.arim.dazzleconf.sorter.AnnotationBasedSorter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public final class ResourceConfigManager<C> implements ConfigManager<C> {

    private static Field declaredField = null;
    private static Method fromRawMap = null;
    private static Method toRawMap = null;

    static {
        try {
            declaredField = Class.forName("space.arim.dazzleconf.factory.AbstractConfigurationFactory").getDeclaredField("delegate");
            fromRawMap = Class.forName("space.arim.dazzleconf.factory.ConfigurationFormatFactory").getDeclaredMethod("fromRawMap", Map.class);
            toRawMap = Class.forName("space.arim.dazzleconf.factory.ConfigurationFormatFactory").getDeclaredMethod("toRawMap", Object.class);
            declaredField.setAccessible(true);
            fromRawMap.setAccessible(true);
            toRawMap.setAccessible(true);
        } catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private final ResourceRawDataHelper rawDataHelper = new ResourceRawDataHelper();
    private final ConfigurationFactory<C> factory;
    private final Path configFile;
    private final String resource;
    private final String configName;
    private final Plugin plugin;
    private volatile C configData;

    public ResourceConfigManager(Path configFile, String configName, String resource, Plugin plugin, ConfigurationFactory<C> factory) {
        this.configFile = configFile;
        this.configName = configName;
        this.resource = resource;
        this.factory = factory;
        this.plugin = plugin;
    }

    public static <C> ResourceConfigManager<C> create(Plugin plugin, String path, Class<C> configClass) {
        return create(plugin, path, path, configClass);
    }

    public static <C> ResourceConfigManager<C> create(Plugin plugin, String path, Class<C> configClass, ConfigurationOptions options) {
        return create(plugin, path, path, configClass, options);
    }

    public static <C> ResourceConfigManager<C> create(Plugin plugin, String configName, String path, Class<C> configClass) {
        return create(plugin, configName, path, configClass,
                new ConfigurationOptions.Builder().sorter(new AnnotationBasedSorter()).build()
        );
    }

    public static <C> ResourceConfigManager<C> create(Plugin plugin, String configName, String path, Class<C> configClass, ConfigurationOptions options) {
        Path configFile = plugin.getDataFolder().toPath().resolve(path);
        // ShakeYaml example
        SnakeYamlOptions yamlOptions = new SnakeYamlOptions.Builder().commentMode(CommentMode.alternativeWriter("%s")).build();
        ConfigurationFactory<C> configFactory = SnakeYamlConfigurationFactory.create(
                configClass,
                options,
                yamlOptions
        );
        return new ResourceConfigManager<>(configFile, configName, path.replace("\\", "/"), plugin, configFactory);
    }


    private InputStream obtainDefaultResource() {
        return getClass().getResourceAsStream("/" + resource);
    }

    private C loadDefaultsFromResource() {
        try (InputStream resourceStream = obtainDefaultResource()) {
            return factory.load(resourceStream);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        } catch (InvalidConfigException ex) {
            throw new IllDefinedConfigException("Default config resource is broken", ex);
        }
    }

    private C reloadConfigData() throws IOException, InvalidConfigException {
        // Create parent directory if it does not exist
        Path parentDir = configFile.toAbsolutePath().getParent();
        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }

        C defaults = loadDefaultsFromResource();

        if (!Files.exists(configFile)) {

            // Copy default config data
            try (FileChannel fileChannel = FileChannel.open(configFile, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE); InputStream defaultResource = obtainDefaultResource(); ReadableByteChannel defaultResourceChannel = Channels.newChannel(defaultResource)) {

                fileChannel.transferFrom(defaultResourceChannel, 0, Long.MAX_VALUE);
            }
            return defaults;
        }

        C loadedData;
        try (FileChannel fileChannel = FileChannel.open(configFile, StandardOpenOption.READ)) {
            loadedData = factory.load(fileChannel, defaults);
        }
        if (loadedData instanceof AuxiliaryKeys) {
            // Update config with latest keys
            try (FileChannel fileChannel = FileChannel.open(configFile, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                factory.write(loadedData, fileChannel);
            }
        }
        return loadedData;
    }

    public void reloadConfig() {
        try {
            configData = reloadConfigData();
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);

        } catch (ConfigFormatSyntaxException ex) {
            configData = loadDefaultsFromResource();
            plugin.getLogger().severe("The yaml syntax in your configuration is invalid. " + "Query your YAML syntax with a tool such as https://yaml-online-parser.appspot.com/");
            ex.printStackTrace();
        } catch (InvalidConfigException ex) {
            configData = loadDefaultsFromResource();
            plugin.getLogger().severe("One of the values in your configuration is not valid. " + "Query to make sure you have specified the right data types.");
            ex.printStackTrace();
        }
    }

    public C getConfigData() {
        C configData = this.configData;
        if (configData == null) {
            throw new IllegalStateException("Configuration has not been loaded yet");
        }
        return configData;
    }

    public String getConfigName(){
        return configName;
    }

    public RawDataHelper getRawDataHelper() {
        return rawDataHelper;
    }

    public class ResourceRawDataHelper implements RawDataHelper {
        public void setData(String fullKey, Object data) {
            Map<String, Object> rawMap = toRawMap();
            removeAllCommentedWrapper(rawMap);
            Queue<String> queue = new ConcurrentLinkedQueue<>(Arrays.asList(fullKey.split("\\.")));
            setData(rawMap, queue, data);
            C newData = fromRawMap(rawMap);

            try (FileChannel fileChannel = FileChannel.open(configFile, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                factory.write(newData, fileChannel);
            } catch (IOException ex) {
                throw new UncheckedIOException(ex);
            }
            reloadConfig();
        }

        public void printData() {
            printData(toRawMap(), null);
        }

        private void removeAllCommentedWrapper(Map<String, Object> map) {

            HashMap<String,Object> tempMap = new HashMap<>();

            for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();

                if (value instanceof CommentedWrapper) {
                    CommentedWrapper wrapper = (CommentedWrapper) value;
                    tempMap.put(key, wrapper.getValue());
                    iterator.remove();
                }

                if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> subMap = (Map<String, Object>) value;
                    removeAllCommentedWrapper(subMap);
                }
            }

            map.putAll(tempMap);

        }

        private void setData(Map<String, Object> rawMap, Queue<String> queue, Object data) {
            String key = queue.remove();

            if (queue.size() == 0) {
                rawMap.remove(key);
                rawMap.put(key, data);
                return;
            }

            Object value = rawMap.get(key);
            if (value instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked") Map<String, Object> subMap = (Map<String, Object>) value;
                setData(subMap, queue, data);
            } else {
                throw new IllegalArgumentException("Wrong key");
            }
        }

        private void printData(Map<String, Object> map, String parentKey) {
            Logger logger = plugin.getLogger();

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = parentKey == null ? entry.getKey() : parentKey + "." + entry.getKey();
                Object value = entry.getValue();

                logger.info("Key: " + key + " , Value:" + value + " , Type: " + value.getClass().getName());

                // Query subsection
                if (!(value instanceof Map<?, ?>)) continue;

                // Print subsection
                @SuppressWarnings("unchecked")
                Map<String, Object> subMap = (Map<String, Object>) value;
                printData(subMap, key);

            }
        }

        private Map<String, Object> toRawMap() {
            if (toRawMap == null || declaredField == null) {
                throw new IllegalStateException("Reflection not loaded properly!");
            }

            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> rawMap = (Map<String, Object>) toRawMap.invoke(declaredField.get(factory), getConfigData());
                return rawMap;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("An error occurred while converting ConfigData to RawData", e);
            }

        }

        private C fromRawMap(Map<String, Object> rawMap) {
            if (fromRawMap == null || declaredField == null) {
                throw new IllegalStateException("Reflection not loaded properly!");
            }

            try {
                @SuppressWarnings("unchecked")
                C configData = (C) fromRawMap.invoke(declaredField.get(factory), rawMap);
                return configData;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("An error occurred while converting RawData to ConfigData", e);
            }

        }
    }
}
