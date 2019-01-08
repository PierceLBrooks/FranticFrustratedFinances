
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Text;
import com.piercelbrooks.common.TextListener;

public abstract class FieldFragment  extends BasicFragment<MayoralFamily> implements TextListener
{
    private static final String TAG = "F3-FieldFrag";

    public abstract int getInputType();
    public abstract String getTitle();
    public abstract String getField();
    public abstract void setField(String field);
    public abstract void onExit();

    public FieldFragment()
    {
        super();
    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
    {

    }

    @Override
    public void onChange(@NonNull Text text)
    {

    }

    @Override
    public void onDelete(@NonNull Text text, boolean action)
    {

    }
}
