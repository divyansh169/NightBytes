package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefOrderTobePrepared extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ChefWaitingOrders1> chefWaitingOrders1List;
    private ChefOrderTobePreparedAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_order_tobe_prepared);
        recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChefOrderTobePrepared.this));
        chefWaitingOrders1List = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.Swipe1);
//        setHasOptionsMenu(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
        adapter = new ChefOrderTobePreparedAdapter(ChefOrderTobePrepared.this, chefWaitingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chefWaitingOrders1List.clear();
                recyclerView = findViewById(R.id.Recycle_orderstobeprepared);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChefOrderTobePrepared.this));
                chefWaitingOrders1List = new ArrayList<>();
                cheforderstobePrepare();
            }
        });
        cheforderstobePrepare();

    }

    private void cheforderstobePrepare() {

        databaseReference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() { //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    chefWaitingOrders1List.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
                                chefWaitingOrders1List.add(chefWaitingOrders1);
                                adapter = new ChefOrderTobePreparedAdapter(ChefOrderTobePrepared.this, chefWaitingOrders1List);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
//                        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                search(newText);
//                return true;
//            }
//        });
    }



//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search, menu);
//        MenuItem menuItem1 = menu.findItem(R.id.Searchdish);
//        searchView1 = (SearchView) menuItem1.getActionView();
//        searchView1.setQueryHint("Search Address,Name,Mob,Note");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem1 = menu.findItem(R.id.Searchdish);
        searchView1 = (SearchView) menuItem1.getActionView();
        searchView1.setQueryHint("Search Address,Name,Mob,Note");

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<ChefWaitingOrders1> mylist1 = new ArrayList<>();
        for (ChefWaitingOrders1 object1 : chefWaitingOrders1List) {
            if (object1.getAddress().toLowerCase().contains(searchtext.toLowerCase())  || object1.getName().toLowerCase().contains(searchtext.toLowerCase()) || object1.getMobileNumber().toLowerCase().contains(searchtext.toLowerCase()) || object1.getNote().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist1.add(object1);
            }
        }
        adapter = new ChefOrderTobePreparedAdapter(ChefOrderTobePrepared.this, mylist1);
        recyclerView.setAdapter(adapter);

    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        int idd = item.getItemId();
//        if (idd == R.id.Searchdish) {
//            search();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }





//    private void search(final String searchtext) {
//
//        ArrayList<ChefWaitingOrders1> mylist1aa = new ArrayList<>();
//        for (ChefWaitingOrders1 object1 : chefWaitingOrders1List) {
//            if (object1.getAddress().toLowerCase().contains(searchtext.toLowerCase())) {
//                mylist1aa.add(object1);
//            }
//        }
//        adapter = new ChefOrderTobePreparedAdapter(this, mylist1aa);
//        recyclerView.setAdapter(adapter);
//
//    }


//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search, menu);
//
//        MenuItem menuItem1 = menu.findItem(R.id.Searchdish);
//        searchView1 = (SearchView) menuItem1.getActionView();
//        searchView1.setQueryHint("Search by Address");
//
//
//    }



}
