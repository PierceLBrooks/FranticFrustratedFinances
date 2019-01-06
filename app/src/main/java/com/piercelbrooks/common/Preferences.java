
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class Preferences
{
    private static final String TAG = "PLB-Preferences";

    private SharedPreferences preferences;

    public Preferences(@NonNull Preferences other)
    {
        preferences = other.getSource();
    }

    public Preferences(@NonNull Context context)
    {
        preferences = context.getSharedPreferences(Constants.TAG, Context.MODE_PRIVATE);
    }

    protected SharedPreferences getSource()
    {
        return preferences;
    }
}
