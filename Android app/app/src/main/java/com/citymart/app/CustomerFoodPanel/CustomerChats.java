package com.citymart.app.CustomerFoodPanel;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerChats extends AppCompatActivity {

    private ListView listViewChat;
    private EditText editTextMessage;
    private Button buttonSend;

    private String chefId ; // ChefId you want to chat with
    private String currentUserId; // Current user's ID
    private DatabaseReference chatReference;

    private List<ChatMessage> chatMessages;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chats);

        // Get the chefId from the intent (you can implement this based on your app logic)
        chefId = getIntent().getStringExtra("chefId");
//        chefId = "mOw2z9swdsbB62b1p5RmWDQTyyw1";

        // Initialize views
        listViewChat = findViewById(R.id.listViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);

        // Get the current user's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        }

        // Set up the database reference
        chatReference = FirebaseDatabase.getInstance().getReference("UserChats")
                .child(currentUserId)
                .child(chefId);

        // Initialize chat messages list and adapter
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, chatMessages);
        listViewChat.setAdapter(chatAdapter);

        // Listen for new messages in the Firebase database
        chatReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
//                ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
//                if (chatMessage != null) {
//                    chatMessages.add(chatMessage);
//                    chatAdapter.notifyDataSetChanged();
//                }
//            }
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, String previousChildName) {
                ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                if (chatMessage != null) {
                    boolean isNewDate = false;
                    if (chatMessages.isEmpty()) {
                        isNewDate = true;
                    } else {
                        // Check if the new message has a different date from the last message
                        ChatMessage lastMessage = chatMessages.get(chatMessages.size() - 1);
                        isNewDate = !isSameDate(lastMessage.getTimestamp(), chatMessage.getTimestamp());
                    }

                    // Add the message to the list
                    chatMessages.add(chatMessage);

                    // Notify the adapter of changes
                    chatAdapter.notifyDataSetChanged();

                    // If it's a new date, scroll to the bottom of the list to show the latest message
                    if (isNewDate) {
                        listViewChat.post(new Runnable() {
                            @Override
                            public void run() {
                                listViewChat.setSelection(chatAdapter.getCount() - 1);
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
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        // Set a text watcher for the editTextMessage to enable/disable the send button
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                buttonSend.setEnabled(editable.length() > 0);
            }
        });
    }


//    private void sendMessage() {
//        String messageText = editTextMessage.getText().toString().trim();
//
//        if (!messageText.isEmpty()) {
//            long timestamp = System.currentTimeMillis();
//            ChatMessage chatMessage = new ChatMessage(currentUserId, messageText, timestamp);
//
//            // Add the new message to the local list and update the adapter
//            chatMessages.add(chatMessage);
//            chatAdapter.notifyDataSetChanged();
//
//            // Add the new message to the Firebase database
//            chatReference.child(String.valueOf(timestamp)).setValue(chatMessage)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                // Clear the editText after sending the message
//                                editTextMessage.setText("");
//                            } else {
//                                // Handle the error if message sending fails
//                                // You may want to remove the message from the local list if Firebase update fails
//                                chatMessages.remove(chatMessage);
//                                chatAdapter.notifyDataSetChanged();
//                            }
//                        }
//                    });
//        }
//    }



//    private void sendMessage() {
//        String messageText = editTextMessage.getText().toString().trim();
//
//        if (!messageText.isEmpty()) {
//            long timestamp = System.currentTimeMillis();
//            final ChatMessage chatMessage = new ChatMessage(currentUserId, messageText, timestamp);
//
//            // Add the new message to the Firebase database
//            chatReference.child(String.valueOf(timestamp)).setValue(chatMessage)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                // Clear the editText after sending the message
//                                editTextMessage.setText("");
//
//                                // Update the local list and the adapter only after Firebase update is successful
//                                chatMessages.add(chatMessage);
//                                chatAdapter.notifyDataSetChanged();
//                            } else {
//                                // Handle the error if message sending fails
//                            }
//                        }
//                    });
//        }
//    }


    private void sendMessage() {
        String messageText = "User: " + editTextMessage.getText().toString().trim();

        if (!messageText.isEmpty()) {
            final long timestamp = System.currentTimeMillis();
            final ChatMessage chatMessage = new ChatMessage(currentUserId, messageText, timestamp);

            // Add the new message to the Firebase database
            chatReference.child(String.valueOf(timestamp)).setValue(chatMessage)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Clear the editText after sending the message
                                editTextMessage.setText("");

                                // Check if the message is already in the local list before adding it
                                if (!containsMessage(chatMessage)) {
                                    // Update the local list and the adapter only after Firebase update is successful
                                    chatMessages.add(chatMessage);
                                    chatAdapter.notifyDataSetChanged();
                                }
                            }

                        }
                    });
        }
    }

    private boolean containsMessage(ChatMessage message) {
        // Check if the message is already in the local list
        for (ChatMessage chatMessage : chatMessages) {
            if (chatMessage.getTimestamp() == message.getTimestamp()) {
                return true;
            }
        }
        return false;
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
