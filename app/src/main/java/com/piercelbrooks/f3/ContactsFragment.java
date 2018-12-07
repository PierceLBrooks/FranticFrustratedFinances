
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
import com.piercelbrooks.common.Utilities;

public class ContactsFragment extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-ContactsFrag";

    private int selectionIndex;
    private View selection;
    private Button contactsExit;
    private Button contactsRemove;
    private Button contactsAdd;
    private Button contactsEdit;
    private Ledger ledger;

    public ContactsFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        contactsExit = null;
        contactsRemove = null;
        contactsAdd = null;
        contactsEdit = null;
    }

    @Override
    protected boolean itemLabelAffix()
    {
        return true;
    }

    @Override
    protected void itemClick(View view, int position)
    {
        ledger.setTargetContact(ledger.getContacts().get(position));
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
        return R.id.contact_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.contact_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.contacts_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        selection = null;

        contactsExit = view.findViewById(R.id.contacts_exit);
        contactsRemove = view.findViewById(R.id.contacts_remove);
        contactsAdd = view.findViewById(R.id.contacts_add);
        contactsEdit = view.findViewById(R.id.contacts_edit);

        contactsExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLobby(ledger);
            }
        });
        contactsRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectionIndex >= 0)
                {
                    ledger.getContacts().remove(selectionIndex);
                    removeItem(selectionIndex);
                    selectionIndex = -1;
                }
            }
        });
        contactsAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem(""+getItemCount());
                ledger.getContacts().add(new Contact(ledger, getItemLabel(getItemCount()-1)));
            }
        });
        contactsEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showContactAddress(ledger.getTargetContact());
            }
        });

        addItems(ledger.getContacts().getAddresses());
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
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.CONTACTS;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return ContactsFragment.class;
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
