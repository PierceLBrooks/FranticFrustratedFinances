
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

public class ActionsFragment extends BasicListFragment
{
    private static final String TAG = "F3-ActionsFrag";

    private int selectionIndex;
    private View selection;
    private Button actionExit;
    private Button actionRemove;
    private Button actionAdd;
    private Button actionEdit;
    private Ledger ledger;

    public ActionsFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        actionExit = null;
        actionRemove = null;
        actionAdd = null;
        actionEdit = null;
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
    protected @IdRes int getItemID()
    {
        return R.id.action_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.action_item;
    }

    @Override
    protected @LayoutRes int getLayout()
    {
        return R.layout.actions_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        selection = null;

        actionExit = view.findViewById(R.id.actions_exit);
        actionRemove = view.findViewById(R.id.actions_remove);
        actionAdd = view.findViewById(R.id.actions_add);
        actionEdit = view.findViewById(R.id.actions_edit);

        actionExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showLobby(ledger);
            }
        });
        actionRemove.setOnClickListener(new View.OnClickListener()
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
        actionAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem(""+getItemCount());
            }
        });
        actionEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showAuthor(ledger.getTargetAction());
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
