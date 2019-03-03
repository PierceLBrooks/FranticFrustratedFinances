
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.piercelbrooks.f3.R;

import java.util.List;

public abstract class BasicService <T extends BasicService<T>> extends /*Intent*/Service implements BasicServiceUser<T>, Citizen
{
    private class BasicServiceReceiver <U extends BasicService<U>> extends BroadcastReceiver
    {
        private BasicService<U> owner;

        public BasicServiceReceiver(BasicService<U> owner)
        {
            super();
            this.owner = owner;
        }

        @Override
        public void onReceive(Context context, Intent intent)
        {
            owner.onHandleIntent(intent);
        }
    }

    private static final String TAG = "PLB-BaseServe";
    private static final String STOP = "com.piercelbrooks.common.STOP";

    private Integer id;
    private String name;
    private BasicServiceReceiver<T> receiver;

    protected abstract void create();
    protected abstract void destroy();
    protected abstract BasicServiceBinder<T> getBinder(T service);
    protected abstract String getName();
    protected abstract Integer getNotification();
    protected abstract List<NotificationCompat.Action> getNotificationActions();
    public abstract String getDescription();

    public BasicService(/*String name*/)
    {
        super(/*name*/);
        this.name = null;//this.name = name;
        this.receiver = null;
        this.id = null;
    }

    //@Override
    protected void onHandleIntent(Intent intent)
    {
        try
        {
            if (intent == null)
            {
                Log.e(TAG, "Handle error 1!");
                return;
            }
            if (intent.getAction() == null)
            {
                Log.e(TAG, "Handle error 2!");
                return;
            }
            if (!intent.getAction().equals(getStop()))
            {
                Log.e(TAG, "Handle error 3!");
                return;
            }
            if (id == null)
            {
                Log.e(TAG, "Handle error 4!");
                return;
            }
            if (!stopSelfResult(id.intValue()))
            {
                Log.e(TAG, "Handle error 5!");
                return;
            }
            Log.v(TAG, "Handle success!");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            Log.e(TAG, "Handle error 0!");
        }
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
                android.app.Notification build;
                List<NotificationCompat.Action> actions = getNotificationActions();
                String id = TAG+"_"+Constants.NOTIFICATION_CHANNEL;
                NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), id);
                builder.setContentTitle(getName());
                builder.setContentText(description);
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setVibrate(new long[]{});
                builder.setSmallIcon(R.drawable.empty);
                if (actions != null)
                {
                    for (int i = 0; i != actions.size(); ++i)
                    {
                        builder.addAction(actions.get(i));
                    }
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    NotificationChannel channel = new NotificationChannel(id, getName(), NotificationManager.IMPORTANCE_HIGH);
                    manager.createNotificationChannel(channel);
                }
                build = builder.build();
                startForeground(notification.intValue(), build);
                manager.notify(notification.intValue(), build);
            }
        }
        super.onStartCommand(intent, flags, startId);
        this.id = startId;
        Log.v(TAG, "Started!");
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
        if (receiver == null)
        {
            receiver = new BasicServiceReceiver<>(this);
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, getServiceIntentFilter());
        }
    }

    @Override
    public void onDestroy()
    {
        if (receiver != null)
        {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
            receiver = null;
        }
        stopForeground(true);
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

    public IntentFilter getServiceIntentFilter()
    {
        IntentFilter filter = new IntentFilter();
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        filter.addAction(getStop());
        return filter;
    }

    public Context getContext()
    {
        return getApplicationContext();
    }

    public static String getStop()
    {
        return STOP;
    }
}
