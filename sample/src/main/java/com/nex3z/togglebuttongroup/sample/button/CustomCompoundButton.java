package com.nex3z.togglebuttongroup.sample.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import com.nex3z.togglebuttongroup.sample.R;

public class CustomCompoundButton extends CompoundButton {

    public CustomCompoundButton(Context context) {
        this(context, null);
    }

    public CustomCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.customCompoundButtonStyle);
    }

    public CustomCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
