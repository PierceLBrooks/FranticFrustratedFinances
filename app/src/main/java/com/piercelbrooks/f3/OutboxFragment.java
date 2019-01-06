
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.piercelbrooks.roe.Outbox;
import com.piercelbrooks.roe.OutboxListener;
import com.piercelbrooks.roe.MailProperties;
import com.piercelbrooks.roe.Mailbox;

public class OutboxFragment extends MailboxFragment<OutboxListener, Outbox> implements OutboxListener
{
    private static final String TAG = "F3-OutboxFrag";
 
    private Button outboxExit;
    private String subject;
    private String message;

    public OutboxFragment()
    {
        super();
        outboxExit = null;
        subject = null;
        message = null;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
        if ((subject != null) && (message != null))
        {
            go(this);
        }
    }

    public void setMessage(String message)
    {
        this.message = message;
        if ((subject != null) && (message != null))
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
        return R.id.outbox_item_label;
    }

    @Override
    protected @LayoutRes int getItemLayout()
    {
        return R.layout.outbox_item;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.outbox_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        super.createView(view);

        outboxExit = view.findViewById(R.id.outbox_exit);

        outboxExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showMailTest(getLedger());
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
        return MayoralFamily.OUTBOX;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return OutboxFragment.class;
    }

    @Override
    protected Outbox getNewBox(@NonNull OutboxListener listener)
    {
        return new Outbox(listener, getLedger().getContacts().getAddresses(), subject, message, getLedger().getAccount().getAddress(), getLedger().getAccount().getPassword());
    }

    @Override
    public void onOut(@NonNull Outbox sender, @Nullable String recipient)
    {
        if (sender != getBox())
        {
            Log.e(TAG, "Box error!");
        }
        addItem(recipient);
    }

    @Override
    public MailProperties getProperties()
    {
        return SettingsFragment.getSettings();
    }

    @Override
    public void onDone(@NonNull Mailbox sender)
    {
        if (sender != getBox())
        {
            Log.e(TAG, "Box error!");
        }
        getMunicipality().getOwner().makeToast("Outbox done!");
    }
}
