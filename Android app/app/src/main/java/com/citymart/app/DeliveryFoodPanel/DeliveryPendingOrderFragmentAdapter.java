package com.citymart.app.DeliveryFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.app.ProgressDialog;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.ChefFoodPanel.Type15;
import com.citymart.app.ChefFoodPanel.Type67;
import com.citymart.app.ChefFoodPanel.TypeC;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryPendingOrderFragmentAdapter extends RecyclerView.Adapter<DeliveryPendingOrderFragmentAdapter.ViewHolder> {

    private Context context;
    private List<DeliveryShipOrders1> deliveryShipOrders1list;
    private APIService apiService;
    private ProgressDialog progressDialog;
    String chefid;
    public static String etaddr, sty;
    public static String delid= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference grp, gty;


    public DeliveryPendingOrderFragmentAdapter(Context context, List<DeliveryShipOrders1> deliveryShipOrders1list) {
        this.deliveryShipOrders1list = deliveryShipOrders1list;
        this.context = context;
    }

    @NonNull
    @Override
    public DeliveryPendingOrderFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_pendingorders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new DeliveryPendingOrderFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryPendingOrderFragmentAdapter.ViewHolder holder, int position) {

        final DeliveryShipOrders1 deliveryShipOrders1 = deliveryShipOrders1list.get(position);
        holder.Address.setText((deliveryShipOrders1.getAddress()).substring((deliveryShipOrders1.getAddress()).indexOf(':') + 1).trim());
        etaddr = deliveryShipOrders1.getAddress();
        sty = deliveryShipOrders1.getRandomUID();
        holder.mobilenumber.setText("+91" + deliveryShipOrders1.getMobileNumber());
        holder.grandtotalprice.setText("Grand Total: ₹ " + deliveryShipOrders1.getGrandTotalPrice());
        DatabaseReference ldbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(sty);
        ldbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {

                    Type67 typechapm = dataSnapshot.getValue(Type67.class);
                    if(dataSnapshot.hasChild("chefsts")) {
                        holder.chefst.setText(typechapm.getchefsts());
                    }
                    if(dataSnapshot.hasChild("ordn")) {
                        holder.countord.setText(typechapm.getordn());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        final String randomuid = deliveryShipOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DeliveryPendingOrderView.class);
                intent.putExtra("Random", randomuid);
                context.startActivity(intent);
            }
        });

        gty = FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(randomuid);
        gty.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Type15 typechal = dataSnapshot.getValue(Type15.class);
                holder.lplp.setText(typechal.getacceptval());

                if (Objects.equals(holder.lplp.getText(), "1")) {

                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue();
                                    holder.Vieworder.setVisibility(View.GONE);
                                    holder.Accept.setVisibility(View.GONE);
                                    holder.Reject.setVisibility(View.GONE);
                                }
                            });

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        progressDialog = new ProgressDialog(context);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        holder.direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.Accept.setVisibility(View.GONE);
                holder.Vieworder.setVisibility(View.GONE);
                holder.Reject.setVisibility(View.GONE);

                Toast.makeText(context, "Wait for The Message To Appear!", Toast.LENGTH_SHORT).show();

                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                final String[] flap = new String[1];

                grp = FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(randomuid);
                grp.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         Type15 typecha = dataSnapshot.getValue(Type15.class);
                         flap[0] = typecha.getacceptval();


                        if (Objects.equals(flap[0], "0")) {

//                            FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(randomuid).setValue("1");
                            DatabaseReference frf = FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(randomuid);
                            Type15 type15 = new Type15("1");
                            frf.setValue(type15);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    DeliveryShipOrders deliveryShipOrderss = snapshot.getValue(DeliveryShipOrders.class);
                                    HashMap<String, String> hashMap = new HashMap<>();
                                    String dishid = deliveryShipOrderss.getDishId();
                                    chefid = deliveryShipOrderss.getChefId();
                                    hashMap.put("ChefId", deliveryShipOrderss.getChefId());
                                    hashMap.put("DishId", deliveryShipOrderss.getDishId());
                                    hashMap.put("DishName", deliveryShipOrderss.getDishName());
                                    hashMap.put("DishPrice", deliveryShipOrderss.getDishPrice());
                                    hashMap.put("DishQuantity", deliveryShipOrderss.getDishQuantity());
                                    hashMap.put("RandomUID", deliveryShipOrderss.getRandomUID());
                                    hashMap.put("TotalPrice", deliveryShipOrderss.getTotalPrice());
                                    hashMap.put("UserId", deliveryShipOrderss.getUserId());
                                    FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").child(dishid).setValue(hashMap);

                                }

                                DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
                                data.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        DeliveryShipOrders1 deliveryShipOrders11 = dataSnapshot.getValue(DeliveryShipOrders1.class);
                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Address", deliveryShipOrders11.getAddress());
                                        hashMap1.put("ChefId", deliveryShipOrders11.getChefId());
                                        hashMap1.put("ChefName", deliveryShipOrders11.getChefName());
                                        hashMap1.put("GrandTotalPrice", deliveryShipOrders11.getGrandTotalPrice());
                                        hashMap1.put("MobileNumber", deliveryShipOrders11.getMobileNumber());
                                        hashMap1.put("Name", deliveryShipOrders11.getName());
                                        hashMap1.put("RandomUID", randomuid);
                                        hashMap1.put("UserId", deliveryShipOrders11.getUserId());
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        String usertoken = dataSnapshot.getValue(String.class);
//                                                                sendNotifications(usertoken, "Order Accepted", "Your Order has been Accepted by the Delivery person", "AcceptOrder");
//                                                                FCMSend.pushNotification(
//                                                                        DeliveryPendingOrderFragmentAdapter.this,
//                                                                        usertoken,
//                                                                        "Order Accepted",
//                                                                        "Your Order has been Accepted by the Delivery person"
//
//                                                                );
                                                                        FirebaseDatabase.getInstance().getReference().child("Chef").child(chefid).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                String chefmobph = dataSnapshot.getValue(String.class);

                                                                                holder.notifMessage.setText("Order Accepted : Your Order has been Accepted by the Delivery person");
                                                                                holder.notifNumber.setText("+91" + chefmobph);
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


                                                                                progressDialog.dismiss();
                                                                                ReusableCodeForAll.ShowAlert(context, "", "Now you can check orders which are to be shipped. Pull down the Page to Refresh!");
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
                                                        }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefid).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefid).child(randomuid).child("OtherInformation").removeValue();
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

