package com.citymart.app.CustomerFoodPanel;

public class CustomerHistory {

    private String DishName,DishPrice,DishQuantity,TotalPrice;
//    private String DishName,DishPrice,DishQuantity,TotalPrice, DishId;

    public CustomerHistory(String dishName, String dishPrice, String dishQuantity, String totalPrice) {
//    public ChefHistory(String dishName, String dishPrice, String dishQuantity, String totalPrice, String dishId) {
//        ChefId = chefId;
//        DishId = dishId;
        DishName = dishName;
        DishPrice = dishPrice;
        DishQuantity = dishQuantity;
//        RandomUID = randomUID;
//        Finishdate = finishdate;
        TotalPrice = totalPrice;
//
//        UserId = userId;
//        Rpayid = rpayid;
    }

    public CustomerHistory()
    {

    }

//    public String getChefId() {
//        return ChefId;
//    }
//
//    public void setChefId(String chefId) {
//        ChefId = chefId;
//    }
//
//    public String getDishId() {
//        return DishId;
//    }
//
//    public void setDishId(String dishId) {
//        DishId = dishId;
//    }

    public String getDishName() {
        return DishName;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public String getDishPrice() {
        return DishPrice;
    }

    public void setDishPrice(String dishPrice) {
        DishPrice = dishPrice;
    }

    public String getDishQuantity() {
        return DishQuantity;
    }

    public void setDishQuantity(String dishQuantity) {
        DishQuantity = dishQuantity;
    }

//    public String getRandomUID() {
//        return RandomUID;
//    }
//
//    public void setRandomUID(String randomUID) {
//        RandomUID = randomUID;
//    }

//    public String getFinishdate() {
//        return Finishdate;
//    }
//
//    public void setFinishdate(String finishdate) {
//        Finishdate = finishdate;
//    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }
//



//    public String getUserId() {
//        return UserId;
//    }
//
//    public void setUserId(String userId) {
//        UserId = userId;
//    }

//    public String getrpayid() {
//        return Rpayid;
//    }
//
//    public void setrpayid(String rpayid) {
//        Rpayid = rpayid;
//    }
}
