package com.citymart.app.CustomerFoodPanel;

public class ChatMessage {
    private String userId;
    private String message;
    private long timestamp;

    // Required default constructor for Firebase
    public ChatMessage() {
    }

    public ChatMessage(String userId, String message, long timestamp) {
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




