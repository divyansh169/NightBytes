package com.citymart.app.CustomerFoodPanel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.Type13;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.ChefFoodPanel.Type41;
import com.citymart.app.ChefFoodPanel.Type67;
import com.citymart.app.ChefFoodPanel.Type68;
import com.citymart.app.ChefFoodPanel.Type7;
import com.citymart.app.CustomerFoodPanel_BottomNavigation;
import com.citymart.app.SendNotification.APIService;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.SendNotification.Client;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.ChefFoodPanel.Type;
import com.citymart.app.R;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class CustomerTrackFragment extends Fragment {

    RecyclerView recyclerView;
    private List<CustomerFinalOrders> customerFinalOrdersList;
    private CustomerTrackAdapter adapter;
    DatabaseReference databaseReference;
    TextView grandtotal, Address,Status, messchef, annchef, ordcnt, don, minago;
    LinearLayout total, kokl, kokla;
    public static String chefannouncement, chefnameop;
    public static String gmat;
    private APIService apiService;
    public static String state, city,sub;
    public static String chefkiid,chefidentity,jid, ssuburban, sstate, ccity;
    public static String rund;
    public static String orderID;
    EditText notifMessage, notifNumber;
    Button send_notif;
//    public static String rozarid, rozarkey;
    LottieAnimationView jjf;
//    private String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
//    private String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;
    Button Payond, addmoreitems;
//    private FirebaseAuth auth;
//    final int UPI_PAYMENT = 0;
//    private static final String TAG = "Razorpay";
//    Checkout checkout;
//    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Track");
        View v = inflater.inflate(R.layout.fragment_customertrack, null);
        recyclerView = v.findViewById(R.id.recyclefinalorders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        grandtotal = v.findViewById(R.id.Rs);
        Address = v.findViewById(R.id.addresstrack);
        jjf = v.findViewById(R.id.jjf);
        messchef = v.findViewById(R.id.messchef);
        ordcnt = v.findViewById(R.id.ordcnt);
        annchef = v.findViewById(R.id.annchef);
        Status=v.findViewById(R.id.status);
        notifMessage= v.findViewById(R.id.notifMessage);
        notifNumber= v.findViewById(R.id.notifNumber);
        send_notif= v.findViewById(R.id.send_notif);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        minago=v.findViewById(R.id.minago);
        total = v.findViewById(R.id.btnn);
        don = v.findViewById(R.id.don);
//        reqcan = v.findViewById(R.id.reqcan);
//        Checkout.preload(getActivity());
//        auth=FirebaseAuth.getInstance();
//        user=auth.getCurrentUser();
        kokl = v.findViewById(R.id.kokl);
        kokla = v.findViewById(R.id.kokla);
        Payond = v.findViewById(R.id.Payond);
        addmoreitems = v.findViewById(R.id.addmoreitems);
        customerFinalOrdersList = new ArrayList<>();
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);



//        DatabaseReference datsu = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        datsu.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                Chef chef = dataSnapshot.getValue(Chef.class);
//
//                state=chef.getState();
//                city=chef.getCity();
//                sub=chef.getSuburban();
//
//                annchef.setText(sub);

//                DatabaseReference datsu = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(state).child(city).child(sub).child("anouncement");
//                datsu.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        Type6 type6 = dataSnapshot.getValue(Type6.class);
//                        annchef.setText(type6.getcfnote());



//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});

        CustomerTrackOrder();

        return v;
    }

    private void CustomerTrackOrder() {

        

        databaseReference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                customerFinalOrdersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
                    rund = snapshot.getKey();
                    data.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                CustomerFinalOrders customerFinalOrders = snapshot1.getValue(CustomerFinalOrders.class);
                                customerFinalOrdersList.add(customerFinalOrders);
                                chefidentity = customerFinalOrders.getChefId();
                            }

                            FirebaseDatabase.getInstance().getReference("Chef").child(chefidentity).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final Chef chif = dataSnapshot.getValue(Chef.class);

                                    ccity = chif.getCity();
                                    sstate = chif.getState();
                                    ssuburban = chif.getSuburban();

                            if (customerFinalOrdersList.size() == 0) {
                                Address.setVisibility(View.INVISIBLE);
                                total.setVisibility(View.INVISIBLE);
                            } else {
//                                Address.setVisibility(View.VISIBLE);
                                total.setVisibility(View.VISIBLE);
                            }
                            adapter = new CustomerTrackAdapter(getContext(), customerFinalOrdersList);
                            recyclerView.setAdapter(adapter);
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

                    DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    datu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Type type = dataSnapshot.getValue(Type.class);

