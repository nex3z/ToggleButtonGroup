package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToggleButton {
    private static final String LOG_TAG = ToggleButton.class.getSimpleName();

    private static final long DEFAULT_ANIMATION_DURATION = 150;

    private boolean mIsChecked;
    private float mButtonSize;

    private boolean mIsAnimationEnabled;
    private long mAnimationDuration = DEFAULT_ANIMATION_DURATION;
    private Animation mExpand;
    private Animation mShrink;

    private View mRootView;
    private ImageView mIvCheckedBg;
    private TextView mTvText;

    public ToggleButton(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.item_toggle_button, null);

        mIvCheckedBg = (ImageView)mRootView.findViewById(R.id.iv_background);
        mIvCheckedBg.setVisibility(View.GONE);

        mTvText = (TextView)mRootView.findViewById(R.id.tv_text);

        mExpand = new ScaleAnimation(0, 1, 0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mExpand.setDuration(mAnimationDuration);

        mShrink = new ScaleAnimation(1, 0, 1, 0,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mShrink.setDuration(mAnimationDuration);
        mShrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mIvCheckedBg.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    public void setOnClickListener(View.OnClickListener listener) {
        mRootView.setOnClickListener(listener);
    }

    public boolean changeCheckedState() {
        mIsChecked = !mIsChecked;

        if (mIsAnimationEnabled) {
            if (mIsChecked) {
                mIvCheckedBg.setVisibility(View.VISIBLE);
                mIvCheckedBg.startAnimation(mExpand);
            } else {
                mIvCheckedBg.setVisibility(View.VISIBLE);
                mIvCheckedBg.startAnimation(mShrink);
            }
        } else {
            mIvCheckedBg.setVisibility(mIsChecked ? View.VISIBLE : View.GONE);
        }
        return mIsChecked;
    }

    public View getView() {
        return mRootView;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean isChecked) {
        mIsChecked = isChecked;
        mIvCheckedBg.setVisibility(mIsChecked ? View.VISIBLE : View.GONE);
    }

    public String getText() {
        return mTvText.getText().toString();
    }

    public void setText(String text) {
        mTvText.setText(text);
    }

    public void setTextSizePx(float pixels) {
        mTvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, pixels);
    }

    public void setTextSizeSp(float sp) {
        mTvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }

    public float getTextSize() {
        return mTvText.getTextSize();
    }

    public void setTextColor(int color) {
        mTvText.setTextColor(color);
    }

    public int getTextColor() {
        return mTvText.getCurrentTextColor();
    }

    public void setButtonSize(float size) {
        mButtonSize = size;
        mRootView.setLayoutParams(
                new LinearLayout.LayoutParams((int) mButtonSize, (int) mButtonSize));
    }

    public float getButtonSize() {
        return mButtonSize;
    }

    public boolean isAnimationEnabled() {
        return mIsAnimationEnabled;
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        mIsAnimationEnabled = animationEnabled;
    }

    public Drawable getCheckedBackgroundDrawable() {
        return mIvCheckedBg.getDrawable();
    }

    public void setCheckedBackgroundDrawable(Drawable drawable) {
        mIvCheckedBg.setImageDrawable(drawable);
    }

    public long getAnimationDuration() {
        return mAnimationDuration;
    }

    public void setAnimationDuration(long animationDuration) {
        mAnimationDuration = animationDuration;
        mShrink.setDuration(mAnimationDuration);
        mExpand.setDuration(mAnimationDuration);
    }

}
