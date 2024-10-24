package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChefOrderTobePreparedAdapter extends RecyclerView.Adapter<ChefOrderTobePreparedAdapter.ViewHolder> {


    private Context context;
    private List<ChefWaitingOrders1> chefWaitingOrders1list;
    public static String paymentstatus, ordnim;
//    public static String etaddr,sty;

    public ChefOrderTobePreparedAdapter(Context context, List<ChefWaitingOrders1> chefWaitingOrders1list) {
        this.chefWaitingOrders1list = chefWaitingOrders1list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chef_ordertobeprepared, parent, false);
        return new ChefOrderTobePreparedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String randomuidk,sty;

        ChefWaitingOrders1 chefWaitingOrders1 = chefWaitingOrders1list.get(position);
//        holder.Address.setText((chefWaitingOrders1.getAddress()).substring(22));
//        holder.Address.setText((chefWaitingOrders1.getAddress()).substring((chefWaitingOrders1.getAddress()).indexOf(':') + 1).trim());

        String fullAddress = chefWaitingOrders1.getAddress();
        int colonIndex = fullAddress.indexOf(':');
        int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
        int lastCommaIndex = fullAddress.lastIndexOf(',');
        String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
        String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

        holder.Address.setText(firstPart);
//        holder.ordtp.setText("Order Type: " + lastPart);


        randomuidk = chefWaitingOrders1.getRandomUID();
        sty = chefWaitingOrders1.getRandomUID();
        holder.grandtotalprice.setText("Grand Total: ₹ " + chefWaitingOrders1.getGrandTotalPrice());

        DatabaseReference tgf = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
        tgf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChefWaitingOrders cwo = snapshot.getValue(ChefWaitingOrders.class);
                        String useriddd = cwo.getUserId();
                        String usermob = chefWaitingOrders1.getMobileNumber();

                        holder.chitchat.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(context, SellerChatActivity.class);
                                intent.putExtra("userId", useriddd);
                                intent.putExtra("usermobph", usermob);
                                context.startActivity(intent);
                            }
                        });



                        DatabaseReference dathu = FirebaseDatabase.getInstance().getReference("CustomerOrderTime").child(useriddd);
                        dathu.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){

                                    Type13 type13 = dataSnapshot.getValue(Type13.class);
                                    String fetchedDateTime = type13.getordertime();
                                    holder.ordertame.setText(fetchedDateTime);

                                    // Calculate time difference
                                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
                                    try {
                                        Date orderDate = format.parse(fetchedDateTime);
                                        Date currentDate = new Date();

                                        long diffInMillis = currentDate.getTime() - orderDate.getTime();
                                        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                                        // Format the time difference
                                        String timeAgo = "";
                                        if (diffInSeconds < 60) {
                                            timeAgo = "Just now";
                                        } else {
                                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                                            long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
                                            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;

                                            if (diffInDays > 0) {
                                                timeAgo += diffInDays + "day ";
                                            }
                                            if (diffInHours > 0) {
                                                timeAgo += diffInHours + "hr ";
                                            }
                                            if (diffInMinutes > 0) {
                                                timeAgo += diffInMinutes + "min ";
                                            }
                                            timeAgo += "ago";
                                        }

                                        holder.minago.setText(timeAgo);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                }


                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference zreference = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(sty).child("Dishes");
        zreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(!(dataSnapshot.exists())){
                    holder.direc.setVisibility(View.GONE);
                    holder.Vieworder.setVisibility(View.GONE);
                    FirebaseDatabase.getInstance().getReference("ChefWaitingOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(sty).removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference ldbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(sty);
        ldbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Type67 typechapm = dataSnapshot.getValue(Type67.class);
//                    holder.chefst.setText(typechapm.getchefsts());
//                    holder.countord.setText(typechapm.getordn());
                    if(dataSnapshot.hasChild("chefsts")) {
                        holder.chefst.setText(typechapm.getchefsts());
                        paymentstatus = typechapm.getchefsts();
                    }
                    if(dataSnapshot.hasChild("ordn")) {
                        holder.countord.setText(typechapm.getordn());
                        ordnim = typechapm.getordn();
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final String random = chefWaitingOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChefOrdertobePrepareView.class);
                intent.putExtra("RandomUID", random);
                intent.putExtra("paymentstatus", paymentstatus);
                intent.putExtra("ordnim", ordnim);
                context.startActivity(intent);
                ((ChefOrderTobePrepared) context).finish();   //removed finish();
            }
        });
//        holder.View_orderfulllist.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChefFullOrdertobePrepareView.class);
////                intent.putExtra("RandomUID", random);
//                context.startActivity(intent);
//                ((ChefOrderTobePrepared) context).finish();
//            }
//        });
        holder.direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etaddr = chefWaitingOrders1.getAddress();

                Intent intent = new Intent (Intent.ACTION_VIEW,
//                        Uri.parse(etaddr));
//                        Uri.parse("google.navigation:q="+latitude+","+longitude+"&mode=l"));
//                        Uri.parse("google.navigation:q="+etaddr.substring(0,20)+"&mode=l"));
                        Uri.parse("google.navigation:q="+etaddr.substring(0, etaddr.indexOf(": "))+"&mode=l"));
                intent.setPackage("com.google.android.apps.maps");
//                if (intent.resolveActivity(getPackageManager ()) != null) {
                context.startActivity(intent);
//                }

            }
        });







    }

    @Override
    public int getItemCount() {
        return chefWaitingOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, chefst, countord, minago, ordertame;
        Button Vieworder, direc;
        ImageView chitchat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.cust_address);
            grandtotalprice = itemView.findViewById(R.id.Grandtotalprice);
            chefst = itemView.findViewById(R.id.chefst);
            countord = itemView.findViewById(R.id.countord);
            minago = itemView.findViewById(R.id.minago);
            ordertame = itemView.findViewById(R.id.ordertame);
            Vieworder = itemView.findViewById(R.id.View_order);
//            View_orderfulllist = itemView.findViewById(R.id.View_order_full_list);
            direc= itemView.findViewById(R.id.direc);
            chitchat= itemView.findViewById(R.id.chitchat);
        }
    }
}
