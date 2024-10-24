package com.citymart.app.ChefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.citymart.app.R;
public class ChefOrderFragment extends Fragment {

    LottieAnimationView OrdertobePrepare, OrdertobePrepare2;
    LottieAnimationView Preparedorders, Preparedorders2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Orders");
        View v = inflater.inflate(R.layout.fragment_chef_order, null);
        OrdertobePrepare=(LottieAnimationView)v.findViewById(R.id.ordertobe);
        OrdertobePrepare2=(LottieAnimationView)v.findViewById(R.id.ordertobe2);
        Preparedorders=(LottieAnimationView)v.findViewById(R.id.prepareorder);
        Preparedorders2=(LottieAnimationView)v.findViewById(R.id.prepareorder2);

        OrdertobePrepare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(),ChefOrderTobePrepared.class);
                startActivity(i);
            }
        });

        OrdertobePrepare2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ChefFullOrdertobePrepareView.class);
                startActivity(intent);
            }
        });

        Preparedorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ChefPreparedOrder.class);
                startActivity(intent);
            }
        });

        Preparedorders2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ChefFullPreparedOrderView.class);
                startActivity(intent);
            }
        });


        return v;
    }
}