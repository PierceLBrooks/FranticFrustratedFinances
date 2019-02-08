
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicListFragment;
import com.piercelbrooks.roe.Inbox;
import com.piercelbrooks.roe.InboxListener;
import com.piercelbrooks.roe.Mail;
import com.piercelbrooks.roe.MailAddress;
import com.piercelbrooks.roe.MailProperties;
import com.piercelbrooks.roe.Mailbox;

import java.util.ArrayList;
import java.util.List;

import javax.mail.search.SearchTerm;

public class InboxFragment extends MailboxFragment<InboxListener, Inbox> implements InboxListener
{
    private static final String TAG = "F3-InboxFrag";

    private Button inboxExit;
    private Button inboxView;
    private SearchTerm searchTerm;
    private ArrayList<Mail> mail;

    public InboxFragment()
    {
        super();
        inboxExit = null;
        inboxView = null;
        searchTerm = null;
        mail = new ArrayList<>();
    }

    public void setSearchTerm(SearchTerm searchTerm)
    {
        this.searchTerm = searchTerm;
        if (searchTerm != null)
        {
            go(this);
        }
    }

    @Override
    protected boolean itemLabelAffix()
    {
        return true;
    }

    @Override
    protected @IdRes int getItemID()
    {
        return R.id.inbox_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.inbox_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.inbox_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        super.createView(view);

        inboxExit = view.findViewById(R.id.inbox_exit);
        inboxView = view.findViewById(R.id.inbox_view);

        inboxExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showMailTest(getLedger());
            }
        });
        inboxView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showMail(getLedger(), mail.get(getSelectionIndex()), searchTerm);
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
        return MayoralFamily.INBOX;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return InboxFragment.class;
    }

    @Override
    protected Inbox getNewBox(@NonNull InboxListener listener)
    {
        return new Inbox(listener, searchTerm, getLedger().getAccount().getAddress(), getLedger().getAccount().getPassword());
    }

    @Override
    public void onIn(@NonNull Inbox sender, @Nullable List<Mail> mail)
    {
        if (sender != getBox())
        {
            Log.e(TAG, "Box error!");
        }
        if (mail == null)
        {
            getApplication().makeToast("Inbox error!");
            return;
        }
        for (int i = 0; i != mail.size(); ++i)
        {
            String from = "";
            List<MailAddress> addresses = mail.get(i).getFrom();
            for (int j = 0; j != addresses.size(); ++j)
            {
                from += addresses.get(j).getAddress();
                from += "\n";
            }
            addItem(from.trim());
            this.mail.add(mail.get(i));
        }
    }

    @Override
    public void onDone(@NonNull Mailbox sender)
    {
        if (sender != getBox())
        {
            Log.e(TAG, "Box error!");
        }
        getApplication().makeToast("Inbox done!");
    }
}
