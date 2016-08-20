package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ToggleButtonGroup extends LinearLayout implements View.OnClickListener {
    private static final String LOG_TAG = ToggleButtonGroup.class.getSimpleName();

    private static final float DEFAULT_TEXT_SIZE = 50;
    private static final float DEFAULT_BUTTON_SIZE = 120;
    private static final int DEFAULT_BACKGROUND_COLOR = Color.BLUE;
    private static final int DEFAULT_TEXT_COLOR = Color.BLACK;

    private Context mContext;
    private float mButtonSize;
    private int mBackgroundColor;
    private int mTextColor;
    private int mTextSize;
    private String mTextButton1;
    private String mTextButton2;
    private ArrayList<String> mLabels;
    private ArrayList<ToggleButton> mButtons;

    private LayoutInflater mInflater;
    private LinearLayout mToggleButtonContainer;
    private ToggleButtonStateChangedListener mToggleButtonStateChangedListener;

    public interface ToggleButtonStateChangedListener {
        void onToggleButtonStateChanged(int position, boolean isEnabled);
    }

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
            mToggleButtonContainer = (LinearLayout) findViewById(R.id.toggle_button_container);

            mTextSize = a.getDimensionPixelSize(R.styleable.ToggleButtonOptions_android_textSize, (int) dp2px(context, DEFAULT_TEXT_SIZE));
            mButtonSize = a.getDimension(R.styleable.ToggleButtonOptions_buttonSize, dp2px(getContext(), DEFAULT_BUTTON_SIZE));
            mBackgroundColor = a.getColor(R.styleable.ToggleButtonOptions_backgroundColor, DEFAULT_BACKGROUND_COLOR);
            mTextColor = a.getColor(R.styleable.ToggleButtonOptions_textColor, DEFAULT_TEXT_COLOR);

            mTextButton1 = a.getString(R.styleable.ToggleButtonOptions_textButton1);
            mTextButton2 = a.getString(R.styleable.ToggleButtonOptions_textButton2);

            Log.v(LOG_TAG, "ToggleButtonGroup(): mTextSize = " + mTextSize
                    + ", mButtonSize = " + mButtonSize
                    + ", mBackgroundColor = " + mBackgroundColor + ", mTextColor = " + mTextColor);

            mLabels = new ArrayList<>();
            mButtons = new ArrayList<>();
            if (mTextButton1 != null && !mTextButton1.isEmpty()) {
                mLabels.add(mTextButton1);
                addToggleButton(mTextButton1);
            }
            if (mTextButton2 != null && !mTextButton2.isEmpty()) {
                mLabels.add(mTextButton2);
                addToggleButton(mTextButton2);
            }

        } finally {
            a.recycle();
        }
    }

    @Override
    public void onClick(View view) {
        int position = mToggleButtonContainer.indexOfChild(view);
        ToggleButton button = mButtons.get(position);
        boolean isEnabled = button.changeCheckedState();
        if (mToggleButtonStateChangedListener != null) {
            mToggleButtonStateChangedListener.onToggleButtonStateChanged(position, isEnabled);
        }
    }

    public boolean getToggleStateAt(int position) {
        ToggleButton button = mButtons.get(position);
        if (button != null) {
            return button.isChecked();
        } else {
            throw new IllegalArgumentException("Toggle button not found.");
        }
    }

    public List<Boolean> getToggleState() {
        List<Boolean> states = new ArrayList<>();
        for(ToggleButton button : mButtons) {
            states.add(button.isChecked());
        }
        return states;
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

    public void setLabels(ArrayList<String> labels){
        if(labels == null || labels.isEmpty())
            throw new RuntimeException("The list cannot be empty.");
        mLabels = labels;
        mToggleButtonContainer.removeAllViews();
        mButtons.clear();
        buildToggleButtons();
    }

    public void setToggleButtonStateChangedListener(ToggleButtonStateChangedListener listener) {
        mToggleButtonStateChangedListener = listener;
    }

    public float getButtonSize() {
        return mButtonSize;
    }

    public void setButtonSize(float buttonSize) {
        mButtonSize = buttonSize;
        for (ToggleButton button : mButtons) {
            View view = button.getView();
            view.setLayoutParams(new LayoutParams((int) mButtonSize, (int) mButtonSize));
        }
        invalidate();
    }

    private void buildToggleButtons() {
        for(String label : mLabels)
            addToggleButton(label);
    }

    private void addToggleButton(String label) {
        ToggleButton toggleButton = new ToggleButton(mContext);

        TextView textView = toggleButton.getText();
        textView.setText(label);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setTextColor(mTextColor);

        ImageView imageView = toggleButton.getBackground();
        DrawableCompat.setTint(imageView.getDrawable(), mBackgroundColor);

        View view = toggleButton.getView();
        view.setLayoutParams(new LayoutParams((int) mButtonSize, (int) mButtonSize));
        view.setOnClickListener(this);

        mButtons.add(toggleButton);
        mToggleButtonContainer.addView(toggleButton.getView());

        uncheck(mToggleButtonContainer.getChildCount() - 1);

    }

    private void uncheck(int position) {
        getToggleButtonAt(position).setChecked(false);
    }

    private void check(int position) {
        getToggleButtonAt(position).setChecked(true);
    }

    private ToggleButton getToggleButtonAt(int position) {
        return mButtons.get(position);
    }

    private float dp2px(Context context, float dp){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * (metrics.densityDpi / 160f);
    }

}
