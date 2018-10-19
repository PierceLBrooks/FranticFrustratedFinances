
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Governor;

public class AuthorFragment extends BasicFragment
{
    private static final String TAG = "ROE-AuthorFragment";
    private static final String TEST_SCRIPT = "\ndef foo\n\treturn \"bar\"\nend\n";
    private static final String TEST_SCRIPT_ENTRY = "foo";

    private TextView display;
    private ScriptBank bank;

    @Override
    protected @LayoutRes int getInflationResource()
    {
        return 0;
    }

    @Override
    protected void createView(View view)
    {
        //display = view.findViewById(Governor.getInstance().getResources());
    }

    @Override
    protected void onBirth()
    {
        bank = new ScriptBank();
    }

    @Override
    protected void onDeath()
    {
        bank.death();
    }
}
