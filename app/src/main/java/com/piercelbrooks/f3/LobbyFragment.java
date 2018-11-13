
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Utilities;

public class LobbyFragment extends BasicFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-LobbyFrag";

    private Button lobbyActions;
    private Button lobbyRename;
    private Button lobbyCalendar;
    private Button lobbyContacts;
    private Button lobbyAccount;
    private Button lobbyFinish;
    private Ledger ledger;

    public LobbyFragment()
    {
        super();
        lobbyActions = null;
        lobbyRename = null;
        lobbyCalendar = null;
        lobbyContacts = null;
        lobbyAccount = null;
        lobbyFinish = null;
        ledger = null;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.lobby_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        lobbyActions = view.findViewById(R.id.lobby_actions);
        lobbyRename = view.findViewById(R.id.lobby_rename);
        lobbyCalendar = view.findViewById(R.id.lobby_calendar);
        lobbyContacts = view.findViewById(R.id.lobby_contacts);
        lobbyAccount = view.findViewById(R.id.lobby_account);
        lobbyFinish = view.findViewById(R.id.lobby_finish);

        lobbyActions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showActions(ledger);
            }
        });

        lobbyRename.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLedgerName(ledger);
            }
        });

        lobbyCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showYears(ledger);
            }
        });

        lobbyContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showContacts(ledger);
            }
        });

        lobbyAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                ((MainActivity)getMunicipality()).showAccount(ledger);
            }
        });

        lobbyFinish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLedgers();
            }
        });
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
        return MayoralFamily.LOBBY;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return LobbyFragment.class;
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
