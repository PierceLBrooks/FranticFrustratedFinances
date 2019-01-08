
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.view.inputmethod.EditorInfo;

public class PasswordFragment extends GateFragment implements Accountant
{
    private static final String TAG = "F3-PassFrag";

    private Ledger ledger;

    public PasswordFragment()
    {
        super();
        ledger = null;
    }

    @Override
    public void onExit()
    {

    }

    @Override
    public boolean onEnter(String field)
    {
        String password = ledger.getPassword();
        if ((field == null) || (password == null))
        {
            if (field == password)
            {
                return true;
            }
            return false;
        }
        if (field.equals(password))
        {
            getMunicipality().getOwner().makeToast("Correct password!");
            return true;
        }
        return false;
    }

    @Override
    public String getTitle()
    {
        return "PASSWORD";
    }

    @Override
    public String getField()
    {
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
        return MayoralFamily.PASSWORD;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return PasswordFragment.class;
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
    public String getMessage()
    {
        return "Incorrect password!";
    }
}
