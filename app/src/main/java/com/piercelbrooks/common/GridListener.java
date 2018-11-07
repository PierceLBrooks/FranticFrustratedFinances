
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.support.annotation.NonNull;
import android.view.View;

public interface GridListener <T extends View>
{
    public void onClick(@NonNull Grid<T>.Slot slot);
}
