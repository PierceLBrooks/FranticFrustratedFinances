
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;

public abstract class BasicActivity <T extends Enum<T>> extends FragmentActivity implements Citizen {
    private class Shower <U extends Enum<U>, V extends Fragment & Mayor<U>> extends Handler {
        private class Runner <W extends Enum<W>, X extends Fragment & Mayor<W>> implements Runnable {
            private BasicActivity activity;
            private X fragment;

            public Runner(@NonNull BasicActivity activity, @Nullable X fragment) {
                this.activity = activity;
                this.fragment = fragment;
            }

            @Override
            public void run() {
                FragmentManager manager = activity.getSupportFragmentManager();
                Fragment activeFragment = activity.getActiveFragment();
                if (manager == null) {
                    return;
                }
                if (activeFragment != null) {
                    if (activeFragment instanceof Citizen) {
                        ((Citizen)activeFragment).death();
                    }
                    manager.beginTransaction().remove(activeFragment).commitNow();
                }
                activeFragment = fragment;
                activity.setActiveFragment(activeFragment);
                if (fragment != null) {
                    fragment.birth();
                    manager.beginTransaction().replace(getFragmentSlot(), fragment, null).commitNow();
                    Log.d(TAG, fragment.getMayoralFamily().name());
                }
            }
        }

        private BasicActivity activity;
        private V fragment;

        public Shower(@NonNull BasicActivity activity, @Nullable V fragment) {
            this.activity = activity;
            this.fragment = fragment;
        }

        public void post() {
            super.post(new Runner<>(activity, fragment));
        }
    }

    private static final String TAG = "PLB-BasicActivity";

    private Fragment activeFragment;
    private Window.Callback androidWindowCallback;
    private WindowCallback commonWindowCallback;

    protected abstract void create();
    protected abstract void destroy();
    protected abstract void start();
    protected abstract void stop();
    protected abstract void resume();
    protected abstract void pause();
    protected abstract @IdRes int getFragmentSlot();
    protected abstract @LayoutRes int getLayout();

    public void show() {
        show(null);
    }

    public <Y extends Fragment & Mayor<T>> void show(@Nullable Y fragment) {
        new Shower<>(this, fragment).post();
    }

    public Fragment getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(Fragment activeFragment) {
        this.activeFragment = activeFragment;
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
        androidWindowCallback = null;
        commonWindowCallback = null;
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
        Window window = getWindow();
        androidWindowCallback = window.getCallback();
        commonWindowCallback = new WindowCallback(this, androidWindowCallback);
        window.setCallback(commonWindowCallback);
        resume();
    }

    @Override
    protected void onPause() {
        pause();
        getWindow().setCallback(androidWindowCallback);
        commonWindowCallback.death();
        commonWindowCallback = null;
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
