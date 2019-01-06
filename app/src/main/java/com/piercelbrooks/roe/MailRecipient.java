
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import javax.mail.Address;

public class MailRecipient extends MailAddress {
    private MailRecipientType type;

    public MailRecipient(MailRecipient other) {
        super(other);
        if (other == null) {
            this.type = null;
            return;
        }
        this.type = other.getType();
    }

    public MailRecipient(Address address, MailRecipientType type) {
        super(address);
        this.type = type;
    }

    public MailRecipientType getType() {
        return type;
    }
}
