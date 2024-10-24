package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.CustomerFoodPanel.OnlineCancelOrder;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefOrdertobePrepareView extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<ChefWaitingOrders> chefWaitingOrdersList;
    private ChefOrdertobePrepareViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID, userid, paymentstatus, ordnim;
    TextView grandtotal, note, address, name, number;
    LinearLayout l1;
    public static String usermop, chefst, countord, usermobph;
    Button Preparing, payst;
    Button retord;
    public static int addonflag=0;
    TextView ordertype;

    EditText notifMessage, notifNumber;
    Button send_notif;


    private ProgressDialog progressDialog;
    private APIService apiService;
    public static String useriddddd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_ordertobe_prepare_view);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
        grandtotal = findViewById(R.id.rupees);
        note = findViewById(R.id.NOTE);
        address = findViewById(R.id.ad);
        name = findViewById(R.id.nm);
        number = findViewById(R.id.num);
        l1 = findViewById(R.id.button1);
        Preparing = findViewById(R.id.preparing);
        payst = findViewById(R.id.payst);
        retord = findViewById(R.id.retord);
        ordertype = findViewById(R.id.ordertype);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(ChefOrdertobePrepareView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerViewdish.setHasFixedSize(true);

        notifMessage= findViewById(R.id.notifMessage);
        notifNumber= findViewById(R.id.notifNumber);
        send_notif= findViewById(R.id.send_notif);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");



        recyclerViewdish.setLayoutManager(new LinearLayoutManager(ChefOrdertobePrepareView.this));
        chefWaitingOrdersList = new ArrayList<>();
        CheforderdishesView();
    }

    private void CheforderdishesView() {
        RandomUID = getIntent().getStringExtra("RandomUID");
        paymentstatus = getIntent().getStringExtra("paymentstatus"); //Pay on Delivery
        ordnim = getIntent().getStringExtra("ordnim");

        if(paymentstatus.contains("Pay on Delivery")){
            payst.setVisibility(View.VISIBLE);
        }

        payst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("ChefStatus").child(RandomUID).child("chefsts").setValue("Payment Done");
                Toast.makeText(ChefOrdertobePrepareView.this, "Payment Done marked for order no. " + ordnim , Toast.LENGTH_SHORT).show();


//                DatabaseReference databaseReference1z = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
//                databaseReference1z.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            final ChefWaitingOrders chefWaitingOrders = dataSnapshot1.getValue(ChefWaitingOrders.class);
////                                                            HashMap<String, String> hashMap = new HashMap<>();
////                                                            String dishid = chefWaitingOrders.getDishId();
//                            userid = chefWaitingOrders.getUserId();
//                            break;
//                        }




//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


                Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
                startActivity(b);
                finish();

            }
        });

        reference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chefWaitingOrdersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChefWaitingOrders chefWaitingOrders = snapshot.getValue(ChefWaitingOrders.class);
                    chefWaitingOrdersList.add(chefWaitingOrders);
                }
                if (chefWaitingOrdersList.size() == 0) {
                    l1.setVisibility(View.INVISIBLE);

                } else {
                    l1.setVisibility(View.VISIBLE);





                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("AddOn").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot addOnSnapshot) {
                            if (addOnSnapshot.exists()) {
                                addonflag=1;
                            } else {
                                addonflag=0;
                            }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Handle error
//            }
//        });




//                     Preparing.setOnClickListener(new View.OnClickListener() {
//                         @Override
//                         public void onClick(View v) {

//                             progressDialog.setMessage("Please wait...");
//                             progressDialog.show();


//                             DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
//                             databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                 @Override
//                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                     for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                                         final ChefWaitingOrders chefWaitingOrders = dataSnapshot1.getValue(ChefWaitingOrders.class);
//                                         HashMap<String, String> hashMap = new HashMap<>();
//                                         String dishid = chefWaitingOrders.getDishId();
//                                         userid = chefWaitingOrders.getUserId();
//                                         hashMap.put("ChefId", chefWaitingOrders.getChefId());
//                                         hashMap.put("DishId", chefWaitingOrders.getDishId());
//                                         hashMap.put("DishName", chefWaitingOrders.getDishName());
//                                         hashMap.put("DishPrice", chefWaitingOrders.getDishPrice());
//                                         hashMap.put("DishQuantity", chefWaitingOrders.getDishQuantity());
//                                         hashMap.put("RandomUID", RandomUID);
//                                         hashMap.put("TotalPrice", chefWaitingOrders.getTotalPrice());
//                                         hashMap.put("UserId", chefWaitingOrders.getUserId());
// //                                        hashMap.put("rpayid", chefWaitingOrders.getrpayid());
//                                         useriddddd = chefWaitingOrders.getUserId();






//                                         FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes").child(dishid).setValue(hashMap);
//                                     }

// //                                    DatabaseReference datuas = FirebaseDatabase.getInstance().getReference("ordertype").child(useriddddd);
// //                                    datuas.addListenerForSingleValueEvent(new ValueEventListener() {
// //                                        @Override
// //                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
// //
// //                                            Type7 type7 = dataSnapshot.getValue(Type7.class);
// //                                            final String ordtype = type7.getordermethod();
// //                                            ordertype.setText("Order Type: " + ordtype);




//                                     DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
//                                     data.addListenerForSingleValueEvent(new ValueEventListener() {
//                                         @Override
//                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                             final ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
//                                             HashMap<String, String> hashMap1 = new HashMap<>();
//                                             hashMap1.put("Address", chefWaitingOrders1.getAddress());
//                                             hashMap1.put("GrandTotalPrice", chefWaitingOrders1.getGrandTotalPrice());
//                                             hashMap1.put("MobileNumber", chefWaitingOrders1.getMobileNumber());
//                                             hashMap1.put("Name", chefWaitingOrders1.getName());
//                                             hashMap1.put("Note", chefWaitingOrders1.getNote());
//                                             hashMap1.put("RandomUID", RandomUID);
//                                             hashMap1.put("Status", "Your Order is being prepared...");
//                                             FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                Preparing.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        String chefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference chefWaitingOrdersRef = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefId).child(RandomUID);

        // Check if it's an AddOn order
        chefWaitingOrdersRef.child("AddOn").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot addOnSnapshot) {
                if (addOnSnapshot.exists()) {
//                    addonflag=1;
                    // AddOn logic: Merge dishes and update GrandTotalPrice
                    handleAddOnOrder(chefWaitingOrdersRef, RandomUID);
                } else {
//                    addonflag=0;
                    // Original logic if AddOn does not exist
                    DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefId).child(RandomUID).child("Dishes");
                    databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                final ChefWaitingOrders chefWaitingOrders = dataSnapshot1.getValue(ChefWaitingOrders.class);
                                HashMap<String, String> hashMap = new HashMap<>();
                                String dishid = chefWaitingOrders.getDishId();
                                userid = chefWaitingOrders.getUserId();
                                hashMap.put("ChefId", chefWaitingOrders.getChefId());
                                hashMap.put("DishId", chefWaitingOrders.getDishId());
                                hashMap.put("DishName", chefWaitingOrders.getDishName());
                                hashMap.put("DishPrice", chefWaitingOrders.getDishPrice());
                                hashMap.put("DishQuantity", chefWaitingOrders.getDishQuantity());
                                hashMap.put("RandomUID", RandomUID);
                                hashMap.put("TotalPrice", chefWaitingOrders.getTotalPrice());
                                hashMap.put("UserId", chefWaitingOrders.getUserId());
                                useriddddd = chefWaitingOrders.getUserId();

                                FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefId).child(RandomUID).child("Dishes").child(dishid).setValue(hashMap);
                            }

                            DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefId).child(RandomUID).child("OtherInformation");
                            data.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
                                    HashMap<String, String> hashMap1 = new HashMap<>();
                                    hashMap1.put("Address", chefWaitingOrders1.getAddress());
                                    hashMap1.put("GrandTotalPrice", chefWaitingOrders1.getGrandTotalPrice());
                                    hashMap1.put("MobileNumber", chefWaitingOrders1.getMobileNumber());
                                    hashMap1.put("Name", chefWaitingOrders1.getName());
                                    hashMap1.put("Note", chefWaitingOrders1.getNote());
                                    hashMap1.put("RandomUID", RandomUID);
                                    hashMap1.put("Status", "Your Order is being prepared...");
                                    FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefId).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Seller is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {




//                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Note").setValue("Chef is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<Void> task) {




                                                                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {



//                                                                            FirebaseDatabase.getInstance().getReference("deliveryCharge").child("deliverychargetext").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                @Override
//                                                                                public void onComplete(@NonNull Task<Void> task) {




                                                                            FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {



                                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                            String usertoken = dataSnapshot.getValue(String.class);
//                                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is being prepared", "Preparing");
                                                                                            FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                @Override
                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                    usermobph = dataSnapshot.getValue(String.class);

                                                                                            notifMessage.setText("Estimated Time : Your Order is being prepared");
                                                                                            notifNumber.setText("+91" + usermobph);

                                                                                            if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
                                                                                                new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                                                    @Override
                                                                                                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {



                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                                                    }

                                                                                                });
                                                                                            }

                                                                                                }

                                                                                                @Override
                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                }
                                                                                            });


                                                                                            progressDialog.dismiss();
                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
                                                                                            builder.setMessage("See Orders which are Prepared");
                                                                                            builder.setCancelable(false);
                                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(DialogInterface dialog, int which) {

                                                                                                    dialog.dismiss();
                                                                                                    Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
//                                                                                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                    startActivity(b);
                                                                                                    finish();


                                                                                                }
                                                                                            });
                                                                                            AlertDialog alert = builder.create();
                                                                                            alert.show();
                                                                                        }

                                                                                        @Override
                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                        }
                                                                                    });

