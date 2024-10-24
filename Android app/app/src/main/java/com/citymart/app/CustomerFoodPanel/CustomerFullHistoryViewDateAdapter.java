package com.citymart.app.CustomerFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.Chef;
import com.citymart.app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CustomerFullHistoryViewDateAdapter extends RecyclerView.Adapter<CustomerFullHistoryViewDateAdapter.ViewHolder> {

    private Context mcontext;
    private List<CustomerHistory1> customerFinalFullHistorydatelist;
//    public static String cid;

    public CustomerFullHistoryViewDateAdapter(Context context, List<CustomerHistory1> customerFinalFullHistorydatelist) {
        this.customerFinalFullHistorydatelist = customerFinalFullHistorydatelist;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_full_history_date_view, parent, false);
        return new CustomerFullHistoryViewDateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CustomerHistory1 CustomerHistory1 = customerFinalFullHistorydatelist.get(position);

        holder.datee.setText("Date: " + (CustomerHistory1.getFinishdate()).substring(6)+ "/" + (CustomerHistory1.getFinishdate()).substring(4,6)+ "/" + (CustomerHistory1.getFinishdate()).substring(0,4));
//        holder.Tprice.setText("Price: ₹ " + ChefHistory.getDishPrice());
//        holder.quantity.setText(" × " + ChefHistory.getDishQuantity());
        holder.Tprice.setText("Total: ₹ " + CustomerHistory1.getGrandTotalPrice());
//        rundm = CustomerHistory1.getRandomUID();
//        cid = CustomerHistory1.getChefId();


//        DatabaseReference dattu = FirebaseDatabase.getInstance().getReference("Chef").child(cid);
////
//        dattu.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Chef chief = dataSnapshot.getValue(Chef.class);
//
//            }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

        holder.subur.setText(CustomerHistory1.getCompanyName());
        holder.custname.setText("Name: " + CustomerHistory1.getName());
        holder.mobi.setText("Phone: " + CustomerHistory1.getMobileNumber());
//        holder.addre.setText("Address: " + (CustomerHistory1.getAddress()).substring((CustomerHistory1.getAddress()).indexOf(':') + 1).trim());

        String fullAddress = CustomerHistory1.getAddress();
        int colonIndex = fullAddress.indexOf(':');
        int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
        int lastCommaIndex = fullAddress.lastIndexOf(',');
        String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
        String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

        holder.addre.setText("Address: " + firstPart);
        holder.ordtp.setText("Order Type: " + lastPart);

        holder.lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext, CustomerFullHistoryView.class);
                intent.putExtra("Finishdate",CustomerHistory1.getFinishdate());
                intent.putExtra("rundm", CustomerHistory1.getRandomUID());
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customerFinalFullHistorydatelist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView datee, Tprice, subur, custname, mobi, addre, ordtp;
        LinearLayout lay1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            datee = itemView.findViewById(R.id.datee);
            subur = itemView.findViewById(R.id.subur);
            custname = itemView.findViewById(R.id.custname);
            mobi = itemView.findViewById(R.id.mobi);
            addre = itemView.findViewById(R.id.addre);
            ordtp = itemView.findViewById(R.id.ordtp);
//            price = itemView.findViewById(R.id.Dprice);
            Tprice = itemView.findViewById(R.id.Tprice);
            lay1 = itemView.findViewById(R.id.lay1);
//            quantity = itemView.findViewById(R.id.Dqty);
        }
    }
}

