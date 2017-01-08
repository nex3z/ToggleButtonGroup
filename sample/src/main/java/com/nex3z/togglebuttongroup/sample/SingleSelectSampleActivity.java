package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SingleSelectSampleActivity extends AppCompatActivity {
    private static final String LOG_TAG = SingleSelectSampleActivity.class.getSimpleName();
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select_sample);
        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setupSingleSelectToggleGroup();
    }

    private void setupSingleSelectToggleGroup() {
        SingleSelectToggleGroup singleSelect =
                (SingleSelectToggleGroup) findViewById(R.id.single_selection_group);
        singleSelect.setOnCheckedChangeListener(new ToggleButtonGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChange(int position, boolean isChecked) {
                Log.v(LOG_TAG, "onCheckedChange(): position = " + position
                        + ", isChecked = " + isChecked);
            }
        });

        String[] choices = getResources().getStringArray(R.array.choices);
        List<String> choicesList = new ArrayList<>(Arrays.asList(choices));
        singleSelect.setButtons(choicesList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
