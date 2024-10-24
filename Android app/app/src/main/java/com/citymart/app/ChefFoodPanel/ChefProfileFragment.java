package com.citymart.app.ChefFoodPanel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.citymart.app.Chef;
import com.citymart.app.Chefsendotp;
import com.citymart.app.Customer;
import com.citymart.app.MainMenu;
import com.citymart.app.R;
import com.citymart.app.SendNotification.Data;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.citymart.app.sendotp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChefProfileFragment extends Fragment {

    Button post, addrzpid, addcop, addcoup, enableautaccept, disableautaccept, paydlbtn, nopaybtn, confirmbtn, post_banners, veflay, pastord;
    Button addord, chati, stati, billi, setdir, techsupp, disablepieond, enablepieond, enabledel, disabledel, enabletw, disabletw, enablepb, disablepb ;
//    TextView podEnabledText, podDisabledText, deliveryEnabledText, deliveryDisabledText, twEnabledText, twDisabledText, pbEnabledText, pbDisabledText;

//    Button enablepieond, disablepieond, enabledel, disabledel, enabletw, disabletw, enablepb, disablepb;
    TextView podenabledtext, poddisabledtext, deliveryenabledtext, deliverydisabledtext, twenabledtext, twdisabledtext, pbenabledtext, pbdisabledtext;

    public static String autoflag;
    public static String enablepodt_flag;
    public static String automaticflag;
    public static String deliverychargetext, pendord;
    ConstraintLayout bgimage;
    LinearLayout autolayout;
    LinearLayout wallay;
    LinearLayout laytot;
    TextView pendcount;
    TextView wal;
    TextView accpetenabledtext, accpetdisabledtext;
    EditText deliverychargetextauto;
    EditText notifMessage, notifNumber, ann_to_all_city_customers;
    Button send_notif,send1;
    Button recwal;
    ProgressBar progressBar;
    TextView walletAmountTextView;
    public static String mib;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chef_profile, container, false);
        getActivity().setTitle("Post Product");
        setHasOptionsMenu(true);
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
        addcop = (Button) v.findViewById(R.id.addcop);
        veflay = (Button) v.findViewById(R.id.veflay);
        pastord = (Button) v.findViewById(R.id.pastord);
        addcoup = (Button) v.findViewById(R.id.addcoup);
        enableautaccept = (Button) v.findViewById(R.id.enableautaccept);
        disableautaccept = (Button) v.findViewById(R.id.disableautaccept);
        paydlbtn = (Button) v.findViewById(R.id.paydlbtn);
        nopaybtn = (Button) v.findViewById(R.id.nopaybtn);
        confirmbtn = (Button) v.findViewById(R.id.confirmbtn);
        autolayout = (LinearLayout) v.findViewById(R.id.autolayout);
        pendcount = (TextView) v.findViewById(R.id.pendcount);
        wal = (TextView) v.findViewById(R.id.wal);
        recwal = (Button) v.findViewById(R.id.recwal);
        wallay = v.findViewById(R.id.wallay);
        laytot = v.findViewById(R.id.laytot);
        accpetenabledtext = (TextView) v.findViewById(R.id.accpetenabledtext);
        accpetdisabledtext = (TextView) v.findViewById(R.id.accpetdisabledtext);
        deliverychargetextauto = (EditText) v.findViewById(R.id.deliverychargetextauto);



        notifMessage= v.findViewById(R.id.notifMessage);
        notifNumber= v.findViewById(R.id.notifNumber);
        send_notif= v.findViewById(R.id.send_notif);
        ann_to_all_city_customers= v.findViewById(R.id.ann_to_all_city_customers);
        send1= v.findViewById(R.id.send1);

        //addord, chati, billi, setdir, techsupp

        addord = v.findViewById(R.id.addord);
        chati = v.findViewById(R.id.chati);
        stati = v.findViewById(R.id.stati);
        billi = v.findViewById(R.id.billi);
        setdir = v.findViewById(R.id.setdir);
        techsupp = v.findViewById(R.id.techsupp);
        disablepieond = v.findViewById(R.id.disablepieond);
        enablepieond = v.findViewById(R.id.enablepieond);
        enabledel = v.findViewById(R.id.enabledel);
        disabledel = v.findViewById(R.id.disabledel);
        enabletw = v.findViewById(R.id.enabletw);
        disabletw = v.findViewById(R.id.disabletw);
        enablepb = v.findViewById(R.id.enablepb);
        disablepb = v.findViewById(R.id.disablepb);

        podenabledtext = v.findViewById(R.id.podenabledtext);
        poddisabledtext = v.findViewById(R.id.poddisabledtext);
        deliveryenabledtext = v.findViewById(R.id.deliveryenabledtext);
        deliverydisabledtext = v.findViewById(R.id.deliverydisabledtext);
        twenabledtext = v.findViewById(R.id.twenabledtext);
        twdisabledtext = v.findViewById(R.id.twdisabledtext);
        pbenabledtext = v.findViewById(R.id.pbenabledtext);
        pbdisabledtext = v.findViewById(R.id.pbdisabledtext);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");





        progressBar = v.findViewById(R.id.progressBar);
        walletAmountTextView = v.findViewById(R.id.walletAmountTextView);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userId = user.getUid();
            FirebaseDatabase.getInstance().getReference().child("ChefWallet").child(userId).child("wal").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        int walletAmount = dataSnapshot.getValue(Integer.class);
                        updateProgressBar(walletAmount);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle database error
                }
            });
        }

