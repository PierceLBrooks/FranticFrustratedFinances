
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.util.Log;

import com.piercelbrooks.common.BasicActivity;
import com.piercelbrooks.common.BasicApplication;

import java.io.File;

public class MainApplication extends BasicApplication
{
    private static final String TAG = "F3-MainApp";

    private MainActivity activity;
    private boolean serviceKill;

    public boolean killService() {
        if (activity != null) {
            Log.v(TAG, "Killing service now!");
            serviceKill = false;
            return activity.endService();
        } else {
            startActivity(BasicActivity.getLauncher(getApplicationContext(), MainActivity.class));
        }
        Log.v(TAG, "Killing service later...");
        serviceKill = true;
        return true;
    }

    public String getDataPath() {
        String path = getApplicationInfo().dataDir;
        if (path.length() != 0) {
            if (path.charAt(path.length()-1) != File.separatorChar) {
                path += File.separatorChar;
            }
        }
        return path;
    }

    @Override
    public MainActivity getActivity() {
        return activity;
    }

    @Override
    public @DrawableRes int getEmptyDrawable() {
        return R.drawable.empty;
    }

    @Override
    protected void create() {
        serviceKill = false;
        activity = null;
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
        if (activity != null) {
            if (activity instanceof MainActivity) {
                this.activity = (MainActivity) activity;
                if (serviceKill) {
                    killService();
                }
            }
        }
    }

    @Override
    protected void activityPaused(Activity activity) {
        if (activity == this.activity) {
            this.activity = null;
        }
    }

    @Override
    public Class<?> getCitizenClass() {
        return MainApplication.class;
    }
}