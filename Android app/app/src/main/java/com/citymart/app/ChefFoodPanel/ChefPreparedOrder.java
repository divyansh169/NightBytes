package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.citymart.app.CustomerFoodPanel.CustomerHomeAdapter;
import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChefPreparedOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ChefFinalOrders1> chefFinalOrders1List;
    private ChefPreparedOrderAdapter adapter;
    private DatabaseReference databaseReference;
    SearchView searchView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_prepared_order);
        recyclerView = findViewById(R.id.Recycle_preparedOrders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChefPreparedOrder.this));
        chefFinalOrders1List = new ArrayList<>();
        ChefPrepareOrders();
    }

    private void ChefPrepareOrders() {

        databaseReference = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefFinalOrders1List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                    data.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                            chefFinalOrders1List.add(chefFinalOrders1);
                            adapter = new ChefPreparedOrderAdapter(ChefPreparedOrder.this, chefFinalOrders1List);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
//                    searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                        @Override
//                        public boolean onQueryTextSubmit(String query) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onQueryTextChange(String newText) {
//                            search(newText);
//                            return true;
//                        }
//                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem menuItem2 = menu.findItem(R.id.Searchdish);
        searchView2 = (SearchView) menuItem2.getActionView();
        searchView2.setQueryHint("Search Address,Name,Mob,Note");

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

        ArrayList<ChefFinalOrders1> mylist2 = new ArrayList<>();
        for (ChefFinalOrders1 object2 : chefFinalOrders1List) {
            if (object2.getAddress().toLowerCase().contains(searchtext.toLowerCase())  || object2.getName().toLowerCase().contains(searchtext.toLowerCase()) || object2.getMobileNumber().toLowerCase().contains(searchtext.toLowerCase()) || object2.getNote().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist2.add(object2);
            }
        }
        adapter = new ChefPreparedOrderAdapter(ChefPreparedOrder.this, mylist2);
        recyclerView.setAdapter(adapter);

    }


//    private void search(final String searchtext) {
//
//        ArrayList<ChefFinalOrders1> mylist2a = new ArrayList<>();
//        for (ChefFinalOrders1 object2 : chefFinalOrders1List) {
//            if (object2.getAddress().toLowerCase().contains(searchtext.toLowerCase())) {
//                mylist2a.add(object2);
//            }
//        }
//        adapter = new ChefPreparedOrderAdapter(this, mylist2a);
//        recyclerView.setAdapter(adapter);
//
//    }


//    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.search, menu);
//        MenuItem menuItem2 = menu.findItem(R.id.Searchdish);
//        searchView2 = (SearchView) menuItem2.getActionView();
//        searchView2.setQueryHint("Search by Address");
//
//
//    }


}
