package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import java.util.List;

public class ChefFullOrdertobePrepareViewAdapter extends RecyclerView.Adapter<ChefFullOrdertobePrepareViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<ChefWaitingOrders2> chefWaitingFullOrderslist;

    public ChefFullOrdertobePrepareViewAdapter(Context context, List<ChefWaitingOrders2> chefWaitingFullOrderslist) {
        this.chefWaitingFullOrderslist = chefWaitingFullOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_full_ordertobeprepared_view, parent, false);
        return new ChefFullOrdertobePrepareViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefWaitingOrders2 chefWaitingOrders2 = chefWaitingFullOrderslist.get(position);

        holder.dishname.setText(chefWaitingOrders2.getDishName());
//        holder.price.setText("Price: ₹ " + chefWaitingOrders.getDishPrice());
        holder.quantity.setText("× " + chefWaitingOrders2.getDishQuantity());
//        holder.totalprice.setText("Total: ₹ " + chefWaitingOrders.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return chefWaitingFullOrderslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.Dname);
//            price = itemView.findViewById(R.id.Dprice);
//            totalprice = itemView.findViewById(R.id.Tprice);
            quantity = itemView.findViewById(R.id.Dqty);
        }
    }
}
