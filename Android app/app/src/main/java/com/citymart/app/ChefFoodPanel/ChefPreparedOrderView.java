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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.Chef;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChefPreparedOrderView extends AppCompatActivity {


    RecyclerView recyclerViewdish;
    TextView countno;
    Spinner selectshipperspinner;
    DatabaseReference spinnerRef;
    ArrayList<String> spinnerList;
    ArrayList<String> dellist;
    ValueEventListener listener;
    ArrayAdapter<String> adapteru;
    private List<ChefFinalOrders> chefFinalOrdersList;
    private ChefPreparedOrderViewAdapter adapter;
    DatabaseReference reference;
    String RandomUID, userid;
    String chefidd;
    TextView grandtotal, address, name, number, delarray;
    TextView nt19, NOTE19;
    LinearLayout l1;
    Button Prepared;
    Button deliveritmyself;
    Button persontakeaway;
    Button finishtakeaway;
    Button cancelord;
    Button showshipper;
    public String amdris, deliveryuserid, deliverysuburban, deliveryId, delfname, resultString, coty, soby;
    public static int count=0;
    public static int gopuikInt, currval, fixedcost, costpercentage, amountToSubtract, newWalletValue ;
    public static String chefst, usermobph;

//    DatabaseReference orderdataflaghere;
//    Button finishdeliveritmyself;
    private ProgressDialog progressDialog;
    private APIService apiService;
//    public String stringhereflagg;
//    public int hereflagg;
    Spinner Shipper;

    EditText notifMessage, notifNumber;
    Button send_notif;
//    String deliveryId = "Kwrnzmus0gcTOEOPNr4tu3YnBq22";
//    String deliveryId = UUID.randomUUID().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_prepared_order_view);
        recyclerViewdish = findViewById(R.id.Recycle_viewOrder);
        grandtotal = findViewById(R.id.Gtotal);
//        countno = findViewById(R.id.countno);
        address = findViewById(R.id.Cadress);
        name = findViewById(R.id.Cname);
        number = findViewById(R.id.Cnumber);
        delarray = findViewById(R.id.delarray);
        nt19 = findViewById(R.id.nt19);
        NOTE19 = findViewById(R.id.NOTE19);
        l1 = findViewById(R.id.linear);
//        Shipper = findViewById(R.id.shipper);
        Prepared = findViewById(R.id.prepared);

        notifMessage= findViewById(R.id.notifMessage);
        notifNumber= findViewById(R.id.notifNumber);
        send_notif= findViewById(R.id.send_notif);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");


        deliveritmyself = findViewById(R.id.deliveritmyself);
//        showshipper = findViewById(R.id.showshipper);
        persontakeaway = findViewById(R.id.persontakeaway);
        finishtakeaway = findViewById(R.id.finishtakeaway);
        cancelord = findViewById(R.id.cancelord);
//        finishdeliveritmyself = findViewById(R.id.finishdeliveritmyself);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        progressDialog = new ProgressDialog(ChefPreparedOrderView.this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        recyclerViewdish.setHasFixedSize(true);
        recyclerViewdish.setLayoutManager(new LinearLayoutManager(ChefPreparedOrderView.this));
        chefFinalOrdersList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        dellist = new ArrayList<>();
        adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
//        selectshipperspinner.setAdapter(adapteru);
//        spinnerRef = FirebaseDatabase.getInstance().getReference("kuchbhi");

//        spinnerList = new ArrayList<>();
//        adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
//        spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child("Kwrnzmus0gcTOEOPNr4tu3YnBq22");
//        selectshipperspinner.setAdapter(adapteru);
//        Showdata();


        CheforderdishesView();
    }




    private void CheforderdishesView() {

        RandomUID = getIntent().getStringExtra("RandomUID");


//        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
//        data.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
//                amdris = chefFinalOrders1.getAddress();
//
//
//        DatabaseReference databaseReferencoe = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//        databaseReferencoe.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
//
////                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(snapshott.getKey());
////                    data.addListenerForSingleValueEvent(new ValueEventListener() {
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            DeliveryShipOrders1 deliveryShipOrders1 = dataSnapshot.getValue(DeliveryShipOrders1.class);
//
//                            if ((snapshott).contains(amdris)){
//
//                            }


//                    spinnerList = new ArrayList<>();
//                    spinnerList.add(snapshott.getValue().toString());
//                    adapteru = new ArrayAdapter<String>(ChefPreparedOrderView.this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
////                            spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//                    selectshipperspinner.setAdapter(adapteru);
//
//
//                    Showdata();

//                    ChefFinalOrders chefFinalOrders = snapshott.getValue(ChefFinalOrders.class);
//                    chefFinalOrdersList.add(chefFinalOrders);



                reference = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                reference.addValueEventListener(new ValueEventListener() {  //single to value
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        chefFinalOrdersList.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            ChefFinalOrders chefFinalOrders = snapshot.getValue(ChefFinalOrders.class);
                            chefFinalOrdersList.add(chefFinalOrders);
                        }




                        if (chefFinalOrdersList.size() == 0) {
                            l1.setVisibility(View.INVISIBLE);

                        } else {
                            l1.setVisibility(View.VISIBLE);

                            finishtakeaway.setVisibility(View.INVISIBLE);


//                            DatabaseReference datuing = FirebaseDatabase.getInstance().getReference("ordertype").child();
//                            datuing.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()) {
//
//                                        Type7 type7 = dataSnapshot.getValue(Type7.class);
//                                        if(((type7.getordermethod()).contains("Dine-In"))){
//                                            .setVisibility(View.VISIBLE);
//                                        }
//                                        else{
//                                            .setVisibility(View.GONE);
//                                        }
//                                    }


//                            DatabaseReference rrreference = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
//                            rrreference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                    final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
//                                    amdris = chefFinalOrders1.getAddress();
////                                    countno.setText(amdris);  //jsdjnfjdn, kalawad road
//
//                            DatabaseReference databaseReferencoe = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//                            databaseReferencoe.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                    for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
//
////                                        countno.setText(snapshott.getKey());  //delivery user id
////                                        deliveryuserid = snapshott.getKey();
//
//                    DatabaseReference dmata = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(snapshott.getKey());
//                    dmata.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            for (DataSnapshot snapshotti : dataSnapshot.getChildren()) {
//
//                                final Chef chef = dataSnapshot.getValue(Chef.class);
//
//                                deliverysuburban = chef.getSuburban();
//                                delfname = chef.getFname();
//
//                            }
//
////                            if((snapshott.getKey()).contains(deliverysuburban)){
//                                if((amdris).contains(deliverysuburban)){
//
////                                    count = count +1;
////                                    countno.setText(snapshott.getKey());
//                                    deliveryuserid = snapshott.getKey();
//                                        deliveryId = snapshott.getKey();
////                                    spinnerList.add(delfname);
////                                    countno.setText((CharSequence) spinnerList);
////                                    String value = deliveryuserid;
////                                    String key = spinnerRef.push().getKey();
////                                    spinnerRef.child(key).setValue(value);
////                                    spinnerList.clear();
////                                    adapter.notifyDataSetChanged();
////                                    spinnerRef = FirebaseDatabase.getInstance().getReference("kuchbhi");
////                                    selectshipperspinner.setAdapter(adapteru);
////                                    Showdata();
//
//                                }
//
//
//
////                                countno.setText(chef.getSuburban()); //jogeshwari
//
////                                countno.setText(snapshotti.getKey());   //suburban
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                                    }
//
//                                    //..............
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
////...........

//                            DeliveryShipOrders1 deliveryShipOrders1 = dataSnapshot.getValue(DeliveryShipOrders1.class);

//                                        if ((snapshott.getKey()).contains(amdris)) {
//
////                                            String value = companyname.getText().toString();
////                                            String key = spinnerRef.push().getKey();
////                                            spinnerRef.child(key).setValue(value);
////                                            spinnerList.clear();
////                                            adapter.notifyDataSetChanged();
////
////                                            spinnerList.add(snapshott.getKey());
////                                            spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child("Kwrnzmus0gcTOEOPNr4tu3YnBq22");
////                                            selectshipperspinner.setAdapter(adapteru);
////                                            Showdata();
//                                            count = count +1;
//                                            countno.setText(count);
//
//                                        }

//                            spinnerList = new ArrayList<>();
//                          adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
//                          spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child("Kwrnzmus0gcTOEOPNr4tu3YnBq22");
//                              selectshipperspinner.setAdapter(adapteru);
//                          Showdata();




//                    if(hereflagg==1){
//
//                        Prepared.setVisibility(View.VISIBLE);
//                        deliveritmyself.setVisibility(View.VISIBLE);
//                        persontakeaway.setVisibility(View.INVISIBLE);
//
//                    }
//                    else if(hereflagg==2){
//                        Prepared.setVisibility(View.INVISIBLE);
//                        deliveritmyself.setVisibility(View.INVISIBLE);
//                        persontakeaway.setVisibility(View.VISIBLE);
//
//                    }
//                    else {
//                        Prepared.setVisibility(View.VISIBLE);
//                        deliveritmyself.setVisibility(View.VISIBLE);
//                        persontakeaway.setVisibility(View.VISIBLE);
//                    }


//                    selectshipperspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            Object value = parent.getItemAtPosition(position);
////                            cityy = value.toString().trim();


//                        }

//                    showshipper.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            spinnerList = new ArrayList<>();
//                            adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
////                            spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child("Kwrnzmus0gcTOEOPNr4tu3YnBq22");
//                            spinnerRef = FirebaseDatabase.getInstance().getReference("Spinner Data").child("Mumbai");
//                            selectshipperspinner.setAdapter(adapteru);
//                            Showdata();


//                            fetchdata();
//
////                    selectshipperspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
////                        @Override
////                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                            Object value = parent.getItemAtPosition(position);
//                            spinnerList = new ArrayList<>();
//                            adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
//                            spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//                            selectshipperspinner.setAdapter(adapteru);
//                            Showdata();
////                        }
////
////                        @Override
////                        public void onNothingSelected(AdapterView<?> parent) {
////
////                        }
////                    });
//
//                        }
//                    });


                            Prepared.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    DatabaseReference ifdbref = FirebaseDatabase.getInstance().getReference("ChefTrack").child(RandomUID);

                                    Type677 type677= new Type677("Order Sent to Delivery Person!");
                                    ifdbref.setValue(type677);


                                    persontakeaway.setVisibility(View.INVISIBLE);
                                    deliveritmyself.setVisibility(View.INVISIBLE);

                                    progressDialog.setMessage("Please wait...");
                                    progressDialog.show();

//                                    DatabaseReference rrreference = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                    rrreference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                            final Chef chef = dataSnapshot.getValue(Chef.class);
//                                            coty = chef.getCity();
//                                            soby = chef.getSuburban();
//
//                                            DatabaseReference kjijo = FirebaseDatabase.getInstance().getReference("DeliveryData").child(coty).child(soby);
//                                            kjijo.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                    for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
//
//
//                                                        deliveryId = snapshott.getKey();



//                                                    }
//
//
//                                                }
//                                                //
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                        }
////
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });

