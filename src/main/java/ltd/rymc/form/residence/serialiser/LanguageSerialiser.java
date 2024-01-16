package ltd.rymc.form.residence.serialiser;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.config.ConfigManager;
import ltd.rymc.form.residence.config.ResourceConfigManager;
import ltd.rymc.form.residence.configs.Language;
import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

import java.util.Arrays;
import java.util.List;

public class LanguageSerialiser implements ValueSerialiser<ConfigManager<Language>> {

    private static final List<String> availableLanguages = Arrays.asList("zh_CN", "en_US");


    @Override
    @SuppressWarnings("unchecked")
    public Class<ConfigManager<Language>> getTargetClass() {
        return (Class<ConfigManager<Language>>) (Class<?>) ConfigManager.class;
    }

    @Override
    public ConfigManager<Language> deserialise(FlexibleType flexibleType) throws BadValueException {
        String language = getValue(flexibleType);
        ResourceConfigManager<Language> configManager = ResourceConfigManager.create(ResidenceForm.getInstance(), language, "lang\\" + language + ".yml", Language.class);
        configManager.reloadConfig();
        return configManager;
    }

    @Override
    public Object serialise(ConfigManager<Language> value, Decomposer decomposer) {
        return value.getConfigName();
    }

    private static String getValue(FlexibleType flexibleType) throws BadValueException {
        String language = flexibleType.getString();
        if (availableLanguages.contains(language)) return language;

        throw new BadValueException
                .Builder()
                .key(flexibleType.getAssociatedKey())
                .message(generateExceptionMessage(language))
                .build();
    }

    private static String generateExceptionMessage(String language){
        StringBuilder message = new StringBuilder();
        message.append("The wrong language was entered. The value should be a name of an available language");
        message.append(", ");
        message.append("but it was ");
        message.append(language);
        message.append('\n');
        message.append("Some examples of valid input: ");
        for (int i = 0, j = Math.min(availableLanguages.size(), 3); i < j; i++) {
            message.append(availableLanguages.get(i));
            message.append(", ");
        }
        return message.toString();
    }
}
