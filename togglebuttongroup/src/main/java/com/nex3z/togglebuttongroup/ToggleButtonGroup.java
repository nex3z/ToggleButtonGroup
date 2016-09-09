package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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

    private static final int DEFAULT_CHECKED_DRAWABLE_ID = R.drawable.ic_circle_48dp;
    private static final float DEFAULT_TEXT_SIZE = 16;
    private static final long DEFAULT_ANIMATION_DURATION = 150;
    private static final float DEFAULT_SPACING = 0;
    private static final float DEFAULT_BUTTON_HEIGHT = -2;
    private static final float DEFAULT_BUTTON_WIDTH = -2;
    private static final int DEFAULT_CHECKED_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_UNCHECKED_TEXT_COLOR = Color.BLACK;

    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mContainer;

    private Drawable mCheckedBackground;
    private float mButtonHeight;
    private float mButtonWidth;
    private int mCheckedTextColor;
    private int mUncheckedTextColor;
    private float mTextSize;
    private float mSpacing;
    private boolean mIsSpacingSet;
    private boolean mIsAnimationEnabled;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private String mTextButton1;
    private String mTextButton2;
    protected ArrayList<ToggleButton> mButtons;

    protected OnCheckedStateChangeListener mListener;

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChange(int position, boolean isChecked);
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
            if (mCheckedBackground == null) {
                mCheckedBackground = ContextCompat.getDrawable(context, DEFAULT_CHECKED_DRAWABLE_ID);
            }

            mButtonHeight = a.getDimension(R.styleable.ToggleButtonOptions_buttonHeight, dpToPx(DEFAULT_BUTTON_HEIGHT));
            mButtonWidth = a.getDimension(R.styleable.ToggleButtonOptions_buttonWidth, dpToPx(DEFAULT_BUTTON_WIDTH));

            mTextSize = a.getDimensionPixelSize(R.styleable.ToggleButtonOptions_android_textSize, (int)dpToPx(DEFAULT_TEXT_SIZE));

            mCheckedTextColor = a.getColor(R.styleable.ToggleButtonOptions_checkedTextColor, DEFAULT_CHECKED_TEXT_COLOR);
            mUncheckedTextColor = a.getColor(R.styleable.ToggleButtonOptions_uncheckedTextColor, DEFAULT_UNCHECKED_TEXT_COLOR);

            mIsSpacingSet = a.hasValue(R.styleable.ToggleButtonOptions_spacing);
            mSpacing = a.getDimension(R.styleable.ToggleButtonOptions_spacing, dpToPx(DEFAULT_SPACING));

            mIsAnimationEnabled = a.getBoolean(R.styleable.ToggleButtonOptions_animationEnabled, false);

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if ((MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.AT_MOST) && (!mIsSpacingSet)) {
            int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
            int buttonWidth = mButtons.get(0).getView().getLayoutParams().width;
            int count = mContainer.getChildCount();
            float spacing = (parentWidth - (count * buttonWidth)) / (count - 1);

            for (int i = 0; i < mContainer.getChildCount() - 1; i++) {
                View view = mContainer.getChildAt(i);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                params.setMargins(0, 0, (int) spacing, 0);
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Registers a callback to be invoked when any button's checked state is changed.
     *
     * @param listener The callback that will run
     */
    public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener listener) {
        mListener = listener;
    }

    /**
     * Sets buttons to the group with the given list of text.
     *
     * @param text The list of text that will be displayed on the buttons
     */
    public void setButtons(List<String> text) {
        clearButtons();

        int count = text != null ? text.size() : 0;
        if (count != 0) {
            for (int i = 0; i < count - 1; i++) {
                addButton(text.get(i), true);
            }
            addButton(text.get(count - 1), false);
        }
    }

    /**
     * Clears all buttons in the group.
     */
    public void clearButtons() {
        mContainer.removeAllViews();
        mButtons.clear();
    }

    /**
     * Unchecks all buttons in the group.
     */
    public void unCheckAll() {
        for (ToggleButton button : mButtons) {
            button.setChecked(false);
        }
    }

    /**
     * Returns the positions of all checked buttons.
     *
     * @return The positions of all checked buttons.
     */
    public Set<Integer> getCheckedPositions() {
        Set<Integer> positions = new HashSet<>();
        for (int i = 0; i < mButtons.size(); i++) {
            if (mButtons.get(i).isChecked()) {
                positions.add(i);
            }
        }
        return positions;
    }

    /**
     * Changes the checked state of the button at specified position.
     *
     * @param position The position of the button
     * @param isChecked true to check the button, false to uncheck it
     */
    public void setCheckedAt(int position, boolean isChecked) {
        ToggleButton button = mButtons.get(position);
        if (button != null) {
            button.setChecked(isChecked);
        }
    }

    /**
     * Gets the text color for the button when checked.
     *
     * @return The text color for checked state
     */
    public int getCheckedTextColor() {
        return mCheckedTextColor;
    }

    /**
     * Sets the text color for the button when checked.
     *
     * @param checkedTextColor The text color for checked state
     */
    public void setCheckedTextColor(int checkedTextColor) {
        mCheckedTextColor = checkedTextColor;
        for (ToggleButton button : mButtons) {
            button.setCheckedTextColor(checkedTextColor);
        }
    }

    /**
     * Gets the text color for the button when unchecked.
     *
     * @return The text color for unchecked state
     */
    public int getUncheckedTextColor() {
        return mUncheckedTextColor;
    }

    /**
     * Sets the text color for the button when unchecked.
     *
     * @param uncheckedTextColor The text color for unchecked state
     */
    public void setUncheckedTextColor(int uncheckedTextColor) {
        mUncheckedTextColor = uncheckedTextColor;
        for (ToggleButton button : mButtons) {
            button.setUncheckedTextColor(uncheckedTextColor);
        }
    }

    /**
     * Returns the text size of the button in pixels.
     * @return The text size in pixels.
     */
    public float getTextSize() {
        return mTextSize;
    }

    /**
     * Sets the default text size to the given value, interpreted as "scaled pixel" units.
     *
     * @param size The scaled pixel size.
     */
    public void setTextSize(float size) {
        mTextSize = dpToPx(size);
        for (ToggleButton button : mButtons) {
            button.setTextSizePx(mTextSize);
        }
    }

    /**
     * Gets the spacing between neighboring buttons in pixels.
     *
     * @return The spacing between neighboring buttons in pixels
     */
    public float getSpacing() {
        return mSpacing;
    }

    /**
     * Sets the spacing between neighboring buttons in pixels.
     *
     * @param spacing The spacing between neighboring buttons in pixels
     */
    public void setSpacing(float spacing) {
        mSpacing = spacing;

        // Do not add spacing after the last button.
        for (int i = 0; i < mContainer.getChildCount() - 1; i++) {
            View view = mContainer.getChildAt(i);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            params.setMargins(0, 0, (int) mSpacing, 0);
        }
    }

    /**
     * Returns whether the animation for toggling button is enabled.
     *
     * @return Whether the animation for toggling button is enabled
     */
    public boolean isAnimationEnabled() {
        return mIsAnimationEnabled;
    }

    /**
     * Sets whether the animation for toggling button is enabled. The default is false, meaning that
     * the animation is disabled.
     *
     * @param isEnabled Whether the animation for toggling button is enabled
     */
    public void setAnimationEnabled(boolean isEnabled) {
        mIsAnimationEnabled = isEnabled;
        for (ToggleButton button : mButtons) {
            button.setAnimationEnabled(isEnabled);
        }
    }

    /**
     * Returns the duration of the animation for toggling button.
     *
     * @return The duration of the animation in milliseconds.
     */
    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    /**
     * Sets the duration of the animation for toggling button in milliseconds. The default is 150
     * milliseconds.
     *
     * @param durationMillis The duration of the animation in milliseconds
     */
    public void setAnimationDuration(long durationMillis) {
        if (durationMillis < 0) {
            throw new IllegalArgumentException("The duration must be greater than 0");
        }
        mAnimationDuration = durationMillis;
        for (ToggleButton button : mButtons) {
            button.setAnimationDuration(durationMillis);
        }
    }

    private void addButton(String text, boolean needSpacing) {
        ToggleButton button = new ToggleButton(mContext);

        button.setText(text);
        button.setTextSizePx(mTextSize);
        button.setCheckedTextColor(mCheckedTextColor);
        button.setUncheckedTextColor(mUncheckedTextColor);
        button.setAnimationEnabled(mIsAnimationEnabled);
        button.setAnimationDuration(mAnimationDuration);

        button.setCheckedBackgroundDrawable(mCheckedBackground);

        button.setOnClickListener(this);

        mButtons.add(button);

        LinearLayout.LayoutParams params = buildLayoutParams(needSpacing);

        mContainer.addView(button.getView(), params);
    }

    private LinearLayout.LayoutParams buildLayoutParams(boolean needSpacing) {
        LinearLayout.LayoutParams params;

        mButtonHeight = mButtonHeight < 0 ? mCheckedBackground.getIntrinsicHeight() : mButtonHeight;
        mButtonWidth = mButtonWidth < 0 ? mCheckedBackground.getIntrinsicWidth() : mButtonWidth;
        params = new LinearLayout.LayoutParams((int) mButtonWidth, (int) mButtonHeight);

        if (needSpacing) {
            params.setMargins(0, 0, (int) mSpacing, 0);
        }
        return params;
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
