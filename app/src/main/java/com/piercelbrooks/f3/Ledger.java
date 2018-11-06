
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class Ledger
{
    private String name;
    private Action targetAction;
    private Contact targetContact;

    public Ledger(String name)
    {
        this.name = name;
        this.targetAction = new Action(this);
        this.targetContact = new Contact(this, "");
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setTargetAction(Action targetAction)
    {
        this.targetAction = targetAction;
    }

    public Action getTargetAction()
    {
        return targetAction;
    }

    public void setTargetContact(Contact targetContact)
    {
        this.targetContact = targetContact;
    }

    public Contact getTargetContact()
    {
        return targetContact;
    }
}
