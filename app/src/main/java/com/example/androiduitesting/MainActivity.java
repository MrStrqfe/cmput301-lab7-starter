package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Declare variables
    ListView cityList;
    EditText newName;
    LinearLayout nameField;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMainLayout();
    }

    // Function to set up the main layout and its buttons
    private void setupMainLayout() {
        nameField = findViewById(R.id.field_nameEntry);
        newName = findViewById(R.id.editText_name);
        cityList = findViewById(R.id.city_list);

        if (dataList == null) {
            dataList = new ArrayList<>();
        }

        if (cityAdapter == null) {
            cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        }

        cityList.setAdapter(cityAdapter);

        // City click event
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = dataList.get(position);
                openCityLayout(selectedCity);
            }
        });

        // Add button
        final Button addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(v -> nameField.setVisibility(View.VISIBLE));

        // Confirm button
        final Button confirmButton = findViewById(R.id.button_confirm);
        confirmButton.setOnClickListener(v -> {
            String cityName = newName.getText().toString().trim();
            if (!cityName.isEmpty()) {
                cityAdapter.add(cityName);
                newName.getText().clear();
            }
            nameField.setVisibility(View.INVISIBLE);
        });

        // Delete button
        final Button deleteButton = findViewById(R.id.button_clear);
        deleteButton.setOnClickListener(v -> cityAdapter.clear());
    }

    // Function to set up the city layout when one is clicked
    private void openCityLayout(String cityName) {
        setContentView(R.layout.activity_list);

        TextView cityNameText = findViewById(R.id.cityNameText);
        cityNameText.setText(cityName);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Go back to main layout
            setContentView(R.layout.activity_main);
            setupMainLayout(); // reinitialize views & listeners
        });
    }
}
