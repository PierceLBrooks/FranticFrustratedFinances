
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.Municipality;

public abstract class GateFragment extends FieldFragment
{
    private static final String TAG = "F3-GateFrag";

    private Button gateExit;
    private Button gateEnter;
    private TextView gateTitle;
    private EditorField gateField;
    private MayoralFamily previous;
    private MayoralFamily next;

    public abstract boolean onEnter(String field);
    public abstract String getMessage();

    public GateFragment()
    {
        super();
        gateExit = null;
        gateEnter = null;
        gateTitle = null;
        gateField = null;
        previous = null;
        next = null;
    }

    @Override
    public @LayoutRes int getLayout()
    {
        return R.layout.gate_fragment;
    }

    @Override
    public void createView(@NonNull View view)
    {
        gateExit = view.findViewById(R.id.gate_exit);
        gateEnter = view.findViewById(R.id.gate_enter);
        gateTitle = view.findViewById(R.id.gate_title);
        gateField = view.findViewById(R.id.gate_field);

        gateTitle.setText(getTitle());
        gateField.setText(getField());
        gateField.setInputType(getInputType());
        gateField.setListener(this);

        gateExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onExit();
                if (!goPrevious())
                {
                    Log.e(TAG, "Failed to go to previous fragment!");
                }
            }
        });

        gateEnter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onEnter(gateField.getText().toString()))
                {
                    if (!goNext())
                    {
                        Log.e(TAG, "Failed to go to next fragment!");
                    }
                }
                else
                {
                    getMunicipality().getOwner().makeToast(getMessage());
                }
            }
        });
    }

    @Override
    public void setField(String field)
    {
        gateField.setText(field);
    }

    public MayoralFamily getPrevious()
    {
        return previous;
    }

    public void setPrevious(MayoralFamily previous)
    {
        this.previous = previous;
    }

    public MayoralFamily getNext()
    {
        return next;
    }

    public void setNext(MayoralFamily next)
    {
        this.next = next;
    }

    public boolean goPrevious()
    {
        return go(previous);
    }

    public boolean goNext()
    {
        return go(next);
    }

    private boolean go(MayoralFamily family)
    {
        if (family == null)
        {
            return false;
        }
        Municipality<MayoralFamily> municipality = getMunicipality();
        if (municipality == null)
        {
            return false;
        }
        if (!municipality.show(municipality.getNewMayor(family)))
        {
            Log.e(TAG, "Failed to go to: "+family.name());
            return false;
        }
        return true;
    }
}
