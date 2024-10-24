package com.citymart.app.CustomerFoodPanel;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.citymart.app.ChefFoodPanel.UpdateDishModel;
import com.citymart.app.ChefFoodPanel.UpdateDishModel_bannerlist;
import com.citymart.app.Customer;

import com.citymart.app.CustomerFoodPanel_BottomNavigation;
import com.citymart.app.Login;
import com.citymart.app.R;
import com.citymart.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private static final int MY_REQUEST_CODE = 107;
    RecyclerView recyclerView;
    RecyclerView recyclerView_banner;
    private List<UpdateDishModel> updateDishModelList;
    private List<UpdateDishModel_bannerlist> updateDishModelList_bannerlist;
//    List<Integer> imageList = new ArrayList<>();
    private CustomerHomeAdapter adapter;
    private CustomerHomeAdapter_bannerlist offeroadapter;
//    private OfferAdapter offerAdapter;
    String State, City, Sub;
    DatabaseReference dataaa, databaseReference;
    SwipeRefreshLayout swipeRefreshLayout;
    SearchView searchView;
    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customerhome, null);
        getActivity().setTitle("NightBytes");
//        getActivity().getWindow().setBackgroundDrawable(getActivity().getColor(R.color.blueish));
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.recycle_menu);
        recyclerView_banner = v.findViewById(R.id.recycle_menu_banner);
        recyclerView.setHasFixedSize(true);
        recyclerView_banner.setHasFixedSize(true);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.move);
        recyclerView.startAnimation(animation);
        dialog= new Dialog(getActivity());
        recyclerView_banner.startAnimation(animation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView_banner.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        updateDishModelList = new ArrayList<>();
        updateDishModelList_bannerlist = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipelayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);

        dialog.setContentView(R.layout.activity_wait);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1000); // 5000 milliseconds (5 seconds)


        Checkforappupdate();
//        Checkforbirthdate();
//        FirebaseMessaging.getInstance().subscribeToTopic("Centralnews");


        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                dataaa = FirebaseDatabase.getInstance().getReference("Customer").child(userid);
                dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            if(dataSnapshot.hasChild("Suburban")) {
                                Customer cust = dataSnapshot.getValue(Customer.class);
                                State = cust.getState();
                                City = cust.getCity();
                                Sub = cust.getSuburban();
                                getActivity().setTitle(Sub);

                                String state_notif = cust.getState().replaceAll("\\s", "");
                                FirebaseMessaging.getInstance().subscribeToTopic(state_notif);
                                FirebaseMessaging.getInstance().subscribeToTopic("Customer"+state_notif);
                                String city_notif = cust.getCity().replaceAll("\\s", "");
                                FirebaseMessaging.getInstance().subscribeToTopic(city_notif);
                                FirebaseMessaging.getInstance().subscribeToTopic("Customer"+city_notif);
                                String suburban_notif = cust.getSuburban().replaceAll("\\s", "");
                                FirebaseMessaging.getInstance().subscribeToTopic(suburban_notif);
                                FirebaseMessaging.getInstance().subscribeToTopic("Customer"+suburban_notif);
                                FirebaseMessaging.getInstance().subscribeToTopic("Customer_notif");
                                FirebaseMessaging.getInstance().subscribeToTopic("User_notif");

                                customermenu();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        return v;
    }


    @Override
    public void onRefresh() {

        customermenu();
    }

    private void customermenu() {






        //..........................................................................

//        List<Integer> imageList = new ArrayList<>();

//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);
//        imageList.add(R.drawable.lnmiitimage);

//        offerAdapter = new OfferAdapter(imageList);

//        recyclerView_banner.setAdapter(offerAdapter);

        swipeRefreshLayout.setRefreshing(true);
        databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner").child(State).child(City).child(Sub);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    updateDishModelList_bannerlist.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            UpdateDishModel_bannerlist updateDishModel_bannerlist = snapshot1.getValue(UpdateDishModel_bannerlist.class);
                            updateDishModelList_bannerlist.add(updateDishModel_bannerlist);
                        }
                    }
                    offeroadapter = new CustomerHomeAdapter_bannerlist(getContext(), updateDishModelList_bannerlist);
                    recyclerView_banner.setAdapter(offeroadapter);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        //..........................................................................

        swipeRefreshLayout.setRefreshing(true);

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    updateDishModelList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            UpdateDishModel updateDishModel = snapshot1.getValue(UpdateDishModel.class);
                            updateDishModelList.add(updateDishModel);
                        }
                    }
                    adapter = new CustomerHomeAdapter(getContext(), updateDishModelList);
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    }

    private void search(final String searchtext) {

        ArrayList<UpdateDishModel> mylist = new ArrayList<>();
        for (UpdateDishModel object : updateDishModelList) {
            if (object.getDishes().toLowerCase().contains(searchtext.toLowerCase()) || object.getPrice().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist.add(object);
            }
        }
        adapter = new CustomerHomeAdapter(getContext(), mylist);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.Searchdish);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Product,Price");


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
