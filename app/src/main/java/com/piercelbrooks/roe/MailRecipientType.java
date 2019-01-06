
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import javax.mail.Message;

public enum MailRecipientType {
    TO() {
        public Message.RecipientType getImplementation() {
            return Message.RecipientType.TO;
        }
    },
    CC() {
        public Message.RecipientType getImplementation() {
            return Message.RecipientType.CC;
        }
    },
    BCC() {
        public Message.RecipientType getImplementation() {
            return Message.RecipientType.BCC;
        }
    };

    public abstract Message.RecipientType getImplementation();
}