//        DatabaseReference dsczax = FirebaseDatabase.getInstance().getReference().child("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        dsczax.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()) {
//                    Type31 typeeeooop = dataSnapshot.getValue(Type31.class);
//                    if(typeeeooop.getstopped() == 1){
//
//                        laytot.setVisibility(View.INVISIBLE);
//                    }
//                    else if (typeeeooop.getstopped() == 0){
//                        laytot.setVisibility(View.VISIBLE);
//                    }
//                    else{
//                        laytot.setVisibility(View.INVISIBLE);
//                    }
//
//                }
//                else{
//                    laytot.setVisibility(View.INVISIBLE);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        DatabaseReference dsc = FirebaseDatabase.getInstance().getReference().child("ChefWallet").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dsc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Type70 typeee = dataSnapshot.getValue(Type70.class);
                    wal.setText(String.valueOf(typeee.getwal()));
                    if (typeee.getwal() > 0 ){
                        FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(0);
                        laytot.setVisibility(View.VISIBLE);
                    }
                    else {
                        FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(1);
                        laytot.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    wal.setText("0");
                    FirebaseDatabase.getInstance().getReference("ChefStopped").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("stopped").setValue(1);
                    laytot.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference mdatabaseReference = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        mdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Chef cheff = dataSnapshot.getValue(Chef.class);

//                firstname.setText(customer.getFirstName());
//                lastname.setText(customer.getLastName());
//                address.setText(customer.getLocalAddress());
//                mobileno.setText(customer.getMobileno());
                mib = cheff.getMobile();
//                Email.setText(customer.getEmailID());
//                State.setSelection(getIndexByString(State, customer.getState()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        FirebaseMessaging.getInstance().subscribeToTopic("messaging");
//        setUpButtons();


        //enable accept
        DatabaseReference gty = FirebaseDatabase.getInstance().getReference().child("ChefAutoAccept").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        gty.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Type16 typechal = dataSnapshot.getValue(Type16.class);

                    if (Objects.equals(typechal.getautoaccept(), "1")) {

                        disableautaccept.setVisibility(View.VISIBLE);
                        enableautaccept.setVisibility(View.GONE);
                        accpetenabledtext.setVisibility(View.VISIBLE);
                        accpetdisabledtext.setVisibility(View.GONE);


                    } else {

                        disableautaccept.setVisibility(View.GONE);
                        enableautaccept.setVisibility(View.VISIBLE);
                        accpetenabledtext.setVisibility(View.GONE);
                        accpetdisabledtext.setVisibility(View.VISIBLE);

                    }

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

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.getpodoption();

                    String so1 = dataSnapshot.getValue(String.class);

                    if (Objects.equals(so1, "1")) {

                        disablepieond.setVisibility(View.VISIBLE);
                        enablepieond.setVisibility(View.GONE);
                        podenabledtext.setVisibility(View.VISIBLE);
                        poddisabledtext.setVisibility(View.GONE);


                    } else {

                        disablepieond.setVisibility(View.GONE);
                        enablepieond.setVisibility(View.VISIBLE);
                        podenabledtext.setVisibility(View.GONE);
                        poddisabledtext.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




//        enable delivery option
        DatabaseReference gty2 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliveryoption");
        gty2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.getdeliveryoption();
                    String so2 = dataSnapshot.getValue(String.class);

                    if (Objects.equals(so2, "1")) {

                        disabledel.setVisibility(View.VISIBLE);
                        enabledel.setVisibility(View.GONE);
                        deliveryenabledtext.setVisibility(View.VISIBLE);
                        deliverydisabledtext.setVisibility(View.GONE);


                    } else {

                        disabledel.setVisibility(View.GONE);
                        enabledel.setVisibility(View.VISIBLE);
                        deliveryenabledtext.setVisibility(View.GONE);
                        deliverydisabledtext.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        //enable takeaway option
        DatabaseReference gty3 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("takeawayoption");
        gty3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.gettakeawayoption();
                    String so3 = dataSnapshot.getValue(String.class);

                    if (Objects.equals(so3, "1")) {

                        disabletw.setVisibility(View.VISIBLE);
                        enabletw.setVisibility(View.GONE);
                        twenabledtext.setVisibility(View.VISIBLE);
                        twdisabledtext.setVisibility(View.GONE);


                    } else {

                        disabletw.setVisibility(View.GONE);
                        enabletw.setVisibility(View.VISIBLE);
                        twenabledtext.setVisibility(View.GONE);
                        twdisabledtext.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        //enable prebooking option
        DatabaseReference gty4 = FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("prebooking");
        gty4.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

//                    Type52 toiu = dataSnapshot.getValue(Type52.class);
//                    String so  = toiu.getprebookingoption();
                    String so4 = dataSnapshot.getValue(String.class);

                    if (Objects.equals(so4, "1")) {

                        disablepb.setVisibility(View.VISIBLE);
                        enablepb.setVisibility(View.GONE);
                        pbenabledtext.setVisibility(View.VISIBLE);
                        pbdisabledtext.setVisibility(View.GONE);


                    } else {

                        disablepb.setVisibility(View.GONE);
                        enablepb.setVisibility(View.VISIBLE);
                        pbenabledtext.setVisibility(View.GONE);
                        pbdisabledtext.setVisibility(View.VISIBLE);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });















//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});






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


        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = ann_to_all_city_customers.getText().toString();

                // Check if the message is not empty
                if (!message.isEmpty()) {
                    // Trigger the process to send messages to all customers
                    sendMessagesToAllCustomers(message);
                } else {
                    // Show an error or inform the user that the message is empty
                    Toast.makeText(getActivity(), "Message cannot be empty", Toast.LENGTH_SHORT).show();
                }
//                notifMessage.setText(messageText);
//                notifNumber.setText("+91" + usermobph);
//                if (!ann_to_all_city_customers.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
//                    new FCMSender().send(String.format(NotificationMessage.message,"messaging", ann_to_all_city_customers.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
//                        @Override
//                        public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
//
//                        }
//
//                        @Override
//                        public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
//
//                        }
//
//                    });
//                }
            }
        });




        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Chef_PostDish.class));
            }
        });

        post_banners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Chef_PostBanner.class));
            }
        });

        pastord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChefFullHistoryDateView.class));
            }
        });

