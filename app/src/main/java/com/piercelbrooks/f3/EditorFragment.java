
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public abstract class EditorFragment extends FieldFragment
{
    private static final String TAG = "F3-EditorFrag";

    private Button editorExit;
    private Button editorSave;
    private TextView editorTitle;
    private EditorField editorField;

    public abstract void onSave(String field);

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
                getMunicipality().getOwner().makeToast("Saved!");
                onSave(editorField.getText().toString());
            }
        });
    }

    @Override
    public void setField(String field)
    {
        editorField.setText(field);
    }
}
