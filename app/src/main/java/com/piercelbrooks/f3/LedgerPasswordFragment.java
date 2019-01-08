
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.inputmethod.EditorInfo;

public class LedgerPasswordFragment extends EditorFragment implements Accountant
{
    private static final String TAG = "F3-LedgerPassFrag";

    private Ledger ledger;

    public LedgerPasswordFragment()
    {
        super();
        ledger = null;
    }

    @Override
    public void onExit()
    {
        ((MainActivity)getMunicipality()).showLobby(ledger);
    }

    @Override
    public void onSave(String field)
    {
        if (field == null)
        {
            return;
        }
        if (ledger != null)
        {
            ledger.setPassword(field);
        }
        ((MainActivity)getMunicipality()).showLobby(ledger);
    }

    @Override
    public String getTitle()
    {
        return "LEDGER PASSWORD";
    }

    @Override
    public String getField()
    {
        if (ledger != null)
        {
            return ledger.getPassword();
        }
        Log.d(TAG, "NULL LEDGER");
        return "";
    }

    @Override
    public int getInputType()
    {
        return EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.LEDGER_PASSWORD;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return LedgerPasswordFragment.class;
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
