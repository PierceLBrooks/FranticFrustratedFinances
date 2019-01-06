
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

// Source: https://stackoverflow.com/a/49787972

public abstract class Mailbox <T extends MailboxListener> extends AsyncTask<Void, Void, Void> implements Runnable {
    private static final String TAG = "ROE-Mailbox";

    private T listener;
    private Session session;
    private String address;
    private String password;

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
        session = Session.getDefaultInstance(listener.getProperties().getMailProperties(), new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(address, password);
                }
            });
        run();
        listener.onDone(this);
        return null;
    }
}
