package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

public class FlowLabelActivity extends AppCompatActivity {
    private static final String LOG_TAG = FlowLabelActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_label);
        init();
    }

    private void init() {
        SingleSelectToggleGroup singleWeekdays = findViewById(R.id.group_weekdays);
        singleWeekdays.setOnCheckedChangeListener(new SingleSelectToggleGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
                Log.v(LOG_TAG, "onCheckedChanged(): checkedId = " + checkedId);
            }
        });

        MultiSelectToggleGroup multiDummy = findViewById(R.id.group_dummy);
        String[] dummyText = getResources().getStringArray(R.array.dummy_text);
        for (String text : dummyText) {
            LabelToggle toggle = new LabelToggle(this);
            toggle.setText(text);
            multiDummy.addView(toggle);
        }
    }
}
