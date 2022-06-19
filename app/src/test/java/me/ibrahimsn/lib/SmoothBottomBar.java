package me.ibrahimsn.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SmoothBottomBar extends View {
    public SmoothBottomBar(Context context) {
        this(context, null);
    }

    public SmoothBottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmoothBottomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
