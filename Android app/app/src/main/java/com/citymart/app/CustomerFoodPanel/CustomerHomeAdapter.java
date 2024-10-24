package com.citymart.app.CustomerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.citymart.app.ChefFoodPanel.UpdateDishModel;
import com.citymart.app.ChefFoodPanel.UpdateDishModel_bannerlist;
import com.citymart.app.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.Objects;

public class CustomerHomeAdapter extends RecyclerView.Adapter<CustomerHomeAdapter.ViewHolder> {


    private Context mcontext;
    private List<UpdateDishModel>updateDishModellist;
    DatabaseReference databaseReference;

    public CustomerHomeAdapter(Context context,List<UpdateDishModel>updateDishModellist)
    {
        this.updateDishModellist=updateDishModellist;
        this.mcontext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.customer_menudish,parent,false);
        return new CustomerHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final UpdateDishModel updateDishModel=updateDishModellist.get(position);
        Glide.with(mcontext).load(updateDishModel.getImageURL()).into(holder.imageView);
        holder.Dishname.setText(updateDishModel.getDishes());
        updateDishModel.getRandomUID();
        updateDishModel.getChefId();
        holder.prodatting.setText(updateDishModel.getprodatt());
        holder.stocking.setText(updateDishModel.getstockcnt());

        if(Objects.equals(updateDishModel.getstockcnt(), "in stock")  ){
            holder.stocklay.setVisibility(View.GONE);
        }
        else{
            if (Objects.equals(updateDishModel.getstockcnt(), "0") ){
                holder.stview.setTextColor(Color.parseColor("#DC143C"));
                holder.stocking.setTextColor(Color.parseColor("#DC143C"));

            }
            else{
                holder.stview.setTextColor(Color.parseColor("#5FB709"));
                holder.stocking.setTextColor(Color.parseColor("#5FB709"));
            }

            holder.stocklay.setVisibility(View.VISIBLE);
        }




        holder.price.setText("Price: ₹ " + updateDishModel.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext,OrderDish.class);
                intent.putExtra("FoodMenu",updateDishModel.getRandomUID());
                intent.putExtra("ChefId",updateDishModel.getChefId());


                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return updateDishModellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView Dishname,price, prodatting, stocking, stview;
        LinearLayout stocklay;
//        Button annbutton;
        ElegantNumberButton additem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.menu_image);
            Dishname=itemView.findViewById(R.id.dishname);
            price=itemView.findViewById(R.id.dishprice);
            prodatting=itemView.findViewById(R.id.prodatting);
            stocking=itemView.findViewById(R.id.stocking);
            additem=itemView.findViewById(R.id.number_btn);
            stocklay=itemView.findViewById(R.id.stocklay);
            stview=itemView.findViewById(R.id.stview);
//            annbutton=itemView.findViewById(R.id.annbutton);


        }
    }
}
