package com.citymart.app.CustomerFoodPanel;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {

    private Spinner dateSpinner;
    private RecyclerView orderRecyclerView;
    private TextView noOrdersTextView;
    private OrderAdapter orderAdapter;
    private List<Order> orderList = new ArrayList<>();
    private List<String> availableDates = new ArrayList<>();

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        dateSpinner = findViewById(R.id.dateSpinner);
        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        noOrdersTextView = findViewById(R.id.noOrdersTextView);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(orderList);
        orderRecyclerView.setAdapter(orderAdapter);

        fetchAvailableDates();
    }

    private void fetchAvailableDates() {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference datesRef = mDatabase.child("CustomerHistory").child(userId);

        datesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                availableDates.clear();
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    availableDates.add(date);
                }
                setupDateSpinner();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void setupDateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availableDates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDate = availableDates.get(position);
                fetchOrderHistory(selectedDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void fetchOrderHistory(String selectedDate) {
        String userId = mAuth.getCurrentUser().getUid();
        DatabaseReference ordersRef = mDatabase.child("CustomerHistory").child(userId).child(selectedDate);

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                orderList.clear();
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    orderList.add(order);
                }

                if (orderList.isEmpty()) {
                    noOrdersTextView.setVisibility(View.VISIBLE);
                    orderRecyclerView.setVisibility(View.GONE);
                } else {
                    noOrdersTextView.setVisibility(View.GONE);
                    orderRecyclerView.setVisibility(View.VISIBLE);
                }

                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }
}
