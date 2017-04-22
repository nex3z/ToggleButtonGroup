package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

import com.nex3z.togglebuttongroup.button.OnCheckedChangeListener;
import com.nex3z.togglebuttongroup.button.ToggleButton;

public abstract class ToggleButtonGroup extends FlowLayout {
    private static final String LOG_TAG = ToggleButtonGroup.class.getSimpleName();

    protected int mInitialCheckedId = View.NO_ID;
    private OnCheckedChangeListener mCheckedStateListener;
    private PassThroughHierarchyChangeListener mPassThroughListener;

    protected abstract <T extends View & Checkable> void onChildCheckedChange(T child, boolean isChecked);

    public ToggleButtonGroup(Context context) {
        this(context, null);
    }

    public ToggleButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ToggleButtonGroup, 0, 0);
        try {
            mInitialCheckedId = a.getResourceId(R.styleable.ToggleButtonGroup_tbgCheckedButton, View.NO_ID);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mCheckedStateListener = new CheckedStateTracker();
        mPassThroughListener = new PassThroughHierarchyChangeListener();
        super.setOnHierarchyChangeListener(mPassThroughListener);
    }

    @Override
    public void setOnHierarchyChangeListener(OnHierarchyChangeListener listener) {
        mPassThroughListener.mOnHierarchyChangeListener = listener;
    }

    protected void setCheckedStateForView(int viewId, boolean checked) {
        View target = findViewById(viewId);
        if (target != null && target instanceof Checkable) {
            ((Checkable) target).setChecked(checked);
        }
    }

    protected void toggleCheckedStateForView(int viewId) {
        View target = findViewById(viewId);
        if (target != null && target instanceof Checkable) {
            ((Checkable) target).toggle();
        }
    }

    private class CheckedStateTracker implements OnCheckedChangeListener {
        @Override
        public <T extends View & Checkable> void onCheckedChanged(T view, boolean isChecked) {
            onChildCheckedChange(view, isChecked);
        }
    }

    private class PassThroughHierarchyChangeListener implements
            ViewGroup.OnHierarchyChangeListener {
        private ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;

        public void onChildViewAdded(View parent, View child) {
            if (parent == ToggleButtonGroup.this && child instanceof ToggleButton) {
                if (child.getId() == View.NO_ID) {
                    child.setId(generateIdForView(child));
                }
                ((ToggleButton) child).setOnCheckedChangeWidgetListener(mCheckedStateListener);

            }
            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewAdded(parent, child);
            }
        }

        public void onChildViewRemoved(View parent, View child) {
            if (parent == ToggleButtonGroup.this && child instanceof ToggleButton) {
                ((ToggleButton) child).setOnCheckedChangeWidgetListener(null);
            }

            if (mOnHierarchyChangeListener != null) {
                mOnHierarchyChangeListener.onChildViewRemoved(parent, child);
            }
        }
    }

    protected int generateIdForView(View view) {
        return android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1
                ? view.hashCode()
                : generateViewId();
    }

}
