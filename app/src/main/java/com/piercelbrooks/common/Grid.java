
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public abstract class Grid <T extends View> extends LinearLayout
{
    public class Slot
    {

    }

    public Grid(Context context)
    {
        super(context);
        initialize(context);
    }

    public Grid(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    public Grid(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    public Grid(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context);
    }

    private void initialize(Context context)
    {

    }
}