//                                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                        @Override
//                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                            String usertoken = dataSnapshot.getValue(String.class);
//                                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is being prepared", "Preparing");
//                                                                                            FCMSend.pushNotification(
//                                                                                                    ChefOrdertobePrepareView.this,
//                                                                                                    usertoken,
//                                                                                                    "Estimated Time",
//                                                                                                    "Your Order is being prepared"
//
//                                                                                            );
//                                                                                            FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                @Override
//                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                    String usermobph = dataSnapshot.getValue(String.class);
//
//                                                                                            notifMessage.setText("Estimated Time : Your Order is being prepared");
////                                                                                            notifNumber.setText("+91" + usermop);
//                                                                                            notifNumber.setText("+91" + usermobph);
//
//                                                                                            if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
//                                                                                                new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
//                                                                                                    @Override
//                                                                                                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//
//
//                                                                                                    }
//
//                                                                                                    @Override
//                                                                                                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                                                                                                    }
//
//                                                                                                });
//                                                                                            }
//
//                                                                                                }
//
//                                                                                                @Override
//                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                }
//                                                                                            });
//                                                                                            progressDialog.dismiss();
//                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
//                                                                                            builder.setMessage("See Orders which are Prepared");
//                                                                                            builder.setCancelable(false);
//                                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                                                                                @Override
//                                                                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                                                                    dialog.dismiss();
//                                                                                                    Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
//                                                                                                    startActivity(b);
//                                                                                                    finish();
//
//
//                                                                                                }
//                                                                                            });
//                                                                                            AlertDialog alert = builder.create();
//                                                                                            alert.show();

