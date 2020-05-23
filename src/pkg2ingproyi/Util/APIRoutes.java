package pkg2ingproyi.Util;

import pkg2ingproyi.Main;

public class APIRoutes {
    private final static String baseUrl     = "https://jj82bm9wo382qd8-sj3.adb.eu-amsterdam-1.oraclecloudapps.com/ords/admin/";
    private final static String dptQuery    = "/?q={%22department_id%22:%22"+ Main.appUser.getDptId() +"%22}";

    public final static String CHAUFFEUR    = baseUrl + "chauffeur" + dptQuery;
    public final static String VEHICLES     = baseUrl + "vehicle"   + dptQuery;
    public final static String SERVICES     = baseUrl + "service"   + dptQuery;
    public final static String ACCOUNTANT   = baseUrl + "accountant"+ dptQuery;
}
