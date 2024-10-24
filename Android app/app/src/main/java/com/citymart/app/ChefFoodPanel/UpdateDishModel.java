package com.citymart.app.ChefFoodPanel;

public class UpdateDishModel {

    String Dishes,RandomUID,Description,Quantity,Price,ImageURL,ChefId,Prodatt, Stockcnt;


    public UpdateDishModel()
    {

    }

    public String getDescription() {
        return Description;
    }

    public String getprodatt() {
        return Prodatt;
    }


    public String getstockcnt() {
        return Stockcnt;
    }

//    public String getrpayid() {
//        return Rpayid;
//    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setProdatt(String prodatt) {
        Prodatt = prodatt;
    }

    public void setStockcnt(String stockcnt) {
        Stockcnt = stockcnt;
    }



//    public void setrpayid(String rpayid) {
//        Rpayid = rpayid;
//    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getRandomUID() {

        return RandomUID;
    }

    public void setRandomUID(String randomUID) {

        RandomUID = randomUID;
    }

    public String getDishes()
    {
        return Dishes;
    }

    public void setDishes(String dishes) {

        Dishes = dishes;
    }

    public String getChefId() {
        return ChefId;
    }

    public void setChefId(String chefId) {
        ChefId = chefId;
    }
}
