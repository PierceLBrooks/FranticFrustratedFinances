
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

// Source: https://stackoverflow.com/a/49787972

public abstract class Mailbox <T extends MailboxListener> extends AsyncTask<Void, Void, Void> implements Runnable {
    private static final String TAG = "ROE-Mailbox";
    public static final String MAIL_TRANSPORT_PROPERTY = "mail.transport.protocol";
    public static final String MAIL_PROTOCOL_PROPERTY = "mail.store.protocol";
    public static final String MAIL_HOST_PROPERTY = "mail.host";
    public static final String MAIL_DEBUG_PROPERTY = "mail.debug";

    private T listener;
    private Session session;
    private String address;
    private String password;

    protected abstract MailProperties getMailboxProperties();
    protected abstract Properties getMailProperties();

    public Mailbox(@NonNull T listener,  String address, String password) {
        this.listener = listener;
        this.session = null;
        this.address = address;
        this.password = password;
    }

    public T getListener() {
        return listener;
    }

    public Session getSession() {
        return session;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    @Override
    protected Void doInBackground(Void... params) {
        session = Session.getInstance(getMailProperties(), new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(address, password);
                }
            });
        Log.d(TAG, Utilities.toString(Utilities.toString(Utilities.getEntries(session.getProperties()))));
        run();
        listener.onDone(this);
        return null;
    }
}
