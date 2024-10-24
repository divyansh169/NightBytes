package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.Chef;
import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class ChefOrderDishesAdapter extends RecyclerView.Adapter<ChefOrderDishesAdapter.ViewHolder> {


    private Context mcontext;
    private List<ChefPendingOrders> chefPendingOrderslist;

    public ChefOrderDishesAdapter(Context context, List<ChefPendingOrders> chefPendingOrderslist) {
        this.chefPendingOrderslist = chefPendingOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_order_dishes, parent, false);
        return new ChefOrderDishesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefPendingOrders chefPendingOrders = chefPendingOrderslist.get(position);
        holder.dishname.setText(chefPendingOrders.getDishName());
        holder.price.setText("Price: ₹ " + chefPendingOrders.getPrice());
        holder.quantity.setText("× " + chefPendingOrders.getDishQuantity());
        holder.totalprice.setText("Total: ₹ " + chefPendingOrders.getTotalPrice());

        String dishidi = chefPendingOrders.getDishId();
        int dq = Integer.parseInt(chefPendingOrders.getDishQuantity()) ;

        DatabaseReference datawq = FirebaseDatabase.getInstance().getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        datawq.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    final Chef chef = dataSnapshot.getValue(Chef.class);
//                    final String ChefNamey = chef.getFname() + " " + chef.getLname();
//                    final String compname = chef.getSuburban();
                    final String State = chef.getState();
                    final String City = chef.getCity();
                    final String Sub = chef.getSuburban();


                    DatabaseReference gty1 = FirebaseDatabase.getInstance().getReference("FoodSupplyDetails").child(State).child(City).child(Sub).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(dishidi).child("Stockcnt");
                    gty1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {

                                String stcnt = dataSnapshot.getValue(String.class);

                                if (!(Objects.equals(stcnt, "in stock"))) {

                                    int stockcount  = Integer.parseInt(stcnt);
                                    if(dq > stockcount){
                                        holder.stkkcnnt.setText(stcnt);
                                        holder.stkly.setVisibility(View.VISIBLE);
                                        holder.dishlay.setBackgroundColor(Color.parseColor("#DC143C"));
                                    }
                                    else {
                                        holder.stkly.setVisibility(View.GONE);
                                        holder.dishlay.setBackgroundColor(Color.parseColor("#FFFFFF"));
                                    }

                                }

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return chefPendingOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, price, totalprice, quantity, stkkcnnt;
        LinearLayout dishlay,stkly;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.DN);
            price = itemView.findViewById(R.id.PR);
            totalprice = itemView.findViewById(R.id.TR);
            quantity = itemView.findViewById(R.id.QY);
            dishlay = itemView.findViewById(R.id.dishlay);
            stkly = itemView.findViewById(R.id.stkly);
            stkkcnnt = itemView.findViewById(R.id.stkkcnnt);
        }
    }
}
