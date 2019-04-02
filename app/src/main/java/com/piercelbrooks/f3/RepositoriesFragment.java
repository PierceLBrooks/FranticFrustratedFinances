
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

public class RepositoriesFragment extends BasicListFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-ReposFrag";

    private int selectionIndex;
    private View selection;
    private Button repositoriesExit;
    private Button repositoriesRemove;
    private Button repositoriesAdd;
    private Button repositoriesEdit;
    private Ledger ledger;

    public RepositoriesFragment()
    {
        super();
        selectionIndex = -1;
        selection = null;
        repositoriesExit = null;
        repositoriesRemove = null;
        repositoriesAdd = null;
        repositoriesEdit = null;
    }

    @Override
    protected boolean itemLabelAffix()
    {
        return true;
    }

    @Override
    protected void itemClick(View view, int position)
    {
        ledger.setTargetRepository(ledger.getRepositories().get(position));
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
        return R.layout.repositories_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        selection = null;

        repositoriesExit = view.findViewById(R.id.repositories_exit);
        repositoriesRemove = view.findViewById(R.id.repositories_remove);
        repositoriesAdd = view.findViewById(R.id.repositories_add);
        repositoriesEdit = view.findViewById(R.id.repositories_edit);

        repositoriesExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLobby(ledger);
            }
        });
        repositoriesRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (selectionIndex >= 0)
                {
                    ledger.getRepositories().remove(selectionIndex);
                    removeItem(selectionIndex);
                    selectionIndex = -1;
                }
            }
        });
        repositoriesAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addItem(""+getItemCount());
                ledger.getRepositories().add(new Repository(ledger, getItemLabel(getItemCount()-1)));
            }
        });
        repositoriesEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showRepository(ledger.getTargetRepository());
            }
        });

        addItems(ledger.getRepositories().getNames());
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
        return MayoralFamily.REPOSITORIES;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return RepositoriesFragment.class;
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
