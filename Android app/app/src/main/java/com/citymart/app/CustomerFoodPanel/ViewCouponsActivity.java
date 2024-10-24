package com.citymart.app.CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.citymart.app.Coupon;
import com.citymart.app.CouponsAdapter;
import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewCouponsActivity extends AppCompatActivity {

    private RecyclerView couponsRecyclerView;
    private CouponsAdapter couponsAdapter;
    private List<Coupon> couponList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_coupons);

        couponsRecyclerView = findViewById(R.id.couponsRecyclerView);
        couponsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        couponList = new ArrayList<>();

        // Initialize CouponsAdapter without delete functionality (set isSellerView to false)
        couponsAdapter = new CouponsAdapter(couponList, mDatabase, null, false);
        couponsRecyclerView.setAdapter(couponsAdapter);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String sellerId = getIntent().getStringExtra("sellerId");

        mDatabase.child("chefCoupons").child(sellerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                couponList.clear();
                for (DataSnapshot couponSnapshot : snapshot.getChildren()) {
                    Coupon coupon = couponSnapshot.getValue(Coupon.class);
                    couponList.add(coupon);
                }
                couponsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewCouponsActivity.this, "Failed to load coupons.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
