package com.citymart.app.CustomerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.citymart.app.ChefFoodPanel.UpdateDishModel_bannerlist;
import com.citymart.app.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CustomerHomeAdapter_bannerlist extends RecyclerView.Adapter<CustomerHomeAdapter_bannerlist.ViewHolder> {


    private Context mcontext;
    private List<UpdateDishModel_bannerlist>updateDishModellist_bannerlist;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter_bannerlist(Context context, List<UpdateDishModel_bannerlist> updateDishModellist_bannerlist)
    {
        this.updateDishModellist_bannerlist=updateDishModellist_bannerlist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.offer_layout,parent,false);
        return new CustomerHomeAdapter_bannerlist.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UpdateDishModel_bannerlist updateDishModel_bannerlist=updateDishModellist_bannerlist.get(position);
        Glide.with(mcontext).load(updateDishModel_bannerlist.getImageURL()).into(holder.imageView);
//        holder.Dishname.setText(updateDishModel_bannerlist.getDishes());
//        updateDishModel_bannerlist.getRandomUID();
//        updateDishModel_bannerlist.getChefId();
//        holder.prodatting.setText(updateDishModel_bannerlist.getprodatt());
//        holder.price.setText("Price: ₹ " + updateDishModel_bannerlist.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext,OrderDishOffer.class);
                intent.putExtra("FoodMenuu",updateDishModel_bannerlist.getRandomUID());
                intent.putExtra("ChefIdd",updateDishModel_bannerlist.getChefId());


                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateDishModellist_bannerlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

//        TextView Dishname,price, prodatting;
        //        Button annbutton;
//        ElegantNumberButton additem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.offer_iv);
//            Dishname=itemView.findViewById(R.id.dishname);
//            price=itemView.findViewById(R.id.dishprice);
//            prodatting=itemView.findViewById(R.id.prodatting);
//            additem=itemView.findViewById(R.id.number_btn);
//            annbutton=itemView.findViewById(R.id.annbutton);


        }
    }
}
