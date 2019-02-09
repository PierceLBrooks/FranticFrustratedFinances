
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public abstract class BasicServiceConnector <T extends Enum<T>, U extends BasicService<U>> implements ServiceConnection, BasicServiceUser<U>
{
    private BasicServiceActivity<T, U> activity;
    private U service;

    public BasicServiceConnector(BasicServiceActivity<T, U> activity)
    {
        this.activity = activity;
        this.service = null;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service)
    {
        if (!name.getClassName().equals(getServiceClass().getName()))
        {
            return;
        }
        BasicServiceBinder<U> binder = (BasicServiceBinder<U>)service;
        this.service = binder.getService();
        this.activity.onServiceConnected(this.service);
    }

    @Override
    public void onServiceDisconnected(ComponentName name)
    {
        if (!name.getClassName().equals(getServiceClass().getName()))
        {
            return;
        }
        this.activity.onServiceDisconnected();
    }

    @Override
    public U getService()
    {
        return service;
    }

    public BasicServiceActivity<T, U> getActivity()
    {
        return activity;
    }
}
