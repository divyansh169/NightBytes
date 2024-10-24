package com.citymart.app.CustomerFoodPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.citymart.app.R;

import java.util.ArrayList;
import java.util.List;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private final List<Dish> dishList;
    private final List<Dish> filteredDishList = new ArrayList<>();
    private final List<Dish> selectedDishes = new ArrayList<>(); // List to track selected dishes

    public DishAdapter(List<Dish> dishList) {
        this.dishList = dishList;
        filterDishes();
    }

    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = filteredDishList.get(position);
        holder.dishName.setText(dish.getDishes());
        holder.priceRs.setText("Price: Rs " + dish.getPrice());

        // Set initial quantity and total price
        int quantity = Integer.parseInt(dish.getQuantity());
        int totalPrice = quantity * Integer.parseInt(dish.getPrice());

        holder.qty.setText("x " + quantity);
        holder.totalRs.setText("Total: Rs " + totalPrice);
        holder.elegantBtn.setNumber(dish.getQuantity());  // Set current quantity

        // Update the Dish object when the quantity changes
        holder.elegantBtn.setOnValueChangeListener((view, oldValue, newValue) -> {
            dish.setQuantity(String.valueOf(newValue));
            holder.qty.setText("x " + newValue);
            int total = Integer.parseInt(dish.getPrice()) * newValue;
            holder.totalRs.setText("Total: Rs " + total);

            // Add or remove from selectedDishes based on quantity
            if (newValue > 0 && !selectedDishes.contains(dish)) {
                selectedDishes.add(dish);
            } else if (newValue == 0) {
                selectedDishes.remove(dish);
            }
            calculateGrandTotal();
        });
    }

    @Override
    public int getItemCount() {
        return filteredDishList.size();
    }

    public List<Dish> getSelectedDishes() {
        return selectedDishes;
    }

    public void addDish(Dish dish) {
        if (!dish.getStockcnt().equals("0")) {
            dish.setQuantity("0"); // Initialize quantity to 0
            filteredDishList.add(dish);
            notifyDataSetChanged();
        }
    }

    private void filterDishes() {
        for (Dish dish : dishList) {
            if (!dish.getStockcnt().equals("0")) {
                dish.setQuantity("0"); // Initialize quantity to 0
                filteredDishList.add(dish);
            }
        }
        notifyDataSetChanged();
    }

    private void calculateGrandTotal() {
        int grandTotal = 0;
        for (Dish dish : filteredDishList) {
            grandTotal += Integer.parseInt(dish.getPrice()) * Integer.parseInt(dish.getQuantity());
        }
        if (grandTotalListener != null) {
            grandTotalListener.onGrandTotalChanged(grandTotal);
        }
    }

    public interface GrandTotalListener {
        void onGrandTotalChanged(int grandTotal);
    }

    private GrandTotalListener grandTotalListener;

    public void setGrandTotalListener(GrandTotalListener grandTotalListener) {
        this.grandTotalListener = grandTotalListener;
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView dishName, priceRs, qty, totalRs;
        ElegantNumberButton elegantBtn;

        public DishViewHolder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.dishName);
            priceRs = itemView.findViewById(R.id.priceRs);
            qty = itemView.findViewById(R.id.qty);
            totalRs = itemView.findViewById(R.id.totalRs);
            elegantBtn = itemView.findViewById(R.id.elegantBtn);
        }
    }
}
