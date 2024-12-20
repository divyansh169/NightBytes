package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.citymart.app.Chef;
import com.citymart.app.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
//import com.yalantis.ucrop.UCrop;
import java.io.File;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import com.canhub.cropper.CropImageView;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import android.net.Uri;
import android.content.Intent;
import android.app.Activity;
import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class Chef_PostBanner extends AppCompatActivity {


    ImageButton imageButton;
    Button post_dish;
    //    Spinner Dishes;
    TextInputLayout Dishes, desc, qty, pri, prodattpri;
    //    TextView rpayidtext;
    String description, quantity, price, dishes, productattribute;
    Uri imageuri;
    private Uri imageUri;
     private Uri mCropimageuri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CropImageView cropImageView;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference dataaa;
    FirebaseAuth FAuth;
    StorageReference ref;
    String ChefId;
    String RandomUId;
    String State, City, Sub;

    //    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef__post_banners);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

//        Dishes = (Spinner) findViewById(R.id.dishes);
//        Dishes = (TextInputLayout) findViewById(R.id.dishes);
//        desc = (TextInputLayout) findViewById(R.id.description);
//        prodattpri = (TextInputLayout) findViewById(R.id.prodatt);
//        rpayidtext = (TextView) findViewById(R.id.rpayidtext);
//        qty = (TextInputLayout) findViewById(R.id.quantity);
//        pri = (TextInputLayout) findViewById(R.id.price);
        post_dish = (Button) findViewById(R.id.post);
        cropImageView = findViewById(R.id.cropImageView);
        FAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner");

        try {
            String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            dataaa = firebaseDatabase.getInstance().getReference("Chef").child(userid);
            dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Chef chefc = dataSnapshot.getValue(Chef.class);
                    State = chefc.getState();
                    City = chefc.getCity();
                    Sub = chefc.getSuburban();
                    imageButton = (ImageButton) findViewById(R.id.imageupload);
//                    imageButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            onSelectImageClick(v);
//                        }
//                    });
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                        }
                    });
                    cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                        @Override
                        public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                            if (result.isSuccessful()) {
                                Bitmap croppedImage = result.getBitmap();
                                imageButton.setImageBitmap(croppedImage);
                                // Store the cropped image for later upload to Firebase
                            } else {
                                // Handle error
                            }
                        }
                    });


                    post_dish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

//                            dishes = Dishes.getEditText().getText().toString().trim();
//                            description = desc.getEditText().getText().toString().trim();
//                            productattribute = prodattpri.getEditText().getText().toString().trim();
//                            if((FirebaseAuth.getInstance().getCurrentUser().getUid()) == "RdDWkrp148Nqw4vsM6JYwFK4zlu1"){
//                                rpayidtext.setText("ab maja ayega na buiidhu");
//                            }

//                            razorpayidtextview = (String) rpayidtext.getText();
//                            quantity = qty.getEditText().getText().toString().trim();
//                            price = pri.getEditText().getText().toString().trim();

//                            if (isValid()) {
//                                uploadImage();
                            uploadImageToFirebase();
//                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

            Log.e("Errrrrr: ", e.getMessage());
        }
    }

    private boolean isValid() {
        desc.setErrorEnabled(false);
        desc.setError("");
        qty.setErrorEnabled(false);
        qty.setError("");
        pri.setErrorEnabled(false);
        pri.setError("");

        boolean isValiDescription = false, isValidPrice = false, isvalidQuantity = false, isvalid = false;
        if (TextUtils.isEmpty(description)) {
            desc.setErrorEnabled(true);
            desc.setError("Description is Required");

        } else {

            desc.setError(null);
            isValiDescription = true;
        }
        if (TextUtils.isEmpty(quantity)) {
            qty.setErrorEnabled(true);
            qty.setError("Quantity is Required");
        } else {
            isvalidQuantity = true;
        }
        if (TextUtils.isEmpty(price)) {
            pri.setErrorEnabled(true);
            pri.setError("Price is Required");
        } else {
            isValidPrice = true;
        }
        isvalid = (isValiDescription && isvalidQuantity && isValidPrice) ? true : false;

        return isvalid;
    }

    private void uploadImage() {

        if (imageuri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Chef_PostBanner.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUId = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUId);
            ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            FoodSupplyDetailsBanner info = new FoodSupplyDetailsBanner(String.valueOf(uri), RandomUId, ChefId);
                            firebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Chef_PostBanner.this, "Banner posted successfully", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(Chef_PostBanner.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }

    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Chef_PostBanner.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            RandomUId = UUID.randomUUID().toString();
            ref = FirebaseStorage.getInstance().getReference().child("images/" + RandomUId);
            ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            ref.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUri = uri.toString();
                                    FoodSupplyDetailsBanner info = new FoodSupplyDetailsBanner(downloadUri, RandomUId, ChefId);
                                    firebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(Chef_PostBanner.this, "Banner posted successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Chef_PostBanner.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            progressDialog.setCanceledOnTouchOutside(false);
                        }
                    });
        }
    }


     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
             imageUri = data.getData();
             cropImageView.setVisibility(View.VISIBLE);  // Show the CropImageView
             cropImageView.setImageUriAsync(imageUri);
         }
     }

















//     private void onSelectImageClick(View v) {
//
//         CropImage.startPickImageActivity(this);
//     }

//    @Override
//    @SuppressLint("NewApi")
//     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//
//         if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//             imageuri = CropImage.getPickImageResultUri(this, data);
//
//             if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
//                 mCropimageuri = imageuri;
//                 requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
//
//             } else {
//
//                 startCropImageActivity(imageuri);
//             }
//         }
//
//         if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//             CropImage.ActivityResult result = CropImage.getActivityResult(data);
//             if (resultCode == RESULT_OK) {
//                 ((ImageButton) findViewById(R.id.imageupload)).setImageURI(result.getUri());
//                 Toast.makeText(this, "Cropped Successfully", Toast.LENGTH_SHORT).show();
//             } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                 Toast.makeText(this, "Cropping failed" + result.getError(), Toast.LENGTH_SHORT).show();
//             }
//         }
//
//
//         super.onActivityResult(requestCode, resultCode, data);
//     }



//     @Override
//     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                            @NonNull int[] grantResults) {
//
//         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//         if (mCropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//             startCropImageActivity(mCropimageuri);
//         } else {
//             Toast.makeText(this, "cancelling,required permission not granted", Toast.LENGTH_SHORT).show();
//         }
//     }
//
//     private void startCropImageActivity(Uri imageuri) {
//
//         CropImage.activity(imageuri)
//                 .setGuidelines(CropImageView.Guidelines.ON)
//                 .setMultiTouchEnabled(true)
//                 .start(this);
//
//
//     }

}


