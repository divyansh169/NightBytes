package com.citymart.app.ChefFoodPanel;

import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;

import com.citymart.app.CustomerFoodPanel.ChatMessage;
import com.citymart.app.R;
import com.citymart.app.notifications.FCMSender;
import com.citymart.app.notifications.NotificationMessage;
import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
        import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SellerChatActivity extends AppCompatActivity {

    private ListView listViewChatSeller;
    private EditText editTextMessageSeller;
    private Button buttonSendSeller;

    private String userId, usermobph; // User ID for whom the seller is chatting
    private String currentSellerId; // Current seller's ID
    private DatabaseReference chatReferenceSeller;
    EditText notifMessage, notifNumber;
    Button send_notif;



    private List<ChatMessageSeller> chatMessagesSeller;
    private ChatAdapterSeller chatAdapterSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_chat);

        notifMessage= findViewById(R.id.notifMessage);
        notifNumber= findViewById(R.id.notifNumber);
        send_notif= findViewById(R.id.send_notif);
        FirebaseMessaging.getInstance().subscribeToTopic("messaging");

        // Get the user ID from the intent (you can implement this based on your app logic)
        userId = getIntent().getStringExtra("userId");
        usermobph = getIntent().getStringExtra("usermobph");
//        userId = "XRq3mtwSTwWwF5nrtk9FNSHAjiW2";


        // Initialize views
        listViewChatSeller = findViewById(R.id.listViewChatSeller);
        editTextMessageSeller = findViewById(R.id.editTextMessageSeller);
        buttonSendSeller = findViewById(R.id.buttonSendSeller);

        // Get the current seller's ID
        FirebaseUser currentSeller = FirebaseAuth.getInstance().getCurrentUser();
        if (currentSeller != null) {
            currentSellerId = currentSeller.getUid();
        }

        // Set up the database reference
        chatReferenceSeller = FirebaseDatabase.getInstance().getReference("UserChats")
                .child(userId)
                .child(currentSellerId);

        // Initialize chat messages list and adapter
        chatMessagesSeller = new ArrayList<>();
        chatAdapterSeller = new ChatAdapterSeller(this, chatMessagesSeller);
        listViewChatSeller.setAdapter(chatAdapterSeller);

        // Listen for new messages in the Firebase database
        chatReferenceSeller.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
//                ChatMessageSeller chatMessageSeller = snapshot.getValue(ChatMessageSeller.class);
//                if (chatMessageSeller != null) {
//                    chatMessagesSeller.add(chatMessageSeller);
//                    chatAdapterSeller.notifyDataSetChanged();
//                }
//            }
@Override
public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
    ChatMessageSeller chatMessageSeller = snapshot.getValue(ChatMessageSeller.class);
    if (chatMessageSeller != null) {
        boolean isNewDate = false;
        if (chatMessagesSeller.isEmpty()) {
            isNewDate = true;
        } else {
            // Check if the new message has a different date from the last message
            ChatMessageSeller lastMessage = chatMessagesSeller.get(chatMessagesSeller.size() - 1);
            isNewDate = !isSameDate(lastMessage.getTimestamp(), chatMessageSeller.getTimestamp());
        }

        // Add the message to the list
        chatMessagesSeller.add(chatMessageSeller);

        // Notify the adapter of changes
        chatAdapterSeller.notifyDataSetChanged();

        // If it's a new date, scroll to the bottom of the list to show the latest message
        if (isNewDate) {
            listViewChatSeller.post(new Runnable() {
                @Override
                public void run() {
                    listViewChatSeller.setSelection(chatAdapterSeller.getCount() - 1);
                }
            });
        }
    }
}

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle changes if needed
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle removal if needed
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, String previousChildName) {
                // Handle movement if needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if needed
            }
        });

        // Set a click listener for the send button
        buttonSendSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessageSeller();

            }
        });
    }

    private void sendMessageSeller() {
        String messageText = "Seller: " + editTextMessageSeller.getText().toString().trim();

        notifMessage.setText(messageText);
        notifNumber.setText("+91" + usermobph);
        if (!notifMessage.getText().toString().isEmpty()&&(!notifNumber.getText().toString().isEmpty())){
            new FCMSender().send(String.format(NotificationMessage.message,"messaging", notifMessage.getText().toString(), notifNumber.getText().toString()), new okhttp3.Callback() {
                @Override
                public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {



                }

                @Override
                public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {

                }

            });
        }


        if (!messageText.isEmpty()) {
            final long timestamp = System.currentTimeMillis();
            final ChatMessageSeller chatMessageSeller = new ChatMessageSeller(currentSellerId, messageText, timestamp);

            // Add the new message to the Firebase database
            chatReferenceSeller.child(String.valueOf(timestamp)).setValue(chatMessageSeller)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Clear the editText after sending the message
                                editTextMessageSeller.setText("");
                            } else {
                                // Handle the error if message sending fails
                            }
                        }
                    });
        }
    }
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

