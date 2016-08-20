package com.nex3z.togglebuttongroup.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    ToggleButtonGroup mTogglGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTogglGroup = (ToggleButtonGroup) findViewById(R.id.toggle_button_group);
        setupMultiSelectToggleGroup();
    }

    private void setupMultiSelectToggleGroup() {
        String[] weekdays = getResources().getStringArray(R.array.weekdays);
        ArrayList<String> weekdaysList = new ArrayList<>(Arrays.asList(weekdays));
        mTogglGroup.setLabels(weekdaysList);
        mTogglGroup.setToggleButtonStateChangedListener(
                new ToggleButtonGroup.ToggleButtonStateChangedListener() {
                    @Override
                    public void onToggleButtonStateChanged(int position, boolean isEnabled) {
                        Log.v(LOG_TAG, "onToggleButtonStateChanged(): position = " + position
                                + ", isChecked = " + isEnabled);
                        Set<Integer> checkedPositions = mTogglGroup.getCheckedPositions();
                        Log.v(LOG_TAG, "onToggleButtonStateChanged(): checkedPositions = "
                                + checkedPositions);
                    }
        });
    }
}
