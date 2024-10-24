package com.citymart.app.CustomerFoodPanel;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.ChefFoodPanel.ChefFinalOrders;
import com.citymart.app.R;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerFullHistoryView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
//    private List<ChefWaitingOrders> chefWaitingFullOrdersList;
    private List<CustomerHistory> aggregatedDishesList;
    private List<String> aggregatedDateList;
    private CustomerFullHistoryViewAdapter adapter;

    private APIService apiService;
    public static String dname, dquant, dishName, price, totp;
    String daat, raat;
    public static int quantity;
    public static int pricey;
    public static int totpy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fullhistoryview);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        recyclerViewdish.setHasFixedSize(true);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(CustomerFullHistoryView.this));
//        chefWaitingFullOrdersList = new ArrayList<>();
        aggregatedDishesList = new ArrayList<>();
        aggregatedDateList = new ArrayList<>();
        CheforderdishesView();
    }

    private void CheforderdishesView() {
//        RandomUID = getIntent().getStringExtra("RandomUID");

        //.................................................................................................
//        DatabaseReference moddatabaseReference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        moddatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                chefWaitingFullOrdersList.clear();
//
//                if(dataSnapshot.exists()){
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    DatabaseReference dataz = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
////                    rund = snapshot.getKey();
//                    chefWaitingFullOrdersList.clear();
//                    dataz.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if(dataSnapshot.exists()){
//                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
//                                ChefWaitingOrders chefWaitingOrders = snapshot1.getValue(ChefWaitingOrders.class);
//                                chefWaitingFullOrdersList.add(chefWaitingOrders);
////                                dname = chefWaitingOrders.getDishName();
////                                dquant = chefWaitingOrders.getDishQuantity();
//                            }
//                            adapter = new ChefFullPreparedOrderViewAdapter(ChefFullPreparedOrderView.this, chefWaitingFullOrdersList);
//                            recyclerViewdish.setAdapter(adapter);
//                        }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
        //.................................................................................................





        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        daat = getIntent().getStringExtra("Finishdate");
        raat = getIntent().getStringExtra("rundm");
//        DatabaseReference moddatabaseReference = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        moddatabaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//
////                    aggregatedDateList.clear();
//
//                    for (DataSnapshot snapshotdate : dataSnapshot.getChildren()) {

//                        CustomerHistory aggregatedDish = new CustomerHistory(dishName, totalPrice, quant, totp);
//                        aggregatedDateList.add(snapshotdate.getKey());


//                    DatabaseReference moddatabaseReferencex1 = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshotdate.getKey());
//                    moddatabaseReferencex1.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.exists()) {
//
//                                for (DataSnapshot snapshot2 : dataSnapshot.getChildren()) {
//
//                                    DatabaseReference moddatabaseReferencex2 = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot1.getKey()).child(snapshot2.getKey());
//                                    moddatabaseReferencex2.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//
//                                                for (DataSnapshot snapshot3 : dataSnapshot.getChildren()) {


//                                                    DatabaseReference moddatabaseReferencex3 = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(daat);
//                                                    moddatabaseReferencex3.addValueEventListener(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                            if (dataSnapshot.exists()) {
//                                                                aggregatedDishesList.clear();

//                                                                Map<String, Integer> dishQuantities = new HashMap<>();
//                                                                Map<String, Integer> dishTotalPrices = new HashMap<>();
//                                                                Map<String, String> dishdates = new HashMap<>();

//                                                                for (DataSnapshot snapshotrandomuids : dataSnapshot.getChildren()) {

//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        DatabaseReference dataz = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(daat).child(raat).child("Dishes");
                                        dataz.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
//                                                    aggregatedDishesList.clear();
                                                    aggregatedDishesList.clear();
                                                    for (DataSnapshot snapshot5 : dataSnapshot.getChildren()) {
                                                        CustomerHistory CustomerHistory = snapshot5.getValue(CustomerHistory.class);
                                                        aggregatedDishesList.add(CustomerHistory);
                                                        adapter = new CustomerFullHistoryViewAdapter(CustomerFullHistoryView.this, aggregatedDishesList);
                                                        recyclerViewdish.setAdapter(adapter);
//                                                        dishName = CustomerHistory.getDishName();
//                                                        dquant = (CustomerHistory.getDishQuantity());
//                                                        totp = (CustomerHistory.getTotalPrice());
//                                                        price = CustomerHistory.getDishPrice();
//                                                        totp = CustomerHistory.getTotalPrice();

//                                                        chefFinalOrdersList.clear();

//                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                                            ChefFinalOrders chefFinalOrders = snapshot.getValue(ChefFinalOrders.class);
//                                                            chefFinalOrdersList.add(chefFinalOrders);
//                                                        }

//                                                        if((snapshotdate.getKey()).contains(findat)) {
//
//                                                            dishdates.put(snapshotdate.getKey(), dishName);

//                                                            if (dishQuantities.containsKey(dishName)) {
//                                                                int currentQuantity = dishQuantities.get(dishName);
//                                                                int currentTotalPrice = dishTotalPrices.get(dishName);
//                                                                dishQuantities.put(dishName, currentQuantity + quantity);
//                                                                dishTotalPrices.put(dishName, pricey);
//                                                            } else {
//                                                                dishQuantities.put(dishName, quantity);
//                                                                dishTotalPrices.put(dishName, pricey);
//                                                            }
//                                                        CustomerHistory aggregatedDish = new CustomerHistory(dishName, price, dquant, totp);
//                                                        aggregatedDishesList.add(aggregatedDish);
//                                                        }
//                                                        else {
//                                                            continue;
//                                                        }
                                                    }

                                                }

//                                                aggregatedDishesList.clear(); //i have chnaged this to top

//                                                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
//                                                    String dishName = entry.getKey();
//                                                    int quantity = entry.getValue();
//                                                    int totka = dishTotalPrices.get(dishName); //indiv price in int
//                                                    String totalPrice = String.valueOf(totka);  //indiv price in string
//                                                    String quant = String.valueOf(quantity);
//                                                    String totp = String.valueOf(totka*quantity);
////                                                    String pri = String.valueOf(totalPrice/quantity);
//                                                    CustomerHistory aggregatedDish = new CustomerHistory(dishName, totalPrice, quant, totp);
//                                                    aggregatedDishesList.add(aggregatedDish);
//                                                }

// Create a new adapter with the aggregated data
//                                                adapter = new CustomerFullHistoryViewAdapter(CustomerFullHistoryView.this, aggregatedDishesList);
//                                                recyclerViewdish.setAdapter(adapter);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
//                                    }


//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });


//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


//                }
//
//                    //
//
//
//
//            }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                chefWaitingFullOrdersList.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    ChefWaitingOrders chefWaitingOrders = snapshot.getValue(ChefWaitingOrders.class);
//                    chefWaitingFullOrdersList.add(chefWaitingOrders);
//                }
//                adapter = new ChefFullPreparedOrderViewAdapter(ChefFullPreparedOrderView.this, chefWaitingFullOrdersList);
//                recyclerViewdish.setAdapter(adapter);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }
}
