
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicListFragment;

import java.util.Calendar;

public class YearsFragment extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-YearsFrag";

    private int selectionIndex;
    private View selection;
    private Button yearExit;
    private Button yearView;
    private Ledger ledger;

    public YearsFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        yearExit = null;
        yearView = null;
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
        return R.id.year_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.year_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.years_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        selection = null;

        yearExit = view.findViewById(R.id.years_exit);
        yearView = view.findViewById(R.id.years_view);

        yearExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLobby(ledger);
            }
        });
        yearView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectionIndex < 0)
                {
                    return;
                }
                ledger.setTargetDateTime(new DateTime(ledger.getTargetDateTime(), Integer.parseInt(getItemLabel(selectionIndex)), DateTimeMember.YEAR));
                ((MainActivity)getMunicipality()).showMonths(ledger);
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
    public void setLedger(@NonNull Ledger ledger)
    {
        this.ledger = ledger;
    }

    @Override
    public Ledger getLedger()
    {
        return ledger;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.YEARS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return YearsFragment.class;
    }

    public void unload()
    {
        removeItems();
    }

    public void load()
    {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        addItem(""+year);
    }
}
