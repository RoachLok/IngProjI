package pkg2ingproyi.Model;

public class Diet {
    private String invoiceId;
    private String businessName;
    private double price;

    public Diet (String invoiceId, String businessName, double price) {
        this.invoiceId      = invoiceId;
        this.businessName   = businessName;
        this.price          = price;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
