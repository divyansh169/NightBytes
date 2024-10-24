package com.citymart.app.DeliveryFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.ChefFoodPanel.Type15;
import com.citymart.app.ChefFoodPanel.Type67;
import com.citymart.app.R;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryShipOrderFragmentAdapter extends RecyclerView.Adapter<DeliveryShipOrderFragmentAdapter.ViewHolder> {

    private Context context;
    private List<DeliveryShipFinalOrders1> deliveryShipFinalOrders1list;
    private APIService apiService;
    public static String etaddr, sty;




    public DeliveryShipOrderFragmentAdapter(Context context, List<DeliveryShipFinalOrders1> deliveryShipFinalOrders1list) {
        this.deliveryShipFinalOrders1list = deliveryShipFinalOrders1list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.delivery_shiporders, parent, false);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        return new DeliveryShipOrderFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final DeliveryShipFinalOrders1 deliveryShipFinalOrders1 = deliveryShipFinalOrders1list.get(position);
//        holder.Address.setText((deliveryShipFinalOrders1.getAddress()).substring(22));
        holder.Address.setText((deliveryShipFinalOrders1.getAddress()).substring((deliveryShipFinalOrders1.getAddress()).indexOf(':') + 1).trim());
        etaddr = deliveryShipFinalOrders1.getAddress();
        sty = deliveryShipFinalOrders1.getRandomUID();
        holder.grandtotalprice.setText("Grand Total: ₹ " + deliveryShipFinalOrders1.getGrandTotalPrice());
        DatabaseReference ldbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(sty);
        ldbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Type67 typechapm = dataSnapshot.getValue(Type67.class);
//                    holder.chefst.setText(typechapm.getchefsts());
//                    holder.countord.setText(typechapm.getordn());
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
        holder.mobilenumber.setText("+91" + deliveryShipFinalOrders1.getMobileNumber());
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        final String random = deliveryShipFinalOrders1.getRandomUID();
        final String userid = deliveryShipFinalOrders1.getUserId();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DeliveryShipOrderView.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
            }
        });
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



//        DatabaseReference gty = FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(random);
//        gty.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type15 typechal = dataSnapshot.getValue(Type15.class);
//                holder.lplp.setText(typechal.getacceptval());
//
//                if (Objects.equals(holder.lplp.getText(), "1")) {
//
////                    holder.Vieworder.setVisibility(View.GONE);
////                    holder.ShipOrder.setVisibility(View.GONE);
//                    holder.remove2.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });





        holder.ShipOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(random).child("OtherInformation").child("Status").setValue("Your Order is on the way...").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String usertoken = dataSnapshot.getValue(String.class);
//                                sendNotifications(usertoken, "Estimated Time", "Your Order has been collected by Delivery Person, He is on the way", "DeliverOrder");
//                                FCMSend.pushNotification(
//                                        getActivity(),
//                                        usertoken,
//                                        "Order Confirmed",
//                                        "Payment mode is confirmed by user, Now you may start preparing order"
//
//                                );
                                FirebaseDatabase.getInstance().getReference().child("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String usermobph = dataSnapshot.getValue(String.class);

                                        holder.notifMessage.setText("Estimated Time : Your Order has been collected by Delivery Person.");
                                        holder.notifNumber.setText("+91" + usermobph);

                                        if (!holder.notifMessage.getText().toString().isEmpty()&&(!holder.notifNumber.getText().toString().isEmpty())){
                                            new FCMSender().send(String.format(NotificationMessage.message,"messaging", holder.notifMessage.getText().toString(), holder.notifNumber.getText().toString()), new okhttp3.Callback() {
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
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent intent = new Intent(context, Delivery_ShippingOrder.class);
                        intent.putExtra("RandomUID",random);
                        context.startActivity(intent);
                    }
                });

            }
        });

//        holder.remove2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Toast.makeText(context, "Order Removed..Pull down to refresh the page", Toast.LENGTH_SHORT).show();
//
////                progressDialog.setMessage("Please wait...");
////                progressDialog.show();
//
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(delid).child(randomuid).child("Dishes");
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//                            DeliveryShipOrders deliveryShipOrders = dataSnapshot1.getValue(DeliveryShipOrders.class);
//                            chefid = deliveryShipOrders.getChefId();
//                        }
//
//                        FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                String usertoken = dataSnapshot.getValue(String.class);
////                                sendNotifications(usertoken, "Order Rejected", "Your Order has been Rejected by the Delivery person", "RejectOrder");
//                                FirebaseDatabase.getInstance().getReference().child("Chef").child(chefid).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                        String chefmobph = dataSnapshot.getValue(String.class);
//
////
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });
//
//
//                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(delid).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(delid).child(randomuid).child("OtherInformation").removeValue();
////                                        progressDialog.dismiss();
//                                    }
//                                });
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//            }
//        });

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
        return deliveryShipFinalOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, mobilenumber, lplp, chefst, countord;
        Button Vieworder, ShipOrder, remove2;
        EditText notifMessage, notifNumber;
        Button send_notif, direc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Address = itemView.findViewById(R.id.ad2);
            mobilenumber = itemView.findViewById(R.id.MB2);
            grandtotalprice = itemView.findViewById(R.id.TP2);
            chefst = itemView.findViewById(R.id.chefst);
            Vieworder = itemView.findViewById(R.id.view2);
            ShipOrder = itemView.findViewById(R.id.ship2);
            remove2 = itemView.findViewById(R.id.remove2);
            lplp = itemView.findViewById(R.id.lplp);
            direc= itemView.findViewById(R.id.direc);
            countord = itemView.findViewById(R.id.countord);


            notifMessage= itemView.findViewById(R.id.notifMessage);
            notifNumber= itemView.findViewById(R.id.notifNumber);
            send_notif= itemView.findViewById(R.id.send_notif);


        }
    }
}
