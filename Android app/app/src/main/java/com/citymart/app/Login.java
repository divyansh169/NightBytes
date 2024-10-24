package com.citymart.app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.util.HashMap;

public class Login extends AppCompatActivity {


    TextInputLayout email, pass;
    Button Signout,SignInphone;
    TextView Forgotpassword;
    TextView txt;
    FirebaseAuth FAuth;
    String em;
    String pwd, st,cty,subu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        try {

            email = (TextInputLayout) findViewById(R.id.Lemail);
            pass = (TextInputLayout) findViewById(R.id.Lpassword);
            Signout = (Button) findViewById(R.id.button4);
            txt = (TextView) findViewById(R.id.textView3);
            Forgotpassword=(TextView)findViewById(R.id.forgotpass);
            SignInphone=(Button)findViewById(R.id.btnphone);

            FAuth = FirebaseAuth.getInstance();
//            getDynamicLinkFromFireBase();

            Signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    em = email.getEditText().getText().toString().trim();
                    pwd = pass.getEditText().getText().toString().trim();
                    if (isValid()) {

                        final ProgressDialog mDialog = new ProgressDialog(Login.this);
                        mDialog.setCanceledOnTouchOutside(false);
                        mDialog.setCancelable(false);
                        mDialog.setMessage("Logging in...");
                        mDialog.show();
                        FAuth.signInWithEmailAndPassword(em, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mDialog.dismiss();
                                    if (FAuth.getCurrentUser().isEmailVerified()) {
                                        Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                        Intent z = new Intent(Login.this, CustomerFoodPanel_BottomNavigation.class);
                                        z.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(z);
                                        finish();
                                    } else {
                                        ReusableCodeForAll.ShowAlert(Login.this,"","Please Verify your Email");
                                    }

                                } else {

                                    mDialog.dismiss();
                                    ReusableCodeForAll.ShowAlert(Login.this,"Error",task.getException().getMessage());
                                }
                            }
                        });

                    }
                }
            });

            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent Register = new Intent(Login.this, Registeration.class);
                    startActivity(Register);

                }
            });

            Forgotpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a=new Intent(Login.this,ForgotPassword.class);
                    startActivity(a);

                }
            });
            SignInphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Login.this,LoginPhone.class);
                    startActivity(intent);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void getDynamicLinkFromFireBase() {

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }
                        
                        if(deepLink!=null){

                            st = deepLink.getQueryParameter("sstate");
                            cty = deepLink.getQueryParameter("ccity");
                            subu = deepLink.getQueryParameter("ssub");
                            em = deepLink.getQueryParameter("email");
                            pwd = deepLink.getQueryParameter("password");

                            String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference datapl = FirebaseDatabase.getInstance().getReference("Customer").child(useridd);
                            datapl.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Customer customer = dataSnapshot.getValue(Customer.class);


                                    long mobilenoo = Long.parseLong(customer.getMobileno());

                                    HashMap<String, String> hashMappp = new HashMap<>();
                                    hashMappp.put("City", cty);
                                    hashMappp.put("ConfirmPassword", customer.getConfirmPassword());
                                    hashMappp.put("EmailID", customer.getEmailID());
                                    hashMappp.put("FirstName", customer.getFirstName());
                                    hashMappp.put("LastName", customer.getLastName());
                                    hashMappp.put("Mobileno", String.valueOf(mobilenoo));
                                    hashMappp.put("Password", customer.getPassword());
                                    hashMappp.put("LocalAddress", customer.getLocalAddress());
                                    hashMappp.put("State", st );
                                    hashMappp.put("Suburban", subu);
                                    FirebaseDatabase.getInstance().getReference("Customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(hashMappp);



                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                            if (isValid()) {

                                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                                mDialog.setCanceledOnTouchOutside(false);
                                mDialog.setCancelable(false);
                                mDialog.setMessage("Logging in...");
                                mDialog.show();
                                FAuth.signInWithEmailAndPassword(em, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            mDialog.dismiss();
                                            if (FAuth.getCurrentUser().isEmailVerified()) {
                                                Toast.makeText(Login.this, "You are logged in", Toast.LENGTH_SHORT).show();
                                                Intent z = new Intent(Login.this, CustomerFoodPanel_BottomNavigation.class);
                                                z.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(z);
                                                finish();
                                            } else {
                                                ReusableCodeForAll.ShowAlert(Login.this,"","Please Verify your Email");
                                            }

                                        } else {

                                            mDialog.dismiss();
                                            ReusableCodeForAll.ShowAlert(Login.this,"Error",task.getException().getMessage());
                                        }
                                    }
                                });

                            }


                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailpattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
//    String emailpattern = "[a-zA-Z0-9._-]+@lnmiit.ac.in";
    public boolean isValid() {
        email.setErrorEnabled(false);
        email.setError("");
        pass.setErrorEnabled(false);
        pass.setError("");

        boolean isvalidemail=false,isvalidpassword=false,isvalid=false;
        if (TextUtils.isEmpty(em))
        {
            email.setErrorEnabled(true);
            email.setError("Email is required");
        }
        else {
            if (em.matches(emailpattern) || em.matches(emailpattern2))
            {
                isvalidemail=true;
            }
            else {
                email.setErrorEnabled(true);
                email.setError("Enter a valid Email Address");
            }

        }
        if (TextUtils.isEmpty(pwd))
        {
            pass.setErrorEnabled(true);
            pass.setError("Password is required");
        }
        else {
            isvalidpassword=true;
            }
         isvalid = (isvalidemail && isvalidpassword) ? true : false;
        return isvalid;
        }
    }


