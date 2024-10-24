package com.citymart.app.CustomerFoodPanel;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.ChefPendingOrders;
import com.citymart.app.ChefFoodPanel.ChefPendingOrders1;
import com.citymart.app.ChefFoodPanel.Chef_PostRazorpayIDDDD;
import com.citymart.app.ChefFoodPanel.Type13;
import com.citymart.app.ChefFoodPanel.Type16;
import com.citymart.app.ChefFoodPanel.Type31;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.ChefFoodPanel.Type6;
import com.citymart.app.ChefFoodPanel.Type7;
import com.citymart.app.ChefFoodPanel.TypeC;
import com.citymart.app.Customer;
//import com.citymart.app.FCMSend;
import com.citymart.app.CustomerFoodPanel_BottomNavigation;
import com.citymart.app.R;
import com.citymart.app.Registeration;
import com.citymart.app.ReusableCode.ReusableCodeForAll;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

//import com.citymart.app.databinding.ActivityCustomerPhonenumberBinding;
//import com.citymart.app.databinding.FragmentCustomercartBinding;
import com.citymart.app.notifications.FCMSender;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

import android.app.Activity;
import android.content.Intent;
import android.view.inputmethod.InputMethodManager;

import androidx.browser.customtabs.CustomTabsIntent;

import com.google.firebase.messaging.FirebaseMessaging;
//import com.citymart.app.databinding.ActivityCustomerPhonenumberBinding;
import com.citymart.app.notifications.NotificationMessage;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


//import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
//import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
//import com.swapnil.helloworld.databinding.ActivityAuthUiBinding;

public class CustomerCartFragment extends Fragment {

    RecyclerView recyclecart;
//    NotificationChannel notificationChannel;
//    RelativeLayout recyclecart;
    private List<Cart> cartModelList;
    private CustomerCartAdapter adapter;
    private LinearLayout TotalBtns;
    DatabaseReference databaseReference, data, reference, referenced, refereu, ref, getRef, dataa, databaseReferencee;
    public static TextView grandt, txtGroupStatus;
    public static String ordermethod, ordertime;
    public static String currentgroup = "";
    public static int deliveryflag=0;
    public static int dineinflag=0;
    public static int takeawayflag=0;
    Button remove, placeorder, instacc;

    LinearLayout grouplay,createlay, joinlay, gtlay, finallay;
    Button crgrp, joingrp, deletecr, detailcr, additemcr, leavejoin, detailjoin, additemjoin;

    LottieAnimationView annote, coup, newadd, openingclosinganim, specialdiscount, todaysdeal, feedback;
    String address, Addnote;
//    LinearLayout layoutt;
    String DishId, RandomUId, ChefId, useriddu, RandomUIddu;
    public static String ChefIdd, chefstorename, ssuburban, sstate, ccity, cmobile, clname, cfname, leadid;
    public static String pauseflag;
    public int stoppedflag;
    public static String Chefwaliid;
    public static String grandtotalop;
    public static String rindomid;
    public static String tablecheck;
    public static String custdelch, custdisc, custminordval, custordtim, custpod, custdeloption, custtwoption, custprebopt;
    Dialog dialog;
    public static int pendcount;
    public static String loadrr, extradd, addressp;
//    String usertoken;
    String ss;
    private ProgressDialog progressDialog;
    private APIService apiService;

    public static String starthour;
    public static String startmin;
    public static String startsec;
    public static String endhour;
    public static String endmin;
    public static String endsec;

    // Optionally, you can convert the strings to integers if needed
    public static int starthourInt;
    public static int startminInt;
    public static int startsecInt;
    public static int endhourInt;
    public static int endminInt;
    public static int endsecInt;

    EditText notifMessage, notifNumber;
    Button send_notif;
    private LocationRequest locationRequest;


//    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Cart");
        View v = inflater.inflate(R.layout.fragment_customercart, null);
        recyclecart = v.findViewById(R.id.recyclecart);
//        sellernote = v.findViewById(R.id.sellernote);
//        layoutt = v.findViewById(R.id.layoutt);
//        layoutt.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        recyclecart.setHasFixedSize(true);
        recyclecart.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        cartModelList = new ArrayList<>();
        grandt = v.findViewById(R.id.GT);
        txtGroupStatus = v.findViewById(R.id.txtGroupStatus);
        //coup, newadd, openingclosinganim, specialdiscount, todaysdeal, feedback
        annote = v.findViewById(R.id.annote);
//        coup = v.findViewById(R.id.coup);
//        newadd = v.findViewById(R.id.newadd);
//        openingclosinganim = v.findViewById(R.id.openingclosinganim);
//        specialdiscount = v.findViewById(R.id.specialdiscount);
//        todaysdeal = v.findViewById(R.id.todaysdeal);
//        feedback = v.findViewById(R.id.feedback);
        remove = v.findViewById(R.id.RM);
        placeorder = v.findViewById(R.id.PO);

//        LinearLayout grouplay,createlay, joinlay;
//        Button crgrp, joingrp, deletecr, detailcr, additemcr, leavejoin, detailjoin, additemjoin;
        grouplay = v.findViewById(R.id.grouplay);
        createlay = v.findViewById(R.id.createlay);
        joinlay = v.findViewById(R.id.joinlay);
        gtlay = v.findViewById(R.id.gtlay);
        finallay = v.findViewById(R.id.finallay);

        crgrp = v.findViewById(R.id.crgrp);
        joingrp = v.findViewById(R.id.joingrp);
        deletecr = v.findViewById(R.id.deletecr);
        detailcr = v.findViewById(R.id.detailcr);
        additemcr = v.findViewById(R.id.additemcr);
        leavejoin = v.findViewById(R.id.leavejoin);
        detailjoin = v.findViewById(R.id.detailjoin);
        additemjoin = v.findViewById(R.id.additemjoin);


        instacc = v.findViewById(R.id.instacc);
        dialog= new Dialog(getContext());
        TotalBtns = v.findViewById(R.id.TotalBtns);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        notifMessage= v.findViewById(R.id.notifMessage);
        notifNumber= v.findViewById(R.id.notifNumber);
        send_notif= v.findViewById(R.id.send_notif);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");

//                binding=FragmentCustomercartBinding.inflate(getLayoutInflater());
//        onCreateView(binding.getRoot());
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
//        setUpButtons();


//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            return;
//                        }
//                        String token = task.getResult();
//
//                    }
//                });



        // fcm settings for perticular user

//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (task.isSuccessful()) {
//                            String  usertoken = Objects.requireNonNull(task.getResult()).getToken();
//                            Log.d("toooo","token: "+usertoken);
//
//                        }
//
//                    }
//                });

//        DatabaseReference datu = FirebaseDatabase.getInstance().getReference("chefnotes").child("RdDWkrp148Nqw4vsM6JYwFK4zlu1");
//        datu.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type6 type6 = dataSnapshot.getValue(Type6.class);
//
//                cheffsnote = type6.getcfnote();
//                sellernote.setText(cheffsnote);
        DatabaseReference brefereu = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        brefereu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotyy) {
                if(dataSnapshotyy.exists()) {
                for (DataSnapshot dataSnapshot13y : dataSnapshotyy.getChildren()) {
                    final Cart cart13yy = dataSnapshot13y.getValue(Cart.class);
                    ChefIdd = cart13yy.getChefId();
                    break;

                }

                DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
                cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {

                        if (dataSnapshotaw.exists()) {
                            Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
                            pauseflag = typechap16.getautoaccept();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                    DatabaseReference kjdk6 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("ordertimings");
                    kjdk6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                custordtim = dataSnapshot.getValue(String.class);
//                                starthour = custordtim.substring(0, 2);
//                                startmin = custordtim.substring(3, 5);
//                                startsec = custordtim.substring(6, 8);
//                                endhour = custordtim.substring(9, 11);
//                                endmin = custordtim.substring(12, 14);
//                                endsec = custordtim.substring(15, 17);
//
//                                starthourInt = Integer.parseInt(starthour);
//                                startminInt = Integer.parseInt(startmin);
//                                startsecInt = Integer.parseInt(startsec);
//                                endhourInt = Integer.parseInt(endhour);
//                                endminInt = Integer.parseInt(endmin);
//                                endsecInt = Integer.parseInt(endsec);


                                try {
                                    // Splitting the time string
                                    String[] timeComponents = custordtim.split(",");

                                    // Ensure that we have exactly 3 components
                                    if (timeComponents.length == 6) {
                                        starthourInt = Integer.parseInt(timeComponents[0]);
                                        startminInt = Integer.parseInt(timeComponents[1]);
                                        startsecInt = Integer.parseInt(timeComponents[2]);
                                        endhourInt = Integer.parseInt(timeComponents[3]);
                                        endminInt = Integer.parseInt(timeComponents[4]);
                                        endsecInt = Integer.parseInt(timeComponents[5]);

                                        starthour = timeComponents[0];
                                        startmin = timeComponents[1];
                                        startsec = timeComponents[2];
                                        endhour = timeComponents[3];
                                        endmin = timeComponents[4];
                                        endsec = timeComponents[5];

                                    } else {
                                        Toast.makeText(getContext(), "Invalid time format", Toast.LENGTH_SHORT).show();
//                                        Log.e("TimeError", "Invalid time format: " + custordtim);
                                    }
                                } catch (NumberFormatException e) {
//                                    Log.e("TimeError", "Error parsing time components: " + e.getMessage());
                                    Toast.makeText(getContext(), "Error parsing time components", Toast.LENGTH_SHORT).show();
                                }









                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //minordval...........................................................................................................................
                    DatabaseReference kjdk9 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("minordval");
                    kjdk9.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                custminordval = dataSnapshot.getValue(String.class);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//        enable delivery option
                    DatabaseReference gty2 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(ChefIdd).child("deliveryoption");
                    gty2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.getdeliveryoption();
                                custdeloption = dataSnapshot.getValue(String.class);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //enable takeaway option
                    DatabaseReference gty3 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(ChefIdd).child("takeawayoption");
                    gty3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.gettakeawayoption();
                                custtwoption = dataSnapshot.getValue(String.class);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //enable prebooking option
                    DatabaseReference gty4 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(ChefIdd).child("prebooking");
                    gty4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.getprebookingoption();
                                custprebopt = dataSnapshot.getValue(String.class);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        customercart();

        return v;
    }

    private void customercart() {

//        getCurrentLocation();

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(userID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


//                ////////
//                int wholetotal = 0;
//
//                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
//                    // Assuming the structure under each dishid is { "Totalprice": value }
//                    Long totalPrice = dishSnapshot.child("Totalprice").getValue(Long.class);
//
//                    if (totalPrice != null) {
//                        wholetotal += totalPrice;
//                    }
//                }
//                ///////



                cartModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Cart cart = snapshot.getValue(Cart.class);

                    cartModelList.add(cart);
                }

                if (cartModelList.size() == 0) {
                    TotalBtns.setVisibility(View.INVISIBLE);

//                    annote.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
////                            openDialog();
//                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
////                            builder2.setTitle("Announcement");
//                            LayoutInflater inflater = getActivity().getLayoutInflater();
//                            View view2 = inflater.inflate(R.layout.activity_announcement_card, null);
//                            final TextView textann = (TextView) view2.findViewById(R.id.textann);
//
//
//
//                            refereu = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            refereu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot dataSnapshot13 : dataSnapshot.getChildren()) {
//                                        final Cart cart13 = dataSnapshot13.getValue(Cart.class);
//                                        ChefIdd = cart13.getChefId();
//                                        break;
//
//                                    }
//                                    DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(ChefIdd);
////
//                                    datu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            Type6 type6 = dataSnapshot.getValue(Type6.class);
//                                            textann.setText(type6.getcfnote());
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
////                            FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//
//
//                            builder2.setView(view2);
//
//                            AlertDialog alert2 = builder2.create();
//                            alert2.show();
//
//
//
//                        }
//                    });


                }
                else {
//                    if(cartModelList.size() <= 5){
                        TotalBtns.setVisibility(View.VISIBLE);
                        checkUserFlagStatus();

//                    FirebaseDatabase.getInstance().getReference().child("Chef").child(ChefIdd).child("Suburban").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            chefstorename = dataSnapshot.getValue(String.class);
//                            getActivity().setTitle(chefstorename);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });



                    annote.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            openDialog();
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
//                            builder2.setTitle("Announcement");
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            View view2 = inflater.inflate(R.layout.activity_announcement_card, null);
                            final TextView textann = (TextView) view2.findViewById(R.id.textann);
                            final TextView textann1 = (TextView) view2.findViewById(R.id.textann1);
                            final TextView textann2 = (TextView) view2.findViewById(R.id.textann2);
                            final TextView textann3 = (TextView) view2.findViewById(R.id.textann3);
                            final TextView textann4 = (TextView) view2.findViewById(R.id.textann4);
                            final TextView textann6 = (TextView) view2.findViewById(R.id.textann6);
                            final TextView textann7 = (TextView) view2.findViewById(R.id.textann7);



//                            refereu = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                            refereu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    for (DataSnapshot dataSnapshot13 : dataSnapshot.getChildren()) {
//                                        final Cart cart13 = dataSnapshot13.getValue(Cart.class);
//                                        ChefIdd = cart13.getChefId();
//                                        break;
//
//                                    }
                                    DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(ChefIdd);
//
                                    datu.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()) {
                                                Type6 type6 = dataSnapshot.getValue(Type6.class);
                                                textann.setText(type6.getcfnote());
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

//                            DatabaseReference vcref = FirebaseDatabase.getInstance().getReference("chefCoupons").child(ChefIdd);
//                            vcref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if(dataSnapshot.exists()) {
//                                        TypeC typecha = dataSnapshot.getValue(TypeC.class);
//                                        if(Objects.equals(typecha.getcpntxt1(), "1")){
//                                            textann1.setText(typecha.getcpntxt() + " : For discount of Rs." + typecha.getcpntxt3() + " at minimum order value of Rs." + typecha.getcpntxt2());
//                                        }
//                                        else if(Objects.equals(typecha.getcpntxt1(), "2")){
//                                            textann1.setText(typecha.getcpntxt() + " : For getting " + typecha.getcpntxt3() + " free with your order at minimum order value of Rs." + typecha.getcpntxt2());
//                                        }
//
//                                    }
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });

                                    //??????????
                            //todays special...........................................................................................................................
                            DatabaseReference kjdk1 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("todayspecial");
                            kjdk1.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()) {
                                        String ts = dataSnapshot.getValue(String.class);
                                        textann2.setText(ts);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //special offer...........................................................................................................................
                            DatabaseReference kjdk2 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("specialoffer");
                            kjdk2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()) {
                                        String so = dataSnapshot.getValue(String.class);
                                        textann3.setText(so);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //newly added...........................................................................................................................
                            DatabaseReference kjdk3 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("newlyadded");
                            kjdk3.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()) {
                                        String so = dataSnapshot.getValue(String.class);
                                        textann4.setText(so);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            //feedback...........................................................................................................................
                            DatabaseReference kjdk4 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("feedback");
                            kjdk4.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()) {
                                        String so = dataSnapshot.getValue(String.class);
                                        textann6.setText(so);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                            //openinghours...........................................................................................................................
                            DatabaseReference kjdk5 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("openinghours");
                            kjdk5.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if(dataSnapshot.exists()) {
                                        String so = dataSnapshot.getValue(String.class);
                                        textann7.setText(so);
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                            //""""""""""""""

//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//                            FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());



                            builder2.setView(view2);

                            AlertDialog alert2 = builder2.create();
                            alert2.show();



                        }
                    });




                    remove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage("Are you sure you want to remove all items from the cart?");
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                                    FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();

                                }
                            });
                            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();

                        }
                    });


