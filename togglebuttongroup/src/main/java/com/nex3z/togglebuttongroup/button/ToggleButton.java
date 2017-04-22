package com.nex3z.togglebuttongroup.button;

import android.widget.Checkable;

public interface ToggleButton extends Checkable {

    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener);

}
