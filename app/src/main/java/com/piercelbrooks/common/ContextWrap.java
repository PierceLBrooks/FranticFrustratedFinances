
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.content.ContextWrapper;

public class ContextWrap extends ContextWrapper implements Citizen
{
    public ContextWrap(Context base)
    {
        super(base);
    }

    @Override
    public Family getFamily()
    {
        return Family.CONTEXT;
    }

    @Override
    public void birth()
    {

    }

    @Override
    public void death()
    {

    }
}