//                    DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
//                                    cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
//
//                                            if(dataSnapshotaw.exists()) {
//                                                Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
//                                                pauseflag = typechap16.getautoaccept();
//                                            }



                    FirebaseDatabase.getInstance().getReference("Chef").child(ChefIdd).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final Chef chif = dataSnapshot.getValue(Chef.class);

                            ccity = chif.getCity();
                            sstate = chif.getState();
                            ssuburban = chif.getSuburban();
//                            getActivity().setTitle(ssuburban);


                    String UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    data = FirebaseDatabase.getInstance().getReference("Customer").child(UserID);
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final Customer customer = dataSnapshot.getValue(Customer.class);


                            cfname = customer.getFirstName();
                            clname = customer.getLastName();
                            cmobile = customer.getMobileno();


//




//                            firebaseAuth = FirebaseAuth.getInstance();
//                            groupsRef = FirebaseDatabase.getInstance().getReference("Groups");
//                            groupsUserFlagRef = FirebaseDatabase.getInstance().getReference("GroupsUserFlag");
//                            userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                            crgrp.setOnClickListener(v -> checkUserFlagBeforeCreate());
                            joingrp.setOnClickListener(v -> checkUserFlagBeforeJoin());
                            deletecr.setOnClickListener(v -> showDeleteConfirmationDialog());
                            leavejoin.setOnClickListener(v -> showLeaveConfirmationDialog());
                            additemcr.setOnClickListener(v -> showDialog());
                            additemjoin.setOnClickListener(v -> showDialog());
                            // detailcr.setOnClickListener(v -> ());
                            detailcr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Show DialogFragment with user details
                                    UserDetailsDialogFragment dialog = new UserDetailsDialogFragment();
                                    dialog.setArguments(getGroupArguments()); 
                                    dialog.show(getFragmentManager(), "UserDetailsDialog");
                                }
                            });
                            detailjoin.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Show DialogFragment with user details
                                    UserDetailsDialogFragment dialog = new UserDetailsDialogFragment();
                                    dialog.setArguments(getGroupArguments()); 
                                    dialog.show(getFragmentManager(), "UserDetailsDialog");
                                }
                            });
                            // detailjoin.setOnClickListener(v -> ());

//                            crgrp.setOnClickListener(v -> showCreateGroupDialog());
//                            joingrp.setOnClickListener(v -> showJoinGroupDialog());



//                            DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
//                                    cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
//
//                                            if(dataSnapshotaw.exists()) {
//                                                Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
//                                                pauseflag = typechap16.getautoaccept();
//                                            }

//                            placeorder.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                }
//                            });



                            // Click listener for placing an order
                            placeorder.setOnClickListener(v -> {
                                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                // Check the flag in GroupsUserFlag for the current user
                                FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        boolean canPlaceOrder = true;  // Default to true unless user is part of a group with flag=2

                                        if (dataSnapshot.exists()) {
                                            Long flagValue = dataSnapshot.child("flag").getValue(Long.class);

                                            if (flagValue != null && flagValue == 2) {
                                                // User has joined a group but didn't create it, show alert message
                                                canPlaceOrder = false;
                                                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                                                builder.setTitle("Error");
                                                builder.setMessage("Only the person who created the group can place the group order. If you want to place a self order, you must leave the group first.");

                                                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()); // Dismiss dialog
                                                builder.show();
                                            }
                                        }

                                        // If the user can place the order (either they created the group or are not part of any group)
                                        if (canPlaceOrder) {

                                            getCurrentLocation();

//                                    DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
//                                    cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
//
//                                            if(dataSnapshotaw.exists()) {
//                                                Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
//                                                pauseflag = typechap16.getautoaccept();
//                                            }
//                                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });


                                            FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("isOrdered").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                                            turnOnGPS();
//                                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                                                    DatabaseReference gref = FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal");
                                                    gref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshoty) {
                                                            grandtotalop = dataSnapshoty.getValue(String.class);
//                                                     grandt.setText(grandtotalop);


//                                                        }
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                        }
//                                                    });


                                                            ss = "";
                                                            if (dataSnapshot.exists()) {
                                                                ss = dataSnapshot.getValue(String.class);
                                                            }

                                                            DatabaseReference fop = FirebaseDatabase.getInstance().getReference("ChefStopped").child(ChefIdd);
                                                            fop.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshoti) {
                                                                    if(dataSnapshoti.exists()) {
                                                                        Type31 typechapkk = dataSnapshoti.getValue(Type31.class);
                                                                        stoppedflag = typechapkk.getstopped();
                                                                    }

//                                                        }
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                        }
//                                                    });


                                                                    if (Objects.equals(pauseflag, "1")  && (stoppedflag==0)) {

                                                                        if (Integer.parseInt(grandtotalop) >= Integer.parseInt(custminordval)) {

//                                                            if (cartModelList.size() <= 5 && cartModelList.size() >= 1) {

//                                                                DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
//                                                                cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                    @Override
//                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
//
//                                                                        if(dataSnapshotaw.exists()) {
//                                                                            Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
//                                                                            pauseflag = typechap16.getautoaccept();
//                                                                        }


                                                                            if (ss.trim().equalsIgnoreCase("false") || ss.trim().equalsIgnoreCase("")) {

                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                                                builder.setTitle("Order Details");
                                                                                LayoutInflater inflater = getActivity().getLayoutInflater();
                                                                                View view = inflater.inflate(R.layout.enter_address, null);
                                                                                LinearLayout addresslayout = view.findViewById(R.id.addresslayout);
                                                                                LinearLayout tablelayout = view.findViewById(R.id.tablelayout);
                                                                                Spinner tableSpinner = view.findViewById(R.id.table_spinner);
                                                                                final EditText localaddress = (EditText) view.findViewById(R.id.LA);
                                                                                final EditText addnote = (EditText) view.findViewById(R.id.addnote);
                                                                                RadioGroup group = (RadioGroup) view.findViewById(R.id.grp);
                                                                                RadioGroup group2 = (RadioGroup) view.findViewById(R.id.grp2);
                                                                                final RadioButton home = (RadioButton) view.findViewById(R.id.HA);
                                                                                final RadioButton other = (RadioButton) view.findViewById(R.id.OA);
                                                                                final RadioButton delivery = (RadioButton) view.findViewById(R.id.delivery);
                                                                                final RadioButton dinein = (RadioButton) view.findViewById(R.id.dinein);
                                                                                final RadioButton takeaway = (RadioButton) view.findViewById(R.id.takeaway);
                                                                                final LottieAnimationView delanime = view.findViewById(R.id.delanime);
                                                                                final LottieAnimationView dineanime = view.findViewById(R.id.dineanime);
                                                                                final LottieAnimationView twanime = view.findViewById(R.id.twanime);
                                                                                builder.setView(view);

                                                                                if (Objects.equals(custdeloption, "1")) {

                                                                                    delivery.setVisibility(View.VISIBLE);
                                                                                    delanime.setVisibility(View.VISIBLE);

                                                                                }
                                                                                else{
                                                                                    delivery.setVisibility(View.GONE);
                                                                                    delanime.setVisibility(View.GONE);
                                                                                    if(Objects.equals(custtwoption, "1")){
                                                                                        deliveryflag = 0;
                                                                                        takeawayflag = 1;
                                                                                        dineinflag = 0;
                                                                                        delivery.setChecked(false);
                                                                                        takeaway.setChecked(true);
                                                                                        dinein.setChecked(false);

                                                                                    }
                                                                                    else if(Objects.equals(custprebopt, "1")){
                                                                                        deliveryflag = 0;
                                                                                        takeawayflag = 0;
                                                                                        dineinflag = 1;
                                                                                        delivery.setChecked(false);
                                                                                        takeaway.setChecked(false);
                                                                                        dinein.setChecked(true);
                                                                                    }
                                                                                }

                                                                                if (Objects.equals(custtwoption, "1")) {

                                                                                    takeaway.setVisibility(View.VISIBLE);
                                                                                    twanime.setVisibility(View.VISIBLE);

                                                                                }
                                                                                else{
                                                                                    takeaway.setVisibility(View.GONE);
                                                                                    twanime.setVisibility(View.GONE);
                                                                                    if(Objects.equals(custdeloption, "1")){
                                                                                        deliveryflag = 1;
                                                                                        takeawayflag = 0;
                                                                                        dineinflag = 0;
                                                                                        delivery.setChecked(true);
                                                                                        takeaway.setChecked(false);
                                                                                        dinein.setChecked(false);
                                                                                    }
                                                                                    else if(Objects.equals(custprebopt, "1")){
                                                                                        deliveryflag = 0;
                                                                                        takeawayflag = 0;
                                                                                        dineinflag = 1;
                                                                                        delivery.setChecked(false);
                                                                                        takeaway.setChecked(false);
                                                                                        dinein.setChecked(true);
                                                                                    }
                                                                                }

                                                                                if (Objects.equals(custprebopt, "1")) {

                                                                                    dinein.setVisibility(View.VISIBLE);
                                                                                    dineanime.setVisibility(View.VISIBLE);

                                                                                }
                                                                                else{
                                                                                    dinein.setVisibility(View.GONE);
                                                                                    dineanime.setVisibility(View.GONE);
                                                                                    if(Objects.equals(custdeloption, "1")){
                                                                                        deliveryflag = 1;
                                                                                        takeawayflag = 0;
                                                                                        dineinflag = 0;
                                                                                        delivery.setChecked(true);
                                                                                        takeaway.setChecked(false);
                                                                                        dinein.setChecked(false);
                                                                                    }
                                                                                    else if(Objects.equals(custtwoption, "1")){
                                                                                        deliveryflag = 0;
                                                                                        takeawayflag = 1;
                                                                                        dineinflag = 0;
                                                                                        delivery.setChecked(false);
                                                                                        takeaway.setChecked(true);
                                                                                        dinein.setChecked(false);
                                                                                    }
                                                                                }


                                                                                if(Objects.equals(custdeloption, "1") && Objects.equals(custtwoption, "1") &&  Objects.equals(custprebopt, "1")){
                                                                                    deliveryflag = 1;
                                                                                    takeawayflag = 0;
                                                                                    dineinflag = 0;
                                                                                    delivery.setChecked(true);
                                                                                    takeaway.setChecked(false);
                                                                                    dinein.setChecked(false);

                                                                                }




                                                                                localaddress.setText(customer.getLocalAddress());
//                                                                    deliveryflag = 1;
//                                                                    takeawayflag = 0;
//                                                                    dineinflag = 0;


//                                                getCurrentLocation();
//                                                localaddress.setText(loadrr);
//                                                                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails/chefid/tables").addValueEventListener(new ValueEventListener() {
                                                                                FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(ChefIdd).child("tables").addValueEventListener(new ValueEventListener() {
                                                                                    @Override
                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                        String tableCountStr = dataSnapshot.getValue(String.class);
                                                                                        tablecheck = tableCountStr;
                                                                                        int tableCount = Integer.parseInt(tableCountStr);
                                                                                        ArrayList<String> tableList = new ArrayList<>();
                                                                                        for (int i = 1; i <= tableCount; i++) {
                                                                                            tableList.add("Table " + i);
                                                                                        }
//                                                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tableList);
//                                                                                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, tableList);
                                                                                        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, tableList);

                                                                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                                                                        tableSpinner.setAdapter(adapter);

                                                                                    }

                                                                                    @Override
                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                                        // Handle possible errors.
                                                                                    }
                                                                                });

                                                                                // Set item selected listener for spinner


                                                                                group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                                                    @Override
                                                                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                                        if (delivery.isChecked()) {
                                                                                            if (home.isChecked()) {

                                                                                                localaddress.setText(customer.getLocalAddress());
                                                                                            } else if (other.isChecked()) {
                                                                                                localaddress.setVisibility(View.VISIBLE);
                                                                                                localaddress.getText().clear();
//                                                            extradd = addressp + ", " + localaddress + " - " + customer.getSuburban() ;
                                                                                                Toast.makeText(getContext(), "Mention the address", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }
//                                                                            else {
//                                                                                localaddress.setText(customer.getLocalAddress());
//                                                                            }
                                                                                    }
                                                                                });

                                                                                if (delivery.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Delivery\n"+ "Note: ");
                                                                                    deliveryflag = 1;
                                                                                    takeawayflag = 0;
                                                                                    dineinflag = 0;
                                                                                    localaddress.setText(customer.getLocalAddress());
                                                                                    addresslayout.setVisibility(View.VISIBLE);
                                                                                    tablelayout.setVisibility(View.GONE);
                                                                                    if (home.isChecked()) {
                                                                                        localaddress.setText(customer.getLocalAddress());
                                                                                    } else if (other.isChecked()) {
                                                                                        localaddress.setVisibility(View.VISIBLE);
                                                                                        localaddress.getText().clear();
                                                                                        Toast.makeText(getContext(), "Mention the address", Toast.LENGTH_SHORT).show();
                                                                                    }
//                                                            Toast.makeText(getContext(), "Order Type Set to Delivery", Toast.LENGTH_SHORT).show();
                                                                                } else if (takeaway.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Takeaway\n"+ "Note: ");
                                                                                    takeawayflag = 1;
                                                                                    deliveryflag = 0;
                                                                                    dineinflag = 0;
                                                                                    addresslayout.setVisibility(View.GONE);
                                                                                    tablelayout.setVisibility(View.GONE);
                                                                                    localaddress.setText("Takeaway/Book/Pre-Order");
//                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
                                                                                } else if (dinein.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Takeaway\n"+ "Note: ");
                                                                                    dineinflag = 1;
                                                                                    takeawayflag = 0;
                                                                                    deliveryflag = 0;
                                                                                    addresslayout.setVisibility(View.GONE);
                                                                                    tablelayout.setVisibility(View.VISIBLE);
                                                                                    if(Objects.equals(tablecheck, "0") || tablecheck.isEmpty()){
                                                                                        localaddress.getText().clear();
                                                                                    }
                                                                                    else {
//                                                                                    localaddress.setText("Table 1");
                                                                                        localaddress.setText(tableSpinner.getSelectedItem().toString().trim());
                                                                                    }
                                                                                    tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                        @Override
                                                                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                            String selected = parent.getItemAtPosition(position).toString();
                                                                                            localaddress.setText(selected);
                                                                                        }

                                                                                        @Override
                                                                                        public void onNothingSelected(AdapterView<?> parent) {
                                                                                            // Do nothing
                                                                                        }
                                                                                    });
