package com.citymart.app.CustomerFoodPanel;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.citymart.app.ChefFoodPanel.Chef_PostRazorpayIDDDD;
import com.citymart.app.ChefFoodPanel.Type4;
import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomerTechSupport extends AppCompatActivity {
    EditText temail, tname, tphone, tproblem, tdesp;
    Button subbtn;
    TextView tresp;
    DatabaseReference mRef, dbref;
    public static String trespp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_techsupport);

        temail = findViewById(R.id.temail);
        tname = findViewById(R.id.tname);
        tphone = findViewById(R.id.tphone);
        tproblem =  findViewById(R.id.tproblem);
        tdesp = findViewById(R.id.tdesp);
        subbtn = findViewById(R.id.subbtn);
        tresp = findViewById(R.id.tresp);


        DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("CustomerTechSupport").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("trespy")) {
                    Type49 type49 = dataSnapshot.getValue(Type49.class);
                    tresp.setText(type49.getTrespy());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        subbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Date dateandtime = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
//                ordertime = dateFormat.format(dateandtime) + "   " + timeFormat.format(dateandtime);






                String temaily = temail.getText().toString();
                String tnamey = tname.getText().toString();
                String tphoney = tphone.getText().toString();
                String tproblemy = tproblem.getText().toString();
                String tdespy = tdesp.getText().toString();


                if((!(temaily.isEmpty()) && !(tnamey.isEmpty()) && !(tphoney.isEmpty()) && !(tproblemy.isEmpty()) && !(tdespy.isEmpty()) )) {

                    Type49 type49 = new Type49(temaily, tnamey, tphoney,tproblemy, tdespy, "Date: " + dateFormat.format(dateandtime) +  "\nTime: " + timeFormat.format(dateandtime) +  "\nProblem: " + tproblemy +   "\nResponse: Response from Tech Help Support will be shown here!" + "\nVerdict: Pending" );
                    dbref = FirebaseDatabase.getInstance().getReference("CustomerTechSupport").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dbref.setValue(type49);
                    Toast.makeText(CustomerTechSupport.this, "Request Submitted Successfully!", Toast.LENGTH_SHORT).show();
                    temail.setText("");
                    tname.setText("");
                    tphone.setText("");
                    tproblem.setText("");
                    tdesp.setText("");
                    DatabaseReference dbr = FirebaseDatabase.getInstance().getReference("CustomerTechSupport").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dbr.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("trespy")) {
                                Type49 type49 = dataSnapshot.getValue(Type49.class);
                                tresp.setText(type49.getTrespy());
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                else {
                    Toast.makeText(CustomerTechSupport.this, "Mention all fields", Toast.LENGTH_SHORT).show();
                }






            }
        });
    }
}
