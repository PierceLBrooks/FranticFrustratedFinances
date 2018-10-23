
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public abstract class BasicActivity extends FragmentActivity implements Citizen {
    private static final String TAG = "PLB-BasicActivity";

    protected abstract void create();
    protected abstract void destroy();
    protected abstract void start();
    protected abstract void stop();
    protected abstract void resume();
    protected abstract void pause();
    protected abstract @IdRes int getFragmentSlot();
    protected abstract @LayoutRes int getLayout();

    private BasicFragment activeFragment;

    public void show(BasicFragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        if (activeFragment != null) {
            activeFragment.death();
            manager.beginTransaction().remove(activeFragment).commitNow();
        }
        activeFragment = fragment;
        if (fragment != null) {
            manager.beginTransaction().replace(getFragmentSlot(), fragment, null).commitNow();
        }
    }

    public BasicFragment getActiveFragment() {
        return activeFragment;
    }

    private void restart() {
        pause();
        stop();
        start();
        resume();
    }

    private void setContentView() {
        int layout = getLayout();
        Log.d(TAG, "Setting content view (0x"+Utilities.getHax(layout)+")...");
        setContentView(layout);
    }

    private void commonOnCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        birth();
        activeFragment = null;
        setContentView();
        create();
    }

    private void commonOnSaveInstanceState(Bundle outState, @Nullable PersistableBundle outPersistentState) {

    }

    private void commonOnRestoreInstanceState(Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        commonOnCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commonOnCreate(savedInstanceState, null);
    }

    @Override
    protected void onDestroy() {
        destroy();
        death();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        start();
    }

    @Override
    protected void onStop() {
        stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onPause() {
        pause();
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        commonOnSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        commonOnSaveInstanceState(outState, null);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        commonOnRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        commonOnRestoreInstanceState(savedInstanceState, null);
    }

    @Override
    public Family getFamily() {
        return Family.ACTIVITY;
    }

    @Override
    public void birth() {
        Governor.getInstance().register(this);
    }

    @Override
    public void death() {
        Governor.getInstance().unregister(this);
    }
}
