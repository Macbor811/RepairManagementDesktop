package pl.polsl.repairmanagementdesktop.utils;

public class StringUtils {

    public static String trim(String str, int maxLength){
        if(str != null && str.length() > maxLength){
            return str.substring(0, maxLength) + "...";
        } else {
            return str;
        }
    }
}
