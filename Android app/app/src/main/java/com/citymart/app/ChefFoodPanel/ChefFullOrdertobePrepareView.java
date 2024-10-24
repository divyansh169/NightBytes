package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.CustomerFoodPanel.CustomerFinalOrders;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.citymart.app.SendNotification.Data;
import com.citymart.app.SendNotification.MyResponse;
import com.citymart.app.SendNotification.NotificationSender;

import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.citymart.app.R;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefFullOrdertobePrepareView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<ChefWaitingOrders> chefWaitingFullOrdersList;
    private List<ChefWaitingOrders2> aggregatedDishesList;
    private ChefFullOrdertobePrepareViewAdapter adapter;
    SearchView searchView3;

    private APIService apiService;
    public static String dname, dquant, dishName;
    public static int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_full_ordertobe_prepare_view);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        recyclerViewdish.setHasFixedSize(true);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(ChefFullOrdertobePrepareView.this));
        chefWaitingFullOrdersList = new ArrayList<>();
        aggregatedDishesList = new ArrayList<>();
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
//                            adapter = new ChefFullOrdertobePrepareViewAdapter(ChefFullOrdertobePrepareView.this, chefWaitingFullOrdersList);
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
        DatabaseReference moddatabaseReference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        moddatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  ////thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Create a map to store the aggregated dish quantities
                    Map<String, Integer> dishQuantities = new HashMap<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference dataz = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
                        dataz.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        ChefWaitingOrders2 chefWaitingOrders2 = snapshot1.getValue(ChefWaitingOrders2.class);
                                        dishName = chefWaitingOrders2.getDishName();
                                        quantity = Integer.valueOf(chefWaitingOrders2.getDishQuantity());

                                        // Update the aggregated quantity for this dish
                                        if (dishQuantities.containsKey(dishName)) {
                                            int currentQuantity = dishQuantities.get(dishName);
                                            dishQuantities.put(dishName, currentQuantity + quantity);
                                        } else {
                                            dishQuantities.put(dishName, quantity);
                                        }
                                    }
                                }
                                // Now dishQuantities contains the aggregated quantities of each dish
                                // You can update your UI or adapter with this data
//                                updateUIWithDishQuantities(dishQuantities);
//                                adapter = new ChefFullOrdertobePrepareViewAdapter(ChefFullOrdertobePrepareView.this, chefWaitingFullOrdersList);
//                                recyclerViewdish.setAdapter(adapter);
                                // Create a list from the dishQuantities map

                                aggregatedDishesList.clear();

                                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                                    String dishName = entry.getKey();
                                    int quantity = entry.getValue();
                                    String quant = String.valueOf(quantity);
                                    ChefWaitingOrders2 aggregatedDish = new ChefWaitingOrders2(dishName, quant);
                                    aggregatedDishesList.add(aggregatedDish);
                                }

// Create a new adapter with the aggregated data
                                adapter = new ChefFullOrdertobePrepareViewAdapter(ChefFullOrdertobePrepareView.this, aggregatedDishesList);
                                recyclerViewdish.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        searchView3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                            @Override
//                            public boolean onQueryTextSubmit(String query) {
//                                return false;
//                            }
//
//                            @Override
//                            public boolean onQueryTextChange(String newText) {
//                                search(newText);
//                                return true;
//                            }
//                        });

                    }
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
//                adapter = new ChefFullOrdertobePrepareViewAdapter(ChefFullOrdertobePrepareView.this, chefWaitingFullOrdersList);
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
        MenuItem menuItem3 = menu.findItem(R.id.Searchdish);
        searchView3 = (SearchView) menuItem3.getActionView();
        searchView3.setQueryHint("Search Product Name,Quantity");

        searchView3.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<ChefWaitingOrders2> mylist3 = new ArrayList<>();
        for (ChefWaitingOrders2 object3 : aggregatedDishesList) {
            if (object3.getDishName().toLowerCase().contains(searchtext.toLowerCase())  || object3.getDishQuantity().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist3.add(object3);
            }
        }
        adapter = new ChefFullOrdertobePrepareViewAdapter(ChefFullOrdertobePrepareView.this, mylist3);
        recyclerViewdish.setAdapter(adapter);

    }






}
