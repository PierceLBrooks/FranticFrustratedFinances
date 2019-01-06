
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.piercelbrooks.common.BasicApplication;

import java.io.File;

public class MainApplication extends BasicApplication {
    private static final String TAG = "F3-MainApp";

    public String getDataPath() {
        String path = getApplicationInfo().dataDir;
        if (path.length() != 0)
        {
            if (path.charAt(path.length()-1) != File.separatorChar)
            {
                path += File.separatorChar;
            }
        }
        return path;
    }

    @Override
    public @DrawableRes int getEmptyDrawable() {
        return R.drawable.empty;
    }

    @Override
    protected void create() {

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
        //Tests.testDateTime();
    }

    @Override
    protected void activityPaused(Activity activity) {

    }

    @Override
    public Class<?> getCitizenClass() {
        return MainApplication.class;
    }
}