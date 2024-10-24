package com.citymart.app.ChefFoodPanel;

public class ChatMessageSeller {
    private String userId;
    private String message;
    private long timestamp;

    // Required default constructor for Firebase
    public ChatMessageSeller() {
    }

    public ChatMessageSeller(String userId, String message, long timestamp) {
        this.userId = userId;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
