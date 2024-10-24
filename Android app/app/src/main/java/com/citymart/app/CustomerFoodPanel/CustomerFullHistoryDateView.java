package com.citymart.app.CustomerFoodPanel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.ChefFoodPanel.ChefFullHistoryDateView;
import com.citymart.app.ChefFoodPanel.ChefFullHistoryViewDateAdapter;
import com.citymart.app.ChefFoodPanel.ChefHistory1;
import com.citymart.app.ChefFoodPanel.ChefOrderTobePrepared;
import com.citymart.app.ChefFoodPanel.ChefOrderTobePreparedAdapter;
import com.citymart.app.ChefFoodPanel.ChefWaitingOrders1;
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

public class CustomerFullHistoryDateView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
//    private List<ChefWaitingOrders> chefWaitingFullOrdersList;
//    private List<ChefHistory> aggregatedDishesList;
    private List<CustomerHistory1> aggregatedDateList;
    private CustomerFullHistoryViewDateAdapter adapter;
    SearchView searchView6;

    private APIService apiService;
    public static String gprice, gdate, gchefid, gadd, gchefname, gcustomername, gmob, grandomuid;
    public int gp=0;
    public static int quantity;
    public static int pricey;
    public static int totpy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_fullhistorydateview);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        recyclerViewdish.setHasFixedSize(true);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(CustomerFullHistoryDateView.this));
//        chefWaitingFullOrdersList = new ArrayList<>();
//        aggregatedDishesList = new ArrayList<>();
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

        DatabaseReference moddatabaseReference = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        moddatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Map<String, Integer> dishdates = new HashMap<>();

//                    aggregatedDateList.clear();

                    for (DataSnapshot snapshotdate : dataSnapshot.getChildren()) {
//
//                        ChefHistory aggregatedDish = new ChefHistory(dishName, totalPrice, quant, totp);
//                        aggregatedDateList.add(snapshotdate.getKey());
//
                                     DatabaseReference moddatabaseReferencex3 = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshotdate.getKey());
                                                    moddatabaseReferencex3.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.exists()) {
//                                                                aggregatedDishesList.clear();
                                                                aggregatedDateList.clear();

//                                                                Map<String, Integer> dishQuantities = new HashMap<>();
//                                                                Map<String, Integer> dishTotalPrices = new HashMap<>();

                                                                for (DataSnapshot snapshotrandomuids : dataSnapshot.getChildren()) {

//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        DatabaseReference dataz = FirebaseDatabase.getInstance().getReference("CustomerHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshotdate.getKey()).child(snapshotrandomuids.getKey()).child("OtherInformation");
                                        dataz.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                                                if (dataSnapshot.exists()) {
//                                                    aggregatedDishesList.clear();
//                                                    for (DataSnapshot snapshot5 : dataSnapshot.getChildren()) {
                                                CustomerHistory1 CustomerHistory1 = dataSnapshot.getValue(CustomerHistory1.class);
//                                                        gdate = CustomerHistory1.getFinishdate();
//                                                        gprice = CustomerHistory1.getGrandTotalPrice();
//                                                        gchefid = CustomerHistory1.getChefId();
//                                                        gadd = CustomerHistory1.getAddress();
//                                                        gchefname = CustomerHistory1.getChefName();
//                                                        gcustomername = CustomerHistory1.getName();
//                                                        gmob = CustomerHistory1.getMobileNumber();
//                                                        grandomuid = CustomerHistory1.getRandomUID();

//                                                    ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
//                                                    chefWaitingOrders1List.add(chefWaitingOrders1);
//                                                    adapter = new ChefOrderTobePreparedAdapter(ChefOrderTobePrepared.this, chefWaitingOrders1List);
//                                                    recyclerView.setAdapter(adapter);

//                                                    CustomerHistory1 aggregatedDish = new CustomerHistory1(gadd, gchefid, gchefname, gprice, gmob, gcustomername, grandomuid, gdate);
                                                    aggregatedDateList.add(CustomerHistory1);
                                                    Collections.sort(aggregatedDateList);

                                                    // Reverse to show latest to oldest
                                                    Collections.reverse(aggregatedDateList);
                                                    adapter = new CustomerFullHistoryViewDateAdapter(CustomerFullHistoryDateView.this, aggregatedDateList);
                                                    recyclerViewdish.setAdapter(adapter);


//                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                                }

                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


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


                }


                    //



            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem6 = menu.findItem(R.id.Searchdish);
        searchView6 = (SearchView) menuItem6.getActionView();
//        searchView6.setQueryHint("Date(yyyymmdd),Price,Store,Address,Mob,Name,SellerName");
        searchView6.setQueryHint("Date(yyyymmdd),Price,Store,Address,Mob,Name,SellerName");

        searchView6.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return true;
            }
        });

        return true;
    }

    private void search(final String searchtext) {

        ArrayList<CustomerHistory1> mylist6 = new ArrayList<>();
        for (CustomerHistory1 object6 : aggregatedDateList) {
            if (object6.getFinishdate().toLowerCase().contains(searchtext.toLowerCase())  ||
                    object6.getGrandTotalPrice().toLowerCase().contains(searchtext.toLowerCase()) ||
                    object6.getAddress().toLowerCase().contains(searchtext.toLowerCase()) ||
                    object6.getMobileNumber().toLowerCase().contains(searchtext.toLowerCase()) ||
                    object6.getName().toLowerCase().contains(searchtext.toLowerCase()) ||
                    object6.getChefName().toLowerCase().contains(searchtext.toLowerCase()) ||
                    object6.getCompanyName().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist6.add(object6);
            }
        }
        adapter = new CustomerFullHistoryViewDateAdapter(CustomerFullHistoryDateView.this, mylist6);
        recyclerViewdish.setAdapter(adapter);

    }
    
    
    
    
    
    
}
