package com.citymart.app.ChefFoodPanel;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioAttributes;
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
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.citymart.app.Chef;
import com.citymart.app.CustomerFoodPanel.CustomerHomeAdapter;
import com.citymart.app.MainMenu;
import androidx.recyclerview.widget.RecyclerView;
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


public class ChefHomeFragment extends Fragment {


    private static final int MY_REQUEST_CODE = 105;
    RecyclerView recyclerView;
    private List<UpdateDishModel> updateDishModelList;
    private ChefhomeAdapter adapter;
    DatabaseReference dataaa, dtbref;
    private String State, City, Sub;
    SearchView searchView;
    Dialog dialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_home, null);
        getActivity().setTitle("NightBytes");
        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.Recycle_menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateDishModelList = new ArrayList<>();
        dialog= new Dialog(getActivity());
        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = FirebaseDatabase.getInstance().getReference("Chef").child(userid);
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
        //''''''''''''''''''''''''''''''''
//        dtbref = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//
//        dtbref.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                showNotification();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        //''''''''''''''''''''''''''''''''
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    if (dataSnapshot.hasChild("Suburban")) {
                        Chef chefc = dataSnapshot.getValue(Chef.class);
                        State = chefc.getState();
                        City = chefc.getCity();
                        Sub = chefc.getSuburban();
                        getActivity().setTitle(Sub);

                        String state_notif = chefc.getState().replaceAll("\\s", "");
                        FirebaseMessaging.getInstance().subscribeToTopic(state_notif);
                        FirebaseMessaging.getInstance().subscribeToTopic("Chef"+state_notif);
                        String city_notif = chefc.getCity().replaceAll("\\s", "");
                        FirebaseMessaging.getInstance().subscribeToTopic(city_notif);
                        FirebaseMessaging.getInstance().subscribeToTopic("Chef"+city_notif);
                        String suburban_notif = chefc.getSuburban().replaceAll("\\s", "");
                        FirebaseMessaging.getInstance().subscribeToTopic(suburban_notif);
                        FirebaseMessaging.getInstance().subscribeToTopic("Chef"+suburban_notif);
                        FirebaseMessaging.getInstance().subscribeToTopic("Chef_notif");
                        FirebaseMessaging.getInstance().subscribeToTopic("User_notif");



                        chefDishes();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return v;
    }


    private void chefDishes() {



        String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(useridd);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                updateDishModelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UpdateDishModel updateDishModel = snapshot.getValue(UpdateDishModel.class);
                    updateDishModelList.add(updateDishModel);

                }
                adapter = new ChefhomeAdapter(getContext(), updateDishModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
            if (object.getDishes().toLowerCase().contains(searchtext.toLowerCase())  ||  object.getprodatt().toLowerCase().contains(searchtext.toLowerCase()) ||  object.getPrice().toLowerCase().contains(searchtext.toLowerCase())  ) {
                mylist.add(object);
            }
        }
        adapter = new ChefhomeAdapter(getContext(), mylist);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.logout, menu);

        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.Searchdish);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Product,Attribute,Price");
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int idd = item.getItemId();
//        if (idd == R.id.LogOut) {
//            Logout();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void Logout() {
//
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(getActivity(), MainMenu.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
//        startActivity(intent);
//
//    }

    private void showNotification()
    {

        Uri sound = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.sound);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(requireContext(), "default_notification_channel_id" )
                .setSmallIcon(R.drawable.ic_menu_black_24dp )
                .setContentTitle( "New Order" )
                .setSound(sound)
                .setContentText( "You have a New Order" ) ;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context. NOTIFICATION_SERVICE );
        NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes. CONTENT_TYPE_SONIFICATION )
                    .setUsage(AudioAttributes. USAGE_ALARM )
                    .build() ;
            int importance = NotificationManager. IMPORTANCE_HIGH ;

            //***************
//            apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//                @Override
//                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                    if (response.code() == 200) {
//                        if (response.body().success != 1) {
//                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<MyResponse> call, Throwable t) {
//
//                }
//            });
            //***************
            NotificationChannel notificationChannel = new NotificationChannel( "NOTIFICATION_CHANNEL_ID" , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationChannel.enableLights( true ) ;
            notificationChannel.setLightColor(Color. RED ) ;
            notificationChannel.enableVibration( true ) ;
            notificationChannel.setVibrationPattern( new long []{ 100 , 200 , 300 , 400 , 500 , 400 , 300 , 200 , 400 }) ;
            notificationChannel.setSound(sound , audioAttributes) ;
            mBuilder.setChannelId( "NOTIFICATION_CHANNEL_ID" ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }

//        else {
        //***************
//            apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//                @Override
//                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                    if (response.code() == 200) {
//                        if (response.body().success != 1) {
//                            Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
//                        }
//
////                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "default")
////                                .setSmallIcon(R.drawable.ic_menu_black_24dp)
////                                .setContentTitle(title)
////                                .setContentText(message)
////                                .setPriority(NotificationCompat.PRIORITY_HIGH)
////                                .setSound(sound)
////                                .setAutoCancel(true);
////
////                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
////                        notificationManager.notify(0, builder.build());
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<MyResponse> call, Throwable t) {
//
//                }
//            });
        //***************
//        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () ,
                mBuilder.build()) ;
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
