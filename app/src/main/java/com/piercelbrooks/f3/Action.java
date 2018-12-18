
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class Action implements LedgerProperty
{
    private Ledger owner;

    public Action(Ledger owner)
    {
        this.owner = owner;
    }

    @Override
    public void setOwner(Ledger owner)
    {
        this.owner = owner;
    }

    @Override
    public Ledger getOwner()
    {
        return owner;
    }
}
