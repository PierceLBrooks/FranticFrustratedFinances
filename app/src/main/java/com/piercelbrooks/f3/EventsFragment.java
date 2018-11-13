
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

public class EventsFragment extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-eventsFrag";

    private int selectionIndex;
    private View selection;
    private Button eventExit;
    private Button eventView;
    private Ledger ledger;

    public EventsFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        eventExit = null;
        eventView = null;
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
        return R.id.event_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.event_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.events_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        selection = null;

        eventExit = view.findViewById(R.id.events_exit);
        eventView = view.findViewById(R.id.events_view);

        eventExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showCalendar(ledger);
            }
        });
        eventView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ledger.setTargetEvent(new Event(ledger, ledger.getTargetDateTime(), selectionIndex));
                ((MainActivity)getMunicipality()).showReview(ledger);
            }
        });
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
        return MayoralFamily.EVENTS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return EventsFragment.class;
    }
}
