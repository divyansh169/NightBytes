package com.citymart.app.CustomerFoodPanel;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.Type;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.ChefFoodPanel.Type67;
import com.citymart.app.CustomerFoodPanel_BottomNavigation;
import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class OnlinePOD extends AppCompatActivity implements PaymentResultListener {

    Button Payond;
    private FirebaseAuth auth;
    private static final String TAG = "Razorpay";
    Checkout checkout;
    private FirebaseUser user;
    private String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;
    public static String chefidentity;
    public static String rund;
    public static String orderID;
    public static String rozarid, rozarkey;
    public static String chefnameop;
    public static String gmat;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onlinepod);
//        recyclefinalorders = findViewById(R.id.recyclefinalorders);
        Payond = findViewById(R.id.Payond);
        Checkout.preload(getApplicationContext());
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        dialog= new Dialog(this);





        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                customerFinalOrdersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
                    rund = snapshot.getKey();
                    data.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                CustomerFinalOrders customerFinalOrders = snapshot1.getValue(CustomerFinalOrders.class);
//                                customerFinalOrdersList.add(customerFinalOrders);
                                chefidentity = customerFinalOrders.getChefId();
                                break;
                            }

//                            if (customerFinalOrdersList.size() == 0) {
//                                Address.setVisibility(View.INVISIBLE);
//                                total.setVisibility(View.INVISIBLE);
//                            } else {
//                                Address.setVisibility(View.VISIBLE);
//                                total.setVisibility(View.VISIBLE);
//                            }
//                            adapter = new CustomerTrackAdapter(getContext(), customerFinalOrdersList);
//                            recyclerView.setAdapter(adapter);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

//                    DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    datu.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            Type type = dataSnapshot.getValue(Type.class);

//                            messchef = type.getchefmessage();
//                            chefannouncement = type.getchefmessage();
//                            messchef.setText(type.getchefmessage());



//                            DatabaseReference dbvatu = FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund);
//                            dbvatu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    Type67 type67 = dataSnapshot.getValue(Type67.class);
//                                    ordcnt.setText(type67.getordn());

//                                    if(type67.getchefsts().contains("Pay on Delivery")){
//                                        kokl.setVisibility(View.VISIBLE);
//                                        don.setVisibility(View.GONE);
//                                    }
//                                    else {
//                                        kokl.setVisibility(View.GONE);
//                                        don.setVisibility(View.VISIBLE);
//                                    }

//                                    DatabaseReference datuca = FirebaseDatabase.getInstance().getReference("chefpaytm").child(chefidentity);
////
//                                    datuca.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                Type41 type41 = dataSnapshot.getValue(Type41.class);
////                                                            razoridtext.setText(type4.getRzpid());
//                                                jid = type41.getpaytmid();

                                    DatabaseReference datuq = FirebaseDatabase.getInstance().getReference("chefrzp").child(chefidentity);
//
                                    datuq.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
                                                Type4 type4 = dataSnapshot.getValue(Type4.class);
//                                                            razoridtext.setText(type4.getRzpid());
                                                rozarid = (String) type4.getRzpid();
                                                rozarkey = (String) type4.getRzpkey();


                                                DatabaseReference datucaza = FirebaseDatabase.getInstance().getReference("Chef").child(chefidentity);
//
                                                datucaza.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                        if (dataSnapshot.exists()) {

                                                            Chef hdik = dataSnapshot.getValue(Chef.class);

                                                            chefnameop = hdik.getFname();

                                                            Payond.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {

                                                                    Toast.makeText(OnlinePOD.this, "Please wait for payment page to open", Toast.LENGTH_SHORT).show();

//                                                            DatabaseReference iidbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(cheffsid);
//
//                                                            Type67 type67= new Type67("Payment Done");
//                                                            iidbref.setValue(type67);

                                                                    DatabaseReference datugtf = FirebaseDatabase.getInstance().getReference("chefrzp").child(chefidentity);
//
                                                                    datugtf.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                            if (dataSnapshot.exists()) {
                                                                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                                                            String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?kid="+rozarid+"&ksec="+rozarkey+"&amt="+(Integer.parseInt(gmat) * 100);

                                                                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                                                                    new Response.Listener<String>() {
                                                                                        @Override
                                                                                        public void onResponse(String response) {
                                                                                            parseResponse(response);
//                                                                                                    startPayment(chefname, rozarid);
                                                                                        }
                                                                                    }, new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError error) {
//                                                                                            textView.setText("That didn't work!");
                                                                                }
                                                                            });

                                                                            queue.add(stringRequest);


//                                                                            } else {
//                                                                                Toast.makeText(getActivity(), "Choose Other Method for making payment for this Seller", Toast.LENGTH_SHORT).show();
//                                                                            }


                                                                        }

                                                                        @Override
                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        }
                                                                    });


                                                                }
                                                            });

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


//                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });
//                                            }
//                                            else {
//                                                Toast.makeText(OnlinePOD.this, "Choose Other Method for making payment for this Seller", Toast.LENGTH_SHORT).show();
//                                            }
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
//                                                grandtotal.setText("₹ " + gmat);

//                                grandtotal.setText("₹ " + customerFinalOrders1.getGrandTotalPrice());