//                            messchef = type.getchefmessage();
                            chefannouncement = type.getchefmessage();
                            messchef.setText(type.getchefmessage());

                            DatabaseReference dathu = FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            dathu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists()){

                                        Type13 type13 = dataSnapshot.getValue(Type13.class);
                                        String fetchedDateTime = type13.getordertime();

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

                                            minago.setText("Order Time : " + timeAgo);

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                    }


//                                }
//
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });



                            DatabaseReference dbvatu = FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund);
                            dbvatu.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Type67 type67 = dataSnapshot.getValue(Type67.class);

                                    if(dataSnapshot.hasChild("ordn")) {
                                        ordcnt.setText(type67.getordn());
                                    }

                                    if(type67.getchefsts().contains("Pay on Delivery")){
                                        kokl.setVisibility(View.VISIBLE);
                                        don.setVisibility(View.GONE);
                                    }
                                    else {
                                        kokl.setVisibility(View.GONE);
                                        don.setVisibility(View.VISIBLE);
                                    }

//                                    DatabaseReference datuca = FirebaseDatabase.getInstance().getReference("chefpaytm").child(chefidentity);
////
//                                    datuca.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Type41 type41 = dataSnapshot.getValue(Type41.class);
////                                                            razoridtext.setText(type4.getRzpid());
//                                                jid = type41.getpaytmid();


                                    DatabaseReference datuing = FirebaseDatabase.getInstance().getReference("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    datuing.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {

                                                Type7 type7 = dataSnapshot.getValue(Type7.class);
                                                if(((type7.getordermethod()).contains("Dine-In"))){
                                                    kokla.setVisibility(View.VISIBLE);
                                                }
                                                else{
                                                    kokla.setVisibility(View.GONE);
                                                }
                                                }






                                            addmoreitems.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            showDialog();


                                            }
                                    });






                                    Payond.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                                DatabaseReference datuq = FirebaseDatabase.getInstance().getReference("chefrzp").child(chefidentity);
//
                                                datuq.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
//                                                            Type4 type4 = dataSnapshot.getValue(Type4.class);
////                                                            razoridtext.setText(type4.getRzpid());
//                                                            rozarid = (String) type4.getRzpid();
//                                                            rozarkey = (String) type4.getRzpkey();


//                                                DatabaseReference datucaza = FirebaseDatabase.getInstance().getReference("Chef").child(chefidentity);
////
//                                                datucaza.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                    @Override
//                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                        if (dataSnapshot.exists()) {

//                                                            Chef hdik = dataSnapshot.getValue(Chef.class);
//
//                                                            chefnameop = hdik.getFname();

//                                                            Payond.setOnClickListener(new View.OnClickListener() {
//                                                                @Override
//                                                                public void onClick(View v) {
                                                                    Intent intn = new Intent(getActivity(), OnlinePOD.class);
                                                                    startActivity(intn);
//                                                                    getActivity().finish();
//                                                                    finish();

//                                                            DatabaseReference iidbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(cheffsid);
//
//                                                            Type67 type67= new Type67("Payment Done");
//                                                            iidbref.setValue(type67);

