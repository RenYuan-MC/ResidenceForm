package ltd.rymc.form.residence.language;

import ltd.rymc.form.residence.serialiser.AbstractLanguageSerialiser;

import java.util.Arrays;
import java.util.List;

public class LanguageSerialiser extends AbstractLanguageSerialiser {

    @Override
    protected Class<?> getTargetConfig(){
        return LanguageConfig.class;
    }

    @Override
    protected List<String> getAvailableLanguages() {
        return Arrays.asList("zh_TW", "zh_CN", "en_US");
    }
}
