package com.citymart.app.CustomerFoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.ChefPendingOrders1;
import com.citymart.app.ChefFoodPanel.Type;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.ChefFoodPanel.Type67;
import com.citymart.app.ChefFoodPanel.Type68;
import com.citymart.app.ChefFoodPanel.TypeC;
import com.citymart.app.Coupon;
import com.citymart.app.CustomerFoodPanel_BottomNavigation;
import com.citymart.app.R;
//import com.citymart.app.RazopayResponse;
//import com.citymart.app.Retrofit.APIServices;
//import com.citymart.app.Retrofit.RetrofitClass;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
//import com.github.kittinunf.fuel.Fuel;
//import com.github.kittinunf.fuel.core.FuelError;
//import com.github.kittinunf.fuel.core.Request;
//import com.github.kittinunf.fuel.core.Response;
//import com.github.kittinunf.fuel.core.ResponseHandler;
import com.google.android.datatransport.runtime.dagger.Lazy;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.play.core.integrity.e;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.annotations.NotNull;
//import com.paytm.pgsdk.PaytmOrder;
//import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
//import com.paytm.pgsdk.TransactionManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;

public class PayableOrders extends AppCompatActivity implements PaymentResultListener {

    RecyclerView recyclerView;
    public static String orderno;
//    public int orderCounter = 1; // Initialize the counter
    private List<CustomerPaymentOrders> customerPaymentOrdersList;
    private PayableOrderAdapter adapter;
    DatabaseReference databaseReference;
    private LinearLayout pay;
    Button payment;
    Button payondelivery;
    Button cancelorder;
    Button paytmpayment;
    TextView mfs;
    Button couponbtn,viewcop;
    private Context context;
    private List<ChefPendingOrders1> chefPendingOrders1list;
    LottieAnimationView loadingutensils;
    Dialog dialog;
//    Button takeaway;
    TextView grandtotal, grandtotal1, grandtotal2, grandtotal3 ;
    TextView pri, pri1, pri2, pri3;
    TextView info;
//    TextView razortext, cheflname, addresschef, citychef, statechef, areachef, postcodechef, housechef;
//    TextView razorchefname;
//    TextView chefmobile;
    TextView validtext,cpnyes;
//    TextView razoridtext;
//    TextView cfnotetext;
    EditText couponcode;
//    TextView recipient;
//    LinearLayout recepientlayout;
    public static CharSequence razortextvalue;
    public static String dishisid;
    private SwipeRefreshLayout swipeRefreshLayout;
    public String price, copn;
    public String convcharge;
    public String randomuidd;
    private FirebaseAuth auth;
    private APIService apiService;
    private FirebaseUser user;
    public String usserid;
    public static String cheffsid;
    public static String chefname;
    public static String coupontext;
    public static String newnote= "Not Applicable";
    public static String currentuserid;
    public static String rozarid, delcost, discost, orderID, rozarkey;
    public static int gt,dc,disc,amtpay, newamtpay;
    public static String chefmop;
    public static int amountt=0;
//    public static String OrderID = "";
//    private String string_payondlv;
    String randomuuuiiiddd;
    public String payondbtn="0";
    DatabaseReference dataaa;
//    String orderID,callbackurl;

//    private static final int TEZ_REQUEST_CODE = 123;

//    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    EditText notifMessage, notifNumber;
    Button send_notif;

    private static final String TAG = "Razorpay";
    Checkout checkout;
//    RazorpayClient razorpayClient;
//    Order order;

    private String order_receipt_no = "Receipt No. " +  System.currentTimeMillis()/1000;
    private String order_reference_no = "Reference No. #" +  System.currentTimeMillis()/1000;

//    public PayableOrders(Context context, List<CustomerPaymentOrders> customerPaymentOrdersList) {
//        this.customerPaymentOrdersList = customerPaymentOrdersList;
//        this.context = context;
//
//    }



//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payable_orders);
        recyclerView = findViewById(R.id.recyclepayableorder);
        pay = findViewById(R.id.btn);
//        loadingutensils = findViewById(R.id.animationView);
        dialog= new Dialog(this);
        grandtotal = findViewById(R.id.rs);
        grandtotal1 = findViewById(R.id.rs1);
        grandtotal2 = findViewById(R.id.rs2);
        mfs = findViewById(R.id.mfs);
        grandtotal3 = findViewById(R.id.rs3);
        couponcode = findViewById(R.id.couponcode);
        info = findViewById(R.id.info);
        payment = (Button) findViewById(R.id.paymentmethod);
        cancelorder = (Button) findViewById(R.id.cancelorder);
//        razortext = (TextView) findViewById(R.id.razortext);
//        recipient = (TextView) findViewById(R.id.recipient);
        pri = (TextView) findViewById(R.id.pri);
        pri1 = (TextView) findViewById(R.id.pri1);
        pri2 = (TextView) findViewById(R.id.pri2);
        pri3 = (TextView) findViewById(R.id.pri3);
        validtext = (TextView) findViewById(R.id.validtext);
        cpnyes = (TextView) findViewById(R.id.cpnyes);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
//        recepientlayout = (LinearLayout) findViewById(R.id.recepientlayout);

//        cheflname = (TextView) findViewById(R.id.cheflname);
//        addresschef = (TextView) findViewById(R.id.addresschef);
//        citychef = (TextView) findViewById(R.id.citychef);
//        statechef = (TextView) findViewById(R.id.statechef);
//        areachef = (TextView) findViewById(R.id.areachef);
//        postcodechef = (TextView) findViewById(R.id.postcodechef);
//        housechef = (TextView) findViewById(R.id.housechef);

