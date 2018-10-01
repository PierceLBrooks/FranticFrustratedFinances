package com.piercelbrooks.common;

import android.support.annotation.Nullable;
import android.util.Log;

public class Runner extends Thread
{
    private static final String TAG = "PLB-Runner";

    private Runnable runnable;

    public Runner(@Nullable Runnable runnable)
    {
        this.runnable = runnable;
    }

    @Override
    public void run()
    {
        if (runnable == null)
        {
            Log.i(TAG, "No runnable for me ("+Utilities.getIdentifier(this) +")!");
        }
        Log.i(TAG, "Running...");
        runnable.run();
        Log.i(TAG, "Ran!");
    }

    @Override
    public String toString()
    {
        return TAG;
    }
}
