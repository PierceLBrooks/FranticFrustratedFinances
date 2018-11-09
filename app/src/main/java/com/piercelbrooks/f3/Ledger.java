
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class Ledger {
    private static Ledger current = null;

    private String name;
    private Action targetAction;
    private Contact targetContact;
    private DateTime targetDateTime;

    public Ledger(String name) {
        current = this;
        this.name = name;
        this.targetAction = new Action(this);
        this.targetContact = new Contact(this, "");
        this.targetDateTime = new DateTime();
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

    public void setTargetDateTime(DateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public DateTime getTargetDateTime() {
        return targetDateTime;
    }
}