//                                                                    DatabaseReference datugtf = FirebaseDatabase.getInstance().getReference("chefrzp").child(chefidentity);
////
//                                                                    datugtf.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                        @Override
//                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                                                            if (dataSnapshot.exists()) {
//                                                                               RequestQueue queue = Volley.newRequestQueue(getActivity());
//                                                                               String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?kid="+rozarid+"&ksec="+rozarkey+"&amt="+(Integer.parseInt(gmat) * 100);
//
//                                                                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                                                                                        new Response.Listener<String>() {
//                                                                                            @Override
//                                                                                            public void onResponse(String response) {
//                                                                                                parseResponse(response);
////                                                                                                    startPayment(chefname, rozarid);
//                                                                                            }
//                                                                                        }, new Response.ErrorListener() {
//                                                                                    @Override
//                                                                                    public void onErrorResponse(VolleyError error) {
////                                                                                            textView.setText("That didn't work!");
//                                                                                    }
//                                                                                });
//
//                                                                                queue.add(stringRequest);
//
//
////                                                                            } else {
////                                                                                Toast.makeText(getActivity(), "Choose Other Method for making payment for this Seller", Toast.LENGTH_SHORT).show();
////                                                                            }
//
//
//                                                                        }
//
//                                                                        @Override
//                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                        }
//                                                                    });


//                                                                }
//                                                            });

//                                            Payond.setOnClickListener(new View.OnClickListener() {
//                                                @Override
//                                                public void onClick(View v) {
//                                                    //amount upi-id name note
////                                                    payUsingUpi(gmat,jid, chefnameop, "Payment for Order");
////                                                    gotourl(jid+"&am="+gmat);
//
////
//                                                }
//                                            });


//                                            }
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
                                        }
                                                        else {
                                                            Toast.makeText(getActivity(), "Choose Other Method for making payment for this Seller", Toast.LENGTH_SHORT).show();
                                                        }


//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                        }
                                    });

//






