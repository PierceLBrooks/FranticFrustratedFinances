
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;

import com.piercelbrooks.common.BasicActivity;
import com.piercelbrooks.roe.AuthorFragment;
import com.piercelbrooks.roe.Script;

public class MainActivity extends BasicActivity {
    private static final String TAG = "F3-MainActivity";

    @Override
    protected void create() {
        findViewById(R.id.rubyTestButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Script script = new Script(Script.TEST_SCRIPT, Script.TEST_SCRIPT_ENTRY);
                String output = script.run();
                Log.i(TAG, output);
            }
        });
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
        //show(new AuthorFragment());
    }

    @Override
    protected void pause() {

    }

    @Override
    protected @IdRes int getFragmentSlot() {
        return 0;
    }

    @Override
    protected @LayoutRes int getLayout() {
        return R.layout.activity_main;
    }
}
