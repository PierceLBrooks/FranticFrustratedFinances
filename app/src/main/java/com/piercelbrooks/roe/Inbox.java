
// Author: Pierce Brooks

package com.piercelbrooks.roe;

// Source: https://stackoverflow.com/questions/28019184/receive-read-emails-in-android

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Store;
import javax.mail.search.SearchTerm;

public class Inbox extends Mailbox<InboxListener> {
    private static final String TAG = "ROE-Inbox";

    private InboxListener listener;
    private SearchTerm searchTerm;
    private Store store;
    private Folder folder;

    public Inbox(@NonNull InboxListener listener, SearchTerm searchTerm, String address, String password) {
        super(listener, address, password);
        this.searchTerm = searchTerm;
        this.store = null;
        this.folder = null;
    }

    public SearchTerm getSearchTerm() {
        return searchTerm;
    }

    @Override
    protected MailProperties getMailboxProperties() {
        return getListener().getInboxProperties();
    }

    @Override
    protected Properties getMailProperties() {
        return getMailboxProperties().getIncomingMailProperties();
    }

    @Override
    public void run() {
        ArrayList<Mail> mail = null;
        Message[] messages = null;
        FetchProfile fetch;
        try {
            Log.d(TAG, "Mail Store Protocol: "+getSession().getProperty(Mailbox.MAIL_PROTOCOL_PROPERTY));
            store = getSession().getStore();
            store.connect(getAddress(), getPassword());
            folder = store.getFolder("Inbox");
            folder.open(Folder.READ_ONLY);
            if (searchTerm instanceof Searcher) {
                messages = ((Searcher)searchTerm).search(folder);
            } else {
                messages = folder.search(searchTerm);
            }
            fetch = new FetchProfile();
            fetch.add(FetchProfile.Item.ENVELOPE);
            fetch.add(FetchProfile.Item.CONTENT_INFO);
            mail = new ArrayList<>();
            folder.fetch(messages, fetch);
            for (int i = 0; i != messages.length; ++i) {
                mail.add(new Mail(messages[i]));
            }
            folder.close(true);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getListener().onIn(this, mail);
    }
}
