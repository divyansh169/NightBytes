package com.citymart.app.DeliveryFoodPanel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.citymart.app.Chef;
import com.citymart.app.ChefFoodPanel.ChefOrdertobePrepareView;
import com.citymart.app.ChefFoodPanel.Type14;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.Customer;
import com.citymart.app.Delivery_SendOtp;
import com.citymart.app.Delivery_registeration;
import com.citymart.app.R;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.citymart.app.sendotp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//public class DeliveryProfileFragment extends Fragment implements CompanyListener{
public class DeliveryProfileFragment extends Fragment{

    Button post, addrzpid, addcoup, enableautaccept, disableautaccept, paydlbtn, nopaybtn, confirmbtn, post_banners;
    public static String autoflag;
    public static String enablepodt_flag;
    public static String automaticflag;
    public static String delcity;
    public static String delsub;
    public static String deliverychargetext, pendord;
    ConstraintLayout bgimage;
    LinearLayout autolayout;
    TextView pendcount, delstate, delcomlis;
    EditText deliverychargetextauto;
    EditText notifMessage, notifNumber;
    Button send_notif;
    RecyclerView recycler_view;
    CompanyAdapter adapter;
    Spinner Suburban;
    ArrayList<String> spinnerList;
    ArrayAdapter<String> adapteru;
    DatabaseReference spinnerRef;
    public static String suburban;
    Button Update, removebtn, veflay;
    DatabaseReference cref,dref;
    public static String mib;

//    private ArrayList<String> companylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_deliveryprofile, container, false);
        getActivity().setTitle("Post Product");
        AnimationDrawable animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bghome2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic2), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic3), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic5), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic6), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bggg), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic9), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic10), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic11), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic12), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic13), 3000);
        animationDrawable.addFrame(getResources().getDrawable(R.drawable.pic14), 3000);



        animationDrawable.setOneShot(false);
        animationDrawable.setEnterFadeDuration(850);
        animationDrawable.setExitFadeDuration(1600);
        bgimage = v.findViewById(R.id.back1);
        bgimage.setBackgroundDrawable(animationDrawable);
        animationDrawable.start();

        post = (Button) v.findViewById(R.id.post_dish);
        post_banners = (Button) v.findViewById(R.id.post_banners);
        addrzpid = (Button) v.findViewById(R.id.addrzpid);
        addcoup = (Button) v.findViewById(R.id.addcoup);
        enableautaccept = (Button) v.findViewById(R.id.enableautaccept);
        disableautaccept = (Button) v.findViewById(R.id.disableautaccept);
        paydlbtn = (Button) v.findViewById(R.id.paydlbtn);
        nopaybtn = (Button) v.findViewById(R.id.nopaybtn);
        confirmbtn = (Button) v.findViewById(R.id.confirmbtn);
        autolayout = (LinearLayout) v.findViewById(R.id.autolayout);
        pendcount = (TextView) v.findViewById(R.id.pendcount);
        delstate = (TextView) v.findViewById(R.id.delstate);
        delcomlis = (TextView) v.findViewById(R.id.delcomlis);
        deliverychargetextauto = (EditText) v.findViewById(R.id.deliverychargetextauto);
        recycler_view = (RecyclerView) v.findViewById(R.id.recycler_view);

        notifMessage= v.findViewById(R.id.notifMessage);
        notifNumber= v.findViewById(R.id.notifNumber);
        send_notif= v.findViewById(R.id.send_notif);
        Suburban = v.findViewById(R.id.suburban);
        Update = (Button) v.findViewById(R.id.update);
        removebtn = (Button) v.findViewById(R.id.removebtn);
        veflay = (Button) v.findViewById(R.id.veflay);
//        companylist = new ArrayList<>();

//        setRecyclerView();

        //get delivery phone
