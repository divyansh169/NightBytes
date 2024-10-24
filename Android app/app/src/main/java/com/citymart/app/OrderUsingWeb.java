package com.citymart.app;

        import android.os.Bundle;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.Comparator;
        import java.util.List;

public class OrderUsingWeb extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnnouncementsAdapter adapter;
    private List<Announcement> announcementList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderusingweb);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        announcementList = new ArrayList<>();
        adapter = new AnnouncementsAdapter(announcementList);
        recyclerView.setAdapter(adapter);

        String state = getIntent().getStringExtra("state");
        String city = getIntent().getStringExtra("city");
        String sub = getIntent().getStringExtra("sub");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("AdminAnn")
                .child(state).child(city).child(sub);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                announcementList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Announcement announcement = dataSnapshot.getValue(Announcement.class);
                    announcementList.add(announcement);
                }
                // Sort announcements by Date and Time in descending order
                Collections.sort(announcementList, new Comparator<Announcement>() {
                    @Override
                    public int compare(Announcement a1, Announcement a2) {
                        String dateTime1 = a1.getDate() + a1.getTime();
                        String dateTime2 = a2.getDate() + a2.getTime();
                        return dateTime2.compareTo(dateTime1); // Descending order
                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }

    private static class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.ViewHolder> {
        private List<Announcement> announcements;

        AnnouncementsAdapter(List<Announcement> announcements) {
            this.announcements = announcements;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), R.layout.card_announcement, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Announcement announcement = announcements.get(position);
            holder.date.setText(formatDate(announcement.getDate()));
            holder.time.setText(formatTime(announcement.getTime()));
            holder.heading.setText(announcement.getHeading());
            holder.description.setText(announcement.getDescription());
            holder.description.setVisibility(View.GONE);

            holder.itemView.setOnClickListener(v -> {
                if (holder.description.getVisibility() == View.GONE) {
                    holder.description.setVisibility(View.VISIBLE);
                } else {
                    holder.description.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public int getItemCount() {
            return announcements.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView date, time, heading, description;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.announcement_date);
                time = itemView.findViewById(R.id.announcement_time);
                heading = itemView.findViewById(R.id.announcement_heading);
                description = itemView.findViewById(R.id.announcement_description);
            }
        }

        private String formatDate(String date) {
            return date.substring(0, 2) + "/" + date.substring(2, 4) + "/" + date.substring(4);
        }

        private String formatTime(String time) {
            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(2));
            String amPm = hour >= 12 ? "PM" : "AM";
            hour = hour % 12 == 0 ? 12 : hour % 12;
            return String.format("%02d:%02d %s", hour, minute, amPm);
        }
    }

    public static class Announcement {
        private String Date, Time, Heading, Description;

        public Announcement() {
            // Default constructor required for calls to DataSnapshot.getValue(Announcement.class)
        }

        public String getDate() {
            return Date;
        }

        public String getTime() {
            return Time;
        }

        public String getHeading() {
            return Heading;
        }

        public String getDescription() {
            return Description;
        }
    }
}

