package com.citymart.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.citymart.app.DeliveryFoodPanel.DeliveryPendingOrderFragment;
import com.citymart.app.DeliveryFoodPanel.DeliveryProfileFragment;
import com.citymart.app.DeliveryFoodPanel.DeliveryShipOrderFragment;

import com.citymart.app.SendNotification.Token;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Delivery_FoodPanelBottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    public static String subburban, tempstring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery__food_panel_bottom_navigation);
        BottomNavigationView navigationView = findViewById(R.id.delivery_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

//        DatabaseReference databaseReferencpoe = FirebaseDatabase.getInstance().getReference("deliveryshipper").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("suburbanus");
//        databaseReferencpoe.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type9 type9 = dataSnapshot.getValue(Type9.class);
//                assert type9 != null;
//                tempstring = type9.getsuburbanus();
//                subburban = FirebaseAuth.getInstance().getCurrentUser().getUid() ;
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        UpdateToken();
        String name = getIntent().getStringExtra("PAGE");
        if (name != null) {
            if (name.equalsIgnoreCase("DeliveryOrderpage"))
            {
                loaddeliveryfragment(new DeliveryPendingOrderFragment());
            }

        } else {
            loaddeliveryfragment(new DeliveryPendingOrderFragment());
        }



    }

    private void UpdateToken() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(refreshToken);




        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(token);



    }

    private boolean loaddeliveryfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }

        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.pendingorders:
                fragment = new DeliveryPendingOrderFragment();
                break;

            case R.id.shiporders:
                fragment = new DeliveryShipOrderFragment();
                break;

            case R.id.deliveryprofile:
                fragment = new DeliveryProfileFragment();
                break;

        }
        return loaddeliveryfragment(fragment);
    }
}
