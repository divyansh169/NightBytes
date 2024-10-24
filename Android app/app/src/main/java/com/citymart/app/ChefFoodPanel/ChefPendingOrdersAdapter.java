package com.citymart.app.ChefFoodPanel;

import android.animation.LayoutTransition;
import android.content.Context;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.citymart.app.ReusableCode.ReusableCodeForAll;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.citymart.app.SendNotification.Data;
import com.citymart.app.SendNotification.MyResponse;
import com.citymart.app.SendNotification.NotificationSender;

import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefPendingOrdersAdapter extends RecyclerView.Adapter<ChefPendingOrdersAdapter.ViewHolder> {

    private Context context;
    private List<ChefPendingOrders1> chefPendingOrders1list;
    private APIService apiService;
    private ProgressDialog progressDialog;
    String userid, dishid;
    public static String fprice, automaticflag;
//    public static String usermobyyy;
    public static String custpod, custdelch, custdisc ;
    public int fl=0;


//    RecyclerView recyclerViewdish;
//    private List<ChefPendingOrders> chefPendingOrdersListing;
//    public List <String> dishnamesall;
//    private ChefPendingOrdersAdapter adapter;
////    DatabaseReference reference;
////    String RandomUID;

    public Button paydlbtn, nopaybtn;
    public String  enablepodt_flag="10";
    public static int pendordercnt, pendcount;
    public static String fnumber, opk;
    public static String deliverychargetext, discnttext;
    public static String annn;
    public static String rzzz;
    public static String chefmessage;
//    String etaddr;


//    public String useriddd;
    public String ordertypeu;

    DatabaseReference databaseReference, dataa, kpopref;
    FirebaseDatabase firebaseDatabasee;
    ChefPendingOrdersFragment chefPendingOrdersFragment = new ChefPendingOrdersFragment();


    public ChefPendingOrdersAdapter(Context context, List<ChefPendingOrders1> chefPendingOrders1list) {
        this.chefPendingOrders1list = chefPendingOrders1list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chef_orders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new ChefPendingOrdersAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ChefPendingOrders1 chefPendingOrders1 = chefPendingOrders1list.get(position);
//        holder.Address.setText((chefPendingOrders1.getAddress()).substring(22));
//        holder.Address.setText((chefPendingOrders1.getAddress()).substring((chefPendingOrders1.getAddress()).indexOf(':') + 1).trim());

        String fullAddress = chefPendingOrders1.getAddress();
        int colonIndex = fullAddress.indexOf(':');
        int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
        int lastCommaIndex = fullAddress.lastIndexOf(',');
        String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
        String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

        holder.Address.setText(firstPart);
//        holder.ordtp.setText("Order Type: " + lastPart);

//        etaddr = chefPendingOrders1.getAddress();
        holder.grandtotalprice.setText("Total Price: ₹ " + chefPendingOrders1.getGrandTotalPrice());
        holder.NOTE2.setText(chefPendingOrders1.getNote());
        holder.nm2.setText(chefPendingOrders1.getName());
        holder.num2.setText("+91" + chefPendingOrders1.getMobileNumber());
        String usermobyyy= chefPendingOrders1.getMobileNumber();
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        String randomuidk = chefPendingOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Chef_order_dishes.class);
                intent.putExtra("RandomUID", randomuidk);
                context.startActivity(intent);
            }
        });



        //deliverycharge...........................................................................................................................
        DatabaseReference kjdk7 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliverycharge");
        kjdk7.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    custdelch = dataSnapshot.getValue(String.class);
                    holder.deliverychargetext.setText(custdelch);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //discount...........................................................................................................................
        DatabaseReference kjdk8 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("discount");
        kjdk8.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    custdisc = dataSnapshot.getValue(String.class);
                    holder.discnttext.setText(custdisc);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //enable pod
        DatabaseReference gty1 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("podoption");
        gty1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    custpod = dataSnapshot.getValue(String.class);

                    if (Objects.equals(custpod, "1")) {
                        enablepodt_flag = "1";
                        holder.paydlbtn.setVisibility(View.GONE);
                        holder.nopaybtn.setVisibility(View.VISIBLE);
                    }
                    else if (Objects.equals(custpod, "0")) {
                        enablepodt_flag = "10";
                        holder.paydlbtn.setVisibility(View.VISIBLE);
                        holder.nopaybtn.setVisibility(View.GONE);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.clforlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.lay1.getVisibility()==View.GONE){
                    holder.lay1.setVisibility(View.VISIBLE);
                }
                else if(holder.lay1.getVisibility()==View.VISIBLE){
                    holder.lay1.setVisibility(View.GONE);
                }
            }
        });
        holder.clforlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.lay2.getVisibility()==View.GONE){
                    holder.lay2.setVisibility(View.VISIBLE);
                }
                else if(holder.lay2.getVisibility()==View.VISIBLE){
                    holder.lay2.setVisibility(View.GONE);
                }
            }
        });
        holder.clforlay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.lay3.getVisibility()==View.GONE){
                    holder.lay3.setVisibility(View.VISIBLE);
                }
                else if(holder.lay3.getVisibility()==View.VISIBLE){
                    holder.lay3.setVisibility(View.GONE);
                }
            }
        });


        holder.Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etaddr = chefPendingOrders1.getAddress();

                Intent intent = new Intent (Intent.ACTION_VIEW,
//                        Uri.parse(etaddr));
//                        Uri.parse("google.navigation:q="+latitude+","+longitude+"&mode=l"));
//                        Uri.parse("google.navigation:q="+etaddr.substring(0,20)+"&mode=l"));
                        Uri.parse("google.navigation:q="+etaddr.substring(0, etaddr.indexOf(": "))+"&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
//                if (intent.resolveActivity(getPackageManager ()) != null) {
                context.startActivity(intent);
//                }

            }
        });
        holder.direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etaddr = chefPendingOrders1.getAddress();

                Intent intent = new Intent (Intent.ACTION_VIEW,
//                        Uri.parse(etaddr));
//                        Uri.parse("google.navigation:q="+latitude+","+longitude+"&mode=l"));
//                        Uri.parse("google.navigation:q="+etaddr.substring(0,20)+"&mode=l"));
                        Uri.parse("google.navigation:q="+etaddr.substring(0, etaddr.indexOf(": "))+"&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
//                if (intent.resolveActivity(getPackageManager ()) != null) {
                context.startActivity(intent);
//                }

            }
        });

        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);


        databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);

                    String useriddd = chefPendingOrders.getUserId();
                    String usermob = chefPendingOrders1.getMobileNumber();


                    holder.chitchat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, SellerChatActivity.class);
                            intent.putExtra("userId", useriddd);
                            intent.putExtra("usermobph", usermob);
                            context.startActivity(intent);
                        }
                    });

                    DatabaseReference datu = FirebaseDatabase.getInstance().getReference("ordertype").child(useriddd);
                    datu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {

                                Type7 type7 = dataSnapshot.getValue(Type7.class);
                                holder.ordertype.setText("Order Type: " + type7.getordermethod());
                                ordertypeu = type7.getordermethod();
                                if(!(ordertypeu.contains("Delivery"))){
                                    holder.deliverychargetext.setText("0");
                                }
                            }


                            DatabaseReference dathu = FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(useriddd);
                            dathu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){

                                    Type13 type13 = dataSnapshot.getValue(Type13.class);
                                    String fetchedDateTime = type13.getordertime();
                                    holder.ordertame.setText(fetchedDateTime);
//                                    holder.minago.setText(type13.getordertime());
//                                        displayTimeDifference(type13.getordertime());
//                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss", Locale.getDefault());
//                                        try {
//                                            Date dateFetched = sdf.parse(type13.getordertime());
//                                            Date currentDate = Calendar.getInstance().getTime();
//
//                                            long diffInMillis = currentDate.getTime() - dateFetched.getTime();
//                                            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
//                                            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
//                                            long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
//                                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
//                                            long diffInMonths = diffInDays / 30; // Approximate, not exact
//                                            long diffInYears = diffInDays / 365; // Approximate, not exact
//
//                                            String timeAgo;
//                                            if (diffInSeconds < 60) {
//                                                timeAgo = diffInSeconds + " sec ago";
//                                            } else if (diffInMinutes < 60) {
//                                                timeAgo = diffInMinutes + " min ago";
//                                            } else if (diffInHours < 24) {
//                                                timeAgo = diffInHours + " hrs ago";
//                                            } else if (diffInDays < 30) {
//                                                timeAgo = diffInDays + " days ago";
//                                            } else if (diffInMonths < 12) {
//                                                timeAgo = diffInMonths + " months ago";
//                                            } else {
//                                                timeAgo = diffInYears + " years ago";
//                                            }
//
//                                            holder.minago.setText(timeAgo);
//                                        } catch (ParseException e) {
//                                            e.printStackTrace();
//                                        }
//                                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss", Locale.getDefault());
//                                            try {
//                                                Date dateFetched = sdf.parse(fettime);
//                                                Date currentDate = Calendar.getInstance().getTime();
//
//                                                long diffInMillis = currentDate.getTime() - dateFetched.getTime();
//                                                long diffInSeconds = diffInMillis / 1000;
//                                                long diffInMinutes = diffInSeconds / 60;
//                                                long diffInHours = diffInMinutes / 60;
//
//                                                String timeAgo;
//                                                if (diffInHours > 0) {
//                                                    timeAgo = diffInHours + " hrs ago";
//                                                } else if (diffInMinutes > 0) {
//                                                    timeAgo = diffInMinutes + " min ago";
//                                                } else {
//                                                    timeAgo = diffInSeconds + " sec ago";
//                                                }
//
//                                                holder.minago.setText(timeAgo);
//                                            } catch (ParseException e) {
//                                                e.printStackTrace();
//                                            }
//                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
//                                        Date fetchedDateTime = null;
//                                        try {
//                                            fetchedDateTime = sdf.parse(type13.getordertime());
//                                        } catch (ParseException e) {
//                                            e.printStackTrace();
//                                        }
//
//                                        if (fetchedDateTime != null) {
//                                            // Calculate time difference in milliseconds
//                                            long timeDifferenceMillis = System.currentTimeMillis() - fetchedDateTime.getTime();
//
//                                            // Convert milliseconds to seconds
//                                            long timeDifferenceSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis);
//
//                                            if (timeDifferenceSeconds < 60) {
//                                                // Less than a minute ago
//                                                holder.minago.setText(timeDifferenceSeconds + "s");
//                                            } else if (timeDifferenceSeconds < 3600) {
//                                                // Less than an hour ago
//                                                long minutes = TimeUnit.SECONDS.toMinutes(timeDifferenceSeconds);
//                                                holder.minago.setText(minutes + "m");
//                                            } else if (timeDifferenceSeconds < 86400) {
//                                                // Less than a day ago
//                                                long hours = TimeUnit.SECONDS.toHours(timeDifferenceSeconds);
//                                                holder.minago.setText(hours + "h");
//                                            } else if (timeDifferenceSeconds < 604800) {
//                                                // Less than a week ago
//                                                long days = TimeUnit.SECONDS.toDays(timeDifferenceSeconds);
//                                                holder.minago.setText(days + "d");
//                                            } else if (timeDifferenceSeconds < 2419200) {
//                                                // Less than a month ago
//                                                long weeks = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 7;
//                                                holder.minago.setText(weeks + "w");
//                                            } else if (timeDifferenceSeconds < 31536000) {
//                                                // Less than a year ago
//                                                long months = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 30;
//                                                holder.minago.setText(months + "M");
//                                            } else {
//                                                // More than a year ago
//                                                long years = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 365;
//                                                holder.minago.setText(years + "Y");
//                                            }
//                                        }

                                        // Calculate time difference
                                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
                                        try {
                                            Date orderDate = format.parse(fetchedDateTime);
                                            Date currentDate = new Date();

                                            long diffInMillis = currentDate.getTime() - orderDate.getTime();
                                            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                                            // Format the time difference
                                            String timeAgo = "";
                                            if (diffInSeconds < 60) {
                                                timeAgo = "Just now";
                                            } else {
                                                long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                                                long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
                                                long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;

                                                if (diffInDays > 0) {
                                                    timeAgo += diffInDays + "day ";
                                                }
                                                if (diffInHours > 0) {
                                                    timeAgo += diffInHours + "hr ";
                                                }
                                                if (diffInMinutes > 0) {
                                                    timeAgo += diffInMinutes + "min ";
                                                }
                                                timeAgo += "ago";
                                            }

                                            holder.minago.setText(timeAgo);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                    
                                }


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    break;
                }
            }
                else {
                    holder.Accept.setVisibility(View.GONE);
                    holder.Vieworder.setVisibility(View.GONE);
                    holder.Reject.setVisibility(View.GONE);
                    holder.chitchat.setVisibility(View.GONE);

                    FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).removeValue();

