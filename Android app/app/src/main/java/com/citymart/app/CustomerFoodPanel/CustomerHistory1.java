package com.citymart.app.CustomerFoodPanel;

public class CustomerHistory1 implements Comparable<CustomerHistory1>{

    private String Address, ChefId, ChefName,CompanyName,GrandTotalPrice,MobileNumber,Name,RandomUID, Finishdate;

    public CustomerHistory1(String address,String chefId,  String companyName ,String chefName, String grandTotalPrice, String mobileNumber, String name, String randomUID,  String finishdate) {
        Address = address;
        ChefId = chefId;
        ChefName = chefName;
        GrandTotalPrice = grandTotalPrice;
//        GrandTotalPrice = ChefPendingOrdersAdapter.fprice;
        MobileNumber = mobileNumber;
        Name = name;
        RandomUID = randomUID;
//        Note = note;
        Finishdate = finishdate;
        CompanyName = companyName;
    }

    public CustomerHistory1()
    {

    }

    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }

    public String getChefId() {
        return ChefId;
    }
    public void setChefId(String chefId) {
        ChefId = chefId;
    }

    public String getChefName() {
        return ChefName;
    }
    public void setChefName(String chefName) {
        ChefName = chefName;
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
    public void setName(String name) {
        Name = name;
    }

//    public String getNote() {
//        return Note;
//    }
//    public void setNote(String note) {
//        Note = note;
//    }

    public String getRandomUID() {
        return RandomUID;
    }
    public void setRandomUID(String randomUID) {
        RandomUID = randomUID;
    }

    public String getFinishdate() {
        return Finishdate;
    }
    public void setFinishdate(String finishdate) {
        Finishdate = finishdate;
    }

    public String getCompanyName() {
        return CompanyName;
    }
    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }
    @Override
    public int compareTo(CustomerHistory1 other) {
        return this.Finishdate.compareTo(other.getFinishdate());
    }
}
