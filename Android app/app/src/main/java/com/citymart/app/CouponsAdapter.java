package com.citymart.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponViewHolder> {

    private List<Coupon> couponList;
    private DatabaseReference databaseReference;
    private String sellerId;
    private boolean isSellerView;

    // Modify constructor to take an additional parameter to identify the context (seller or customer)
    public CouponsAdapter(List<Coupon> couponList, DatabaseReference databaseReference, String sellerId, boolean isSellerView) {
        this.couponList = couponList;
        this.databaseReference = databaseReference;
        this.sellerId = sellerId;
        this.isSellerView = isSellerView;
    }

    @NonNull
    @Override
    public CouponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_item, parent, false);
        return new CouponViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CouponViewHolder holder, int position) {
        Coupon coupon = couponList.get(position);

        // holder.couponCodeTextView.setText("Code: " + coupon.getCouponCode());
        // holder.couponTypeTextView.setText("Type: " + coupon.getCouponType());
        // holder.discountTextView.setText("Discount: ₹" + coupon.getDiscount());
        // holder.minOrderValueTextView.setText("Min Order: ₹" + coupon.getMinOrderValue());
        // holder.freeItemTextView.setText("Free Item: " + coupon.getFreeItem());
        // if (coupon.getCouponType().contains("Get free")) {
        //     holder.freeItemTextView.setVisibility(View.VISIBLE);
        //     holder.freeItemTextView.setText("Free Item: " + coupon.getFreeItem());
        // } else {
        //     holder.freeItemTextView.setVisibility(View.GONE);
        // }
        String discountText;
        String conditionText;

        switch (coupon.getCouponType()) {
            case "₹X off above ₹Y":
                discountText = "₹" + coupon.getDiscount() + " discount";
                conditionText = "on orders above ₹" + coupon.getMinOrderValue();
                break;

            case "X% off above ₹Y":
                discountText = coupon.getDiscount() + "% discount";
                conditionText = "on orders above ₹" + coupon.getMinOrderValue();
                break;

            case "Get free Z above ₹Y":
                discountText = "Get Free " + coupon.getFreeItem();
                conditionText = "on orders above ₹" + coupon.getMinOrderValue();
                break;

            case "Free delivery above ₹Y":
                discountText = "Free Delivery";
                conditionText = "on orders above ₹" + coupon.getMinOrderValue();
                break;

            default:
                discountText = coupon.getDiscount() + " off";
                conditionText = "on orders above " + coupon.getMinOrderValue();
                break;
        }

        holder.discountTextView.setText(discountText);
        holder.conditionTextView.setText(conditionText);
        holder.couponCodeTextView.setText(coupon.getCouponCode());

        if (isSellerView) {
            // Show the delete button if in seller view
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.deleteButton.setOnClickListener(v -> {
                databaseReference.child("chefCoupons").child(sellerId).child(coupon.getId()).removeValue();
            });
        }
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    static class CouponViewHolder extends RecyclerView.ViewHolder {
        TextView couponCodeTextView, couponTypeTextView, discountTextView, conditionTextView, minOrderValueTextView, freeItemTextView;
        ImageButton deleteButton;

        public CouponViewHolder(@NonNull View itemView) {
            super(itemView);
            couponCodeTextView = itemView.findViewById(R.id.couponCodeTextView);
            couponTypeTextView = itemView.findViewById(R.id.couponTypeTextView);
            discountTextView = itemView.findViewById(R.id.discountTextView);
            conditionTextView = itemView.findViewById(R.id.conditionTextView);
            minOrderValueTextView = itemView.findViewById(R.id.minOrderValueTextView);
            freeItemTextView = itemView.findViewById(R.id.freeItemTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
