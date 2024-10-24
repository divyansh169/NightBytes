package com.citymart.app.CustomerFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.orderDateTextView.setText(order.getFinishDate());
        holder.companyNameTextView.setText(order.getCompanyName());
        holder.orderTotalTextView.setText("Total: ₹" + order.getGrandTotalPrice());
        // Bind more data as needed
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderDateTextView, companyNameTextView, orderTotalTextView;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderDateTextView = itemView.findViewById(R.id.orderDateTextView);
            companyNameTextView = itemView.findViewById(R.id.companyNameTextView);
            orderTotalTextView = itemView.findViewById(R.id.orderTotalTextView);
        }
    }
}
