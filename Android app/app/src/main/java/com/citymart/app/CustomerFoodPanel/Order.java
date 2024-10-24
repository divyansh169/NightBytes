package com.citymart.app.CustomerFoodPanel;

import java.util.Map;

public class Order {
    private String companyName;
    private String finishDate;
    private String grandTotalPrice;
    private Map<String, Dish> dishes;

    // Constructor, getters, and setters

    public Order() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getGrandTotalPrice() {
        return grandTotalPrice;
    }

    public void setGrandTotalPrice(String grandTotalPrice) {
        this.grandTotalPrice = grandTotalPrice;
    }

    public Map<String, Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Map<String, Dish> dishes) {
        this.dishes = dishes;
    }
}
