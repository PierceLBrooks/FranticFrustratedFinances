
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

public abstract class Text extends android.support.v7.widget.AppCompatEditText implements TextWatcher
{
    private class TextInput extends InputConnectionWrapper implements View.OnKeyListener
    {
        private static final String TAG = "PLB-TextInput";

        private Text owner;
        private View.OnKeyListener listener;

        public TextInput(Text owner, InputConnection target, boolean mutable)
        {
            super(target, mutable);
            this.owner = owner;
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength)
        {
            if ((beforeLength == 1) && (afterLength == 0))
            {
                boolean result = true;
                result &= sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                result &= sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
                return result;
            }
            if ((beforeLength == 0) && (afterLength == 1))
            {
                boolean result = true;
                result &= sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_FORWARD_DEL));
                result &= sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_FORWARD_DEL));
                return result;
            }
            return super.deleteSurroundingText(beforeLength, afterLength);
        }

        @Override
        public boolean onKey(View view, int keyCode, KeyEvent event)
        {
            Log.d(TAG, "onKey");
            if (owner != null)
            {
                int action = event.getAction();
                boolean result = false;
                switch (keyCode)
                {
                    case KeyEvent.KEYCODE_DEL:
                    case KeyEvent.KEYCODE_FORWARD_DEL:
                        switch (action)
                        {
                            case KeyEvent.ACTION_DOWN:
                                owner.delete(true);
                                break;
                            case KeyEvent.ACTION_UP:
                                owner.delete(false);
                                break;
                        }
                        result = true;
                        break;
                }
                return result;
            }
            return false;
        }
    }

    private static final String TAG = "PLB-Text";

    private TextListener listener;
    private String cache;
    private TextInput input;

    protected abstract void onInitialize(Context context);

    public Text(Context context)
    {
        super(context);
        initialize(context);
    }

    public Text(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    public Text(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context)
    {
        Log.d(TAG, "initialize "+Utilities.getIdentifier(this));
        listener = null;
        cache = null;
        input = null;
        addTextChangedListener(this);
        onInitialize(context);
    }

    public void setListener(TextListener listener)
    {
        this.listener = listener;
    }

    public TextListener getListener()
    {
        return listener;
    }

    public void change()
    {
        if (listener != null)
        {
            listener.onChange(this);
        }
    }

    public void delete(boolean action)
    {
        if (listener != null)
        {
            listener.onDelete(this, action);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (input != null)
        {
            input.onKey(this, keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if (input != null)
        {
            input.onKey(this, keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event)
    {
        if (input != null)
        {
            input.onKey(this, keyCode, event);
        }
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs)
    {
        input = new TextInput(this, super.onCreateInputConnection(outAttrs), false);
        return input;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {
        cache = getText().toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {
        if (cache == null)
        {
            change();
            return;
        }
        if (!getText().toString().equals(cache))
        {
            change();
            return;
        }
        Log.d(TAG, "No change...");
    }
}
