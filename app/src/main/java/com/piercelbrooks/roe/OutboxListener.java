
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface OutboxListener extends MailboxListener {
    public void onOut(@NonNull Outbox sender, @Nullable String recipient);
}
