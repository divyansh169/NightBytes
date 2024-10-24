package com.citymart.app.notifications;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class FCMSender {
    /*
     * URL where we request to send notification and the key to send notification using admin sdk
     * */
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send", KEY_STRING = "key=AAAACsNsjOs:APA91bGQ391e_WUGlXCU8zxoAsLC-SQ4FUyWVZTJEWon0KXQBKVvjgQRRP6br5A3TGFb7kNhEaNF780ReBKGe1D9R1NsK0AukKXOps6tqGq_eyyylQHAbCAnjHbC3j81lVu2BTI0-_G-";

    OkHttpClient client = new OkHttpClient();

    /*
     * Method to send notification to the application
     * */
    public void send(String message, Callback callback) {
        RequestBody reqBody = RequestBody.create(MediaType.get("application/json")
                        , message);

        Request request = new Request.Builder()
                .url(FCM_URL)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", KEY_STRING)
                .post(reqBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
