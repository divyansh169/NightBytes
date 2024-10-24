package com.citymart.app.ChefFoodPanel;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.citymart.app.R;
import com.citymart.app.SendNotification.APIService;
import com.citymart.app.SendNotification.Client;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChefOrderStats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_stats);



        barplotgrandtotalvsdates();  //chart1
        lineplotgrandtotalvsdates();  //chart2
        setupSpinner();  //spinner dish vs quantity by date
//        String initialDate = "20231127";
//        ChefPieChartForDate(initialDate); //chart4
//        setupSpinner2(); //spinner2
//        ChefPieChartForMonth(initialMonth);  //chart41
        ChefPieChartForChef();  //chart5

        ChefMonthlyBarChart();  //chart6
        ChefYearlyBarChart();  //chart7

//        setupMonthSpinnerBar(); //spinner3

    }



    private void barplotgrandtotalvsdates() {

        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {   //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BarChart chart1 = findViewById(R.id.chart1);

                List<BarEntry> entries = new ArrayList<>();
                List<String> dateLabels = new ArrayList<>();

                // Create a map to store totals for each date
                Map<String, Float> dateTotals = new HashMap<>();

                // Initialize dateTotals with zeros for all consecutive dates
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    dateTotals.put(date, 0f);
                }

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    float totalGrandTotal = 0;

                    for (DataSnapshot randomUidSnapshot : dateSnapshot.getChildren()) {
                        if (randomUidSnapshot.child("OtherInformation").exists()) {
                            String grandTotalString = randomUidSnapshot.child("OtherInformation").child("GrandTotalPrice").getValue(String.class);

                            if (grandTotalString != null) {
                                try {
                                    float grandTotal = Float.parseFloat(grandTotalString);
                                    totalGrandTotal += grandTotal;
                                } catch (NumberFormatException e) {
//                                    //"ChartError", "Error parsing GrandTotalPrice for date " + date + ": " + e.getMessage());
                                }
                            }
                        }
                    }

                    // Update dateTotals with the calculated total for the current date
                    dateTotals.put(date, totalGrandTotal);

                    // Add entry to the chart
                    entries.add(new BarEntry(entries.size(), totalGrandTotal));
                    dateLabels.add(formatDateForChart(date));
                    Log.d("ChartDebug", "Date: " + date + ", Total GrandTotal: " + totalGrandTotal);
                }

                // Ensure all consecutive dates are represented
                for (Map.Entry<String, Float> entry : dateTotals.entrySet()) {
                    if (!dateLabels.contains(formatDateForChart(entry.getKey()))) {
                        entries.add(new BarEntry(entries.size(), 0f));
                        dateLabels.add(formatDateForChart(entry.getKey()));
                    }
                }

                BarDataSet dataSet = new BarDataSet(entries, "Total GrandTotalPrice by Date");
                dataSet.setValueTextSize(10f);
                BarData data = new BarData(dataSet);
                chart1.setData(data);

                // Set custom x-axis labels
                XAxis xAxis = chart1.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(dateLabels));
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1f); // Ensure only one label per date

                chart1.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