//                    FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(random).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                    FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(useriddd).child(random).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(useriddd).removeValue();
//                            }
//                        });
//                    }
//                });


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        holder.paydlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.paydlbtn.setVisibility(View.GONE);
                holder.nopaybtn.setVisibility(View.GONE);
                enablepodt_flag = "1";

            }
        });
        holder.nopaybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.nopaybtn.setVisibility(View.GONE);
                holder.paydlbtn.setVisibility(View.GONE);
                enablepodt_flag = "10";

            }
        });

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.paydlbtn.setVisibility(View.GONE);
                holder.nopaybtn.setVisibility(View.GONE);

                databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
                                final String useriddd = chefPendingOrders.getUserId();


                                dataa = FirebaseDatabase.getInstance().getReference().child("deliveryCharge").child(useriddd);
                                String deliverychargetext = holder.deliverychargetext.getText().toString();
                                String discnttext = holder.discnttext.getText().toString();
                                String chefmessage = holder.chefmessage.getText().toString();

                                Type type = new Type(deliverychargetext, chefmessage, enablepodt_flag, randomuidk, automaticflag, discnttext);
                                dataa.setValue(type);


                                int number = Integer.parseInt(deliverychargetext);
                                int discnumber = Integer.parseInt(discnttext);
                                fnumber = String.valueOf(number);
                                opk = String.valueOf(discnumber);
                                fprice = String.valueOf((Integer.parseInt(chefPendingOrders1.getGrandTotalPrice()) + number) - discnumber);
                                holder.finalgrandtotal.setText("Final Grand Total: ₹ " + fprice);

                                progressDialog.setMessage("Please wait...");
                                progressDialog.show();

                                holder.Accept.setVisibility(View.GONE);
                                holder.Vieworder.setVisibility(View.GONE);
                                holder.Reject.setVisibility(View.GONE);
                                holder.chitchat.setVisibility(View.GONE);

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        if(dataSnapshot.exists()){

                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            final ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
                                            HashMap<String, String> hashMap = new HashMap<>();
                                            String chefid = chefPendingOrders.getChefId();
                                            String dishid = chefPendingOrders.getDishId();
                                            hashMap.put("ChefId", chefPendingOrders.getChefId());
                                            hashMap.put("DishId", chefPendingOrders.getDishId());
                                            hashMap.put("DishName", chefPendingOrders.getDishName());
                                            hashMap.put("DishPrice", chefPendingOrders.getPrice());
                                            hashMap.put("DishQuantity", chefPendingOrders.getDishQuantity());
                                            hashMap.put("RandomUID", randomuidk);
                                            hashMap.put("TotalPrice", chefPendingOrders.getTotalPrice());
                                            hashMap.put("UserId", chefPendingOrders.getUserId());
                                            FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(chefid).child(randomuidk).child("Dishes").child(dishid).setValue(hashMap);
                                        }
