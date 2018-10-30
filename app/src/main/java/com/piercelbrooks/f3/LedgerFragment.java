
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

public class LedgerFragment extends BasicListFragment {
    private static final String TAG = "F3-LedgerFrag";

    private int selectionIndex;
    private View selection;
    private Button ledgerExit;
    private Button ledgerRemove;
    private Button ledgerAdd;
    private Button ledgerEdit;

    public LedgerFragment()
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
        return R.id.action_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.action_item;
    }

    @Override
    protected @LayoutRes int getInflationResource()
    {
        return R.layout.action_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        selection = null;

        ledgerExit = view.findViewById(R.id.action_exit);
        ledgerRemove = view.findViewById(R.id.action_remove);
        ledgerAdd = view.findViewById(R.id.action_add);
        ledgerEdit = view.findViewById(R.id.action_edit);

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
                ((MainActivity)getActivity()).showLobby(new Ledger());
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
}
