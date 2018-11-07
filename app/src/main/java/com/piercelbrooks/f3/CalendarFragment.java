
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;

import com.piercelbrooks.common.BasicFragment;

public class CalendarFragment extends BasicFragment
{
    private static final String TAG = "F3-CalendarFrag";

    private Month month;
    private Ledger ledger;

    public CalendarFragment()
    {
        super();
        ledger = null;
        month = null;
    }

    @Override
    protected int getLayout()
    {
        return R.layout.calendar_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        month = new Month(getContext());
        month.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.outline));
        ((ViewGroup)view.findViewById(R.id.calendar_month_slot)).addView(month, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        view.requestLayout();
    }

    @Override
    protected void onBirth()
    {

    }

    @Override
    protected void onDeath()
    {

    }

    public Month getMonth()
    {
        return month;
    }

    public void setLedger(Ledger ledger)
    {
        this.ledger = ledger;
    }

    public Ledger getLedger()
    {
        return ledger;
    }
}
