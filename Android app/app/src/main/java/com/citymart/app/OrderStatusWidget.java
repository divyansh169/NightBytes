package com.citymart.app;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.citymart.app.CustomerFoodPanel.CustomerFinalOrders1;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderStatusWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_order_status);

        // Fetch order status from Firebase and update widget accordingly
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference reference = databaseReference.child(snapshot.getKey()).child("OtherInformation");
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
                                String status = customerFinalOrders1.getStatus();
                                views.setTextViewText(R.id.status_text, status);
                                appWidgetManager.updateAppWidget(appWidgetId, views);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle error
                        }
                    });
                }
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), OrderStatusWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}
