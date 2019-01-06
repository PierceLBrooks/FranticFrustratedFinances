
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;

import com.piercelbrooks.common.Preferences;
import com.piercelbrooks.roe.Mail;

import javax.mail.search.SearchTerm;

public class MailFragment extends SerialFragment<MailSerial> implements Accountant
{
    private static final String TAG = "F3-MailFrag";

    private Ledger ledger;
    private Mail mail;
    private SearchTerm searchTerm;

    public MailFragment()
    {
        super();
        ledger = null;
        mail = null;
        searchTerm = null;
    }

    public void setMail(Mail mail)
    {
        this.mail = mail;
    }

    public void setSearchTerm(SearchTerm searchTerm)
    {
        this.searchTerm = searchTerm;
    }

    @Override
    protected MailSerial getSerial()
    {
        return new MailSerial(mail);
    }

    @Override
    protected String getTitle()
    {
        return "MAIL";
    }

    @Override
    protected void onExit()
    {
        ((MainActivity)getMunicipality()).showInbox(ledger, searchTerm);
    }

    @Override
    public MayoralFamily getMayoralFamily()
    {
        return MayoralFamily.MAIL;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return MailFragment.class;
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
