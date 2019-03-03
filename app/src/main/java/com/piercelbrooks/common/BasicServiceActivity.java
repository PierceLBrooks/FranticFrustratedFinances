
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BasicServiceActivity <T extends Enum<T>, U extends BasicService<U>> extends BasicActivity<T> implements BasicServiceUser<U>
{
    private static final String TAG = "PLB-BaseServeActivity";

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
            Log.v(TAG, "No binding.");
            return;
        }
        this.service = null;
    }

    public boolean getIsServiceBound()
    {
        if (connector == null)
        {
            Log.v(TAG, "No connection.");
            return false;
        }
        return isBound.get();
    }

    public boolean getIsServiceRunning()
    {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (getServiceClass().getName().equals(service.service.getClassName()))
            {
                if (!service.foreground)
                {
                    Log.v(TAG, "Service not foregrounded...");
                    /*if (service.started)
                    {
                        Log.v(TAG, "Service not started...");
                        stopService();
                    }*/
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public boolean beginService()
    {
        if (!getIsServiceRunning())
        {
            Log.d(TAG, "Starting service...");
            startService();
            Log.d(TAG, "Started service!");
        }
        return bindService();
    }

    public boolean endService()
    {
        if (!getIsServiceRunning())
        {
            Log.v(TAG, "No service.");
            return false;
        }
        unbindService();
        return stopService();
    }

    public boolean bindService()
    {
        if (!getIsServiceBound())
        {
            boolean success;
            Log.d(TAG, "Binding service...");
            connector = getConnector(this);
            success = bindService(getServiceIntent(), connector, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "Bound service!");
            return success;
        }
        return true;
    }

    public boolean unbindService()
    {
        if (getIsServiceBound())
        {
            Log.d(TAG, "Unbinding service...");
            unbindService(connector);
            Log.d(TAG, "Unbound service!");
            return true;
        }
        return false;
    }

    public boolean startService()
    {
        Log.v(TAG, "Starting service...");
        startService(getServiceIntent());
        return true;
    }

    public boolean stopService()
    {
        Log.v(TAG, "Stopping service...");
        //return LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(getServiceIntent(BasicService.getStop()));
        return stopService(getServiceIntent());
    }

    public Intent getServiceIntent()
    {
        return (new Intent(getApplicationContext(), getServiceClass())).addCategory(Intent.CATEGORY_DEFAULT);
    }

    public Intent getServiceIntent(String action)
    {
        return (new Intent(action, null, getApplicationContext(), getServiceClass())).addCategory(Intent.CATEGORY_DEFAULT);
    }

    @Override
    public U getService()
    {
        return service;
    }

    @Override
    protected void onPause()
    {
        unbindService();
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        bindService();
        super.onResume();
    }
}
