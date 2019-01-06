
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.search.SearchTerm;

public class Searcher extends SearchTerm {
    private String term;

    public Searcher(String term) {
        this.term = term;
    }

    @Override
    public boolean match(Message message) {
        try {
            if (message.getSubject().contains(term)) {
                return true;
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }
}
