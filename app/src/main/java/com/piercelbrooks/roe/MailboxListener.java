
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;

public interface MailboxListener {
    public MailProperties getInboxProperties();
    public MailProperties getOutboxProperties();
    public void onDone(@NonNull Mailbox sender);
}
