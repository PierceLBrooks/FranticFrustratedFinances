
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public abstract class BasicCalendar <T extends ViewGroup> extends Grid<T> implements GridListener<T>
{
    private static final String TAG = "PLB-BasicCalendar";

    private Integer days = null;

    public abstract @ColorRes int getSlotLabelColor();
    public abstract @DrawableRes int getSlotBackground();
    public abstract int getYear();
    public abstract int getMonth();
    public abstract T getNewSlot(Context context);
    public abstract void onClick(int day, int column, int row);

    public BasicCalendar(Context context)
    {
        super(context);
    }

    public BasicCalendar(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BasicCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public BasicCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public int getDays()
    {
        if (days != null)
        {
            return days;
        }
        GregorianCalendar calendar = new GregorianCalendar(getYear(), getMonth(), 1);
        days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    @Override
    public void onClick(@NonNull Grid<T>.Slot slot)
    {
        int column = slot.getColumn();
        int row = slot.getRow();
        onClick(getIndex(column, row), column, row);
    }

    @Override
    protected void onInitialize(Context context)
    {
        if (getDays() > getSlotCount())
        {
            throw new RuntimeException("Bad calendar format!");
        }
        setListener(this);
    }

    @Override
    protected T getNewSlot(Context context, int column, int row)
    {
        int index = getIndex(column, row);
        TextView label = new TextView(context);
        T slot = getNewSlot(context);
        T.LayoutParams params = new T.LayoutParams(T.LayoutParams.MATCH_PARENT, T.LayoutParams.MATCH_PARENT);
        slot.addView(label, params);
        slot.setBackground(ContextCompat.getDrawable(context, getSlotBackground()));
        label.setGravity(Gravity.CENTER);
        label.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
        label.setTextColor(ContextCompat.getColor(context, getSlotLabelColor()));
        if (index < getDays())
        {
            label.setText(""+index);
        }
        else
        {
            label.setText("");
        }
        slot.requestLayout();
        Log.d(TAG, ""+index);
        return slot;
    }

    @Override
    protected float getSlotWidth(int column, int row)
    {
        return 1.0f;
    }

    @Override
    protected float getSlotHeight(int column, int row)
    {
        return 1.0f;
    }

    @Override
    protected int getSlotsPerRow()
    {
        return 8;
    }

    @Override
    protected int getSlotsPerColumn()
    {
        return 4;
    }
}
