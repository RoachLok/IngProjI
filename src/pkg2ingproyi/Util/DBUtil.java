package pkg2ingproyi.Util;

public class DBUtil {

    public static String fixDateFormat(String dbDateStr) {
        if (dbDateStr == null)
            return null;
        dbDateStr = dbDateStr.replace('-', '/');
        dbDateStr = dbDateStr.replace('T', ' ');
        dbDateStr = dbDateStr.substring(0, 19);
        return dbDateStr;
    }

}
