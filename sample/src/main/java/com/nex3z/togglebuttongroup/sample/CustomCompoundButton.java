package com.nex3z.togglebuttongroup.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

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