//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                        }
//                                                                                    });

                                                                                }
                                                                            });
//                                                                                }
//                                                                            });
                                                                        }
                                                                    });
//                                                                }
//                                                            });


                                                        }
                                                    });


                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

//                                }
//
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

    }
                                                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });

                        }
                    });


                    DatabaseReference ldbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(RandomUID);
                    ldbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                Type67 typechapm = dataSnapshot.getValue(Type67.class);
//                    holder.chefst.setText(typechapm.getchefsts());
//                    holder.countord.setText(typechapm.getordn());
                                if(dataSnapshot.hasChild("chefsts")) {
                                    chefst = (typechapm.getchefsts());


                                    retord.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            //
                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
                                            builder.setTitle("Cancel Order");
                                            builder.setMessage("Are you sure you want to cancel this order?");

                                            // If the user confirms the deletion
                                            builder.setPositiveButton("Yes", (dialog, which) -> {
                                                // Proceed with deleting the group

//                                            });
//
//                                            // If the user cancels the deletion
//                                            builder.setNegativeButton("No", (dialog, which) -> {
//                                                dialog.dismiss();  // Close the dialog
//                                            });
//
//                                            builder.show();
                                            //


                                            if (addonflag == 0){


                                                if (chefst.contains("Pay on Delivery")) {

                                                    progressDialog.setMessage("Please wait...");
                                                    progressDialog.show();

                                                    DatabaseReference databaseReference1z = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                                    databaseReference1z.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                final ChefWaitingOrders chefWaitingOrders = dataSnapshot1.getValue(ChefWaitingOrders.class);
//                                                            HashMap<String, String> hashMap = new HashMap<>();
//                                                            String dishid = chefWaitingOrders.getDishId();
                                                                userid = chefWaitingOrders.getUserId();
                                                                break;
                                                            }


                                                            FirebaseDatabase.getInstance().getReference("ordertype").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    FirebaseDatabase.getInstance().getReference("deliveryCharge").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        //                                                                            @Override
//                                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                                FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rund).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                @Override
                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                            FirebaseDatabase.getInstance().getReference("ChefStatus").child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    FirebaseDatabase.getInstance().getReference("ChefTrack").child(RandomUID).removeValue();
                                                                                                                }
                                                                                                            });
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            });
                                                                                        }