        notifMessage= findViewById(R.id.notifMessage);
        notifNumber= findViewById(R.id.notifNumber);
        send_notif= findViewById(R.id.send_notif);

//        razorchefname = (TextView) findViewById(R.id.razorchefname);
//        chefmobile = (TextView) findViewById(R.id.chefmobile);
//        razoridtext = (TextView) findViewById(R.id.razoridtext);
//        cfnotetext = (TextView) findViewById(R.id.cfnotetext);
        payondelivery = (Button) findViewById(R.id.payondelivery);
        paytmpayment = (Button) findViewById(R.id.paytmpayment);
//        paytmbtn = (Button) findViewById(R.id.paytmbtn);
        couponbtn = (Button) findViewById(R.id.couponbtn);
        viewcop = (Button) findViewById(R.id.viewcop);
//        takeaway = (Button) findViewById(R.id.takeaway);
        Checkout.preload(getApplicationContext());
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PayableOrders.this));
        customerPaymentOrdersList = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.Swipe2);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.green);
        adapter = new PayableOrderAdapter(PayableOrders.this, customerPaymentOrdersList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView = findViewById(R.id.recyclepayableorder);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PayableOrders.this));
                customerPaymentOrdersList = new ArrayList<>();
                CustomerpayableOrders();
            }
        });
        CustomerpayableOrders();

    }

    private void CustomerpayableOrders() {


//        if (customerPaymentOrdersList.size() == 0) {
//            pay.setVisibility(View.GONE);
//        } else {
//            pay.setVisibility(View.VISIBLE);


//        payondelivery.setVisibility(View.GONE);
//        final List<String> mArrayList = new ArrayList<>();
//        usserid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference().child("deliveryCharge").child(usserid);
//thisone
//        databaseReference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    final String randomuido = snapshot.getKey();
//                    randomuuuiiiddd = randomuido;
//thisone

//        databaseReference = FirebaseDatabase.getInstance().getReference("deliveryCharge");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    final String randomuido = snapshot.getKey();
//                    randomuuuiiiddd = randomuido;


        DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        datu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                Type type = dataSnapshot.getValue(Type.class);

                mfs.setText(type.getchefmessage());

                payondbtn = type.getEnablepodt_flag();
                delcost = type.getdeliverychargetext();
                discost = type.getdiscnttext();

                pri.setText("GrandTotal");
                pri1.setText("Delivery / Add-On Charge");
                pri2.setText("Discount / Offers");
                pri3.setText("Amount to Pay");


                if (payondbtn.equals("1")) {
                    payondelivery.setVisibility(View.VISIBLE);
                }
//                            else if(payondbtn.equals("10")){
//                                payondelivery.setVisibility(View.GONE);
//                            }
//                            else{
//                                payondelivery.setVisibility(View.GONE);
//                            }


//                            Type6 type6 = dataSnapshot.getValue(Type6.class);
//                            razortext.setText(type6.getcfnote());


//                FirebaseDatabase.getInstance().getReference("deliveryCharge").child(randomuuuiiiddd).removeValue();


                databaseReference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {  //thisy value to single
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            customerPaymentOrdersList.clear();
                            for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                final String randomuid = snapshot.getKey();
                                randomuidd = randomuid;

                                final DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes");
                                data.addListenerForSingleValueEvent(new ValueEventListener() { //thisy value to single
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){

                                        for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                            CustomerPaymentOrders customerPaymentOrders = snapshot1.getValue(CustomerPaymentOrders.class);
                                            customerPaymentOrdersList.add(customerPaymentOrders);
                                            final String dis = snapshot1.getKey();
                                            dishisid = dis;
                                            cheffsid = customerPaymentOrders.getChefId();
//                                                    cfnotetext.setText(cheffsid);
//
//
//
//
//                                                    final DatabaseReference datia = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("Dishes").child(dishisid);
//                                                    datia.addValueEventListener(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                            CustomerPaymentOrders hju = dataSnapshot.getValue(CustomerPaymentOrders.class);
//                                                            cheffsid=hju.getChefId();
//                                                            razortext.setText(hju.getChefId());
////                                                            razoridtext.setText(hju.getrpayid());
////                                                            razortextvalue=hju.getChefId();
////                                                            chefrazorpay();
                                        }

                                        DatabaseReference datu = FirebaseDatabase.getInstance().getReference("chefrzp").child(cheffsid);
//
                                        datu.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    Type4 type4 = dataSnapshot.getValue(Type4.class);
//                                                            razoridtext.setText(type4.getRzpid());
                                                    rozarid = (String) type4.getRzpid();
                                                    rozarkey = (String) type4.getRzpkey();
                                                }
//                                                        else {
//                                                            rozarid = "null";
////                                                            Toast.makeText(PayableOrders.this, "Choose Pay on Delivery for making payment for this seller", Toast.LENGTH_SHORT).show();
//                                                        }


                                                DatabaseReference dattu = FirebaseDatabase.getInstance().getReference("Chef").child(cheffsid);
//
                                                dattu.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.exists()) {
                                                            Chef chief = dataSnapshot.getValue(Chef.class);
//                                                                razortext.setText(chief.getFname());
                                                            chefname = chief.getFname();
//                                                                razorchefname.setText(chief.getEmailID());
//                                                                chefmobile.setText(chief.getMobile());
                                                            chefmop = chief.getMobile();
//                                                                cheflname.setText(chief.getLname());
//                                                                addresschef.setText(chief.getSuburban());
//                                                                citychef.setText(chief.getCity());
//                                                                statechef.setText(chief.getState());
//                                                                areachef.setText(chief.getArea());
//                                                                postcodechef.setText(chief.getPostcode());
//                                                                housechef.setText(chief.getHouse());


                                                            if (customerPaymentOrdersList.size() == 0) {
                                                                pay.setVisibility(View.GONE);
                                                            } else {
                                                                pay.setVisibility(View.VISIBLE);

                                                                payment.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                        Toast.makeText(PayableOrders.this, "Please wait for payment page to open", Toast.LENGTH_SHORT).show();

//                                                            DatabaseReference iidbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(cheffsid);
//
//                                                            Type67 type67= new Type67("Payment Done");
//                                                            iidbref.setValue(type67);

                                                                        DatabaseReference datu = FirebaseDatabase.getInstance().getReference("chefrzp").child(cheffsid);
//
                                                                        datu.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.exists()) {
//                                                                                    try {
//                                                                                        getOrderID();
//                                                                                    } catch (IOException e) {
//                                                                                        e.printStackTrace();
//                                                                                    }
//                                                                                    startPayment(chefname, rozarid);
//                                                                                    tothepayment(chefname, rozarid);
                //                                                                      startPayment();

                //                                                                      gpayment();
                                                                                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//                                                                                    String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?amt=10002";
//                                                                                    String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?amt="+(Integer.parseInt(price) * 100);
//                                                                                    String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder";

                                                                                    String url ="https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?kid="+rozarid+"&ksec="+rozarkey+"&amt="+(Integer.parseInt(price) * 100);

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


                                                                                } else {
                                                                                    Toast.makeText(PayableOrders.this, "Choose Other Method for making payment for this Seller", Toast.LENGTH_SHORT).show();
                                                                                }


                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });


                                                                    }
                                                                });
                                                                payondelivery.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
//                                                            Intent intent = new Intent(PayableOrders.this, CustomerPayment.class);
//                                                            intent.putExtra("RandomUID", randomuid);
//                                                            startActivity(intent);
//                                                            finish();


//                                                                    String orderNumber = String.valueOf(orderCounter);

                                                                        DatabaseReference ldbrefxx = FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid);
                                                                        ldbrefxx.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                if (dataSnapshot.exists()) {
                                                                                    Type68 typechapm68 = dataSnapshot.getValue(Type68.class);
                                                                                    orderno = typechapm68.getcunt();


                                                                                    DatabaseReference idbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(randomuidd);

                                                                                    Type67 typex67 = new Type67("Pay on Delivery", orderno);
                                                                                    idbref.setValue(typex67);


//                                                                    orderCounter= orderCounter + 1;
                                                                                    int opcnt = Integer.parseInt(orderno);
                                                                                    opcnt = opcnt + 1;
                                                                                    orderno = Integer.toString(opcnt);
                                                                                    FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid).child("cunt").setValue(orderno);

//                                                                                Type68 type68 = new Type68(orderno);
//                                                                                idxbref.setValue(type68);


                                                                                }
                                                                                else {
                                                                                    DatabaseReference idbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(randomuidd);

                                                                                    Type67 typex67 = new Type67("Pay on Delivery", "1");
                                                                                    idbref.setValue(typex67);
                                                                                    FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid).child("cunt").setValue("2");
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                            }
                                                                        });


                                                                        notifMessage.setText("Order Confirmed : Payment mode confirmed by user, Now you may start preparing order");
                                                                        notifNumber.setText("+91" + chefmop);
