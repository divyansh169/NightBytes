package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

// Add the following imports at the beginning
import androidx.recyclerview.widget.LinearLayoutManager;

import com.citymart.app.Coupon;
import com.citymart.app.CouponsAdapter;
import com.google.firebase.database.Query;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.Chef;

import com.citymart.app.R;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.citymart.app.SendNotification.Data;
import com.citymart.app.SendNotification.MyResponse;
import com.citymart.app.SendNotification.NotificationSender;

import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import com.google.firebase.database.DatabaseReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCouponActivity extends AppCompatActivity {

    private EditText couponCodeEditText, discountEditText, minOrderValueEditText, freeItemEditText;

    private Spinner couponTypeSpinner;
    private Button createCouponButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    // Other existing members
    private RecyclerView couponsRecyclerView;
    private CouponsAdapter couponsAdapter;
    private List<Coupon> couponList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_coupon);
        couponsRecyclerView = findViewById(R.id.couponsRecyclerView);
        couponsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        couponCodeEditText = findViewById(R.id.couponCodeEditText);
        discountEditText = findViewById(R.id.discountEditText);
        minOrderValueEditText = findViewById(R.id.minOrderValueEditText);
        freeItemEditText = findViewById(R.id.freeItemEditText);
        couponTypeSpinner = findViewById(R.id.couponTypeSpinner);
        createCouponButton = findViewById(R.id.createCouponButton);

        // Define coupon types
        String[] couponTypes = {
            "₹X off above ₹Y",
            "X% off above ₹Y",
            "Get free Z above ₹Y",
            "Free delivery above ₹Y"
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, couponTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        couponTypeSpinner.setAdapter(adapter);

        // Assuming the spinner is populated with couponTypes
        couponTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedType = couponTypeSpinner.getSelectedItem().toString();
                if (selectedType.contains("Get free Z")) {
                    freeItemEditText.setVisibility(View.VISIBLE);
                } else {
                    freeItemEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        

        
        couponsAdapter = new CouponsAdapter(couponList, mDatabase, mAuth.getCurrentUser().getUid(), true); // Pass true for seller view
        couponsRecyclerView.setAdapter(couponsAdapter);
        loadCoupons(); // Load existing coupons
        createCouponButton.setOnClickListener(v -> createCoupon());
    }

    private void createCoupon() {
    String couponCode = couponCodeEditText.getText().toString().trim();

    // Check if couponCodeEditText is empty
    if (couponCode.isEmpty()) {
        couponCodeEditText.setError("Coupon code is required");
        couponCodeEditText.requestFocus();
        return;
    }

    // Set default values to "0" if fields are empty
    String discount = discountEditText.getText().toString().trim();
    if (discount.isEmpty()) {
        discount = "0";
    }

    String minOrderValue = minOrderValueEditText.getText().toString().trim();
    if (minOrderValue.isEmpty()) {
        minOrderValue = "0";
    }

    String freeItem = freeItemEditText.getText().toString().trim();
    if (freeItem.isEmpty()) {
        freeItem = "0";
    }

    String couponType = couponTypeSpinner.getSelectedItem().toString().trim();

    String sellerId = mAuth.getCurrentUser().getUid();
    String couponId = mDatabase.child("chefCoupons").child(sellerId).push().getKey();

    Map<String, Object> coupon = new HashMap<>();
    coupon.put("couponCode", couponCode);
    coupon.put("discount", discount);
    coupon.put("minOrderValue", minOrderValue);
    coupon.put("freeItem", freeItem);
    coupon.put("couponType", couponType);

    mDatabase.child("chefCoupons").child(sellerId).child(couponId).setValue(coupon)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateCouponActivity.this, "Coupon created successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateCouponActivity.this, "Failed to create coupon.", Toast.LENGTH_SHORT).show();
                }
            });
}
private void loadCoupons() {
        String sellerId = mAuth.getCurrentUser().getUid();
        Query couponsQuery = mDatabase.child("chefCoupons").child(sellerId);

        couponsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                couponList.clear();
                for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
                    Coupon coupon = couponSnapshot.getValue(Coupon.class);
                    coupon.setId(couponSnapshot.getKey()); // Capture the coupon ID for deletion
                    couponList.add(coupon);
                }
                couponsAdapter.notifyDataSetChanged();

                if (couponList.isEmpty()) {
                    findViewById(R.id.noCouponsTextView).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.noCouponsTextView).setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CreateCouponActivity.this, "Failed to load coupons.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
