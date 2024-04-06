package com.bassyboy5.picker;

import android.content.Context;
import android.content.SharedPreferences;


import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class CalculationHistoryManager {
    private static final String PREF_NAME = "CalculationHistory";
    private static final String KEY_HISTORY = "history";

    private SharedPreferences sharedPreferences;
    private Gson gson;

    public CalculationHistoryManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void saveCalculation(CalculationEntry entry) {
        List<CalculationEntry> history = getCalculationHistory();
        history.add(entry);
        String json = gson.toJson(history);
        sharedPreferences.edit().putString(KEY_HISTORY, json).apply();
    }

    public List<CalculationEntry> getCalculationHistory() {
        String json = sharedPreferences.getString(KEY_HISTORY, "");
        List<CalculationEntry> history = new ArrayList<>();
        if (!json.isEmpty()) {
            CalculationEntry[] entries = gson.fromJson(json, CalculationEntry[].class);
            for (CalculationEntry entry : entries) {
                history.add(entry);
            }
        }
        return history;
    }
}
