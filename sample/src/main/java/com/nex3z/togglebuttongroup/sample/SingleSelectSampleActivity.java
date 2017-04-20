package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

public class SingleSelectSampleActivity extends AppCompatActivity {
    private static final String LOG_TAG = SingleSelectSampleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select_sample);
        init();
    }

    private void init() {
        SingleSelectToggleGroup single = (SingleSelectToggleGroup) findViewById(R.id.group_choices);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                Log.v(LOG_TAG, "onCheckedChanged(): checkedId = " + checkedId);
            }
        });
    }
}
