package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ToggleButtonGroup extends ViewGroup implements View.OnClickListener {
    private static final String LOG_TAG = ToggleButtonGroup.class.getSimpleName();

    private static final float DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_ANIMATION_DURATION = 150;
    private static final int DEFAULT_CHECKED_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_UNCHECKED_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_BUTTON_SPACING = 0;
    private static final int DEFAULT_BUTTON_SPACING_FOR_LAST_ROW = 0;
    private static final float DEFAULT_ROW_SPACING = 0;
    private static final boolean DEFAULT_FLOW = false;
    private static final boolean DEFAULT_SAVE_ENABLED = false;
    private static final int DEFAULT_BUTTON_WIDTH = LayoutParams.WRAP_CONTENT;
    private static final int DEFAULT_BUTTON_HEIGHT = LayoutParams.WRAP_CONTENT;
    private static final String KEY_SUPER_STATE = "super_state";
    private static final String KEY_CHECKED_POSITIONS = "checked_positions";
    private static final int DEFAULT_BUTTON_TEXT_PADDING = 0;

    public static final int SPACING_AUTO = -65536;
    public static final int SPACING_ALIGN = -65537;
    public static final int SPACING_UNDEFINED = -65538;

    private float mTextSize = dpToPx(DEFAULT_TEXT_SIZE);
    private ColorStateList mTextColors;
    private int mCheckedTextColor = DEFAULT_CHECKED_TEXT_COLOR;
    private int mUncheckedTextColor = DEFAULT_UNCHECKED_TEXT_COLOR;
    private int mCheckedBackgroundResource;
    private int mButtonBackgroundResource;

    @ToggleButton.AnimationType private int mAnimationType = ToggleButton.ANIMATION_NONE;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private int mButtonSpacing = DEFAULT_BUTTON_SPACING;
    private int mButtonSpacingForLastRow = DEFAULT_BUTTON_SPACING_FOR_LAST_ROW;
    private float mRowSpacing = DEFAULT_ROW_SPACING;
    private float mAdjustedRowSpacing = DEFAULT_ROW_SPACING;
    private boolean mFlow = DEFAULT_FLOW;
    private boolean mSaveEnabled = DEFAULT_SAVE_ENABLED;
    private int mButtonWidth = DEFAULT_BUTTON_WIDTH;
    private int mButtonHeight = DEFAULT_BUTTON_HEIGHT;
    private int mButtonTextPaddingTop = DEFAULT_BUTTON_TEXT_PADDING;
    private int mButtonTextPaddingBottom = DEFAULT_BUTTON_TEXT_PADDING;
    private int mButtonTextPaddingLeft = DEFAULT_BUTTON_TEXT_PADDING;
    private int mButtonTextPaddingRight = DEFAULT_BUTTON_TEXT_PADDING;

    protected List<ToggleButton> mButtons = new ArrayList<>();
    private List<Float> mButtonSpacingForRow = new ArrayList<>();
    private List<Integer> mHeightForRow = new ArrayList<>();
    private List<Integer> mButtonNumForRow = new ArrayList<>();
    protected OnCheckedChangeListener mOnCheckedChangeListener;
    protected OnCheckedPositionChangeListener mOnCheckedPositionChangeListener;

    protected abstract void onButtonClick(int position);

    public ToggleButtonGroup(Context context) {
        this(context, null);
    }

    public ToggleButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ToggleButtonGroup, 0, 0);

        CharSequence textButton1;
        CharSequence textButton2;
        CharSequence[] textButtons;
        try {
            mTextSize = a.getDimension(R.styleable.ToggleButtonGroup_android_textSize, dpToPx(DEFAULT_TEXT_SIZE));
            mTextColors = a.getColorStateList(R.styleable.ToggleButtonGroup_android_textColor);
            if (mTextColors != null) {
                mUncheckedTextColor = mTextColors.getDefaultColor();
                mCheckedTextColor = mTextColors.getColorForState(new int[]{android.R.attr.state_checked}, mUncheckedTextColor);
            }
            mCheckedTextColor = a.getColor(R.styleable.ToggleButtonGroup_checkedTextColor, mCheckedTextColor);
            mUncheckedTextColor = a.getColor(R.styleable.ToggleButtonGroup_uncheckedTextColor, mUncheckedTextColor);
            mCheckedBackgroundResource = a.getResourceId(R.styleable.ToggleButtonGroup_checkedBackground, 0);
            mButtonBackgroundResource = a.getResourceId(R.styleable.ToggleButtonGroup_buttonBackground, 0);
            // noinspection ResourceType
            mAnimationType = a.getInt(R.styleable.ToggleButtonGroup_animationType, 0);
            mAnimationDuration = a.getInt(R.styleable.ToggleButtonGroup_animationDuration, DEFAULT_ANIMATION_DURATION);
            try {
                mButtonSpacing = a.getInt(R.styleable.ToggleButtonGroup_buttonSpacing, DEFAULT_BUTTON_SPACING);
            } catch (NumberFormatException e) {
                mButtonSpacing = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonSpacing, (int)dpToPx(DEFAULT_BUTTON_SPACING));
            }
            try {
                mButtonSpacingForLastRow = a.getInt(R.styleable.ToggleButtonGroup_buttonSpacingForLastRow, SPACING_UNDEFINED);
            } catch (NumberFormatException e) {
                mButtonSpacingForLastRow = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonSpacingForLastRow, (int)dpToPx(DEFAULT_BUTTON_SPACING));
            }
            try {
                mRowSpacing = a.getInt(R.styleable.ToggleButtonGroup_rowSpacing, 0);
            }  catch (NumberFormatException e) {
                mRowSpacing = a.getDimension(R.styleable.ToggleButtonGroup_rowSpacing, dpToPx(DEFAULT_ROW_SPACING));
            }
            mFlow = a.getBoolean(R.styleable.ToggleButtonGroup_flow, DEFAULT_FLOW);
            mSaveEnabled = a.getBoolean(R.styleable.ToggleButtonGroup_android_saveEnabled, DEFAULT_SAVE_ENABLED);
            try {
                mButtonWidth = a.getInt(R.styleable.ToggleButtonGroup_buttonWidth, DEFAULT_BUTTON_WIDTH);
            } catch (NumberFormatException e) {
                mButtonWidth = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonWidth, DEFAULT_BUTTON_WIDTH);
            }
            try {
                mButtonHeight = a.getInt(R.styleable.ToggleButtonGroup_buttonHeight, DEFAULT_BUTTON_HEIGHT);
            } catch (NumberFormatException e) {
                mButtonHeight = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonHeight, DEFAULT_BUTTON_HEIGHT);
            }
            mButtonTextPaddingTop = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonTextPaddingTop, DEFAULT_BUTTON_TEXT_PADDING);
            mButtonTextPaddingBottom = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonTextPaddingBottom, DEFAULT_BUTTON_TEXT_PADDING);
            mButtonTextPaddingLeft = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonTextPaddingLeft, DEFAULT_BUTTON_TEXT_PADDING);
            mButtonTextPaddingRight = a.getDimensionPixelSize(R.styleable.ToggleButtonGroup_buttonTextPaddingRight, DEFAULT_BUTTON_TEXT_PADDING);
            textButtons = a.getTextArray(R.styleable.ToggleButtonGroup_textButtons);
            textButton1 = a.getText(R.styleable.ToggleButtonGroup_textButton1);
            textButton2 = a.getString(R.styleable.ToggleButtonGroup_textButton2);
        } finally {
            a.recycle();
        }

        List<String> labels = new ArrayList<>();
        if (textButtons != null) {
            if (textButtons.length == 0) {
                Log.e(LOG_TAG, "The array read from textButtons is empty.");
            }
            for (CharSequence cs : textButtons) {
                labels.add(cs.toString());
            }
        } else {
            if (textButton1 != null && textButton1.length() != 0) {
                labels.add(textButton1.toString());
            }
            if (textButton2 != null && textButton2.length() != 0) {
                labels.add(textButton2.toString());
            }
        }
        if (!labels.isEmpty()) {
            setButtons(labels);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        mButtonSpacingForRow.clear();
        mButtonNumForRow.clear();
        mHeightForRow.clear();
        int measuredHeight = 0, measuredWidth = 0, childCount = getChildCount();
        int rowWidth = 0, maxChildHeightInRow = 0, childNumInRow = 0;
        int rowSize = widthSize - getPaddingLeft() - getPaddingRight();
        float tmpSpacing = mButtonSpacing == SPACING_AUTO ? 0 : mButtonSpacing;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            if (child.getVisibility() == GONE) {
                continue;
            }
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (mFlow && rowWidth + childWidth > rowSize) { // Need flow to next row
                // Save parameters for current row
                mButtonSpacingForRow.add(getSpacingForRow(mButtonSpacing, rowSize, rowWidth, childNumInRow));
                mButtonNumForRow.add(childNumInRow);
                mHeightForRow.add(maxChildHeightInRow);
                measuredHeight += maxChildHeightInRow;
                measuredWidth = max(measuredWidth, rowWidth);

                // Place the button to next row
                childNumInRow = 1;
                rowWidth = childWidth;
                maxChildHeightInRow = childHeight;
            } else {
                childNumInRow++;
                rowWidth += childWidth + tmpSpacing;
                maxChildHeightInRow = max(maxChildHeightInRow, childHeight);
            }
        }

        // Measure remaining buttons in the last row
        if (mButtonSpacingForLastRow == SPACING_ALIGN) {
            // For SPACING_ALIGN, use the same spacing from the row above if there is more than one
            // row.
            if (mButtonSpacingForRow.size() >= 1) {
                mButtonSpacingForRow.add(mButtonSpacingForRow.get(mButtonSpacingForRow.size() - 1));
            } else {
                mButtonSpacingForRow.add(getSpacingForRow(mButtonSpacing, rowSize, rowWidth, childNumInRow));
            }
        } else if (mButtonSpacingForLastRow != SPACING_UNDEFINED) {
            mButtonSpacingForRow.add(getSpacingForRow(mButtonSpacingForLastRow, rowSize, rowWidth, childNumInRow));
        } else {
            mButtonSpacingForRow.add(getSpacingForRow(mButtonSpacing, rowSize, rowWidth, childNumInRow));
        }

        mButtonNumForRow.add(childNumInRow);
        mHeightForRow.add(maxChildHeightInRow);
        measuredHeight += maxChildHeightInRow;
        measuredWidth = max(measuredWidth, rowWidth);

        if (mButtonSpacing == SPACING_AUTO) {
            measuredWidth = widthSize;
        } else {
            measuredWidth = min(measuredWidth + getPaddingLeft() + getPaddingRight(), widthSize);
        }

        measuredHeight += getPaddingTop() + getPaddingBottom();
        int rowNum = mButtonSpacingForRow.size();
        if (mRowSpacing == SPACING_AUTO) {
            mAdjustedRowSpacing = (heightSize - measuredHeight) / (rowNum - 1);
            measuredHeight = heightSize;
        } else {
            mAdjustedRowSpacing = mRowSpacing;
            measuredHeight = min((int)(measuredHeight + mAdjustedRowSpacing * (rowNum - 1)), heightSize);
        }

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, measuredHeight);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(measuredWidth, heightSize);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int x = paddingLeft;
        int y = paddingTop;

        int rowCount = mButtonNumForRow.size(), childIdx = 0;
        for (int row = 0; row < rowCount; row++) {
            int buttonNum = mButtonNumForRow.get(row);
            int rowHeight = mHeightForRow.get(row);
            float spacing = mButtonSpacingForRow.get(row);
            for (int i = 0; i < buttonNum; i++) {
                View child = getChildAt(childIdx++);
                if (child.getVisibility() == GONE) {
                    continue;
                }
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                child.layout(x, y, x + childWidth, y + childHeight);
                x += childWidth + spacing;
            }
            x = paddingLeft;
            y += rowHeight + mAdjustedRowSpacing;
        }
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

    @Override
    public void onClick(View v) {
        int position = indexOfChild(v);
        onButtonClick(position);
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
            button.setTextSize(mTextSize);
        }
    }

    /**
     * Gets the text colors for the different states (normal, checked) of the button.
     *
     * @return The text colors.
     */
    public ColorStateList getTextColors() {
        if (mTextColors != null) {
            return mTextColors;
        } else {
            int[][] states = new int[][] {new int[] {android.R.attr.state_checked}, new int[]{}};
            int[] colors = new int[] {mCheckedTextColor, mUncheckedTextColor};
            return new ColorStateList(states, colors);
        }
    }

    /**
     * Sets the text color for the different states (normal, checked) of the button.
     *
     * @param textColors The text color to be set.
     */
    public void setTextColor(ColorStateList textColors) {
        mTextColors = textColors;
        if (mTextColors != null) {
            mUncheckedTextColor = mTextColors.getDefaultColor();
            mCheckedTextColor = mTextColors.getColorForState(
                    new int[]{android.R.attr.state_checked}, mUncheckedTextColor);
            for (ToggleButton button : mButtons) {
                button.setCheckedTextColor(mCheckedTextColor);
                button.setUncheckedTextColor(mUncheckedTextColor);
            }
        } else {
            mUncheckedTextColor = DEFAULT_UNCHECKED_TEXT_COLOR;
            mCheckedTextColor = DEFAULT_CHECKED_TEXT_COLOR;
        }
    }

    /**
     * Sets the text color for all the states (normal, checked) of the button.
     *
     * @param color The text color to be set.
     */
    public void setTextColor(int color) {
        mUncheckedTextColor = color;
        mCheckedTextColor = color;
        for (ToggleButton button : mButtons) {
            button.setCheckedTextColor(mCheckedTextColor);
            button.setUncheckedTextColor(mUncheckedTextColor);
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

    /**
     * Returns whether the saving of this view's state is enabled.
     *
     * @return Whether the saving of this view's state is enabled.
     */
    @Override
    public boolean isSaveEnabled() {
        return mSaveEnabled;
    }

    /**
     * Gets the text color for the button when checked.
     *
     * @return The text color for checked state.
     */
    public int getCheckedTextColor() {
        return mCheckedTextColor;
    }

    /**
     * Sets the text color for the button when checked.
     *
     * @param checkedTextColor The text color for checked state.
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
     * @return The text color for unchecked state.
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
     * Gets the drawable resource ID for checked state.
     *
     * @return The drawable resource ID for checked state.
     */
    public int getCheckedBackgroundResource() {
        return mCheckedBackgroundResource;
    }

    /**
     * Sets the drawable resource ID for checked state.
     *
     * @param resId The drawable resource ID for checked state.
     */
    public void setCheckedBackgroundResource(int resId) {
        mCheckedBackgroundResource = resId;
        for (ToggleButton button : mButtons) {
            button.setCheckedBackgroundResource(mCheckedBackgroundResource);
        }
    }

    /**
     * Gets the drawable resource ID for button background.
     *
     * @return The drawable resource ID for button background.
     */
    public int getButtonBackgroundResource() {
        return mButtonBackgroundResource;
    }

    /**
     * Sets the drawable resource ID for button background.
     *
     * @param resId The drawable resource ID for button background.
     */
    public void setButtonBackgroundResource(int resId) {
        mButtonBackgroundResource = resId;
        for (ToggleButton button : mButtons) {
            button.setButtonBackgroundResource(mButtonBackgroundResource);
        }
    }

    /**
     * Gets the type of animation which is played when checking and unchecking buttons.
     * ANIMATION_NONE for disabling animation, ANIMATION_SCALE / ANIMATION_ALPHA for scale / alpha
     * animation.
     *
     * @return The type of animation for checking and unchecking buttons.
     */
    public int getAnimationType() {
        return mAnimationType;
    }

    /**
     * Sets the type of animation which is played when checking and unchecking buttons.
     *
     * @param animationType The type of animation for checking and unchecking buttons.
     *                      ANIMATION_NONE for disabling animation, ANIMATION_SCALE /
     *                      ANIMATION_ALPHA for scale / alpha animation.
     *
     */
    public void setAnimationType(@ToggleButton.AnimationType int animationType) {
        mAnimationType = animationType;
        for (ToggleButton button : mButtons) {
            button.setAnimationType(animationType);
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

    /**
     * Sets the button size in pixels. May be a layout constant such as WRAP_CONTENT or
     * MATCH_PARENT.
     *
     * @param width The button width in pixels or a layout constant.
     * @param height The button height in pixels or a layout constant.
     */
    public void setButtonSize(int width, int height) {
        mButtonWidth = width;
        mButtonHeight = height;
        for (ToggleButton button : mButtons) {
            button.setButtonSize(mButtonWidth, mButtonHeight);
        }
    }

    /**
     * Returns the horizontal spacing between buttons in pixels.
     *
     * @return The horizontal spacing between buttons in pixels.
     */
    public float getButtonSpacing() {
        return mButtonSpacing;
    }

    /**
     * Sets the horizontal spacing between buttons in pixels. Use SPACING_AUTO to evenly place
     * the buttons in each row.
     *
     * @param buttonSpacing The horizontal spacing between buttons in pixels.
     */
    public void setButtonSpacing(int buttonSpacing) {
        mButtonSpacing = buttonSpacing;
        requestLayout();
    }

    /**
     * Returns the horizontal spacing between buttons in pixels for the last row.
     *
     * @return The horizontal spacing between buttons in pixels for the last row.
     */
    public int getButtonSpacingForLastRow() {
        return mButtonSpacingForLastRow;
    }

    /**
     * Returns the horizontal spacing between buttons in pixels for the last row. Use SPACING_AUTO
     * to evenly place the buttons in each row. Use SPACING_ALIGN to use the same spacing from the
     * row above.
     *
     * @param buttonSpacingForLastRow The horizontal spacing between buttons in pixels for the last
     *                                row.
     */
    public void setButtonSpacingForLastRow(int buttonSpacingForLastRow) {
        mButtonSpacingForLastRow = buttonSpacingForLastRow;
        requestLayout();
    }

    /**
     * Returns the vertical spacing between button rows in pixels.
     *
     * @return The vertical spacing between button rows in pixels.
     */
    public float getRowSpacing() {
        return mRowSpacing;
    }

    /**
     * Sets the vertical spacing between button rows in pixels. Use SPACING_AUTO to evenly place
     * all rows in vertical.
     *
     * @param rowSpacing The vertical spacing between button rows in pixels.
     */
    public void setRowSpacing(float rowSpacing) {
        mRowSpacing = rowSpacing;
        requestLayout();
    }

    /**
     * Returns whether to allow buttons flow to next row when there is no enough space.
     *
     * @return Whether to flow buttons to next row when there is no enough space.
     */
    public boolean isFlow() {
        return mFlow;
    }

    /**
     * Sets whether to allow buttons flow to next row when there is no enough space.
     *
     * @param flow true to allow flow. false to restrict all buttons in one row.
     */
    public void setFlow(boolean flow) {
        mFlow = flow;
    }

    /**
     * Sets padding for the text of each button.
     *
     * @param left The left padding in pixels.
     * @param top The top padding in pixels.
     * @param right The right padding in pixels.
     * @param bottom The bottom padding in pixels.
     */
    public void setButtonTextPadding(int left, int top, int right, int bottom) {
        for (ToggleButton button : mButtons) {
            button.setTextPadding(left, top, right, bottom);
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
     * Returns whether the animation for toggling button is enabled.
     *
     * @return Whether the animation for toggling button is enabled
     */
    public boolean isAnimationEnabled() {
        return mAnimationType != ToggleButton.ANIMATION_NONE;
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

    public void clearButtons() {
        removeAllViews();
        mButtons.clear();
    }

    public void setButtons(List<String> labels) {
        clearButtons();
        if (labels != null) {
            for (String label : labels) {
                addButton(label);
            }
        }
    }

    private void addButton(String label) {
        ToggleButton button = new ToggleButton(getContext());
        button.setText(label);
        button.setTextSize(mTextSize);
        button.setButtonSize(mButtonWidth, mButtonHeight);
        button.setTextPadding(mButtonTextPaddingLeft, mButtonTextPaddingTop,
                mButtonTextPaddingRight, mButtonTextPaddingBottom);
        button.setCheckedTextColor(mCheckedTextColor);
        button.setUncheckedTextColor(mUncheckedTextColor);
        if (mCheckedBackgroundResource != 0) {
            button.setCheckedBackgroundResource(mCheckedBackgroundResource);
        }
        button.setButtonBackgroundResource(mButtonBackgroundResource);
        button.setAnimationType(mAnimationType);
        button.setAnimationDuration(mAnimationDuration);
        button.setOnClickListener(this);
        button.setChecked(false);
        addView(button);
        mButtons.add(button);
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private int max(int a, int b) {
        return a > b ? a : b;
    }

    private int min(int a, int b) {
        return a < b ? a : b;
    }

    private float getSpacingForRow(int spacingAttribute, int rowSize, int usedSize, int buttonNum) {
        float spacing;
        if (spacingAttribute == SPACING_AUTO) {
            if (buttonNum > 1) {
                spacing = (rowSize - usedSize) / (buttonNum - 1);
            } else {
                spacing = 0;
            }
        } else {
            spacing = spacingAttribute;
        }
        return spacing;
    }

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
}