//                                    }
                                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("OtherInformation");
                                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                                                if(dataSnapshot.exists()){

                                                ChefPendingOrders1 chefPendingOrders1 = dataSnapshot.getValue(ChefPendingOrders1.class);
                                                HashMap<String, String> hashMap1 = new HashMap<>();
                                                hashMap1.put("Address", chefPendingOrders1.getAddress() + " , " + ordertypeu);
//                                hashMap1.put("GrandTotalPrice", chefPendingOrders1.getGrandTotalPrice());
                                                hashMap1.put("GrandTotalPrice", fprice);
                                                hashMap1.put("MobileNumber", chefPendingOrders1.getMobileNumber());
                                                hashMap1.put("Name", chefPendingOrders1.getName());
                                                hashMap1.put("Note", chefPendingOrders1.getNote());
                                                hashMap1.put("RandomUID", randomuidk);
                                                FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
                                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                if(dataSnapshot.exists()){


                                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                    final ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
                                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                                    userid = chefPendingOrders.getUserId();
                                                                    dishid = chefPendingOrders.getDishId();
                                                                    hashMap2.put("ChefId", chefPendingOrders.getChefId());
                                                                    hashMap2.put("DishId", chefPendingOrders.getDishId());
                                                                    hashMap2.put("DishName", chefPendingOrders.getDishName());
                                                                    hashMap2.put("DishPrice", chefPendingOrders.getPrice());
                                                                    hashMap2.put("DishQuantity", chefPendingOrders.getDishQuantity());
                                                                    hashMap2.put("RandomUID", randomuidk);
                                                                    hashMap2.put("TotalPrice", chefPendingOrders.getTotalPrice());
                                                                    hashMap2.put("UserId", chefPendingOrders.getUserId());
                                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(randomuidk).child("Dishes").child(dishid).setValue(hashMap2);
                                                                }
//                                                            }
                                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("OtherInformation");
                                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                        if(dataSnapshot.exists()){
                                                                        ChefPendingOrders1 chefPendingOrders1 = dataSnapshot.getValue(ChefPendingOrders1.class);
                                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                                        hashMap3.put("Address", chefPendingOrders1.getAddress() + " , " + ordertypeu);
//                                                        hashMap3.put("GrandTotalPrice", chefPendingOrders1.getGrandTotalPrice());
                                                                        hashMap3.put("GrandTotalPrice", fprice);
                                                                        hashMap3.put("MobileNumber", chefPendingOrders1.getMobileNumber());
                                                                        hashMap3.put("Name", chefPendingOrders1.getName());
                                                                        hashMap3.put("Note", chefPendingOrders1.getNote());
                                                                        hashMap3.put("RandomUID", randomuidk);
                                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(userid).child(randomuidk).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(randomuidk).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(randomuidk).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                                        FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onSuccess(Void aVoid) {
                                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                    @Override
                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
//                                                                                                        if(chefmessage.length()!=0){
//                                                                                                            sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order." + "\n" + "\n" + "Message from Seller:" + "\n" + chefmessage, "Payment");
//                                                                                                        }
//                                                                                                        else{
//                                                                                                            sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order.", "Payment");
//                                                                                                        }
//                                                                                                        sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Seller. Delivery Charge of Rs." + fnumber + " has been added to your total price. Your final GrandTotal value is Rs." + fprice + ". Now make Payment for Order." + "\n" + "\n" + "Message from Seller:" + "\n" + chefmessage, "Payment");
//                                                                                                        FCMSend.pushNotification(
//                                                                                                                context,
//                                                                                                                usertoken,
//                                                                                                                "Order Accepted",
//                                                                                                                "Your Order has been Accepted by the Seller. Now make Payment for Order."
//
//                                                                                                        );
                                                                                                                        FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                            @Override
                                                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                String usermobph = dataSnapshot.getValue(String.class);

//                                                                                                                String chefmessage = holder.;

                                                                                                                                String chefmessageiiiiyyyy = holder.chefmessage.getText().toString().trim();

                                                                                                                                if (chefmessageiiiiyyyy.isEmpty()) {
                                                                                                                                    holder.notifMessage.setText("Order Accepted : Your Order has been Accepted. Now make Payment for Order.");
                                                                                                                                } else {
                                                                                                                                    holder.notifMessage.setText("Order Accepted. " + "Note from Seller: " + chefmessageiiiiyyyy);
                                                                                                                                }

//                                                                                                        holder.notifMessage.setText("Order Accepted : Your Order has been Accepted. Now make Payment for Order.");
//                                                                                                        holder.notifNumber.setText("+91" + usermob);
                                                                                                                                holder.notifNumber.setText("+91" + usermobph);
//
                                                                                                                                if (!holder.notifMessage.getText().toString().isEmpty() && (!holder.notifNumber.getText().toString().isEmpty())) {
                                                                                                                                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", holder.notifMessage.getText().toString(), holder.notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                                                                                        @Override
                                                                                                                                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {


                                                                                                                                        }

                                                                                                                                        @Override
                                                                                                                                        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                                                                                        }

                                                                                                                                    });
                                                                                                                                }


