package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.Set;

public class MultiSelectSampleActivity extends AppCompatActivity {
    private static final String LOG_TAG = MultiSelectSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_sample);
        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setupMultiSelectToggleGroup();
    }

    private void setupMultiSelectToggleGroup() {
        MultiSelectToggleGroup multiSelect =
                (MultiSelectToggleGroup) findViewById(R.id.multi_selection_group);
        multiSelect.setOnCheckedPositionChangeListener(
                new ToggleButtonGroup.OnCheckedPositionChangeListener() {
                    @Override
                    public void onCheckedPositionChange(Set<Integer> checkedPositions) {
                        Log.v(LOG_TAG, "onCheckedPositionChange(): checkedPositions = "
                                + checkedPositions);
                        Toast.makeText(MultiSelectSampleActivity.this,
                                "Checked positions: " + checkedPositions.toString(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
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
