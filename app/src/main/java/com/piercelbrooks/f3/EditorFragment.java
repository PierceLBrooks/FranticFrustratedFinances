
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Text;
import com.piercelbrooks.common.TextListener;

public abstract class EditorFragment extends BasicFragment<MayoralFamily> implements TextListener
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
    public @LayoutRes int getLayout()
    {
        return R.layout.editor_fragment;
    }

    @Override
    public void createView(@NonNull View view)
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
                onExit();
            }
        });

        editorSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onSave(editorField.getText().toString());
            }
        });
    }

    @Override
    public void onBirth()
    {

    }

    @Override
    public void onDeath()
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

    public void setField(String field)
    {
        editorField.setText(field);
    }
}