//                                    DatabaseReference rrreference = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
//                                    rrreference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                            final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
//                                            amdris = chefFinalOrders1.getAddress();
////                                    countno.setText(amdris);  //jsdjnfjdn, kalawad road
//
//                                            DatabaseReference databaseReferencoe = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//                                            databaseReferencoe.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                @Override
//                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                    for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
//
////                                        countno.setText(snapshott.getKey());  //delivery user id
////                                        deliveryuserid = snapshott.getKey();
//
//                                                        DatabaseReference dmata = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(snapshott.getKey());
//                                                        dmata.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                for (DataSnapshot snapshotti : dataSnapshot.getChildren()) {
//
//                                                                    final Chef chef = dataSnapshot.getValue(Chef.class);
//
//                                                                    deliverysuburban = chef.getSuburban();
//                                                                    delfname = chef.getFname();
//
//                                                                }
//
//                                                                // Find the index of "India,"
//                                                                int indexOfIndia = amdris.indexOf("India,");
//                                                                if (indexOfIndia != -1) {
//                                                                    // Find the index of the next comma (,) after "India,"
//                                                                    int indexOfNextComma = amdris.indexOf(',', indexOfIndia);
//
//                                                                    // Extract the substring between "India," and the next comma
//                                                                    resultString = amdris.substring(indexOfIndia + 6, indexOfNextComma).trim();
//                                                                }
//
////                            if((snapshott.getKey()).contains(deliverysuburban)){
//                                                                if((deliverysuburban).contains(resultString)){
////                                                                if((amdris).contains(deliverysuburban)){
//
////                                    count = count +1;
////                                    countno.setText(snapshott.getKey());
//                                                                    deliveryuserid = snapshott.getKey();
//                                                                    deliveryId = snapshott.getKey();
////                                    spinnerList.add(delfname);
////                                    countno.setText((CharSequence) spinnerList);
////                                    String value = deliveryuserid;
////                                    String key = spinnerRef.push().getKey();
////                                    spinnerRef.child(key).setValue(value);
////                                    spinnerList.clear();
////                                    adapter.notifyDataSetChanged();
////                                    spinnerRef = FirebaseDatabase.getInstance().getReference("kuchbhi");
////                                    selectshipperspinner.setAdapter(adapteru);
////                                    Showdata();
//
//                                                                }
//
//
//
////                                countno.setText(chef.getSuburban()); //jogeshwari
//
////                                countno.setText(snapshotti.getKey());   //suburban
//
//
//                                                            }
//
//                                                            @Override
//                                                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                            }
//                                                        });
//
//                                                    }
//
//                                                    //..............
//
//
//                                                }
//
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()) {

                                            final Chef chef = dataSnapshot.getValue(Chef.class);

                                            coty = chef.getCity();
                                            soby = chef.getSuburban();

//                                    final CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
//                                    final String stringnote = customerFinalOrders1.getNote();
//                                    NOTE19.setText(stringnote);
                                            final String ChefName = chef.getFname() + " " + chef.getLname();

                                            FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(RandomUID).setValue(0);
                                            DatabaseReference frf = FirebaseDatabase.getInstance().getReference().child("DeliveryOrdersAccepted").child(RandomUID);
                                            Type15 type15 = new Type15("0");
                                            frf.setValue(type15);


                                            DatabaseReference kjijo = FirebaseDatabase.getInstance().getReference("DeliveryData").child(coty).child(soby);
                                            kjijo.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {

//                                                    dellist.clear();

                                                    for (DataSnapshot snapshott : dataSnapshot.getChildren()) {

//                                                        dellist.add(snapshott.getKey());

//                                                    }
//
//                                                    StringBuilder stringBuilder = new StringBuilder();
//                                                    for (String item : dellist) {
//                                                        stringBuilder.append(item).append("\n"); // Append each item and a new line
//                                                    }
//                                                    delarray.setText(stringBuilder.toString());
//
//                                                    for(int i=0; i< dellist.size(); ++i){
//
//                                                        deliveryId = dellist.get(i);


                                                        String deliveryIdp = snapshott.getKey();


                                                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                                        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                if(dataSnapshot.exists()) {
                                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                    final ChefFinalOrders chefFinalOrders = dataSnapshot1.getValue(ChefFinalOrders.class);
                                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                                    String dishid = chefFinalOrders.getDishId();
                                                                    userid = chefFinalOrders.getUserId();
                                                                    hashMap.put("ChefId", chefFinalOrders.getChefId());
                                                                    hashMap.put("DishId", chefFinalOrders.getDishId());
                                                                    hashMap.put("DishName", chefFinalOrders.getDishName());
                                                                    hashMap.put("DishPrice", chefFinalOrders.getDishPrice());
                                                                    hashMap.put("DishQuantity", chefFinalOrders.getDishQuantity());
                                                                    hashMap.put("RandomUID", RandomUID);
                                                                    hashMap.put("TotalPrice", chefFinalOrders.getTotalPrice());
                                                                    hashMap.put("UserId", chefFinalOrders.getUserId());
//                                                hashMap.put("rpayid", chefFinalOrders.getrpayid());

//                                                        FirebaseDatabase.getInstance().getReference().child("DeliveryShipOrders").child(deliveryId).child(RandomUID).child("Dishes").child(dishid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    DatabaseReference databaseReference1ed = FirebaseDatabase.getInstance().getReference().child("DeliveryShipOrders").child(deliveryIdp).child(RandomUID).child("Dishes").child(dishid);

                                                                    databaseReference1ed.setValue(hashMap);

//                                                DatabaseReference dataal = FirebaseDatabase.getInstance().getReference().child("deliveryDetails");
//                                                Type8 type8 = new Type8(deliveryId);
//                                                dataal.setValue(type8);

//                                                FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(RandomUID).child("Dishes").child(dishid).setValue(hashMap);
                                                                }
                                                                DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                                                data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                        if(dataSnapshot.exists()) {
                                                                        final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                                                                        HashMap<String, String> hashMap1 = new HashMap<>();
                                                                        String chefid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                        hashMap1.put("Address", chefFinalOrders1.getAddress());
                                                                        hashMap1.put("ChefId", chefid);
                                                                        hashMap1.put("ChefName", ChefName);
                                                                        hashMap1.put("GrandTotalPrice", chefFinalOrders1.getGrandTotalPrice());
                                                                        hashMap1.put("MobileNumber", chefFinalOrders1.getMobileNumber());
                                                                        hashMap1.put("Note", chefFinalOrders1.getNote());
                                                                        hashMap1.put("Name", chefFinalOrders1.getName());
                                                                        hashMap1.put("RandomUID", RandomUID);
                                                                        hashMap1.put("Status", "Order is Prepared...");
                                                                        hashMap1.put("UserId", userid);

//                                                            DatabaseReference dataal = FirebaseDatabase.getInstance().getReference().child("deliveryDetails");
//                                                            Type8 type8 = new Type8(deliveryId, chefFinalOrders1.getAddress());
//                                                            dataal.setValue(type8);
//                                                    DatabaseReference dataalop = FirebaseDatabase.getInstance().getReference().child("deliveryDetails").child(deliveryId);
//                                                    Type9 type9 = new Type9(chefFinalOrders1.getAddress());
//                                                    dataalop.setValue(type9);

                                                                        FirebaseDatabase.getInstance().getReference().child("DeliveryShipOrders").child(deliveryIdp).child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        FirebaseDatabase.getInstance().getReference("DeliveryShipOrders").child(RandomUID).child("OtherInformation").setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {

                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared and shifted to delivery person...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {


//                                                                    FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Note").setValue(CustomerFinalOrders1.getNote()).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                        @Override
//                                                                        public void onSuccess(Void aVoid) {


//                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                @Override
//                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                                                            sendNotifications(usertoken, "Estimated Time", "Your Order is Prepared", "Prepared");
//                                                                                    FCMSend.pushNotification(
//                                                                                            ChefPreparedOrderView.this,
//                                                                                            usertoken,
//                                                                                            "Estimated Time",
//                                                                                            "Your Order is Prepared"
//
//                                                                                    );
                                                                                        FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                if(dataSnapshot.exists()) {
                                                                                                String usermobph = dataSnapshot.getValue(String.class);

                                                                                                notifMessage.setText("Estimated Time : Your Order is Prepared");
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
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                            }
                                                                                        });
//                                                                                }
//
//                                                                                @Override
//                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                }
//                                                                            });
//                                                                }
//                                                            });
                                                                                    }
                                                                                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                    @Override
                                                                                    public void onSuccess(Void aVoid) {
//                                                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(deliveryId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                //                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(deliveryId).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                @Override
//                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                                                            sendNotifications(usertoken, "New Order", "You have a New Order", "DeliveryOrder");
//                                                                                    FCMSend.pushNotification(
//                                                                                            ChefPreparedOrderView.this,
//                                                                                            usertoken,
//                                                                                            "New Order",
//                                                                                            "You have a New Order"
//
//                                                                                    );
                                                                                        FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(deliveryIdp).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                if(dataSnapshot.exists()) {
                                                                                                String delmob = dataSnapshot.getValue(String.class);

                                                                                                notifMessage.setText("New Order : You have a New Order");
                                                                                                notifNumber.setText("+91" + delmob);

                                                                                                if (!notifMessage.getText().toString().isEmpty() && (!notifNumber.getText().toString().isEmpty())) {
                                                                                                    new FCMSender().send(String.format(NotificationMessage.message, "messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                                                                                                        @Override
                                                                                                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {


                                                                                                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
                                                                                                            builder.setMessage("Order has been sent to shipper");
                                                                                                            builder.setCancelable(false);
                                                                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                                                                @Override
                                                                                                                public void onClick(DialogInterface dialog, int which) {

                                                                                                                    dialog.dismiss();
                                                                                                                    Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
                                                                                                                    startActivity(b);
                                                                                                                    finish();


                                                                                                                }
                                                                                                            });
                                                                                                            AlertDialog alert = builder.create();
                                                                                                            alert.show();

                                                                                                        }

                                                                                                        @Override
                                                                                                        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                                                                                                        }

                                                                                                    });
                                                                                                }
                                                                                            }
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                            }
                                                                                        });

