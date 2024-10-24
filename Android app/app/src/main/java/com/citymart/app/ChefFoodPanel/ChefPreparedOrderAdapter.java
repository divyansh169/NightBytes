package com.citymart.app.ChefFoodPanel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class ChefPreparedOrderAdapter extends RecyclerView.Adapter<ChefPreparedOrderAdapter.ViewHolder> {




    private Context context;
    private List<ChefFinalOrders1> chefFinalOrders1list;

    public ChefPreparedOrderAdapter(Context context, List<ChefFinalOrders1> chefFinalOrders1list) {
        this.chefFinalOrders1list = chefFinalOrders1list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chef_preparedorder, parent, false);
        return new ChefPreparedOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String randomuidk, stu;
        final ChefFinalOrders1 chefFinalOrders1 = chefFinalOrders1list.get(position);
//        holder.Address.setText((chefFinalOrders1.getAddress()).substring(22));
//        holder.Address.setText((chefFinalOrders1.getAddress()).substring((chefFinalOrders1.getAddress()).indexOf(':') + 1).trim());

        String fullAddress = chefFinalOrders1.getAddress();
        int colonIndex = fullAddress.indexOf(':');
        int firstCommaIndex = fullAddress.indexOf(',', colonIndex);
        int lastCommaIndex = fullAddress.lastIndexOf(',');
        String firstPart = fullAddress.substring(colonIndex + 1, firstCommaIndex).trim();
        String lastPart = fullAddress.substring(lastCommaIndex + 1).trim();

        holder.Address.setText(firstPart);
//        holder.ordtp.setText("Order Type: " + lastPart);


        randomuidk=chefFinalOrders1.getRandomUID();
        stu = chefFinalOrders1.getRandomUID();
//        holder.NOTE19.setText(chefFinalOrders1.getNote());
        holder.grandtotalprice.setText("Grand Total: ₹ " + chefFinalOrders1.getGrandTotalPrice());


        DatabaseReference tgf = FirebaseDatabase.getInstance().getReference("ChefFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(randomuidk).child("Dishes");
        tgf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        ChefFinalOrders cwo = snapshot.getValue(ChefFinalOrders.class);
                         String useriddd = cwo.getUserId();
                        String usermob = chefFinalOrders1.getMobileNumber();

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

//                                    Type13 type13 = dataSnapshot.getValue(Type13.class);
////                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
//
//                                    Date fetchedDateTime = null;
//                                    try {
//                                        fetchedDateTime = sdf.parse(type13.getordertime());
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    if (fetchedDateTime != null) {
//                                        // Calculate time difference in milliseconds
//                                        long timeDifferenceMillis = System.currentTimeMillis() - fetchedDateTime.getTime();
//
//                                        // Convert milliseconds to seconds
//                                        long timeDifferenceSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis);
//
//                                        if (timeDifferenceSeconds < 60) {
//                                            // Less than a minute ago
//                                            holder.minago.setText(timeDifferenceSeconds + "s");
//                                        } else if (timeDifferenceSeconds < 3600) {
//                                            // Less than an hour ago
//                                            long minutes = TimeUnit.SECONDS.toMinutes(timeDifferenceSeconds);
//                                            holder.minago.setText(minutes + "m");
//                                        } else if (timeDifferenceSeconds < 86400) {
//                                            // Less than a day ago
//                                            long hours = TimeUnit.SECONDS.toHours(timeDifferenceSeconds);
//                                            holder.minago.setText(hours + "h");
//                                        } else if (timeDifferenceSeconds < 604800) {
//                                            // Less than a week ago
//                                            long days = TimeUnit.SECONDS.toDays(timeDifferenceSeconds);
//                                            holder.minago.setText(days + "d");
//                                        } else if (timeDifferenceSeconds < 2419200) {
//                                            // Less than a month ago
//                                            long weeks = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 7;
//                                            holder.minago.setText(weeks + "w");
//                                        } else if (timeDifferenceSeconds < 31536000) {
//                                            // Less than a year ago
//                                            long months = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 30;
//                                            holder.minago.setText(months + "M");
//                                        } else {
//                                            // More than a year ago
//                                            long years = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 365;
//                                            holder.minago.setText(years + "Y");
//                                        }
//                                    }

                                    Type13 type13 = dataSnapshot.getValue(Type13.class);
                                    String fetchedDateTime = type13.getordertime();
                                    holder.ordertame.setText(fetchedDateTime);

//                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
//                                    Date fetchedDateTime = null;
//                                    try {
//                                        fetchedDateTime = sdf.parse(type13.getordertime());
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }
//
//                                    if (fetchedDateTime != null) {
//                                        // Get the current date
//                                        Calendar cal = Calendar.getInstance();
//                                        Date currentDate = cal.getTime();
//
//                                        // If fetchedDateTime is after currentDate, it means it's the previous day
//                                        if (fetchedDateTime.after(currentDate)) {
//                                            cal.add(Calendar.DATE, -1); // Move the current date one day back
//                                            currentDate = cal.getTime();
//                                        }
//
//                                        // Calculate time difference in milliseconds
//                                        long timeDifferenceMillis = currentDate.getTime() - fetchedDateTime.getTime();
//
//                                        // Convert milliseconds to seconds
//                                        long timeDifferenceSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis);
//
//                                        if (timeDifferenceSeconds < 60) {
//                                            // Less than a minute ago
//                                            holder.minago.setText(timeDifferenceSeconds + "s");
//                                        } else if (timeDifferenceSeconds < 3600) {
//                                            // Less than an hour ago
//                                            long minutes = TimeUnit.SECONDS.toMinutes(timeDifferenceSeconds);
//                                            holder.minago.setText(minutes + "m");
//                                        } else if (timeDifferenceSeconds < 86400) {
//                                            // Less than a day ago
//                                            long hours = TimeUnit.SECONDS.toHours(timeDifferenceSeconds);
//                                            holder.minago.setText(hours + "h");
//                                        } else if (timeDifferenceSeconds < 604800) {
//                                            // Less than a week ago
//                                            long days = TimeUnit.SECONDS.toDays(timeDifferenceSeconds);
//                                            holder.minago.setText(days + "d");
//                                        } else if (timeDifferenceSeconds < 2419200) {
//                                            // Less than a month ago
//                                            long weeks = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 7;
//                                            holder.minago.setText(weeks + "w");
//                                        } else if (timeDifferenceSeconds < 31536000) {
//                                            // Less than a year ago
//                                            long months = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 30;
//                                            holder.minago.setText(months + "M");
//                                        } else {
//                                            // More than a year ago
//                                            long years = TimeUnit.SECONDS.toDays(timeDifferenceSeconds) / 365;
//                                            holder.minago.setText(years + "Y");
//                                        }
//                                    }


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




        DatabaseReference ldbref = FirebaseDatabase.getInstance().getReference("ChefStatus").child(stu);
        ldbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Type67 typechapm = dataSnapshot.getValue(Type67.class);
//                    holder.chefst.setText(typechapm.getchefsts());
//                    holder.countord.setText(typechapm.getordn());
                    if(dataSnapshot.hasChild("chefsts")) {
                        holder.chefst.setText(typechapm.getchefsts());
                    }
                    if(dataSnapshot.hasChild("ordn")) {
                        holder.countord.setText(typechapm.getordn());
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        DatabaseReference uldbref = FirebaseDatabase.getInstance().getReference("ChefTrack").child(stu);
        uldbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    Type677 typechapmm = dataSnapshot.getValue(Type677.class);
                    holder.trackdet.setVisibility(View.VISIBLE);
                    holder.trackdet.setText(typechapmm.getcheftrack());
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final String random = chefFinalOrders1.getRandomUID();
        holder.Vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChefPreparedOrderView.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((ChefPreparedOrder) context).finish();//removed finish();
            }
        });
        holder.direc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String etaddr = chefFinalOrders1.getAddress();

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
        return chefFinalOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, grandtotalprice, chefst, trackdet, countord, minago, ordertame;
        Button Vieworder, direc;
        LinearLayout conflay;
        ImageView chitchat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.customer_address);
            chefst = itemView.findViewById(R.id.chefst);
            trackdet = itemView.findViewById(R.id.trackdet);
            countord = itemView.findViewById(R.id.countord);
            grandtotalprice = itemView.findViewById(R.id.customer_totalprice);
            Vieworder = itemView.findViewById(R.id.View);
            direc= itemView.findViewById(R.id.direc);
            conflay= itemView.findViewById(R.id.conflay);
            minago = itemView.findViewById(R.id.minago);
            ordertame = itemView.findViewById(R.id.ordertame);
            chitchat= itemView.findViewById(R.id.chitchat);
        }
    }
}
