package MyUtils;

public enum StringUtils {
    INSTANCE;
    public static boolean HasPrefix(String str, String prefix, boolean ignoreCase) {
        if(str.length()< prefix.length())
            return false;
        return str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }
    public static boolean HasSuffix(String str, String suffix, boolean ignoreCase){
        if(str.length()< suffix.length())
            return false;
        return str.regionMatches(ignoreCase, str.length() - suffix.length(), suffix, 0, suffix.length());
    }
}
