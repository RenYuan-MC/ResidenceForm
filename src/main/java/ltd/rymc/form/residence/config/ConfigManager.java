package ltd.rymc.form.residence.config;

public interface ConfigManager<C> {
    void reloadConfig();

    C getConfigData();

    RawDataHelper getRawDataHelper();

    String getConfigName();

    interface RawDataHelper {
        void setData(String fullKey, Object data);

        void printData();
    }
}
