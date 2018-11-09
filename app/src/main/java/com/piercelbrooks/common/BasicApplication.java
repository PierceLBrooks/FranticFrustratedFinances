
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.widget.Toast;

public abstract class BasicApplication extends Application implements Application.ActivityLifecycleCallbacks, Citizen {
    private static final String TAG = "PLB-BasicApp";

    public abstract @DrawableRes int getEmptyDrawable();
    protected abstract void create();
    protected abstract void terminate();
    protected abstract void activityCreated(Activity activity);
    protected abstract void activityDestroyed(Activity activity);
    protected abstract void activityStarted(Activity activity);
    protected abstract void activityStopped(Activity activity);
    protected abstract void activityResumed(Activity activity);
    protected abstract void activityPaused(Activity activity);

    private Governor governor;
    private Preferences preferences;

    public static BasicApplication getInstance() {
        return (BasicApplication)Governor.getInstance().getCitizen(Family.APPLICATION);
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void makeToast(String message) {
        Toast.makeText(this, message, Constants.TOAST_DURATION);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        birth();
        create();
    }

    @Override
    public void onTerminate() {
        terminate();
        unregisterActivityLifecycleCallbacks(this);
        death();
        super.onTerminate();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        activityCreated(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activityStarted(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        activityResumed(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {
        activityPaused(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityStopped(activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        activityDestroyed(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public Family getFamily() {
        return Family.APPLICATION;
    }

    @Override
    public void birth() {
        preferences = new Preferences(this);
        governor = new Governor();
        governor.birth();
        governor.register(this);
    }

    @Override
    public void death() {
        preferences = null;
        governor.unregister(this);
        governor.death();
        governor = null;
    }
}