//
//
//


                                                                                                                                progressDialog.dismiss();
//                                                                                                        ReusableCodeForAll.ShowAlert(context, "", "Delivery Charge of Rs." + fnumber + " has been added to customer's Total Price. Wait for the Customer to make Payment, Pull down the page to Refresh!");
//                                                                                                        progressDialog.dismiss();
                                                                                                                                //10,f,0,f,5,0,10,36
                                                                                                                                //cheryy-4, straw=2, mango=1, balls=7, wedd=6

                                                                                                                            }


                                                                                                                            @Override
                                                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                            }
                                                                                                                        });

                                                                                                                    }


                                                                                                                    @Override
                                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                    }
                                                                                                                });
                                                                                                            }
                                                                                                        });
                                                                                                    }
                                                                                                });

                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                            }
                                                                        });
//                                                                    }

                                                                    }


                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                    }
                                                                });

                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                });

//                                            }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        ReusableCodeForAll.ShowAlert(context, "", "Delivery Charge of Rs." + fnumber + " and " + "Discount of Rs." + opk + " " + "has been added to customer's Total Price. Wait for the Customer to make Payment, Pull down the page to Refresh!");
//                    }
        }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            }

                });

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chefmessageiiii = holder.chefmessage.getText().toString().trim();

                if(chefmessageiiii.isEmpty()){
                    holder.notifMessage.setText("Order Rejected : Your Order has been Rejected by the seller due to some Circumstances.");
                }
                else {
                    holder.notifMessage.setText("Order Rejected. " + "Note from Seller: " + chefmessageiiii);
                }