//                                                                                    progressDialog.dismiss();
//                                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
//                                                                                    builder.setMessage("Order has been sent to shipper");
//                                                                                    builder.setCancelable(false);
//                                                                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                                                                        @Override
//                                                                                        public void onClick(DialogInterface dialog, int which) {
//
//                                                                                            dialog.dismiss();
//                                                                                            Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
//                                                                                            startActivity(b);
//                                                                                            finish();
//
//
//                                                                                        }
//                                                                                    });
//                                                                                    AlertDialog alert = builder.create();
//                                                                                    alert.show();
//                                                                                }
//
//                                                                                @Override
//                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                                }
//                                                                            });
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
                                                        //..................
                                                        continue;
                                                    }
                                                    progressDialog.dismiss();
                                                }
//
//
                                                }

                                                //
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            //................................
                                        }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                    //.................lolo

//                                                    }
//
//
//                                                }
//                                                //
//                                                @Override
//                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                }
//                                            });
//
//
//
//                                        }
//                                        //
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });

                                                        //................


                                }
                            });


                            deliveritmyself.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    finishtakeaway.setVisibility(View.VISIBLE);
                                    persontakeaway.setVisibility(View.INVISIBLE);
                                    Prepared.setVisibility(View.INVISIBLE);
                                    deliveritmyself.setVisibility(View.INVISIBLE);

                                    DatabaseReference ifdbref = FirebaseDatabase.getInstance().getReference("ChefTrack").child(RandomUID);

                                    Type677 type677= new Type677("You will Deliver it yourself!");
                                    ifdbref.setValue(type677);

