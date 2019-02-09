
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.content.Intent;

import com.piercelbrooks.common.BasicService;
import com.piercelbrooks.common.BasicServiceBinder;

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

    private static final String TAG = "F3-MainService";

    public MainService()
    {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {

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
}
