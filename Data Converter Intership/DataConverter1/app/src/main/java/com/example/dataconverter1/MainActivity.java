package com.example.dataconverter1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText inputEditText;
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private Button convertButton;
    private TextView resultTextView;

    private String[] dataUnits = {"Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes"};
    private double[] multipliers = {1, 1024, 1048576, 1073741824, 1099511627776.0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataUnits);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        fromSpinner.setOnItemSelectedListener(this);
        toSpinner.setOnItemSelectedListener(this);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
    }

    private void convert() {
        double inputValue = Double.parseDouble(inputEditText.getText().toString());
        int fromIndex = fromSpinner.getSelectedItemPosition();
        int toIndex = toSpinner.getSelectedItemPosition();

        double result = inputValue * multipliers[fromIndex] / multipliers[toIndex];

        resultTextView.setText(String.format("%.2f", result) + " " + dataUnits[toIndex]);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (inputEditText.getText().toString().isEmpty()) {
            return;
        }
        convert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

