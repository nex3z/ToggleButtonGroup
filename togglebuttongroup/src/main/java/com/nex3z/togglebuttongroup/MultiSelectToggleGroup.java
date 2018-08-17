package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.nex3z.togglebuttongroup.button.ToggleButton;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MultiSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = MultiSelectToggleGroup.class.getSimpleName();

    private OnCheckedStateChangeListener mOnCheckedStateChangeListener;
    private int mMaxSelectCount = -1;

    public MultiSelectToggleGroup(Context context) {
        super(context);
    }

    public MultiSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.MultiSelectToggleGroup, 0, 0);
        try {
            mMaxSelectCount = a.getInt(R.styleable.MultiSelectToggleGroup_tbgMaxSelect, -1);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int initialCheckedId = mInitialCheckedId != View.NO_ID ?
                mInitialCheckedId : mSilentInitialCheckedId;
        if (initialCheckedId != View.NO_ID) {
            setCheckedStateForView(initialCheckedId, true);
        }
    }

    @Override
    protected <T extends View & Checkable> void onChildCheckedChange(T child, boolean isChecked) {
        checkSelectCount();

        if (mSilentInitialCheckedId == child.getId()) {
            mSilentInitialCheckedId = View.NO_ID;
        } else {
            notifyCheckedStateChange(child.getId(), isChecked);
        }
    }

    public void check(int id) {
        setCheckedStateForView(id, true);
    }

    public void check(int id, boolean checked) {
        setCheckedStateForView(id, checked);
    }

    public void clearCheck() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ToggleButton) {
                ((ToggleButton) child).setChecked(false);
            }
        }
    }

    public Set<Integer> getCheckedIds() {
        Set<Integer> ids = new LinkedHashSet<>();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof ToggleButton && ((ToggleButton) child).isChecked()) {
                ids.add(child.getId());
            }
        }
        return ids;
    }

    public void toggle(int id) {
        toggleCheckedStateForView(id);
    }

    public void setOnCheckedChangeListener(OnCheckedStateChangeListener listener) {
        mOnCheckedStateChangeListener = listener;
    }

    public int getMaxSelectCount() {
        return mMaxSelectCount;
    }

    public void setMaxSelectCount(int maxSelectCount) {
        mMaxSelectCount = maxSelectCount;
        checkSelectCount();
    }

    private void checkSelectCount() {
        if (mMaxSelectCount < 0) {
            return;
        }

        List<View> uncheckedViews = new ArrayList<>();
        int checkedCount = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof Checkable) {
                if (((Checkable) view).isChecked()) {
                    checkedCount++;
                } else {
                    uncheckedViews.add(view);
                }
            }
        }
        if (checkedCount >= mMaxSelectCount) {
            for (View view : uncheckedViews) {
                view.setEnabled(false);
            }
        } else {
            for (View view : uncheckedViews) {
                view.setEnabled(true);
            }
        }
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
