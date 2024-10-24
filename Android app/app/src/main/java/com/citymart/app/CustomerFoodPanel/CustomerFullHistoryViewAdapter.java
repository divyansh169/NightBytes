package com.citymart.app.CustomerFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.citymart.app.R;

import java.util.List;

public class CustomerFullHistoryViewAdapter extends RecyclerView.Adapter<CustomerFullHistoryViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<CustomerHistory> customerFinalFullHistorylist;

    public CustomerFullHistoryViewAdapter(Context context, List<CustomerHistory> customerFinalFullHistorylist) {
        this.customerFinalFullHistorylist = customerFinalFullHistorylist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_full_history_view, parent, false);
        return new CustomerFullHistoryViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CustomerHistory CustomerHistory = customerFinalFullHistorylist.get(position);

        holder.dishname.setText(CustomerHistory.getDishName());
        holder.price.setText("Price: ₹ " + CustomerHistory.getDishPrice());
        holder.quantity.setText(" × " + CustomerHistory.getDishQuantity());
        holder.totalprice.setText("Total: ₹ " + CustomerHistory.getTotalPrice());
//        holder.find.setText(CustomerHistory.getFinishdate());
    }

    @Override
    public int getItemCount() {
        return customerFinalFullHistorylist.size();
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

