
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.view.inputmethod.EditorInfo;

public class LedgerNameFragment extends EditorFragment
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
        ((MainActivity)getActivity()).showLobby(ledger);
    }

    @Override
    protected void onSave(String field)
    {
        if (ledger != null)
        {
            ledger.setName(field);
        }
        ((MainActivity)getActivity()).showLobby(ledger);
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

    public void setLedger(Ledger ledger)
    {
        this.ledger = ledger;
    }

    public Ledger getLedger()
    {
        return ledger;
    }
}
