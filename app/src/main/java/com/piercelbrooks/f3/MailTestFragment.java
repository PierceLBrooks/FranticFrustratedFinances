
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.Searcher;

public class MailTestFragment extends BasicFragment<MayoralFamily> implements Accountant
{
    public static class InboxTestFragment extends EditorFragment
    {
        private MailTestFragment test;
        private String subject;

        public InboxTestFragment()
        {
            super();
            test = null;
            subject = null;
        }

        public void setTest(MailTestFragment test)
        {
            this.test = test;
            this.subject = test.getDefault();
        }

        @Override
        protected int getInputType()
        {
            return EditorInfo.TYPE_TEXT_VARIATION_NORMAL;
        }

        @Override
        protected String getField()
        {
            return subject;
        }

        @Override
        protected String getTitle()
        {
            return "MAIL INBOX TEST SUBJECT";
        }

        @Override
        protected void onExit()
        {
            setField(test.getDefault());
        }

        @Override
        protected void onSave(String field)
        {
            subject = field;
            Utilities.closeKeyboard(getActivity());
            test.onInbox(subject);
        }

        @Override
        public MayoralFamily getMayoralFamily()
        {
            return MayoralFamily.MAIL_TEST_INBOX;
        }

        @Override
        public Class<?> getCitizenClass()
        {
            return InboxTestFragment.class;
        }
    }

    public static class OutboxTestFragment extends EditorFragment
    {
        private MailTestFragment test;
        private String message;

        public OutboxTestFragment()
        {
            super();
            test = null;
            message = null;
        }

        public void setTest(MailTestFragment test)
        {
            this.test = test;
            this.message = test.getDefault();
        }

        @Override
        protected int getInputType()
        {
            return EditorInfo.TYPE_TEXT_VARIATION_NORMAL;
        }

        @Override
        protected String getField()
        {
            return message;
        }

        @Override
        protected String getTitle()
        {
            return "MAIL OUTBOX TEST MESSAGE";
        }

        @Override
        protected void onExit()
        {
            setField(test.getDefault());
        }

        @Override
        protected void onSave(String field)
        {
            message = field;
            Utilities.closeKeyboard(getActivity());
            test.onOutbox(message);
        }

        @Override
        public MayoralFamily getMayoralFamily()
        {
            return MayoralFamily.MAIL_TEST_OUTBOX;
        }

        @Override
        public Class<?> getCitizenClass()
        {
            return OutboxTestFragment.class;
        }
    }

    private static final String TAG = "F3-MailTestFrag";

    private Button mailTestExit;
    private InboxTestFragment inbox;
    private OutboxTestFragment outbox;
    private Ledger ledger;

    public MailTestFragment()
    {
        super();
        mailTestExit = null;
        inbox = null;
        outbox = null;
        ledger = null;
    }

    public void onInbox(String subject)
    {
        ((MainActivity)getMunicipality()).showInbox(ledger, new Searcher(subject));
    }

    public void onOutbox(String message)
    {
        ((MainActivity)getMunicipality()).showOutbox(ledger, getDefault(), message);
    }

    public String getDefault()
    {
        return "Test";
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.mail_test_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        inbox = new InboxTestFragment();
        outbox = new OutboxTestFragment();
        inbox.setTest(this);
        outbox.setTest(this);
        manager.beginTransaction().replace(R.id.mail_test_inbox_slot, inbox, null).commit();
        manager.beginTransaction().replace(R.id.mail_test_outbox_slot, outbox, null).commit();
        mailTestExit = view.findViewById(R.id.mail_test_exit);
        mailTestExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getMunicipality()).showAccount(getLedger());
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
        return MayoralFamily.MAIL_TEST;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return MailTestFragment.class;
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
