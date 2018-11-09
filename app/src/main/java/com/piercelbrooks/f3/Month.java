
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.piercelbrooks.common.BasicCalendar;

import java.util.Date;

public class Month extends BasicCalendar<FrameLayout>
{
    private static final String TAG = "F3-Month";

    public Month(Context context)
    {
        super(context);
    }

    public Month(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public Month(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public Month(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public int getSlotLabelColor()
    {
        return R.color.calendar_day_label_color;
    }

    @Override
    public int getSlotBackground()
    {
        return R.drawable.calendar_day_background;
    }

    @Override
    public int getYear()
    {
        return getDate().getYear();
    }

    @Override
    public int getMonth()
    {
        return getDate().getMonth();
    }

    @Override
    public FrameLayout getNewSlot(Context context)
    {
        return new FrameLayout(context);
    }

    @Override
    public void onClick(int day, int column, int row)
    {
        if (day > getDays())
        {
            return;
        }
        Log.d(TAG, ""+(day+1));
    }

    public DateTime getDate()
    {
        return Ledger.getCurrent().getTargetDateTime();
    }
}