//                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    String usertoken = dataSnapshot.getValue(String.class);
//                                    sendNotifications(usertoken, "Note", "Note is " + chefannouncement, "Note");
////                                    progressDialog.dismiss();
////                                    ReusableCodeForAll.ShowAlert(context, "", "Delivery Charge of Rs." + fnumber + " has been added to customer's Total Price. Wait for the Customer to make Payment, Pull down the page to Refresh!");

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
                            try{
                                gmat = customerFinalOrders1.getGrandTotalPrice();
                                grandtotal.setText("₹ " + gmat);

//                                grandtotal.setText("₹ " + customerFinalOrders1.getGrandTotalPrice());

//                                Address.setText((customerFinalOrders1.getAddress()).substring(22));
//                                Address.setText((customerFinalOrders1.getAddress()).substring((customerFinalOrders1.getAddress()).indexOf(':') + 1).trim());
                                Status.setText(customerFinalOrders1.getStatus());
//                                if(Status.getText() == "Your order is waiting to be prepared by Seller..." ){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.fooddeliveryprocess);
//                                }else if(Status.getText() == "Chef is preparing your order..." || Status.getText() ==  "Your Order is being prepared..." ){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.loadingutensils1);
//                                }else if(Status.getText() == "Order is Prepared..."){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.help);
//                                }else if(Status.getText() == "Order is Prepared and shifted to delivery person..."){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.back);
//                                }else if(Status.getText() == "Your Order is on the way..."){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.fooddeliveryprocess);
//                                }else if(Status.getText() == "Your Order is delivered"){
//                                    jjf.setVisibility(View.VISIBLE);
//                                    jjf.setAnimation(R.raw.fooddeliveryprocess);
//                                }
//                                else{
//                                    jjf.setVisibility(View.GONE);
//                                }
                            }catch (Exception e){
                                Log.d("CustomerTrackFragment", "onDataChange: "+e);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void showDialog() {
    Dialog dialog = new Dialog(getActivity());
    dialog.setContentView(R.layout.dialog_dishes);

    RecyclerView recyclerView = dialog.findViewById(R.id.dishesRecyclerView);
    TextView grandTotalTextView = dialog.findViewById(R.id.grandTotalTextView);
    Button removeAllButton = dialog.findViewById(R.id.removeAllButton);
    Button placeOrderButton = dialog.findViewById(R.id.placeOrderButton);

    // Set up RecyclerView
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    List<Dish> dishList = new ArrayList<>();
    DishAdapter adapter = new DishAdapter(dishList);
    recyclerView.setAdapter(adapter);

    adapter.setGrandTotalListener(grandTotal -> grandTotalTextView.setText("Grand Total: Rs " + grandTotal));

    // Fetch data from Firebase and update the RecyclerView
    fetchDishes(adapter);
    dialog.show();

    removeAllButton.setOnClickListener(v -> {
//        dishList.clear();
//        adapter.notifyDataSetChanged();
//        grandTotalTextView.setText("Grand Total: Rs 0");
        dialog.dismiss();
    });

    placeOrderButton.setOnClickListener(v -> {
        List<Dish> selectedDishes = adapter.getSelectedDishes();

        if (selectedDishes.isEmpty()) {
            Toast.makeText(getActivity(), "No dishes selected", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference customerOrderRef = database.getReference("CustomerFinalOrders").child(userId).child(rund);
        DatabaseReference chefWaitingOrdersRef = database.getReference("ChefWaitingOrders").child(chefidentity).child(rund);

        final int[] newGrandTotal = {0};


        customerOrderRef.child("Dishes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (Dish selectedDish : selectedDishes) {
                    String dishId = selectedDish.getDishId();
                    int selectedQuantity = Integer.parseInt(selectedDish.getQuantity());
                    int selectedTotalPrice = Integer.parseInt(selectedDish.getTotalPrice());

                    if (snapshot.hasChild(dishId)) {
                        // Update existing dish in CustomerFinalOrders
                        DataSnapshot existingDishSnapshot = snapshot.child(dishId);
                        int existingQuantity = Integer.parseInt(existingDishSnapshot.child("DishQuantity").getValue(String.class));
                        int existingTotalPrice = Integer.parseInt(existingDishSnapshot.child("TotalPrice").getValue(String.class));

                        int updatedQuantity = existingQuantity + selectedQuantity;
                        int updatedTotalPrice = existingTotalPrice + selectedTotalPrice;

                        customerOrderRef.child("Dishes").child(dishId).child("DishQuantity").setValue(String.valueOf(updatedQuantity));
                        customerOrderRef.child("Dishes").child(dishId).child("TotalPrice").setValue(String.valueOf(updatedTotalPrice));
                    } else {
                        // Add new dish to CustomerFinalOrders with only the required fields
                        customerOrderRef.child("Dishes").child(dishId).setValue(selectedDish.toMap());
                    }

                    newGrandTotal[0] += selectedTotalPrice;
                }

                // Update OtherInformation in CustomerFinalOrders
                customerOrderRef.child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int existingGrandTotal = 0;
                        if (snapshot.hasChild("GrandTotalPrice")) {
                            existingGrandTotal = Integer.parseInt(snapshot.child("GrandTotalPrice").getValue(String.class));
                        }
                        int updatedGrandTotal = existingGrandTotal + newGrandTotal[0];

                        customerOrderRef.child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(updatedGrandTotal));
                        customerOrderRef.child("OtherInformation").child("Status").setValue("Your add-on order is waiting to be prepared by Seller...");

                        // Now handle ChefWaitingOrders based on the condition
                        handleChefWaitingOrders(chefWaitingOrdersRef, selectedDishes, newGrandTotal[0]);

                        // Show a toast message to the user
                        Toast.makeText(getActivity(), "Add-On Order Placed!", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference("Chef").child(chefidentity).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                                    String chefmobph = dataSnapshot.getValue(String.class);

                                    notifMessage.setText("New Add-On Order for Dine-In");
                                    notifNumber.setText("+91" + chefmobph);
//
                                    if (!notifMessage.getText().toString().isEmpty() && (!notifNumber.getText().toString().isEmpty())) {
                                        new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                            @Override
                                            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

                                            }

                                            @Override
                                            public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @com.google.firebase.database.annotations.NotNull IOException e) {

                                            }

                                        });
                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                        // Dismiss the dialog
                        dialog.dismiss();
                        Intent z = new Intent(getActivity(), CustomerFoodPanel_BottomNavigation.class);
                        z.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(z);
                        getActivity().finish();
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
    });
}

