
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;

public class LobbyFragment extends BasicFragment<MayoralFamily> implements Accountant
{
    private static final String TAG = "F3-LobbyFrag";

    private Button lobbyActions;
    private Button lobbyName;
    private Button lobbyPassword;
    private Button lobbyCalendar;
    private Button lobbyContacts;
    private Button lobbyAccount;
    private Button lobbyFinish;
    private Ledger ledger;

    public LobbyFragment()
    {
        super();
        lobbyActions = null;
        lobbyName = null;
        lobbyPassword = null;
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
        lobbyName = view.findViewById(R.id.lobby_name);
        lobbyPassword = view.findViewById(R.id.lobby_password);
        lobbyCalendar = view.findViewById(R.id.lobby_calendar);
        lobbyContacts = view.findViewById(R.id.lobby_contacts);
        lobbyAccount = view.findViewById(R.id.lobby_account);
        lobbyFinish = view.findViewById(R.id.lobby_finish);

        lobbyActions.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showActions(getLedger());
            }
        });

        lobbyName.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showLedgerName(getLedger());
            }
        });

        lobbyPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showPassword(getLedger(), getMayoralFamily(), MayoralFamily.LEDGER_PASSWORD);
            }
        });

        lobbyCalendar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showYears(getLedger());
            }
        });

        lobbyContacts.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showContacts(getLedger());
            }
        });

        lobbyAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showAccount(getLedger());
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
