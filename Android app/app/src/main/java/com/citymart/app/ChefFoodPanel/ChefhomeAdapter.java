package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.citymart.app.Chef;
import com.citymart.app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class ChefhomeAdapter extends RecyclerView.Adapter<ChefhomeAdapter.ViewHolder> {

   private Context mcont;
   private List<UpdateDishModel>updateDishModellist;
    String State, City, Sub;
    public static String idu;
//    String ID;

   public ChefhomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
   {
       this.updateDishModellist=updateDishModellist;
       this.mcont=context;
   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mcont).inflate(R.layout.chef_menu_update_delete,parent,false);
       return new ChefhomeAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       final UpdateDishModel updateDishModel=updateDishModellist.get(position);
       holder.dishes.setText(updateDishModel.getDishes());
        holder.productattrbutton.setText(updateDishModel.getprodatt());
        holder.stockcntbtn.setText(updateDishModel.getstockcnt());
       idu =  updateDishModel.getRandomUID();
//       if (  (holder.productattrbutton.getText() == "out of stock")  ||  (holder.stockcntbtn.getText() == "0") ){
       if ( (holder.stockcntbtn.getText() == "0") ){
           holder.switchbtnoff.setVisibility(View.VISIBLE);
           holder.switchbtnon.setVisibility(View.GONE);
       }
       else {
           holder.switchbtnon.setVisibility(View.VISIBLE);
           holder.switchbtnoff.setVisibility(View.GONE);
       }
       //................................................................................................................
        DatabaseReference adataaa = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        adataaa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshotax) {
                Chef chefc = dataSnapshotax.getValue(Chef.class);
                State = chefc.getState();
                City = chefc.getCity();
                Sub = chefc.getSuburban();

                DatabaseReference jhgdatabaseReference = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID());
                jhgdatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        UpdateDishModel updateDishModel = dataSnapshot.getValue(UpdateDishModel.class);

//                        desc.getEditText().setText(updateDishModel.getDescription());
//                        prodattpri.getEditText().setText(updateDishModel.getprodatt());
//                        if (updateDishModel.getprodatt().contains("out of stock") || holder.productattrbutton.getText() == "out of stock"  ||   holder.stockcntbtn.getText() == "0"  || Objects.equals(updateDishModel.getstockcnt(), "0")){
                        if (holder.stockcntbtn.getText() == "0"  || Objects.equals(updateDishModel.getstockcnt(), "0")){
                            holder.switchbtnoff.setVisibility(View.VISIBLE);
                            holder.switchbtnon.setVisibility(View.GONE);
                        }
                        else {
                            holder.switchbtnon.setVisibility(View.VISIBLE);
                            holder.switchbtnoff.setVisibility(View.GONE);
                        }
//                        qty.getEditText().setText(updateDishModel.getQuantity());
//                        Dishname.setText("Product name: " + updateDishModel.getDishes());
//                        dishes = updateDishModel.getDishes();
//                        pri.getEditText().setText(updateDishModel.getPrice());
//                        Glide.with(Update_Delete_Dish.this).load(updateDishModel.getImageURL()).into(imageButton);
//                        dburi = updateDishModel.getImageURL();

//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


        holder.switchbtnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.switchbtnoff.setVisibility(View.VISIBLE);
                holder.switchbtnon.setVisibility(View.GONE);
//                FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).child("Prodatt")
//                        .setValue("out of stock");
//                holder.productattrbutton.setText("out of stock");
                FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).child("Stockcnt")
                        .setValue("0");
                holder.stockcntbtn.setText("0");



            }
        });
                holder.switchbtnoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        holder.switchbtnoff.setVisibility(View.GONE);
                        holder.switchbtnon.setVisibility(View.VISIBLE);
//                        FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
//                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).child("Prodatt")
//                                .setValue("Available");
//                        holder.productattrbutton.setText("Available");
                        FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub)
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(updateDishModel.getRandomUID()).child("Stockcnt")
                                .setValue("in stock");
                        holder.stockcntbtn.setText("in stock");


                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(mcont,Update_Delete_Dish.class);
                        intent.putExtra("updatedeletedish",updateDishModel.getRandomUID());
                        mcont.startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

       TextView dishes;
       TextView productattrbutton, stockcntbtn;
//       Switch sw;
        Button switchbtnoff, switchbtnon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishes=itemView.findViewById(R.id.dish_name);
            productattrbutton=itemView.findViewById(R.id.productattrbutton);
            stockcntbtn=itemView.findViewById(R.id.stockcntbtn);
            switchbtnoff=itemView.findViewById(R.id.switchbtnoff);
            switchbtnon=itemView.findViewById(R.id.switchbtnon);
//            sw=itemView.findViewById(R.id.sw);
//            ID = getIntent().getStringExtra("updatedeletedish");

        }
    }

}