//                                                                                });
//                                                                            }
                                                                                    });
                                                                                }
                                                                            });
                                                                        }
                                                                    });
                                                                }
                                                            });

                                                            FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    usermobph = dataSnapshot.getValue(String.class);

                                                                    notifMessage.setText("Order Cancelled: Your Order has been cancelled by the Seller due to some circumstances");
//                                                                                            notifNumber.setText("+91" + usermop);
                                                                    notifNumber.setText("+91" + usermobph);

                                                                    if (!notifMessage.getText().toString().isEmpty() && (!notifNumber.getText().toString().isEmpty())) {
                                                                        new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                            @Override
                                                                            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {


                                                                            }

                                                                            @Override
                                                                            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                            }

                                                                        });
                                                                    }

                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });

//                                                AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
//                                                builder.setMessage("Order Cancelled Successfully! Message of order cancelled reached to the customer");

                                                            progressDialog.dismiss();
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
                                                            builder.setMessage("Order Cancelled Successfully! Message of order cancelled reached to the customer");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {

                                                                    dialog.dismiss();
                                                                    Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
                                                                    startActivity(b);
                                                                    finish();


                                                                }
                                                            });
                                                            AlertDialog alert = builder.create();
                                                            alert.show();


                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });


                                                } else {
                                                    Toast.makeText(ChefOrdertobePrepareView.this, "Payment has already been done", Toast.LENGTH_SHORT).show();
                                                }


                                            }
                                            else {
                                                Toast.makeText(ChefOrdertobePrepareView.this, "Add-On Orders cannot be cancelled", Toast.LENGTH_SHORT).show();
                                            }


                                        });

                                        // If the user cancels the deletion
                                            builder.setNegativeButton("No", (dialog, which) -> {
                                            dialog.dismiss();  // Close the dialog
                                        });

                                            builder.show();




                                        }
                                    });




                                }
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });



                }


                adapter = new ChefOrdertobePrepareViewAdapter(ChefOrdertobePrepareView.this, chefWaitingOrdersList);
                recyclerViewdish.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
                grandtotal.setText("₹ " + chefWaitingOrders1.getGrandTotalPrice());
                note.setText(chefWaitingOrders1.getNote());
