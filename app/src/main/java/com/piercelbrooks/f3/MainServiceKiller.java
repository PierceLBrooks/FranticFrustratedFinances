
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainServiceKiller extends BroadcastReceiver
{
    private static final String TAG = "F3-MainServeKill";

    public MainServiceKiller()
    {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if ((context == null) || (intent == null))
            {
                Log.e(TAG, "Kill error 1!");
                return;
            }
            if (intent.getAction() == null)
            {
                Log.e(TAG, "Kill error 2!");
                return;
            }
            if (!intent.getAction().equals(MainService.getKill()))
            {
                Log.e(TAG, "Kill error 3!");
                return;
            }
            if (context.getApplicationContext() == null)
            {
                Log.e(TAG, "Kill error 4!");
                return;
            }
            if (!(context.getApplicationContext() instanceof MainApplication))
            {
                Log.e(TAG, "Kill error 5!");
                return;
            }
            if (!((MainApplication)context.getApplicationContext()).killService())
            {
                Log.e(TAG, "Kill error 6!");
                return;
            }
            Log.v(TAG, "Kill success!");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            Log.e(TAG, "Kill error 0!");
        }
    }
}
