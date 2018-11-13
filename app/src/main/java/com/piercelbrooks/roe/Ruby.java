
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Utilities;

public class Ruby implements Citizen
{
    private static final String TAG = "ROE-Ruby";
    private static Ruby instance = null;

    public Ruby()
    {
        System.loadLibrary("native-lib");
    }

    @Override
    public Family getFamily()
    {
        return Family.SINGLETON;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return Ruby.class;
    }

    @Override
    public void birth()
    {
        if (instance != null)
        {
            Utilities.throwSingletonException(TAG);
            return;
        }
        instance = this;
        Governor.getInstance().register(this);
        begin();
    }

    @Override
    public void death()
    {
        if (instance != this)
        {
            Utilities.throwSingletonException(TAG);
            return;
        }
        instance = null;
        end();
        Governor.getInstance().unregister(this);
    }

    public static Ruby getInstance()
    {
        return instance;
    }

    private native void begin();

    private native void end();

    public native String run(String script, String entry);
}
