package com.citymart.app.ChefFoodPanel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ChefPendingOrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<ChefPendingOrders1> chefPendingOrders1List;
    private ChefPendingOrdersAdapter adapter;
    SearchView searchView;
    private DatabaseReference databaseReference, dtbref;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static int pendingorderscount=0;
    public String usertoken;
    private View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Pending Orders");
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.fragment_chef_pendingorders, null);
        recyclerView = v.findViewById(R.id.Recycle_orders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chefPendingOrders1List = new ArrayList<>();
        swipeRefreshLayout = v.findViewById(R.id.Swipe_layoutt);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
        adapter = new ChefPendingOrdersAdapter(getContext(),chefPendingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chefPendingOrders1List.clear();
                recyclerView = v.findViewById(R.id.Recycle_orders);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                chefPendingOrders1List = new ArrayList<>();
                cheforders();
            }
        });
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
        cheforders();
        return v;
    }

    private void cheforders() {






        databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                chefPendingOrders1List.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    pendingorderscount = pendingorderscount + 1;
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ChefPendingOrders1 chefPendingOrders1 = dataSnapshot.getValue(ChefPendingOrders1.class);
                            chefPendingOrders1List.add(chefPendingOrders1);
                            adapter = new ChefPendingOrdersAdapter(getContext(),chefPendingOrders1List);
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                            //....................
//                            int count = 0; // Counter to keep track of added items
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                if (count < 2) {
//                                    ChefPendingOrders1 chefPendingOrders1 = snapshot.getValue(ChefPendingOrders1.class);
//                                    chefPendingOrders1List.add(chefPendingOrders1);
//                                    ++count;
//                                } else {
//                                    break; // Exit the loop after adding 2 items
//                                }
//                            }
//                        }
                            //....................
                            //..........................
//                            chefPendingOrders1List.clear();
//                            for (DataSnapshot dSnapshot : dataSnapshot.getChildren()) {
//                                ChefPendingOrders1 chefPendingOrders1 = dSnapshot.getValue(ChefPendingOrders1.class);
//                                if (chefPendingOrders1List.size() <= 2) {
//                                    chefPendingOrders1List.add(chefPendingOrders1);
//
//                                    adapter = new ChefPendingOrdersAdapter(getContext(), chefPendingOrders1List);
//                                    recyclerView.setAdapter(adapter);
//                                    swipeRefreshLayout.setRefreshing(true);
//                                }
//                            }
//                        }
                        //...........................
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
//                    DatabaseReference fdataa = FirebaseDatabase.getInstance().getReference().child("ChefPendingOrdersCount").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    Type9 type9 = new Type9(String.valueOf(pendingorderscount));
//                    fdataa.setValue(type9);
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void search(final String searchtext) {

        ArrayList<ChefPendingOrders1> mylist = new ArrayList<>();
        for (ChefPendingOrders1 object : chefPendingOrders1List) {
            if (object.getAddress().toLowerCase().contains(searchtext.toLowerCase())  || object.getName().toLowerCase().contains(searchtext.toLowerCase()) || object.getMobileNumber().toLowerCase().contains(searchtext.toLowerCase()) || object.getNote().toLowerCase().contains(searchtext.toLowerCase()) ) {
                mylist.add(object);
            }
        }
        adapter = new ChefPendingOrdersAdapter(getContext(),mylist);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem menuItem = menu.findItem(R.id.Searchdish);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Address,Name,Mob,Note");
    }

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


}