//
                                                                        if (!notifMessage.getText().toString().isEmpty() && (!notifNumber.getText().toString().isEmpty())) {
                                                                            new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                                @Override
                                                                                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

                                                                                }

                                                                                @Override
                                                                                public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                                }

                                                                            });
                                                                        }


                                                                        openDialog();
                                                                        DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes");
                                                                        databaseReferencel.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                if (dataSnapshot.exists()) {
                                                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                        final CustomerPaymentOrders customerPaymentOrders = dataSnapshot1.getValue(CustomerPaymentOrders.class);
                                                                                        HashMap<String, String> hashMap = new HashMap<>();
                                                                                        String dishid = customerPaymentOrders.getDishId();
                                                                                        hashMap.put("ChefId", customerPaymentOrders.getChefId());
                                                                                        hashMap.put("DishId", customerPaymentOrders.getDishId());
                                                                                        hashMap.put("DishName", customerPaymentOrders.getDishName());
                                                                                        hashMap.put("DishPrice", customerPaymentOrders.getDishPrice());
                                                                                        hashMap.put("DishQuantity", customerPaymentOrders.getDishQuantity());
                                                                                        hashMap.put("RandomUID", randomuid);
                                                                                        hashMap.put("TotalPrice", customerPaymentOrders.getTotalPrice());
                                                                                        hashMap.put("UserId", customerPaymentOrders.getUserId());
//                            hashMap.put("rpayid", customerPaymentOrders.getrpayid());
                                                                                        FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").child(dishid).setValue(hashMap);
                                                                                    }
                                                                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
                                                                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                            if(dataSnapshot.exists()){

                                                                                            final CustomerPaymentOrders1 customerPaymentOrders1 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                                                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                                                                            hashMap1.put("Address", customerPaymentOrders1.getAddress());
                                                                                            hashMap1.put("GrandTotalPrice", customerPaymentOrders1.getGrandTotalPrice());
                                                                                            hashMap1.put("MobileNumber", customerPaymentOrders1.getMobileNumber());
                                                                                            hashMap1.put("Name", customerPaymentOrders1.getName());
                                                                                            hashMap1.put("Note", customerPaymentOrders1.getNote() + " . Coupon Offer " + newnote);
                                                                                            hashMap1.put("RandomUID", randomuid);
                                                                                            hashMap1.put("Status", "Your order is waiting to be prepared by Seller...");
                                                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").setValue(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                @Override
                                                                                                public void onSuccess(Void aVoid) {
                                                                                                    DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes");
                                                                                                    Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                                                            if(dataSnapshot.exists()){

                                                                                                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                                                                final CustomerPaymentOrders customerPaymentOrderss = snapshot.getValue(CustomerPaymentOrders.class);
                                                                                                                HashMap<String, String> hashMap2 = new HashMap<>();
                                                                                                                String dishid = customerPaymentOrderss.getDishId();
                                                                                                                cheffsid = customerPaymentOrderss.getChefId();
                                                                                                                hashMap2.put("ChefId", customerPaymentOrderss.getChefId());
                                                                                                                hashMap2.put("DishId", customerPaymentOrderss.getDishId());
                                                                                                                hashMap2.put("DishName", customerPaymentOrderss.getDishName());
                                                                                                                hashMap2.put("DishPrice", customerPaymentOrderss.getDishPrice());
                                                                                                                hashMap2.put("DishQuantity", customerPaymentOrderss.getDishQuantity());
                                                                                                                hashMap2.put("RandomUID", randomuid);
                                                                                                                hashMap2.put("TotalPrice", customerPaymentOrderss.getTotalPrice());
                                                                                                                hashMap2.put("UserId", customerPaymentOrderss.getUserId());
//                                                    hashMap2.put("rpayid", customerPaymentOrderss.getrpayid());
                                                                                                                FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(cheffsid).child(randomuid).child("Dishes").child(dishid).setValue(hashMap2);
                                                                                                            }
                                                                                                            DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
                                                                                                            dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                @Override
                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                    if(dataSnapshot.exists()){
                                                                                                                    CustomerPaymentOrders1 customerPaymentOrders11 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                                                                                                    HashMap<String, String> hashMap3 = new HashMap<>();
                                                                                                                    hashMap3.put("Address", customerPaymentOrders11.getAddress());
                                                                                                                    hashMap3.put("GrandTotalPrice", customerPaymentOrders11.getGrandTotalPrice());
                                                                                                                    hashMap3.put("MobileNumber", customerPaymentOrders11.getMobileNumber());
                                                                                                                    hashMap3.put("Name", customerPaymentOrders11.getName());
                                                                                                                    hashMap3.put("Note", customerPaymentOrders11.getNote() + " . Coupon Offer " + newnote);
                                                                                                                    hashMap3.put("RandomUID", randomuid);
                                                                                                                    hashMap3.put("Status", "Your order is waiting to be prepared by Seller...");
                                                                                                                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(cheffsid).child(randomuid).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                        @Override
                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                            FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(cheffsid).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(cheffsid).child(randomuid).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                        @Override
                                                                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                            FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                @Override
                                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(cheffsid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                                                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                                                                                                                                    sendNotifications(usertoken, "Order Confirmed", "Payment mode is confirmed by user, Now you can start the order", "Confirm");
//                                                                                                                                                    FCMSend.pushNotification(
//                                                                                                                                                            PayableOrders.this,
//                                                                                                                                                            usertoken,
//                                                                                                                                                            "Order Confirmed",
//                                                                                                                                                            "Payment mode is confirmed by user, Now you may start preparing order"
//
//                                                                                                                                                    );
//                                                                                                                                                    FirebaseDatabase.getInstance().getReference().child("Chef").child(cheffsid).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                                                        @Override
//                                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                                            String chefmobph = dataSnapshot.getValue(String.class);

//                                                                                                                                                    notifMessage.setText("Order Confirmed : Payment mode is confirmed by user, Now you may start preparing order");
//                                                                                                                                                    notifNumber.setText("+91" + chefmobph);
////
//                                                                                                                                                    if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())) {
//                                                                                                                                                        new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
//                                                                                                                                                            @Override
//                                                                                                                                                            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//                                                                                                                                                            }
//
//                                                                                                                                                            @Override
//                                                                                                                                                            public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                                                                                                                                                            }
//
//                                                                                                                                                        });
//                                                                                                                                                    }
//
//                                                                                                                                                        }
//
//                                                                                                                                                        @Override
//                                                                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                                                                                        }
//                                                                                                                                                    });
                                                                                                                                                                }

                                                                                                                                                                @Override
                                                                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                                                                }
                                                                                                                                                            });

                                                                                                                                                        }
                                                                                                                                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                                                                                        @Override
                                                                                                                                                        public void onSuccess(Void aVoid) {
                                                                                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(PayableOrders.this);
                                                                                                                                                            builder.setMessage("Payment mode confirmed, Now you can track your order. If you are paying offline now, track order page should show 'Payment Done' status. If not, ask seller to update your payment status");
                                                                                                                                                            builder.setCancelable(false);
                                                                                                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                                                                                                @Override
                                                                                                                                                                public void onClick(DialogInterface dialog, int which) {

                                                                                                                                                                    dialog.dismiss();
                                                                                                                                                                    Intent b = new Intent(PayableOrders.this, CustomerFoodPanel_BottomNavigation.class);
                                                                                                                                                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                                                                                    startActivity(b);
                                                                                                                                                                    finish();

                                                                                                                                                                }
                                                                                                                                                            });
                                                                                                                                                            AlertDialog alert = builder.create();
                                                                                                                                                            alert.show();
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
                                                                                            });
                                                                                        }


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
                                                                });
                                                                cancelorder.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

                                                                        Intent intna = new Intent(PayableOrders.this, OnlineCancelOrder.class);
                                                                        startActivity(intna);


                                                                    }
                                                                });



