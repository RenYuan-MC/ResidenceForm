package ltd.rymc.form.residence.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringUtilsTests {

    @Test
    void handleNewLineChar_singleReplacement() {
        assertEquals("line1\nline2", StringUtils.handleNewLineChar("line1^nline2"));
    }

    @Test
    void handleNewLineChar_multipleReplacements() {
        assertEquals("a\nb\nc", StringUtils.handleNewLineChar("a^nb^nc"));
    }

    @Test
    void handleNewLineChar_noNewLineChar() {
        assertEquals("plain text", StringUtils.handleNewLineChar("plain text"));
    }

    @Test
    void handleNewLineChar_consecutiveNewLine() {
        assertEquals("\n\n", StringUtils.handleNewLineChar("^n^n"));
    }

    @Test
    void handleNewLineChar_null() {
        assertNull(StringUtils.handleNewLineChar((String) null));
    }

    @Test
    void handleNewLineChar_empty() {
        assertEquals("", StringUtils.handleNewLineChar(""));
    }

    @Test
    void handleNewLineChar_escapedCaret_doubleCaret() {
        assertEquals("^", StringUtils.handleNewLineChar("^^"));
    }

    @Test
    void handleNewLineChar_escapedCaret_beforeNewline() {
        assertEquals("^\n", StringUtils.handleNewLineChar("^^^n"));
    }

    @Test
    void handleNewLineChar_trailingCaret() {
        assertEquals("text^", StringUtils.handleNewLineChar("text^"));
    }

    @Test
    void handleNewLineChar_unknownEscape() {
        assertEquals("x^z", StringUtils.handleNewLineChar("x^z"));
    }

    @Test
    void handleNewLineChar_caretOnly() {
        assertEquals("^", StringUtils.handleNewLineChar("^"));
    }

    @Test
    void handleNewLineChar_arrayOfStrings() {
        String[] input = {"a^nb", "c^nd", "plain"};
        String[] expected = {"a\nb", "c\nd", "plain"};
        assertArrayEquals(expected, StringUtils.handleNewLineChar(input));
    }

    @Test
    void handleNewLineChar_arrayDoesNotMutateInput() {
        String[] input = {"a^nb", "c^nd"};
        String[] snapshot = {"a^nb", "c^nd"};
        StringUtils.handleNewLineChar(input);
        assertArrayEquals(snapshot, input);
    }

    @Test
    void handleNewLineChar_emptyArray() {
        String[] input = {};
        assertArrayEquals(new String[0], StringUtils.handleNewLineChar(input));
    }
}
