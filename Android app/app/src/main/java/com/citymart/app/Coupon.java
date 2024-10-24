package com.citymart.app;

public class Coupon {
    private String id;            // Unique identifier for each coupon
    private String couponCode;
    private String couponType;
    private String discount;
    private String freeItem;
    private String minOrderValue;

    // Default constructor (no arguments)
    public Coupon() {
    }

    // Constructor to initialize all fields except the ID
    public Coupon(String couponCode, String discount, String minOrderValue, String freeItem, String couponType) {
        this.couponCode = couponCode;
        this.discount = discount;
        this.minOrderValue = minOrderValue;
        this.freeItem = freeItem;
        this.couponType = couponType;
    }

    // Constructor to initialize all fields including the ID
    public Coupon(String id, String couponCode, String discount, String minOrderValue, String freeItem, String couponType) {
        this.id = id;
        this.couponCode = couponCode;
        this.discount = discount;
        this.minOrderValue = minOrderValue;
        this.freeItem = freeItem;
        this.couponType = couponType;
    }

    // Getters and Setters for all fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getFreeItem() {
        return freeItem;
    }

    public void setFreeItem(String freeItem) {
        this.freeItem = freeItem;
    }

    public String getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(String minOrderValue) {
        this.minOrderValue = minOrderValue;
    }
}
