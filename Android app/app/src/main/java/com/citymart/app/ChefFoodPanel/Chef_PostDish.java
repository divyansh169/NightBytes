package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.citymart.app.ChefFoodPanel_BottomNavigation;
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
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageView;
import com.canhub.cropper.CropImage;
import com.canhub.cropper.CropImageActivity;
import android.net.Uri;
import android.content.Intent;
import android.app.Activity;
import java.io.ByteArrayOutputStream;
import java.util.UUID;

//import com.yalantis.ucrop.UCrop;
//import com.yalantis.ucrop.view.CropImageView;

import java.io.File;

import java.util.UUID;

public class Chef_PostDish extends AppCompatActivity {


    
    
//    Spinner Dishes;
    TextInputLayout Dishes, desc, qty, pri, prodattpri, stockcntpri;
//    TextView rpayidtext;
    String description, quantity, price, dishes, productattribute, stockcounter;
    Uri imageuri;
     private Uri mCropimageuri;
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
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private CropImageView cropImageView;
    ImageButton imageButton;
    Button post_dish;
    private Bitmap croppedImage;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef__post_dish);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
//        Dishes = (Spinner) findViewById(R.id.dishes);
        Dishes = (TextInputLayout) findViewById(R.id.dishes);
        desc = (TextInputLayout) findViewById(R.id.description);
        prodattpri = (TextInputLayout) findViewById(R.id.prodatt);
        stockcntpri = (TextInputLayout) findViewById(R.id.stockcnt);
//        rpayidtext = (TextView) findViewById(R.id.rpayidtext);
        qty = (TextInputLayout) findViewById(R.id.quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        cropImageView = findViewById(R.id.cropImageView);   
        post_dish = (Button) findViewById(R.id.post);
        FAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getInstance().getReference("FoodSupplyDetails");

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


                    // imageButton.setOnClickListener(new View.OnClickListener() {
                    //     @Override
                    //     public void onClick(View v) {
                    //         // Check for camera permission
                    //         if (ContextCompat.checkSelfPermission(Chef_PostDish.this, Manifest.permission.CAMERA)
                    //                 != PackageManager.PERMISSION_GRANTED) {
                    //             // Permission is not granted, request it
                    //             ActivityCompat.requestPermissions(Chef_PostDish.this,
                    //                     new String[]{Manifest.permission.CAMERA},
                    //                     CAMERA_PERMISSION_REQUEST_CODE);
                    //         } else {
                    //             // Permission has already been granted, proceed with image selection logic
                    //             onSelectImageClick(v);
                    //         }
                    //     }
                    // });

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

                            dishes = Dishes.getEditText().getText().toString().trim();
                            description = desc.getEditText().getText().toString().trim();
                            productattribute = prodattpri.getEditText().getText().toString().trim();
                            stockcounter = stockcntpri.getEditText().getText().toString().trim();
//                            if((FirebaseAuth.getInstance().getCurrentUser().getUid()) == "RdDWkrp148Nqw4vsM6JYwFK4zlu1"){
//                                rpayidtext.setText("ab maja ayega na buiidhu");
//                            }

//                            razorpayidtextview = (String) rpayidtext.getText();
                            quantity = qty.getEditText().getText().toString().trim();
                            price = pri.getEditText().getText().toString().trim();

                            if (isValid()) {
                                // uploadImage();
                                uploadImageToFirebase();
                            }
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
            final ProgressDialog progressDialog = new ProgressDialog(Chef_PostDish.this);
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
                            FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, String.valueOf(uri), RandomUId, ChefId, productattribute, stockcounter);
                            firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(Chef_PostDish.this, "Dish posted successfully", Toast.LENGTH_SHORT).show();