//                address.setText((chefWaitingOrders1.getAddress()).substring(22));
//                address.setText((chefWaitingOrders1.getAddress()).substring((chefWaitingOrders1.getAddress()).indexOf(':') + 1).trim());

                String fullAddress = chefWaitingOrders1.getAddress();
                int colonIndex = fullAddress.indexOf(':');
                int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
                int lastCommaIndex = fullAddress.lastIndexOf(',');
                String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
                String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

                address.setText(firstPart);
//        holder.ordtp.setText("Order Type: " + lastPart);

                name.setText(chefWaitingOrders1.getName());
                number.setText("+91" + chefWaitingOrders1.getMobileNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }












    private void handleAddOnOrder(DatabaseReference chefWaitingOrdersRef, String RandomUID) {
    DatabaseReference chefFinalOrdersRef = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
    
    chefWaitingOrdersRef.child("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dishesSnapshot) {
            for (DataSnapshot dishSnapshot : dishesSnapshot.getChildren()) {
                ChefWaitingOrders chefWaitingOrders = dishSnapshot.getValue(ChefWaitingOrders.class);
                String dishId = chefWaitingOrders.getDishId();
                userid = chefWaitingOrders.getUserId();

                chefFinalOrdersRef.child(dishId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot finalOrderDishSnapshot) {
                        if (finalOrderDishSnapshot.exists()) {
                            // Dish already exists in ChefFinalOrders
                            int existingQuantity = Integer.parseInt(finalOrderDishSnapshot.child("DishQuantity").getValue(String.class));
                            int existingTotalPrice = Integer.parseInt(finalOrderDishSnapshot.child("TotalPrice").getValue(String.class));

                            int newQuantity = Integer.parseInt(chefWaitingOrders.getDishQuantity());
                            int newTotalPrice = Integer.parseInt(chefWaitingOrders.getTotalPrice());

                            int updatedQuantity = existingQuantity + newQuantity;
                            int updatedTotalPrice = existingTotalPrice + newTotalPrice;

                            chefFinalOrdersRef.child(dishId).child("DishQuantity").setValue(String.valueOf(updatedQuantity));
                            chefFinalOrdersRef.child(dishId).child("TotalPrice").setValue(String.valueOf(updatedTotalPrice));
                        }
                        else {
                            // New dish, simply add it
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("ChefId", chefWaitingOrders.getChefId());
                            hashMap.put("DishId", chefWaitingOrders.getDishId());
                            hashMap.put("DishName", chefWaitingOrders.getDishName());
                            hashMap.put("DishPrice", chefWaitingOrders.getDishPrice());
                            hashMap.put("DishQuantity", chefWaitingOrders.getDishQuantity());
                            hashMap.put("RandomUID", RandomUID);
                            hashMap.put("TotalPrice", chefWaitingOrders.getTotalPrice());
                            hashMap.put("UserId", chefWaitingOrders.getUserId());

                            chefFinalOrdersRef.child(dishId).setValue(hashMap);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }

            // After processing all dishes, update the OtherInformation
            chefWaitingOrdersRef.child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot otherInfoSnapshot) {
                    ChefWaitingOrders1 chefWaitingOrders1 = otherInfoSnapshot.getValue(ChefWaitingOrders1.class);

                    FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot finalOrderInfoSnapshot) {
                            int existingGrandTotal = 0;
                            if (finalOrderInfoSnapshot.hasChild("GrandTotalPrice")) {
                                existingGrandTotal = Integer.parseInt(finalOrderInfoSnapshot.child("GrandTotalPrice").getValue(String.class));
                            }
                            int newGrandTotal = Integer.parseInt(chefWaitingOrders1.getGrandTotalPrice());
                            int updatedGrandTotal = existingGrandTotal + newGrandTotal;

                            HashMap<String, String> hashMap1 = new HashMap<>();
                            hashMap1.put("Address", chefWaitingOrders1.getAddress());
                            hashMap1.put("GrandTotalPrice", String.valueOf(updatedGrandTotal));
                            hashMap1.put("MobileNumber", chefWaitingOrders1.getMobileNumber());
                            hashMap1.put("Name", chefWaitingOrders1.getName());
                            hashMap1.put("Note", chefWaitingOrders1.getNote());
                            hashMap1.put("RandomUID", RandomUID);
                            hashMap1.put("Status", "Your add-on Order is being prepared...");

//                            chefFinalOrdersRef.getParent().child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressDialog.dismiss();
//                                    Toast.makeText(getApplicationContext(), "Add-On Order is being prepared...", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                            //start
                            FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Seller is preparing your add-on order...").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {




//                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Note").setValue("Chef is preparing your order...").addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                @Override
//                                                                public void onComplete(@NonNull Task<Void> task) {




                                            FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {



//                                                                            FirebaseDatabase.getInstance().getReference("deliveryCharge").child("deliverychargetext").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                                @Override
//                                                                                public void onComplete(@NonNull Task<Void> task) {




//                                                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                        @Override
//                                                        public void onSuccess(Void aVoid) {



                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is being prepared", "Preparing");
                                                                    FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            usermobph = dataSnapshot.getValue(String.class);

                                                                            notifMessage.setText("Your Add-On Order is being prepared");
                                                                            notifNumber.setText("+91" + usermobph);

                                                                            if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
                                                                                new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                                    @Override
                                                                                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {



                                                                                    }

                                                                                    @Override
                                                                                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                                    }

                                                                                });
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });


                                                                    progressDialog.dismiss();
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
                                                                    builder.setMessage("See Orders which are Prepared");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            dialog.dismiss();
                                                                            Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
