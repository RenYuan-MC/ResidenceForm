package ltd.rymc.form.residence.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LanguageUtilsTests {

    @Test
    void getSystemLanguage_returnsNonNull() {
        assertNotNull(LanguageUtils.getSystemLanguage());
    }

    @Test
    void translateLanguage_zh() {
        assertEquals("zh_CN", LanguageUtils.translateLanguage("zh"));
    }

    @Test
    void translateLanguage_en() {
        assertEquals("en_US", LanguageUtils.translateLanguage("en"));
    }

    @Test
    void translateLanguage_unknown() {
        assertEquals("en_US", LanguageUtils.translateLanguage("fr"));
    }

    @Test
    void translateLanguage_empty() {
        assertEquals("en_US", LanguageUtils.translateLanguage(""));
    }

    @Test
    void translateLanguage_nullThrowsNPE() {
        assertThrows(NullPointerException.class, () -> LanguageUtils.translateLanguage(null));
    }
}
