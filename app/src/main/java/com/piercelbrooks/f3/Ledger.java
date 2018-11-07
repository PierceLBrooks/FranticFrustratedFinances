
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import java.util.Date;

public class Ledger {
    private static Ledger current = null;

    private String name;
    private Action targetAction;
    private Contact targetContact;
    private Date targetDate;

    public Ledger(String name) {
        current = this;
        this.name = name;
        this.targetAction = new Action(this);
        this.targetContact = new Contact(this, "");
        this.targetDate = new Date();
    }

    public static void setCurrent(Ledger current) {
        Ledger.current = current;
    }

    public static Ledger getCurrent() {
        return current;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTargetAction(Action targetAction) {
        this.targetAction = targetAction;
    }

    public Action getTargetAction() {
        return targetAction;
    }

    public void setTargetContact(Contact targetContact) {
        this.targetContact = targetContact;
    }

    public Contact getTargetContact() {
        return targetContact;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public Date getTargetDate() {
        return targetDate;
    }
}
