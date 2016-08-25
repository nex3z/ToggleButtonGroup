package com.nex3z.togglebuttongroup.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMultiSelectToggleGroup();
        setupManualSelectToggleGroup();
    }

    private void setupMultiSelectToggleGroup() {
        final ToggleButtonGroup toggleGroup =
                (ToggleButtonGroup) findViewById(R.id.toggle_button_group);

        setupWeekdays(toggleGroup);
        toggleGroup.setToggleButtonStateChangedListener(
                new ToggleButtonGroup.ToggleButtonStateChangedListener() {
                    @Override
                    public void onToggleButtonStateChanged(int position, boolean isEnabled) {
                        Log.v(LOG_TAG, "onToggleButtonStateChanged(): position = " + position
                                + ", isChecked = " + isEnabled);
                        Set<Integer> checkedPositions = toggleGroup.getCheckedPositions();
                        Log.v(LOG_TAG, "onToggleButtonStateChanged(): checkedPositions = "
                                + checkedPositions);
                    }
        });
    }

    private void setupManualSelectToggleGroup() {
        final ToggleButtonGroup toggleGroup =
                (ToggleButtonGroup) findViewById(R.id.toggle_button_group_2);

        toggleGroup.setCheckedBackground(R.drawable.ic_bookmark_48dp);

        setupWeekdays(toggleGroup);

        Set<Integer> positions = new HashSet<>();
        positions.add(1);
        positions.add(3);
        positions.add(5);

        toggleGroup.setCheckedPositions(positions);
    }

    private void setupWeekdays(ToggleButtonGroup toggleGroup) {
        String[] weekdays = getResources().getStringArray(R.array.weekdays);
        ArrayList<String> weekdaysList = new ArrayList<>(Arrays.asList(weekdays));
        toggleGroup.setLabels(weekdaysList);
    }
}
