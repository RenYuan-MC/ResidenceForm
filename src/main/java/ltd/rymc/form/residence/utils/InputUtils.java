package ltd.rymc.form.residence.utils;


public class InputUtils {

    public static boolean isValid(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isMismatch(String input, String input1){
        return !(
                input != null &&
                !input.trim().isEmpty() &&
                !input.trim().contains(" ") &&
                input1 != null &&
                input.trim().equals(input1.trim())
        );
    }

}
