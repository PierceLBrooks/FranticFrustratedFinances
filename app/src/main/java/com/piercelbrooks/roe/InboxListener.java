
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface InboxListener extends MailboxListener {
    public void onIn(@NonNull Inbox sender, @Nullable List<Mail> mail);
}
