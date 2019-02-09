
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BasicServiceActivity <T extends Enum<T>, U extends BasicService<U>> extends BasicActivity<T> implements BasicServiceUser<U>
{
    private AtomicBoolean isBound;
    private BasicServiceConnector<T, U> connector;
    private U service;

    protected abstract BasicServiceConnector<T, U> getConnector(BasicServiceActivity<T, U> activity);

    public BasicServiceActivity()
    {
        super();
        isBound = new AtomicBoolean();
    }

    public void onServiceConnected(U service)
    {
        if (isBound.get())
        {
            return;
        }
        this.service = service;
    }

    public void onServiceDisconnected()
    {
        if (!isBound.get())
        {
            return;
        }
        this.service = null;
    }

    public boolean getIsServiceBound()
    {
        return isBound.get();
    }

    public boolean getIsServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (getServiceClass().getName().equals(service.service.getClassName()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean beginService()
    {
        if (!getIsServiceRunning())
        {
            startService(getServiceIntent());
        }
        if (!getIsServiceBound())
        {
            connector = getConnector(this);
            return bindService(getServiceIntent(), connector, 0);
        }
        return true;
    }

    public boolean endService() {
        if (!getIsServiceRunning()) {
            return false;
        }
        if (getIsServiceBound())
        {
            unbindService(connector);
        }
        return stopService(getServiceIntent());
    }

    public Intent getServiceIntent()
    {
        return new Intent(getApplicationContext(), getServiceClass());
    }

    @Override
    public U getService()
    {
        return service;
    }
}
