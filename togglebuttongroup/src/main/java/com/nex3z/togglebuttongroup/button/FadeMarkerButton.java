package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.util.AttributeSet;

import com.nex3z.togglebuttongroup.R;

public class FadeMarkerButton extends MarkerButton {
    private static final String LOG_TAG = FadeMarkerButton.class.getSimpleName();

    public FadeMarkerButton(Context context) {
        this(context, null);
    }

    public FadeMarkerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mIvBg.setImageResource(R.drawable.ic_circle);
        mTvText.setBackgroundDrawable(null);
        mRadioStyle = true;
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        mIvBg.setVisibility(checked ? VISIBLE : INVISIBLE);
        mTvText.setTextColor(checked ? mCheckedTextColor : mUncheckedTextColor);
    }
}
