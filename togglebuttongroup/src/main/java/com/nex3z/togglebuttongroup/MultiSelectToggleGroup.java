package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.util.AttributeSet;

public class MultiSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = MultiSelectToggleGroup.class.getSimpleName();

    public MultiSelectToggleGroup(Context context) {
        this(context, null);
    }

    public MultiSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onToggleButtonClicked(int position) {
        ToggleButton button = mButtons.get(position);
        boolean isChecked = button.changeCheckedState();
        if (mListener != null) {
            mListener.onToggleStateChange(position, isChecked);
        }
    }

}