//                //"ChartError", "Database Error: " + databaseError.getMessage());
            }


        });

    }
    private void lineplotgrandtotalvsdates() {

        DatabaseReference chefHistoryRef2 = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef2.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LineChart chart2 = findViewById(R.id.chart2);

                List<Entry> entries = new ArrayList<>();
                List<String> dateLabels = new ArrayList<>();

                // Create a map to store totals for each date
                Map<String, Float> dateTotals = new HashMap<>();

                // Initialize dateTotals with zeros for all consecutive dates
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    dateTotals.put(date, 0f);
                }

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    float totalGrandTotal = 0;

                    for (DataSnapshot randomUidSnapshot : dateSnapshot.getChildren()) {
                        if (randomUidSnapshot.child("OtherInformation").exists()) {
                            String grandTotalString = randomUidSnapshot.child("OtherInformation").child("GrandTotalPrice").getValue(String.class);

                            if (grandTotalString != null) {
                                try {
                                    float grandTotal = Float.parseFloat(grandTotalString);
                                    totalGrandTotal += grandTotal;
                                } catch (NumberFormatException e) {
                                    // Handle parsing error if needed
                                }
                            }
                        }
                    }

                    // Update dateTotals with the calculated total for the current date
                    dateTotals.put(date, totalGrandTotal);

                    // Add entry to the chart
                    entries.add(new Entry(entries.size(), totalGrandTotal));
                    dateLabels.add(formatDateForChart(date));
                    Log.d("ChartDebug", "Date: " + date + ", Total GrandTotal: " + totalGrandTotal);
                }

                // Ensure all consecutive dates are represented
                for (Map.Entry<String, Float> entry : dateTotals.entrySet()) {
                    if (!dateLabels.contains(formatDateForChart(entry.getKey()))) {
                        entries.add(new Entry(entries.size(), 0f));
                        dateLabels.add(formatDateForChart(entry.getKey()));
                    }
                }

                LineDataSet dataSet = new LineDataSet(entries, "Total GrandTotalPrice by Date");
                dataSet.setValueTextSize(10f);
                LineData data = new LineData(dataSet);
                chart2.setData(data);

                // Set custom x-axis labels
                XAxis xAxis = chart2.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(dateLabels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1f); // Ensure only one label per date

                chart2.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                //"ChartError", "Database Error: " + databaseError.getMessage());
            }
        });

    }

    private void ChefPieChartForDate_working_for_specific_date() {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("20231127"); // Change the date

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PieChart chart4 = findViewById(R.id.chart4);

                Map<String, Integer> dishQuantities = new HashMap<>();

                // Iterate through all RandomUIDs
                for (DataSnapshot randomUidSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dishSnapshot : randomUidSnapshot.child("Dishes").getChildren()) {
                        String dishName = dishSnapshot.child("DishName").getValue(String.class);
                        String dishQuantityString = dishSnapshot.child("DishQuantity").getValue(String.class);

                        // Parse DishQuantity as an integer (assuming it's stored as a string)
                        int dishQuantity = 0;
                        if (dishQuantityString != null && !dishQuantityString.isEmpty()) {
                            try {
                                dishQuantity = Integer.parseInt(dishQuantityString);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update dishQuantities with the aggregated total for the current dish
                        dishQuantities.put(dishName, dishQuantities.getOrDefault(dishName, 0) + dishQuantity);
                    }
                }

                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                // Populate entries with dish quantities and colors
                int index = 0;
                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                    colors.add(getRandomColor(index));
                    index++;
                }

                PieDataSet dataSet = new PieDataSet(entries, "Total Dish Quantity by Dish Name");
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                chart4.setData(data);

                // Set legend
                chart4.getLegend().setEnabled(true);

                chart4.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("ChartError", "Database Error: " + databaseError.getMessage());
            }
        });
    }

    private void setupSpinner() {
        Spinner dateSpinner = findViewById(R.id.dateSpinner);

        // Fetch dates from Firebase
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> dateList = new ArrayList<>();

                // Iterate through dates and add them to the list
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    dateList.add(dateSnapshot.getKey());
                }

                // Create an ArrayAdapter with the list of dates
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ChefOrderStats.this, android.R.layout.simple_spinner_item, dateList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                dateSpinner.setAdapter(adapter);

                // Set a default selection or handle it as needed
                if (dateList.size() > 0) {
                    dateSpinner.setSelection(0);
                    ChefPieChartForDate(dateList.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
//                Log.e("FirebaseError", "Error fetching dates: " + databaseError.getMessage());
            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedDate = parentView.getItemAtPosition(position).toString();
                ChefPieChartForDate(selectedDate);
            }

//            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
    private void ChefPieChartForDate(String selectedDate) {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(selectedDate);

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PieChart chart4 = findViewById(R.id.chart4);

                Map<String, Integer> dishQuantities = new HashMap<>();

                // Iterate through all RandomUIDs
                for (DataSnapshot randomUidSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot dishSnapshot : randomUidSnapshot.child("Dishes").getChildren()) {
                        String dishName = dishSnapshot.child("DishName").getValue(String.class);
                        String dishQuantityString = dishSnapshot.child("DishQuantity").getValue(String.class);

                        // Parse DishQuantity as an integer (assuming it's stored as a string)
                        int dishQuantity = 0;
                        if (dishQuantityString != null && !dishQuantityString.isEmpty()) {
                            try {
                                dishQuantity = Integer.parseInt(dishQuantityString);
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }

                        // Update dishQuantities with the aggregated total for the current dish
                        dishQuantities.put(dishName, dishQuantities.getOrDefault(dishName, 0) + dishQuantity);
                    }
                }

                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                // Populate entries with dish quantities and colors
                int index = 0;
                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                    colors.add(getRandomColor(index));
                    index++;
                }

                PieDataSet dataSet = new PieDataSet(entries, "Total Dish Quantity by Dish Name");
                dataSet.setValueTextSize(12f);
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                chart4.setData(data);

                // Set legend
                chart4.getLegend().setEnabled(true);

                chart4.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("ChartError", "Database Error: " + databaseError.getMessage());
            }
        });
    }


    private void setupSpinner2() {
        Spinner monthSpinner = findViewById(R.id.spinner2);

        // Fetch months from Firebase
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> monthList = new ArrayList<>();

                // Iterate through dates and extract unique months
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String month = dateSnapshot.getKey().substring(4, 6) + dateSnapshot.getKey().substring(0, 4);
                    if (!monthList.contains(month)) {
                        monthList.add(month);
                    }
                }

                // Create an ArrayAdapter with the list of months
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ChefOrderStats.this, android.R.layout.simple_spinner_item, monthList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                monthSpinner.setAdapter(adapter);

                // Set a default selection or handle it as needed
                if (monthList.size() > 0) {
                    monthSpinner.setSelection(0);
                    ChefPieChartForMonth(monthList.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("FirebaseError", "Error fetching months: " + databaseError.getMessage());
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedMonth = parentView.getItemAtPosition(position).toString();
                ChefPieChartForMonth(selectedMonth);
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void ChefPieChartForMonth(String selectedMonth) {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PieChart chart41 = findViewById(R.id.chart41);

                Map<String, Integer> dishQuantities = new HashMap<>();

                // Iterate through all RandomUIDs
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String month = dateSnapshot.getKey().substring(4, 6) + dateSnapshot.getKey().substring(0, 4);

                    if (month.equals(selectedMonth)) {
                        for (DataSnapshot randomUidSnapshot : dateSnapshot.child("Dishes").getChildren()) {
                            for (DataSnapshot dishSnapshot : randomUidSnapshot.getChildren()) {
                                String dishName = dishSnapshot.child("DishName").getValue(String.class);
                                String dishQuantityString = dishSnapshot.child("DishQuantity").getValue(String.class);

                                // Parse DishQuantity as an integer (assuming it's stored as a string)
                                int dishQuantity = 0;
                                if (dishQuantityString != null && !dishQuantityString.isEmpty()) {
                                    try {
                                        dishQuantity = Integer.parseInt(dishQuantityString);
                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }

                                // Update dishQuantities with the aggregated total for the current dish
                                dishQuantities.put(dishName, dishQuantities.getOrDefault(dishName, 0) + dishQuantity);
                            }
                        }
                    }
                }

                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                // Populate entries with dish quantities and colors
                int index = 0;
                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                    colors.add(getRandomColor(index));
                    index++;
                }

                PieDataSet dataSet = new PieDataSet(entries, "Total Dish Quantity by Dish Name");
                dataSet.setValueTextSize(12f);
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                chart41.setData(data);

                // Set legend
                chart41.getLegend().setEnabled(true);

                chart41.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("ChartError", "Database Error: " + databaseError.getMessage());
            }
        });
    }






    private void ChefPieChartForChef() {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                PieChart chart5 = findViewById(R.id.chart5);

                Map<String, Integer> dishQuantities = new HashMap<>();

                // Iterate through all dates
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    // Iterate through all RandomUIDs under the current date
                    for (DataSnapshot randomUidSnapshot : dateSnapshot.getChildren()) {
                        for (DataSnapshot dishSnapshot : randomUidSnapshot.child("Dishes").getChildren()) {
                            String dishName = dishSnapshot.child("DishName").getValue(String.class);
                            String dishQuantityString = dishSnapshot.child("DishQuantity").getValue(String.class);

                            // Parse DishQuantity as an integer (assuming it's stored as a string)
                            int dishQuantity = 0;
                            if (dishQuantityString != null && !dishQuantityString.isEmpty()) {
                                try {
                                    dishQuantity = Integer.parseInt(dishQuantityString);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Update dishQuantities with the aggregated total for the current dish
                            dishQuantities.put(dishName, dishQuantities.getOrDefault(dishName, 0) + dishQuantity);
                        }
                    }
                }

                List<PieEntry> entries = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                // Populate entries with dish quantities and colors
                int index = 0;
                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                    entries.add(new PieEntry(entry.getValue(), entry.getKey()));
                    colors.add(getRandomColor(index));
                    index++;
                }

                PieDataSet dataSet = new PieDataSet(entries, "Total Dish Quantity by Dish Name");
                dataSet.setValueTextSize(12f);
                dataSet.setColors(colors);

                PieData data = new PieData(dataSet);
                chart5.setData(data);

                // Set legend
                chart5.getLegend().setEnabled(true);

                chart5.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("ChartError", "Database Error: " + databaseError.getMessage());
            }
        });
    }




    private void ChefMonthlyBarChart() {

        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BarChart chart6 = findViewById(R.id.chart6);

                List<BarEntry> entries = new ArrayList<>();
                List<String> monthLabels = new ArrayList<>();

                // Create a map to store totals for each month
                Map<String, Float> monthTotals = new HashMap<>();

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    String monthYear = date.substring(4, 6) + date.substring(0, 4); // MMYYYY format

                    float totalGrandTotal = 0;

                    for (DataSnapshot randomUidSnapshot : dateSnapshot.getChildren()) {
                        if (randomUidSnapshot.child("OtherInformation").exists()) {
                            String grandTotalString = randomUidSnapshot.child("OtherInformation").child("GrandTotalPrice").getValue(String.class);

                            if (grandTotalString != null) {
                                try {
                                    float grandTotal = Float.parseFloat(grandTotalString);
                                    totalGrandTotal += grandTotal;
                                } catch (NumberFormatException e) {
                                    // Handle parsing error
                                }
                            }
                        }
                    }

                    // Update monthTotals with the calculated total for the current month
                    monthTotals.put(monthYear, monthTotals.getOrDefault(monthYear, 0f) + totalGrandTotal);
                }

                for (Map.Entry<String, Float> entry : monthTotals.entrySet()) {
                    entries.add(new BarEntry(entries.size(), entry.getValue()));
                    monthLabels.add(formatMonthForChart(entry.getKey()));
                }

                BarDataSet dataSet = new BarDataSet(entries, "Total GrandTotalPrice by Month");
                dataSet.setValueTextSize(12f);
                BarData data = new BarData(dataSet);
                chart6.setData(data);

                // Set custom x-axis labels
                XAxis xAxis = chart6.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(monthLabels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1f); // Ensure only one label per month

                chart6.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });




    }
    private void ChefYearlyBarChart() {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BarChart chart7 = findViewById(R.id.chart7);

                List<BarEntry> entries = new ArrayList<>();
                List<String> yearLabels = new ArrayList<>();

                // Create a map to store totals for each year
                Map<String, Float> yearTotals = new HashMap<>();

                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String date = dateSnapshot.getKey();
                    String year = date.substring(0, 4); // YYYY format

                    float totalGrandTotal = 0;

                    for (DataSnapshot randomUidSnapshot : dateSnapshot.getChildren()) {
                        if (randomUidSnapshot.child("OtherInformation").exists()) {
                            String grandTotalString = randomUidSnapshot.child("OtherInformation").child("GrandTotalPrice").getValue(String.class);

                            if (grandTotalString != null) {
                                try {
                                    float grandTotal = Float.parseFloat(grandTotalString);
                                    totalGrandTotal += grandTotal;
                                } catch (NumberFormatException e) {
                                    // Handle parsing error
                                }
                            }
                        }
                    }

                    // Update yearTotals with the calculated total for the current year
                    yearTotals.put(year, yearTotals.getOrDefault(year, 0f) + totalGrandTotal);
                }

                for (Map.Entry<String, Float> entry : yearTotals.entrySet()) {
                    entries.add(new BarEntry(entries.size(), entry.getValue()));
                    yearLabels.add(entry.getKey());
                }

                BarDataSet dataSet = new BarDataSet(entries, "Total GrandTotalPrice by Year");
                dataSet.setValueTextSize(14f);
                BarData data = new BarData(dataSet);
                chart7.setData(data);

                // Set custom x-axis labels
                XAxis xAxis = chart7.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(yearLabels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1f); // Ensure only one label per year

                chart7.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });

    }




    private void setupMonthSpinnerBar() {
        Spinner monthSpinner = findViewById(R.id.monthSpinner);

        // Fetch months from Firebase
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> monthList = new ArrayList<>();

                // Iterate through dates and add unique months to the list
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String month = dateSnapshot.getKey().substring(4, 6) + dateSnapshot.getKey().substring(0, 4);
                    if (!monthList.contains(month)) {
                        monthList.add(month);
                    }
                }

                // Create an ArrayAdapter with the list of months
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        ChefOrderStats.this, android.R.layout.simple_spinner_item, monthList);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                monthSpinner.setAdapter(adapter);

                // Set a default selection or handle it as needed
                if (monthList.size() > 0) {
                    monthSpinner.setSelection(0);
                    ChefBarChartForMonth(monthList.get(0));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
                Log.e("FirebaseError", "Error fetching months: " + databaseError.getMessage());
            }
        });

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedMonth = parentView.getItemAtPosition(position).toString();
                ChefBarChartForMonth(selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }
    private void ChefBarChartForMonth(String selectedMonth) {
        DatabaseReference chefHistoryRef = FirebaseDatabase.getInstance().getReference("ChefHistory")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        chefHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {  //valuetosingle
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BarChart chart8 = findViewById(R.id.chart8);

                Map<String, Integer> dishQuantities = new HashMap<>();

                // Iterate through all dates and extract dish quantities for the selected month
                for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                    String month = dateSnapshot.getKey().substring(4, 6) + dateSnapshot.getKey().substring(0, 4);

                    if (month.equals(selectedMonth)) {
                        for (DataSnapshot randomUidSnapshot : dateSnapshot.child("Dishes").getChildren()) {
                            String dishName = randomUidSnapshot.child("DishName").getValue(String.class);
                            String dishQuantityString = randomUidSnapshot.child("DishQuantity").getValue(String.class);

                            // Parse DishQuantity as an integer (assuming it's stored as a string)
                            int dishQuantity = 0;
                            if (dishQuantityString != null && !dishQuantityString.isEmpty()) {
                                try {
                                    dishQuantity = Integer.parseInt(dishQuantityString);
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            // Update dishQuantities with the aggregated total for the current dish
                            dishQuantities.put(dishName, dishQuantities.getOrDefault(dishName, 0) + dishQuantity);
                        }
                    }
                }

                List<BarEntry> entries = new ArrayList<>();
                List<String> dishNames = new ArrayList<>();
                List<Integer> colors = new ArrayList<>();

                // Populate entries with dish quantities and colors
                int index = 0;
                for (Map.Entry<String, Integer> entry : dishQuantities.entrySet()) {
                    entries.add(new BarEntry(index, entry.getValue()));
                    dishNames.add(entry.getKey());
                    colors.add(getRandomColor(index));
                    index++;
                }

                BarDataSet dataSet = new BarDataSet(entries, "Dish Quantity by Dish Name");
                dataSet.setValueTextSize(12f);
                dataSet.setColors(colors);

                BarData data = new BarData(dataSet);
                chart8.setData(data);

                // Set custom x-axis labels
                XAxis xAxis = chart8.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(dishNames));
                xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
                xAxis.setGranularity(1f); // Ensure only one label per dish name

                chart8.invalidate();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error
            }
        });
    }
















    private String formatDateForChart(String date) {
        // Assuming date is in the format yyyymmdd
        return date.substring(6, 8) + "/" + date.substring(4, 6) + "/" + date.substring(0, 4);
    }
    private String getMonthYearLabel(String date) {
        // Assuming date is in the format yyyymmdd
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        return new DateFormatSymbols().getShortMonths()[month - 1] + year;
    }