//                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
                                                                                }

                                                                                group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                                                    @Override
                                                                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                                                        if (delivery.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Delivery\n"+ "Note: ");
                                                                                            deliveryflag = 1;
                                                                                            takeawayflag = 0;
                                                                                            dineinflag = 0;
                                                                                            localaddress.setText(customer.getLocalAddress());
                                                                                            addresslayout.setVisibility(View.VISIBLE);
                                                                                            tablelayout.setVisibility(View.GONE);
                                                                                            if (home.isChecked()) {
                                                                                                localaddress.setText(customer.getLocalAddress());
                                                                                            } else if (other.isChecked()) {
                                                                                                localaddress.setVisibility(View.VISIBLE);
                                                                                                localaddress.getText().clear();
                                                                                                Toast.makeText(getContext(), "Mention the address", Toast.LENGTH_SHORT).show();
                                                                                            }
//                                                            Toast.makeText(getContext(), "Order Type Set to Delivery", Toast.LENGTH_SHORT).show();
                                                                                        } else if (takeaway.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Takeaway\n"+ "Note: ");
                                                                                            takeawayflag = 1;
                                                                                            deliveryflag = 0;
                                                                                            dineinflag = 0;
                                                                                            addresslayout.setVisibility(View.GONE);
                                                                                            tablelayout.setVisibility(View.GONE);
                                                                                            localaddress.setText("Takeaway/Book/Pre-Order");
//                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
                                                                                        } else if (dinein.isChecked()) {
//                                                            getCurrentLocation();
//                                                            addnote.getText().clear();
//                                                            addnote.setText("Takeaway\n"+ "Note: ");
                                                                                            dineinflag = 1;
                                                                                            takeawayflag = 0;
                                                                                            deliveryflag = 0;
                                                                                            addresslayout.setVisibility(View.GONE);
                                                                                            tablelayout.setVisibility(View.VISIBLE);
                                                                                            if(Objects.equals(tablecheck, "0") || tablecheck.isEmpty()){
                                                                                                localaddress.getText().clear();
                                                                                            }
                                                                                            else {
//                                                                                    localaddress.setText("Table 1");
                                                                                                localaddress.setText(tableSpinner.getSelectedItem().toString().trim());
                                                                                            }
                                                                                            tableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                                                                @Override
                                                                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                                                    String selected = parent.getItemAtPosition(position).toString();
                                                                                                    localaddress.setText(selected);
                                                                                                }

                                                                                                @Override
                                                                                                public void onNothingSelected(AdapterView<?> parent) {
                                                                                                    // Do nothing
                                                                                                }
                                                                                            });
//                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
                                                                                        }
//                                                                            else {
//                                                                                deliveryflag = 1;
//                                                                                takeawayflag = 0;
//                                                                                dineinflag = 0;
//                                                                            }
                                                                                    }
                                                                                });

//                                                                    if((localaddress.getText().toString().trim())!= "" || (localaddress.getText().toString().trim()) !=null){

                                                                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialog, int which) {


                                                                                        getCurrentLocation();

                                                                                        progressDialog.setMessage("Please wait...");
                                                                                        progressDialog.show();

//                                                        getCurrentLocation();
//                                                                            DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
//                                                                            cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                @Override
//                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
//
//                                                                                    if(dataSnapshotaw.exists()) {
//                                                                                        Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
//                                                                                        pauseflag = typechap16.getautoaccept();
//                                                                                    }
//                                                                                }
//                                                                                @Override
//                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                }
//                                                                            });

//                                                                            String[] parts = custordtim.split(",");
//
//// Assign values to individual variables
//                                                                             starthour = parts[0];
//                                                                             startmin = parts[1];
//                                                                             startsec = parts[2];
//                                                                             endhour = parts[3];
//                                                                             endmin = parts[4];
//                                                                             endsec = parts[5];
//
//// Optionally, you can convert the strings to integers if needed
//                                                                             starthourInt = Integer.parseInt(starthour);
//                                                                             startminInt = Integer.parseInt(startmin);
//                                                                             startsecInt = Integer.parseInt(startsec);
//                                                                             endhourInt = Integer.parseInt(endhour);
//                                                                             endminInt = Integer.parseInt(endmin);
//                                                                             endsecInt = Integer.parseInt(endsec);



                                                                                        DatabaseReference fmr = FirebaseDatabase.getInstance().getReference().child("CustomerOrderTime").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                        Date dateandtime = Calendar.getInstance().getTime();
                                                                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                                                                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
                                                                                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());

                                                                                        Date currentTime = Calendar.getInstance().getTime();

// Set the desired time range
                                                                                        Calendar startTime = Calendar.getInstance();
                                                                                        startTime.set(Calendar.HOUR_OF_DAY, starthourInt); // 11:00 PM
                                                                                        startTime.set(Calendar.MINUTE, startminInt); // 15 minutes past the hour
                                                                                        startTime.set(Calendar.SECOND, startsecInt); // 0 seconds

                                                                                        Calendar endTime = Calendar.getInstance();
                                                                                        endTime.set(Calendar.HOUR_OF_DAY, endhourInt); // 2:00 AM
                                                                                        endTime.set(Calendar.MINUTE, endminInt); // 0 minutes past the hour
                                                                                        endTime.set(Calendar.SECOND, endsecInt); // 0 seconds

//                                                                            Calendar endTimeNextDay = (Calendar) endTime.clone();
//                                                                            endTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);

                                                                                        // Assuming startTime and endTime are for the first day
//                                                                            Calendar startTimeNextDay = (Calendar) startTime.clone();
//                                                                            startTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);
//
//                                                                            Calendar endTimeNextDay = (Calendar) startTimeNextDay.clone(); // Same date as startTimeNextDay
//
//// Check if the end time is before the start time, indicating a transition to the next day
//                                                                            if (endTime.get(Calendar.HOUR_OF_DAY) < startTime.get(Calendar.HOUR_OF_DAY)) {
//                                                                                // Handle transition from PM to AM
//                                                                                endTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);
//                                                                            }
//
//                                                                            endTimeNextDay.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
//                                                                            endTimeNextDay.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
//                                                                            endTimeNextDay.set(Calendar.SECOND, endTime.get(Calendar.SECOND));

//                                                                            Calendar startTimeNextDay = (Calendar) startTime.clone();
//                                                                            startTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);
//
//                                                                            Calendar endTimeNextDay = (Calendar) endTime.clone();
//                                                                            endTimeNextDay.add(Calendar.DAY_OF_YEAR, 1);
                                                                                        Calendar beforemidnightCalendar = Calendar.getInstance();

// Set the time components to midnight
                                                                                        beforemidnightCalendar.set(Calendar.HOUR_OF_DAY, 23);
                                                                                        beforemidnightCalendar.set(Calendar.MINUTE, 59);
                                                                                        beforemidnightCalendar.set(Calendar.SECOND, 59);

                                                                                        Calendar midnightCalendar = Calendar.getInstance();

// Set the time components to midnight
                                                                                        midnightCalendar.set(Calendar.HOUR_OF_DAY, 0);
                                                                                        midnightCalendar.set(Calendar.MINUTE, 0);
                                                                                        midnightCalendar.set(Calendar.SECOND, 0);




