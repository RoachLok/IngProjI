package pkg2ingproyi.Util;

import pkg2ingproyi.Main;

public class APIRoutes {
    //Main objects Queries
    private final static String baseUrl     = "https://jj82bm9wo382qd8-sj3.adb.eu-amsterdam-1.oraclecloudapps.com/ords/admin/";
    private final static String dptQuery    = "/?q={%22department_id%22:%22"+ Main.appUser.getDptId() +"%22}";

    public final static String CHAUFFEUR    = baseUrl + "chauffeur" + dptQuery;
    public final static String DIET         = baseUrl + "diet"      + dptQuery;
    public final static String VEHICLES     = baseUrl + "vehicle"   + dptQuery;
    public final static String ALL_SERVICES = baseUrl + "service"   + dptQuery;
    public final static String ACCOUNTANT   = baseUrl + "accountant"+ dptQuery;
    public final static String CLIENT       = baseUrl + "client"    + dptQuery;
    public final static String INVOICE      = baseUrl + "invoice"   + dptQuery;


    //Module Queries
    public final static String RESERVES     = baseUrl + "demo/reserve" ; //Supports POST requests.
    public final static String SERVICES     = baseUrl + "demo/service" ;
    public final static String MONTAJES     = baseUrl + "demo/mounted" ;
}
