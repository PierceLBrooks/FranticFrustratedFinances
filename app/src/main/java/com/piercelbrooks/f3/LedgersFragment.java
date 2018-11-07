
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

public class LedgersFragment extends BasicListFragment
{
    private static final String TAG = "F3-LedgersFrag";

    private int selectionIndex;
    private View selection;
    private Button ledgerExit;
    private Button ledgerRemove;
    private Button ledgerAdd;
    private Button ledgerEdit;

    public LedgersFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        ledgerExit = null;
        ledgerRemove = null;
        ledgerAdd = null;
        ledgerEdit = null;
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
        return R.id.ledger_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.ledger_item;
    }

    @Override
    protected @LayoutRes int getLayout()
    {
        return R.layout.ledgers_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        selection = null;

        ledgerExit = view.findViewById(R.id.ledgers_exit);
        ledgerRemove = view.findViewById(R.id.ledgers_remove);
        ledgerAdd = view.findViewById(R.id.ledgers_add);
        ledgerEdit = view.findViewById(R.id.ledgers_edit);

        ledgerExit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
            }
        });
        ledgerRemove.setOnClickListener(new View.OnClickListener()
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
        ledgerAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem(""+getItemCount());
            }
        });
        ledgerEdit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showLobby(new Ledger(getItemLabel(selectionIndex)));
            }
        });
    }

    @Override
    protected void onBirth()
    {
        Ledger.setCurrent(null);
    }

    @Override
    protected void onDeath()
    {

    }
}
