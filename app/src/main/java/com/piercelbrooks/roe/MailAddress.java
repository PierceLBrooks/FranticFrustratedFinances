
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

public class MailAddress {
    private String address;

    public MailAddress(MailAddress other) {
        this((other == null)?null:other.getAddress());
    }

    public MailAddress(String address) {
        this.address = address;
    }

    public MailAddress(Address address) {
        this.address = null;
        if (address instanceof InternetAddress) {
            this.address = ((InternetAddress)address).getAddress();
        }
    }

    public String getAddress() {
        return address;
    }
}
