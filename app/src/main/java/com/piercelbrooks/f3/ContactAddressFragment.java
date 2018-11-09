
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.view.inputmethod.EditorInfo;

public class ContactAddressFragment extends EditorFragment
{
    private static final String TAG = "F3-ContactFrag";

    private Contact contact;

    public ContactAddressFragment()
    {
        super();
        contact = null;
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getActivity()).showContacts(contact.getOwner());
    }

    @Override
    protected void onSave(String field)
    {
        if (contact != null)
        {
            contact.setAddress(field);
        }
        ((MainActivity)getActivity()).showContacts(contact.getOwner());
    }

    @Override
    protected String getTitle()
    {
        return "CONTACT ADDRESS";
    }

    @Override
    protected String getField()
    {
        if (contact != null)
        {
            return contact.getAddress();
        }
        return "";
    }

    @Override
    protected int getInputType()
    {
        return EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
    }

    public void setContact(Contact contact)
    {
        this.contact = contact;
    }

    public Contact getContact()
    {
        return contact;
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.CONTACT_ADDRESS;
    }
}