//                holder.notifMessage.setText("Order Rejected : Your Order has been Rejected by seller due to some Circumstances.");
                holder.notifNumber.setText("+91" + usermobyyy);
//
                if (!holder.notifMessage.getText().toString().isEmpty()&&(!holder.notifNumber.getText().toString().isEmpty())) {
                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", holder.notifMessage.getText().toString(), holder.notifNumber.getText().toString()), new okhttp3.Callback() {
                        @Override
                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

                        }

                        @Override
                        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                        }

                    });
                }

                Toast.makeText(context, "Order Rejected..Pull down to refresh the page", Toast.LENGTH_SHORT).show();

                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                holder.Accept.setVisibility(View.GONE);
                holder.Vieworder.setVisibility(View.GONE);
                holder.Reject.setVisibility(View.GONE);
                holder.chitchat.setVisibility(View.GONE);

//                openDialog();


                DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
                Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()){
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final ChefPendingOrders chefPendingOrders = snapshot.getValue(ChefPendingOrders.class);
                            userid = chefPendingOrders.getUserId();
                            dishid = chefPendingOrders.getDishId();
                        }
//                    }
                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
//                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the seller of the product due to some Circumstances.", "Home");
//                                FCMSend.pushNotification(
//                                        ChefPendingOrdersAdapter.this,
//                                        usertoken,
//                                        "Order Rejected",
//                                        "Your Order has been Rejected by the seller of the product due to some Circumstances."
//
//                                );
//                                FCMSend.pushNotification(
//                                        context,
//                                        usertoken,
//                                        "Order Rejected",
//                                        "Your Order has been Rejected by the seller of the product due to some Circumstances."
//
//                                );
                                FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(randomuidk).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        FirebaseDatabase.getInstance().getReference("CustomerPendingOrders").child(userid).child(randomuidk).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        FirebaseDatabase.getInstance().getReference("ChefPendingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");
                                                                progressDialog.dismiss();
                                                            }
                                                        });

                                                    }
                                                });

                                            }
                                        });

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }

        });
    }