//                            progressDialog.setMessage("Please wait...");
//                            progressDialog.show();


                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()) {
                                            final Chef chef = dataSnapshot.getValue(Chef.class);
                                            final String ChefName = chef.getFname() + " " + chef.getLname();
                                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {
                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                        final ChefFinalOrders chefFinalOrders = dataSnapshot1.getValue(ChefFinalOrders.class);
                                                        HashMap<String, String> hashMap = new HashMap<>();
                                                        String dishid = chefFinalOrders.getDishId();
                                                        userid = chefFinalOrders.getUserId();
                                                        hashMap.put("ChefId", chefFinalOrders.getChefId());
                                                        hashMap.put("DishId", chefFinalOrders.getDishId());
                                                        hashMap.put("DishName", chefFinalOrders.getDishName());
                                                        hashMap.put("DishPrice", chefFinalOrders.getDishPrice());
                                                        hashMap.put("DishQuantity", chefFinalOrders.getDishQuantity());
                                                        hashMap.put("RandomUID", RandomUID);
                                                        hashMap.put("TotalPrice", chefFinalOrders.getTotalPrice());
                                                        hashMap.put("UserId", chefFinalOrders.getUserId());
//                                                hashMap.put("rpayid", chefFinalOrders.getrpayid());
                                                    }
                                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()) {
                                                            final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                                            String chefid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            chefidd = chefid;
                                                            hashMap1.put("Address", chefFinalOrders1.getAddress());
                                                            hashMap1.put("ChefId", chefid);
                                                            hashMap1.put("ChefName", ChefName);
                                                            hashMap1.put("GrandTotalPrice", chefFinalOrders1.getGrandTotalPrice());
                                                            hashMap1.put("MobileNumber", chefFinalOrders1.getMobileNumber());
                                                            hashMap1.put("Name", chefFinalOrders1.getName());
                                                            hashMap1.put("Note", chefFinalOrders1.getNote());
                                                            hashMap1.put("RandomUID", RandomUID);
                                                            hashMap1.put("Status", "Order is Prepared...");
                                                            hashMap1.put("UserId", userid);
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if(dataSnapshot.exists()) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
//                                                                            sendNotifications(usertoken, "Estimated Time", "Order Ready to deliver", "Prepared");
//                                                                            FCMSend.pushNotification(
//                                                                                    ChefPreparedOrderView.this,
//                                                                                    usertoken,
//                                                                                    "Estimated Time",
//                                                                                    "Order Ready to deliver"
//
//                                                                            );
                                                                            FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    if(dataSnapshot.exists()) {
                                                                                    String usermobph = dataSnapshot.getValue(String.class);

                                                                                    notifMessage.setText("Estimated Time : Order Ready to be deliver");
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


//                                                        }
//                                                    });
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

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                            });


                            persontakeaway.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    finishtakeaway.setVisibility(View.VISIBLE);
                                    persontakeaway.setVisibility(View.INVISIBLE);
                                    Prepared.setVisibility(View.INVISIBLE);
                                    deliveritmyself.setVisibility(View.INVISIBLE);

                                    DatabaseReference ifdbref = FirebaseDatabase.getInstance().getReference("ChefTrack").child(RandomUID);

                                    Type677 type677= new Type677("Customer Notified for Takeaway/Dine-In order!");
                                    ifdbref.setValue(type677);