//    private float getXValueForMonth(String monthYearLabel) {
//        // Convert monthYearLabel to a unique float value for x-axis position
//        // You can implement your logic here based on your specific requirements
//        // For simplicity, we'll use a hash code
//        return monthYearLabel.hashCode();
//    }
private float getXValueForMonth(String monthYearLabel) {
    // Assign consecutive indices to each month
    String[] months = new DateFormatSymbols().getShortMonths();
    for (int i = 0; i < months.length; i++) {
        if (monthYearLabel.startsWith(months[i])) {
            return i;
        }
    }
    return 0;  // Default to 0 if the month is not found (January)
}

//    private int getRandomColor(int index) {
//        // Generate a random color based on the index
//        Random rnd = new Random();
//        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//    }
    private int getRandomColor(int index) {
        // Ensure a good balance of brightness for the generated color
        Random rnd = new Random();
        int baseColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        // Adjust brightness to avoid very dark or very light colors
        float[] hsv = new float[3];
        Color.colorToHSV(baseColor, hsv);
        hsv[2] = 0.5f + 0.5f * rnd.nextFloat(); // Adjust brightness to be in the middle range

        return Color.HSVToColor(hsv);
    }


    private String formatMonthForChart(String monthYear) {
        // Assuming monthYear is in the format MMYYYY
        return monthYear.substring(0, 2) + "/" + monthYear.substring(2);
    }

}
