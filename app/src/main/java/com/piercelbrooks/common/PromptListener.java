
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.support.annotation.NonNull;

public interface PromptListener
{
    public void onPositive(@NonNull Prompt sender);
    public void onNegative(@NonNull Prompt sender);
    public void onNeutral(@NonNull Prompt sender);
}
