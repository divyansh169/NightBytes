package com.citymart.app.ChefFoodPanel;

public class FoodSupplyDetails {

    public String Dishes,Quantity,Price,Description,ImageURL,RandomUID,ChefId,Prodatt,Stockcnt;

    public FoodSupplyDetails(String dishes, String quantity, String price, String description, String imageURL,String randomUID,String chefId,String prodatt, String stockcnt) {
        Dishes = dishes;
        Quantity = quantity;
        Price = price;
        Description = description;
        ImageURL = imageURL;
        RandomUID=randomUID;
        ChefId=chefId;
        Prodatt=prodatt;
        Stockcnt=stockcnt;
//        Rpayid=rpayid;
    }

}
