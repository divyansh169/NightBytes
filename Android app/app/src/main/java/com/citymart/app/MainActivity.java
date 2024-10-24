package com.citymart.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.material.snackbar.BaseTransientBottomBar;
//import com.google.android.material.snackbar.Snackbar;
//import com.google.android.play.core.appupdate.AppUpdateInfo;
//import com.google.android.play.core.appupdate.AppUpdateManager;
//import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
//import com.google.android.play.core.install.InstallStateUpdatedListener;
//import com.google.android.play.core.install.model.AppUpdateType;
//import com.google.android.play.core.install.model.InstallStatus;
//import com.google.android.play.core.install.model.UpdateAvailability;
//import com.google.android.play.core.tasks.OnSuccessListener;
//import com.google.android.play.core.tasks.Task;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

//    private static final int MY_REQUEST_CODE = 103;
    FirebaseAuth Fauth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView imageVieww;
    TextView textView;
    LottieAnimationView loadingutensils;

//    public static int UPDATE_CODE = 22;
//    AppUpdateManager appUpdateManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        loadingutensils.setVisibility(View.VISIBLE);

//        inAppUp();
//        FirebaseMessaging.getInstance().subscribeToTopic("Centralnews");

        imageVieww=(ImageView)findViewById(R.id.imageView);
        textView=(TextView)findViewById(R.id.textView7);
        loadingutensils = findViewById(R.id.animationView);
        imageVieww.animate().alpha(0f).setDuration(0);
        textView.animate().alpha(0f).setDuration(0);
        imageVieww.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                textView.animate().alpha(1f).setDuration(800);
            }
        });

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){



            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

//                loadingutensils.setVisibility(View.VISIBLE);



                Fauth = FirebaseAuth.getInstance();
                if (Fauth.getCurrentUser() != null) {
//                    if (Fauth.getCurrentUser().isEmailVerified()) {
                    if (Fauth.getCurrentUser().isEmailVerified()) {
                        Fauth = FirebaseAuth.getInstance();
                        databaseReference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getUid() + "/Role");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String role = dataSnapshot.getValue(String.class);
                                if (role.equals("Customer")) {
                                    Intent n = new Intent(MainActivity.this, CustomerFoodPanel_BottomNavigation.class);
                                    startActivity(n);
                                    finish();//removed finish(); undo removed finish();
                                }
                                if (role.equals("Chef")) {
                                    Intent a = new Intent(MainActivity.this, ChefFoodPanel_BottomNavigation.class);
                                    startActivity(a);
                                    finish();//removed finish(); undo removed finish();
                                }
                                if (role.equals("DeliveryPerson")) {
                                    Intent intent = new Intent(MainActivity.this, Delivery_FoodPanelBottomNavigation.class);
                                    startActivity(intent);
                                    finish();//removed finish(); undo removed finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    } else {

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Check whether you have verified your details, Otherwise please verify");
                        builder.setCancelable(false);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                dialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                                startActivity(intent);
                                finish();//removed finish() undo;

                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                        Fauth.signOut();
                    }
                } else {
                    Intent intent = new Intent(MainActivity.this, MainMenu.class);
                    startActivity(intent);
                    finish();//removed finish() undo;

                }

//                loadingutensils.setVisibility(View.GONE);

            }
        }, 2500);

//        Checkforappupdate();

//        loadingutensils.setVisibility(View.GONE);
    }


//    private void Checkforappupdate(){
//        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
//
//// Returns an intent object that you use to check for an update.
//        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
//
//// Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    // This example applies an immediate update. To apply a flexible update
//                    // instead, pass in AppUpdateType.FLEXIBLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
//                // Request the update.
//                try {
//                    appUpdateManager.startUpdateFlowForResult(
//                            appUpdateInfo,
//                            AppUpdateType.IMMEDIATE,
//                            this,
//                            MY_REQUEST_CODE);
//                } catch (IntentSender.SendIntentException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == MY_REQUEST_CODE) {
//            if (resultCode != RESULT_OK) {
//
//            }
//        }
//    }

//    private void inAppUp() {
//
//        appUpdateManager = AppUpdateManagerFactory.create(this);
//        Task<AppUpdateInfo> task = appUpdateManager.getAppUpdateInfo();
//        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
//            @Override
//            public void onSuccess(AppUpdateInfo appUpdateInfo) {
//
//                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){
//
//
//                    try {
//                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,
//                                MainActivity.this,
//                                UPDATE_CODE
//
//                        );
//                    } catch (IntentSender.SendIntentException e) {
//                        e.printStackTrace();
//                        Log.d("updateerror", "onSuccess: "+ e.toString());
//                    }
//
//                }
//            }
//        });
//
//        appUpdateManager.registerListener(listener);
//
//
//    }
//    InstallStateUpdatedListener listener = installState -> {
//
//        if(installState.installStatus() == InstallStatus.DOWNLOADED){
//
//            popUp();
//        }
//
//    };
//
//    private void popUp() {
//
//        Snackbar snackbar = Snackbar.make(
//
//                findViewById(android.R.id.content),
//                "App Update Almost Done.",
//                Snackbar.LENGTH_INDEFINITE
//
//        );
//
//        snackbar.setAction("Reload", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appUpdateManager.completeUpdate();
//
//
//            }
//        });
//
//        snackbar.setTextColor(Color.parseColor("#FF0000"));
//        snackbar.show();
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == UPDATE_CODE){
//
//            if(resultCode != RESULT_OK){
//
//            }
//
//        }
//
//    }
}