//                                                                                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                            startActivity(b);
                                                                            finish();


                                                                        }
                                                                    });
                                                                    AlertDialog alert = builder.create();
                                                                    alert.show();
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });

//                                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                        @Override
//                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                            String usertoken = dataSnapshot.getValue(String.class);
//                                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is being prepared", "Preparing");
//                                                                                            FCMSend.pushNotification(
//                                                                                                    ChefOrdertobePrepareView.this,
//                                                                                                    usertoken,
//                                                                                                    "Estimated Time",
//                                                                                                    "Your Order is being prepared"
//
//                                                                                            );
//                                                                                            FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                @Override
//                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                    String usermobph = dataSnapshot.getValue(String.class);
//
//                                                                                            notifMessage.setText("Estimated Time : Your Order is being prepared");
////                                                                                            notifNumber.setText("+91" + usermop);
//                                                                                            notifNumber.setText("+91" + usermobph);
//
//                                                                                            if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
//                                                                                                new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
//                                                                                                    @Override
//                                                                                                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//
//
//                                                                                                    }
//
//                                                                                                    @Override
//                                                                                                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                                                                                                    }
//
//                                                                                                });
//                                                                                            }
//
//                                                                                                }
//
//                                                                                                @Override
//                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                }
//                                                                                            });
//                                                                                            progressDialog.dismiss();
//                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefOrdertobePrepareView.this);
//                                                                                            builder.setMessage("See Orders which are Prepared");
//                                                                                            builder.setCancelable(false);
//                                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                                                                                @Override
//                                                                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                                                                    dialog.dismiss();
//                                                                                                    Intent b = new Intent(ChefOrdertobePrepareView.this, ChefOrderTobePrepared.class);
//                                                                                                    startActivity(b);
//                                                                                                    finish();
//
//
//                                                                                                }
//                                                                                            });
//                                                                                            AlertDialog alert = builder.create();
//                                                                                            alert.show();

//                                                                                        }
//
//                                                                                        @Override
//                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                        }
//                                                                                    });

//                                                        }
//                                                    });
//                                                                                }
//                                                                            });
                                                }
                                            });
//                                                                }
//                                                            });


                                        }
                                    });


                                }
                            });
                            //end
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle error
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Handle error
        }
    });
}


    private void sendNotifications(String usertoken, String title, String message, String preparing) {
        Data data = new Data(title, message, preparing);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(ChefOrdertobePrepareView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
