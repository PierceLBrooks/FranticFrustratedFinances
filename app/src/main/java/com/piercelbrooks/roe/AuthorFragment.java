
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.f3.R;

public class AuthorFragment extends BasicFragment
{
    private static final String TAG = "ROE-AuthorFragment";

    private TextView outputLabel;
    private TextView inputLabel;
    private EditText outputScript;
    private EditText inputScript;
    private Button authorExit;
    private Button authorRun;
    private ScriptBank bank;

    @Override
    protected @LayoutRes int getInflationResource()
    {
        return R.layout.author_fragment;
    }

    @Override
    protected void createView(View view)
    {
        outputLabel = view.findViewById(R.id.output_label);
        inputLabel = view.findViewById(R.id.input_label);
        outputScript = view.findViewById(R.id.output_script);
        inputScript  = view.findViewById(R.id.input_script);
        authorExit = view.findViewById(R.id.author_exit);
        authorRun = view.findViewById(R.id.author_run);
        authorRun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Script input = new Script(inputScript.getText().toString(), "run");
                String output = input.run();
                outputScript.setText(output);
                Log.d(TAG, output);
            }
        });
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
        bank = null;
    }
}
