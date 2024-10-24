package com.citymart.app.ChefFoodPanel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.citymart.app.SendNotification.APIService;

import androidx.annotation.NonNull;

import com.citymart.app.SendNotification.Client;
import com.citymart.app.SendNotification.Data;
import com.citymart.app.SendNotification.MyResponse;
import com.citymart.app.SendNotification.NotificationSender;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import com.citymart.app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Chef_PostRazorpayIDDDD extends AppCompatActivity  {

        EditText etrzpid, paytmid, etrzpkey;
        EditText sellernoteedittext;
        EditText couponcode,coupontype,couponmin,couponvalue;
        TextView allcoupons,allids,allann,paytmupiid, asd;
        String randomuid;
        Button RegisterRazorpayID, paytmbun;
        Button selleraddnote;
        Button couponaddbtn;
        TextView todayspecial, specialoffers, NewlyAdded, Feedback, tsthr, tsthrrr, delcharge, dicount, minval, tibnim;

        // Corresponding EditText and Button variables
        EditText rzncjdnpid, rzncnnkjdnpid, rzncnnkkokojdnpid, rznmoncnnkkokojdnpid, sthr, sthrer, delchrg, delcfdfhrg, defdvlchrg, tabnum;
        Button tsbtn, sobtn, nabtn, fbtn, ohbtn, ohbtnnn, delchi, disctt, minorder, tabnumbtn;

    private APIService apiService;

        DatabaseReference mRef, dbref, kbref, cref, dbrefxa;

//        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_chef__post_razorpayid);

            etrzpid = (EditText) findViewById(R.id.rzpid);
            etrzpkey = (EditText) findViewById(R.id.rzpkey);
            paytmid = (EditText) findViewById(R.id.rznmonnbncnnkdlmdpkokojdnpid);
            couponcode = (EditText) findViewById(R.id.couponcode);
            coupontype = (EditText) findViewById(R.id.coupontype);
            couponmin = (EditText) findViewById(R.id.couponmin);
            couponvalue = (EditText) findViewById(R.id.couponvalue);
            RegisterRazorpayID = (Button) findViewById(R.id.RegisterRazorpayID);
            paytmbun = (Button) findViewById(R.id.paytmbun);
            sellernoteedittext = (EditText) findViewById(R.id.sellernoteedittext);
            selleraddnote = (Button) findViewById(R.id.selleraddnote);
            couponaddbtn = (Button) findViewById(R.id.couponaddbtn);
            todayspecial = findViewById(R.id.todayspecial);
            specialoffers = findViewById(R.id.specialoffers);
            NewlyAdded = findViewById(R.id.NewlyAdded);
            Feedback = findViewById(R.id.Feedback);
            tsthr = findViewById(R.id.tsthr);
            tsthrrr = findViewById(R.id.tsthrrr);
            delcharge = findViewById(R.id.delcharge);
            dicount = findViewById(R.id.dicount);
            minval = findViewById(R.id.minval);
            tibnim = findViewById(R.id.tibnim);
            rzncjdnpid = findViewById(R.id.rzncjdnpid);
            rzncnnkjdnpid = findViewById(R.id.rzncnnkjdnpid);
            rzncnnkkokojdnpid = findViewById(R.id.rzncnnkkokojdnpid);
            rznmoncnnkkokojdnpid = findViewById(R.id.rznmoncnnkkokojdnpid);
            sthr = findViewById(R.id.sthr);
            sthrer = findViewById(R.id.sthrer);
            delchrg = findViewById(R.id.delchrg);
            delcfdfhrg = findViewById(R.id.delcfdfhrg);
            defdvlchrg = findViewById(R.id.defdvlchrg);
            tabnum = findViewById(R.id.tabnum);
            tsbtn = findViewById(R.id.tsbtn);
            sobtn = findViewById(R.id.sobtn);
            nabtn = findViewById(R.id.nabtn);
            fbtn = findViewById(R.id.fbtn);
            ohbtn = findViewById(R.id.ohbtn);
            ohbtnnn = findViewById(R.id.ohbtnnn);
            delchi = findViewById(R.id.delchi);
            disctt = findViewById(R.id.disctt);
            minorder = findViewById(R.id.minorder);
            tabnumbtn = findViewById(R.id.tabnumbtn);
            allcoupons = (TextView) findViewById(R.id.allcoupons);
            allids = (TextView) findViewById(R.id.allids);
            asd = (TextView) findViewById(R.id.asd);
            paytmupiid = (TextView) findViewById(R.id.paytmupiid);
            allann = (TextView) findViewById(R.id.allann);
            randomuid = UUID.randomUUID().toString();
            apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

//            mRef = FirebaseDatabase.getInstance().getReference().child("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            //...........................................................................................................................
            dbref = FirebaseDatabase.getInstance().getReference("chefrzp").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        Type4 typechap = dataSnapshot.getValue(Type4.class);
                        allids.setText(typechap.getRzpid());
                        asd.setText(typechap.getRzpkey());
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            RegisterRazorpayID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertnewstypedata();

                }
            });



            //todays special...........................................................................................................................
            DatabaseReference kjdk1 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("todayspecial");
            kjdk1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String ts = dataSnapshot.getValue(String.class);
                        todayspecial.setText(ts);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            tsbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    String tsvalue = rzncjdnpid.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("todayspecial").setValue(tsvalue);
                    kjdk1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String ts = dataSnapshot.getValue(String.class);
                                todayspecial.setText(ts);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });



            //special offer...........................................................................................................................
            DatabaseReference kjdk2 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("specialoffer");
            kjdk2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        specialoffers.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            sobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = rzncnnkjdnpid.getText().toString();
                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("specialoffer").setValue(tsvalue);

                    kjdk2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                specialoffers.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });




            //newly added...........................................................................................................................
            DatabaseReference kjdk3 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("newlyadded");
            kjdk3.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        NewlyAdded.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            nabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = rzncnnkkokojdnpid.getText().toString();
                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("newlyadded").setValue(tsvalue);

                    kjdk3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                NewlyAdded.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });






            //feedback...........................................................................................................................
            DatabaseReference kjdk4 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("feedback");
            kjdk4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        Feedback.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            fbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = rznmoncnnkkokojdnpid.getText().toString();
                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("feedback").setValue(tsvalue);

                    kjdk4.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                Feedback.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });


            //openinghours...........................................................................................................................
            DatabaseReference kjdk5 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("openinghours");
            kjdk5.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        tsthr.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ohbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = sthr.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("openinghours").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk5.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                tsthr.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });









            //ordertimimngs...........................................................................................................................
            DatabaseReference kjdk6 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ordertimings");
            kjdk6.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        tsthrrr.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            ohbtnnn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = sthrer.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("ordertimings").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk6.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                tsthrrr.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });







            //deliverycharge...........................................................................................................................
            DatabaseReference kjdk7 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliverycharge");
            kjdk7.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        delcharge.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            delchi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = delchrg.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("deliverycharge").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk7.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                delcharge.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });






            //discount...........................................................................................................................
            DatabaseReference kjdk8 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("discount");
            kjdk8.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        dicount.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            disctt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = delcfdfhrg.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("discount").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk8.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                dicount.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });





            //minordval...........................................................................................................................
            DatabaseReference kjdk9 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("minordval");
            kjdk9.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        minval.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            minorder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = defdvlchrg.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("minordval").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk9.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                minval.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });


            DatabaseReference kjdk10 = FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("tables");
            kjdk10.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        String so = dataSnapshot.getValue(String.class);
                        tibnim.setText(so);
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            tabnumbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String tsvalue = tabnum.getText().toString();
                    FirebaseDatabase.getInstance().getReference("ChefExtraDetails").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("tables").setValue(tsvalue);

                    Toast.makeText(Chef_PostRazorpayIDDDD.this, "Inserted",Toast.LENGTH_SHORT).show();
                    kjdk10.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.exists()) {
                                String so = dataSnapshot.getValue(String.class);
                                tibnim.setText(so);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            });



