
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class Action
{
    private Ledger owner;

    public Action(Ledger owner)
    {
        this.owner = owner;
    }

    public void setOwner(Ledger owner)
    {
        this.owner = owner;
    }

    public Ledger getOwner()
    {
        return owner;
    }
}
