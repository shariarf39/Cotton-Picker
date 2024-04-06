package com.bassyboy5.picker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class History extends AppCompatActivity {

   private EditText widthEditText;
   private EditText yieldEditText;
   private EditText speedEditText;
    private TextView targetBalesTextView;
    private TextView targetSpeedTextView;
    private RecyclerView recyclerView;
    private CalculationHistoryAdapter adapter;

    private CalculationHistoryManager historyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


  /*      widthEditText = findViewById(R.id.widthEditText);
        yieldEditText = findViewById(R.id.yieldEditText);
        speedEditText = findViewById(R.id.speedEditText);
        targetBalesTextView = findViewById(R.id.targetBalesTextView);
        targetSpeedTextView = findViewById(R.id.targetSpeedTextView);*/
        recyclerView = findViewById(R.id.recyclerView);

        historyManager = new CalculationHistoryManager(this);
        adapter = new CalculationHistoryAdapter(historyManager.getCalculationHistory());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    public void calculate(View view) {
        double width = Double.parseDouble(widthEditText.getText().toString());
        double yield = Double.parseDouble(yieldEditText.getText().toString());
        double speed = Double.parseDouble(speedEditText.getText().toString());

        double targetBalesPerHour = (speed * width * yield) / 10;
        double targetSpeed = (targetBalesPerHour * 10) / (width * yield);

        targetBalesTextView.setText("Target Bales per Hour: " + targetBalesPerHour);
        targetSpeedTextView.setText("Target Speed: " + targetSpeed);

        // Save calculation to history
        CalculationEntry entry = new CalculationEntry();
        entry.setWidth(width);
        entry.setYield(yield);
        entry.setSpeed(speed);
        entry.setTargetBalesPerHour(targetBalesPerHour);
        entry.setTargetSpeed(targetSpeed);
        historyManager.saveCalculation(entry);

        // Refresh RecyclerView

        adapter.setCalculationHistory(historyManager.getCalculationHistory());

        adapter.notifyDataSetChanged();
    }

    private static class CalculationHistoryAdapter extends RecyclerView.Adapter<CalculationHistoryAdapter.ViewHolder> {
        private List<CalculationEntry> calculationHistory;

        public CalculationHistoryAdapter(List<CalculationEntry> calculationHistory) {
            this.calculationHistory = calculationHistory;
        }

        public void setCalculationHistory(List<CalculationEntry> calculationHistory) {
            this.calculationHistory = calculationHistory;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calculation_history, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            CalculationEntry entry = calculationHistory.get(position);
            if (Double.isNaN(entry.getYield())) {
                holder.historyItem.setText("No Data Here");
            } else {

                holder.historyItem.setText("Width: " + entry.getWidth() +
                        ", Yield: " + entry.getYield() +
                        ", Speed: " + entry.getSpeed() + ",\n" +
                        "Target Bales per Hour: " + entry.getTargetBalesPerHour() +
                        ", Target Speed: " + entry.getTargetSpeed());
            }
        }

        @Override
        public int getItemCount() {
            return calculationHistory.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public TextView historyItem;

            public ViewHolder(View itemView) {
                super(itemView);
                historyItem = itemView.findViewById(R.id.historyItem);
            }
        }
    }
}