
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;

public class Focus extends ContextWrap
{
    private static final String TAG = "PLB-Focus";

    private View view;

    public Focus(Context context)
    {
        super(context);
        this.view = null;
    }

    @Override
    protected void onBirth()
    {

    }

    @Override
    protected void onDeath()
    {

    }

    public void setView(@Nullable View view)
    {

    }
}
