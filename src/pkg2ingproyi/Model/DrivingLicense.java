package pkg2ingproyi.Model;

import java.sql.Date;

public class DrivingLicense {
    private int permission;
    private Date expeditionDate;
    private Date expiringDate;

    public DrivingLicense (String permission, Date expeditionDate, Date expiringDate) {
        this.expeditionDate = expeditionDate;
        this.expiringDate   = expiringDate;


    }

    public DrivingLicense(int permission, Date expeditionDate, Date expiringDate) {
        this.permission     = permission;
        this.expeditionDate = expeditionDate;
        this.expiringDate   = expiringDate;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public void setPermission(String permission) {
        
    }

    public Date getExpeditionDate() {
        return expeditionDate;
    }

    public void setExpeditionDate(Date expeditionDate) {
        this.expeditionDate = expeditionDate;
    }

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }
}