//                                                                            if(Objects.equals(pauseflag, "1")) {
                                                                                        if(Objects.equals(custdeloption, "0") && Objects.equals(custtwoption, "0") &&  Objects.equals(custprebopt, "0")){
                                                                                            ReusableCodeForAll.ShowAlert(getContext(), "Error", "Seller has disabled all order types. Please wait for the seller to enable at least one order method to continue ordering.");
                                                                                            dialog.dismiss();
                                                                                            progressDialog.dismiss();
                                                                                            Toast.makeText(getActivity(), "Seller has disabled all order types", Toast.LENGTH_SHORT).show();

                                                                                        }

                                                                                        else {

//                                                                            if(!(localaddress.getText().toString().trim()).isEmpty() || (localaddress.getText().toString().trim())!= "" || (localaddress.getText().toString().trim()) !=null) {
                                                                                            if (!((localaddress.getText().toString().trim()).isEmpty())) {

                                                                                                String texti = localaddress.getText().toString().trim();
                                                                                                texti = texti.replace(":", "").replace(",", "");
                                                                                                localaddress.setText(texti);

                                                                                                if (cartModelList.size() > 0) {


//                                                                                        if (currentTime.after(startTime.getTime()) || currentTime.before(endTime.getTime())) {
//                                                                                            if (currentTime.compareTo(startTime.getTime()) >= 0 && currentTime.compareTo(endTime.getTime()) <= 0) {
//                                                                                        if ((currentTime.after(startTime.getTime()) && currentTime.before(endTime.getTime())) ||
//                                                                                                (currentTime.after(startTime.getTime()) && currentTime.before(endTimeNextDay.getTime()))) {

                                                                                                    if (  ( (currentTime.after(startTime.getTime()) && currentTime.before(endTime.getTime())) && (starthourInt <= endhourInt) ) ||  ( (starthourInt > endhourInt) && ( (currentTime.after(startTime.getTime()) && currentTime.before(beforemidnightCalendar.getTime())) || (currentTime.after(midnightCalendar.getTime()) && currentTime.before(endTime.getTime())) )   ) ) {

                                                                                                        // The current time is within the allowed time range for ordering.
                                                                                                        // You can proceed with the order.
//                                                                                            String date = dateFormat.format(currentTime);
//                                                                                            String time = timeFormat.format(currentTime);
                                                                                                        ordertime = dateFormat.format(dateandtime) + "   " + timeFormat.format(dateandtime);
                                                                                                        Type13 type13 = new Type13(ordertime);
                                                                                                        fmr.setValue(type13);
                                                                                                        // ... (add your order code here)


                                                                                                        if (deliveryflag == 1) {

                                                                                                            referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                                                                            ordermethod = "Delivery";
                                                                                                            Type7 type7 = new Type7(ordermethod);
                                                                                                            referenced.setValue(type7);

                                                                                                        } else if (takeawayflag == 1) {

                                                                                                            referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                                                                            ordermethod = "Takeaway/Book/Pre-Order";
                                                                                                            Type7 type7 = new Type7(ordermethod);
                                                                                                            referenced.setValue(type7);

                                                                                                        } else if (dineinflag == 1) {

                                                                                                            referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                                                                                                            ordermethod = "Dine-In";
                                                                                                            Type7 type7 = new Type7(ordermethod);
                                                                                                            referenced.setValue(type7);

                                                                                                        }


//                                                        databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders");
//                                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                                    ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
//                                                                    final String usedid = chefPendingOrders.getUserId();
//                                                                    final String RandomUIdddu = chefPendingOrders.getRandomUID();
//                                                                    useriddu = usedid;
//                                                                    RandomUIddu = RandomUIdddu;
//////.............................................................................
////
//                                                                    DatabaseReference datuu = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(useriddu).child(RandomUIddu);
//                                                                    datuu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                        @Override
//                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                            pendcount= pendcount + 1;
//
//                                                                            Type5 type5 = new Type5(pendcount);
//                                                                            dataa.setValue(type5);



                                                                                                        reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                            @Override
                                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                RandomUId = (System.currentTimeMillis() / 1000) + (UUID.randomUUID().toString());
                                                                                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                                    final Cart cart1 = dataSnapshot1.getValue(Cart.class);
                                                                                                                    DishId = cart1.getDishID();
                                                                                                                    ChefIdd = cart1.getChefId();
                                                                                                                    address = loadrr + ": " + (localaddress.getText().toString().trim()) + ", " + customer.getSuburban(); //changed
//                                                                    address = loadrr+ ": " + customer.getSuburban()  ;
                                                                                                                    Addnote = addnote.getText().toString().trim();
                                                                                                                    final HashMap<String, String> hashMap = new HashMap<>();
                                                                                                                    hashMap.put("ChefId", cart1.getChefId());
                                                                                                                    hashMap.put("DishID", cart1.getDishID());
                                                                                                                    hashMap.put("DishName", cart1.getDishName());
                                                                                                                    hashMap.put("DishQuantity", cart1.getDishQuantity());
                                                                                                                    hashMap.put("Price", cart1.getPrice());
                                                                                                                    hashMap.put("TotalPrice", cart1.getTotalprice());
//                                                                    hashMap.put("rpayid", cart1.getrpayid());
                                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("Dishes").child(DishId).setValue(hashMap);
//                                                                    annote.setText(cart1.getrpayid());

                                                                                                                }

                                                                                                                ref = FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal");
                                                                                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                    @Override
                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                        String grandtotal = dataSnapshot.getValue(String.class);
                                                                                                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                                                                                                        hashMap1.put("Address", address);
                                                                                                                        hashMap1.put("GrandTotalPrice", grandtotalop);
//                                                                        hashMap1.put("GrandTotalPrice", String.valueOf(ChefPendingOrdersAdapter.fprice));
                                                                                                                        hashMap1.put("MobileNumber", customer.getMobileno());
                                                                                                                        hashMap1.put("Name", customer.getFirstName() + " " + customer.getLastName());
                                                                                                                        hashMap1.put("Note", Addnote);
                                                                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                            @Override
                                                                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                                getRef = FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("Dishes");
                                                                                                                                                getRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                                                                                        for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                                                                                                                                            final CustomerPendingOrders customerPendingOrders = dataSnapshot2.getValue(CustomerPendingOrders.class);
                                                                                                                                                            String d = customerPendingOrders.getDishID();
                                                                                                                                                            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                                                                                                            ChefId = customerPendingOrders.getChefId();
                                                                                                                                                            final HashMap<String, String> hashMap2 = new HashMap<>();
                                                                                                                                                            hashMap2.put("ChefId", ChefId);
                                                                                                                                                            hashMap2.put("DishId", customerPendingOrders.getDishID());
                                                                                                                                                            hashMap2.put("DishName", customerPendingOrders.getDishName());
                                                                                                                                                            hashMap2.put("DishQuantity", customerPendingOrders.getDishQuantity());
                                                                                                                                                            hashMap2.put("Price", customerPendingOrders.getPrice());
                                                                                                                                                            hashMap2.put("RandomUID", RandomUId);
                                                                                                                                                            hashMap2.put("TotalPrice", customerPendingOrders.getTotalPrice());
                                                                                                                                                            hashMap2.put("UserId", userid);
//                                                                                                            hashMap2.put("rpayid", customerPendingOrders.getrpayid());

                                                                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("Dishes").child(d).setValue(hashMap2);

                                                                                                                                                        }
                                                                                                                                                        dataa = FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("OtherInformation");
                                                                                                                                                        dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                            @Override
                                                                                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                                CustomerPendingOrders1 customerPendingOrders1 = dataSnapshot.getValue(CustomerPendingOrders1.class);
                                                                                                                                                                HashMap<String, String> hashMap3 = new HashMap<>();
                                                                                                                                                                hashMap3.put("Address", customerPendingOrders1.getAddress());
                                                                                                                                                                hashMap3.put("GrandTotalPrice", customerPendingOrders1.getGrandTotalPrice());
                                                                                                                                                                hashMap3.put("MobileNumber", customerPendingOrders1.getMobileNumber());
                                                                                                                                                                hashMap3.put("Name", customerPendingOrders1.getName());
                                                                                                                                                                hashMap3.put("Note", customerPendingOrders1.getNote());
                                                                                                                                                                hashMap3.put("RandomUID", RandomUId);

                                                                                                                                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("OtherInformation").setValue(hashMap3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                    @Override
                                                                                                                                                                    public void onSuccess(Void aVoid) {

                                                                                                                                                                        FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("isOrdered").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                                            @Override
                                                                                                                                                                            public void onSuccess(Void aVoid) {

                                                                                                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(ChefId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
//                                                                                                                                        sendNotifications(usertoken, "New Order", "You have a new Order", "Order");
//                                                                                                                                        showNotification(usertoken, "New Order", "You have a new Order", "Order");
//                                                                                                                                        sendNotificationsp(usertoken, "New Order", "You have a new Order", "Order");
//                                                                                                                                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(usertoken,"New Order", "You have a new Order", getActivity().getApplicationContext(), getActivity());
//                                                                                                                                        notificationsSender.SendNotifications();

//                                                                                                                                        FCMSend.pushNotification(
//                                                                                                                                                getContext(),
//                                                                                                                                                usertoken,
//                                                                                                                                                "New Order",
//                                                                                                                                                "You have a new order"
//
//                                                                                                                                        );
                                                                                                                                                                                        FirebaseDatabase.getInstance().getReference().child("Chef").child(ChefId).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                                            @Override
                                                                                                                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                                                                String chefmobph = dataSnapshot.getValue(String.class);

                                                                                                                                                                                                notifMessage.setText("New Order : You have a New Order");
                                                                                                                                                                                                notifNumber.setText("+91" + chefmobph);

                                                                                                                                                                                                if (!notifMessage.getText().toString().isEmpty() && (!notifNumber.getText().toString().isEmpty())) {
                                                                                                                                                                                                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new Callback() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                                                                                                                                                                                        }

                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                                                                                                                                                                                            getActivity().runOnUiThread(new Runnable() {
                                                                                                                                                                                                                @Override
                                                                                                                                                                                                                public void run() {
                                                                                                                                                                                                                    if (response.code() == 200) {
                                                                                                                                                                                                                        Toast.makeText(getActivity(), "Notification sent", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                                                        progressDialog.dismiss();
                                                                                                                                                                                                                        ReusableCodeForAll.ShowAlert(getContext(), "", "Your Order has been shifted to Pending state, please wait until the seller accept your order.");
                                                                                                                                                                                                                        hideKeyboard(getActivity());
                                                                                                                                                                                                                    }
                                                                                                                                                                                                                }
                                                                                                                                                                                                            });
                                                                                                                                                                                                        }
                                                                                                                                                                                                    });
                                                                                                                                                                                                }


//                                                                                                                                                                                    progressDialog.dismiss();
//                                                                                                                                                                                    ReusableCodeForAll.ShowAlert(getContext(), "", "Your Order has been shifted to Pending state, please wait until the seller accept your order.");
                                                                                                                                                                                            }

                                                                                                                                                                                            @Override
                                                                                                                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                                                            }
                                                                                                                                                                                        });
                                                                                                                                                                                    }

                                                                                                                                                                                    @Override
                                                                                                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                                                    }
                                                                                                                                                                                });

                                                                                                                                                                            }
                                                                                                                                                                        });


                                                                                                                                                                    }


                                                                                                                                                                });
                                                                                                                                                            }

                                                                                                                                                            @Override
                                                                                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                            }
                                                                                                                                                        });
//                                                                                                            }
                                                                                                                                                    }

                                                                                                                                                    @Override
                                                                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                    }
                                                                                                                                                });
                                                                                                                                            }
                                                                                                                                        });
                                                                                                                                    }
                                                                                                                                });
                                                                                                                            }
                                                                                                                        });
                                                                                                                    }

                                                                                                                    @Override
                                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                    }
                                                                                                                });
//                                                                }
                                                                                                            }

                                                                                                            @Override
                                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                            }
                                                                                                        });

                                                                                                        dialog.dismiss();
                                                                                                    } else {
//                                                                                        ReusableCodeForAll.ShowAlert(getContext(), "Error", "You can order only between 11:15 PM and 2:00 AM.");
                                                                                                        dialog.dismiss();
                                                                                                        progressDialog.dismiss();
                                                                                                        Toast.makeText(getActivity(), "You can order only between " + starthour + ":" + startmin + " and " + endhour + ":" + endmin, Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                } else {
                                                                                                    ReusableCodeForAll.ShowAlert(getContext(), "Error", "There should be at least 1 item");
                                                                                                    dialog.dismiss();
                                                                                                    progressDialog.dismiss();
                                                                                                    Toast.makeText(getActivity(), "There should be at least 1 item", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                ReusableCodeForAll.ShowAlert(getContext(), "Error", "Address is required");
                                                                                                dialog.dismiss();
                                                                                                progressDialog.dismiss();
                                                                                                Toast.makeText(getActivity(), "Address is required", Toast.LENGTH_SHORT).show();
                                                                                            }

                                                                                            progressDialog.dismiss();

                                                                                        }


                                                                                        progressDialog.dismiss();


//                                                                            }
//                                                                            else {
//                                                                                dialog.dismiss();
//                                                                                progressDialog.dismiss();
//                                                                                ReusableCodeForAll.ShowAlert(getContext(), "Paused!", "Seller has paused accepting more orders due to high demand, please try again after some time.");
//                                                                                Toast.makeText(getActivity(), "Paused", Toast.LENGTH_SHORT).show();
//                                                                            }
                                                                                    }
                                                                                });
                                                                                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                                                    @Override
                                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                                        dialog.dismiss();
                                                                                    }
                                                                                });
                                                                                AlertDialog aler = builder.create();
                                                                                aler.show();

                                                                            }
                                                                            else {
                                                                                ReusableCodeForAll.ShowAlert(getContext(), "Error", "It seems you have already placed the order, So you cannot place another order until the delivery of first order");
                                                                            }
//                                                            }
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                        }
//                                                    });
//                                                            }
//                                                            else {
//                                                                ReusableCodeForAll.ShowAlert(getContext(), "Error", "You can add only upto 5 items per delivery");
//                                                            }
                                                                        }
                                                                        else {
                                                                            progressDialog.setMessage("Please wait...");
                                                                            progressDialog.show();
                                                                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    progressDialog.dismiss();
                                                                                }
                                                                            }, 3000); // 5000 milliseconds (5 seconds)
                                                                            ReusableCodeForAll.ShowAlert(getContext(), "Error", "Order must be above Rs." + custminordval );

                                                                        }
                                                                    }
                                                                    else {
                                                                        progressDialog.setMessage("Please wait...");
                                                                        progressDialog.show();
                                                                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                progressDialog.dismiss();
                                                                            }
                                                                        }, 3000); // 5000 milliseconds (5 seconds)
                                                                        ReusableCodeForAll.ShowAlert(getContext(), "Paused!", "Seller has paused accepting the orders!");

                                                                    }

                                                                }
                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        Toast.makeText(getContext(), "Error checking group status", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            });

//                        }
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });












