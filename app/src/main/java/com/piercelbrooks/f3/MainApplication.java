
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;

public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks, Citizen {
    private static final String TAG = "F3-MainApp";

    private Governor governor;

    @Override
    public void onCreate() {
        super.onCreate();
        birth();
    }

    @Override
    public void onTerminate() {
        death();
        super.onTerminate();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public Family getFamily() {
        return Family.APPLICATION;
    }

    @Override
    public void birth() {
        governor = new Governor();
        governor.birth();
        governor.register(this);
    }

    @Override
    public void death() {
        governor.unregister(this);
        governor.death();
    }
}
