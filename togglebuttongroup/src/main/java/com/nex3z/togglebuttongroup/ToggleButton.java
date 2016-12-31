package com.nex3z.togglebuttongroup;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class ToggleButton extends FrameLayout {
    private static final String LOG_TAG = ToggleButton.class.getSimpleName();

    @Retention(SOURCE)
    @IntDef({ANIMATION_SCALE, ANIMATION_ALPHA, ANIMATION_NONE})
    public @interface AnimationType {}
    public static final int ANIMATION_NONE = 0;
    public static final int ANIMATION_SCALE = 1;
    public static final int ANIMATION_ALPHA = 2;

    private static final float DEFAULT_TEXT_SIZE = 16;
    private static final int DEFAULT_ANIMATION_DURATION = 150;
    private static final int DEFAULT_CHECKED_TEXT_COLOR = Color.BLACK;
    private static final int DEFAULT_UNCHECKED_TEXT_COLOR = Color.BLACK;

    private boolean mIsChecked;
    private int mCheckedTextColor = DEFAULT_CHECKED_TEXT_COLOR;
    private int mUncheckedTextColor = DEFAULT_UNCHECKED_TEXT_COLOR;
    @AnimationType private int mAnimationType = ANIMATION_NONE;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private Animation mCheckAnimation;
    private Animation mUncheckAnimation;
    private ValueAnimator mTextColorAnimator;
    private Drawable mCheckedBackground;
    private Drawable mButtonBackground;

    private FrameLayout mContainer;
    private ImageView mIvBg;
    private TextView mTvText;

    public ToggleButton(Context context) {
        this(context, null);
    }

    public ToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = inflater.inflate(R.layout.toggle_button, this, true);
        mContainer = (FrameLayout) rootView.findViewById(R.id.fl_container);
        mIvBg = (ImageView) rootView.findViewById(R.id.iv_background);
        mTvText = (TextView) rootView.findViewById(R.id.tv_text);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.ToggleButton, 0, 0);
        try {
            float textSize = a.getDimension(R.styleable.ToggleButton_android_textSize, dpToPx(DEFAULT_TEXT_SIZE));
            setTextSize(textSize);

            CharSequence text = a.getText(R.styleable.ToggleButton_android_text);
            setText(text);

            mCheckedTextColor = a.getColor(R.styleable.ToggleButton_checkedTextColor, DEFAULT_CHECKED_TEXT_COLOR);
            mUncheckedTextColor = a.getColor(R.styleable.ToggleButton_uncheckedTextColor, DEFAULT_UNCHECKED_TEXT_COLOR);

            Drawable buttonBackground = a.getDrawable(R.styleable.ToggleButton_buttonBackground);
            setButtonBackground(buttonBackground);

            Drawable checkedBackground = a.getDrawable(R.styleable.ToggleButton_checkedBackground);
            if (checkedBackground == null) {
                checkedBackground = ContextCompat.getDrawable(context, R.drawable.ic_circle_48dp);
            }
            setCheckedBackground(checkedBackground);

            // noinspection ResourceType
            mAnimationType = a.getInt(R.styleable.ToggleButton_animationType, 0);
            mAnimationDuration = a.getInt(R.styleable.ToggleButton_animationDuration, DEFAULT_ANIMATION_DURATION);
        } finally {
            a.recycle();
        }

        mTextColorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(), mUncheckedTextColor, mCheckedTextColor);
        mTextColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTvText.setTextColor((Integer)valueAnimator.getAnimatedValue());
            }
        });
        mTextColorAnimator.setDuration(mAnimationDuration);
        updateAnimationType(mAnimationType);
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        mIsChecked = isChecked;
        mIvBg.setVisibility(mIsChecked ? View.VISIBLE : View.INVISIBLE);
        updateTextColor();
    }

    public void setChecked(boolean isChecked, boolean animate) {
        if (!animate) {
            setChecked(isChecked);
        } else {
            mIsChecked = isChecked;
            if (mIsChecked) {
                mIvBg.setVisibility(View.VISIBLE);
                mIvBg.startAnimation(mCheckAnimation);
                mTextColorAnimator.start();
            } else {
                mIvBg.setVisibility(View.VISIBLE);
                mIvBg.startAnimation(mUncheckAnimation);
                mTextColorAnimator.reverse();
            }
        }
    }

    public void setTextSize(float px) {
        mTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }

    public float getTextSize() {
        return mTvText.getTextSize();
    }

    public void setText(CharSequence text) {
        mTvText.setText(text);
    }

    public void setTextPadding(int left, int top, int right, int bottom) {
        mTvText.setPadding(left, top, right, bottom);
    }

    public void setTextColor(int color) {
        mTvText.setTextColor(color);
    }

    public void setCheckedTextColor(int color) {
        mCheckedTextColor = color;
        mTextColorAnimator.setIntValues(mUncheckedTextColor, mCheckedTextColor);
        updateTextColor();
    }

    public void setUncheckedTextColor(int color) {
        mUncheckedTextColor = color;
        mTextColorAnimator.setIntValues(mUncheckedTextColor, mCheckedTextColor);
        updateTextColor();
    }

    public void setCheckedBackground(Drawable drawable) {
        mCheckedBackground = drawable;
        mIvBg.setImageDrawable(drawable);
    }

    public Drawable getCheckedBackground() {
        return mCheckedBackground;
    }

    @SuppressWarnings("deprecation")
    public void setButtonBackground(Drawable drawable) {
        mButtonBackground = drawable;
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mContainer.setBackgroundDrawable(drawable);
        } else {
            mContainer.setBackground(drawable);
        }
    }

    public Drawable getButtonBackground() {
        return mButtonBackground;
    }

    public void setAnimationType(@AnimationType int animationType) {
        mAnimationType = animationType;
        updateAnimationType(mAnimationType);
    }

    public void setAnimationDuration(long duration) {
        mAnimationDuration = duration;
        if (mCheckAnimation != null && mUncheckAnimation != null) {
            mCheckAnimation.setDuration(duration);
            mUncheckAnimation.setDuration(duration);
        } else {
            Log.e(LOG_TAG, "setAnimationDuration(): Animation is disabled, cannot apply animation duration.");
        }
    }

    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    public void setButtonSize(int width, int height) {
        mContainer.setLayoutParams(new FrameLayout.LayoutParams(width, height));
        setLayoutParams(new FrameLayout.LayoutParams(width, height));
        invalidate();
    }

    private void updateTextColor() {
        if (mIsChecked) {
            setTextColor(mCheckedTextColor);
        } else {
            setTextColor(mUncheckedTextColor);
        }
    }

    private void updateAnimationType(@AnimationType int animationType) {
        if (animationType == ANIMATION_ALPHA) {
            mCheckAnimation = new AlphaAnimation(0, 1);
            mUncheckAnimation = new AlphaAnimation(1, 0);
        } else if (animationType == ANIMATION_SCALE){
            mCheckAnimation = new ScaleAnimation(0, 1, 0, 1,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mUncheckAnimation = new ScaleAnimation(1, 0, 1, 0,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            return;
        }

        mCheckAnimation.setDuration(mAnimationDuration);
        mCheckAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                updateTextColor();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        mUncheckAnimation.setDuration(mAnimationDuration);
        mUncheckAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvBg.setVisibility(View.INVISIBLE);
                updateTextColor();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private float dpToPx(float dp){
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
