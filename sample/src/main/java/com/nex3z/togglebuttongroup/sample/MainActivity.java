package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMultiSelectToggleGroup();
        setupSingleSelectToggleGroup();
    }

    private void setupMultiSelectToggleGroup() {
        final MultiSelectToggleGroup multiSelect =
                (MultiSelectToggleGroup) findViewById(R.id.multi_selection_group);
        multiSelect.setOnCheckedStateChangeListener(new ToggleButtonGroup.OnCheckedStateChangeListener() {
            @Override
            public void onToggleStateChange(int position, boolean isChecked) {
                Set<Integer> checkedPositions =  multiSelect.getCheckedPositions();
                Log.v(LOG_TAG, "setupMultiSelectToggleGroup(): position = " + position
                        + ", isChecked = " + isChecked
                        + ", checkedPositions = " + checkedPositions);
            }
        });

        String[] weekdays = getResources().getStringArray(R.array.weekdays);
        ArrayList<String> weekdaysList = new ArrayList<>(Arrays.asList(weekdays));
        multiSelect.setButtons(weekdaysList);
    }

    private void setupSingleSelectToggleGroup() {
        final SingleSelectToggleGroup singleSelect =
                (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
        singleSelect.setOnCheckedStateChangeListener(new ToggleButtonGroup.OnCheckedStateChangeListener() {
            @Override
            public void onToggleStateChange(int position, boolean isChecked) {
                Log.v(LOG_TAG, "setupSingleSelectToggleGroup(): position = " + position
                        + ", isChecked = " + isChecked);
            }
        });

        String[] weekdays = getResources().getStringArray(R.array.choices);
        ArrayList<String> weekdaysList = new ArrayList<>(Arrays.asList(weekdays));
        singleSelect.setButtons(weekdaysList);
    }

}
