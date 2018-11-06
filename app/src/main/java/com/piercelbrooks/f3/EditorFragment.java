
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Text;
import com.piercelbrooks.common.TextListener;
import com.piercelbrooks.common.Utilities;

public abstract class EditorFragment extends BasicFragment implements TextListener
{
    private static final String TAG = "F3-EditorFrag";

    private Button editorExit;
    private Button editorSave;
    private TextView editorTitle;
    private EditorField editorField;

    protected abstract int getInputType();
    protected abstract String getField();
    protected abstract String getTitle();
    protected abstract void onExit();
    protected abstract void onSave(String field);

    public EditorFragment()
    {
        super();
        editorExit = null;
        editorSave = null;
        editorTitle = null;
        editorField = null;
    }

    @Override
    protected @LayoutRes int getLayout()
    {
        return R.layout.editor_fragment;
    }

    @Override
    protected void createView(@NonNull View view)
    {
        editorExit = view.findViewById(R.id.editor_exit);
        editorSave = view.findViewById(R.id.editor_save);
        editorTitle = view.findViewById(R.id.editor_title);
        editorField = view.findViewById(R.id.editor_field);

        editorTitle.setText(getTitle());
        editorField.setText(getField());
        editorField.setInputType(getInputType());
        editorField.setListener(this);

        editorExit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                onExit();
            }
        });

        editorSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utilities.closeKeyboard(getActivity());
                onSave(editorField.getText().toString());
            }
        });
    }

    @Override
    protected void onBirth()
    {

    }

    @Override
    protected void onDeath()
    {

    }

    @Override
    public void onChange(@NonNull Text text)
    {

    }

    @Override
    public void onDelete(@NonNull Text text, boolean action)
    {

    }
}