//                            progressDialog.setMessage("Please wait...");
//                            progressDialog.show();


                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if(dataSnapshot.exists()) {
                                            final Chef chef = dataSnapshot.getValue(Chef.class);
                                            final String ChefName = chef.getFname() + " " + chef.getLname();
                                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {
                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                        final ChefFinalOrders chefFinalOrders = dataSnapshot1.getValue(ChefFinalOrders.class);
                                                        HashMap<String, String> hashMap = new HashMap<>();
                                                        String dishid = chefFinalOrders.getDishId();
                                                        userid = chefFinalOrders.getUserId();
                                                        hashMap.put("ChefId", chefFinalOrders.getChefId());
                                                        hashMap.put("DishId", chefFinalOrders.getDishId());
                                                        hashMap.put("DishName", chefFinalOrders.getDishName());
                                                        hashMap.put("DishPrice", chefFinalOrders.getDishPrice());
                                                        hashMap.put("DishQuantity", chefFinalOrders.getDishQuantity());
                                                        hashMap.put("RandomUID", RandomUID);
                                                        hashMap.put("TotalPrice", chefFinalOrders.getTotalPrice());
                                                        hashMap.put("UserId", chefFinalOrders.getUserId());
//                                                hashMap.put("rpayid", chefFinalOrders.getrpayid());
                                                    }
                                                    DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                                    data.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()) {
                                                            final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                                            String chefid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                            chefidd = chefid;
                                                            hashMap1.put("Address", chefFinalOrders1.getAddress());
                                                            hashMap1.put("ChefId", chefid);
                                                            hashMap1.put("ChefName", ChefName);
                                                            hashMap1.put("GrandTotalPrice", chefFinalOrders1.getGrandTotalPrice());
                                                            hashMap1.put("MobileNumber", chefFinalOrders1.getMobileNumber());
                                                            hashMap1.put("Name", chefFinalOrders1.getName());
                                                            hashMap1.put("Note", chefFinalOrders1.getNote());
                                                            hashMap1.put("RandomUID", RandomUID);
                                                            hashMap1.put("Status", "Order is Prepared...");
                                                            hashMap1.put("UserId", userid);
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                            if(dataSnapshot.exists()) {
                                                                            String usertoken = dataSnapshot.getValue(String.class);
//                                                                            sendNotifications(usertoken, "Estimated Time", "Order Ready for Takeaway", "Prepared");
//                                                                            FCMSend.pushNotification(
//                                                                                    ChefPreparedOrderView.this,
//                                                                                    usertoken,
//                                                                                    "Estimated Time",
//                                                                                    "Order Ready for Takeaway"
//
//                                                                            );
                                                                            FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                    if(dataSnapshot.exists()) {
                                                                                    String usermobph = dataSnapshot.getValue(String.class);

                                                                                    notifMessage.setText("Estimated Time : Order Ready for Takeaway/Dine-In");
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


//                                                        }
//                                                    });
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

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                }
                            });


                            finishtakeaway.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    progressDialog.setMessage("Please wait...");
                                    progressDialog.show();

                                    finishtakeaway.setVisibility(View.INVISIBLE);

                                    //aa
                                    Date dateandtime = Calendar.getInstance().getTime();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                                    String dati = dateFormat.format(dateandtime);

//                                    DatabaseReference dattuzo = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
////
//                                    dattuzo.addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            Chef chief = dataSnapshot.getValue(Chef.class);


