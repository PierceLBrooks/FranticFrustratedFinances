
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.Activity;
import android.support.annotation.DrawableRes;

import com.piercelbrooks.common.BasicApplication;

public class MainApplication extends BasicApplication {
    private static final String TAG = "F3-MainApp";

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

    }

    @Override
    protected void activityPaused(Activity activity) {

    }
}