//                                                                orderID = String.valueOf(UUID.randomUUID());
//                                                                callbackurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderID;
                                                                paytmpayment.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {

//                                                                        orderID = String.valueOf(UUID.randomUUID());
//                                                                        callbackurl = "https://securegw-stage.paytm.in/theia/paytmCallback?ORDER_ID=" + orderID;

//                                                                        getTransactionToken(orderID, "220");

                                                                    }
                                                                });

                                                            }
                                                            adapter = new PayableOrderAdapter(PayableOrders.this, customerPaymentOrdersList);
                                                            recyclerView.setAdapter(adapter);
                                                            swipeRefreshLayout.setRefreshing(false);
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

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            CustomerPaymentOrders1 customerPaymentOrders1 = dataSnapshot.getValue(CustomerPaymentOrders1.class);

                                            dc = Integer.parseInt(delcost);
                                            disc = Integer.parseInt(discost);

                                            gt = (Integer.parseInt(customerPaymentOrders1.getGrandTotalPrice()) + disc) - dc;

                                            amtpay = Integer.parseInt(customerPaymentOrders1.getGrandTotalPrice());

                                            grandtotal.setText("₹ " + gt);
                                            grandtotal1.setText("₹ " + delcost);
                                            grandtotal2.setText("₹ " + discost);
                                            grandtotal3.setText("₹ " + amtpay);
//                                                    convcharge = Double.toString((Integer.parseInt(price) * 0.02));

//                                             couponbtn.setOnClickListener(new View.OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {

//                                                     coupontext = couponcode.getText().toString();
//                                                     DatabaseReference cref = FirebaseDatabase.getInstance().getReference("chefCoupons").child(cheffsid);
//                                                     cref.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                         @Override
//                                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                             if (dataSnapshot.exists()) {
//                                                                 TypeC typechal = dataSnapshot.getValue(TypeC.class);
//                                                                 int minorder = Integer.parseInt(typechal.getcpntxt2());
//                                                                 validtext.setVisibility(View.VISIBLE);
//                                                                 if (Objects.equals(coupontext, typechal.getcpntxt()) && gt >= minorder) {

//                                                                     if (Objects.equals(typechal.getcpntxt1(), "1")) {
//                                                                         couponbtn.setVisibility(View.GONE);
//                                                                         newamtpay = amtpay - Integer.parseInt(typechal.getcpntxt3());
//                                                                         customerPaymentOrders1.setGrandTotalPrice(String.valueOf(newamtpay));
//                                                                         grandtotal3.setText("₹ " + newamtpay);
//                                                                         validtext.setText("Coupon Applied Successsfully!!");
//                                                                         cpnyes.setVisibility(View.VISIBLE);
//                                                                         cpnyes.setText("Discount of Rs." + typechal.getcpntxt3() + " is added");
//                                                                         price = customerPaymentOrders1.getGrandTotalPrice();
//                                                                         newnote = "Applied with discount of Rs." + Integer.parseInt(typechal.getcpntxt3()) + " on Rs." + amtpay + " . Effective Price = Rs." + newamtpay;
// //                                                                                        DatabaseReference Lcref = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuid).child("OtherInformation");
// //
// //                                                                                        Lcref.addListenerForSingleValueEvent(new ValueEventListener() {
// //                                                                                            @Override
// //                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
// //
// //                                                                                                CustomerPaymentOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
// //                                                                                                CustomerPaymentOrders1 customerFinalOrders11 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
// //                                                                                                customerFinalOrders1.setGrandTotalPrice(String.valueOf(newamtpay));
// //                                                                                                customerFinalOrders11.setGrandTotalPrice(String.valueOf(newamtpay));
// //
// //
// //                                                                                                DatabaseReference Lecref =  FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(cheffsid).child(randomuidd).child("OtherInformation");
// //
// //                                                                                                Lecref.addListenerForSingleValueEvent(new ValueEventListener() {
// //                                                                                                    @Override
// //                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
// //
// //                                                                                                        ChefPaymentOrders1 chefPaymentOrders1 = dataSnapshot.getValue(ChefPaymentOrders1.class);
// //                                                                                                        chefPaymentOrders1.setGrandTotalPrice(String.valueOf(newamtpay));
// //
// //
// //                                                                                                    }
// //                                                                                                    @Override
// //                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {
// //
// //                                                                                                    }
// //                                                                                                });
// //
// //
// //                                                                                            }
// //                                                                                            @Override
// //                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
// //
// //                                                                                            }
// //                                                                                        });


//                                                                     }
//                                                                     else if (Objects.equals(typechal.getcpntxt1(), "2")) {
//                                                                         couponbtn.setVisibility(View.GONE);
//                                                                         newnote = "Free " + typechal.getcpntxt3();
//                                                                         validtext.setText("Coupon Applied Successsfully!!");
//                                                                         cpnyes.setVisibility(View.VISIBLE);
//                                                                         cpnyes.setText("Offer of Free " + typechal.getcpntxt3() + " is added");
//                                                                     } else {
//                                                                         validtext.setText("Coupon is Invalid or Expired");
//                                                                     }

//                                                                 } else {
//                                                                     validtext.setText("Invalid Coupon Code or Condtion. Please Check Minimum Order Value Before Applying");
//                                                                 }

//                                                             } else {
//                                                                 validtext.setVisibility(View.VISIBLE);
//                                                                 validtext.setText("No Coupon exists for this seller");
//                                                             }

//                                                         }

//                                                         @Override
//                                                         public void onCancelled(@NonNull DatabaseError databaseError) {

//                                                         }
//                                                     });