//                                    DatabaseReference datax1 = FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(userid);
//                                    datax1.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                            @Override
//                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                if(dataSnapshot.exists()) {
//                                                                                            Type13 type13 = dataSnapshot.getValue(Type13.class);
//                                                                                            final String dattu = (type13.getordertime()).substring(0, 10);
//                                                                                            final String timttu = (type13.getordertime()).substring(13, 21);
                                    DatabaseReference datawq = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    datawq.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                if(dataSnapshot.exists()) {
                                                                                    final Chef chef = dataSnapshot.getValue(Chef.class);
                                                                                    final String ChefNamey = chef.getFname() + " " + chef.getLname();
                                                                                    final String compname = chef.getSuburban();
                                                                                    final String State = chef.getState();
                                                                                    final String City = chef.getCity();
                                                                                    final String Sub = chef.getSuburban();
                                                                                    DatabaseReference databaseReference1xzx = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                                                                    databaseReference1xzx.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                        @Override
                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                            if (dataSnapshot.exists()) {
                                                                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                    final ChefFinalOrders chefFinalOrders = dataSnapshot1.getValue(ChefFinalOrders.class);
                                                                                                    HashMap<String, String> hashMap = new HashMap<>();
                                                                                                    HashMap<String, String> hashMap3 = new HashMap<>();
                                                                                                    String dishid = chefFinalOrders.getDishId();
                                                                                                    int dq = Integer.parseInt(chefFinalOrders.getDishQuantity()) ;
                                                                                                    userid = chefFinalOrders.getUserId();
                                                                                                    hashMap.put("ChefId", chefFinalOrders.getChefId());
                                                                                                    hashMap.put("DishId", chefFinalOrders.getDishId());
                                                                                                    hashMap.put("DishName", chefFinalOrders.getDishName());
                                                                                                    hashMap.put("DishPrice", chefFinalOrders.getDishPrice());
                                                                                                    hashMap.put("DishQuantity", chefFinalOrders.getDishQuantity());
                                                                                                    hashMap.put("Finishdate", dati);
                                                                                                    hashMap.put("RandomUID", RandomUID);
                                                                                                    hashMap.put("TotalPrice", chefFinalOrders.getTotalPrice());
                                                                                                    hashMap.put("UserId", chefFinalOrders.getUserId());

//                                                                                                    hashMap3.put("ChefFinishDate", dati);
//                                                                                                    hashMap3.put("CustomerOrderdate", dattu );
//                                                                                                    hashMap3.put("CustomerOrdertime", timttu );


                                                                                                    DatabaseReference gty1 = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dishid).child("Stockcnt");
                                                                                                    gty1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                            if(dataSnapshot.exists()) {

                                                                                                                String stcnt = dataSnapshot.getValue(String.class);

                                                                                                                if (!(Objects.equals(stcnt, "in stock"))) {

                                                                                                                    int stockcount  = Integer.parseInt(stcnt);
                                                                                                                    int finval = stockcount - dq;
                                                                                                                    String finalvalstring = Integer.toString(finval);

                                                                                                                    if (finval <= 0){
                                                                                                                        FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
                                                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dishid).child("Stockcnt")
                                                                                                                                .setValue("0");
                                                                                                                    }
                                                                                                                    else{
                                                                                                                        FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
                                                                                                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dishid).child("Stockcnt")
                                                                                                                                .setValue(finalvalstring);
                                                                                                                    }

                                                                                                                }

                                                                                                            }

                                                                                                        }

                                                                                                        @Override
                                                                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                        }
                                                                                                    });






                                                                                                    FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dati).child(RandomUID).child("Dishes").child(dishid).setValue(hashMap);
//                                                                                                    FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dati).child(RandomUID).child("OtherInformation2").setValue(hashMap3);
                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerHistory").child(userid).child(dati).child(RandomUID).child("Dishes").child(dishid).setValue(hashMap);
//                                                                                                    FirebaseDatabase.getInstance().getReference("CustomerHistory").child(userid).child(dati).child(RandomUID).child("OtherInformation2").setValue(hashMap3);
//                                                hashMap.put("rpayid", chefFinalOrders.getrpayid());
                                                                                                }
                                                                                                DatabaseReference datazz = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
                                                                                                datazz.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                    @Override
                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                                        if (dataSnapshot.exists()) {
                                                                                                            final ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                                                                                                            HashMap<String, String> hashMap1 = new HashMap<>();
                                                                                                            String chefid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                                                            chefidd = chefid;
                                                                                                            gopuikInt = Integer.parseInt(chefFinalOrders1.getGrandTotalPrice());
                                                                                                            hashMap1.put("Address", chefFinalOrders1.getAddress());
                                                                                                            hashMap1.put("ChefId", chefid);
                                                                                                            hashMap1.put("ChefName", ChefNamey);
                                                                                                            hashMap1.put("CompanyName", compname);
                                                                                                            hashMap1.put("Finishdate", dati);
                                                                                                            hashMap1.put("GrandTotalPrice", chefFinalOrders1.getGrandTotalPrice());
                                                                                                            hashMap1.put("MobileNumber", chefFinalOrders1.getMobileNumber());
                                                                                                            hashMap1.put("Name", chefFinalOrders1.getName());
                                                                                                            hashMap1.put("Note", chefFinalOrders1.getNote());
                                                                                                            hashMap1.put("RandomUID", RandomUID);
                                                                                                            hashMap1.put("Status", "Your order is Prepared.");
                                                                                                            hashMap1.put("UserId", userid);


                                                                                                            FirebaseDatabase.getInstance().getReference("ChefHistory").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dati).child(RandomUID).child("OtherInformation").setValue(hashMap1);
                                                                                                            FirebaseDatabase.getInstance().getReference("CustomerHistory").child(userid).child(dati).child(RandomUID).child("OtherInformation").setValue(hashMap1);