//                                Address.setText((customerFinalOrders1.getAddress()).substring(22));
//                                                Address.setText((customerFinalOrders1.getAddress()).substring((customerFinalOrders1.getAddress()).indexOf(':') + 1).trim());
//                                                Status.setText(customerFinalOrders1.getStatus());
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
//                                                Log.d("OnlinePOD", "onDataChange: "+e);
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


//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });



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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    public void parseResponse(String res){


        try {
            JSONObject object = new JSONObject(res);
            orderID = object.getString("orderID");
//            info.setText(orderID);
            startPayment();
        } catch(JSONException e){
            e.printStackTrace();
        }

    }



    public void startPayment() {
//                private void startPayment() {
//****************************************************************************************************************************
        /**
         * Instantiate Checkout
         */


        Checkout checkout = new Checkout();
//                checkout.setKeyID(rozarid);
        checkout.setKeyID(rozarid);



        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.ic_shopping_cart_black_24dp);




        /**
         * Reference to current activity
         */
        final Activity activity = this;



        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();


            options.put("name", chefnameop);
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderID);
//            options.put("order_id", order.get("id"));//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");

//                    options.put("receipt", "rcptid_11");

//                    final double orignum = Integer.parseInt(price) * 100 * 1.00;
//                    final double amt = 0.9764;
//                    final double result = orignum/amt;
//                    double amt = orignum/0.9764;
//                    BigDecimal roundedvalue = BigDecimal.valueOf(amt).setScale(2, RoundingMode.HALF_UP);
//                    DecimalFormat df = new DecimalFormat("#.##");
//                    amt = Double.parseDouble(df.format(amt));
//                    double finamt = ;
//                        options.put("amount", Math.floor(result*100)/100 );//pass amount in currency subunits
//                        options.put("amount", Double.parseDouble(df.format(  (orignum + ((orignum * 0.0236))  )  )  ) );//pass amount in currency subunits
            //
            //gfg

            //int amount = Math.round(Float.parseFloat(samount) * 100);

//                    options.put("amount", Integer.parseInt(price) * 100 );//pass amount in currency subunits 2.36% ie 0.0236
            options.put("prefill.email", user.getEmail());
//            options.put("prefill.contact","");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

//                    JSONObject settlementRequest = new JSONObject();
////                    settlementRequest.put("amount", 200000);
//                    settlementRequest.put("settle_full_balance", false);
//                    options.put("settlement", settlementRequest);


            //extra for auto capture/////////////////////////////////////////////////////////////////////////////////
//                    RazorpayClient razorpay = new RazorpayClient("[YOUR_KEY_ID]", "[YOUR_KEY_SECRET]");
//                    JSONObject orderRequest = new JSONObject();
//                    orderRequest.put("amount",50000);
//                    orderRequest.put("currency","INR");

//                    JSONObject payment = new JSONObject();
//                    payment.put("capture","automatic");
//                    JSONObject captureOptions = new JSONObject();
//                    captureOptions.put("automatic_expiry_period",12);
//                    captureOptions.put("manual_expiry_period",7200);
//                    captureOptions.put("refund_speed","optimum");
//                    payment.put("capture_options",captureOptions);
//                    options.put("payment",payment);
//                    Order order = razorpay.Orders.create(orderRequest);
//                } catch (RazorpayException e) {
//                    // Handle Exception
//                    System.out.println(e.getMessage());
//                }
//                    Order order = razorpay.orders.create(options);
            ////////////////////////////////////////////////////////////////////////////////////////////////

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Log.d("ONSUCCESS", "onPaymentSuccess: " + s);
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(PayableOrders.this, CustomerPayment.class);
//                intent.putExtra("RandomUID", randomuidd);
//                startActivity(intent);
//                finish();

//        DatabaseReference ldbrefxxz = FirebaseDatabase.getInstance().getReference("ChefOrderCounter");
//        ldbrefxxz.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                if(dataSnapshot.exists()) {
//                    Type68 typechapm68z = dataSnapshot.getValue(Type68.class);
//                    orderno = typechapm68z.getcunt();
        openDialog();


        FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund).child("chefsts").setValue("Payment Done");
//        Intent i = new Intent(OnlinePOD.this, CustomerTrackFragment.class);
//        startActivity(i);
//        finish();

        AlertDialog.Builder builder = new AlertDialog.Builder(OnlinePOD.this);
        builder.setMessage("Payment done successfully");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Intent b = new Intent(OnlinePOD.this, CustomerFoodPanel_BottomNavigation.class);
                b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(b);
                finish();

            }
        });
        AlertDialog alert = builder.create();
        alert.show();



//        notifMessage.setText("POD Payment Received Successfully!");
//        notifNumber.setText("+91" + chefmop);
////
//        if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())) {
//            new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
//                @Override
//                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//                }
//
//                @Override
//                public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                }
//
//            });
//        }




//        openDialog();










    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("ONERROR", "onPaymentError: " + s);
        Toast.makeText(getApplicationContext(), "Error: " + s, Toast.LENGTH_LONG).show();
//        textView.setText("Error: " + s);
    }

    private void openDialog() {

        dialog.setContentView(R.layout.activity_loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LottieAnimationView loadingutensils = dialog.findViewById(R.id.progressAnimationView);
        dialog.show();

    }
}