//                                                 }
//                                             });
                                            viewcop.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                Intent b = new Intent(PayableOrders.this, ViewCouponsActivity.class);
                                                b.putExtra("sellerId", cheffsid);
                                                startActivity(b);
                                        }
                                    });
                                            couponbtn.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("chefCoupons").child(cheffsid);

                                                    coupontext = couponcode.getText().toString();
                                                    Query couponQuery = databaseReference.orderByChild("couponCode").equalTo(coupontext);   
                                                        couponQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if (dataSnapshot.exists()) {
                                                                    // Coupon exists, apply the logic
                                                                    for (DataSnapshot couponSnapshot : dataSnapshot.getChildren()) {
                                                                        Coupon coupon = couponSnapshot.getValue(Coupon.class);
                                                                        // applyCoupon(coupon);  // Method to handle applying the coupon
                                                                        String couponType = coupon.getCouponType();
                                                                        int X = Integer.parseInt(coupon.getDiscount());
                                                                        int Y = Integer.parseInt(coupon.getMinOrderValue());
                                                                        String Z = coupon.getFreeItem();

                                                                        switch (couponType) {
//                                                                            case "₹X off on any order value":
//                                                                                newamtpay = amtpay - X;
//                                                                                break;

                                                                            case "₹X off above ₹Y":
                                                                                if (gt >= Y) {
                                                                                    newamtpay = amtpay - X;
                                                                                    cpnyes.setText("Discount of Rs." + X + " is added");
                                                                                    newnote = "Applied with discount of Rs." + X + " on Rs." + amtpay + " . Effective Price = Rs." + newamtpay;
                                                                                } else {
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Invalid Coupon Condition. Please Check Minimum Order Value Before Applying");
                                                                                    return;
                                                                                }
                                                                                break;

//                                                                            case "X% off on any order value":
//                                                                                newamtpay = amtpay - (amtpay * X / 100);
//                                                                                break;

                                                                            case "X% off above ₹Y":
                                                                                if (gt >= Y) {
                                                                                    newamtpay = amtpay - (amtpay * X / 100);
                                                                                    cpnyes.setText("Discount of " + X + "% is added");
                                                                                    newnote = "Applied with discount of " + X + "% on Rs." + amtpay + " . Effective Price = Rs." + newamtpay;
                                                                                } else {
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Invalid Coupon Condition. Please Check Minimum Order Value Before Applying");
                                                                                    return;
                                                                                }
                                                                                break;

//                                                                            case "Get free Z on any order value":
//                                                                                newnote = "Free " + Z;
//                                                                                couponbtn.setVisibility(View.GONE);
//                                                                                validtext.setText("Coupon Applied Successfully!!");
//                                                                                cpnyes.setVisibility(View.VISIBLE);
//                                                                                cpnyes.setText("Offer of Free " + Z + " is added");
//                                                                                return;

                                                                            case "Get free Z above ₹Y":
                                                                                if (gt >= Y) {
                                                                                    newnote = "Free " + Z;
                                                                                    couponbtn.setVisibility(View.GONE);
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Coupon Applied Successfully!!");
                                                                                    cpnyes.setVisibility(View.VISIBLE);
                                                                                    cpnyes.setText("Offer of Free " + Z + " is added");
                                                                                } else {
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Invalid Coupon Condition. Please Check Minimum Order Value Before Applying");
                                                                                }
                                                                                return;

                                                                            case "Free delivery above ₹Y":
                                                                                if (gt >= Y) {
                                                                                    newamtpay = (amtpay - dc);
                                                                                    couponbtn.setVisibility(View.GONE);
                                                                                    customerPaymentOrders1.setGrandTotalPrice(String.valueOf(newamtpay));
                                                                                    grandtotal3.setText("₹ " + newamtpay);
                                                                                    grandtotal1.setText("₹ 0");
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Coupon Applied Successfully!!");
                                                                                    cpnyes.setVisibility(View.VISIBLE);
                                                                                    cpnyes.setText("Free Delivery enabled! Delivery Charge of Rs." + delcost + " has been removed.");
                                                                                    price = customerPaymentOrders1.getGrandTotalPrice();
                                                                                    newnote = "Applied for free delivery with discount in delivery charge of Rs." + delcost + " on Rs." + amtpay + " . Effective Price = Rs." + newamtpay;
                                                                                } else {
                                                                                    validtext.setVisibility(View.VISIBLE);
                                                                                    validtext.setText("Invalid Coupon Condition. Please Check Minimum Order Value Before Applying");
                                                                                }
                                                                                return;

                                                                            default:
                                                                                validtext.setVisibility(View.VISIBLE);
                                                                                validtext.setText("Invalid Coupon Type");
                                                                                return;
                                                                        }

                                                                        couponbtn.setVisibility(View.GONE);
                                                                        customerPaymentOrders1.setGrandTotalPrice(String.valueOf(newamtpay));
                                                                        grandtotal3.setText("₹ " + newamtpay);
                                                                        validtext.setVisibility(View.VISIBLE);
                                                                        validtext.setText("Coupon Applied Successfully!!");
                                                                        cpnyes.setVisibility(View.VISIBLE);
                                                                        price = customerPaymentOrders1.getGrandTotalPrice();

                                                                        break;
                                                                    }
                                                                } else {
                                                                    // Coupon doesn't exist
                                                                    validtext.setVisibility(View.VISIBLE);
                                                                    validtext.setText("Invalid Coupon Code");
                                                                }
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                                // Handle possible errors
                                                                validtext.setVisibility(View.VISIBLE);
                                                                validtext.setText("Error checking coupon. Please try again.");
                                                            }
                                                        });
                                                }
                                            });



//                                                    info.setText("");
                                            price = customerPaymentOrders1.getGrandTotalPrice();
                                            swipeRefreshLayout.setRefreshing(false);


//                                                    DatabaseReference datifa = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(cheffsid);
//                                                    datifa.addValueEventListener(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                            Type6 types6 = dataSnapshot.getValue(Type6.class);
//                                                            cfnotetext.setText(types6.getcfnote());
//
//
//                                                        }
//
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                        }
//                                                    });


                                        }
//                                                }
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                }
//                                                }

                                        else {
                                            swipeRefreshLayout.setRefreshing(false);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


//                                    }
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                    }
//                                });


                            }
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                        }
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

//    }

        //this
//                    }

//                        }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//
//
//                        });

                        //this




            }


//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//    private fun getOrderID(){
//
//        val response: Response<RzorpayResponse> by lazy{
//            RetrofitClass.getRetrofit().getOrderID(amount)
//        }
//        if(response.isSuccessful){
//            orderID = response.body()?.orderID!!
//                startPayment()
//        }else {
//            Toast.makeText(applicationContext(), response.body()?.error?.description, Toast.LENGTH_SHORT).show()
//        }
//    }

//    private void getOrderID() {
//        APIServices apiServices = RetrofitClass.INSTANCE.getRetrofit();
//        Call<RazopayResponse> call = (Call<RazopayResponse>) apiServices.getOrderID(amountt);
//
//        call.enqueue(new Callback<RazopayResponse>() {
////            @Override
//            public void onResponse(Call<RazopayResponse> call, Response<RazopayResponse> response) {
//                if (response.code() == 200) { // Check the HTTP status code
//                    RazopayResponse razopayResponse = response.body();
//                    if (razopayResponse != null) {
//                        orderID = razopayResponse.getOrderID();
//                    }
////                    startPayment();
//                } else {
//                    Toast.makeText(getApplicationContext(), response.body() != null ? response.body().getError().getDescription() : "", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onResponse(Call<RazopayResponse> call, retrofit2.Response<RazopayResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<RazopayResponse> call, Throwable t) {
//                // Handle network call failure if needed
//            }
//        });
//    }

//    private void getOrderID() throws IOException {
//        Response<RazopayResponse> response = RetrofitClass.getRetrofit().getOrderID(Integer.parseInt(price) * 100 ).execute();
//        if (response.isSuccessful()) {
//            OrderID = response.body().getOrderID();
//            startPayment(chefname, rozarid);
////            initrzp();
//        }
////        else {
//////            Toast.makeText(this, response.body().getErrorDescription().getDescription(), Toast.LENGTH_SHORT).show();
////        }
//    }