private void fetchDishes(DishAdapter adapter) {
//    FirebaseAuth auth = FirebaseAuth.getInstance();
//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference userRef = database.getReference("Customer").child(auth.getCurrentUser().getUid());

//    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            String city = snapshot.child("City").getValue(String.class);
//            String state = snapshot.child("State").getValue(String.class);
//            String suburban = snapshot.child("Suburban").getValue(String.class);

            DatabaseReference dishesRef = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails")
                    .child(sstate).child(ccity).child(ssuburban).child(chefidentity);

            dishesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                        Dish dish = dishSnapshot.getValue(Dish.class);
                        if (dish != null && !dish.getStockcnt().equals("0")) {
                            dish.setQuantity("0");
                            adapter.addDish(dish);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
//        }
//
//        @Override
//        public void onCancelled(@NonNull DatabaseError error) {
//            // Handle error
//        }
//    });
}

private void handleChefWaitingOrders(DatabaseReference chefWaitingOrdersRef, List<Dish> selectedDishes, int newGrandTotal) {
    final int[] newgt = {0};
    chefWaitingOrdersRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                // Case 1: Update existing ChefWaitingOrders
                for (Dish selectedDish : selectedDishes) {
                    String dishId = selectedDish.getDishId();
                    int selectedQuantity = Integer.parseInt(selectedDish.getQuantity());
                    int selectedTotalPrice = Integer.parseInt(selectedDish.getTotalPrice());

                    if (snapshot.child("Dishes").hasChild(dishId)) {
                        // Update existing dish in ChefWaitingOrders
                        DataSnapshot existingDishSnapshot = snapshot.child("Dishes").child(dishId);
                        int existingQuantity = Integer.parseInt(existingDishSnapshot.child("DishQuantity").getValue(String.class));
                        int existingTotalPrice = Integer.parseInt(existingDishSnapshot.child("TotalPrice").getValue(String.class));

                        int updatedQuantity = existingQuantity + selectedQuantity;
                        int updatedTotalPrice = existingTotalPrice + selectedTotalPrice;

                        chefWaitingOrdersRef.child("Dishes").child(dishId).child("DishQuantity").setValue(String.valueOf(updatedQuantity));
                        chefWaitingOrdersRef.child("Dishes").child(dishId).child("TotalPrice").setValue(String.valueOf(updatedTotalPrice));
                    } else {
                        // Add new dish to ChefWaitingOrders with only the required fields
                        chefWaitingOrdersRef.child("Dishes").child(dishId).setValue(selectedDish.toMap());
                    }
                    newgt[0] += selectedTotalPrice;
                }

                // Update GrandTotalPrice in OtherInformation
                updateGrandTotalInChefOrders(snapshot, newGrandTotal);
            } else {
                // Case 2: Add new ChefWaitingOrders
//                copyOtherInformationFromCustomerOrders(chefWaitingOrdersRef);


                for (Dish selectedDish : selectedDishes) {
                    int selectedTotalPrice = Integer.parseInt(selectedDish.getTotalPrice());
                    chefWaitingOrdersRef.child("Dishes").child(selectedDish.getDishId()).setValue(selectedDish.toMap());
                    newgt[0] += selectedTotalPrice;
                }

                // Copy OtherInformation from CustomerFinalOrders to ChefWaitingOrders

                // Add OtherInformation to ChefWaitingOrders
//                chefWaitingOrdersRef.child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(newGrandTotal));
//                FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefidentity).child(rund).child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(newgt[0]));
                chefWaitingOrdersRef.child("AddOn").setValue(1);

                //
                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rund).child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot finalOrderInfoSnapshot) {

                        CustomerFinalOrders1 customerFinalOrders1 = finalOrderInfoSnapshot.getValue(CustomerFinalOrders1.class);
                        HashMap<String, String> hashMap1 = new HashMap<>();
                        hashMap1.put("Address", customerFinalOrders1.getAddress());
                        hashMap1.put("GrandTotalPrice", String.valueOf(newgt[0]));
                        hashMap1.put("MobileNumber", customerFinalOrders1.getMobileNumber());
                        hashMap1.put("Name", customerFinalOrders1.getName());
                        hashMap1.put("Note", customerFinalOrders1.getNote());
                        hashMap1.put("RandomUID", rund);
                        hashMap1.put("Status", "Your add-on order is waiting to be prepared by Seller...");
                        FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefidentity).child(rund).child("OtherInformation").setValue(hashMap1);
