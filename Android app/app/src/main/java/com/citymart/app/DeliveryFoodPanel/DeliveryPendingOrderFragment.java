package com.citymart.app.DeliveryFoodPanel;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.citymart.app.MainMenu;
import com.citymart.app.R;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeliveryPendingOrderFragment extends Fragment {

    private static final int MY_REQUEST_CODE = 103;
    private RecyclerView recyclerView;
    private List<DeliveryShipOrders1> deliveryShipOrders1List;
    private DeliveryPendingOrderFragmentAdapter adapter;
    private DatabaseReference databaseReference;
    private SwipeRefreshLayout swipeRefreshLayout;
//    public static String deliveryidd, adddruss, addor;
//    String deliveryId = "Kwrnzmus0gcTOEOPNr4tu3YnBq22";
    public static String suburbln;
    public static String deliveryId = FirebaseAuth.getInstance().getCurrentUser().getUid();;
//    public static String deliveryId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_deliverypendingorder, null);
        getActivity().setTitle("Pending Orders");
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.delipendingorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        deliveryShipOrders1List = new ArrayList<>();
        swipeRefreshLayout = view.findViewById(R.id.Swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
        adapter = new DeliveryPendingOrderFragmentAdapter(getContext(), deliveryShipOrders1List);
        recyclerView.setAdapter(adapter);
        FirebaseMessaging.getInstance().subscribeToTopic("Deliver_notif");
        FirebaseMessaging.getInstance().subscribeToTopic("User_notif");
        Checkforappupdate();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                deliveryShipOrders1List.clear();
                recyclerView = view.findViewById(R.id.delipendingorder);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                deliveryShipOrders1List = new ArrayList<>();
                DeliveryPendingOrders();
            }
        });
        DeliveryPendingOrders();

        return view;
    }

    private void DeliveryPendingOrders() {

//        DatabaseReference datups = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Suburban");
//        datups.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                Chef chef = dataSnapshot.getValue(Chef.class);
////                addor = chef.getSuburban();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                    addor = (String) snapshot.getValue();
//                }
//
//                DatabaseReference dataall = FirebaseDatabase.getInstance().getReference().child("addordetails");
//                Type10 type10 = new Type10(deliveryidd, addor);
//                dataall.setValue(type10);
//
//
//        DatabaseReference datup = FirebaseDatabase.getInstance().getReference("deliveryDetails");
//        datup.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type8 type8 = dataSnapshot.getValue(Type8.class);
//                deliveryidd = type8.getdeliveryId();
//
//                DatabaseReference datupp = FirebaseDatabase.getInstance().getReference("deliveryDetails").child(deliveryidd);
//                datupp.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Type9 type9 = dataSnapshot.getValue(Type9.class);
//                        adddruss = type9.getaddrus();

//        DatabaseReference databaseReferencpoe = FirebaseDatabase.getInstance().getReference("deliveryshipper").child((FirebaseAuth.getInstance().getCurrentUser().getUid())).child("suburbanus");
//        databaseReferencpoe.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type9 type9 = dataSnapshot.getValue(Type9.class);
//                suburbln = type9.getsuburbanus();


//                deliveryId= FirebaseAuth.getInstance().getCurrentUser().getUid();


//        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(deliveryidd);


//        DatabaseReference databaseReferencemk = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders");
//        databaseReferencemk.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (DataSnapshot snapshotop : dataSnapshot.getChildren()) {
//
//
//                        DatabaseReference databaseReferencemkll = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(snapshotop.getKey());
//                        databaseReferencemkll.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    for (DataSnapshot snapshotopt : dataSnapshot.getChildren()) {
//
//
//
//                                    if (Objects.equals(snapshotopt.getKey(), deliveryId)){


                        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    deliveryShipOrders1List.clear();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                DeliveryShipOrders1 deliveryShipOrders1 = dataSnapshot.getValue(DeliveryShipOrders1.class);
                                                deliveryShipOrders1List.add(deliveryShipOrders1);
                                                adapter = new DeliveryPendingOrderFragmentAdapter(getContext(), deliveryShipOrders1List);
                                                recyclerView.setAdapter(adapter);
                                                swipeRefreshLayout.setRefreshing(false);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                } else {
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


//                                    }
//                                    }
//                                }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//                    }
//
//                    //.................
//
//
//
//
//        //..................................
//
//
//
//
//
//                }
//
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});

//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idd = item.getItemId();
        if (idd == R.id.LogOut) {
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }


    private void Checkforappupdate(){
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getActivity());

// Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // This example applies an immediate update. To apply a flexible update
                    // instead, pass in AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                try {
                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            AppUpdateType.IMMEDIATE,
                            getActivity(),
                            MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {

            }
        }
    }


}
