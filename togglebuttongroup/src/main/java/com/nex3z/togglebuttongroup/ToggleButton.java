package com.nex3z.togglebuttongroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ToggleButton {

    private View mView;
    private ImageView mIvBackground;
    private TextView mTvText;
    private boolean mIsChecked = false;

    public ToggleButton(Context context) {
        this(LayoutInflater.from(context).inflate(R.layout.item_toggle_button, null));
    }

    public ToggleButton(View view) {
        this.mView = view;
        this.mIvBackground = (ImageView)view.findViewById(R.id.iv_background);
        this.mTvText = (TextView)view.findViewById(R.id.tv_text);
    }

    public View getView() {
        return mView;
    }

    public TextView getText() {
        return mTvText;
    }

    public ImageView getBackground() {
        return mIvBackground;
    }

    public void setChecked(boolean checked) {
        if (checked) {
            mIvBackground.setVisibility(View.VISIBLE);
        } else {
            mIvBackground.setVisibility(View.GONE);
        }
        mIsChecked = checked;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public boolean changeCheckedState() {
        mIsChecked = !mIsChecked;
        setChecked(mIsChecked);
        return mIsChecked;
    }

}