//                            DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders");
//                            databaseReferencel.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                                        String otherFirebaseAuth.getInstance().getCurrentUser().getUid() = snapshot.getKey();
//
//                                        FirebaseDatabase.getInstance().getReference().child("DeliveryPerson").child(otherFirebaseAuth.getInstance().getCurrentUser().getUid()).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                String delmobph = dataSnapshot.getValue(String.class);
//
//                                                holder.notifMessage.setText("The Order has already been Accepted by the other Delivery person");
//                                                holder.notifNumber.setText("+91" + delmobph);
////
//                                                if (!holder.notifMessage.getText().toString().isEmpty() && (!holder.notifNumber.getText().toString().isEmpty())) {
//                                                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", holder.notifMessage.getText().toString(), holder.notifNumber.getText().toString()), new okhttp3.Callback() {
//                                                        @Override
//                                                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//                                                        }
//
//                                                        @Override
//                                                        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                                                        }
//
//                                                    });
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//                                        DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(otherFirebaseAuth.getInstance().getCurrentUser().getUid());
//                                        databaseReferencel.addListenerForSingleValueEvent(new ValueEventListener() {
//                                            @Override
//                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                for (DataSnapshot snapshoty : dataSnapshot.getChildren()) {
//
//                                                    String randkey = snapshoty.getKey();
//
//                                                    if (randkey==randomuid){
//                                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(otherFirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                            @Override
//                                                            public void onComplete(@NonNull Task<Void> task) {
//                                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(otherFirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue();
//                                                                progressDialog.dismiss();
//                                                            }
//                                                        });
//                                                        break;
//                                                    }
//
//
//                                                }
//                                            }
//
//                                            @Override
//                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            }
//                                        });
//
//
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
                        //....
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
                        //....
                    }


//                        else if (Objects.equals(flap[0], "1")){
//                            Toast.makeText(context, "Order has already been Accepted! Please check for other orders.", Toast.LENGTH_SHORT).show();
//                            FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue();
//                                    progressDialog.dismiss();
//                                }
//                            });
//                        }

//                        else{
//                            holder.Vieworder.setVisibility(View.GONE);
//                            holder.Accept.setVisibility(View.GONE);
//                            holder.Reject.setVisibility(View.GONE);
//                            holder.remove2.setVisibility(View.VISIBLE);
//
//
//                        }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }
        });






        holder.remove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(context, "Order Removed..Pull down to refresh the page", Toast.LENGTH_SHORT).show();

                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DeliveryShipOrders deliveryShipOrders = dataSnapshot1.getValue(DeliveryShipOrders.class);
                            chefid = deliveryShipOrders.getChefId();
                        }

                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
//                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Delivery person", "RejectOrder");
                                FirebaseDatabase.getInstance().getReference().child("Chef").child(chefid).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String chefmobph = dataSnapshot.getValue(String.class);

//
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue();
                                        progressDialog.dismiss();
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

        holder.Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.Accept.setVisibility(View.GONE);
                holder.Vieworder.setVisibility(View.GONE);
                holder.Reject.setVisibility(View.GONE);


                Toast.makeText(context, "Order Rejected..Pull down to refresh the page", Toast.LENGTH_SHORT).show();

                progressDialog.setMessage("Please wait...");
                progressDialog.show();


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            DeliveryShipOrders deliveryShipOrders = dataSnapshot1.getValue(DeliveryShipOrders.class);
                            chefid = deliveryShipOrders.getChefId();
                        }

                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
//                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Delivery person", "RejectOrder");
                                FirebaseDatabase.getInstance().getReference().child("Chef").child(chefid).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String chefmobph = dataSnapshot.getValue(String.class);

                                        holder.notifMessage.setText("Order Accepted : Your Order has been Rejected by the Delivery person");
                                        holder.notifNumber.setText("+91" + chefmobph);
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
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue();
                                        progressDialog.dismiss();
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
        return deliveryShipOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, mobilenumber, lplp, chefst, countord;
        Button Vieworder, Accept, Reject, remove2;
        EditText notifMessage, notifNumber;
        Button send_notif, direc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.ad1);
            mobilenumber = itemView.findViewById(R.id.MB1);
            grandtotalprice = itemView.findViewById(R.id.TP1);
            lplp = itemView.findViewById(R.id.lplp);
            Vieworder = itemView.findViewById(R.id.view1);
            Accept = itemView.findViewById(R.id.accept1);
            Reject = itemView.findViewById(R.id.reject1);
            remove2 = itemView.findViewById(R.id.remove2);
            chefst = itemView.findViewById(R.id.chefst);
            countord = itemView.findViewById(R.id.countord);

            notifMessage= itemView.findViewById(R.id.notifMessage);
            notifNumber= itemView.findViewById(R.id.notifNumber);
            send_notif= itemView.findViewById(R.id.send_notif);
            direc= itemView.findViewById(R.id.direc);


        }
    }
}
