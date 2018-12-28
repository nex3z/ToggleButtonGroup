package com.nex3z.togglebuttongroup.sample.button;

import android.content.Context;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.nex3z.togglebuttongroup.button.OnCheckedChangeListener;
import com.nex3z.togglebuttongroup.button.ToggleButton;

public class CustomToggleButton extends AppCompatButton implements ToggleButton {
    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };

    private boolean mChecked;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private boolean mBroadcasting;

    public CustomToggleButton(Context context) {
        this(context, null);
    }

    public CustomToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }
}
