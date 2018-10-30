
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
    private Button lobbyFinish;
    private Ledger ledger;

    public LobbyFragment()
    {
        super();
        lobbyActions = null;
        lobbyRename = null;
        lobbyFinish = null;
        ledger = null;
    }

    @Override
    protected @LayoutRes int getInflationResource()
    {
        return R.layout.lobby_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        lobbyActions = view.findViewById(R.id.lobby_actions);
        lobbyRename = view.findViewById(R.id.lobby_rename);
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
