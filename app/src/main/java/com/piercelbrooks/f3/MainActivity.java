
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
        //showLedgers();
        showCalendar(new Ledger(""));
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
        LedgersFragment fragment = new LedgersFragment();
        show(fragment);
    }

    public void showActions(Ledger ledger) {
        ActionsFragment fragment = new ActionsFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showContacts(Ledger ledger) {
        ContactsFragment fragment = new ContactsFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showContactAddress(Contact contact) {
        ContactAddressFragment fragment = new ContactAddressFragment();
        fragment.setContact(contact);
        if (ledger != null) {
            ledger.setTargetContact(contact);
        }
        contact.setOwner(ledger);
        show(fragment);
    }

    public void showLedgerName(Ledger ledger) {
        LedgerNameFragment fragment = new LedgerNameFragment();
        fragment.setLedger(ledger);
        show(fragment);
    }

    public void showAccount(Ledger ledger) {
        AccountFragment fragment = new AccountFragment();
        fragment.setLedger(ledger);
        show(fragment);
    }

    public void showAuthor(Action action) {
        AuthorFragment fragment = new AuthorFragment();
        fragment.setAction(action);
        if (ledger != null) {
            ledger.setTargetAction(action);
        }
        action.setOwner(ledger);
        show(fragment);
    }

    private void showCalendar(Ledger ledger) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setLedger(ledger);
        show(fragment);
    }

    public void setLedger(Ledger ledger) {
        this.ledger = ledger;
    }

    public Ledger getLedger() {
        return ledger;
    }
}