//        chati.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), SellerChatActivity.class));
//            }
//        });

        stati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChefOrderStats.class));
            }
        });

        addrzpid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Chef_PostRazorpayIDDDD.class));
            }
        });

        addcop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateCouponActivity.class));
            }
        });

        recwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OnlineWalletRecharge.class));
            }
        });

        //addord, chati, billi, setdir, techsupp

        addord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(), "Coming Soon..", Toast.LENGTH_SHORT).show();}
        });
        chati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(), "Coming Soon..", Toast.LENGTH_SHORT).show();}
        });
        billi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(), "Coming Soon..", Toast.LENGTH_SHORT).show();}
        });
        setdir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(), "Coming Soon..", Toast.LENGTH_SHORT).show();}
        });
        techsupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Toast.makeText(getActivity(), "Coming Soon..", Toast.LENGTH_SHORT).show();}
        });





        wallay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OnlineWalletRecharge.class));
            }
        });
        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OnlineWalletRecharge.class));
            }
        });
        walletAmountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OnlineWalletRecharge.class));
            }
        });

        veflay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(this, , Toast.LENGTH_SHORT).show();

                String phonenumber = "+91" + mib;
                Intent b = new Intent(getActivity(), Chefsendotp.class);
                b.putExtra("phonenumber", phonenumber);
                startActivity(b);


            }
        });

