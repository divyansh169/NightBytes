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

public class ChefFullHistoryViewAdapter extends RecyclerView.Adapter<ChefFullHistoryViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<ChefHistory> chefFinalFullHistorylist;

    public ChefFullHistoryViewAdapter(Context context, List<ChefHistory> chefFinalFullHistorylist) {
        this.chefFinalFullHistorylist = chefFinalFullHistorylist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_full_history_view, parent, false);
        return new ChefFullHistoryViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefHistory ChefHistory = chefFinalFullHistorylist.get(position);

        holder.dishname.setText(ChefHistory.getDishName());
        holder.price.setText("Price: ₹ " + ChefHistory.getDishPrice());
        holder.quantity.setText(" × " + ChefHistory.getDishQuantity());
        holder.totalprice.setText("Total: ₹ " + ChefHistory.getTotalPrice());
//        holder.find.setText(ChefHistory.getFinishdate());
    }

    @Override
    public int getItemCount() {
        return chefFinalFullHistorylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishname, quantity, price, totalprice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dishname = itemView.findViewById(R.id.Dname);
            price = itemView.findViewById(R.id.Dprice);
            totalprice = itemView.findViewById(R.id.Tprice);
            quantity = itemView.findViewById(R.id.Dqty);
//            find = itemView.findViewById(R.id.find);
        }
    }
}

