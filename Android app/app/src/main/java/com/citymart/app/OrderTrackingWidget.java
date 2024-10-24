package com.citymart.app;


        import android.app.PendingIntent;
        import android.appwidget.AppWidgetManager;
        import android.appwidget.AppWidgetProvider;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.view.View;
        import android.widget.RemoteViews;

        import androidx.annotation.NonNull;

        import com.airbnb.lottie.LottieAnimationView;
        import com.citymart.app.CustomerFoodPanel.CustomerFinalOrders1;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

public class OrderTrackingWidget extends AppWidgetProvider {

    private static final String ACTION_UPDATE_STATUS = "com.citymart.app.UPDATE_STATUS";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_order_tracking);

        // Setup an intent to update the widget status
        Intent intent = new Intent(context, OrderTrackingWidget.class);
        intent.setAction(ACTION_UPDATE_STATUS);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

        // Fetch order status from Firebase and update widget accordingly
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {  //thisy value to single
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("CustomerFinalOrders").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey()).child("OtherInformation");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                CustomerFinalOrders1 customerFinalOrders1 = dataSnapshot.getValue(CustomerFinalOrders1.class);
//                String status = dataSnapshot.child("status").getValue(String.class);
                                String status = customerFinalOrders1.getStatus();
                                updateWidgetStatus(context, appWidgetManager, appWidgetId, status);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void updateWidgetStatus(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String status) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_order_tracking);

        if ("Your order is waiting to be prepared by Seller...".equals(status)) {
            views.setViewVisibility(R.id.step1, View.VISIBLE);
            views.setFloat(R.id.step1, "setAlpha", 1.0f);
            views.setImageViewResource(R.id.line1, R.drawable.dotted_line);
        } else if ("Your Order is being prepared...".equals(status) || "Seller is preparing your order...".equals(status)) {
            views.setFloat(R.id.step1, "setAlpha", 1.0f);
            views.setFloat(R.id.step2, "setAlpha", 1.0f);
            views.setImageViewResource(R.id.line1, R.drawable.solid_line);  // Create a solid line drawable
            views.setImageViewResource(R.id.line2, R.drawable.dotted_line);
        } else if ("Your Order is delivered".equals(status) || "Your Order is on the way...".equals(status) || "Order is Prepared and shifted to delivery person...".equals(status) || "Your order is Prepared.".equals(status) || "Order is Prepared...".equals(status)  ) {
            views.setFloat(R.id.step1, "setAlpha", 1.0f);
            views.setFloat(R.id.step2, "setAlpha", 1.0f);
            views.setFloat(R.id.step3, "setAlpha", 1.0f);
            views.setImageViewResource(R.id.line1, R.drawable.solid_line);
            views.setImageViewResource(R.id.line2, R.drawable.solid_line);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (ACTION_UPDATE_STATUS.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), OrderTrackingWidget.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }
}

