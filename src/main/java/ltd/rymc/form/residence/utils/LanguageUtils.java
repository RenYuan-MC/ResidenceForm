package ltd.rymc.form.residence.utils;

import java.util.Locale;

public class LanguageUtils {

    public static String getSystemLanguage(){
        return Locale.getDefault().getLanguage();
    }

    public static String translateLanguage(String locale){
        return locale.equals("zh") ? "zh_CN" : "en_US";
    }
}
