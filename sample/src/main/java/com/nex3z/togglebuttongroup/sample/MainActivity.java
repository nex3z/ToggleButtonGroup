package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        multiSelect.setOnCheckedPositionChangeListener(
                new ToggleButtonGroup.OnCheckedPositionChangeListener() {
                    @Override
                    public void onCheckedPositionChange(Set<Integer> checkedPositions) {
                        Log.v(LOG_TAG, "onCheckedPositionChange(): checkedPositions = "
                                + checkedPositions);
                    }
        });
    }

    private void setupSingleSelectToggleGroup() {
        final SingleSelectToggleGroup singleSelect =
                (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
        singleSelect.setOnCheckedChangeListener(new ToggleButtonGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(int position, boolean isChecked) {
                Log.v(LOG_TAG, "setupSingleSelectToggleGroup(): position = " + position
                        + ", isChecked = " + isChecked);
            }
        });

        String[] choices = getResources().getStringArray(R.array.choices);
        List<String> choicesList = new ArrayList<>(Arrays.asList(choices));
        singleSelect.setButtons(choicesList);
    }

}
