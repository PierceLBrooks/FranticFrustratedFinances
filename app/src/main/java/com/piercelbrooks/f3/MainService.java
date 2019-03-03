
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.piercelbrooks.common.BasicService;
import com.piercelbrooks.common.BasicServiceBinder;

import java.util.ArrayList;
import java.util.List;

public class MainService extends BasicService<MainService>
{
    public class MainServiceBinder extends BasicServiceBinder<MainService>
    {
        public MainServiceBinder(MainService service)
        {
            super(service);
        }

        @Override
        public Class<?> getServiceClass()
        {
            return MainService.class;
        }
    }

    private static final String TAG = "F3-MainServe";
    private static final String KILL = "com.piercelbrooks.f3.KILL";
    private static final int KILL_CODE = 2;
    private static final int NOTIFICATION_CODE = 1;

    public MainService()
    {
        super(/*TAG*/);
    }

    @Override
    protected void create()
    {

    }

    @Override
    protected void destroy()
    {

    }

    @Override
    public Class<?> getServiceClass()
    {
        return MainService.class;
    }

    @Override
    protected BasicServiceBinder<MainService> getBinder(MainService service)
    {
        return new MainServiceBinder(service);
    }

    @Override
    protected String getName()
    {
        return TAG;
    }

    @Override
    protected Integer getNotification()
    {
        return getNotificationCode();
    }

    @Override
    protected List<NotificationCompat.Action> getNotificationActions()
    {
        ArrayList<NotificationCompat.Action> actions = new ArrayList<>();
        PendingIntent action = PendingIntent.getBroadcast(getContext(), getKillCode(), (new Intent(getKill())).addCategory(Intent.CATEGORY_DEFAULT), 0);
        actions.add(new NotificationCompat.Action(R.drawable.empty, "KILL", action));
        return actions;
    }

    @Override
    public String getDescription()
    {
        return TAG;
    }

    public static String getKill()
    {
        return KILL;
    }

    public static int getKillCode()
    {
        return KILL_CODE;
    }

    public static int getNotificationCode()
    {
        return NOTIFICATION_CODE;
    }
}