//    private void displayTimeDifference(String fetchedTime) {
//        // Parse fetched time string to Date object
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
//        try {
//            Date dateFetched = sdf.parse(fetchedTime);
//            Date currentDate = Calendar.getInstance().getTime();
//
//            long diffInMillis = currentDate.getTime() - dateFetched.getTime();
//            long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);
//            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis);
//            long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
//            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
//            long diffInMonths = diffInDays / 30; // Approximate, not exact
//            long diffInYears = diffInDays / 365; // Approximate, not exact
//
//            String timeAgo;
//            if (diffInSeconds < 60) {
//                timeAgo = diffInSeconds + " sec ago";
//            } else if (diffInMinutes < 60) {
//                timeAgo = diffInMinutes + " min ago";
//            } else if (diffInHours < 24) {
//                timeAgo = diffInHours + " hrs ago";
//            } else if (diffInDays < 30) {
//                timeAgo = diffInDays + " days ago";
//            } else if (diffInMonths < 12) {
//                timeAgo = diffInMonths + " months ago";
//            } else {
//                timeAgo = diffInYears + " years ago";
//            }
//
//            minago.setText(timeAgo);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }

    private void sendNotifications(String usertoken, String title, String message, String order) {

        Data data = new Data(title, message, order);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return chefPendingOrders1list.size();
    }

