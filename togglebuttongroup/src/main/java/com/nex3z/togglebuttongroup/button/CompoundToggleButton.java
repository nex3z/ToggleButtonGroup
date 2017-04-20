package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.nex3z.togglebuttongroup.OnCheckedChangeListener;

public abstract class CompoundToggleButton extends FrameLayout implements ToggleButton {
    private static final String LOG_TAG = CompoundToggleButton.class.getSimpleName();

    private boolean mChecked;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedWidgetListener;

    public CompoundToggleButton(Context context) {
        this(context, null);
    }

    public CompoundToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedWidgetListener = listener;
    }

    @Override @CallSuper
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;

            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedWidgetListener != null) {
                mOnCheckedWidgetListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override @CallSuper
    public void toggle() {
        setChecked(!mChecked);
    }
}