//                        userid = chefWaitingOrders.getUserId();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });

                            //
                
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Handle error
        }
    });
}
private void updateGrandTotalInChefOrders(DataSnapshot snapshot, int newGrandTotal) {
    int existingGrandTotal = 0;
    if (snapshot.child("OtherInformation").hasChild("GrandTotalPrice")) {
        existingGrandTotal = Integer.parseInt(snapshot.child("OtherInformation").child("GrandTotalPrice").getValue(String.class));
    }
    int updatedGrandTotal = existingGrandTotal + newGrandTotal;

    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(chefidentity).child(rund).child("OtherInformation").child("GrandTotalPrice").setValue(String.valueOf(updatedGrandTotal));
}
private void copyOtherInformationFromCustomerOrders(DatabaseReference chefWaitingOrdersRef) {
    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rund).child("OtherInformation").addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot customerSnapshot) {
            chefWaitingOrdersRef.child("OtherInformation").setValue(customerSnapshot.getValue());
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            // Handle error
        }
    });
}







//    private void gotourl(String s){
//
//        Uri uri = Uri.parse(s);
//        startActivity(new Intent(Intent.ACTION_VIEW, uri));
//
//    }

//    public void parseResponse(String res){
//
//
//        try {
//            JSONObject object = new JSONObject(res);
//            orderID = object.getString("orderID");
////            info.setText(orderID);
//            startPayment();
//        } catch(JSONException e){
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    public void startPayment() {
////                private void startPayment() {
////****************************************************************************************************************************
//        /**
//         * Instantiate Checkout
//         */
//
//
//        Checkout checkout = new Checkout();
////                checkout.setKeyID(rozarid);
//        checkout.setKeyID(rozarid);
//
//
//
//        /**
//         * Set your logo here
//         */
//        checkout.setImage(R.drawable.ic_shopping_cart_black_24dp);
//
//
//
//
//        /**
//         * Reference to current activity
//         */
//        final Activity activity = getActivity();
//
//
//
//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
//        try {
//            JSONObject options = new JSONObject();
//
//
//            options.put("name", chefnameop);
//            options.put("description", order_reference_no);
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", orderID);
////            options.put("order_id", order.get("id"));//from response of step 3.
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "INR");
//
////                    options.put("receipt", "rcptid_11");
//
////                    final double orignum = Integer.parseInt(price) * 100 * 1.00;
////                    final double amt = 0.9764;
////                    final double result = orignum/amt;
////                    double amt = orignum/0.9764;
////                    BigDecimal roundedvalue = BigDecimal.valueOf(amt).setScale(2, RoundingMode.HALF_UP);
////                    DecimalFormat df = new DecimalFormat("#.##");
////                    amt = Double.parseDouble(df.format(amt));
////                    double finamt = ;
////                        options.put("amount", Math.floor(result*100)/100 );//pass amount in currency subunits
////                        options.put("amount", Double.parseDouble(df.format(  (orignum + ((orignum * 0.0236))  )  )  ) );//pass amount in currency subunits
//            //
//            //gfg
//
//            //int amount = Math.round(Float.parseFloat(samount) * 100);
//
////                    options.put("amount", Integer.parseInt(price) * 100 );//pass amount in currency subunits 2.36% ie 0.0236
//            options.put("prefill.email", user.getEmail());
////            options.put("prefill.contact","");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);
//
////                    JSONObject settlementRequest = new JSONObject();
//////                    settlementRequest.put("amount", 200000);
////                    settlementRequest.put("settle_full_balance", false);
////                    options.put("settlement", settlementRequest);
//
//
//            //extra for auto capture/////////////////////////////////////////////////////////////////////////////////
////                    RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
////                    JSONObject orderRequest = new JSONObject();
////                    orderRequest.put("amount",50000);
////                    orderRequest.put("currency","INR");
//
////                    JSONObject payment = new JSONObject();
////                    payment.put("capture","automatic");
////                    JSONObject captureOptions = new JSONObject();
////                    captureOptions.put("automatic_expiry_period",12);
////                    captureOptions.put("manual_expiry_period",7200);
////                    captureOptions.put("refund_speed","optimum");
////                    payment.put("capture_options",captureOptions);
////                    options.put("payment",payment);
////                    Order order = razorpay.Orders.create(orderRequest);
////                } catch (RazorpayException e) {
////                    // Handle Exception
////                    System.out.println(e.getMessage());
////                }
////                    Order order = razorpay.orders.create(options);
//            ////////////////////////////////////////////////////////////////////////////////////////////////
//
//            checkout.open(activity, options);
//
//        } catch (Exception e) {
//            Log.e("TAG", "Error in starting Razorpay Checkout", e);
//        }
//    }
//
//
//    @Override
//    public void onPaymentSuccess(String s) {
//        Log.d("ONSUCCESS", "onPaymentSuccess: " + s);
//        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
//
////                Intent intent = new Intent(PayableOrders.this, CustomerPayment.class);
////                intent.putExtra("RandomUID", randomuidd);
////                startActivity(intent);
////                finish();
//
////        DatabaseReference ldbrefxxz = FirebaseDatabase.getInstance().getReference("ChefOrderCounter");
////        ldbrefxxz.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////
////                if(dataSnapshot.exists()) {
////                    Type68 typechapm68z = dataSnapshot.getValue(Type68.class);
////                    orderno = typechapm68z.getcunt();
//
//
//                    FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund).child("chefsts").setValue("Payment Done");
//
//
//
////        notifMessage.setText("POD Payment Received Successfully!");
////        notifNumber.setText("+91" + chefmop);
//////
////        if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())) {
////            new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
////                @Override
////                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
////
////                }
////
////                @Override
////                public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @NotNull IOException e) {
////
////                }
////
////            });
////        }
//
//
//
//
////        openDialog();
//
//
//
//
//
//
//
//
//
//
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Log.d("ONERROR", "onPaymentError: " + s);
//        Toast.makeText(getActivity(), "Error: " + s, Toast.LENGTH_LONG).show();
////        textView.setText("Error: " + s);
//    }


//    @Override
//    public void onPaymentSuccess(String s, PaymentData paymentData) {
//        Log.d("ONSUCCESS", "onPaymentSuccess: " + s);
//        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
//        FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund).child("chefsts").setValue("Payment Done");
//        Toast.makeText(getActivity(), "Please refresh the page", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onPaymentError(int i, String s, PaymentData paymentData) {
//        Log.d("ONERROR", "onPaymentError: " + s);
//        Toast.makeText(getContext(), "Error: " + s, Toast.LENGTH_LONG).show();
//
//    }
}
