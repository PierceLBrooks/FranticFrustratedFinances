
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

public abstract class BasicService <T extends BasicService<T>> extends IntentService implements BasicServiceUser<T>, Citizen
{
    private String name;

    protected abstract void create();
    protected abstract void destroy();
    protected abstract BasicServiceBinder<T> getBinder(T service);

    public BasicService(String name)
    {
        super(name);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return getBinder((T)this);
    }

    @Override
    public void onRebind(Intent intent)
    {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        birth();
        create();
    }

    @Override
    public void onDestroy()
    {
        destroy();
        death();
        super.onDestroy();
    }

    @Override
    public T getService()
    {
        return (T)this;
    }

    @Override
    public Class<?> getCitizenClass()
    {
        return getServiceClass();
    }

    @Override
    public Family getFamily()
    {
        return Family.SERVICE;
    }

    @Override
    public void birth()
    {
        Governor.getInstance().register(this);
    }

    @Override
    public void death()
    {
        Governor.getInstance().unregister(this);
    }
}
