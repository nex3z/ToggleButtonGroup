package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Set;

public class MultiSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = MultiSelectToggleGroup.class.getSimpleName();

    public MultiSelectToggleGroup(Context context) {
        this(context, null);
    }

    public MultiSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onButtonClick(int position) {
        ToggleButton button = mButtons.get(position);
        boolean isChecked = button.isChecked();
        isChecked = !isChecked;
        button.setChecked(isChecked, isAnimationEnabled());
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChange(position, isChecked);
        }
        if (mOnCheckedPositionChangeListener != null) {
            mOnCheckedPositionChangeListener.onCheckedPositionChange(getCheckedPositions());
        }
    }

    /**
     * Check buttons at the positions from the given set.
     *
     * @param checkedPositions positions to be checked
     */
    public void setCheckedPositions(Set<Integer> checkedPositions) {
        uncheckAll();
        for (int position : checkedPositions) {
            setCheckedAt(position, true);
        }
    }

}
