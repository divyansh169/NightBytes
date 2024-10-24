package com.citymart.app.CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.UpdateDishModel;
import com.citymart.app.ChefFoodPanel.UpdateDishModel_bannerlist;
import com.citymart.app.Customer;
import com.citymart.app.CustomerFoodPanel_BottomNavigation;

import com.citymart.app.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OrderDishOffer extends AppCompatActivity {


    String RandomId, ChefID;
    ImageView imageView;
    //    ElegantNumberButton additem;
//    TextView Foodname, ChefName, ChefLoaction, FoodQuantity, FoodPrice, FoodDescription;
    DatabaseReference databaseReference, dataaa, chefdata, reference, data, dataref;
    String State, City, Sub, dishname;
    int dishprice;
    String custID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dish_offer);


//        Foodname = (TextView) findViewById(R.id.food_name);
//        ChefName = (TextView) findViewById(R.id.chef_name);
//        ChefLoaction = (TextView) findViewById(R.id.chef_location);
//        FoodQuantity = (TextView) findViewById(R.id.food_quantity);
//        FoodPrice = (TextView) findViewById(R.id.food_price);
//        FoodDescription = (TextView) findViewById(R.id.food_description);
        imageView = (ImageView) findViewById(R.id.image);
//        additem = (ElegantNumberButton) findViewById(R.id.number_btn);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer cust = dataSnapshot.getValue(Customer.class);
                State = cust.getState();
                City = cust.getCity();
                Sub = cust.getSuburban();
//                getActionBar().setTitle(Sub);

                RandomId = getIntent().getStringExtra("FoodMenuu");
                ChefID = getIntent().getStringExtra("ChefIdd");

                databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateDishModel_bannerlist updateDishModel_bannerlist = dataSnapshot.getValue(UpdateDishModel_bannerlist.class);
//                        Foodname.setText(updateDishModel.getDishes());
//                        String qua = "<b>" + "Quantity: " + "</b>" + updateDishModel.getQuantity();
//                        FoodQuantity.setText(Html.fromHtml(qua));
//                        String ss = "<b>" + "Description: " + "</b>" + updateDishModel.getDescription();
//                        FoodDescription.setText(Html.fromHtml(ss));
//                        String pri = "<b>" + "Price: ₹ " + "</b>" + updateDishModel.getPrice();
//                        FoodPrice.setText(Html.fromHtml(pri));
                        Glide.with(OrderDishOffer.this).load(updateDishModel_bannerlist.getImageURL()).into(imageView);

//                        chefdata = FirebaseDatabase.getInstance().getReference("Chef").child(ChefID);
//                        chefdata.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Chef chef = dataSnapshot.getValue(Chef.class);
//
//                                String name = "<b>" + "Chef Name: " + "</b>" + chef.getFname() + " " + chef.getLname();
//                                ChefName.setText(Html.fromHtml(name));
//                                String loc = "<b>" + "Location: " + "</b>" + chef.getSuburban();
//                                ChefLoaction.setText(Html.fromHtml(loc));
//                                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
//                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        Cart cart = dataSnapshot.getValue(Cart.class);
//                                        if (dataSnapshot.exists()) {
//                                            additem.setNumber(cart.getDishQuantity());
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
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