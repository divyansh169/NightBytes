//package com.citymart.app;
//
//import com.google.auth.oauth2.GoogleCredentials;
//        import com.google.auth.oauth2.ServiceAccountCredentials;
//
//        import java.io.FileInputStream;
//
//import com.google.auth.oauth2.AccessToken;
//
//
//public class AccessTokenProvider {
//    public static String getAccessToken() {
//        try {
//            // Load service account credentials from JSON file
//            FileInputStream fis = new FileInputStream("credentials/fooddelivery-d9c7a-0aa788bc0054.json");
//            GoogleCredentials credentials = ServiceAccountCredentials.fromStream(fis);
//
//            // Create access token
//            AccessToken accessToken = credentials.refreshAccessToken();
//            return accessToken.getTokenValue();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
//
////
////import com.google.api.gax.core.FixedCredentialsProvider;
////import com.google.auth.oauth2.GoogleCredentials;
////import com.google.cloud.dialogflow.v2.SessionsClient;
////import com.google.cloud.dialogflow.v2.SessionsSettings;
////import com.google.protobuf.ByteString;
////
////import java.io.FileInputStream;
////
////public class AccessTokenProvider {
////    public static String getAccessToken() {
////        try {
////            // Load service account credentials from JSON file
////            FileInputStream fis = new FileInputStream("path/to/credentials/service_account_credentials.json");
////            GoogleCredentials credentials = GoogleCredentials.fromStream(fis);
////
////            // Set up SessionsSettings
////            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
////            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials)).build();
////
////            // Create SessionsClient
////            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);
////
////            // Generate access token (not recommended for long-term usage)
////            String accessToken = sessionsClient.getAccessToken(ByteString.copyFromUtf8("")).getTokenValue();
////            return accessToken;
////        } catch (Exception e) {
////            e.printStackTrace();
////            return null;
////        }
////    }
////}