//                                                                                                            DatabaseReference dew = FirebaseDatabase.getInstance().getReference("ChefWallet").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                                                                            dew.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                @Override
//                                                                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                    if (dataSnapshot.exists()) {
//
//                                                                                                                        Type70 tity = dataSnapshot.getValue(Type70.class);
//                                                                                                                        int currval = tity.getwal();
//
//
//                                                                                                                        DatabaseReference dewza = FirebaseDatabase.getInstance().getReference("ChefOrderCost").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                                                                                                                        dewza.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                                                                            @Override
//                                                                                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                                                                                                                if (dataSnapshot.exists()) {
//
//                                                                                                                                    Type71 titya = dataSnapshot.getValue(Type71.class);
//                                                                                                                                    int costpercentage = titya.getcostpercent();
//
//                                                                                                                                // Calculate the amount to subtract based on the cost percentage
//                                                                                                                                    int amountToSubtract = (gopuikInt * costpercentage) / 100;
//
//                                                                                                                                    // Calculate the new wallet value
//                                                                                                                                    int newWalletValue = currval - amountToSubtract;
//
//                                                                                                                                    // Update the wal value in the database
//                                                                                                                                    dew.child("wal").setValue(newWalletValue);
//                                                                                                                                    if (newWalletValue > 0 ){
//                                                                                                                                        FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(0);
////                                                                                                                                        laytot.setVisibility(View.VISIBLE);
//                                                                                                                                    }
//                                                                                                                                    else {
//                                                                                                                                        FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(1);
////                                                                                                                                        laytot.setVisibility(View.INVISIBLE);
//                                                                                                                                    }

                                                                                                                                    DatabaseReference dew = FirebaseDatabase.getInstance().getReference("ChefWallet").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                                                                    dew.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                        @Override
                                                                                                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                                                                                                                                            if (dataSnapshot1.exists()) {
                                                                                                                                                Type70 tity = dataSnapshot1.getValue(Type70.class);
                                                                                                                                                currval = tity.getwal(); // Assuming getwal() returns double

                                                                                                                                                DatabaseReference dewzafm = FirebaseDatabase.getInstance().getReference("ChefFixedCost").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                                                                                dewzafm.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                                                                                                                        if (dataSnapshot2.exists()) {
                                                                                                                                                            Type72 fm = dataSnapshot2.getValue(Type72.class);
                                                                                                                                                            fixedcost = fm.getfixedcost();

                                                                                                                                                DatabaseReference dewza = FirebaseDatabase.getInstance().getReference("ChefOrderCost").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                                                                                dewza.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                                                                    @Override
                                                                                                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                                                                                                                                                        if (dataSnapshot3.exists()) {
                                                                                                                                                            Type71 titya = dataSnapshot3.getValue(Type71.class);
                                                                                                                                                            costpercentage = titya.getcostpercent(); // Divide by 100.0 to get the percentage in decimal format

                                                                                                                                                            // Calculate the amount to subtract based on the cost percentage
//                                                                                                                                                            int amountToSubtract = gopuikInt * costpercentage;
                                                                                                                                                            amountToSubtract = (gopuikInt * costpercentage) / 100;


                                                                                                                                                            // Calculate the new wallet value
                                                                                                                                                            newWalletValue = currval - amountToSubtract - fixedcost;

                                                                                                                                                            // Format the values to two decimal places
//                                                                                                                                                            String formattedCostPercentage = String.format("%.2f", costpercentage);
//                                                                                                                                                            String formattedNewWalletValue = String.format("%.2f", newWalletValue);

//                                                                                                                                                            int roundedWalletValue = (int) Math.round(newWalletValue);

                                                                                                                                                            // Update the wal value in the database
                                                                                                                                                            dew.child("wal").setValue(newWalletValue);

                                                                                                                                                            if (newWalletValue > 0) {
                                                                                                                                                                FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(0);
//                          laytot.setVisibility(View.VISIBLE);
                                                                                                                                                            } else {
                                                                                                                                                                FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(1);
//                          laytot.setVisibility(View.INVISIBLE);
                                                                                                                                                            }


//

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
                                                                                                                }

                                                                                                                @Override
                                                                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                                                }
                                                                                                            });






                                                                                                        }

                                                                                                        //aa
//                                                                                                                }
//                                                                                                            });
//                                                                                                        }
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
                                                                            }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
//                                                                                }
//                                                                            }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
                                    //aa






                                    //aa



                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Order is Prepared...").addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {
                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                            sendNotifications(usertoken, "Home Chef", "Thank you for Ordering", "ThankYou");
