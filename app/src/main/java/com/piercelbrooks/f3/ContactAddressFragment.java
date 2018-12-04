
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;
import android.view.inputmethod.EditorInfo;

public class ContactAddressFragment extends EditorFragment implements Accountant
{
    private static final String TAG = "F3-ContactFrag";

    private Ledger ledger;

    public ContactAddressFragment()
    {
        super();
        ledger = null;
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showContacts(ledger);
    }

    @Override
    protected void onSave(String field)
    {
        getContact().setAddress(field);
        Log.d(TAG, "LEDGER SERIAL TEST:\n"+getContact().getOwner());
        ((MainActivity)getMunicipality()).showContacts(ledger);
    }

    @Override
    protected String getTitle()
    {
        return "CONTACT ADDRESS";
    }

    @Override
    protected String getField()
    {
        return getContact().getAddress();
    }

    @Override
    protected int getInputType()
    {
        return EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.CONTACT_ADDRESS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return ContactAddressFragment.class;
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


    public Contact getContact()
    {
        return ledger.getTargetContact();
    }
}