//        pastord.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Toast.makeText(this, , Toast.LENGTH_SHORT).show();
//
////                String phonenumber = "+91" + mib;
//                Intent b = new Intent(getActivity(), Chefsendotp.class);
////                b.putExtra("phonenumber", phonenumber);
//                startActivity(b);
//
//
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
       // enable accept
        enableautaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefAutoAccept").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("autoaccept").setValue("1");
                enableautaccept.setVisibility(View.GONE);
                disableautaccept.setVisibility(View.VISIBLE);
                accpetenabledtext.setVisibility(View.VISIBLE);
                accpetdisabledtext.setVisibility(View.GONE);
            }
        });
        disableautaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefAutoAccept").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("autoaccept").setValue("0");
                enableautaccept.setVisibility(View.VISIBLE);
                disableautaccept.setVisibility(View.GONE);
                accpetenabledtext.setVisibility(View.GONE);
                accpetdisabledtext.setVisibility(View.VISIBLE);
            }
        });


        // enable pod option
        enablepieond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("podoption").setValue("1");
                disablepieond.setVisibility(View.VISIBLE);
                enablepieond.setVisibility(View.GONE);
                podenabledtext.setVisibility(View.VISIBLE);
                poddisabledtext.setVisibility(View.GONE);
            }
        });
        disablepieond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("podoption").setValue("0");
                disablepieond.setVisibility(View.GONE);
                enablepieond.setVisibility(View.VISIBLE);
                podenabledtext.setVisibility(View.GONE);
                poddisabledtext.setVisibility(View.VISIBLE);
            }
        });




        // enable delivery option
        enabledel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliveryoption").setValue("1");
                disabledel.setVisibility(View.VISIBLE);
                enabledel.setVisibility(View.GONE);
                deliveryenabledtext.setVisibility(View.VISIBLE);
                deliverydisabledtext.setVisibility(View.GONE);
            }
        });
        disabledel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliveryoption").setValue("0");
                disabledel.setVisibility(View.GONE);
                enabledel.setVisibility(View.VISIBLE);
                deliveryenabledtext.setVisibility(View.GONE);
                deliverydisabledtext.setVisibility(View.VISIBLE);
            }
        });




        // enable takeaway option
        enabletw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("takeawayoption").setValue("1");
                disabletw.setVisibility(View.VISIBLE);
                enabletw.setVisibility(View.GONE);
                twenabledtext.setVisibility(View.VISIBLE);
                twdisabledtext.setVisibility(View.GONE);
            }
        });
        disabletw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("takeawayoption").setValue("0");
                disabletw.setVisibility(View.GONE);
                enabletw.setVisibility(View.VISIBLE);
                twenabledtext.setVisibility(View.GONE);
                twdisabledtext.setVisibility(View.VISIBLE);
            }
        });



        // enable prebooking option
        enablepb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("prebooking").setValue("1");
                disablepb.setVisibility(View.VISIBLE);
                enablepb.setVisibility(View.GONE);
                pbenabledtext.setVisibility(View.VISIBLE);
                pbdisabledtext.setVisibility(View.GONE);
            }
        });
        disablepb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("prebooking").setValue("0");
                disablepb.setVisibility(View.GONE);
                enablepb.setVisibility(View.VISIBLE);
                pbenabledtext.setVisibility(View.GONE);
                pbdisabledtext.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }

    private void sendMessagesToAllCustomers(String message) {
        // Implement the logic to fetch phone numbers and send messages
        // ... (use the logic provided in the previous responses)

        // For example:
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Customer");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String phoneNumber = userSnapshot.child("Mobileno").getValue(String.class);

                        if (phoneNumber != null) {
                            String formattedPhoneNumber = "+91" + phoneNumber;
                            sendMessageToUser(formattedPhoneNumber, message);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    private void updateProgressBar(int walletAmount) {
        int maxWalletAmount = 2000;
        double percentage = (walletAmount / (double) maxWalletAmount) * 100;
        progressBar.setProgress((int) percentage);
        walletAmountTextView.setText(String.format("%.2f%% (%d)", percentage, walletAmount));
    }

    private void sendMessageToUser(String phoneNumber, String message) {
        // Use your FCMSender code to send the message
        // Replace this with your actual implementation
        new FCMSender().send(
                String.format(NotificationMessage.message, "messaging", message, phoneNumber),
                new okhttp3.Callback() {
                    @Override
                    public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {
                        // Handle the response
                    }

                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                        // Handle the failure
                    }
                });
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
    }
        @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int idd = item.getItemId();
        if (idd == R.id.LogOut) {
            Logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Logout() {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getActivity(), MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);

    }


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