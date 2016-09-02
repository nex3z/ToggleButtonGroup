package com.nex3z.togglebuttongroup;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ToggleButtonGroup extends LinearLayout implements View.OnClickListener {
    private static final String LOG_TAG = ToggleButtonGroup.class.getSimpleName();

    private static final float DEFAULT_TEXT_SIZE = 50;
    private static final float DEFAULT_BUTTON_SIZE = 120;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    private static final long DEFAULT_ANIMATION_DURATION = 150;

    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mContainer;

    private Drawable mCheckedBackground;
    private float mButtonSize;
    private int mTextColor;
    private float mTextSize;
    private boolean mIsAnimationEnabled;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private String mTextButton1;
    private String mTextButton2;
    protected ArrayList<ToggleButton> mButtons;

    protected OnCheckedStateChangeListener mListener;

    public interface OnCheckedStateChangeListener {
        void onToggleStateChange(int position, boolean isChecked);
    }

    protected abstract void onToggleButtonClicked(int position);

    public ToggleButtonGroup(Context context) {
        this(context, null);
    }

    public ToggleButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ToggleButtonOptions,
                0, 0);

        try {
            mContext = context;

            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInflater.inflate(R.layout.toggle_button_group, this, true);
            mContainer = (LinearLayout) findViewById(R.id.toggle_button_container);

            mCheckedBackground = a.getDrawable(R.styleable.ToggleButtonOptions_checkedBackground);

            mTextSize = a.getDimensionPixelSize(R.styleable.ToggleButtonOptions_android_textSize, (int)dpToPx(DEFAULT_TEXT_SIZE));
            mButtonSize = a.getDimension(R.styleable.ToggleButtonOptions_buttonSize, dpToPx(DEFAULT_BUTTON_SIZE));
            mTextColor = a.getColor(R.styleable.ToggleButtonOptions_textColor, DEFAULT_TEXT_COLOR);
            mIsAnimationEnabled = a.getBoolean(R.styleable.ToggleButtonOptions_enableAnimation, false);

            mTextButton1 = a.getString(R.styleable.ToggleButtonOptions_textButton1);
            mTextButton2 = a.getString(R.styleable.ToggleButtonOptions_textButton2);

            mButtons = new ArrayList<>();

            List<String> attrLabels = new ArrayList<>();
            if (mTextButton1 != null && !mTextButton1.isEmpty()) {
                attrLabels.add(mTextButton1);
            }
            if (mTextButton2 != null && !mTextButton2.isEmpty()) {
                attrLabels.add(mTextButton2);
            }
            if (!attrLabels.isEmpty()) {
                setButtons(attrLabels);
            }

        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View view) {
        int position = mContainer.indexOfChild(view);
        onToggleButtonClicked(position);
    }

    public void setOnToggleStateChangeListener(OnCheckedStateChangeListener listener) {
        mListener = listener;
    }

    public void setButtons(List<String> text) {
        clearButtons();

        for (String str : text) {
            addButton(str);
        }
    }

    public void clearButtons() {
        mContainer.removeAllViews();
        mButtons.clear();
    }

    public void unCheckAll() {
        for (ToggleButton button : mButtons) {
            button.setChecked(false);
        }
    }

    public Set<Integer> getCheckedPositions() {
        Set<Integer> positions = new HashSet<>();
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).isChecked()) {
                positions.add(i);
            }
        }
        return positions;
    }

    public float getButtonSize() {
        return mButtonSize;
    }

    public void setButtonSize(float pixels) {
        mButtonSize = pixels;
        for (ToggleButton button : mButtons) {
            button.setButtonSize(mButtonSize);
        }
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        for (ToggleButton button : mButtons) {
            button.setTextColor(mTextColor);
        }
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float size) {
        mTextSize = dpToPx(size);
        for (ToggleButton button : mButtons) {
            button.setTextSizePx(mTextSize);
        }
    }

    public boolean isAnimationEnabled() {
        return mIsAnimationEnabled;
    }

    public void setAnimationEnabled(boolean isEnabled) {
        mIsAnimationEnabled = isEnabled;
        for (ToggleButton button : mButtons) {
            button.setAnimationEnabled(isEnabled);
        }
    }

    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    public void setAnimationDuration(long durationMillis) {
        if (durationMillis < 0) {
            throw new IllegalArgumentException("The duration must be greater than 0");
        }
        mAnimationDuration = durationMillis;
        for (ToggleButton button : mButtons) {
            button.setAnimationDuration(durationMillis);
        }
    }

    private void addButton(String text) {
        ToggleButton button = new ToggleButton(mContext);

        button.setButtonSize(mButtonSize);
        button.setText(text);
        button.setTextSizePx(mTextSize);
        button.setTextColor(mTextColor);
        button.setAnimationEnabled(mIsAnimationEnabled);
        button.setAnimationDuration(mAnimationDuration);

        if (mCheckedBackground != null) {
            button.setCheckedBackgroundDrawable(mCheckedBackground);
        }

        button.setOnClickListener(this);

        mButtons.add(button);
        mContainer.addView(button.getView());
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
