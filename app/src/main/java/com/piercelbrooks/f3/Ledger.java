
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public class Ledger
{
    private Action target;

    public Ledger()
    {
        target = new Action(this);
    }

    public void setTarget(Action target)
    {
        this.target = target;
    }

    public Action getTarget()
    {
        return target;
    }
}
