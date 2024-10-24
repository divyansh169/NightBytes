package com.citymart.app.CustomerFoodPanel;

public class CustomerPendingOrders {

    private String ChefId, DishID, DishName, DishQuantity, Price, TotalPrice;

    public CustomerPendingOrders(String dishID, String dishName, String dishQuantity, String price, String totalPrice, String chefId) {
        ChefId = chefId;
        DishID = dishID;
        DishName = dishName;
        DishQuantity = dishQuantity;
        Price = price;
        TotalPrice = totalPrice;
//        Rpayid = rpayid;

    }

    public CustomerPendingOrders() {

    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }

//    public String getrpayid() {
//        return Rpayid;
//    }
//
//    public void setrpayid(String rpayid) {
//        Rpayid = rpayid;
//    }

    public String getDishID() {
        return DishID;
    }

    public void setDishID(String dishID) {
        DishID = dishID;
    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishQuantity() {
        return DishQuantity;
    }

    public void setDishQuantity(String dishQuantity) {
        DishQuantity = dishQuantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }


}
