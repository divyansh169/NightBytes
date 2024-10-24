package com.citymart.app.ChefFoodPanel;

public class ChefHistory3 {

    private String Address,GrandTotalPrice,MobileNumber,Name,RandomUID,Status,Note, Finishdate;

    public ChefHistory3(String address, String grandTotalPrice, String mobileNumber, String name, String note, String randomUID, String status, String finishdate) {
        Address = address;
        GrandTotalPrice = grandTotalPrice;
//        GrandTotalPrice = ChefPendingOrdersAdapter.fprice;
        MobileNumber = mobileNumber;
        Note = note;
        Name = name;
        RandomUID = randomUID;
        Status = status;
        Finishdate = finishdate;
    }

    public ChefHistory3()
    {

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGrandTotalPrice() {
        return GrandTotalPrice;
    }

    public void setGrandTotalPrice(String grandTotalPrice) {
        GrandTotalPrice = grandTotalPrice;
    }

    public String getMobileNumber() {
        return MobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    public String getName() {
        return Name;
    }

    public String getNote() {
        return Note;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRandomUID() {
        return RandomUID;
    }

    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getFinishdate() {
        return Finishdate;
    }

    public void setFinishdate(String finishdate) {
        Finishdate = finishdate;
    }
}