//    public void expand(ViewHolder viewHolder){
//        int v = (viewHolder.direc.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
//        TransitionManager.beginDelayedTransition(viewHolder.laytot, new AutoTransition());
//
//        viewHolder.direc.setVisibility(v);
//
//
//    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, nt2, NOTE2, nm2, num2, finalgrandtotal, ordertype ,announcementsller,razopayidselr, ordertame, minago;
        EditText deliverychargetext, discnttext;
        EditText chefmessage;
        Button Vieworder, Accept, Reject, paydlbtn, nopaybtn, khudaccept;
        EditText notifMessage, notifNumber;
        Button send_notif, direc;
        LinearLayout lay1,laytot, lay2,lay3,clforlay1, clforlay2,clforlay3;
        ImageView chitchat;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.AD);
            grandtotalprice = itemView.findViewById(R.id.TP);
            nt2 = itemView.findViewById(R.id.nt2);
            NOTE2 = itemView.findViewById(R.id.NOTE2);
            nm2 = itemView.findViewById(R.id.nm2);
            num2 = itemView.findViewById(R.id.num2);
            finalgrandtotal = itemView.findViewById(R.id.finalgrandtotal);
            ordertype = itemView.findViewById(R.id.ordertype);
            ordertame = itemView.findViewById(R.id.ordertame);
            minago = itemView.findViewById(R.id.minago);
            deliverychargetext = itemView.findViewById(R.id.deliverychargetext);
            discnttext = itemView.findViewById(R.id.discnttext);
            announcementsller = itemView.findViewById(R.id.announcementsller);
            razopayidselr = itemView.findViewById(R.id.razopayidselr);
            chefmessage = itemView.findViewById(R.id.chefmessage);
            Vieworder = itemView.findViewById(R.id.vieww);
            Accept = itemView.findViewById(R.id.accept);
            paydlbtn = itemView.findViewById(R.id.paydlbtn);
            khudaccept = itemView.findViewById(R.id.khudaccept);
            nopaybtn = itemView.findViewById(R.id.nopaybtn);
            Reject = itemView.findViewById(R.id.reject);
            chitchat= itemView.findViewById(R.id.chitchat);
            direc= itemView.findViewById(R.id.direc);
//            loctext= itemView.findViewById(R.id.loctext);
            clforlay1= itemView.findViewById(R.id.clforlay1);
            clforlay2= itemView.findViewById(R.id.clforlay2);
            clforlay3= itemView.findViewById(R.id.clforlay3);
            lay1= itemView.findViewById(R.id.lay1);
            lay2= itemView.findViewById(R.id.lay2);
            lay3= itemView.findViewById(R.id.lay3);
            laytot= itemView.findViewById(R.id.laytot);
            laytot.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

//            randomuidk = UUID.randomUUID().toString();

            notifMessage= itemView.findViewById(R.id.notifMessage);
            notifNumber= itemView.findViewById(R.id.notifNumber);
            send_notif= itemView.findViewById(R.id.send_notif);


        }
    }
}