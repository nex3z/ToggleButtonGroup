package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;

public class MultiSelectActivity extends AppCompatActivity {
    private static final String LOG_TAG = MultiSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select);
        init();
    }

    private void init() {
        MultiSelectToggleGroup multi = findViewById(R.id.group_weekdays);
        multi.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                Log.v(LOG_TAG, "onCheckedStateChanged(): group.getCheckedIds() = " + group.getCheckedIds());
            }
        });
    }
}
