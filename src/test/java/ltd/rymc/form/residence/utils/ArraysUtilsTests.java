package ltd.rymc.form.residence.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ArraysUtilsTests {

    @Test
    void rotate_shiftByOne() {
        String[] input = {"a", "b", "c"};
        String[] expected = {null, "a", "b", "c"};
        assertArrayEquals(expected, ArraysUtils.rotate(input, 1));
    }

    @Test
    void rotate_shiftByTwo() {
        String[] input = {"a", "b"};
        String[] expected = {null, null, "a", "b"};
        assertArrayEquals(expected, ArraysUtils.rotate(input, 2));
    }

    @Test
    void rotate_zeroShift() {
        String[] input = {"x", "y", "z"};
        String[] expected = {"x", "y", "z"};
        assertArrayEquals(expected, ArraysUtils.rotate(input, 0));
    }

    @Test
    void rotate_emptyArray() {
        String[] input = {};
        String[] expected = {null, null};
        assertArrayEquals(expected, ArraysUtils.rotate(input, 2));
    }
}
