package com.citymart.app;

import android.app.Service;
        import android.appwidget.AppWidgetManager;
        import android.content.Intent;
        import android.os.IBinder;
        import android.widget.RemoteViews;

        import androidx.annotation.NonNull;

import com.citymart.app.ChefFoodPanel.Type13;
import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.Date;
        import java.util.Locale;
        import java.util.concurrent.TimeUnit;

public class WidgetUpdateService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            updateWidget(appWidgetId);
        }
        return START_NOT_STICKY;
    }

    private void updateWidget(final int appWidgetId) {
        DatabaseReference dathu = FirebaseDatabase.getInstance().getReference("CustomerOrderTime")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dathu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Type13 type13 = dataSnapshot.getValue(Type13.class);
                    String fetchedDateTime = type13.getordertime();

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.getDefault());
                    try {
                        Date orderDate = format.parse(fetchedDateTime);
                        Date currentDate = new Date();

                        long diffInMillis = currentDate.getTime() - orderDate.getTime();
                        long diffInSeconds = TimeUnit.MILLISECONDS.toSeconds(diffInMillis);

                        String timeAgo = "";
                        if (diffInSeconds < 60) {
                            timeAgo = "Just now";
                        } else {
                            long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillis);
                            long diffInHours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24;
                            long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;

                            if (diffInDays > 0) {
                                timeAgo += diffInDays + " day ";
                            }
                            if (diffInHours > 0) {
                                timeAgo += diffInHours + " hr ";
                            }
                            if (diffInMinutes > 0) {
                                timeAgo += diffInMinutes + " min ";
                            }
                            timeAgo += "ago";
                        }

                        RemoteViews views = new RemoteViews(getPackageName(), R.layout.new_app_widget);
                        views.setTextViewText(R.id.widget_time_diff, "Order Time: " + timeAgo);

                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetUpdateService.this);
                        appWidgetManager.updateAppWidget(appWidgetId, views);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                stopSelf();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

