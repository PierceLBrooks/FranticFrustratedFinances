
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Outbox extends Mailbox<OutboxListener> {
    private static final String TAG = "ROE-Outbox";

    private List<String> recipients;
    private String subject;
    private String message;

    public Outbox(@NonNull OutboxListener listener, List<String> recipients, String subject, String message, String address, String password) {
        super(listener, address, password);
        this.recipients = recipients;
        this.subject = subject;
        this.message = message;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected MailProperties getMailboxProperties() {
        return getListener().getOutboxProperties();
    }

    @Override
    protected Properties getMailProperties() {
        return getMailboxProperties().getOutgoingMailProperties();
    }

    @Override
    public void run() {
        if (recipients == null)
        {
            return;
        }
        for (int i = 0; i != recipients.size(); ++i) {
            run(recipients.get(i));
        }
    }

    private void run(String recipient) {

        try {
            MimeMessage mime = new MimeMessage(getSession());
            Transport transport = getSession().getTransport();
            mime.setFrom(new InternetAddress(getAddress()));
            mime.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            mime.setSubject(subject);
            mime.setText(message);
            transport.connect(getAddress(), getPassword());
            transport.sendMessage(mime, mime.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getListener().onOut(this, recipient);
    }
}
