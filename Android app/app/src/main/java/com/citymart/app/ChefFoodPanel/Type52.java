package com.citymart.app.ChefFoodPanel;

public class Type52 {
    String podoption;
    String deliveryoption;
    String takeawayoption;
    String prebookingoption;

    public Type52(){

    }

    public Type52(String podoption, String deliveryoption, String takeawayoption, String prebookingoption) {
        this.podoption = podoption;
        this.deliveryoption = deliveryoption;
        this.takeawayoption = takeawayoption;
        this.prebookingoption = prebookingoption;
    }
    public String getpodoption() {
        return podoption;
    }
    public String getdeliveryoption() {
        return deliveryoption;
    }
    public String gettakeawayoption() {
        return takeawayoption;
    }
    public String getprebookingoption() {
        return prebookingoption;
    }

    public void  setpodoption(String podoption) {
        this.podoption = podoption;
    }
    public void  setdeliveryoption(String deliveryoption) {
        this.deliveryoption = deliveryoption;
    }
    public void  settakeawayoption(String takeawayoption) {
        this.takeawayoption = takeawayoption;
    }
    public void  setprebookingoption(String prebookingoption) {
        this.prebookingoption = prebookingoption;
    }
}
