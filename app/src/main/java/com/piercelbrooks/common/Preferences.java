
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences
{
    SharedPreferences preferences;

    public Preferences(Context context)
    {
        preferences = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE);
    }
}
