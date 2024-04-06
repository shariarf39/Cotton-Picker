package com.bassyboy5.picker;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

     EditText widthEditText, yieldEditText, speedEditText;
   TextView targetBalesTextView, targetSpeedTextView, history;
   ImageView copy;


    private CalculationHistoryManager historyManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);
/*
      ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
*/


        widthEditText = findViewById(R.id.widthEditText);
        yieldEditText = findViewById(R.id.yieldEditText);
        speedEditText = findViewById(R.id.speedEditText);
        targetBalesTextView = findViewById(R.id.targetBalesTextView);
        targetSpeedTextView = findViewById(R.id.targetSpeedTextView);
        history = findViewById(R.id.history);
        copy = findViewById(R.id.copy);

        historyManager = new CalculationHistoryManager(this);

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                String label = "Your label"; // Set your label here
                String textToCopy = targetBalesTextView.getText().toString() + "\n" + targetSpeedTextView.getText().toString();
                ClipData clip = ClipData.newPlainText(label, textToCopy);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(Home.this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });


        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, History.class));
            }
        });
    }

    public void calculate(View view) {

        if(TextUtils.isEmpty(widthEditText.getText().toString())){
            widthEditText.setError("Please Enter Width");
            widthEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(yieldEditText.getText().toString())){
            yieldEditText.setError("Please Enter yield");
            yieldEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(speedEditText.getText().toString())){
            speedEditText.setError("Please Enter Speed");
            speedEditText.requestFocus();
            return;
        }

        double width = Double.parseDouble(widthEditText.getText().toString());
        double yield = Double.parseDouble(yieldEditText.getText().toString());
        double speed = Double.parseDouble(speedEditText.getText().toString());



        double targetBalesPerHour = (speed * width * yield) / 10;
        double targetSpeed = (targetBalesPerHour * 10) / (width * yield);

        targetBalesTextView.setText("Target Bales per Hour: " + targetBalesPerHour);
        targetSpeedTextView.setText("Target Speed: " + targetSpeed);

    /*    // Save calculation to history
        CalculationEntry entry = new CalculationEntry();
        entry.setWidth(width);
        entry.setYield(yield);
        entry.setSpeed(speed);
        entry.setTargetBalesPerHour(targetBalesPerHour);
        entry.setTargetSpeed(targetSpeed);
        historyManager.saveCalculation(entry);*/


        CalculationEntry entry = new CalculationEntry(width, yield, speed, targetBalesPerHour, targetSpeed);
        historyManager.saveCalculation(entry);



    }
}