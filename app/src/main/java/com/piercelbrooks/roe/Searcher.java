
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.util.Log;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

public class Searcher extends SearchTerm {
    private static final String TAG = "ROE-Search";

    private String term;
    private Boolean seen;

    public Searcher(String term) {
        this(term, null);
    }

    public Searcher(String term, Boolean seen) {
        this.term = term;
        this.seen = seen;
    }

    @Override
    public boolean match(Message message) {
        if (message == null) {
            return false;
        }
        if (seen != null) {
            if (!(new FlagTerm(new Flags(Flags.Flag.SEEN), seen.booleanValue())).match(message)) {
                return false;
            }
        }
        try {
            String other = message.getSubject();
            if ((other == null) || (term == null)) {
                if (other != term) {
                    return false;
                }
                return true;
            }
            Log.d(TAG, "\""+other+"\" ? \""+term+"\"");
            if (other.contains(term)) {
                return true;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