//                            instacc.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    getCurrentLocation();
//
////                                    DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
////                                    cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
////                                        @Override
////                                        public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
////
////                                            if(dataSnapshotaw.exists()) {
////                                                Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
////                                                pauseflag = typechap16.getautoaccept();
////                                            }
////                                        }
////                                        @Override
////                                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                        }
////                                    });
//
//
//                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("isOrdered").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                                            turnOnGPS();
////                                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//                                            DatabaseReference gref = FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal");
//                                            gref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshoty) {
//                                                    String grandtotalop = dataSnapshoty.getValue(String.class);
//
//
////                                                        }
////                                                        @Override
////                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                                        }
////                                                    });
//
//
//                                                    String ss = "";
//                                                    if (dataSnapshot.exists()) {
//                                                        ss = dataSnapshot.getValue(String.class);
//                                                    }
//
//
////                                                    if (Objects.equals(pauseflag, "1")) {
//
//                                                        if (Integer.parseInt(grandtotalop) >= 50) {
//
//                                                            if (cartModelList.size() <= 5 && cartModelList.size() >= 1) {
//
//
//
//                                                                if (ss.trim().equalsIgnoreCase("false") || ss.trim().equalsIgnoreCase("")) {
//
//                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                                                                    builder.setTitle("Enter Address");
//                                                                    LayoutInflater inflater = getActivity().getLayoutInflater();
//                                                                    View view = inflater.inflate(R.layout.enter_address, null);
//                                                                    final EditText localaddress = (EditText) view.findViewById(R.id.LA);
//                                                                    final EditText addnote = (EditText) view.findViewById(R.id.addnote);
//                                                                    RadioGroup group = (RadioGroup) view.findViewById(R.id.grp);
//                                                                    RadioGroup group2 = (RadioGroup) view.findViewById(R.id.grp2);
//                                                                    final RadioButton home = (RadioButton) view.findViewById(R.id.HA);
//                                                                    final RadioButton other = (RadioButton) view.findViewById(R.id.OA);
//                                                                    final RadioButton delivery = (RadioButton) view.findViewById(R.id.delivery);
//                                                                    final RadioButton dinein = (RadioButton) view.findViewById(R.id.dinein);
//                                                                    final RadioButton takeaway = (RadioButton) view.findViewById(R.id.takeaway);
//                                                                    builder.setView(view);
//                                                                    localaddress.setText(customer.getLocalAddress());
//                                                                    deliveryflag = 1;
//                                                                    takeawayflag = 0;
//                                                                    dineinflag = 0;
//
//
////                                                getCurrentLocation();
////                                                localaddress.setText(loadrr);
//
//
//                                                                    group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                                                        @Override
//                                                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                                            if (home.isChecked()) {
//
//                                                                                localaddress.setText(customer.getLocalAddress());
//                                                                            } else if (other.isChecked()) {
//                                                                                localaddress.setVisibility(View.VISIBLE);
//                                                                                localaddress.getText().clear();
////                                                            extradd = addressp + ", " + localaddress + " - " + customer.getSuburban() ;
//                                                                                Toast.makeText(getContext(), "Mention the address", Toast.LENGTH_SHORT).show();
//                                                                            }
////                                                                            else {
////                                                                                localaddress.setText(customer.getLocalAddress());
////                                                                            }
//                                                                        }
//                                                                    });
//
//                                                                    group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                                                        @Override
//                                                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                                                            if (delivery.isChecked()) {
////                                                            getCurrentLocation();
////                                                            addnote.getText().clear();
////                                                            addnote.setText("Delivery\n"+ "Note: ");
//                                                                                deliveryflag = 1;
//                                                                                takeawayflag = 0;
//                                                                                dineinflag = 0;
////                                                            Toast.makeText(getContext(), "Order Type Set to Delivery", Toast.LENGTH_SHORT).show();
//                                                                            } else if (takeaway.isChecked()) {
////                                                            getCurrentLocation();
////                                                            addnote.getText().clear();
////                                                            addnote.setText("Takeaway\n"+ "Note: ");
//                                                                                takeawayflag = 1;
//                                                                                deliveryflag = 0;
//                                                                                dineinflag = 0;
////                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
//                                                                            } else if (dinein.isChecked()) {
////                                                            getCurrentLocation();
////                                                            addnote.getText().clear();
////                                                            addnote.setText("Takeaway\n"+ "Note: ");
//                                                                                dineinflag = 1;
//                                                                                takeawayflag = 0;
//                                                                                deliveryflag = 0;
////                                                            Toast.makeText(getContext(), "Order Type Set to Takeaway/Pre-Book", Toast.LENGTH_SHORT).show();
//                                                                            }
////                                                                            else {
////                                                                                deliveryflag = 1;
////                                                                                takeawayflag = 0;
////                                                                                dineinflag = 0;
////                                                                            }
//                                                                        }
//                                                                    });
//
////                                                                    if((localaddress.getText().toString().trim())!= "" || (localaddress.getText().toString().trim()) !=null){
//
//                                                                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//
//
//                                                                            getCurrentLocation();
//
//                                                                            progressDialog.setMessage("Please wait...");
//                                                                            progressDialog.show();
//
////                                                        getCurrentLocation();
////                                                                            DatabaseReference cddbref = FirebaseDatabase.getInstance().getReference("ChefAutoAccept").child(ChefIdd);
////                                                                            cddbref.addListenerForSingleValueEvent(new ValueEventListener() {
////                                                                                @Override
////                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshotaw) {
////
////                                                                                    if(dataSnapshotaw.exists()) {
////                                                                                        Type16 typechap16 = dataSnapshotaw.getValue(Type16.class);
////                                                                                        pauseflag = typechap16.getautoaccept();
////                                                                                    }
////                                                                                }
////                                                                                @Override
////                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                                                                }
////                                                                            });
//
//
//                                                                            DatabaseReference fmr = FirebaseDatabase.getInstance().getReference().child("CustomerOrderTime").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                                            Date dateandtime = Calendar.getInstance().getTime();
//                                                                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                                                                            SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
//
//                                                                            Date currentTime = Calendar.getInstance().getTime();
//
//// Set the desired time range
//                                                                            Calendar startTime = Calendar.getInstance();
//                                                                            startTime.set(Calendar.HOUR_OF_DAY, 23); // 11:00 PM
//                                                                            startTime.set(Calendar.MINUTE, 15); // 15 minutes past the hour
//                                                                            startTime.set(Calendar.SECOND, 0); // 0 seconds
////                                                            startTime.set(Calendar.HOUR_OF_DAY, 12); // 4:00 PM
////                                                            startTime.set(Calendar.MINUTE, 25); // 52 minutes past the hour
////                                                            startTime.set(Calendar.SECOND, 0); // 0 seconds
//
//                                                                            Calendar endTime = Calendar.getInstance();
//                                                                            endTime.set(Calendar.HOUR_OF_DAY, 2); // 2:00 AM
//                                                                            endTime.set(Calendar.MINUTE, 0); // 0 minutes past the hour
//                                                                            endTime.set(Calendar.SECOND, 0); // 0 seconds
////                                                            try {
////                                                                Date date1 = timeFormat.parse("11:14:00 pm");
////                                                            } catch (ParseException e) {
////                                                                e.printStackTrace();
////                                                            }
////                                                            try {
////                                                                Date date2 = timeFormat.parse("02:00:00 am");
////                                                            } catch (ParseException e) {
////                                                                e.printStackTrace();
////                                                            }
//
////                                                                            if(Objects.equals(pauseflag, "1")) {
//
////                                                                            if(!(localaddress.getText().toString().trim()).isEmpty() || (localaddress.getText().toString().trim())!= "" || (localaddress.getText().toString().trim()) !=null) {
//                                                                            if(!((localaddress.getText().toString().trim()).isEmpty())) {
//
//
//                                                                                if (currentTime.after(startTime.getTime()) || currentTime.before(endTime.getTime())) {
//                                                                                    // The current time is within the allowed time range for ordering.
//                                                                                    // You can proceed with the order.
//                                                                                    String date = dateFormat.format(currentTime);
//                                                                                    String time = timeFormat.format(currentTime);
//                                                                                    ordertime = dateFormat.format(dateandtime) + "   " + timeFormat.format(dateandtime);
//                                                                                    Type13 type13 = new Type13(ordertime);
//                                                                                    fmr.setValue(type13);
//                                                                                    // ... (add your order code here)
//
//
//                                                                                    if (deliveryflag == 1) {
//
//                                                                                        referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                                                                                        ordermethod = "Delivery";
//                                                                                        Type7 type7 = new Type7(ordermethod);
//                                                                                        referenced.setValue(type7);
//
//                                                                                    } else if (takeawayflag == 1) {
//
//                                                                                        referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                                                                                        ordermethod = "Takeaway/Book/Pre-Order";
//                                                                                        Type7 type7 = new Type7(ordermethod);
//                                                                                        referenced.setValue(type7);
//
//                                                                                    } else if (dineinflag == 1) {
//
//                                                                                        referenced = FirebaseDatabase.getInstance().getReference().child("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//                                                                                        ordermethod = "Dine-In";
//                                                                                        Type7 type7 = new Type7(ordermethod);
//                                                                                        referenced.setValue(type7);
//
//                                                                                    }
//
//
////                                                        databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders");
////                                                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
////                                                            @Override
////                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                                                                    ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
////                                                                    final String usedid = chefPendingOrders.getUserId();
////                                                                    final String RandomUIdddu = chefPendingOrders.getRandomUID();
////                                                                    useriddu = usedid;
////                                                                    RandomUIddu = RandomUIdddu;
////////.............................................................................
//////
////                                                                    DatabaseReference datuu = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(useriddu).child(RandomUIddu);
////                                                                    datuu.addListenerForSingleValueEvent(new ValueEventListener() {
////                                                                        @Override
////                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////                                                                            pendcount= pendcount + 1;
////
////                                                                            Type5 type5 = new Type5(pendcount);
////                                                                            dataa.setValue(type5);
//
//
//                                                                                    reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                        @Override
//                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                            RandomUId = (System.currentTimeMillis() / 1000) + (UUID.randomUUID().toString());
//                                                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                                                                                final Cart cart1 = dataSnapshot1.getValue(Cart.class);
//                                                                                                DishId = cart1.getDishID();
//                                                                                                ChefIdd = cart1.getChefId();
//                                                                                                address = loadrr + ": " + (localaddress.getText().toString().trim()) + ", " + customer.getSuburban(); //changed
////                                                                    address = loadrr+ ": " + customer.getSuburban()  ;
//                                                                                                Addnote = addnote.getText().toString().trim();
//                                                                                                final HashMap<String, String> hashMap = new HashMap<>();
//                                                                                                hashMap.put("ChefId", cart1.getChefId());
//                                                                                                hashMap.put("DishID", cart1.getDishID());
//                                                                                                hashMap.put("DishName", cart1.getDishName());
//                                                                                                hashMap.put("DishQuantity", cart1.getDishQuantity());
//                                                                                                hashMap.put("Price", cart1.getPrice());
//                                                                                                hashMap.put("TotalPrice", cart1.getTotalprice());
////                                                                    hashMap.put("rpayid", cart1.getrpayid());
//                                                                                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("Dishes").child(DishId).setValue(hashMap);
////                                                                    annote.setText(cart1.getrpayid());
//
//                                                                                            }
//
//                                                                                            ref = FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GrandTotal");
//                                                                                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                @Override
//                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                    String grandtotal = dataSnapshot.getValue(String.class);
//                                                                                                    HashMap<String, String> hashMap1 = new HashMap<>();
//                                                                                                    hashMap1.put("Address", address);
//                                                                                                    hashMap1.put("GrandTotalPrice", String.valueOf(grandtotal));
////                                                                        hashMap1.put("GrandTotalPrice", String.valueOf(ChefPendingOrdersAdapter.fprice));
//                                                                                                    hashMap1.put("MobileNumber", customer.getMobileno());
//                                                                                                    hashMap1.put("Name", customer.getFirstName() + " " + customer.getLastName());
//                                                                                                    hashMap1.put("Note", Addnote);
//                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                        @Override
//                                                                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                            FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                @Override
//                                                                                                                public void onComplete(@NonNull Task<Void> task) {
//                                                                                                                    FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                        @Override
//                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                            getRef = FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("Dishes");
//                                                                                                                            getRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                @Override
//                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                                                                                    for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
//                                                                                                                                        final CustomerPendingOrders customerPendingOrders = dataSnapshot2.getValue(CustomerPendingOrders.class);
//                                                                                                                                        String d = customerPendingOrders.getDishID();
//                                                                                                                                        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                                                                                                                        ChefId = customerPendingOrders.getChefId();
//                                                                                                                                        final HashMap<String, String> hashMap2 = new HashMap<>();
//                                                                                                                                        hashMap2.put("ChefId", ChefId);
//                                                                                                                                        hashMap2.put("DishId", customerPendingOrders.getDishID());
//                                                                                                                                        hashMap2.put("DishName", customerPendingOrders.getDishName());
//                                                                                                                                        hashMap2.put("DishQuantity", customerPendingOrders.getDishQuantity());
//                                                                                                                                        hashMap2.put("Price", customerPendingOrders.getPrice());
//                                                                                                                                        hashMap2.put("RandomUID", RandomUId);
//                                                                                                                                        hashMap2.put("TotalPrice", customerPendingOrders.getTotalPrice());
//                                                                                                                                        hashMap2.put("UserId", userid);
////                                                                                                            hashMap2.put("rpayid", customerPendingOrders.getrpayid());
//
//                                                                                                                                        FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("Dishes").child(d).setValue(hashMap2);
//
//                                                                                                                                    }
//                                                                                                                                    dataa = FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId).child("OtherInformation");
//                                                                                                                                    dataa.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                        @Override
//                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                            CustomerPendingOrders1 customerPendingOrders1 = dataSnapshot.getValue(CustomerPendingOrders1.class);
//                                                                                                                                            HashMap<String, String> hashMap3 = new HashMap<>();
//                                                                                                                                            hashMap3.put("Address", customerPendingOrders1.getAddress());
//                                                                                                                                            hashMap3.put("GrandTotalPrice", customerPendingOrders1.getGrandTotalPrice());
//                                                                                                                                            hashMap3.put("MobileNumber", customerPendingOrders1.getMobileNumber());
//                                                                                                                                            hashMap3.put("Name", customerPendingOrders1.getName());
//                                                                                                                                            hashMap3.put("Note", customerPendingOrders1.getNote());
//                                                                                                                                            hashMap3.put("RandomUID", RandomUId);
//
//                                                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("OtherInformation").setValue(hashMap3).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                                                                                @Override
//                                                                                                                                                public void onSuccess(Void aVoid) {
//
//                                                                                                                                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("isOrdered").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                                                                                        @Override
//                                                                                                                                                        public void onSuccess(Void aVoid) {
//
//                                                                                                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(ChefId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                @Override
//                                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                    String usertoken = dataSnapshot.getValue(String.class);
////                                                                                                                                        sendNotifications(usertoken, "New Order", "You have a new Order", "Order");
////                                                                                                                                        showNotification(usertoken, "New Order", "You have a new Order", "Order");
////                                                                                                                                        sendNotificationsp(usertoken, "New Order", "You have a new Order", "Order");
////                                                                                                                                        FcmNotificationsSender notificationsSender = new FcmNotificationsSender(usertoken,"New Order", "You have a new Order", getActivity().getApplicationContext(), getActivity());
////                                                                                                                                        notificationsSender.SendNotifications();
//
////                                                                                                                                        FCMSend.pushNotification(
////                                                                                                                                                getContext(),
////                                                                                                                                                usertoken,
////                                                                                                                                                "New Order",
////                                                                                                                                                "You have a new order"
////
////                                                                                                                                        );
//                                                                                                                                                                    FirebaseDatabase.getInstance().getReference().child("Chef").child(ChefId).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                        @Override
//                                                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                            String chefmobph = dataSnapshot.getValue(String.class);
//
//
//
//                                                                                                                                                                            DatabaseReference databaseReferencezud = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("Dishes");
//                                                                                                                                                                            databaseReferencezud.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                @Override
//                                                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                                                                                                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                                                                                                                                                        final ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
//                                                                                                                                                                                        HashMap<String, String> hashMap = new HashMap<>();
//                                                                                                                                                                                        String chefid = chefPendingOrders.getChefId();
//                                                                                                                                                                                        String dishid = chefPendingOrders.getDishId();
//                                                                                                                                                                                        hashMap.put("ChefId", chefPendingOrders.getChefId());
//                                                                                                                                                                                        hashMap.put("DishId", chefPendingOrders.getDishId());
//                                                                                                                                                                                        hashMap.put("DishName", chefPendingOrders.getDishName());
//                                                                                                                                                                                        hashMap.put("DishPrice", chefPendingOrders.getPrice());
//                                                                                                                                                                                        hashMap.put("DishQuantity", chefPendingOrders.getDishQuantity());
//                                                                                                                                                                                        hashMap.put("RandomUID", RandomUId);
//                                                                                                                                                                                        hashMap.put("TotalPrice", chefPendingOrders.getTotalPrice());
//                                                                                                                                                                                        hashMap.put("UserId", chefPendingOrders.getUserId());
//                                                                                                                                                                                        FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(chefid).child(RandomUId).child("Dishes").child(dishid).setValue(hashMap);
//                                                                                                                                                                                    }
//                                                                                                                                                                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(ChefId).child(RandomUId).child("OtherInformation");
//                                                                                                                                                                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                        @Override
//                                                                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                                            ChefPendingOrders1 chefPendingOrders1 = dataSnapshot.getValue(ChefPendingOrders1.class);
//                                                                                                                                                                                            HashMap<String, String> hashMap1 = new HashMap<>();
//                                                                                                                                                                                            hashMap1.put("Address", chefPendingOrders1.getAddress() + " , " + ordertypeu );
////                                hashMap1.put("GrandTotalPrice", chefPendingOrders1.getGrandTotalPrice());
//                                                                                                                                                                                            hashMap1.put("GrandTotalPrice", fprice);
//                                                                                                                                                                                            hashMap1.put("MobileNumber", chefPendingOrders1.getMobileNumber());
//                                                                                                                                                                                            hashMap1.put("Name", chefPendingOrders1.getName());
//                                                                                                                                                                                            hashMap1.put("Note", chefPendingOrders1.getNote());
//                                                                                                                                                                                            hashMap1.put("RandomUID", RandomUId);
//                                                                                                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                                                                                                @Override
//                                                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                                                                                                    DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes");
//                                                                                                                                                                                                    Reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                                                                                                                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                                                                                                                                                                                final ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
//                                                                                                                                                                                                                HashMap<String, String> hashMap2 = new HashMap<>();
//                                                                                                                                                                                                                userid = chefPendingOrders.getUserId();
//                                                                                                                                                                                                                dishid = chefPendingOrders.getDishId();
//                                                                                                                                                                                                                hashMap2.put("ChefId", chefPendingOrders.getChefId());
//                                                                                                                                                                                                                hashMap2.put("DishId", chefPendingOrders.getDishId());
//                                                                                                                                                                                                                hashMap2.put("DishName", chefPendingOrders.getDishName());
//                                                                                                                                                                                                                hashMap2.put("DishPrice", chefPendingOrders.getPrice());
//                                                                                                                                                                                                                hashMap2.put("DishQuantity", chefPendingOrders.getDishQuantity());
//                                                                                                                                                                                                                hashMap2.put("RandomUID", RandomUId);
//                                                                                                                                                                                                                hashMap2.put("TotalPrice", chefPendingOrders.getTotalPrice());
//                                                                                                                                                                                                                hashMap2.put("UserId", chefPendingOrders.getUserId());
//                                                                                                                                                                                                                FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(random).child("Dishes").child(dishid).setValue(hashMap2);
//                                                                                                                                                                                                            }
//                                                                                                                                                                                                            DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation");
//                                                                                                                                                                                                            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                                                                    ChefPendingOrders1 chefPendingOrders1 = dataSnapshot.getValue(ChefPendingOrders1.class);
//                                                                                                                                                                                                                    HashMap<String, String> hashMap3 = new HashMap<>();
//                                                                                                                                                                                                                    hashMap3.put("Address", chefPendingOrders1.getAddress() + " , " + ordertypeu);
////                                                        hashMap3.put("GrandTotalPrice", chefPendingOrders1.getGrandTotalPrice());
//                                                                                                                                                                                                                    hashMap3.put("GrandTotalPrice", fprice);
//                                                                                                                                                                                                                    hashMap3.put("MobileNumber", chefPendingOrders1.getMobileNumber());
//                                                                                                                                                                                                                    hashMap3.put("Name", chefPendingOrders1.getName());
//                                                                                                                                                                                                                    hashMap3.put("Note", chefPendingOrders1.getNote());
//                                                                                                                                                                                                                    hashMap3.put("RandomUID", random);
//                                                                                                                                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(random).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                                                                                                                            FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                                                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(random).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                                                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                                                                                                                                                                                                                    FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                                                                        public void onSuccess(Void aVoid) {
//                                                                                                                                                                                                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                                                                                                                    String usertoken = dataSnapshot.getValue(String.class);
////                                                                                                        if(chefmessage.length()!=0){
////                                                                                                            sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order." + "\n" + "\n" + "Message from Seller:" + "\n" + chefmessage, "Payment");
////                                                                                                        }
////                                                                                                        else{
////                                                                                                            sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order.", "Payment");
////                                                                                                        }
////                                                                                                        sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order." + "\n" + "\n" + "Message from Seller:" + "\n" + chefmessage, "Payment");
////                                                                                                        FCMSend.pushNotification(
////                                                                                                                context,
////                                                                                                                usertoken,
////                                                                                                                "Order Accepted",
////                                                                                                                "Your Order has been Accepted by the Seller. Now make Payment for Order."
////
////                                                                                                        );
//                                                                                                                                                                                                                                                                    FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                                                                                                                                            String usermobph = dataSnapshot.getValue(String.class);
//
////                                                                                                                String chefmessage = holder.;
//
//                                                                                                                                                                                                                                                                            String chefmessageiiiiyyyy = holder.chefmessage.getText().toString().trim();
//
//                                                                                                                                                                                                                                                                            if(chefmessageiiiiyyyy.isEmpty()){
//                                                                                                                                                                                                                                                                                holder.notifMessage.setText("Order Accepted : Your Order has been Accepted by the seller. Now make Payment for Order.");
//                                                                                                                                                                                                                                                                            }
//                                                                                                                                                                                                                                                                            else {
//                                                                                                                                                                                                                                                                                holder.notifMessage.setText("Order Accepted : Now make Payment for Order." + "\n" + "Note from Seller: " + chefmessageiiiiyyyy);
//                                                                                                                                                                                                                                                                            }
//
////                                                                                                        holder.notifMessage.setText("Order Accepted : Your Order has been Accepted. Now make Payment for Order.");
////                                                                                                        holder.notifNumber.setText("+91" + usermob);
//                                                                                                                                                                                                                                                                            holder.notifNumber.setText("+91" + usermobph);
////
//                                                                                                                                                                                                                                                                            if (!holder.notifMessage.getText().toString().isEmpty()&&(!holder.notifNumber.getText().toString().isEmpty())) {
//                                                                                                                                                                                                                                                                                new FCMSender().send(String.format(NotificationMessage.message, "messaging", holder.notifMessage.getText().toString(), holder.notifNumber.getText().toString()), new okhttp3.Callback() {
//                                                                                                                                                                                                                                                                                    @Override
//                                                                                                                                                                                                                                                                                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//
//
//                                                                                                                                                                                                                                                                                    }
//
//                                                                                                                                                                                                                                                                                    @Override
//                                                                                                                                                                                                                                                                                    public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @com.google.firebase.database.annotations.NotNull IOException e) {
//
//                                                                                                                                                                                                                                                                                    }
//
//                                                                                                                                                                                                                                                                                });
//                                                                                                                                                                                                                                                                            }
//
//
//
////
////
////
//
//
//                                                                                                                                                                                                                                                                            progressDialog.dismiss();
////                                                                                                        ReusableCodeForAll.ShowAlert(context, "", "Delivery Charge of Rs." + fnumber + " has been added to customer's Total Price. Wait for the Customer to make Payment, Pull down the page to Refresh!");
////                                                                                                        progressDialog.dismiss();
//
//                                                                                                                                                                                                                                                                        }
//
//
//                                                                                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                                    });
//
//                                                                                                                                                                                                                                                                }
//
//
//                                                                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                                            });
//                                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                                    });
//                                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                                            });
//
//                                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                                    });
//                                                                                                                                                                                                                                }
//                                                                                                                                                                                                                            });
//                                                                                                                                                                                                                        }
//                                                                                                                                                                                                                    });
//
//                                                                                                                                                                                                                }
//
//
//                                                                                                                                                                                                                @Override
//                                                                                                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                                                }
//                                                                                                                                                                                                            });
//
//                                                                                                                                                                                                        }
//
//                                                                                                                                                                                                        @Override
//                                                                                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                                        }
//                                                                                                                                                                                                    });
//                                                                                                                                                                                                }
//                                                                                                                                                                                            });
//
//
//                                                                                                                                                                                        }
//
//                                                                                                                                                                                        @Override
//                                                                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                        }
//                                                                                                                                                                                    });
//
//
//                                                                                                                                                                                }
//
//
//                                                                                                                                                                                @Override
//                                                                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                                }
//                                                                                                                                                                            });
//
//                                                                                                                                                                            //................................................................
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//                                                                                                                                                                            progressDialog.dismiss();
//                                                                                                                                                                            ReusableCodeForAll.ShowAlert(getContext(), "Order Accepted", "Your Order has been accepted. Now make payment for your order.");
//                                                                                                                                                                        }
//
//                                                                                                                                                                        @Override
//                                                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                        }
//                                                                                                                                                                    });
//                                                                                                                                                                }
//
//                                                                                                                                                                @Override
//                                                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                                }
//                                                                                                                                                            });
//
//                                                                                                                                                        }
//                                                                                                                                                    });
//
//
//                                                                                                                                                }
//
//
//                                                                                                                                            });
//                                                                                                                                        }
//
//                                                                                                                                        @Override
//                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                        }
//                                                                                                                                    });
////                                                                                                            }
//                                                                                                                                }
//
//                                                                                                                                @Override
//                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                }
//                                                                                                                            });
//                                                                                                                        }
//                                                                                                                    });
//                                                                                                                }
//                                                                                                            });
//                                                                                                        }
//                                                                                                    });
//                                                                                                }
//
//                                                                                                @Override
//                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                }
//                                                                                            });
////                                                                }
//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                        }
//                                                                                    });
//
//                                                                                    dialog.dismiss();
//                                                                                }
//                                                                                else {
//                                                                                    dialog.dismiss();
//                                                                                    progressDialog.dismiss();
//                                                                                    // The current time is outside the allowed time range for ordering.
//                                                                                    // Display a message to the user or take appropriate action.
//                                                                                    // For example:
//                                                                                    Toast.makeText(getActivity(), "You can order only between 11:15 PM and 2:00 AM.", Toast.LENGTH_SHORT).show();
////                                                             Toast.makeText(this, "Ordering is only allowed between 11:15 PM and 2:00 AM.", Toast.LENGTH_SHORT).show();
//                                                                                }
//                                                                            }
//                                                                            else {
//                                                                                dialog.dismiss();
//                                                                                progressDialog.dismiss();
//                                                                                Toast.makeText(getActivity(), "Address is required", Toast.LENGTH_SHORT).show();
//                                                                            }
////                                                                            }
////                                                                            else {
////                                                                                dialog.dismiss();
////                                                                                progressDialog.dismiss();
////                                                                                ReusableCodeForAll.ShowAlert(getContext(), "Paused!", "Seller has paused accepting more orders due to high demand, please try again after some time.");
////                                                                                Toast.makeText(getActivity(), "Paused", Toast.LENGTH_SHORT).show();
////                                                                            }
//                                                                        }
//                                                                    });
//                                                                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                                                        @Override
//                                                                        public void onClick(DialogInterface dialog, int which) {
//                                                                            dialog.dismiss();
//                                                                        }
//                                                                    });
//                                                                    AlertDialog aler = builder.create();
//                                                                    aler.show();
//
//                                                                }
//                                                                else {
//                                                                    ReusableCodeForAll.ShowAlert(getContext(), "Error", "It seems you have already placed the order, So you cannot place another order until the delivery of first order");
//                                                                }
////                                                            }
////                                                        @Override
////                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                                        }
////                                                    });
//                                                            }
//                                                            else {
//                                                                ReusableCodeForAll.ShowAlert(getContext(), "Error", "You can add only upto 5 items per delivery");
//                                                            }
//                                                        } else {
//                                                            ReusableCodeForAll.ShowAlert(getContext(), "Error", "Order must be above Rs.50");
//                                                        }
////                                                    }
////                                                    else {
////                                                        ReusableCodeForAll.ShowAlert(getContext(), "Paused!", "Seller has paused accepting orders due to high demand!");
////                                                    }
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//
////                                }
//
////                                }
////                                @Override
////                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                }
////                            });
//
//
//
//
//
//                                }
//                            });




                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });


