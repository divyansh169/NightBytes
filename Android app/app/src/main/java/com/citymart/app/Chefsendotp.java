package com.citymart.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citymart.app.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Chefsendotp extends AppCompatActivity {

    String verificationId;
    FirebaseAuth FAuth;
    Button verify;
    TextView txt;
    String phonenumber;
    Button Resend;
    EditText entercode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chefsendotp);

        phonenumber = getIntent().getStringExtra("phonenumber").trim();
        sendverificationcode(phonenumber);
        entercode = (EditText) findViewById(R.id.phoneno);
        txt = (TextView) findViewById(R.id.text);
        Resend = (Button) findViewById(R.id.Resendotp);
        FAuth = FirebaseAuth.getInstance();
        Resend.setVisibility(View.INVISIBLE);
        txt.setVisibility(View.INVISIBLE);
        verify = (Button) findViewById(R.id.Verify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Resend.setVisibility(View.INVISIBLE);
                String code = entercode.getText().toString().trim();

                if (code.isEmpty() && code.length() < 6) {
                    entercode.setError("Enter code");
                    entercode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt.setVisibility(View.VISIBLE);
                txt.setText("Resend Code within " + millisUntilFinished / 1000 + " Seconds");
            }

            @Override
            public void onFinish() {
                Resend.setVisibility(View.VISIBLE);
                txt.setVisibility(View.INVISIBLE);

            }
        }.start();

        Resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Resend.setVisibility(View.INVISIBLE);
                Resendotp(phonenumber);

                new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        txt.setVisibility(View.VISIBLE);
                        txt.setText("Resend Code within " + millisUntilFinished / 1000 + " Seconds");
                    }

                    @Override
                    public void onFinish() {
                        Resend.setVisibility(View.VISIBLE);
                        txt.setVisibility(View.INVISIBLE);

                    }
                }.start();

            }
        });
    }

    private void Resendotp(String phonenumber) {

        sendverificationcode(phonenumber);
    }


    private void verifyCode(String code)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,code);
        signInwithCredential(credential);
    }

    private void signInwithCredential(PhoneAuthCredential credential) {

        FAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(Chefsendotp.this,ChefFoodPanel_BottomNavigation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            ReusableCodeForAll.ShowAlert(Chefsendotp.this,"Error",task.getException().getMessage());
                        }
                    }
                });
    }


//    private  void sendverificationcode(String number)
//    {
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                number,
//                60,
//                TimeUnit.SECONDS,
//                Chefsendotp.this,
//                mCallBack
//        );
//        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth).setPhoneNumber(number)
//        ContactsContract.CommonDataKinds.Phone number to verify .setTimeout(60L, TimeUnit.SECONDS)
//
//    }

//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
//            mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//
//            verificationId=s;
//
//        }

//        @Override
//        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
//
//
//            String code=phoneAuthCredential.getSmsCode();
//            if (code !=null)
//            {
//                entercode.setText(code);
//                verifyCode(code);
//
//            }
//        }
//
//        @Override
//        public void onVerificationFailed(FirebaseException e) {
//
//            Toast.makeText(Chefsendotp.this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    };


    private void sendverificationcode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                Chefsendotp.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);

                        verificationId = s;

                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


                        String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            entercode.setText(code);
                            verifyCode(code);
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                        Toast.makeText(Chefsendotp.this, "THis 2:" + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }
        );
    }





}

