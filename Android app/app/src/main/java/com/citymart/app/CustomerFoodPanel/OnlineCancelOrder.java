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
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
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

public class OnlineCancelOrder extends AppCompatActivity implements PaymentResultListener {

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
        setContentView(R.layout.activity_onlinecancelorder);
//        recyclefinalorders = findViewById(R.id.recyclefinalorders);
        Payond = findViewById(R.id.Payond);
        Checkout.preload(getApplicationContext());
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        dialog= new Dialog(this);





        DatabaseReference axudatabaseReference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        axudatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    customerPaymentOrdersList.clear();
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final String randomuid = snapshot.getKey();
                        rund = randomuid;

                        final DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {

                                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                        CustomerPaymentOrders customerPaymentOrders = snapshot1.getValue(CustomerPaymentOrders.class);
//                                        customerPaymentOrdersList.add(customerPaymentOrders);
//                                        final String dis = snapshot1.getKey();
//                                        dishisid = dis;
                                        chefidentity = customerPaymentOrders.getChefId();
                                    }


                                    Payond.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Toast.makeText(OnlineCancelOrder.this, "Please wait for payment page to open", Toast.LENGTH_SHORT).show();

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
                                                    String url = "https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?kid=" + "rzp_live_cZZPCw7KRIyrHE" + "&ksec=" + "vIQ1yUknoXYAR5XnVOA9dEdX" + "&amt=" + (1 * 100);

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
                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        checkout.setKeyID("rzp_live_cZZPCw7KRIyrHE");



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

            options.put("name", "Cancellation Charge");
            options.put("description", order_reference_no);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", orderID);
//            options.put("order_id", order.get("id"));//from response of step 3.
            options.put("theme.color", "#EB0D0D");
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


//        FirebaseDatabase.getInstance().getReference("ChefStatus").child(rund).child("chefsts").setValue("Payment Done");
//        Intent i = new Intent(OnlineCancelOrder.this, CustomerTrackFragment.class);
//        startActivity(i);
//        finish();
        FirebaseDatabase.getInstance().getReference("ordertype").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(chefidentity).child(rund).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(rund).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(OnlineCancelOrder.this);
        builder.setMessage("Order cancelled successfully");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Intent b = new Intent(OnlineCancelOrder.this, CustomerFoodPanel_BottomNavigation.class);
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
