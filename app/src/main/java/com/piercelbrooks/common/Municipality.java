
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface Municipality <T extends Enum<T>> extends Citizen
{
    public T popBack();
    public boolean onBack();
    public <U extends Fragment & Mayor<T>> boolean getIsTemporary(@Nullable U fragment);
    public <U extends Fragment & Mayor<T>> void postShow(@Nullable U previous, @Nullable U current);
    public <U extends Fragment & Mayor<T>> void preShow(@Nullable U fragment);
    public <U extends Fragment & Mayor<T>> void onShow(@Nullable U fragment);
    public <U extends Fragment & Mayor<T>> boolean show(@Nullable U fragment);
    public <U extends Fragment & Mayor<T>> U getNewMayor(@Nullable T mayoralFamily);
    public BasicApplication getOwner();
}
