package ltd.rymc.form.residence.serialiser;

import ltd.rymc.form.residence.ResidenceForm;
import ltd.rymc.form.residence.config.ConfigManager;
import ltd.rymc.form.residence.config.ResourceConfigManager;
import ltd.rymc.form.residence.language.Language;
import space.arim.dazzleconf.error.BadValueException;
import space.arim.dazzleconf.serialiser.Decomposer;
import space.arim.dazzleconf.serialiser.FlexibleType;
import space.arim.dazzleconf.serialiser.ValueSerialiser;

import java.io.File;
import java.util.List;

public abstract class AbstractLanguageSerialiser implements ValueSerialiser<Language> {

    private static String generateExceptionMessage(String language, List<String> availableLanguages) {
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

    @Override
    public Class<Language> getTargetClass() {
        return Language.class;
    }

    @Override
    public Language deserialise(FlexibleType flexibleType) throws BadValueException {
        String language = getValue(flexibleType);
        ConfigManager<?> configManager = ResourceConfigManager.create(ResidenceForm.getInstance(), language, "lang" + File.separator + language + ".yml", getTargetConfig());
        configManager.reloadConfig();
        return Language.of(configManager, getTargetConfig());
    }

    @Override
    public Object serialise(Language value, Decomposer decomposer) {
        return value.getConfigManager().getConfigName();
    }

    private String getValue(FlexibleType flexibleType) throws BadValueException {
        String language = flexibleType.getString();
        List<String> availableLanguages = getAvailableLanguages();
        if (availableLanguages.contains(language)) return language;

        throw new BadValueException.Builder().key(flexibleType.getAssociatedKey()).message(generateExceptionMessage(language, availableLanguages)).build();
    }

    protected Class<?> getTargetConfig() {
        return null;
    }

    protected List<String> getAvailableLanguages() {
        return null;
    }
}
