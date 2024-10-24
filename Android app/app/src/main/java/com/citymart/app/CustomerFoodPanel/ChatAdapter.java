package com.citymart.app.CustomerFoodPanel;

//import android.content.Context;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//        import android.widget.ArrayAdapter;
//        import android.widget.TextView;
//
//import com.citymart.app.R;
//
//import java.text.SimpleDateFormat;
//import java.util.List;
//import java.util.Locale;
//
//public class ChatAdapter extends ArrayAdapter<ChatMessage> {
//
//    public ChatAdapter(Context context, List<ChatMessage> messages) {
//        super(context, 0, messages);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ChatMessage message = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat_message, parent, false);
//        }
//
//        TextView textViewMessage = convertView.findViewById(R.id.textViewMessage);
//        TextView textViewTime = convertView.findViewById(R.id.textViewTime);
//
//        // Set the message text
//        textViewMessage.setText(message.getMessage());
//
//        // Set the time
//        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
//        String formattedTime = dateFormat.format(message.getTimestamp());
//        textViewTime.setText(formattedTime);
//
//        return convertView;
//    }
//
//
//
//
//
//}


import android.content.Context;
        import android.text.format.DateUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.citymart.app.R;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;
        import java.util.Locale;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {

    private Calendar calendar;

    public ChatAdapter(Context context, List<ChatMessage> messages) {
        super(context, 0, messages);
        calendar = Calendar.getInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage message = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat_message, parent, false);
        }

        TextView textViewMessage = convertView.findViewById(R.id.textViewMessage);
        TextView textViewTime = convertView.findViewById(R.id.textViewTime);
        TextView textViewDate = convertView.findViewById(R.id.textViewDate);

        // Set the message text
        textViewMessage.setText(message.getMessage());

        // Set the time
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String formattedTime = dateFormat.format(message.getTimestamp());
        textViewTime.setText(formattedTime);

        // Show date if it's different from the previous message's date
        if (position == 0 || !isSameDate(getItem(position - 1).getTimestamp(), message.getTimestamp())) {
            String dateString;
//            if (DateUtils.isToday(message.getTimestamp().getTime())) {
//                dateString = getContext().getString(R.string.today);
//            }
            if (DateUtils.isToday(message.getTimestamp())) {
                dateString = getContext().getString(R.string.today);
            }

            else {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                dateString = dateFormat2.format(message.getTimestamp());
            }
            textViewDate.setVisibility(View.VISIBLE);
            textViewDate.setText(dateString);
        } else {
            textViewDate.setVisibility(View.GONE);
        }

        return convertView;
    }

//    private boolean isSameDate(Date date1, Date date2) {
//        calendar.setTime(date1);
//        int year1 = calendar.get(Calendar.YEAR);
//        int month1 = calendar.get(Calendar.MONTH);
//        int day1 = calendar.get(Calendar.DAY_OF_MONTH);
//
//        calendar.setTime(date2);
//        int year2 = calendar.get(Calendar.YEAR);
//        int month2 = calendar.get(Calendar.MONTH);
//        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
//
//        return year1 == year2 && month1 == month2 && day1 == day2;
//    }
private boolean isSameDate(long timestamp1, long timestamp2) {
    // Convert timestamps to Date objects
    Date date1 = new Date(timestamp1);
    Date date2 = new Date(timestamp2);

    // Compare dates
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);

    return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
            cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
}
}


