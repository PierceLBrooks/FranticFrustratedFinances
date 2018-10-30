
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.piercelbrooks.common.BasicActivity;

public class MainActivity extends BasicActivity {
    private static final String TAG = "F3-MainActivity";

    private Ledger ledger;

    @Override
    protected void create() {
        ledger = null;
    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void resume() {
        showLedgers();
    }

    @Override
    protected void pause() {

    }

    @Override
    protected @IdRes int getFragmentSlot() {
        return R.id.fragment_slot;
    }

    @Override
    protected @LayoutRes int getLayout() {
        return R.layout.activity_main;
    }

    public void showLobby(Ledger ledger)
    {
        LobbyFragment fragment = new LobbyFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showLedgers() {
        LedgerFragment fragment = new LedgerFragment();
        show(fragment);
    }

    public void showActions(Ledger ledger) {
        ActionFragment fragment = new ActionFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showAuthor(Action action) {
        AuthorFragment fragment = new AuthorFragment();
        fragment.setAction(action);
        if (ledger != null)
        {
            ledger.setTarget(action);
        }
        action.setOwner(ledger);
        show(fragment);
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public Ledger getLedger() {
        return ledger;
    }
}
