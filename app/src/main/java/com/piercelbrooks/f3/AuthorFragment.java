
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.BasicApplication;
import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Text;
import com.piercelbrooks.common.TextListener;
import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.Script;
import com.piercelbrooks.roe.ScriptBank;

public class AuthorFragment extends BasicFragment implements TextListener
{
    private static final String TAG = "F3-AuthorFrag";

    private TextView outputLabel;
    private TextView callLabel;
    private TextView inputLabel;
    private Text outputScript;
    private Text call;
    private Text inputScript;
    private Button authorExit;
    private Button authorClear;
    private Button authorRun;
    private ScriptBank bank;

    @Override
    protected @LayoutRes int getInflationResource()
    {
        return R.layout.author_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        outputLabel = view.findViewById(R.id.output_label);
        callLabel = view.findViewById(R.id.call_label);
        inputLabel = view.findViewById(R.id.input_label);
        outputScript = view.findViewById(R.id.output_script);
        call = view.findViewById(R.id.call);
        inputScript  = view.findViewById(R.id.input_script);
        authorExit = view.findViewById(R.id.author_exit);
        authorClear = view.findViewById(R.id.author_clear);
        authorRun = view.findViewById(R.id.author_run);

        outputScript.setListener(this);
        call.setListener(this);
        inputScript.setListener(this);

        authorExit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
            }
        });
        authorClear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                inputScript.setText("");
                call.setText("");
                outputScript.setText("");
            }
        });
        authorRun.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Script input = new Script(inputScript.getText().toString(), call.getText().toString());
                String output = input.run();
                if (output != null)
                {
                    outputScript.setText(output);
                    Log.d(TAG, output);
                }
                else
                {
                    BasicApplication.getInstance().makeToast("Error!");
                    Log.d(TAG, "Error!");
                }
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
        if (bank == null)
        {
            return;
        }
        bank.death();
        bank = null;
    }

    @Override
    public void onChange(@NonNull Text text)
    {
        Log.d(TAG, "onChange");
    }

    @Override
    public void onDelete(@NonNull Text text, boolean action)
    {
        Log.d(TAG, "onDelete");
    }
}
