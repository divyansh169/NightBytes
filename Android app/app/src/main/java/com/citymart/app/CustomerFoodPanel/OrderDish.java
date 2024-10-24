package com.citymart.app.CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.UpdateDishModel;
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
import java.util.Objects;

public class OrderDish extends AppCompatActivity {


    String RandomId, ChefID;
    ImageView imageView;
    ElegantNumberButton additem;
    TextView Foodname, ChefName, ChefLoaction, FoodQuantity, FoodPrice, FoodDescription, foodstatus, oostext, grptext, foodstock, ChefMob;
    DatabaseReference databaseReference, dataaa, chefdata, reference, data, dataref;
    String State, City, Sub, dishname;
    int dishprice;
    String custID;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_dish);


        Foodname = (TextView) findViewById(R.id.food_name);
        ChefName = (TextView) findViewById(R.id.chef_name);
        ChefMob = (TextView) findViewById(R.id.chef_mob);
        ChefLoaction = (TextView) findViewById(R.id.chef_location);
        FoodQuantity = (TextView) findViewById(R.id.food_quantity);
        FoodPrice = (TextView) findViewById(R.id.food_price);
        FoodDescription = (TextView) findViewById(R.id.food_description);
        foodstatus = (TextView) findViewById(R.id.food_status);
        foodstock = (TextView) findViewById(R.id.food_stock);
        oostext = (TextView) findViewById(R.id.oostext);
        grptext = (TextView) findViewById(R.id.grptext);
        imageView = (ImageView) findViewById(R.id.image);
        additem = (ElegantNumberButton) findViewById(R.id.number_btn);

        final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Customer cust = dataSnapshot.getValue(Customer.class);
                State = cust.getState();
                City = cust.getCity();
                Sub = cust.getSuburban();
                setTitle(Sub);

                RandomId = getIntent().getStringExtra("FoodMenu");
                ChefID = getIntent().getStringExtra("ChefId");

                databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateDishModel updateDishModel = dataSnapshot.getValue(UpdateDishModel.class);
                        Foodname.setText(updateDishModel.getDishes());
                        String qua = "<b>" + "Quantity: " + "</b>" + updateDishModel.getQuantity();
                        FoodQuantity.setText(Html.fromHtml(qua));
                        String ss = "<b>" + "Description: " + "</b>" + updateDishModel.getDescription();
                        FoodDescription.setText(Html.fromHtml(ss));
//                        vav = updateDishModel.getprodatt();
                        String ssop = "<b>" + "Status: " + "</b>" + updateDishModel.getprodatt();
                        String ssoppp = "<b>" + "Stock Count: " + "</b>" + updateDishModel.getstockcnt();

                        foodstatus.setText(Html.fromHtml(ssop));
                        foodstock.setText(Html.fromHtml(ssoppp));

