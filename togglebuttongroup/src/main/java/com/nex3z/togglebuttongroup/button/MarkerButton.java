package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.togglebuttongroup.R;

public class MarkerButton extends CompoundToggleButton implements ToggleButton {
    private static final String LOG_TAG = MarkerButton.class.getSimpleName();

    protected TextView mTvText;
    protected ImageView mIvBg;
    protected int mCheckedTextColor;
    protected int mUncheckedTextColor;
    protected boolean mRadioStyle;

    public MarkerButton(Context context) {
        this(context, null);
    }

    public MarkerButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_marker_button, this, true);
        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mTvText = (TextView) findViewById(R.id.tv_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.MarkerButton, 0, 0);
        try {
            CharSequence text = a.getText(R.styleable.MarkerButton_android_text);
            mTvText.setText(text);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mCheckedTextColor = ContextCompat.getColor(getContext(), android.R.color.white);
        mUncheckedTextColor = ContextCompat.getColor(getContext(), android.R.color.black);
        mTvText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    @Override
    public void toggle() {
        // Do not allow toggle to unchecked state
        if (mRadioStyle && isChecked()) {
            return;
        }
        super.toggle();
    }
}