//    private void tothepayment(String chefname, String rozarid) {
//
//        final Activity activity = this;
//
//        // Create an OkHttpClient instance
//        OkHttpClient client = new OkHttpClient();
//
//        // Define the Firebase Function URL
//        String functionUrl = "https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/createRazorpayOrder?amt=1000"; // Update with your actual URL
//
//        // Create an HTTP request
//        Request request = new Request.Builder()
//                .url(functionUrl)
//                .build();
//
//        // Make the HTTP request asynchronously
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
////                try {
////                    if (response.isSuccessful()) {
//                if (response.isSuccessful()) {
//
////                    String responseBody = response.body().string();
//                    String responseBody = response.body().toString();
//                    try {
//                        // Parse the JSON response to get the orderID
//                        JSONObject jsonObject = new JSONObject(responseBody);
//                        final String orderID = jsonObject.getString("orderID");
//
//
//                        // Now you have the orderID, proceed with starting the Razorpay Checkout
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Instantiate Checkout
//                                Checkout checkout = new Checkout();
////                checkout.setKeyID(rozarid);
//                                checkout.setKeyID(rozarid);
//
//
//                                /**
//                                 * Set your logo here
//                                 */
//                                checkout.setImage(R.drawable.ic_shopping_cart_black_24dp);
//
//
//                                /**
//                                 * Reference to current activity
//                                 */
//
//
//                                /**
//                                 * Pass your payment options to the Razorpay Checkout as a JSONObject
//                                 */
//                                try {
//                                    JSONObject options = new JSONObject();
//
//
//                                    options.put("name", chefname);
//                                    options.put("description", order_reference_no);
//                                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
////            options.put("order_id", order.get("id"));//from response of step 3.
//                                    options.put("theme.color", "#3399cc");
//                                    options.put("currency", "INR");
//
//                                    options.put("order_id", orderID);
//                                    options.put("receipt", "rcptid_11");
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
//                                    //
//                                    //gfg
//
//                                    //int amount = Math.round(Float.parseFloat(samount) * 100);
//
//                                    options.put("amount", Integer.parseInt(price) * 100);//pass amount in currency subunits 2.36% ie 0.0236
//                                    options.put("prefill.email", user.getEmail());
////            options.put("prefill.contact","");
//                                    JSONObject retryObj = new JSONObject();
//                                    retryObj.put("enabled", true);
//                                    retryObj.put("max_count", 4);
//                                    options.put("retry", retryObj);
//
//
//                                    //extra for auto capture/////////////////////////////////////////////////////////////////////////////////
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
//                                    ////////////////////////////////////////////////////////////////////////////////////////////////
//
//                                    checkout.open(activity, options);
//
//                                } catch (Exception e) {
//                                    Log.e("TAG", "Error in starting Razorpay Checkout", e);
//                                }
//
//                            }
//
//                        });
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        // Handle JSON parsing error or I/O error here
//                    }
//                }
//                    else {
//                    // Handle the error, e.g., by showing an error message to the user
//                    Log.e("HTTP Error", "HTTP Error: " + response.code());
//                }
//
//        }
//
////            @Override
////            public void onFailure(Call call, Throwable t) {
////
////            }
//
//            //        @Override
////        public void onFailure(Call call, IOException e) {
////            // Handle network error
////            Log.e("Network Error", "Network Error: " + e.getMessage());
////        }
//    });
//}

//    private void getOrderId() {
//        Response<RazopayResponse> response = null;
//
//        CoroutineScope(Dispatchers.IO).launch(new CoroutineScope() {
//            @Override
//            public void run() {
//                response = RetrofitClass.getRetrofit().getOrderID(amountt);
//
//                if (response.isSuccessful()) {
//                    OrderID = response.body().getOrderID();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            initiateRazorpaySdk();
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(), response.body().getError().getDescription(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            }
//        });
//    }

//    private void inititiatepaym(){
//        PayloadHelper payloadHelper = new PayloadHelper("INR", 100, "order_XXXXXXXXX");
//        payloadHelper.setName("Gaurav Kumar");
//        payloadHelper.setDescription("Description");
//        payloadHelper.setPrefillEmail("gaurav.kumar@example.com");
//        payloadHelper.setPrefillContact("9000090000");
//        payloadHelper.setPrefillCardNum("4111111111111111");
//        payloadHelper.setPrefillCardCvv("111");
//        payloadHelper.setPrefillCardExp("11/24");
//        payloadHelper.setPrefillMethod("card");
//        payloadHelper.setPrefillName("MerchantName");
//        payloadHelper.setSendSmsHash(true);
//        payloadHelper.setRetryMaxCount(4);
//        payloadHelper.setRetryEnabled(true);
//        payloadHelper.setColor("#000000");
//        payloadHelper.setAllowRotation(true);
//        payloadHelper.setRememberCustomer(true);
//        payloadHelper.setTimeout(10);
//        payloadHelper.setRedirect(true);
//        payloadHelper.setRecurring("1");
//        payloadHelper.setSubscriptionCardChange(true);
//        payloadHelper.setCustomerId("cust_XXXXXXXXXX");
//        payloadHelper.setCallbackUrl("https://accepts-posts.request");
//        payloadHelper.setSubscriptionId("sub_XXXXXXXXXX");
//        payloadHelper.setModalConfirmClose(true);
//        payloadHelper.setBackDropColor("#ffffff");
//        payloadHelper.setHideTopBar(true);
//        payloadHelper.setNotes(new JSONObject("{\"remarks\":\"Discount to cusomter\"}"));
//        payloadHelper.setReadOnlyEmail(true);
//        payloadHelper.setReadOnlyContact(true);
//        payloadHelper.setReadOnlyName(true);
//        payloadHelper.setImage("https://www.razorpay.com");
//        payloadHelper.setAmount(100);
//        payloadHelper.setCurrency("INR");
//        payloadHelper.setOrderId("order_XXXXXXXXXXXXXX");
//    }

//    private void initrzp() {
//
//        PayloadHelper payloadHelper = new PayloadHelper("INR", 100*100, OrderID);
//        Checkout checkout = new Checkout();
//        checkout.setKeyID(rozarid);
//        checkout.open(this, payloadHelper);
//
//    }

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


                    options.put("name", chefname);
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

//            private void gpayment(){
//
////                String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
////                int TEZ_REQUEST_CODE = 123;
//
//                Uri uri =
//                        new Uri.Builder()
//                                .scheme("upi")
//                                .authority("pay")
//                                .appendQueryParameter("pa", "test@axisbank")
//                                .appendQueryParameter("pn", "Test Merchant")
//                                .appendQueryParameter("mc", "1234")
//                                .appendQueryParameter("tr", "123456789")
//                                .appendQueryParameter("tn", "test transaction note")
//                                .appendQueryParameter("am", "10.01")
//                                .appendQueryParameter("cu", "INR")
//                                .appendQueryParameter("url", "https://test.merchant.website")
//                                .build();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setData(uri);
//                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
//                startActivityForResult(intent, TEZ_REQUEST_CODE);
//
//            }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == TEZ_REQUEST_CODE) {
//            // Process based on the data in response.
////            Log.d("result", data.getStringExtra("Status"));
//        }
//    }

            @Override
            public void onPaymentSuccess(String s) {
                Log.d("ONSUCCESS", "onPaymentSuccess: " + s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

//                Intent intent = new Intent(PayableOrders.this, CustomerPayment.class);
//                intent.putExtra("RandomUID", randomuidd);
//                startActivity(intent);
//                finish();

                DatabaseReference ldbrefxxz = FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid);
                ldbrefxxz.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            Type68 typechapm68z = dataSnapshot.getValue(Type68.class);
                            orderno = typechapm68z.getcunt();


                            DatabaseReference idbrefz = FirebaseDatabase.getInstance().getReference("ChefStatus").child(randomuidd);

                            Type67 typex67z = new Type67("Payment Done", orderno);
                            idbrefz.setValue(typex67z);


//                                                                    orderCounter= orderCounter + 1;
                            int opcnt = Integer.parseInt(orderno);
                            opcnt = opcnt + 1;
                            orderno = Integer.toString(opcnt);
                            FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid).child("cunt").setValue(orderno);

