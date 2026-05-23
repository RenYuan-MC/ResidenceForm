package ltd.rymc.form.residence.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputUtilsTests {

    @Test
    void isValid_normalInput() {
        assertTrue(InputUtils.isValid("hello"));
    }

    @Test
    void isValid_emptyString() {
        assertFalse(InputUtils.isValid(""));
    }

    @Test
    void isValid_whitespaceOnly() {
        assertFalse(InputUtils.isValid("   "));
    }

    @Test
    void isValid_null() {
        assertFalse(InputUtils.isValid(null));
    }

    @Test
    void isValid_whitespaceWithContent() {
        assertTrue(InputUtils.isValid("  hello  "));
    }

    @Test
    void isMismatch_matchingInputs() {
        assertFalse(InputUtils.isMismatch("hello", "hello"));
    }

    @Test
    void isMismatch_differentInputs() {
        assertTrue(InputUtils.isMismatch("hello", "world"));
    }

    @Test
    void isMismatch_nullFirst() {
        assertTrue(InputUtils.isMismatch(null, "hello"));
    }

    @Test
    void isMismatch_nullSecond() {
        assertTrue(InputUtils.isMismatch("hello", null));
    }

    @Test
    void isMismatch_bothNull() {
        assertTrue(InputUtils.isMismatch(null, null));
    }

    @Test
    void isMismatch_containsSpace() {
        assertTrue(InputUtils.isMismatch("hello world", "hello world"));
    }

    @Test
    void isMismatch_emptyString() {
        assertTrue(InputUtils.isMismatch("", ""));
    }

    @Test
    void isMismatch_whitespaceOnly() {
        assertTrue(InputUtils.isMismatch("   ", "   "));
    }

    @Test
    void isMismatch_trimmedMatchWithDifferentWhitespace() {
        assertFalse(InputUtils.isMismatch("  hello  ", "hello"));
    }
}
