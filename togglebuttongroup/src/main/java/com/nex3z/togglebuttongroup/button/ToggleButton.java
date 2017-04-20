package com.nex3z.togglebuttongroup.button;

import android.widget.Checkable;

import com.nex3z.togglebuttongroup.OnCheckedChangeListener;

public interface ToggleButton extends Checkable {

    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);

}
