package com.nex3z.togglebuttongroup.sample.button;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nex3z.togglebuttongroup.button.CompoundToggleButton;
import com.nex3z.togglebuttongroup.sample.R;

public class CustomCompoundToggleButton extends CompoundToggleButton {
    private static final String LOG_TAG = CustomCompoundToggleButton.class.getSimpleName();
    private ImageView mIvFront;
    private ImageView mIvBack;
    private AnimatorSet mFlipOut;
    private AnimatorSet mFlipIn;
    private boolean mPlaying;

    public CustomCompoundToggleButton(Context context) {
        this(context, null);
    }

    public CustomCompoundToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_custom_compound_toggle_button, this, true);

        mIvFront = findViewById(R.id.iv_front);
        mIvBack = findViewById(R.id.iv_back);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CustomCompoundToggleButton, 0, 0);
        try {
            Drawable front = a.getDrawable(R.styleable.CustomCompoundToggleButton_tbgFrontImage);
            mIvFront.setImageDrawable(front);
            Drawable back = a.getDrawable(R.styleable.CustomCompoundToggleButton_tbgBackImage);
            mIvBack.setImageDrawable(back);
        } finally {
            a.recycle();
        }

        CheckedAnimationListener animationListener = new CheckedAnimationListener();
        mFlipOut = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.flip_out);
        mFlipOut.addListener(animationListener);
        mFlipIn = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(),
                R.animator.flip_in);
        mFlipIn.addListener(animationListener);
    }

    @Override
    public boolean performClick() {
        return (!isChecked()) && (!mPlaying) && super.performClick();
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);
        playAnimation(checked);
    }

    private void playAnimation(boolean checked) {
        if (checked) {
            mFlipOut.setTarget(mIvFront);
            mFlipOut.start();
            mFlipIn.setTarget(mIvBack);
            mFlipIn.start();
        } else {
            mFlipOut.setTarget(mIvBack);
            mFlipOut.start();
            mFlipIn.setTarget(mIvFront);
            mFlipIn.start();
        }
    }

    private class CheckedAnimationListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {
            mPlaying = true;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mPlaying = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            mPlaying = false;
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
            mPlaying = true;
        }
    }
}
