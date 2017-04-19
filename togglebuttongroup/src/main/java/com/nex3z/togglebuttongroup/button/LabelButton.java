package com.nex3z.togglebuttongroup.button;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nex3z.togglebuttongroup.OnCheckedChangeListener;
import com.nex3z.togglebuttongroup.R;

public class LabelButton extends FrameLayout implements ToggleButton {
    private static final String LOG_TAG = LabelButton.class.getSimpleName();

    private TextView mTvText;
    private ImageView mIvBg;
    private boolean mChecked;
    private boolean mBroadcasting;
    private OnCheckedChangeListener mOnCheckedWidgetListener;
    private int mCheckedTextColor;
    private int mUnchecnedTextColor;

    public LabelButton(Context context) {
        this(context, null);
    }

    public LabelButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_label_button, this, true);
        mIvBg = (ImageView) findViewById(R.id.iv_bg);
        mTvText = (TextView) findViewById(R.id.tv_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.LabelButton, 0, 0);
        try {
            CharSequence text = a.getText(R.styleable.LabelButton_android_text);
            mTvText.setText(text);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mCheckedTextColor = ContextCompat.getColor(getContext(), android.R.color.white);
        mUnchecnedTextColor = ContextCompat.getColor(getContext(), android.R.color.black);
        mTvText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    @Override
    public void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedWidgetListener = listener;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            mIvBg.setVisibility(checked ? VISIBLE : INVISIBLE);
            mTvText.setTextColor(checked ? mCheckedTextColor : mUnchecnedTextColor);

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

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
