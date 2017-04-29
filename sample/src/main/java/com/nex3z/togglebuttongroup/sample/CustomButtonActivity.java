package com.nex3z.togglebuttongroup.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup;

public class CustomButtonActivity extends AppCompatActivity {
    private static final String LOG_TAG = CustomButtonActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_button);
        init();
    }


    private void init() {
        SingleSelectToggleGroup singleRadioButton =
                (SingleSelectToggleGroup) findViewById(R.id.group_single_radiobutton);
        singleRadioButton.setOnCheckedChangeListener(new SingleSelectListener());

        MultiSelectToggleGroup multiCustomCompoundButton =
                (MultiSelectToggleGroup) findViewById(R.id.group_multi_custom_compoundbutton);
        multiCustomCompoundButton.setOnCheckedChangeListener(new MultiSelectListener());

        MultiSelectToggleGroup multiCustomToggleButton =
                (MultiSelectToggleGroup) findViewById(R.id.group_multi_custom_togglebutton);
        multiCustomToggleButton.setOnCheckedChangeListener(new MultiSelectListener());

        SingleSelectToggleGroup singleCompoundToggleButton =
                (SingleSelectToggleGroup) findViewById(R.id.group_single_custom_compoundtogglebutton);
        singleCompoundToggleButton.setOnCheckedChangeListener(new SingleSelectListener());
    }

    private static class SingleSelectListener implements SingleSelectToggleGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(SingleSelectToggleGroup group, int checkedId) {
            Log.v(LOG_TAG, "onCheckedChanged(): " + checkedId);
        }
    }

    private static class MultiSelectListener implements MultiSelectToggleGroup.OnCheckedStateChangeListener {
        @Override
        public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
            Log.v(LOG_TAG, "onCheckedStateChanged(): " + checkedId + ", isChecked = " + isChecked);
        }
    }

}
