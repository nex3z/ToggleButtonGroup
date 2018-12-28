package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

public class SingleSelectActivity extends AppCompatActivity {
    private static final String LOG_TAG = SingleSelectActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_select);
        init();
    }

    private void init() {
        SingleSelectToggleGroup single = findViewById(R.id.group_choices);
        single.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                Log.v(LOG_TAG, "onCheckedChanged(): checkedId = " + checkedId);
            }
        });
    }
}
