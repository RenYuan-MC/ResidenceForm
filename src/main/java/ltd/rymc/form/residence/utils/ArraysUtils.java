package ltd.rymc.form.residence.utils;

public class ArraysUtils {
    public static String[] rotate(String[] original, int num){
        String[] newList = new String[original.length + num];
        System.arraycopy(original, 0, newList, num, original.length);
        return newList;
    }
}
