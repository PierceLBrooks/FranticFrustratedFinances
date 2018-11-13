
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicCalendar;
import com.piercelbrooks.common.BasicListFragment;

import java.util.Calendar;

public class MonthsFragment extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-MonthsFrag";

    private int selectionIndex;
    private View selection;
    private Button monthExit;
    private Button monthView;
    private Ledger ledger;

    public MonthsFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        monthExit = null;
        monthView = null;
        ledger = null;
    }

    @Override
    protected boolean itemLabelAffix()
    {
        return true;
    }

    @Override
    protected void itemClick(View view, int position)
    {
        if (selection != null)
        {
            selection.setBackground(ContextCompat.getDrawable(getContext(), BasicApplication.getInstance().getEmptyDrawable()));
            selectionIndex = -1;
        }
        selection = view;
        if (selection != null)
        {
            selection.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.outline));
            selectionIndex = position;
        }
    }

    @Override
    protected @IdRes int getItemID()
    {
        return R.id.month_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.month_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.months_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        selection = null;

        monthExit = view.findViewById(R.id.months_exit);
        monthView = view.findViewById(R.id.months_view);

        monthExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showYears(ledger);
            }
        });
        monthView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectionIndex < 0)
                {
                    return;
                }
                ledger.setTargetDateTime(new DateTime(ledger.getTargetDateTime(), BasicCalendar.getMonth(getItemLabel(selectionIndex)), DateTimeMember.MONTH));
                ((MainActivity)getMunicipality()).showCalendar(ledger);
            }
        });

        load();
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
        return MayoralFamily.MONTHS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return MonthsFragment.class;
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

    public void unload()
    {
        removeItems();
    }

    public void load()
    {
        int month = Calendar.JANUARY;
        while (month <= Calendar.DECEMBER)
        {
            addItem(BasicCalendar.getMonthName(month));
            ++month;
        }
    }
}
