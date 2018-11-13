
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.piercelbrooks.common.BasicFragment;

public class AccountFragment extends BasicFragment<MayoralFamily> implements Accountant {
    public static class AccountAddressFragment extends EditorFragment {
        public AccountAddressFragment() {
            super();
        }

        @Override
        protected int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        }

        @Override
        protected String getField() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected void onSave(String field) {

        }

        @Override
        public MayoralFamily getMayoralFamily() {
            return MayoralFamily.ACCOUNT_ADDRESS;
        }

        @Override
        public Class<?> getCitizenClass() {
            return AccountAddressFragment.class;
        }
    }

    public static class AccountPasswordFragment extends EditorFragment {

        @Override
        protected int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
        }

        @Override
        protected String getField() {
            return null;
        }

        @Override
        protected String getTitle() {
            return null;
        }

        @Override
        protected void onExit() {

        }

        @Override
        protected void onSave(String field) {

        }

        @Override
        public MayoralFamily getMayoralFamily() {
            return MayoralFamily.ACCOUNT_PASSWORD;
        }

        @Override
        public Class<?> getCitizenClass() {
            return AccountPasswordFragment.class;
        }
    }

    private static final String TAG = "F3-AccountFrag";

    private Ledger ledger;

    public AccountFragment() {
        super();
        ledger = null;
    }

    @Override
    public @LayoutRes int getLayout() {
        return R.layout.account_fragment;
    }

    @Override
    public void createView(@NonNull View view) {

    }

    @Override
    public void onBirth() {

    }

    @Override
    public void onDeath() {

    }

    @Override
    public MayoralFamily getMayoralFamily() {
        return MayoralFamily.ACCOUNT;
    }

    @Override
    public Class<?> getCitizenClass() {
        return AccountFragment.class;
    }

    @Override
    public void setLedger(Ledger ledger)
    {
        this.ledger = ledger;
    }

    @Override
    public Ledger getLedger()
    {
        return ledger;
    }
}
