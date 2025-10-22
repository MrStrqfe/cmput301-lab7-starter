package com.example.androiduitesting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CityDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Get the city name from the intent
        String cityName = getIntent().getStringExtra("CITY_NAME");

        // Find views
        TextView cityNameText = findViewById(R.id.cityNameText);
        Button backButton = findViewById(R.id.backButton);

        // Set the city name in the header
        if (cityName != null) {
            cityNameText.setText(cityName);
        }

        // Back button functionality
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close this activity and return to MainActivity
            }
        });
    }
}