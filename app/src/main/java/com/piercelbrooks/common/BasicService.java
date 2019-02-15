
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.piercelbrooks.f3.R;

public abstract class BasicService <T extends BasicService<T>> extends IntentService implements BasicServiceUser<T>, Citizen
{
    private static final String TAG = "PLB-BaseServe";

    private String name;

    protected abstract void create();
    protected abstract void destroy();
    protected abstract BasicServiceBinder<T> getBinder(T service);
    protected abstract Integer getNotification();
    public abstract String getDescription();

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
        Integer notification = getNotification();
        if (notification != null)
        {
            String description = getDescription();
            if (description != null)
            {
                String id = TAG+"_"+Constants.NOTIFICATION_CHANNEL;
                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), id);
                builder.setContentTitle(name);
                builder.setContentText(description);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setVibrate(new long[]{});
                builder.setSmallIcon(R.drawable.empty);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }
                manager.notify(notification.intValue(), builder.build());
            }
        }
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
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

    public Context getContext()
    {
        return getApplicationContext();
    }
}