//                                                    FCMSend.pushNotification(
//                                                            ChefPreparedOrderView.this,
//                                                            usertoken,
//                                                            "Enjoy!!",
//                                                            "Thank you for Ordering"
//
//                                                    );
                                                    FirebaseDatabase.getInstance().getReference("Customer").child(userid).child("Mobileno").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()) {
                                                            String usermobph = dataSnapshot.getValue(String.class);

                                                            notifMessage.setText("Enjoy!! : Thank you for Ordering");
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
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefidd).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if(dataSnapshot.exists()) {
                                                    String usertoken = dataSnapshot.getValue(String.class);
//                                            sendNotifications(usertoken, "Order Placed", "Message of Order Ready reached to the Customer", "Delivered");
//                                                    FCMSend.pushNotification(
//                                                            ChefPreparedOrderView.this,
//                                                            usertoken,
//                                                            "Order Placed",
//                                                            "Message of Order Ready reached to the Customer"
//
//                                                    );
                                                    FirebaseDatabase.getInstance().getReference("Chef").child(chefidd).child("Mobile").addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if(dataSnapshot.exists()) {
                                                            String chefmobph = dataSnapshot.getValue(String.class);

                                                            notifMessage.setText("Order Placed : Message of Order Ready reached to Customer");
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

//                                                            DatabaseReference dathu = FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(userid);
//                                                            dathu.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                                @Override
//                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                                                    Type13 type13 = dataSnapshot.getValue(Type13.class);
//                                                                    final String ordtime = type13.getordertime();
//
//
//
////                                                            DatabaseReference ncref = FirebaseDatabase.getInstance().getReference("ChefTotal").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ordtime);
////                                                            ncref.addListenerForSingleValueEvent(new ValueEventListener() {
////                                                                @Override
////                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                                                    if(dataSnapshot.exists()) {
////                                                                        Type67 typechapi7 = dataSnapshot.getValue(Type67.class);
//////                                                                        String tut = typechapi7.getcheftot();
////                                                                        ncref.setValue(typechapi7+);
////                                                                    }
////                                                                }
////                                                                @Override
////                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                                                                }
////                                                            });
//                                                                }
//                                                                @Override
//                                                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                                                }
//                                                            });

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
                                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");

//                                                    FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                @Override
//                                                                public void onSuccess(Void aVoid) {
//                                                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");
//                                                                }
//                                                            });
//                                                        }
//                                                    });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefidd).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefidd).child(RandomUID).child("OtherInformation").removeValue();
                                                }
                                            });
                                        }
                                        //all unecessary used remove things
                                    }).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            FirebaseDatabase.getInstance().getReference("ordertype").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    FirebaseDatabase.getInstance().getReference("deliveryCharge").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            FirebaseDatabase.getInstance().getReference("ChefTrack").child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    FirebaseDatabase.getInstance().getReference("ChefStatus").child(RandomUID).removeValue();
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
                                    builder.setMessage("Message of Order Ready reached to the Customer");
                                    builder.setCancelable(false);
                                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
                                            startActivity(b);
                                            finish();


                                        }
                                    });

                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder119 = new AlertDialog.Builder(ChefPreparedOrderView.this);
                                    builder119.setMessage("Finished Successfully!");
                                    builder119.setCancelable(false);
                                    builder119.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();
                                            Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
                                            startActivity(b);
                                            finish();


                                        }
                                    });
                                    AlertDialog alert = builder119.create();
                                    alert.show();






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


                                            cancelord.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    //

                                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
                                                    builder.setTitle("Cancel Order");
                                                    builder.setMessage("Are you sure you want to cancel this order?");

                                                    // If the user confirms the deletion
                                                    builder.setPositiveButton("Yes", (dialog, which) -> {
                                                        // Proceed with deleting the group
//                                                    });
//
//                                                    // If the user cancels the deletion
//                                                    builder.setNegativeButton("No", (dialog, which) -> {
//                                                        dialog.dismiss();  // Close the dialog
//                                                    });
//
//                                                    builder.show();

                                                    //



                                                    if(chefst.contains("Pay on Delivery")){

                                                        progressDialog.setMessage("Please wait...");
                                                        progressDialog.show();

                                                        DatabaseReference databaseReference1z = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes");
                                                        databaseReference1z.addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                    final ChefFinalOrders chefFinalOrders = dataSnapshot1.getValue(ChefFinalOrders.class);
//                                                            HashMap<String, String> hashMap = new HashMap<>();
//                                                            String dishid = chefWaitingOrders.getDishId();
                                                                    userid = chefFinalOrders.getUserId();
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
                                                                                                FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){
                                                                                                    @Override
                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                        FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){

                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                FirebaseDatabase.getInstance().getReference("ChefStatus").child(RandomUID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>(){

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
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
                                                                builder.setMessage("Order Cancelled Successfully! Message of order cancelled reached to the customer");
                                                                builder.setCancelable(false);
                                                                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {

                                                                        dialog.dismiss();
                                                                        Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
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





                                                    }
                                                    else{
                                                        Toast.makeText(ChefPreparedOrderView.this, "Payment has already been done", Toast.LENGTH_SHORT).show();
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


//                    finishdeliveritmyself.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            finishdeliveritmyself.setVisibility(View.INVISIBLE);
//
//                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").child("Status").setValue("Your Order is Prepared. You Can Now Go to Take Your Order").addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(userid).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            String usertoken = dataSnapshot.getValue(String.class);
//                                            sendNotifications(usertoken, "Home Chef", "Thank you for Ordering", "ThankYou");
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
//                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    FirebaseDatabase.getInstance().getReference().child("Tokens").child(chefidd).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                            String usertoken = dataSnapshot.getValue(String.class);
//                                            sendNotifications(usertoken, "Order Placed", "Message of Order Ready for deliver reached to the Customer", "Delivered");
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
//                            }).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void aVoid) {
//                                    FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(userid).child(RandomUID).child("OtherInformation").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//                                                            FirebaseDatabase.getInstance().getReference("DeliveryShipFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                                @Override
//                                                                public void onSuccess(Void aVoid) {
//                                                                    FirebaseDatabase.getInstance().getReference("AlreadyOrdered").child(userid).child("isOrdered").setValue("false");
//                                                                }
//                                                            });
//                                                        }
//                                                    });
//                                                }
//                                            });
//                                        }
//                                    });
//                                }
//                            }).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefidd).child(RandomUID).child("Dishes").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(chefidd).child(RandomUID).child("OtherInformation").removeValue();
//                                        }
//                                    });
//                                }
//                            });
//                            AlertDialog.Builder builder = new AlertDialog.Builder(ChefPreparedOrderView.this);
//                            builder.setMessage("Message of Order Ready for Deliver reached to the Customer");
//                            builder.setCancelable(false);
//                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                    dialog.dismiss();
//                                    Intent b = new Intent(ChefPreparedOrderView.this, ChefPreparedOrder.class);
//                                    startActivity(b);
//                                    finish();
//
//
//                                }
//                            });
//
//                        }
//                    });



//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
                        }
                        adapter = new ChefPreparedOrderViewAdapter(ChefPreparedOrderView.this, chefFinalOrdersList);
                        recyclerViewdish.setAdapter(adapter);




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //...........
//            }
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//        }
//    });
        //..........

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    ChefFinalOrders1 chefFinalOrders1 = dataSnapshot.getValue(ChefFinalOrders1.class);
                    grandtotal.setText("₹ " + chefFinalOrders1.getGrandTotalPrice());
//                    address.setText((chefFinalOrders1.getAddress()).substring(22));
//                    address.setText((chefFinalOrders1.getAddress()).substring((chefFinalOrders1.getAddress()).indexOf(':') + 1).trim());

                    String fullAddress = chefFinalOrders1.getAddress();
                    int colonIndex = fullAddress.indexOf(':');
                    int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
                    int lastCommaIndex = fullAddress.lastIndexOf(',');
                    String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
                    String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

                    address.setText(firstPart);
//        holder.ordtp.setText("Order Type: " + lastPart);

                    name.setText(chefFinalOrders1.getName());
                    NOTE19.setText(chefFinalOrders1.getNote());
                    number.setText("+91" + chefFinalOrders1.getMobileNumber());

//                CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
//                NOTE19.setText(customerFinalOrders1.getNote());
                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        DatabaseReference databaseReferenceee = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUID).child("OtherInformation");
//        databaseReferenceee.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ChefWaitingOrders1 chefWaitingOrders1 = dataSnapshot.getValue(ChefWaitingOrders1.class);
//                NOTE19.setText(chefWaitingOrders1.getNote());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


    }

    public void  fetchdata(){


        listener=spinnerRef.addValueEventListener(new ValueEventListener() { //fixed
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    for (DataSnapshot mydata : snapshot.getChildren()) {
                        spinnerList.add(mydata.getValue().toString());
                    }
                    adapteru.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        spinnerList = new ArrayList<>();
//        adapteru = new ArrayAdapter<String> (ChefPreparedOrderView.this , android.R.layout.simple_spinner_dropdown_item, spinnerList);
//        spinnerRef = FirebaseDatabase.getInstance().getReference("DeliveryPerson");
//        selectshipperspinner.setAdapter(adapteru);
//        Showdata();

    }

    private void  Showdata(){

        spinnerRef.addValueEventListener(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                for (DataSnapshot item : snapshot.getChildren()) {
                    spinnerList.add(item.getValue().toString());
                }
                adapteru.notifyDataSetChanged();
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotifications(String usertoken, String title, String message, String prepared) {

        Data data = new Data(title, message, prepared);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(ChefPreparedOrderView.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });
    }
}