//                                                                                Type68 type68 = new Type68(orderno);
//                                                                                idxbref.setValue(type68);


                        }
                        else {
                            DatabaseReference idbrefz = FirebaseDatabase.getInstance().getReference("ChefStatus").child(randomuidd);

                            Type67 typex67z = new Type67("Payment Done", "1");
                            idbrefz.setValue(typex67z);
                            FirebaseDatabase.getInstance().getReference("ChefOrderCounter").child(cheffsid).child("cunt").setValue("2");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



                notifMessage.setText("Order Confirmed : Payment mode confirmed by user, Now you may start preparing order");
                notifNumber.setText("+91" + chefmop);
//
                if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())) {
                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                        @Override
                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

                        }

                        @Override
                        public void onFailure(@com.google.firebase.database.annotations.NotNull okhttp3.Call call, @NotNull IOException e) {

                        }

                    });
                }




                openDialog();
                DatabaseReference databaseReferencel = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("Dishes");
                databaseReferencel.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            final CustomerPaymentOrders customerPaymentOrders = dataSnapshot1.getValue(CustomerPaymentOrders.class);
                            HashMap<String, String> hashMap = new HashMap<>();
                            String dishid = customerPaymentOrders.getDishId();
                            hashMap.put("ChefId", customerPaymentOrders.getChefId());
                            hashMap.put("DishId", customerPaymentOrders.getDishId());
                            hashMap.put("DishName", customerPaymentOrders.getDishName());
                            hashMap.put("DishPrice", customerPaymentOrders.getDishPrice());
                            hashMap.put("DishQuantity", customerPaymentOrders.getDishQuantity());
                            hashMap.put("RandomUID", randomuidd);
                            hashMap.put("TotalPrice", customerPaymentOrders.getTotalPrice());
                            hashMap.put("UserId", customerPaymentOrders.getUserId());
//                            hashMap.put("rpayid", customerPaymentOrders.getrpayid());
                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("Dishes").child(dishid).setValue(hashMap);
                        }
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){

                                final CustomerPaymentOrders1 customerPaymentOrders1 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                HashMap<String, String> hashMap1 = new HashMap<>();
                                hashMap1.put("Address", customerPaymentOrders1.getAddress());
                                hashMap1.put("GrandTotalPrice", price);
                                hashMap1.put("MobileNumber", customerPaymentOrders1.getMobileNumber());
                                hashMap1.put("Name", customerPaymentOrders1.getName());
                                hashMap1.put("Note", customerPaymentOrders1.getNote() + " . Coupon Offer " + newnote);
                                hashMap1.put("RandomUID", randomuidd);
                                hashMap1.put("Status", "Your order is waiting to be prepared by Seller...");
                                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("OtherInformation").setValue(hashMap1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("Dishes");
                                        Reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                if(dataSnapshot.exists()){

                                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    final CustomerPaymentOrders customerPaymentOrderss = snapshot.getValue(CustomerPaymentOrders.class);
                                                    HashMap<String, String> hashMap2 = new HashMap<>();
                                                    String dishid = customerPaymentOrderss.getDishId();
                                                    cheffsid = customerPaymentOrderss.getChefId();
                                                    hashMap2.put("ChefId", customerPaymentOrderss.getChefId());
                                                    hashMap2.put("DishId", customerPaymentOrderss.getDishId());
                                                    hashMap2.put("DishName", customerPaymentOrderss.getDishName());
                                                    hashMap2.put("DishPrice", customerPaymentOrderss.getDishPrice());
                                                    hashMap2.put("DishQuantity", customerPaymentOrderss.getDishQuantity());
                                                    hashMap2.put("RandomUID", randomuidd);
                                                    hashMap2.put("TotalPrice", customerPaymentOrderss.getTotalPrice());
                                                    hashMap2.put("UserId", customerPaymentOrderss.getUserId());
//                                                    hashMap2.put("rpayid", customerPaymentOrderss.getrpayid());
                                                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(cheffsid).child(randomuidd).child("Dishes").child(dishid).setValue(hashMap2);
                                                }
                                                DatabaseReference dataa = FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("OtherInformation");
                                                dataa.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                        if(dataSnapshot.exists()){
                                                        CustomerPaymentOrders1 customerPaymentOrders11 = dataSnapshot.getValue(CustomerPaymentOrders1.class);
                                                        HashMap<String, String> hashMap3 = new HashMap<>();
                                                        hashMap3.put("Address", customerPaymentOrders11.getAddress());
                                                        hashMap3.put("GrandTotalPrice", price);
                                                        hashMap3.put("MobileNumber", customerPaymentOrders11.getMobileNumber());
                                                        hashMap3.put("Name", customerPaymentOrders11.getName());
                                                        hashMap3.put("Note", customerPaymentOrders11.getNote() + " . Coupon Offer " + newnote);
                                                        hashMap3.put("RandomUID", randomuidd);
                                                        hashMap3.put("Status", "Your order is waiting to be prepared by Seller...");
                                                        FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(cheffsid).child(randomuidd).child("OtherInformation").setValue(hashMap3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(cheffsid).child(randomuidd).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        FirebaseDatabase.getInstance().getReference("ChefPaymentOrders").child(cheffsid).child(randomuidd).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        FirebaseDatabase.getInstance().getReference("CustomerPaymentOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidd).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                FirebaseDatabase.getInstance().getReference().child("Tokens").child(cheffsid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        String usertoken = dataSnapshot.getValue(String.class);
//                                                                                                      sendNotifications(usertoken, "Order Confirmed", "Payment mode is confirmed by user, Now you can start the order", "Confirm");
//                                                                                                        FCMSend.pushNotification(
//                                                                                                                PayableOrders.this,
//                                                                                                                usertoken,
//                                                                                                                "Order Confirmed",
//                                                                                                                "Payment mode is confirmed by user, Now you may start preparing order"
//
//                                                                                                        );


                                                                                                    }

                                                                                                    @Override
                                                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                    }
                                                                                                });

                                                                                            }
                                                                                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                            @Override
                                                                                            public void onSuccess(Void aVoid) {
                                                                                                AlertDialog.Builder builder = new AlertDialog.Builder(PayableOrders.this);
                                                                                                builder.setMessage("Payment mode confirmed, Now you can track your order.");
                                                                                                builder.setCancelable(false);
                                                                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                                    @Override
                                                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                                                        dialog.dismiss();
                                                                                                        Intent b = new Intent(PayableOrders.this, CustomerFoodPanel_BottomNavigation.class);
                                                                                                        b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                        startActivity(b);
                                                                                                        finish();

                                                                                                    }
                                                                                                });
                                                                                                AlertDialog alert = builder.create();
                                                                                                alert.show();
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
                                });

                            }


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

            @Override
            public void onPaymentError(int i, String s) {
                Log.d("ONERROR", "onPaymentError: " + s);
                Toast.makeText(getApplicationContext(), "Error: " + s, Toast.LENGTH_LONG).show();
//        textView.setText("Error: " + s);
            }






