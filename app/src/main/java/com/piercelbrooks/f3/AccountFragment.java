
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import com.piercelbrooks.common.BasicFragment;
import com.piercelbrooks.common.Utilities;

public class AccountFragment extends BasicFragment<MayoralFamily> implements Accountant {
    public static class AccountAddressFragment extends EditorFragment {
        private AccountFragment account;
        private String address;

        public AccountAddressFragment() {
            super();
            account = null;
            address = null;
        }

        public void setAccount(AccountFragment account) {
            this.account = account;
            this.address = account.getAddress();
        }

        @Override
        public int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
        }

        @Override
        public String getField() {
            return account.getAddress();
        }

        @Override
        public String getTitle() {
            return "ACCOUNT ADDRESS";
        }

        @Override
        public void onExit() {
            setField(address);
        }

        @Override
        public void onSave(String field) {
            address = field;
            account.getLedger().getAccount().setAddress(field);
            Utilities.closeKeyboard(getActivity());
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
        private AccountFragment account;
        private String password;

        public AccountPasswordFragment() {
            super();
            account = null;
            password = null;
        }

        public void setAccount(AccountFragment account) {
            this.account = account;
            this.password = account.getPassword();
        }

        @Override
        public int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_PASSWORD;
        }

        @Override
        public String getField() {
            return account.getPassword();
        }

        @Override
        public String getTitle() {
            return "ACCOUNT PASSWORD";
        }

        @Override
        public void onExit() {
            setField(password);
        }

        @Override
        public void onSave(String field) {
            password = field;
            account.getLedger().getAccount().setPassword(field);
            Utilities.closeKeyboard(getActivity());
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
    private AccountAddressFragment address;
    private AccountPasswordFragment password;
    private Button accountExit;
    private Button accountTest;

    public AccountFragment() {
        super();
        ledger = null;
        address = null;
        password = null;
        accountExit = null;
        accountTest = null;
    }

    public String getAddress() {
        return ledger.getAccount().getAddress();
    }

    public String getPassword() {
        return ledger.getAccount().getPassword();
    }

    @Override
    public @LayoutRes int getLayout() {
        return R.layout.account_fragment;
    }

    @Override
    public void createView(@NonNull View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        address = new AccountAddressFragment();
        password = new AccountPasswordFragment();
        address.setAccount(this);
        password.setAccount(this);
        address.birth();
        manager.beginTransaction().replace(R.id.account_address_slot, address, null).commit();
        password.birth();
        manager.beginTransaction().replace(R.id.account_password_slot, password, null).commit();
        accountExit = view.findViewById(R.id.account_exit);
        accountExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getMunicipality()).showLobby(getLedger());
            }
        });
        accountTest = view.findViewById(R.id.account_test);
        accountTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getMunicipality()).showPassword(getLedger(), MayoralFamily.LOBBY, MayoralFamily.MAIL_TEST);
            }
        });
    }

    @Override
    public void onBirth() {

    }

    @Override
    public void onDeath() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        address.death();
        manager.beginTransaction().remove(address).commitAllowingStateLoss();
        password.death();
        manager.beginTransaction().remove(password).commitAllowingStateLoss();
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
