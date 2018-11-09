
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;

public class CalendarFragment extends BasicFragment<MayoralFamily>
{
    private static final String TAG = "F3-CalendarFrag";

    private TextView year;
    private TextView month;
    private Month calendar;
    private Ledger ledger;

    public CalendarFragment()
    {
        super();
        ledger = null;
        calendar = null;
    }

    @Override
    protected int getLayout()
    {
        return R.layout.calendar_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        year = view.findViewById(R.id.calendar_year);
        year.setText(""+ledger.getTargetDateTime().getYear());
        month = view.findViewById(R.id.calendar_month);
        month.setText(Month.getMonthName(ledger.getTargetDateTime().getMonth()));
        calendar = new Month(getContext());
        calendar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.outline));
        ((ViewGroup)view.findViewById(R.id.calendar_month_slot)).addView(calendar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
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
        return calendar;
    }

    public void setLedger(Ledger ledger)
    {
        this.ledger = ledger;
    }

    public Ledger getLedger()
    {
        return ledger;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.CALENDAR;
    }
}
