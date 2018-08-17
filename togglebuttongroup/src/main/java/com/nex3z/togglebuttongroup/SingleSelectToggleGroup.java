package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;

import com.nex3z.togglebuttongroup.button.MarkerButton;

public class SingleSelectToggleGroup extends ToggleButtonGroup {
    private static final String LOG_TAG = SingleSelectToggleGroup.class.getSimpleName();

    protected OnCheckedChangeListener mOnCheckedChangeListener;
    private int mCheckedId = View.NO_ID;

    public SingleSelectToggleGroup(Context context) {
        super(context);
    }

    public SingleSelectToggleGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int initialCheckedId = mInitialCheckedId != View.NO_ID ?
                mInitialCheckedId : mSilentInitialCheckedId;
        if (initialCheckedId != View.NO_ID) {
            setCheckedStateForView(initialCheckedId, true);
            setCheckedId(initialCheckedId, false);
        }
    }

    @Override
    public void addView(View child, int index, LayoutParams params) {
        if (child instanceof Checkable) {
            final Checkable checkable = (Checkable) child;
            if (checkable.isChecked()) {
                if (mCheckedId != -1) {
                    setCheckedStateForView(mCheckedId, false);
                }
                if (child.getId() == View.NO_ID) {
                    child.setId(generateIdForView(child));
                }
                setCheckedId(child.getId());
            }
            if (child instanceof MarkerButton) {
                ((MarkerButton) child).setRadioStyle(true);
            }
        }

        super.addView(child, index, params);
    }

    @Override
    protected <T extends View & Checkable> void onChildCheckedChange(T child, boolean isChecked) {
        if (isChecked) {
            if (mCheckedId != View.NO_ID && mCheckedId != child.getId()) {
                setCheckedStateForView(mCheckedId, false);
            }
            int id = child.getId();
            if (mSilentInitialCheckedId == id) {
                mSilentInitialCheckedId = View.NO_ID;
                setCheckedId(id, false);
            } else {
                setCheckedId(id);
            }
        }
    }

    public void check(int id) {
        if (id == mCheckedId) {
            return;
        }
        if (mCheckedId != -1) {
            setCheckedStateForView(mCheckedId, false);
        }
        setCheckedStateForView(id, true);
        setCheckedId(id, false);
    }

    public int getCheckedId() {
        return mCheckedId;
    }

    public void clearCheck() {
        check(-1);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    private void setCheckedId(int id) {
        setCheckedId(id, true);
    }
    
    private void setCheckedId(int id, boolean notify) {
        mCheckedId = id;
        if (notify) {
            notifyCheckedChange(mCheckedId);
        }
    }

    private void notifyCheckedChange(int id) {
        if (mOnCheckedChangeListener != null) {
            mOnCheckedChangeListener.onCheckedChanged(this, id);
        }
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SingleSelectToggleGroup group, int checkedId);
    }
}