//                        if((ssop.contains("out of stock"))   ||  (ssoppp.equals("<b>" + "Stock Count: " + "</b>" + "0")) ){


                        if ((ssoppp.equals("<b>" + "Stock Count: " + "</b>" + "0"))) {
                            additem.setVisibility(View.GONE);
                            oostext.setVisibility(View.VISIBLE);
                            grptext.setVisibility(View.GONE);
                        }
                        else {
//                            additem.setVisibility(View.VISIBLE);
                            oostext.setVisibility(View.GONE);
//                            grptext.setVisibility(View.GONE);



                        // Reference to the Firebase Realtime Database at the specified path
                        DatabaseReference userFlagRef = FirebaseDatabase.getInstance().getReference("GroupsUserFlag").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        // Attach a listener to check if the user ID exists and check the flag value
                        userFlagRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    if (dataSnapshot.hasChild("flag")) {
                                        int flagi = dataSnapshot.child("flag").getValue(Integer.class);

                                        if (flagi == 1) {
                                            additem.setVisibility(View.GONE);
                                            grptext.setVisibility(View.VISIBLE);
                                        } else {
                                            additem.setVisibility(View.VISIBLE);
                                            grptext.setVisibility(View.GONE);
                                        }
                                    } else {
                                        additem.setVisibility(View.VISIBLE);
                                        grptext.setVisibility(View.GONE);
                                    }
                                } else {
                                    additem.setVisibility(View.VISIBLE);
                                    grptext.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // Handle any database error

                            }
                        });

                    }

                        String pri = "<b>" + "Price: ₹ " + "</b>" + updateDishModel.getPrice();
                        FoodPrice.setText(Html.fromHtml(pri));
                        Glide.with(OrderDish.this).load(updateDishModel.getImageURL()).into(imageView);

                        chefdata = FirebaseDatabase.getInstance().getReference("Chef").child(ChefID);
                        chefdata.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Chef chef = dataSnapshot.getValue(Chef.class);

                                String name = "<b>" + "Seller Name: " + "</b>" + chef.getFname() + " " + chef.getLname();
                                ChefName.setText(Html.fromHtml(name));

                                String mib = chef.getMobile();
                                ChefMob.setText(mib);

                                String loc = "<b>" + "Location: " + "</b>" + chef.getSuburban();
                                ChefLoaction.setText(Html.fromHtml(loc));
                                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Cart cart = dataSnapshot.getValue(Cart.class);
                                        if (dataSnapshot.exists()) {
                                            additem.setNumber(cart.getDishQuantity());
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
                additem.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        if(Objects.equals(foodstatus.getText(), "out of stock")) {
//
//                                Toast.makeText(OrderDish.this, "This item is out of stock", Toast.LENGTH_SHORT).show();
//
//                        }
//                        else {

                        dataref = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Cart cart1 = null;
                                if (dataSnapshot.exists()) {
                                    int totalcount = 0;
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        totalcount++;
                                    }
                                    int i = 0;
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        i++;
                                        if (i == totalcount) {
                                            cart1 = snapshot.getValue(Cart.class);
                                        }
                                    }

                                    if (ChefID.equals(cart1.getChefId())) {
                                        data = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                                                dishname = update.getDishes();
                                                dishprice = Integer.parseInt(update.getPrice());

                                                int num = Integer.parseInt(additem.getNumber());
                                                int totalprice = num * dishprice;
                                                if (num != 0) {
                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                    hashMap.put("DishName", dishname);
                                                    hashMap.put("DishID", RandomId);
                                                    hashMap.put("DishQuantity", String.valueOf(num));
                                                    hashMap.put("Price", String.valueOf(dishprice));
                                                    hashMap.put("Totalprice", String.valueOf(totalprice));
                                                    hashMap.put("ChefId", ChefID);
                                                    custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                    reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                    reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {

                                                            Toast.makeText(OrderDish.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                                } else {

                                                    firebaseDatabase.getInstance().getReference("Cart").child(custID).child(RandomId).removeValue();
                                                    Toast.makeText(OrderDish.this, "Removed from cart", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDish.this);
                                        builder.setMessage("You can't add product items of multiple Stores at a time. Try to add items of same Store");
                                        builder.setCancelable(false);
                                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                dialog.dismiss();
                                                Intent intent = new Intent(OrderDish.this, CustomerFoodPanel_BottomNavigation.class);
                                                startActivity(intent);
                                                finish();    //removed finish() thisy;

                                            }
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                } else {
                                    data = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(ChefID).child(RandomId);
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            UpdateDishModel update = dataSnapshot.getValue(UpdateDishModel.class);
                                            dishname = update.getDishes();
                                            dishprice = Integer.parseInt(update.getPrice());
                                            int num = Integer.parseInt(additem.getNumber());
                                            int totalprice = num * dishprice;
                                            if (num != 0) {
                                                HashMap<String, String> hashMap = new HashMap<>();
                                                hashMap.put("DishName", dishname);
                                                hashMap.put("DishID", RandomId);
                                                hashMap.put("DishQuantity", String.valueOf(num));
                                                hashMap.put("Price", String.valueOf(dishprice));
                                                hashMap.put("Totalprice", String.valueOf(totalprice));
                                                hashMap.put("ChefId", ChefID);
                                                custID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                reference = FirebaseDatabase.getInstance().getReference("Cart").child("CartItems").child(custID).child(RandomId);
                                                reference.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {

                                                        Toast.makeText(OrderDish.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } else {

                                                firebaseDatabase.getInstance().getReference("Cart").child(custID).child(RandomId).removeValue();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                    }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