//        DatabaseReference lml = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        lml.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                final DeliveryShipOrders1 dsp = dataSnapshot.getValue(DeliveryShipOrders1.class);
//
////                firstname.setText(customer.getFirstName());
////                lastname.setText(customer.getLastName());
////                address.setText(customer.getLocalAddress());
////                mobileno.setText(customer.getMobileno());
//                mib = dsp.getMobileNumber();
////                Email.setText(customer.getEmailID());
////                State.setSelection(getIndexByString(State, customer.getState()));
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
        setUpButtons();

        DatabaseReference dmata = FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dmata.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    if(dataSnapshot.hasChild("Suburban")) {

                        final Chef chef = dataSnapshot.getValue(Chef.class);

                        delcity = chef.getCity();
                        delstate.setText(delcity);
                        delsub = chef.getSuburban();
                        delcomlis.setText(delsub);
                        mib = chef.getMobile();

                        spinnerList = new ArrayList<>();
                        adapteru = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerList);
                        spinnerRef = FirebaseDatabase.getInstance().getReference("Spinner Data").child(delcity);
                        Suburban.setAdapter(adapteru);
                        Showdata();

                    }
                }

//                DatabaseReference dmatad = FirebaseDatabase.getInstance().getReference("Spinner Data").child(delcity);
//                dmatad.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        for (DataSnapshot snapshott : dataSnapshot.getChildren()) {
//
////                            companylist.add(snapshott.getKey());
//
//
//                        }
//
//
//
//
//                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });





            }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

        Suburban.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object value = parent.getItemAtPosition(position);
                suburban = value.toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Company Added Successfully!", Toast.LENGTH_SHORT).show();

//                FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Suburban").setValue(delsub+","+suburban);
                cref = FirebaseDatabase.getInstance().getReference("DeliveryData").child(delcity).child(suburban).child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                Type14 type14= new Type14("1");
                cref.setValue(type14);

//                cref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()) {
//                            Type14 Type14ha = dataSnapshot.getValue(Type14.class);
//
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });





            }
        });

        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Company Removed Successfully!", Toast.LENGTH_SHORT).show();



//                FirebaseDatabase.getInstance().getReference("DeliveryPerson").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Suburban").setValue(delsub.replace(suburban,""));
                FirebaseDatabase.getInstance().getReference("DeliveryData").child(delcity).child(suburban).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).removeValue();
//                Type14 type14= new Type14(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                cref.removeValue(type14);

//                cref.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if(dataSnapshot.exists()) {
//                            Type14 Type14ha = dataSnapshot.getValue(Type14.class);
//
//
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });





            }
        });

        veflay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();

                String phonenumber = "+91" + mib;
                Intent b = new Intent(getActivity(), Delivery_SendOtp.class);
                b.putExtra("phonenumber", phonenumber);
                startActivity(b);


            }
        });





//        DatabaseReference ddatabaseReference = FirebaseDatabase.getInstance().getReference("deliveryChargeAuto").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        ddatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshotp) {
//
//                Type10 type10 = dataSnapshotp.getValue(Type10.class);
////                deliverychargetext = type10.getdeliverychargetext();
////                enablepodt_flag = type10.getenablepodt_flag();
////                automaticflag = type10.getautoflag();
//
//                if(Objects.equals(type10.getautoflag(), "10")){
//
//                    enableautaccept.setVisibility(View.GONE);
//                    disableautaccept.setVisibility(View.VISIBLE);
//
//
//                }
//
//                else{
//                    enableautaccept.setVisibility(View.VISIBLE);
//                    disableautaccept.setVisibility(View.GONE);
//                }
//
//
//
//
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        //.................................>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//        DatabaseReference idatu = FirebaseDatabase.getInstance().getReference("ChefPendingOrdersCount").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pendingorderscount");
//
//        idatu.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type9 type9 = dataSnapshot.getValue(Type9.class);
//                pendord = type9.getpendingorderscount();
//                pendcount.setText("Your Pending Orders Count: " + pendord);
//
//
//            }
//2
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



//        post.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), Chef_PostDish.class));
//            }
//        });
//
//        post_banners.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), Chef_PostBanner.class));
//            }
//        });
//
//        addrzpid.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), Chef_PostRazorpayIDDDD.class));
//            }
//        });

//        addcoup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), Chef_coup.class));
//            }
//        });

