package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.util.AttributeSet;

import com.nex3z.togglebuttongroup.R;

public class LabelButton extends MarkerButton implements ToggleButton {
    private static final String LOG_TAG = LabelButton.class.getSimpleName();

    public LabelButton(Context context) {
        this(context, null);
    }

    public LabelButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIvBg.setImageResource(R.drawable.bg_label_checked);
        mTvText.setBackgroundResource(R.drawable.bg_label_unchecked);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        mIvBg.setVisibility(checked ? VISIBLE : INVISIBLE);
        mTvText.setTextColor(checked ? mCheckedTextColor : mUncheckedTextColor);
    }
}