//.............................................................................................................................
            //...........................................................................................................................
            dbrefxa = FirebaseDatabase.getInstance().getReference("chefpaytm").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dbrefxa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        Type41 typechap41 = dataSnapshot.getValue(Type41.class);
                        paytmupiid.setText(typechap41.getpaytmid());
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            paytmbun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertnewstypedatapaytm();

                }
            });
//.............................................................................................................................





            cref = FirebaseDatabase.getInstance().getReference("chefCoupons").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            cref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        TypeC typecha = dataSnapshot.getValue(TypeC.class);
                        allcoupons.setText(typecha.getcpntxt() + " " + typecha.getcpntxt1() + " " + typecha.getcpntxt2() + " " + typecha.getcpntxt3());
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            couponaddbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertnewstypedatacpn();

                }
            });


//......................................................................................................................................................
            kbref = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            kbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Type6 typechapi = dataSnapshot.getValue(Type6.class);
                        allann.setText(typechapi.getcfnote());
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            selleraddnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertnewstypedataau();


                }
            });

            //...............................................................................................................
            DatabaseReference kbrfref = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            kbrfref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Type6 typechapi = dataSnapshot.getValue(Type6.class);
                        allann.setText(typechapi.getcfnote());
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            selleraddnote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    insertnewstypedataau();


                }
            });

        }



        private void insertnewstypedata(){
            String rzpid = etrzpid.getText().toString();
            String rzpkey = etrzpkey.getText().toString();
            Type4 type4= new Type4(rzpid, randomuid, rzpkey);
//            mRef.child("RazorpaySellerID").setValue(type4);
            dbref.setValue(type4);
            Toast.makeText(Chef_PostRazorpayIDDDD.this, "ID inserted",Toast.LENGTH_SHORT).show();
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        Type4 typechap = dataSnapshot.getValue(Type4.class);
                        allids.setText(typechap.getRzpid());
                        asd.setText(typechap.getRzpkey());
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    private void insertnewstypedatapaytm(){
        String pid = paytmid.getText().toString();
        Type41 type41= new Type41(pid, randomuid);
//            mRef.child("RazorpaySellerID").setValue(type4);
        dbrefxa.setValue(type41);
        Toast.makeText(Chef_PostRazorpayIDDDD.this, "ID inserted",Toast.LENGTH_SHORT).show();
        dbrefxa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Type41 typechap111 = dataSnapshot.getValue(Type41.class);
                    paytmupiid.setText(typechap111.getpaytmid());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void insertnewstypedatacpn(){
        String cpntxt = couponcode.getText().toString();
        String cpntxt1 = coupontype.getText().toString();
        String cpntxt2 = couponmin.getText().toString();
        String cpntxt3 = couponvalue.getText().toString();
        TypeC typeC= new TypeC(cpntxt,cpntxt1,cpntxt2,cpntxt3,randomuid);
//            mRef.child("RazorpaySellerID").setValue(type4);
        cref.setValue(typeC);
        Toast.makeText(Chef_PostRazorpayIDDDD.this, "Coupon Created successfully",Toast.LENGTH_SHORT).show();
        cref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    TypeC typecha = dataSnapshot.getValue(TypeC.class);
                    allcoupons.setText(typecha.getcpntxt() + " " + typecha.getcpntxt1() + " " + typecha.getcpntxt2() + " " + typecha.getcpntxt3());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void insertnewstypedataau(){
        String cfnote = sellernoteedittext.getText().toString();
        Type6 type6= new Type6(cfnote);
//            mRef.child("RazorpaySellerID").setValue(type4);
        kbref.setValue(type6);
        Toast.makeText(Chef_PostRazorpayIDDDD.this, "Announcement Done",Toast.LENGTH_SHORT).show();
        kbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    Type6 typechapi = dataSnapshot.getValue(Type6.class);
                    allann.setText(typechapi.getcfnote());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//        Intent b = new Intent(Chef_PostRazorpayIDDDD.this, ChefProfileFragment.class);
//        startActivity(b);
//        finish();


//        DatabaseReference datu = FirebaseDatabase.getInstance().getReference("deliveryCharge").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        datu.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Type6 type6 = dataSnapshot.getValue(Type6.class);
//
////                            messchef = type.getchefmessage();
////                annchef.setText(type6.getcfnote());
//
//            }
//
//    @Override
//    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//    }
//});
        FirebaseDatabase.getInstance().getReference().child("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String usertoken = dataSnapshot.getValue(String.class);
//                sendNotifications(usertoken, "Announcement made Successfully", "" + cfnote, "Announcement");
//                FCMSend.pushNotification(
//                        Chef_PostRazorpayIDDDD.this,
//                        usertoken,
//                        "Announcement made Successfully",
//                        cfnote
//
//                );
//                                    progressDialog.dismiss();
//                ReusableCodeForAll.ShowAlert(context, "Announcement Success", "Announcement of: " + "\n" + cfnote + "\n" + "has been made successfully");


            }
//
    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

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
//                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }


    }

