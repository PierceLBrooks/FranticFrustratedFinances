
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;

public class MailFragment extends SerialFragment<MailSerial> implements Accountant
{
    private static final String TAG = "F3-MailFrag";

    private Ledger ledger;

    public MailFragment()
    {
        super();
        ledger = null;
    }

    @Override
    protected MailSerial getSerial()
    {
        return null;
    }

    @Override
    protected String getTitle()
    {
        return "MAIL";
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showLaunch();
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.MAIL;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return MailFragment.class;
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
}
