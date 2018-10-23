
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;

import com.piercelbrooks.common.BasicActivity;
import com.piercelbrooks.roe.AuthorFragment;

public class MainActivity extends BasicActivity {
    private static final String TAG = "F3-MainActivity";

    @Override
    protected void create() {

    }

    @Override
    protected void destroy() {

    }

    @Override
    protected void start() {

    }

    @Override
    protected void stop() {

    }

    @Override
    protected void resume() {
        show(new AuthorFragment());
    }

    @Override
    protected void pause() {

    }

    @Override
    protected @IdRes int getFragmentSlot() {
        return R.id.fragment_slot;
    }

    @Override
    protected @LayoutRes int getLayout() {
        return R.layout.activity_main;
    }
}
