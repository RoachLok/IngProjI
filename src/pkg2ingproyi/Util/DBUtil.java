package pkg2ingproyi.Util;

public class DBUtil {

    public static String fixDateFormat(String dbDateStr) {
        dbDateStr = dbDateStr.replace('-', '/');
        dbDateStr = dbDateStr.replace('T', ' ');
        dbDateStr = dbDateStr.substring(0, 19);
        return dbDateStr;
    }

}
