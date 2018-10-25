
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;

import com.piercelbrooks.common.LinedText;

public class AuthorScript extends LinedText
{
    public AuthorScript(Context context)
    {
        super(context);
    }

    public AuthorScript(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AuthorScript(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected @ColorRes int getColor()
    {
        return R.color.colorAccent;
    }
}