//    private void getTransactionToken(String orderID, String amount){
//        Fuel.INSTANCE.post("https://us-central1-fooddelivery-d9c7a.cloudfunctions.net/InitiateTransactionApi" + "?amt=" + amount
//                + "&oid=" + orderID, null).responseString(new ResponseHandler<String>() {
//            @Override
//            public void success(@NonNull Request request, @NonNull Response response, String s) {
//                try {
//                    JSONObject result = new JSONObject(s);
//                    JSONObject paytmResponse = new JSONObject(result.getString("Response"));
//                    String merchantID = result.getString("MerchantID");
//                    String txnToken = paytmResponse.getJSONObject("body").getString("txnToken");
//
//                    invokePaytmSdk(merchantID, txnToken);
//
//                } catch (JSONException e){
//                    Log.e("Json error", e.getMessage());
//                }
//            }
//
//            @Override
//            public void failure(@NonNull Request request, @NonNull Response response, @NonNull FuelError fuelError) {
//                Log.e("Error retrieving", fuelError.toString());
//            }
//        });
//    }
//
//    private void invokePaytmSdk(String merchantID, String txnToken){
//        PaytmOrder paytmOrder = new PaytmOrder(orderID, merchantID, txnToken, "220", callbackurl);
//        TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback() {
////            @Override
////            public void onTransactionResponse(@Nullable Bundle bundle) {
////
////            }
//
////            @Override
//            public void onTransactionSuccess(Bundle bundle) {
//
//            }
//
////            @Override
//            public void onTransactionFailure(String s, Bundle bundle) {
//
//            }
//
//            @Override
//            public void onTransactionResponse(@Nullable Bundle bundle) {
//
//            }
//
//            @Override
//            public void networkNotAvailable() {
//
//            }
//
//            @Override
//            public void onErrorProceed(String s) {
//
//            }
//
////            @Override
////            public void onErrorProceed(String s) {
////
////            }
//
//            @Override
//            public void clientAuthenticationFailed(String s) {
//
//            }
//
//            @Override
//            public void someUIErrorOccurred(String s) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int i, String s, String s1) {
//
//            }
//
//            @Override
//            public void onBackPressedCancelTransaction() {
//
//            }
//
//            @Override
//            public void onTransactionCancel(String s, Bundle bundle) {
//
//            }
//
////            @Override
////            public void onTransactionCancel(String s, Bundle bundle) {
////
////            }
//        });
//        transactionManager.setAppInvokeEnabled(true);
//        transactionManager.setShowPaymentUrl("https://securegw-stage.paytm.in/theia/api/v1/showPaymentPage");
//        transactionManager.startTransaction(this, 123);
//
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == 123 && data != null){
//            Log.d("Result", data.toString());
//        }
//    }





    private void openDialog() {

        dialog.setContentView(R.layout.activity_loading_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        LottieAnimationView loadingutensils = dialog.findViewById(R.id.progressAnimationView);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

    }
    
//    private void generetethis() {
//        String txnAmount = textViewPrice.getText().toString().trim();
//
//        //creating a retrofit object.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Api.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        //creating the retrofit api service
//        Api apiService = retrofit.create(Api.class);
//
//        //creating paytm object
//        //containing all the values required
//        final Paytm paytm = new Paytm(
//                Constants.M_ID,
//                Constants.CHANNEL_ID,
//                txnAmount,
//                Constants.WEBSITE,
//                Constants.CALLBACK_URL,
//                Constants.INDUSTRY_TYPE_ID
//        );
//
//        //creating a call object from the apiService
//        Call<Checksum> call = apiService.getChecksum(
//                paytm.getmId(),
//                paytm.getOrderId(),
//                paytm.getCustId(),
//                paytm.getChannelId(),
//                paytm.getTxnAmount(),
//                paytm.getWebsite(),
//                paytm.getCallBackUrl(),
//                paytm.getIndustryTypeId()
//        );
//
//        //making the call to generate checksum
//        call.enqueue(new Callback<Checksum>() {
//            @Override
//            public void onResponse(Call<Checksum> call, Response<Checksum> response) {
//
//                //once we get the checksum we will initiailize the payment.
//                //the method is taking the checksum we got and the paytm object as the parameter
//                initializePaytmPayment(response.body().getChecksumHash(), paytm);
//            }
//
//            @Override
//            public void onFailure(Call<Checksum> call, Throwable t) {
//
//            }
//        });
//    }
//    private void initializePaytmPayment(String checksumHash, Paytm paytm) {
//
//        //getting paytm service
//        PaytmPGService Service = PaytmPGService.getStagingService();
//
//        //use this when using for production
//        //PaytmPGService Service = PaytmPGService.getProductionService();
//
//        //creating a hashmap and adding all the values required
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("MID", Constants.M_ID);
//        paramMap.put("ORDER_ID", paytm.getOrderId());
//        paramMap.put("CUST_ID", paytm.getCustId());
//        paramMap.put("CHANNEL_ID", paytm.getChannelId());
//        paramMap.put("TXN_AMOUNT", paytm.getTxnAmount());
//        paramMap.put("WEBSITE", paytm.getWebsite());
//        paramMap.put("CALLBACK_URL", paytm.getCallBackUrl());
//        paramMap.put("CHECKSUMHASH", checksumHash);
//        paramMap.put("INDUSTRY_TYPE_ID", paytm.getIndustryTypeId());
//
//
//        //creating a paytm order object using the hashmap
//        PaytmOrder order = new PaytmOrder(paramMap);
//
//        //intializing the paytm service
//        Service.initialize(order, null);
//
//        //finally starting the payment transaction
//        Service.startPaymentTransaction(this, true, true, this);
//
//    }
//
//    //all these overriden method is to detect the payment result accordingly
//    @Override
//    public void onTransactionResponse(Bundle bundle) {
//
//        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void networkNotAvailable() {
//        Toast.makeText(this, "Network error", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void clientAuthenticationFailed(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void someUIErrorOccurred(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onErrorLoadingWebPage(int i, String s, String s1) {
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onBackPressedCancelTransaction() {
//        Toast.makeText(this, "Back Pressed", Toast.LENGTH_LONG).show();
//    }
//
//    @Override
//    public void onTransactionCancel(String s, Bundle bundle) {
//        Toast.makeText(this, s + bundle.toString(), Toast.LENGTH_LONG).show();
//    }

//    private void sendNotifications(String usertoken, String title, String message, String confirm) {
//        Data data = new Data(title, message, confirm);
//        NotificationSender sender = new NotificationSender(data, usertoken);
//        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
//            @Override
//            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
//                if (response.code() == 200) {
//                    if (response.body().success != 1) {
//                        Toast.makeText(PayableOrders.this, "Failed", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MyResponse> call, Throwable t) {
//
//            }
//        });
//    }



}
