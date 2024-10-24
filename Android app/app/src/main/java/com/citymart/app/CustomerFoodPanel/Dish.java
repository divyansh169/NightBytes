package com.citymart.app.CustomerFoodPanel;

import com.google.firebase.auth.FirebaseAuth;
import java.util.HashMap;
import java.util.Map;

public class Dish {
    private String ChefId;
    private String Description;
    private String Dishes; // Assuming this is the dish name
    private String ImageURL;
    private String Price;
    private String Prodatt;
    private String Quantity;
    private String RandomUID;
    private String Stockcnt;

    public Dish() {
        // Default constructor required for calls to DataSnapshot.getValue(Dish.class)
    }

    // Getters and setters for all fields
    public String getChefId() { return ChefId; }
    public void setChefId(String chefId) { ChefId = chefId; }

    public String getDescription() { return Description; }
    public void setDescription(String description) { Description = description; }

    public String getDishes() { return Dishes; }
    public void setDishes(String dishes) { Dishes = dishes; }

    public String getImageURL() { return ImageURL; }
    public void setImageURL(String imageURL) { ImageURL = imageURL; }

    public String getPrice() { return Price; }
    public void setPrice(String price) { Price = price; }

    public String getProdatt() { return Prodatt; }
    public void setProdatt(String prodatt) { Prodatt = prodatt; }

    public String getQuantity() { return Quantity; }
    public void setQuantity(String quantity) { Quantity = quantity; }

    public String getRandomUID() { return RandomUID; }
    public void setRandomUID(String randomUID) { RandomUID = randomUID; }

    public String getStockcnt() { return Stockcnt; }
    public void setStockcnt(String stockcnt) { Stockcnt = stockcnt; }

    // Modified method to get only the required fields for Firebase
    public Map<String, String> toMap() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> dishMap = new HashMap<>();
        dishMap.put("ChefId", ChefId);
        dishMap.put("DishId", RandomUID);
        dishMap.put("DishName", Dishes);
        dishMap.put("DishPrice", Price);
        dishMap.put("DishQuantity", Quantity);
        dishMap.put("RandomUID", RandomUID);
        dishMap.put("TotalPrice", getTotalPrice());
        dishMap.put("UserId", userId);

        return dishMap;
    }

    // Method to calculate total price as a string (stored as an integer in Firebase)
    public String getTotalPrice() {
        int price = Integer.parseInt(Price);
        int quantity = Integer.parseInt(Quantity);
        return String.valueOf(price * quantity);
    }

    // Method to get DishId (which is assumed to be the same as RandomUID)
    public String getDishId() {
        return RandomUID;
    }
}
