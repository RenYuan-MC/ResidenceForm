package ltd.rymc.form.residence.utils;

public class StringUtils {
    public static String handleNewLineChar(String text) {
        if (text == null || text.isEmpty()) return text;
        StringBuilder sb = new StringBuilder(text.length());
        boolean escaped = false;
        for (int i = 0, len = text.length(); i < len; i++) {
            char c = text.charAt(i);
            if (escaped) {
                if (c == 'n') {
                    sb.append('\n');
                } else if (c == '^') {
                    sb.append('^');
                } else {
                    sb.append('^');
                    sb.append(c);
                }
                escaped = false;
            } else if (c == '^') {
                escaped = true;
            } else {
                sb.append(c);
            }
        }
        if (escaped) sb.append('^');
        return sb.toString();
    }

    public static String[] handleNewLineChar(String... texts) {
        String[] result = new String[texts.length];
        for (int i = 0; i < texts.length; i++) {
            result[i] = handleNewLineChar(texts[i]);
        }
        return result;
    }
}
