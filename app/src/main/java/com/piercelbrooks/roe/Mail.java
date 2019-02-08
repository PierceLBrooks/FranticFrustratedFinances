
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.piercelbrooks.common.Utilities;
import com.sun.mail.util.ReadableMime;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

public class Mail {
    private MailDate date;
    private String subject;
    private ArrayList<String> content;
    private ArrayList<MailAddress> from;
    private ArrayList<MailRecipient> to;

    public Mail(@Nullable Mail other) {
        if (other == null) {
            this.date = null;
            this.subject = null;
            this.content = null;
            this.from = new ArrayList<>();
            this.to = new ArrayList<>();
            return;
        }
        List<String> content = other.getContent();
        List<MailAddress> from = other.getFrom();
        List<MailRecipient> to = other.getTo();
        this.date = null;
        this.subject = other.getSubject();
        this.content = new ArrayList<>();
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
        for (int i = 0; i != content.size(); ++i) {
            this.content.add(content.get(i));
        }
        for (int i = 0; i != from.size(); ++i) {
            this.from.add(new MailAddress(from.get(i)));
        }
        for (int i = 0; i != to.size(); ++i) {
            this.to.add(new MailRecipient(to.get(i)));
        }
    }

    public Mail(@NonNull Message message) {
        MailRecipientType[] types = MailRecipientType.values();
        MailRecipientType type;
        Address[] addresses;
        this.date = null;
        this.subject = null;
        this.content = null;
        this.from = new ArrayList<>();
        this.to = new ArrayList<>();
        try {
            this.date = new MailDate(message);
            this.subject = message.getSubject();
            this.content = getContentParse(message.getContent());
            addresses = message.getFrom();
            for (int i = 0; i != addresses.length; ++i) {
                this.from.add(new MailAddress(addresses[i]));
            }
            for (int i = 0; i != types.length; ++i) {
                type = types[i];
                addresses = message.getRecipients(type.getImplementation());
                if (addresses == null) {
                    continue;
                }
                for (int j = 0; j != addresses.length; ++j) {
                    this.to.add(new MailRecipient(addresses[j], type));
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MailDate getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getContent() {
        return content;
    }

    public List<MailAddress> getFrom() {
        return from;
    }

    public List<MailRecipient> getTo() {
        return to;
    }

    public static ArrayList<String> getContentParse(Object content) {
        try {
            ArrayList<String> parse = new ArrayList<>();
            if (content == null) {
                return parse;
            }
            if (content instanceof MimeMultipart) {
                Object bodyPart;
                MimeMultipart mime = (MimeMultipart)content;
                for (int i = 0; i != mime.getCount(); ++i) {
                    bodyPart = mime.getBodyPart(i);
                    Utilities.add(parse, getContentParse(bodyPart));
                }
                return parse;
            }
            if (content instanceof ReadableMime) {
                Utilities.add(parse, Utilities.toString(((ReadableMime)content).getMimeStream()));
                return parse;
            }
            parse.add(content.toString());
            return parse;
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
