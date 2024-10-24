package com.citymart.app.ChefFoodPanel;

import android.graphics.Color;
import android.os.Bundle;
import java.util.Collections;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.DeliveryFoodPanel.DeliveryShipOrders1;
import com.citymart.app.R;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class ChefFullHistoryDateView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
//    private List<ChefWaitingOrders> chefWaitingFullOrdersList;
//    private List<ChefHistory> aggregatedDishesList;
    private List<ChefHistory1> aggregatedDateList;
    private ChefFullHistoryViewDateAdapter adapter;
    SearchView searchView5;

    private APIService apiService;
    public static String gprice, gdate;
    public int gp=0;
    public static int quantity;
    public static int pricey;
    public static int totpy;
//    public BarChart chart1, chart2, chart3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_fullhistorydateview);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
//        BarChart chart1 = findViewById(R.id.chart1);
//        BarChart chart2 = findViewById(R.id.chart2);
//        BarChart chart3 = findViewById(R.id.chart3);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        recyclerViewdish.setHasFixedSize(true);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(ChefFullHistoryDateView.this));
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

        DatabaseReference moddatabaseReference = FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        moddatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {   //thisy valye to single
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
                                     DatabaseReference moddatabaseReferencex3 = FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshotdate.getKey());
                                                    moddatabaseReferencex3.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.exists()) {
//                                                                aggregatedDishesList.clear();

//                                                                Map<String, Integer> dishQuantities = new HashMap<>();
//                                                                Map<String, Integer> dishTotalPrices = new HashMap<>();

                                                                for (DataSnapshot snapshotrandomuids : dataSnapshot.getChildren()) {

//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        DatabaseReference dataz = FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshotdate.getKey()).child(snapshotrandomuids.getKey()).child("OtherInformation");
                                        dataz.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if (dataSnapshot.exists()) {
//                                                    aggregatedDishesList.clear();
//                                                    for (DataSnapshot snapshot5 : dataSnapshot.getChildren()) {
                                                    ChefHistory1 ChefHistory1 = dataSnapshot.getValue(ChefHistory1.class);
                                                        gprice = ChefHistory1.getGrandTotalPrice();
                                                        gdate = ChefHistory1.getFinishdate();
                                                        gp = Integer.parseInt(gprice);

                                                    if (dishdates.containsKey(gdate)) {
                                                        int currentQuantity = dishdates.get(gdate);
//                                                        int currentTotalPrice = dishTotalPrices.get(dishName);
                                                        dishdates.put(gdate, currentQuantity + gp);
//                                                        dishTotalPrices.put(dishName, pricey);
                                                    } else {
                                                        dishdates.put(gdate, gp);
//                                                        dishTotalPrices.put(gdate, pricey);
                                                    }
                                                }

                                                aggregatedDateList.clear(); //i have chnaged this to top

                                                for (Map.Entry<String, Integer> entry : dishdates.entrySet()) {
                                                    String gdate = entry.getKey();
                                                    int gpp = entry.getValue();
//                                                                    int totka = dishTotalPrices.get(dishName); //indiv price in int
//                                                                    String totalPrice = String.valueOf(totka);  //indiv price in string
                                                    String quant = String.valueOf(gpp);
//                                                                    String totp = String.valueOf(totka*quantity);
//                                                    String pri = String.valueOf(totalPrice/quantity);
                                                    ChefHistory1 aggregatedDish = new ChefHistory1(gdate, quant);
                                                    aggregatedDateList.add(aggregatedDish);

                                                }
                                                
                                                Collections.sort(aggregatedDateList, Collections.reverseOrder());

                                                adapter = new ChefFullHistoryViewDateAdapter(ChefFullHistoryDateView.this, aggregatedDateList);
                                                recyclerViewdish.setAdapter(adapter);


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
//                                    }

//                                                                    String totgp = String.valueOf(gp);
//                                                                    ChefHistory1 aggregatedDate = new ChefHistory1(gdate, totgp);
//                                                                    aggregatedDateList.add(aggregatedDate);

                                                }
//


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
        MenuItem menuItem5 = menu.findItem(R.id.Searchdish);
        searchView5 = (SearchView) menuItem5.getActionView();
        searchView5.setQueryHint("Date(yyyymmdd),Price");

        searchView5.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<ChefHistory1> mylist5 = new ArrayList<>();
        for (ChefHistory1 object5 : aggregatedDateList) {
            if (object5.getFinishdate().toLowerCase().contains(searchtext.toLowerCase())  || object5.getGrandTotalPrice().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist5.add(object5);
            }
        }
        adapter = new ChefFullHistoryViewDateAdapter(ChefFullHistoryDateView.this, mylist5);
        recyclerViewdish.setAdapter(adapter);

    }




}
