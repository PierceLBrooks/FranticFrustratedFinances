
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;

public class CalendarFragment extends BasicFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-CalendarFrag";

    private TextView year;
    private TextView month;
    private Month calendar;
    private Button exit;
    private Button view;
    private Ledger ledger;

    public CalendarFragment()
    {
        super();
        ledger = null;
        calendar = null;
        year = null;
        month = null;
        exit = null;
        view = null;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.calendar_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        this.exit = view.findViewById(R.id.calendar_exit);
        this.exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showMonths(ledger);
            }
        });
        this.view = view.findViewById(R.id.calendar_view);
        this.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int day = calendar.getDay();
                if (day < 0)
                {
                    return;
                }
                ledger.setTargetDateTime(new DateTime(ledger.getTargetDateTime(), day, DateTimeMember.DAY));
                ((MainActivity)getMunicipality()).showEvents(ledger);
            }
        });
        this.year = view.findViewById(R.id.calendar_year);
        this.year.setText(""+ledger.getTargetDateTime().getYear());
        this.month = view.findViewById(R.id.calendar_month);
        this.month.setText(Month.getMonthName(this.ledger.getTargetDateTime().getMonth()));
        this.calendar = new Month(getContext());
        this.calendar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.outline));
        ((ViewGroup)view.findViewById(R.id.calendar_month_slot)).addView(this.calendar, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.requestLayout();
    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
    {

    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.CALENDAR;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return CalendarFragment.class;
    }

    @Override
    public void setLedger(Ledger ledger)
    {
        this.ledger = ledger;
    }

    @Override
    public Ledger getLedger()
    {
        return ledger;
    }

    public Month getMonth()
    {
        return calendar;
    }
}
