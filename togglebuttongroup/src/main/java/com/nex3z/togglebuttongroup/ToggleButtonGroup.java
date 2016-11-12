package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
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
    private static final int DEFAULT_ANIMATION_DURATION = 150;
    private static final float DEFAULT_SPACING = 0;
    private static final float DEFAULT_BUTTON_HEIGHT = -2;
    private static final float DEFAULT_BUTTON_WIDTH = -2;
    private static final int DEFAULT_CHECKED_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_UNCHECKED_TEXT_COLOR = Color.BLACK;
    private static final boolean DEFAULT_SAVE_ENABLED = false;

    private static final String KEY_SUPER_STATE = "super_state";
    private static final String KEY_CHECKED_POSITIONS = "checked_positions";

    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mContainer;

    private Drawable mCheckedBackground;
    private Drawable mButtonBackground;
    private float mButtonHeight;
    private float mButtonWidth;
    private int mCheckedTextColor;
    private int mUncheckedTextColor;
    private float mTextSize;
    private float mSpacing;
    private boolean mIsSpacingSet;
    private @ToggleButton.AnimationType String mAnimationType;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private boolean mSaveEnabled;
    private String mTextButton1;
    private String mTextButton2;
    private CharSequence[] mText;

    protected ArrayList<ToggleButton> mButtons;

    protected OnCheckedChangeListener mOnCheckedChangeListener;
    protected OnCheckedPositionChangeListener mOnCheckedPositionChangeListener;

    /**
     * Interface definition for a callback to be invoked when any button's checked state is
     * changed.
     */
    public interface OnCheckedChangeListener {
        /**
         * Called when a toggle button's checked state is changed.
         *
         * @param position The position of the button whose state has changed
         * @param isChecked The new checked state of buttonView
         */
        void onCheckedChange(int position, boolean isChecked);
    }

    /**
     * Interface definition for a callback to be invoked when the positions of checked buttons
     * are changed.
     */
    public interface OnCheckedPositionChangeListener {
        /**
         * Called when the positions of checked buttons are changed.
         *
         * @param checkedPositions The positions of all checked buttons
         */
        void onCheckedPositionChange(Set<Integer> checkedPositions);
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

            mButtonBackground = a.getDrawable(R.styleable.ToggleButtonOptions_buttonBackground);

            mButtonHeight = a.getDimension(R.styleable.ToggleButtonOptions_buttonHeight, dpToPx(DEFAULT_BUTTON_HEIGHT));
            mButtonWidth = a.getDimension(R.styleable.ToggleButtonOptions_buttonWidth, dpToPx(DEFAULT_BUTTON_WIDTH));

            mTextSize = a.getDimensionPixelSize(R.styleable.ToggleButtonOptions_android_textSize, (int)dpToPx(DEFAULT_TEXT_SIZE));

            mCheckedTextColor = a.getColor(R.styleable.ToggleButtonOptions_checkedTextColor, DEFAULT_CHECKED_TEXT_COLOR);
            mUncheckedTextColor = a.getColor(R.styleable.ToggleButtonOptions_uncheckedTextColor, DEFAULT_UNCHECKED_TEXT_COLOR);

            mIsSpacingSet = a.hasValue(R.styleable.ToggleButtonOptions_spacing);
            mSpacing = a.getDimension(R.styleable.ToggleButtonOptions_spacing, dpToPx(DEFAULT_SPACING));

            mAnimationType = a.getString(R.styleable.ToggleButtonOptions_animationType);
            mAnimationDuration = a.getInt(R.styleable.ToggleButtonOptions_animationDuration, DEFAULT_ANIMATION_DURATION);

            mSaveEnabled = a.getBoolean(R.styleable.ToggleButtonOptions_android_saveEnabled, DEFAULT_SAVE_ENABLED);

            mTextButton1 = a.getString(R.styleable.ToggleButtonOptions_textButton1);
            mTextButton2 = a.getString(R.styleable.ToggleButtonOptions_textButton2);

            mText =  a.getTextArray(R.styleable.ToggleButtonOptions_textButtons);

            mButtons = new ArrayList<>();

            List<String> attrLabels = new ArrayList<>();
            if (mText != null) {
                if (mText.length == 0) {
                    Log.e(LOG_TAG, "The array read from textButtons is empty.");
                }
                for (CharSequence cs : mText) {
                    attrLabels.add(cs.toString());
                }
            } else {
                if (mTextButton1 != null && !mTextButton1.isEmpty()) {
                    attrLabels.add(mTextButton1);
                }
                if (mTextButton2 != null && !mTextButton2.isEmpty()) {
                    attrLabels.add(mTextButton2);
                }
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
        if ((MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.AT_MOST) && (!mIsSpacingSet)
                && mButtons.size() > 0) {
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

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (mSaveEnabled && state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            List<Integer> checkedPositions = bundle.getIntegerArrayList(KEY_CHECKED_POSITIONS);

            if (checkedPositions != null) {
                uncheckAll();
                for (int position : checkedPositions) {
                    setCheckedAt(position, true);
                }
                if (mOnCheckedPositionChangeListener != null) {
                    mOnCheckedPositionChangeListener.onCheckedPositionChange(
                            new HashSet<>(checkedPositions));
                }
            }

            state = bundle.getParcelable(KEY_SUPER_STATE);
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        if (mSaveEnabled) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
            ArrayList<Integer> checkedPositions = new ArrayList<>(getCheckedPositions());
            bundle.putIntegerArrayList(KEY_CHECKED_POSITIONS, checkedPositions);
            return bundle;
        } else {
            return super.onSaveInstanceState();
        }
    }

    /**
     * Registers a callback to be invoked when any button's checked state is changed.
     *
     * @param listener The callback that will run
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Registers a callback to be invoked when the positions of checked buttons are changed.
     *
     * @param listener The callback that will run
     */
    public void setOnCheckedPositionChangeListener(OnCheckedPositionChangeListener listener) {
        mOnCheckedPositionChangeListener = listener;
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
    public void uncheckAll() {
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
     * @param position the position of the button
     * @param isChecked true to check the button, false to uncheck it
     */
    public void setCheckedAt(int position, boolean isChecked) {
        ToggleButton button = mButtons.get(position);
        if (button != null) {
            button.setChecked(isChecked);
        }
    }

    /**
     * Changes the checked state of the button at specified position, optionally animating the
     * check and uncheck operation.
     *
     * @param position the position of the button
     * @param isChecked true to check the button, false to uncheck it
     * @param animate true to animate between the checked and unchecked state or false to not
     *                animate
     */
    public void setCheckedAt(int position, boolean isChecked, boolean animate) {
        ToggleButton button = mButtons.get(position);
        if (button != null) {
            button.setChecked(isChecked, animate);
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
        return mAnimationType != null && !mAnimationType.equals(ToggleButton.ANIMATION_NONE);
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

    /**
     * Controls whether the saving of this view's state is enabled.
     *
     * @param enabled Set to true to allow state saving, or false (the default) to disable it
     */
    public void setSaveEnabled(boolean enabled) {
        mSaveEnabled = enabled;
    }

    private void addButton(String text, boolean needSpacing) {
        ToggleButton button = new ToggleButton(mContext);

        button.setText(text);
        button.setTextSizePx(mTextSize);
        button.setCheckedTextColor(mCheckedTextColor);
        button.setUncheckedTextColor(mUncheckedTextColor);
        button.setAnimationType(mAnimationType);
        button.setAnimationDuration(mAnimationDuration);

        button.setCheckedBackgroundDrawable(mCheckedBackground);
        if (mButtonBackground != null) {
            button.setBackground(mButtonBackground);
        }

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
