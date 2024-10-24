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

public class ChefFullPreparedOrderViewAdapter extends RecyclerView.Adapter<ChefFullPreparedOrderViewAdapter.ViewHolder> {

    private Context mcontext;
    private List<ChefFinalOrders2> chefFinalFullOrderslist;

    public ChefFullPreparedOrderViewAdapter(Context context, List<ChefFinalOrders2> chefFinalFullOrderslist) {
        this.chefFinalFullOrderslist = chefFinalFullOrderslist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.chef_full_preparedorder_view, parent, false);
        return new ChefFullPreparedOrderViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ChefFinalOrders2 ChefFinalOrders2 = chefFinalFullOrderslist.get(position);

        holder.dishname.setText(ChefFinalOrders2.getDishName());
//        holder.price.setText("Price: ₹ " + chefWaitingOrders.getDishPrice());
        holder.quantity.setText("× " + ChefFinalOrders2.getDishQuantity());
//        holder.totalprice.setText("Total: ₹ " + chefWaitingOrders.getTotalPrice());

    }

    @Override
    public int getItemCount() {
        return chefFinalFullOrderslist.size();
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