//        enableautaccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                autoflag="10";
////                enableautaccept.setVisibility(View.GONE);
//                autolayout.setVisibility(View.VISIBLE);
//                disableautaccept.setVisibility(View.VISIBLE);
//
//                paydlbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        paydlbtn.setVisibility(View.GONE);
//                        nopaybtn.setVisibility(View.GONE);
//                        enablepodt_flag = "1";
//
//                    }
//                });
//                nopaybtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        nopaybtn.setVisibility(View.GONE);
//                        paydlbtn.setVisibility(View.GONE);
//                        enablepodt_flag = "10";
//
//                    }
//                });
//
//                confirmbtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        deliverychargetext = deliverychargetextauto.getText().toString();
//                        DatabaseReference datkp = FirebaseDatabase.getInstance().getReference().child("deliveryChargeAuto").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                        Type10 type10 = new Type10(deliverychargetext, enablepodt_flag, autoflag);
//                        datkp.setValue(type10);
//
//                        autolayout.setVisibility(View.GONE);
//                    }
//
//                });
//
//
//
////                DatabaseReference damtaa = FirebaseDatabase.getInstance().getReference().child("AutoAccept").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
////                Type9 type9 = new Type9(autoflag);
////                damtaa.setValue(type9);
//
//            }
//        });
//
//        disableautaccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                autoflag="01";
//                enableautaccept.setVisibility(View.VISIBLE);
//                autolayout.setVisibility(View.GONE);
//                disableautaccept.setVisibility(View.GONE);
//
////                DatabaseReference datkp = FirebaseDatabase.getInstance().getReference().child("deliveryChargeAuto").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
////                Type10 type10 = new Type10(deliverychargetext, enablepodt_flag, autoflag);
////                datkp.setValue(type10);
//
////                DatabaseReference damtaa = FirebaseDatabase.getInstance().getReference().child("AutoAccept").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
////                Type9 type9 = new Type9(autoflag);
////                damtaa.setValue(type9);
//
//            }
//        });

        return v;
    }

//    private ArrayList<String> getCompanyData(){
//        ArrayList<String> companylist = new ArrayList<>();
//        companylist.add("company1");
//        companylist.add("company2");
//        companylist.add("company3");
//        return companylist;
//    }

    private void  Showdata(){

        spinnerRef.addValueEventListener(new ValueEventListener() { //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item: snapshot.getChildren()){
                    spinnerList.add(item.getValue().toString());
                }
                adapteru.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void setRecyclerView() {
//
//        recycler_view.setHasFixedSize(true);
//        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new CompanyAdapter(getContext(), getCompanyData(), this);
//        recycler_view.setAdapter(adapter);
//
//    }

//    @Override
//    public void onCompanyChange(ArrayList<String> companylist) {
//
//        Toast.makeText(getActivity(),companylist.toString(), Toast.LENGTH_SHORT).show();
//
//    }


    private void setUpButtons() {
//        binding.signOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AuthUI.getInstance().signOut(getActivity()).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull @NotNull Task<Void> task) {
//                        startActivity(new Intent(getActivity(), FirebaseAuthUIActivity.class));
//                        finish();
//                        FirebaseMessaging.getInstance().unsubscribeFromTopic("messaging");
//                    }
//                });
//            }
//        });
        send_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
                    new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new Callback() {
//                        @Override
//                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//
//                        }

                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(response.code()==200){
                                        Toast.makeText(getActivity(), "Notification sent", Toast.LENGTH_SHORT).show();
                                        hideKeyboard(getActivity());
                                    }
                                }
                            });
                        }
                    });
                }else{
                    if (notifNumber.getText().toString().isEmpty()){
                        notifNumber.setError("Please enter the mobile number");
                    }if (notifMessage.getText().toString().isEmpty()){
                        notifMessage.setError("Please enter the message you want to send");
                    }
                }
            }
        });
//        binding.blogLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "https://medium.com/@swapnil20711/firebase-push-notifications-android-using-admin-sdk-617d2c0be130";
//                // initializing object for custom chrome tabs.
//                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
//
//                // below line is setting toolbar color
//                // for our custom chrome tab.
//                customIntent.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.black));
//
//                // we are calling below method after
//                // setting our toolbar color.
//                openCustomTab(getActivity(), customIntent.build(), Uri.parse(url));
//            }
//        });
    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
