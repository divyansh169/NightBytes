package com.citymart.app.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.canhub.cropper.CropImageView;
//import com.yalantis.ucrop.UCrop;
//import com.yalantis.ucrop.view.CropImageView;

import java.io.File;

import java.util.UUID;

public class Update_Delete_Dish extends AppCompatActivity {


    TextInputLayout desc, qty, pri, prodattpri, stockcntpri;
    TextView Dishname;
    ImageButton imageButton;
    Uri imageuri;
    String dburi;
     private Uri mCropimageuri;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private CropImageView cropImageView;
    Button Update_dish, Delete_dish;
    String description, quantity, price, dishes, ChefId, productattribute, stockcounter;
    String RandomUId;
    StorageReference ref;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth FAuth;
    String ID;
    private ProgressDialog progressDialog;
    DatabaseReference dataaa;
    String State, City, Sub;

//    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update__delete__dish);

        desc = (TextInputLayout) findViewById(R.id.description);
        qty = (TextInputLayout) findViewById(R.id.quantity);
        pri = (TextInputLayout) findViewById(R.id.price);
        prodattpri = (TextInputLayout) findViewById(R.id.prodatt);
        stockcntpri = (TextInputLayout) findViewById(R.id.stockcnt);
        Dishname = (TextView) findViewById(R.id.dish_name);
        imageButton = (ImageButton) findViewById(R.id.imageupload);
        Update_dish = (Button) findViewById(R.id.Updatedish);
        Delete_dish = (Button) findViewById(R.id.Deletedish);
        cropImageView = findViewById(R.id.cropImageView);
        ID = getIntent().getStringExtra("updatedeletedish");

        String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dataaa = firebaseDatabase.getInstance().getReference("Chef").child(userid);
        dataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Chef chefc = dataSnapshot.getValue(Chef.class);
                State = chefc.getState();
                City = chefc.getCity();
                Sub = chefc.getSuburban();
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

                Update_dish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        description = desc.getEditText().getText().toString().trim();
                        quantity = qty.getEditText().getText().toString().trim();
                        price = pri.getEditText().getText().toString().trim();
                        productattribute = prodattpri.getEditText().getText().toString().trim();
                        stockcounter = stockcntpri.getEditText().getText().toString().trim();


                        if (isValid()) {
                            if (imageuri != null) {
//                                uploadImage();
                                uploadImageToFirebase();
                            } else {
                                updatedesc(dburi);
                            }
                            Intent b = new Intent(Update_Delete_Dish.this, ChefFoodPanel_BottomNavigation.class);
                            b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(b);
//                            finish();//removed finish();
                        }
                    }
                });

                Delete_dish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(Update_Delete_Dish.this);
                        builder.setMessage("Are you sure you want to Delete Dish");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID).removeValue();

                                AlertDialog.Builder food = new AlertDialog.Builder(Update_Delete_Dish.this);
                                food.setMessage("Your Dish has been Deleted");
                                food.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

//                                        startActivity(new Intent(Update_Delete_Dish.this, ChefFoodPanel_BottomNavigation.class));
                                      Intent b = new Intent(Update_Delete_Dish.this, ChefFoodPanel_BottomNavigation.class);
                                      b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                      startActivity(b);
                                    }
                                });
                                AlertDialog alertt = food.create();
                                alertt.show();


                            }
                        });
                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });


                String useridd = FirebaseAuth.getInstance().getCurrentUser().getUid();
                progressDialog = new ProgressDialog(Update_Delete_Dish.this);
                databaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(useridd).child(ID);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateDishModel updateDishModel = dataSnapshot.getValue(UpdateDishModel.class);

                        desc.getEditText().setText(updateDishModel.getDescription());
                        prodattpri.getEditText().setText(updateDishModel.getprodatt());
                        stockcntpri.getEditText().setText(updateDishModel.getstockcnt());
                        qty.getEditText().setText(updateDishModel.getQuantity());
                        Dishname.setText("Product name: " + updateDishModel.getDishes());
                        dishes = updateDishModel.getDishes();
                        pri.getEditText().setText(updateDishModel.getPrice());
                        Glide.with(Update_Delete_Dish.this).load(updateDishModel.getImageURL()).into(imageButton);
                        dburi = updateDishModel.getImageURL();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                FAuth = FirebaseAuth.getInstance();
                databaseReference = firebaseDatabase.getInstance().getReference("FoodSupplyDetails");
                storage = FirebaseStorage.getInstance();
                storageReference = storage.getReference();
//                imageButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        onSelectImageClick(v);
//                    }
//                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            RandomUId = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUId);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            updatedesc(String.valueOf(uri));
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Update_Delete_Dish.this, "Failed : " + e.getMessage(), Toast.LENGTH_LONG).show();
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
        if (imageuri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(Update_Delete_Dish.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            RandomUId = UUID.randomUUID().toString();
            ref = FirebaseStorage.getInstance().getReference().child("images/" + RandomUId);
            ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            ref.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUri = uri.toString();
                                    updatedesc(downloadUri);
//                                    FoodSupplyDetailsBanner info = new FoodSupplyDetailsBanner(downloadUri, RandomUId, ChefId);
//                                    firebaseDatabase.getInstance().getReference("FoodSupplyDetailsBanner").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(RandomUId)
//                                            .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    progressDialog.dismiss();
//                                                    Toast.makeText(Update_Delete_Dish.this, "Banner posted successfully", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });

                                    // Assuming these values are initialized somewhere in your activity
                                    // String dishes = "Example Dish";  // Replace with your actual data
                                    // String quantity = "1";  // Replace with your actual data
                                    // String price = "100";  // Replace with your actual data
                                    // String description = "Example Description";  // Replace with your actual data
                                    // String productattribute = "Example Attribute";  // Replace with your actual data
                                    // String stockcounter = "10";  // Replace with your actual data

//                                    FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, downloadUri, RandomUId, ChefId, productattribute, stockcounter);
//                                    FirebaseDatabase.getInstance().getReference("FoodSupplyDetails")
//                                            .child(State)
//                                            .child(City)
//                                            .child(Sub)
//                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                                            .child(RandomUId)
//                                            .setValue(info)
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    progressDialog.dismiss();
//                                                    Toast.makeText(Chef_PostBanner.this, "Dish posted successfully", Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Update_Delete_Dish.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void updatedesc(String uri) {
        ChefId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FoodSupplyDetails info = new FoodSupplyDetails(dishes, quantity, price, description, uri, ID, ChefId, productattribute, stockcounter);
        firebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(ID)
                .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(Update_Delete_Dish.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            cropImageView.setVisibility(View.VISIBLE);  // Show the CropImageView
            cropImageView.setImageUriAsync(imageuri);
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
//                 Toast.makeText(this, "Cropped Successfully" + result.getSampleSize(), Toast.LENGTH_SHORT).show();
//             } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                 Toast.makeText(this, "cropping failed" + result.getError(), Toast.LENGTH_SHORT).show();
//             }
//         }
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
