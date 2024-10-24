package com.citymart.app.DeliveryFoodPanel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.ViewHolder>{

    View view;
    Context context;
    ArrayList<String> companylist;
    CompanyListener companyListener;

     ArrayList<String> companylist_0 = new ArrayList<>();
    
    public CompanyAdapter(Context context, ArrayList<String> companylist, CompanyListener companyListener){
        this.context = context;
        this.companylist= companylist;
        this.companyListener = companyListener;
    }

    public View getView(){
        return view;
    }
    
    @NonNull
    @Override
    public CompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.rv_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyAdapter.ViewHolder holder, int position) {

        if (companylist!=null && companylist.size()>0){
            holder.check_box.setText(companylist.get(position));
            holder.check_box.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.check_box.isChecked()){
                        companylist_0.add(companylist.get(position));
                    }
                    else {
                        companylist_0.remove(companylist.get(position));
                    }
                    companyListener.onCompanyChange(companylist_0);

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return companylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox check_box;
            public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check_box = itemView.findViewById(R.id.check_box);
        }
    }
}
