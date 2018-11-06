
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Utilities;

public class LobbyFragment extends BasicFragment
{
    private static final String TAG = "F3-LobbyFrag";

    private Button lobbyActions;
    private Button lobbyRename;
    private Button lobbyContacts;
    private Button lobbyAccount;
    private Button lobbyFinish;
    private Ledger ledger;

    public LobbyFragment()
    {
        super();
        lobbyActions = null;
        lobbyRename = null;
        lobbyContacts = null;
        lobbyAccount = null;
        lobbyFinish = null;
        ledger = null;
    }

    @Override
    protected @LayoutRes int getLayout()
    {
        return R.layout.lobby_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        lobbyActions = view.findViewById(R.id.lobby_actions);
        lobbyRename = view.findViewById(R.id.lobby_rename);
        lobbyContacts = view.findViewById(R.id.lobby_contacts);
        lobbyAccount = view.findViewById(R.id.lobby_account);
        lobbyFinish = view.findViewById(R.id.lobby_finish);

        lobbyActions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showActions(ledger);
            }
        });

        lobbyRename.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showLedgerName(ledger);
            }
        });

        lobbyContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showContacts(ledger);
            }
        });

        lobbyAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showAccount(ledger);
            }
        });

        lobbyFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getActivity()).showLedgers();
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
