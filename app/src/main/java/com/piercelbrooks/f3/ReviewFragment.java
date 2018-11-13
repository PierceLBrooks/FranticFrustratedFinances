
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;

public class ReviewFragment extends SerialFragment<Event> implements Accountant
{
    private static final String TAG = "F3-ReviewFrag";

    private Ledger ledger;

    public ReviewFragment()
    {
        super();
        ledger = null;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.REVIEW;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return ReviewFragment.class;
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
    protected Event getSerial()
    {
        return Ledger.getCurrent().getTargetEvent();
    }

    @Override
    protected String getTitle()
    {
        return "REVIEW";
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showEvents(ledger);
    }
}
