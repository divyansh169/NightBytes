package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.CustomerFoodPanel.OrderDish;
import com.citymart.app.R;

import java.util.List;

public class ChefFullHistoryViewDateAdapter extends RecyclerView.Adapter<ChefFullHistoryViewDateAdapter.ViewHolder> {

    private Context mcontext;
    private List<ChefHistory1> chefFinalFullHistorydatelist;

    public ChefFullHistoryViewDateAdapter(Context context, List<ChefHistory1> chefFinalFullHistorydatelist) {
        this.chefFinalFullHistorydatelist = chefFinalFullHistorydatelist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_full_history_date_view, parent, false);
        return new ChefFullHistoryViewDateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefHistory1 ChefHistory1 = chefFinalFullHistorydatelist.get(position);

        holder.datee.setText((ChefHistory1.getFinishdate()).substring(6)+ "/" + (ChefHistory1.getFinishdate()).substring(4,6)+ "/" + (ChefHistory1.getFinishdate()).substring(0,4));
//        holder.Tprice.setText("Price: ₹ " + ChefHistory.getDishPrice());
//        holder.quantity.setText(" × " + ChefHistory.getDishQuantity());
        holder.Tprice.setText("Total: ₹ " + ChefHistory1.getGrandTotalPrice());
        holder.lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext, ChefFullHistoryView.class);
                intent.putExtra("Finishdate",ChefHistory1.getFinishdate());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return chefFinalFullHistorydatelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView datee, Tprice;
        LinearLayout lay1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            datee = itemView.findViewById(R.id.datee);
//            price = itemView.findViewById(R.id.Dprice);
            Tprice = itemView.findViewById(R.id.Tprice);
            lay1 = itemView.findViewById(R.id.lay1);
//            quantity = itemView.findViewById(R.id.Dqty);
        }
    }
}

