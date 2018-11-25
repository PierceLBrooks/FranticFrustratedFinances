
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.piercelbrooks.common.BasicApplication;

import java.io.File;

public class MainApplication extends BasicApplication {
    private static final String TAG = "F3-MainApp";

    @Override
    public @DrawableRes int getEmptyDrawable() {
        return R.drawable.empty;
    }

    @Override
    protected void create() {
        DateTime test = new DateTime();
        String path = getApplicationInfo().dataDir;
        if (path.length() != 0)
        {
            if (path.charAt(path.length()-1) != File.separatorChar)
            {
                path += File.separatorChar;
            }
            path += "test.txt";
        }
        Log.d(TAG, "Path: "+path);
        Log.d(TAG, "Test:"+test);
        Log.d(TAG, "Saving...");
        if (test.save(path))
        {
            Log.d(TAG, "Success!");
            test.set(0, 0, 0);
            Log.d(TAG, "Test:"+test);
            Log.d(TAG, "Loading...");
            if (test.load(path))
            {
                Log.d(TAG, "Success!");
                Log.d(TAG, "Test:"+test);
            }
            else
            {
                Log.d(TAG, "Failure!");
            }
        }
        else
        {
            Log.d(TAG, "Failure!");
        }
    }

    @Override
    protected void terminate() {

    }

    @Override
    protected void activityCreated(Activity activity) {

    }

    @Override
    protected void activityDestroyed(Activity activity) {

    }

    @Override
    protected void activityStarted(Activity activity) {

    }

    @Override
    protected void activityStopped(Activity activity) {

    }

    @Override
    protected void activityResumed(Activity activity) {

    }

    @Override
    protected void activityPaused(Activity activity) {

    }

    @Override
    public Class<?> getCitizenClass() {
        return MainApplication.class;
    }
}