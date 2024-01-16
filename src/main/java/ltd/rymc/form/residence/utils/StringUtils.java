package ltd.rymc.form.residence.utils;

public class StringUtils {
    public static String handleNewLineChar(String text){
        return text.replace("^n","\n");
    }

    public static String[] handleNewLineChar(String... texts){
        for (int i = 0, j = texts.length; i < j; i++) {
            texts[i] = handleNewLineChar(texts[i]);
        }
        return texts;
    }
}
