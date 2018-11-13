
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.piercelbrooks.common.BasicActivity;
import com.piercelbrooks.common.Mayor;

public class MainActivity extends BasicActivity<MayoralFamily> implements Accountant {
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
    public @LayoutRes int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public Class<?> getCitizenClass() {
        return MainActivity.class;
    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> void preShow(@Nullable T fragment) {
        setMayorLedger(fragment);
    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> void onShow(@Nullable T fragment) {
        setMayorLedger(fragment);
    }

    @Override
    public <T extends Fragment & Mayor<MayoralFamily>> T getNewMayor(@Nullable MayoralFamily mayoralFamily) {
        T mayor = null;
        if (mayoralFamily == null) {
            return mayor;
        }
        switch (mayoralFamily) {
            case AUTHOR:
                mayor = (T)(new AuthorFragment());
                break;
            case ACCOUNT:
                mayor = (T)(new AccountFragment());
                break;
            case ACCOUNT_ADDRESS:
                mayor = (T)(new AccountFragment.AccountAddressFragment());
                break;
            case ACCOUNT_PASSWORD:
                mayor = (T)(new AccountFragment.AccountPasswordFragment());
                break;
            case ACTIONS:
                mayor = (T)(new ActionsFragment());
                break;
            case YEARS:
                mayor = (T)(new YearsFragment());
                break;
            case MONTHS:
                mayor = (T)(new MonthsFragment());
                break;
            case CALENDAR:
                mayor = (T)(new CalendarFragment());
                break;
            case EVENTS:
                mayor = (T)(new EventsFragment());
                break;
            case REVIEW:
                mayor = (T)(new ReviewFragment());
                break;
            case CONTACTS:
                mayor = (T)(new ContactsFragment());
                break;
            case CONTACT_ADDRESS:
                mayor = (T)(new ContactAddressFragment());
                break;
            case LEDGERS:
                mayor = (T)(new LedgersFragment());
                break;
            case LEDGER_NAME:
                mayor = (T)(new LedgerNameFragment());
                break;
            case LOBBY:
                mayor = (T)(new LobbyFragment());
                break;
        }
        setMayorLedger(mayor);
        return mayor;
    }

    @Override
    public void setLedger(Ledger ledger) {
        if (ledger != null) {
            Log.d(TAG, ledger.getName());
        }
        this.ledger = ledger;
    }

    @Override
    public Ledger getLedger() {
        return ledger;
    }

    public void showLobby(Ledger ledger) {
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
        fragment.setLedger(ledger);
        ledger.setTargetContact(contact);
        show(fragment);
    }

    public void showLedgerName(Ledger ledger) {
        LedgerNameFragment fragment = new LedgerNameFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showAccount(Ledger ledger) {
        AccountFragment fragment = new AccountFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showAuthor(Action action) {
        AuthorFragment fragment = new AuthorFragment();
        fragment.setLedger(ledger);
        ledger.setTargetAction(action);
        show(fragment);
    }

    public void showYears(Ledger ledger) {
        YearsFragment fragment = new YearsFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showMonths(Ledger ledger) {
        MonthsFragment fragment = new MonthsFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showCalendar(Ledger ledger) {
        CalendarFragment fragment = new CalendarFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showEvents(Ledger ledger) {
        EventsFragment fragment = new EventsFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    public void showReview(Ledger ledger) {
        ReviewFragment fragment = new ReviewFragment();
        fragment.setLedger(ledger);
        show(fragment);
        this.ledger = ledger;
    }

    private void setMayorLedger(Mayor<MayoralFamily> mayor) {
        if (mayor != null) {
            if (Accountant.class.isAssignableFrom(mayor.getCitizenClass())) {
                ((Accountant)mayor).setLedger(ledger);
            }
        }
    }
}
