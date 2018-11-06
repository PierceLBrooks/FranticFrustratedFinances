
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

public class ContactsFragment extends BasicListFragment
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
    protected void itemClick(View view, int position)
    {
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
    protected @LayoutRes int getLayout()
    {
        return R.layout.contacts_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
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
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showLobby(ledger);
            }
        });
        contactsRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectionIndex >= 0)
                {
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
            }
        });
        contactsEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showContactAddress(ledger.getTargetContact());
            }
        });
    }

    @Override
    protected void onBirth()
    {

    }

    @Override
    protected void onDeath()
    {

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
