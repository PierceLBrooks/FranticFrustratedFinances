
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicListFragment;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.roe.Mailbox;
import com.piercelbrooks.roe.MailboxListener;

public abstract class MailboxFragment <T extends MailboxListener, U extends Mailbox<T>> extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-MailboxFrag";

    private int selectionIndex;
    private View selection;
    private Ledger ledger;
    private U box;

    protected abstract U getNewBox(@NonNull T listener);

    public MailboxFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        ledger = null;
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
    public void createView(@NonNull View view)
    {
        selection = null;
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

    public U getBox()
    {
        return box;
    }

    public int getSelectionIndex()
    {
        return selectionIndex;
    }

    public boolean go(@NonNull T listener)
    {
        if (box != null)
        {
            return false;
        }
        box = getNewBox(listener);
        box.execute();
        return true;
    }

    public BasicApplication getApplication()
    {
        return (BasicApplication)Governor.getInstance().getCitizen(Family.APPLICATION);
    }
}
