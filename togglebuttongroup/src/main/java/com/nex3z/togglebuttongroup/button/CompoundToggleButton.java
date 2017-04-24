package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public abstract class CompoundToggleButton extends FrameLayout implements ToggleButton {
    private static final String LOG_TAG = CompoundToggleButton.class.getSimpleName();

    private boolean mChecked;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedWidgetListener;

    public CompoundToggleButton(Context context) {
        super(context);
    }

    public CompoundToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
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