//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

                }
                adapter = new CustomerCartAdapter(getContext(), cartModelList);
                recyclecart.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void openDialog() {

        dialog.setContentView(R.layout.activity_announcement_card);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LottieAnimationView loadingutensils = dialog.findViewById(R.id.progressAnimationView);
        dialog.show();

    }
//    private void sendNotifications(String usertoken, String title, String message, String order) {
//
//        Data data = new Data(title, message, order);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }

//    private void showNotification(String usertoken, String title, String message, String order)
//    {
//        Data data = new Data(title, message, order);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//
//        Uri sound = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.sound);
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(requireContext(), "default_notification_channel_id" )
//                .setSmallIcon(R.drawable.ic_menu_black_24dp )
//                .setContentTitle( "Success" )
//                .setSound(sound)
//                .setContentText( "Order Placed" ) ;
////        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
//        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
//        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
//            AudioAttributes audioAttributes = new AudioAttributes.Builder()
//                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
//                    .setUsage(AudioAttributes. USAGE_ALARM )
//                    .build() ;
//            int importance = NotificationManager. IMPORTANCE_HIGH ;
//            NotificationChannel notificationChannel = new NotificationChannel( "NOTIFICATION_CHANNEL_ID" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
//            notificationChannel.enableLights( true ) ;
//            notificationChannel.setLightColor(Color. RED ) ;
//            notificationChannel.enableVibration( true ) ;
//            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
//            notificationChannel.setSound(sound , audioAttributes) ;
//            mBuilder.setChannelId( "NOTIFICATION_CHANNEL_ID" ) ;
//            assert mNotificationManager != null;
//            mNotificationManager.createNotificationChannel(notificationChannel) ;
//        }
//        assert mNotificationManager != null;
//        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
//                mBuilder.build()) ;
//    }


