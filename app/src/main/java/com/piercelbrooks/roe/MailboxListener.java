
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;

public interface MailboxListener {
    MailProperties getProperties();
    public void onDone(@NonNull Mailbox sender);
}
