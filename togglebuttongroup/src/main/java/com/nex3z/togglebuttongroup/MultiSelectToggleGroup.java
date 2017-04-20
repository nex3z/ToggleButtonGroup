package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

public class MultiSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = MultiSelectToggleGroup.class.getSimpleName();

    private OnCheckedStateChangeListener mOnCheckedStateChangeListener;

    public MultiSelectToggleGroup(Context context) {
        super(context);
    }

    public MultiSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mInitialCheckedId != View.NO_ID) {
            setCheckedStateForView(mInitialCheckedId, true);
        }
    }

    @Override
    protected <T extends View & Checkable> void onChildCheckedChange(T child, boolean isChecked) {
        notifyCheckedStateChange(child.getId(), isChecked);
    }

    public void check(int id) {
        setCheckedStateForView(id, true);
        notifyCheckedStateChange(id, true);
    }

    public void uncheck(int id) {
        setCheckedStateForView(id, false);
        notifyCheckedStateChange(id, false);
    }

    public void toggle(int id) {
        toggleCheckedStateForView(id);
        View target = findViewById(id);
        if (target instanceof Checkable) {
            notifyCheckedStateChange(id, ((Checkable) target).isChecked());
        }
    }

    public void setOnCheckedChangeListener(OnCheckedStateChangeListener listener) {
        mOnCheckedStateChangeListener = listener;
    }

    private void notifyCheckedStateChange(int id, boolean isChecked) {
        if (mOnCheckedStateChangeListener != null) {
            mOnCheckedStateChangeListener.onCheckedStateChanged(this, id, isChecked);
        }
    }

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked);
    }
}