//    private void sendNotificationsp(String usertoken, String title, String message, String order) {
//
//        // Create a notification channel
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("my_channel_id", "My Channel Name", NotificationManager.IMPORTANCE_HIGH);
//            channel.setDescription("My Channel Description");
//            NotificationManager manager = (NotificationManager) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
//            manager.createNotificationChannel(channel);
//        }
//
//        // Create the notification
//        Data data = new Data(title, message, order);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }
    private void setUpButtons() {
//        binding.signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthUI.getInstance().signOut(getActivity()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        startActivity(new Intent(getActivity(), FirebaseAuthUIActivity.class));
//                        finish();
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("messaging");
//                    }
//                });
//            }
//        });
        send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
                    new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.code()==200){
                                        Toast.makeText(getActivity(), "Notification sent", Toast.LENGTH_SHORT).show();
                                        hideKeyboard(getActivity());
                                    }
                                }
                            });
                        }
                    });
                }else{
                    if (notifNumber.getText().toString().isEmpty()){
                        notifNumber.setError("Please enter the mobile number");
                    }if (notifMessage.getText().toString().isEmpty()){
                        notifMessage.setError("Please enter the message you want to send");
                    }
                }
            }
        });
//        binding.blogLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://medium.com/@swapnil20711/firebase-push-notifications-android-using-admin-sdk-617d2c0be130";
//                // initializing object for custom chrome tabs.
//                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
//
//                // below line is setting toolbar color
//                // for our custom chrome tab.
//                customIntent.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.black));
//
//                // we are calling below method after
//                // setting our toolbar color.
//                openCustomTab(getActivity(), customIntent.build(), Uri.parse(url));
//            }
//        });
    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                if (isGPSEnabled()) {

                    getCurrentLocation();

                }else {

                    turnOnGPS();
                }
            }
        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                getCurrentLocation();
            }
        }
    }
    private void getCurrentLocation() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (isGPSEnabled()) {

                    LocationServices.getFusedLocationProviderClient(getActivity())
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);

                                    LocationServices.getFusedLocationProviderClient(getActivity())
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){

                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();

//                                        Localaddress = localadd.setText("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
//                                        localadd.setText;
//                                        loadrr = "google.navigation:q=22.659239,88.435534&mode=l"; (19 charaxcters)

//                                        loadrr = "google.navigation:q="+latitude+","+longitude+"&mode=l";
                                        loadrr = latitude+","+longitude;


                                        try {
                                            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                                            List<Address> addresses = geocoder.getFromLocation(latitude, longitude,1);
                                            addressp=addresses.get(0).getAddressLine(0);
//                                            loadrr = addressp;
                                            loadrr = latitude+","+longitude;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

//                                        Localaddress = latitude + ","+ longitude;
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    private void turnOnGPS() {



        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getContext())  //getApplicationContext()
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
//                    Toast.makeText(getActivity(), "GPS is already turned on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(getActivity(), 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;

    }














    // Check user's flag status to set up the UI appropriately
    private void checkUserFlagStatus() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
                    String groupName = dataSnapshot.child("groupName").getValue(String.class);  // Fetch groupName

                    if (flagValue != null) {
                        if (flagValue == 1) {
                            // User created a group
                            grouplay.setVisibility(View.GONE);
                            createlay.setVisibility(View.VISIBLE);
                            joinlay.setVisibility(View.GONE);
                            recyclecart.setVisibility(View.INVISIBLE);

                            // Set the current group to the fetched groupName
                            currentgroup = groupName != null ? groupName : "";
                        } else if (flagValue == 2) {
                            // User joined a group
                            grouplay.setVisibility(View.GONE);
                            createlay.setVisibility(View.GONE);
                            joinlay.setVisibility(View.VISIBLE);
                            recyclecart.setVisibility(View.VISIBLE);

                            // Set the current group to the fetched groupName
                            currentgroup = groupName != null ? groupName : "";
                        }
                    }
                } else {
                    // Default state when user is not part of any group
                    grouplay.setVisibility(View.VISIBLE);
                    createlay.setVisibility(View.GONE);
                    joinlay.setVisibility(View.GONE);
                    recyclecart.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if the user can create a group based on their flag
    private void checkUserFlagBeforeCreate() {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
            Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
            if (flagValue != null && flagValue == 1) {
            // User already created a group
            Toast.makeText(getContext(), "You have already created a group", Toast.LENGTH_SHORT).show();
            } else if (flagValue != null && flagValue == 2) {
            // User already joined a group
            Toast.makeText(getContext(), "You have already joined a group", Toast.LENGTH_SHORT).show();
            } else {
            // Allow the user to create a group
            showCreateGroupDialog();
            }
            }

    @Override
    public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
            }
            });
            }

    // Show Create Group dialog
    private void showCreateGroupDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Create Group");

    final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Create", (dialog, which) -> {
            String groupName = input.getText().toString().trim();
            if (!groupName.isEmpty()) {
            createGroupInFirebase(groupName);
            } else {
            Toast.makeText(getContext(), "Group name cannot be empty", Toast.LENGTH_SHORT).show();
            }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
            }

    // Create Group in Firebase
    private void createGroupInFirebase(final String groupName) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference chefGroupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd).child(groupName);

        chefGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getContext(), "Group name already exists, choose another", Toast.LENGTH_SHORT).show();
                } else {
                    // Create the group and mark the user as having created a group (flag = 1)
                    chefGroupRef.child(currentUserId).child("isLeader").setValue(true);
                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).child("flag").setValue(1);  // Mark user as having created a group
                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).child("groupName").setValue(groupName);  // Store group name

                    // Update UI
                    grouplay.setVisibility(View.GONE);
                    createlay.setVisibility(View.VISIBLE);
                    joinlay.setVisibility(View.GONE);
                    recyclecart.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), "Group created successfully!", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                    builder.setTitle("Note");
                    builder.setMessage("The group is created only for the store whose items were there in the cart. That means, group members can only add items from one store at a time. Also, if other people have to join your group, they should have items of same store (under which you created this group) in their carts.");

                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()); // Dismiss dialog
                    builder.show();
                    checkUserFlagStatus(); // Ensure UI and group status is updated

                    // Now, copy the cart items to the group
                    copyCartToGroup(currentUserId, groupName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to create group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Function to copy the cart items and grand total from Cart to the new Group
    private void copyCartToGroup(String currentUserId, String groupName) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        // Path to the user's cart
        DatabaseReference cartRef = database.getReference("Cart").child("CartItems").child(currentUserId);
        DatabaseReference cartTotalRef = database.getReference("Cart").child("GrandTotal").child(currentUserId);

        // Path to the newly created group
        DatabaseReference groupDishesRef = database.getReference("Groups").child(ChefIdd).child(groupName).child(currentUserId).child("Dishes");
        DatabaseReference groupOtherInfoRef = database.getReference("Groups").child(ChefIdd).child(groupName).child(currentUserId).child("OtherInformation");

        // Step 1: Copy all cart items to Group->Dishes
        cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                        String dishId = dishSnapshot.getKey();
                        String chefId = dishSnapshot.child("ChefId").getValue(String.class);
                        String dishName = dishSnapshot.child("DishName").getValue(String.class);
                        String dishQuantity = dishSnapshot.child("DishQuantity").getValue(String.class);
                        String dishPrice = dishSnapshot.child("Price").getValue(String.class);
                        String totalPrice = dishSnapshot.child("Totalprice").getValue(String.class);

                        // Create a new map to store the dish in the Group->Dishes node
                        HashMap<String, String> dishData = new HashMap<>();
                        dishData.put("ChefId", chefId);
                        dishData.put("DishId", dishId);
                        dishData.put("DishName", dishName);
                        dishData.put("DishPrice", dishPrice);
                        dishData.put("DishQuantity", dishQuantity);
                        dishData.put("RandomUID", dishId); // RandomUID is just the dishId here
                        dishData.put("TotalPrice", totalPrice);
                        dishData.put("UserId", currentUserId); // Add UserId as well

                        // Add each dish under Group->ChefIdd->groupname->currentUserId->Dishes->dishId
                        groupDishesRef.child(dishId).setValue(dishData);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        // Step 2: Copy the grand total to Group->OtherInformation->GrandTotalPrice
        cartTotalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String grandTotal = snapshot.child("GrandTotal").getValue(String.class);

                    // Add GrandTotalPrice to Group->OtherInformation
                    groupOtherInfoRef.child("GrandTotalPrice").setValue(grandTotal);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    // Show confirmation dialog for deleting the group
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Group");
        builder.setMessage("Are you sure you want to delete this group? This action cannot be undone.");

        // If the user confirms the deletion
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Proceed with deleting the group
            deleteGroup();
        });

        // If the user cancels the deletion
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();  // Close the dialog
        });

        builder.show();
    }
    // Delete Group from Firebase
    private void deleteGroup() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd);

        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String groupName = dataSnapshot.child("groupName").getValue(String.class);
                    if (groupName != null) {
                        // Remove the group and its users
                        groupRef.child(groupName).removeValue();
                        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).removeValue();
                        FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
                        FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();

                        // Set visibility of layouts
                        grouplay.setVisibility(View.VISIBLE);
                        createlay.setVisibility(View.GONE);
                        joinlay.setVisibility(View.GONE);
                        recyclecart.setVisibility(View.VISIBLE);

                        checkUserFlagStatus();

                        Toast.makeText(getContext(), "Group deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to delete group", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Show confirmation dialog for leaving the group
    private void showLeaveConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Leave Group");
        builder.setMessage("Are you sure you want to leave this group?");

        // If the user confirms the action
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Proceed with leaving the group
            leaveGroup();
        });

        // If the user cancels the action
        builder.setNegativeButton("No", (dialog, which) -> {
            dialog.dismiss();  // Close the dialog
        });

        builder.show();
    }
    // Leave Group in Firebase
    private void leaveGroup() {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference groupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd);

        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String groupName = dataSnapshot.child("groupName").getValue(String.class);
                    if (groupName != null) {
                        // Remove only the current user from the group and the flag
                        groupRef.child(groupName).child(currentUserId).removeValue();
                        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).removeValue();

                        // Set visibility of layouts
                        grouplay.setVisibility(View.VISIBLE);
                        createlay.setVisibility(View.GONE);
                        joinlay.setVisibility(View.GONE);

                        Toast.makeText(getContext(), "You have left the group", Toast.LENGTH_SHORT).show();

                        checkUserFlagStatus();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to leave group", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if the user can join a group based on their flag
    private void checkUserFlagBeforeJoin() {
            String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
            Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
            if (flagValue != null && flagValue == 1) {
            Toast.makeText(getContext(), "You have already created a group", Toast.LENGTH_SHORT).show();
            } else if (flagValue != null && flagValue == 2) {
            Toast.makeText(getContext(), "You have already joined a group", Toast.LENGTH_SHORT).show();
            } else {
            showJoinGroupDialog();
            }
            }

    @Override
    public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
            }
            });
            }

    // Show Join Group dialog
    private void showJoinGroupDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Join Group");

    final EditText input = new EditText(requireContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("Join", (dialog, which) -> {
            String groupName = input.getText().toString().trim();
            if (!groupName.isEmpty()) {
            joinGroupInFirebase(groupName);
            } else {
            Toast.makeText(getContext(), "Group name cannot be empty", Toast.LENGTH_SHORT).show();
            }
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
            }

    // Join Group in Firebase
    private void joinGroupInFirebase(final String groupName) {
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference chefGroupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd).child(groupName);

        chefGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
        if (dataSnapshot.exists()) {
        // Join the group and mark the user as having joined a group (flag = 2)
        chefGroupRef.child(currentUserId).child("isLeader").setValue(false);
        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).child("flag").setValue(2);  // Mark user as having joined a group
        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(currentUserId).child("groupName").setValue(groupName);  // Store group name

        // Update UI
        grouplay.setVisibility(View.GONE);
        createlay.setVisibility(View.GONE);
        joinlay.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), "Group joined successfully!", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Note");
            builder.setMessage("The group is created/joined only for the store whose items were there in the cart. That means, group members can only add items from one store at a time.");

            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()); // Dismiss dialog
            builder.show();
        checkUserFlagStatus();
        }
        else {
        Toast.makeText(getContext(), "There is no group named " + groupName, Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Note");
            builder.setMessage("The items in the cart should be of same store whose group has to be joined.");

            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss()); // Dismiss dialog
            builder.show();
        }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Toast.makeText(getContext(), "Failed to join group", Toast.LENGTH_SHORT).show();
        }
        });

        }


    private void showDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_dishes);

        RecyclerView recyclerView = dialog.findViewById(R.id.dishesRecyclerView);
        TextView grandTotalTextView = dialog.findViewById(R.id.grandTotalTextView);
        Button removeAllButton = dialog.findViewById(R.id.removeAllButton);
        Button placeOrderButton = dialog.findViewById(R.id.placeOrderButton);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<Dish> dishList = new ArrayList<>();
        DishAdapter adapter = new DishAdapter(dishList);
        recyclerView.setAdapter(adapter);

        adapter.setGrandTotalListener(grandTotal -> grandTotalTextView.setText("Grand Total: Rs " + grandTotal));

        // Fetch data from Firebase and update the RecyclerView
        fetchDishes(adapter);
        dialog.show();

        removeAllButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        placeOrderButton.setOnClickListener(v -> {

            dialog.dismiss();
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            List<Dish> selectedDishes = adapter.getSelectedDishes();

            if (selectedDishes.isEmpty()) {
                Toast.makeText(getActivity(), "No dishes selected", Toast.LENGTH_SHORT).show();
                return;
            }

//            checkUserFlagStatus();

            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            String userId = auth.getCurrentUser().getUid();
            DatabaseReference orderRef = database.getReference("Groups").child(ChefIdd).child(currentgroup).child(userId);

            final int[] newGrandTotal = {0};

            // Step 1: Handle adding dishes to Groups
            orderRef.child("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (Dish selectedDish : selectedDishes) {
                        String dishId = selectedDish.getDishId();
                        int selectedQuantity = Integer.parseInt(selectedDish.getQuantity());
                        int selectedTotalPrice = Integer.parseInt(selectedDish.getTotalPrice());

                        if (snapshot.hasChild(dishId)) {
                            // Update existing dish in Groups
                            DataSnapshot existingDishSnapshot = snapshot.child(dishId);
                            int existingQuantity = Integer.parseInt(existingDishSnapshot.child("DishQuantity").getValue(String.class));
                            int existingTotalPrice = Integer.parseInt(existingDishSnapshot.child("TotalPrice").getValue(String.class));

                            int updatedQuantity = existingQuantity + selectedQuantity;
                            int updatedTotalPrice = existingTotalPrice + selectedTotalPrice;

                            orderRef.child("Dishes").child(dishId).child("DishQuantity").setValue(String.valueOf(updatedQuantity));
                            orderRef.child("Dishes").child(dishId).child("TotalPrice").setValue(String.valueOf(updatedTotalPrice));
                        } else {
                            // Add new dish to Groups
                            orderRef.child("Dishes").child(dishId).setValue(selectedDish.toMap());
                        }

                        newGrandTotal[0] += selectedTotalPrice;
                    }

                    // Step 2: Update GrandTotal in Groups->OtherInformation
                    orderRef.child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int existingGrandTotal = 0;
                            if (snapshot.hasChild("GrandTotalPrice")) {
                                existingGrandTotal = Integer.parseInt(snapshot.child("GrandTotalPrice").getValue(String.class));
                            }
                            int updatedGrandTotal = existingGrandTotal + newGrandTotal[0];

                            orderRef.child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(updatedGrandTotal));

                            // Step 3: Add dishes and grand total to Cart for the leader
                            addOrderToLeaderCart(selectedDishes, newGrandTotal[0]);



                            progressDialog.dismiss();

                            Toast.makeText(getActivity(), "Order Added to your Group Order!", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        });
    }

    // Function to add orders to leader's Cart
    private void addOrderToLeaderCart(List<Dish> selectedDishes, int newGrandTotal) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference groupRef = database.getReference("Groups").child(ChefIdd).child(currentgroup);

        // Define an array to store leaderUserId (this avoids final requirement issues)
        final String[] leaderUserId = new String[1];

        // Find the user with isLeader = true
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    Boolean isLeader = userSnapshot.child("isLeader").getValue(Boolean.class);
                    if (isLeader != null && isLeader) {
                        leaderUserId[0] = userSnapshot.getKey();  // Store the leader's user ID in the array
                        leadid = leaderUserId[0];
                        break;
                    }
                }

                if (leaderUserId[0] != null) {
                    DatabaseReference cartRef = database.getReference("Cart").child("CartItems").child(leaderUserId[0]);

                    // Step 1: Add or update the dishes in the leader's Cart
                    cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (Dish selectedDish : selectedDishes) {
                                String dishId = selectedDish.getDishId();
                                int selectedQuantity = Integer.parseInt(selectedDish.getQuantity());
                                int selectedTotalPrice = Integer.parseInt(selectedDish.getTotalPrice());
                                int dishPrice = Integer.parseInt(selectedDish.getPrice());

                                if (snapshot.hasChild(dishId)) {
                                    // Update existing dish in Cart
                                    DataSnapshot existingDishSnapshot = snapshot.child(dishId);
                                    int existingQuantity = Integer.parseInt(existingDishSnapshot.child("DishQuantity").getValue(String.class));
                                    int existingTotalPrice = Integer.parseInt(existingDishSnapshot.child("Totalprice").getValue(String.class));

                                    int updatedQuantity = existingQuantity + selectedQuantity;
                                    int updatedTotalPrice = existingTotalPrice + selectedTotalPrice;

                                    cartRef.child(dishId).child("DishQuantity").setValue(String.valueOf(updatedQuantity));
                                    cartRef.child(dishId).child("Totalprice").setValue(String.valueOf(updatedTotalPrice));
                                } else {
                                    // Add new dish to Cart
                                    HashMap<String, String> cartDishData = new HashMap<>();
                                    cartDishData.put("ChefId", ChefIdd);
                                    cartDishData.put("DishID", dishId);
                                    cartDishData.put("DishName", selectedDish.getDishes());
                                    cartDishData.put("DishQuantity", String.valueOf(selectedQuantity));
                                    cartDishData.put("Price", String.valueOf(dishPrice));
                                    cartDishData.put("Totalprice", String.valueOf(selectedTotalPrice));
                                    cartRef.child(dishId).setValue(cartDishData);
                                }
                            }

                            // Step 2: Update GrandTotal in Cart
                            if(!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(leaderUserId[0])){
                                updateCartGrandTotal(leaderUserId[0], newGrandTotal);  // Use leaderUserId[0] to pass the value
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    // Function to update the GrandTotal in the Cart
    private void updateCartGrandTotal(String leaderUserId, int newGrandTotal) {
        DatabaseReference cartGrandTotalRef = FirebaseDatabase.getInstance().getReference("Cart").child("GrandTotal").child(leaderUserId);

        cartGrandTotalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int existingGrandTotal = 0;
                if (snapshot.hasChild("GrandTotal")) {
                    existingGrandTotal = Integer.parseInt(snapshot.child("GrandTotal").getValue(String.class));
                }
                int updatedGrandTotal = existingGrandTotal + newGrandTotal;

                cartGrandTotalRef.child("GrandTotal").setValue(String.valueOf(updatedGrandTotal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void fetchDishes(DishAdapter adapter) {
        DatabaseReference dishesRef = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails")
                .child(sstate).child(ccity).child(ssuburban).child(ChefIdd);

        dishesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                    Dish dish = dishSnapshot.getValue(Dish.class);
                    if (dish != null && !dish.getStockcnt().equals("0")) {
                        dish.setQuantity("0");
                        adapter.addDish(dish);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }


    private Bundle getGroupArguments() {
        Bundle bundle = new Bundle();
        bundle.putString("ChefIdd", ChefIdd);
        bundle.putString("currentgroup", currentgroup);

        bundle.putString("cfname", cfname);
        bundle.putString("clname", clname);
        bundle.putString("cmobile", cmobile);

        bundle.putString("sstate", sstate);
        bundle.putString("ccity", ccity);
        bundle.putString("ssuburban", ssuburban);


        bundle.putString("leaderUserId", leadid);
        return bundle;
    }

}












//    // Check user's flag status to set up the UI appropriately
//    private void checkUserFlagStatus() {
//        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
//                    String groupName = dataSnapshot.child("groupName").getValue(String.class);
//
//                    if (flagValue != null) {
//                        if (flagValue == 1) {
//                            // User created a group
//                            crgrp.setVisibility(View.GONE);
//                            txtGroupStatus.setText("You created a group named: " + groupName);
//                        } else if (flagValue == 2) {
//                            // User joined a group
//                            joingrp.setVisibility(View.GONE);
//                            txtGroupStatus.setText("You joined a group named: " + groupName);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Check if the user can create a group based on their flag
//    private void checkUserFlagBeforeCreate() {
//        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
//                if (flagValue != null && flagValue == 1) {
//                    // User already created a group
//                    Toast.makeText(getContext(), "You have already created a group", Toast.LENGTH_SHORT).show();
//                } else if (flagValue != null && flagValue == 2) {
//                    // User already joined a group
//                    Toast.makeText(getContext(), "You have already joined a group", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Allow the user to create a group
//                    showCreateGroupDialog();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Show Create Group dialog
//    private void showCreateGroupDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Create Group");
//
//        final EditText input = new EditText(requireContext());
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String groupName = input.getText().toString().trim();
//                if (!groupName.isEmpty()) {
//                    createGroupInFirebase(groupName);
//                } else {
//                    Toast.makeText(getContext(), "Group name cannot be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//        builder.show();
//    }
//
//    // Create Group in Firebase under provided chefId
//    private void createGroupInFirebase(final String groupName) {
//        DatabaseReference chefGroupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd).child(groupName);
//
//        chefGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Toast.makeText(getContext(), "Group name already exists, choose another", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Create the group and mark the user as having created a group (flag = 1)
//                    chefGroupRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
//                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("flag").setValue(1);  // Mark user as having created a group
//                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groupName").setValue(groupName);  // Store group name
//
//                    // Update UI
//                    crgrp.setVisibility(View.GONE);
//                    joingrp.setVisibility(View.GONE);
//                    txtGroupStatus.setVisibility(View.VISIBLE);
//                    txtGroupStatus.setText("You created a group named: " + groupName);
//                    Toast.makeText(getContext(), "Group created successfully!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Failed to create group", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Check if the user can join a group based on their flag
//    private void checkUserFlagBeforeJoin() {
//        FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Long flagValue = dataSnapshot.child("flag").getValue(Long.class);
//                if (flagValue != null && flagValue == 1) {
//                    // User already created a group
//                    Toast.makeText(getContext(), "You have already created a group", Toast.LENGTH_SHORT).show();
//                } else if (flagValue != null && flagValue == 2) {
//                    // User already joined a group
//                    Toast.makeText(getContext(), "You have already joined a group", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Allow the user to join a group
//                    showJoinGroupDialog();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Error checking user status", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    // Show Join Group dialog
//    private void showJoinGroupDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setTitle("Join Group");
//
//        final EditText input = new EditText(requireContext());
//        input.setInputType(InputType.TYPE_CLASS_TEXT);
//        builder.setView(input);
//
//        builder.setPositiveButton("Join", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String groupName = input.getText().toString().trim();
//                if (!groupName.isEmpty()) {
//                    joinGroupInFirebase(groupName);
//                } else {
//                    Toast.makeText(getContext(), "Group name cannot be empty", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//        builder.show();
//    }
//
//    // Join Group in Firebase under provided chefId
//    private void joinGroupInFirebase(final String groupName) {
//        DatabaseReference chefGroupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd).child(groupName);
//
//        chefGroupRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Join the group and mark the user as having joined a group (flag = 2)
//                    chefGroupRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(true);
//                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("flag").setValue(2);  // Mark user as having joined a group
//                    FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("groupName").setValue(groupName);  // Store group name
//
//                    // Update UI
//                    joingrp.setVisibility(View.GONE);
//                    txtGroupStatus.setText("You joined a group named: " + groupName);
//                    Toast.makeText(getContext(), "Group joined successfully!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getContext(), "There is no group named " + groupName, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Failed to join group", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

