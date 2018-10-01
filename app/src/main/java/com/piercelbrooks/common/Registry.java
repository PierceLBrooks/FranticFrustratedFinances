
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

public class Registry <T, U, V extends Set<U>, W extends Map<T, V>>
{
    private static final String TAG = "PLB-Registry";

    private W elements;

    public Registry(@NonNull W elements)
    {
        this.elements = elements;
    }

    public boolean register(@Nullable T key, @Nullable U element)
    {
        if ((key == null) || (element == null))
        {
            return false;
        }
        if (find(key, element))
        {
            return false;
        }
        return false;
    }

    public boolean unregister(@Nullable T key, @Nullable U element)
    {
        if ((key == null) || (element == null))
        {
            return false;
        }
        if (!find(key, element))
        {
            return false;
        }
        return false;
    }

    private boolean find(@NonNull T key, @NonNull U element)
    {
        if (!elements.containsKey(key))
        {
            return false;
        }
        if (!elements.get(element).contains(element))
        {
            return false;
        }
        return true;
    }
}
