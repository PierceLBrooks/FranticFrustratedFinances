
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.inputmethod.EditorInfo;

import com.piercelbrooks.common.Utilities;

import java.io.File;
import java.util.ArrayList;

public class LedgerNameFragment extends EditorFragment implements Accountant
{
    private static final String TAG = "F3-LedgerNameFrag";

    private Ledger ledger;

    public LedgerNameFragment()
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
        ArrayList<String> ledgers = new ArrayList<>();
        if (Utilities.read(Ledger.getPath()+"ledgers.dat", ledgers))
        {
            String ledger;
            for (int i = 0; i != ledgers.size(); ++i)
            {
                ledger = ledgers.get(i).trim();
                if (ledger.equals(this.ledger.getName().trim()))
                {
                    Utilities.delete(Ledger.getPath()+"ledgers"+File.separator+ledger+".dat");
                    ledgers.set(i, field.trim());
                    continue;
                }
                ledgers.set(i, ledger);
            }
            if (!Utilities.write(Ledger.getPath()+"ledgers.dat", ledgers))
            {
                Log.e(TAG, "Could not update ledgers!");
            }
        }
        if (ledger != null)
        {
            ledger.setName(field.trim());
            if (Ledger.getCurrent() == ledger)
            {
                Ledger.setCurrent(null);
                Ledger.setCurrent(ledger);
            }
            else
            {
                Ledger other = Ledger.getCurrent();
                Ledger.setCurrent(ledger);
                Ledger.setCurrent(other);
            }
        }
        ((MainActivity)getMunicipality()).showLobby(ledger);
    }

    @Override
    public String getTitle()
    {
        return "LEDGER NAME";
    }

    @Override
    public String getField()
    {
        if (ledger != null)
        {
            return ledger.getName();
        }
        return "";
    }

    @Override
    public int getInputType()
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
