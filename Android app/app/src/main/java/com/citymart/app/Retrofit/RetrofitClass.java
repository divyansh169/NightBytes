//package com.citymart.app.Retrofit;
//
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//public class RetrofitClass {
//    public static APIServices getRetrofit() {
//        return new Retrofit.Builder()
//                .baseUrl("https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(APIServices.class);
//    }
//}
