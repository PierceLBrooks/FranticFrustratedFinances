
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.view.inputmethod.EditorInfo;

public class LedgerNameFragment extends EditorFragment implements Accountant
{
    private static final String TAG = "F3-ContactFrag";

    private Ledger ledger;

    public LedgerNameFragment()
    {
        super();
        ledger = null;
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showLobby(ledger);
    }

    @Override
    protected void onSave(String field)
    {
        if (ledger != null)
        {
            ledger.setName(field);
        }
        ((MainActivity)getMunicipality()).showLobby(ledger);
    }

    @Override
    protected String getTitle()
    {
        return "LEDGER NAME";
    }

    @Override
    protected String getField()
    {
        if (ledger != null)
        {
            return ledger.getName();
        }
        return "";
    }

    @Override
    protected int getInputType()
    {
        return EditorInfo.TYPE_CLASS_TEXT;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.LEDGER_NAME;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return LedgerNameFragment.class;
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