//                                    Intent b = new Intent(Update_Delete_Dish.this, ChefFoodPanel_BottomNavigation.class);
//                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(b);
//                                    Intent b = new Intent(Chef_PostDish.this, ChefProfileFragment.class);
//                                    startActivity(b);
//                                    finish();
                                }
                            });
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    progressDialog.dismiss();
                    Toast.makeText(Chef_PostDish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        final ProgressDialog progressDialog = new ProgressDialog(Chef_PostDish.this);
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

                            // Assuming these values are initialized somewhere in your activity
                            // String dishes = "Example Dish";  // Replace with your actual data
                            // String quantity = "1";  // Replace with your actual data
                            // String price = "100";  // Replace with your actual data
                            // String description = "Example Description";  // Replace with your actual data
                            // String productattribute = "Example Attribute";  // Replace with your actual data
                            // String stockcounter = "10";  // Replace with your actual data

                            FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, downloadUri, RandomUId, ChefId, productattribute, stockcounter);
                            FirebaseDatabase.getInstance().getReference("FoodSupplyDetails")
                                    .child(State)
                                    .child(City)
                                    .child(Sub)
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(RandomUId)
                                    .setValue(info)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(Chef_PostDish.this, "Dish posted successfully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Chef_PostDish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
//private void uploadImageToFirebasei() {
//        if (croppedImage != null) {
//            final ProgressDialog progressDialog = new ProgressDialog(Chef_PostDish.this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();
//
//            RandomUId = UUID.randomUUID().toString();
//             ref = FirebaseStorage.getInstance().getReference().child("images/" + RandomUId);
//             ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//            // Convert the bitmap to a byte array
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] data = baos.toByteArray();
//
//            ref.putBytes(data)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                String downloadUri = uri.toString();
//
//                                // Assuming these values are initialized
//                                // String dishes = "Example Dish";  // Replace with your actual data
//                                // String quantity = "1";  // Replace with your actual data
//                                // String price = "100";  // Replace with your actual data
//                                // String description = "Example Description";  // Replace with your actual data
//                                // String productattribute = "Example Attribute";  // Replace with your actual data
//                                // String stockcounter = "10";  // Replace with your actual data
//
//                                FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, downloadUri, RandomUId, ChefId, productattribute, stockcounter);
//                                FirebaseDatabase.getInstance().getReference("FoodSupplyDetails")
//                                        .child(State)
//                                        .child(City)
//                                        .child(Sub)
//                                        .child(ChefId)
//                                        .child(RandomUId)
//                                        .setValue(info)
//                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                progressDialog.dismiss();
//                                                Toast.makeText(Chef_PostDish.this, "Dish posted successfully", Toast.LENGTH_SHORT).show();
//                                            }
//                                        });
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        progressDialog.dismiss();
//                        Toast.makeText(Chef_PostDish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnProgressListener(new UploadTask.OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
//                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
//                        progressDialog.setCanceledOnTouchOutside(false);
//                    }
//                });
//        } else {
//            Toast.makeText(Chef_PostDish.this, "No image selected", Toast.LENGTH_SHORT).show();
//        }
//    }




//      private void onSelectImageClick(View v) {
//
//          CropImage.startPickImageActivity(this);
//      }

    @Override
    // @SuppressLint("NewApi")
    //  protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    //      if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
    //          imageuri = CropImage.getPickImageResultUri(this, data);

    //          if (CropImage.isReadExternalStoragePermissionsRequired(this, imageuri)) {
    //              mCropimageuri = imageuri;
    //              requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

    //          } else {

    //              startCropImageActivity(imageuri);
    //          }
    //      }

    //      if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
    //          CropImage.ActivityResult result = CropImage.getActivityResult(data);
    //          if (resultCode == RESULT_OK) {
    //              ((ImageButton) findViewById(R.id.imageupload)).setImageURI(result.getUri());
    //              Toast.makeText(this, "Cropped Successfully", Toast.LENGTH_SHORT).show();
    //          } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
    //              Toast.makeText(this, "Cropping failed" + result.getError(), Toast.LENGTH_SHORT).show();
    //          }
    //      }


    //      super.onActivityResult(requestCode, resultCode, data);
    //  }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
        imageUri = data.getData();
        cropImageView.setVisibility(View.VISIBLE);  // Show the CropImageView
        cropImageView.setImageUriAsync(imageUri);

        // Optionally, you can automatically crop after setting the URI
//        cropImageView.getCroppedImageAsync();
        // Instead of getCroppedImageAsync(), use getCroppedImage() after setting the URI

        // croppedImage = cropImageView.getCroppedImage();
        // if (croppedImage != null) {
        //     imageUploadButton.setImageBitmap(croppedImage);
        // } else {
        //     // Handle error if croppedImage is null
        //     Toast.makeText(this, "Failed to crop image", Toast.LENGTH_SHORT).show();
        // }

        
    }
}





    //  @Override
    //  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
    //                                         @NonNull int[] grantResults) {

    //      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    //      if (mCropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
    //          startCropImageActivity(mCropimageuri);
    //      } else {
    //          Toast.makeText(this, "Camera permission not granted", Toast.LENGTH_SHORT).show();
    //      }
    //  }

    //  private void startCropImageActivity(Uri imageuri) {

    //      CropImage.activity(imageuri)
    //              .setGuidelines(CropImageView.Guidelines.ON)
    //              .setMultiTouchEnabled(true)
    //              .start(this);


    //  }


}


