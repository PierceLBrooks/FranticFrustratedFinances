
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

public class Mail {
    private String subject;
    private String content;
    private ArrayList<MailAddress> from;
    private ArrayList<MailRecipient> to;

    public Mail(Mail other) {
        if (other == null) {
            this.subject = null;
            this.content = null;
            this.from = new ArrayList<>();
            this.to = new ArrayList<>();
            return;
        }
        List<MailAddress> from = other.getFrom();
        List<MailRecipient> to = other.getTo();
        this.subject = ""+other.getSubject();
        this.content = ""+other.getContent();
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
        for (int i = 0; i != from.size(); ++i) {
            this.from.add(new MailAddress(from.get(i)));
        }
        for (int i = 0; i != to.size(); ++i) {
            this.to.add(new MailRecipient(to.get(i)));
        }
    }

    public Mail(Message message) {
        MailRecipientType[] types = MailRecipientType.values();
        MailRecipientType type;
        Address[] addresses;
        this.subject = null;
        this.content = null;
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
        try {
            this.subject = ""+message.getSubject();
            this.content = ""+message.getContent().toString();
            addresses = message.getFrom();
            for (int i = 0; i != addresses.length; ++i) {
                this.from.add(new MailAddress(addresses[i]));
            }
            for (int i = 0; i != types.length; ++i) {
                type = types[i];
                addresses = message.getRecipients(type.getImplementation());
                for (int j = 0; j != addresses.length; ++j) {
                    this.to.add(new MailRecipient(addresses[j], type));
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public List<MailAddress> getFrom() {
        return from;
    }

    public List<MailRecipient> getTo() {
        return to;
    }
}
