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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
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


public class UserDetailsDialogFragment extends DialogFragment {

    private String ChefIdd;
    private String currentgroup, cfname, clname, cmobile, sstate, ccity, ssuburban, leaderUserId;
    private LinearLayout userListLayout;
    private FirebaseAuth auth;
    private DatabaseReference groupRef, cartRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_details, container, false);

        userListLayout = view.findViewById(R.id.userListLayout);
        TextView suburbanText = view.findViewById(R.id.suburban);
        TextView groupNameText = view.findViewById(R.id.groupName);

        if (getArguments() != null) {
            ChefIdd = getArguments().getString("ChefIdd");
            currentgroup = getArguments().getString("currentgroup");
            cfname = getArguments().getString("cfname");
            clname = getArguments().getString("clname");
            cmobile = getArguments().getString("cmobile");
            sstate = getArguments().getString("sstate");
            ccity = getArguments().getString("ccity");
            ssuburban = getArguments().getString("ssuburban");
            leaderUserId = getArguments().getString("leaderUserId");


        }

        // Set static values
        suburbanText.setText(ssuburban + ", " + ccity + ", " + sstate);  // Assuming you already have the Suburban value
        groupNameText.setText(currentgroup);

        // Initialize Firebase
        auth = FirebaseAuth.getInstance();
        groupRef = FirebaseDatabase.getInstance().getReference("Groups").child(ChefIdd).child(currentgroup);
        cartRef = FirebaseDatabase.getInstance().getReference("Cart");

        fetchGroupDetails();

        return view;
    }

    private void fetchGroupDetails() {
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userId = userSnapshot.getKey();
                    DataSnapshot dishesSnapshot = userSnapshot.child("Dishes");
                    DataSnapshot otherInfoSnapshot = userSnapshot.child("OtherInformation");
                    createUserCard(userId, dishesSnapshot, otherInfoSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private void createUserCard(String userId, DataSnapshot dishesSnapshot, DataSnapshot otherInfoSnapshot) {
        View userCard = LayoutInflater.from(getContext()).inflate(R.layout.user_card, null, false);
        TextView userName = userCard.findViewById(R.id.userName);
        TextView userMobile = userCard.findViewById(R.id.userMobile);
        LinearLayout dishesLayout = userCard.findViewById(R.id.dishesLayout);
        TextView grandTotalTextView = userCard.findViewById(R.id.grandTotalTextView);
        Button okButton = userCard.findViewById(R.id.okButton);
        TextView isReadyTextView = userCard.findViewById(R.id.isReadyStatus);

        // Assuming user name and mobile are already available, set them here
        userName.setText(cfname + " " + clname);  // Replace with real data
        userMobile.setText(cmobile);  // Replace with real data

        // Iterate over dishes and add to layout
        for (DataSnapshot dishSnapshot : dishesSnapshot.getChildren()) {
            View dishView = LayoutInflater.from(getContext()).inflate(R.layout.dish_card, null, false);
            TextView dishNameTextView = dishView.findViewById(R.id.dishName);
            TextView dishPriceTextView = dishView.findViewById(R.id.priceRs);
            TextView dishQuantityTextView = dishView.findViewById(R.id.qty);
            TextView totalTextView = dishView.findViewById(R.id.totalRs);
            ElegantNumberButton elegantButton = dishView.findViewById(R.id.elegantBtn);

            String dishId = dishSnapshot.getKey();
            String dishName = dishSnapshot.child("DishName").getValue(String.class);
            String dishPrice = dishSnapshot.child("DishPrice").getValue(String.class);
            String dishQuantity = dishSnapshot.child("DishQuantity").getValue(String.class);
            String totalPrice = dishSnapshot.child("TotalPrice").getValue(String.class);

            dishNameTextView.setText(dishName);
            dishPriceTextView.setText("Rs " + dishPrice);
            dishQuantityTextView.setText(dishQuantity);
            totalTextView.setText("Rs " + totalPrice);

            if (userId.equals(auth.getCurrentUser().getUid())) {
                elegantButton.setVisibility(View.VISIBLE);
                elegantButton.setNumber(dishQuantity);
                elegantButton.setOnValueChangeListener((view, oldValue, newValue) -> {

                    int newTotalPrice = Integer.parseInt(dishPrice) * newValue;
                    totalTextView.setText(newTotalPrice);
                    dishSnapshot.getRef().child("DishQuantity").setValue(String.valueOf(newValue));
                    dishQuantityTextView.setText(newValue);
                    dishSnapshot.getRef().child("TotalPrice").setValue(String.valueOf(newTotalPrice));
                    updateCartAndGrandTotal(userId, dishId, newValue, newTotalPrice);

                });
                okButton.setVisibility(View.VISIBLE);
                setupToggleOkButton(userId, okButton, isReadyTextView);
            }

            dishesLayout.addView(dishView);
        }

        String grandTotal = otherInfoSnapshot.child("GrandTotalPrice").getValue(String.class);
        grandTotalTextView.setText("Grand Total: Rs " + grandTotal);
        userListLayout.addView(userCard);
    }

    private void setupToggleOkButton(String userId, Button okButton, TextView isReadyTextView) {
        DatabaseReference isReadyRef = groupRef.child(userId).child("isReady");
        isReadyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean isReady = dataSnapshot.getValue(Boolean.class);
                if (isReady != null && isReady) {
                    isReadyTextView.setText("Ready");
                    okButton.setText("Mark as NOT Ready");
                } else {
                    isReadyTextView.setText("NOT Ready");
                    okButton.setText("Mark as Ready");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        okButton.setOnClickListener(v -> {
            boolean currentStatus = isReadyTextView.getText().toString().equals("Ready");
            isReadyRef.setValue(!currentStatus);
        });
    }

    /**
     * Update Cart and GrandTotal in the "Cart" node and the group.
     */
    private void updateCartAndGrandTotal(String userId, String dishId, int newQuantity, int newTotalPrice) {
//        leaderUserId = "LEADER_USER_ID";  // Replace with actual leader user ID

        // Update the dish quantity and total price in the Cart for the leader user
        DatabaseReference dishRefInCart = cartRef.child("CartItems").child(leaderUserId).child(dishId);
        dishRefInCart.child("DishQuantity").setValue(String.valueOf(newQuantity));
        dishRefInCart.child("TotalPrice").setValue(String.valueOf(newTotalPrice));

        // Recalculate GrandTotal for the leader
        cartRef.child("CartItems").child(leaderUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int grandTotal = 0;
                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    String totalPrice = dishSnapshot.child("TotalPrice").getValue(String.class);
                    grandTotal += Integer.parseInt(totalPrice);
                }

                // Update GrandTotal for the leader user
                cartRef.child("GrandTotal").child(leaderUserId).child("GrandTotal").setValue(String.valueOf(grandTotal));

                // Also update GrandTotal in the Groups node for the current user
                groupRef.child(userId).child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(grandTotal));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
