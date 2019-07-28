package com.nex3z.togglebuttongroup.button;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import com.nex3z.togglebuttongroup.R;

public class CircularToggle extends MarkerButton {
    private static final String LOG_TAG = CircularToggle.class.getSimpleName();

    private static final int DEFAULT_ANIMATION_DURATION = 150;

    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private Animation mCheckAnimation;
    private Animation mUncheckAnimation;
    private ValueAnimator mTextColorAnimator;

    public CircularToggle(Context context) {
        this(context, null);
    }

    public CircularToggle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void setMarkerColor(int markerColor) {
        super.setMarkerColor(markerColor);
        initBackground();
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        initAnimation();
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        initAnimation();
    }

    private void init() {
        initBackground();
        initAnimation();
        initText();
    }

    private void initBackground() {
        GradientDrawable checked = (GradientDrawable) ContextCompat.getDrawable(
                getContext(), R.drawable.bg_circle);
        checked.setColor(mMarkerColor);
        mIvBg.setImageDrawable(checked);
    }

    private void initText() {
        mTvText.setBackgroundDrawable(null);
    }

    private void initAnimation() {
        final int defaultTextColor = getDefaultTextColor();
        final int checkedTextColor = getCheckedTextColor();

        mTextColorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(), defaultTextColor, checkedTextColor);
        mTextColorAnimator.setDuration(mAnimationDuration);
        mTextColorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mTvText.setTextColor((Integer)valueAnimator.getAnimatedValue());
            }
        });

        mCheckAnimation = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mCheckAnimation.setDuration(mAnimationDuration);
        mCheckAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mTvText.setTextColor(checkedTextColor);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        mUncheckAnimation = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mUncheckAnimation.setDuration(mAnimationDuration);
        mUncheckAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvBg.setVisibility(INVISIBLE);
                mTvText.setTextColor(defaultTextColor);}

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }


    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        if (checked) {
            mIvBg.setVisibility(VISIBLE);
            mIvBg.startAnimation(mCheckAnimation);
            mTextColorAnimator.start();
        } else {
            mIvBg.setVisibility(VISIBLE);
            mIvBg.startAnimation(mUncheckAnimation);
            mTextColorAnimator.reverse();
        }
    }

}
