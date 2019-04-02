
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

public class RepositoryFragment extends BasicFragment<MayoralFamily> implements Accountant {
    public static class RepositoryNameFragment extends EditorFragment {
        private RepositoryFragment repository;
        private String name;

        public RepositoryNameFragment() {
            super();
            repository = null;
            name = null;
        }

        public void setRepository(RepositoryFragment repository) {
            this.repository = repository;
            this.name = repository.getName();
        }

        @Override
        public int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_NORMAL|EditorInfo.TYPE_CLASS_TEXT;
        }

        @Override
        public String getExit()
        {
            return "RESET";
        }

        @Override
        public String getSave()
        {
            return "SET";
        }

        @Override
        public String getField() {
            return repository.getName();
        }

        @Override
        public String getTitle() {
            return "REPOSITORY NAME";
        }

        @Override
        public void onExit() {
            setField(name);
        }

        @Override
        public void onSave(String field) {
            name = field;
            repository.getLedger().getTargetRepository().setName(name);
            Utilities.closeKeyboard(getActivity());
        }

        @Override
        public MayoralFamily getMayoralFamily() {
            return MayoralFamily.REPOSITORY_NAME;
        }

        @Override
        public Class<?> getCitizenClass() {
            return RepositoryNameFragment.class;
        }
    }

    public static class RepositoryURLFragment extends EditorFragment {
        private RepositoryFragment repository;
        private String url;

        public RepositoryURLFragment() {
            super();
            repository = null;
            url = null;
        }

        public void setRepository(RepositoryFragment repository) {
            this.repository = repository;
            this.url = repository.getURL();
        }

        @Override
        public int getInputType() {
            return EditorInfo.TYPE_TEXT_VARIATION_NORMAL|EditorInfo.TYPE_CLASS_TEXT;
        }

        @Override
        public String getExit()
        {
            return "RESET";
        }

        @Override
        public String getSave()
        {
            return "SET";
        }

        @Override
        public String getField() {
            return repository.getURL();
        }

        @Override
        public String getTitle() {
            return "REPOSITORY URL";
        }

        @Override
        public void onExit() {
            setField(url);
        }

        @Override
        public void onSave(String field) {
            url = field;
            repository.getLedger().getTargetRepository().setURL(field);
            Utilities.closeKeyboard(getActivity());
        }

        @Override
        public MayoralFamily getMayoralFamily() {
            return MayoralFamily.REPOSITORY_URL;
        }

        @Override
        public Class<?> getCitizenClass() {
            return RepositoryURLFragment.class;
        }
    }

    private static final String TAG = "F3-RepoFrag";

    private Ledger ledger;
    private RepositoryNameFragment name;
    private RepositoryURLFragment url;
    private Button repositoryExit;

    public RepositoryFragment() {
        super();
        ledger = null;
        name = null;
        url = null;
        repositoryExit = null;
    }

    public String getName() {
        return ledger.getTargetRepository().getName();
    }

    public String getURL() {
        return ledger.getTargetRepository().getURL();
    }

    @Override
    public @LayoutRes int getLayout() {
        return R.layout.repository_fragment;
    }

    @Override
    public void createView(@NonNull View view) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        name = new RepositoryNameFragment();
        url = new RepositoryURLFragment();
        name.setRepository(this);
        url.setRepository(this);
        name.birth();
        manager.beginTransaction().replace(R.id.repository_name_slot, name, null).commit();
        url.birth();
        manager.beginTransaction().replace(R.id.repository_url_slot, url, null).commit();
        repositoryExit = view.findViewById(R.id.repository_exit);
        repositoryExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getMunicipality()).showRepositories(getLedger());
            }
        });
    }

    @Override
    public void onBirth() {

    }

    @Override
    public void onDeath() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        name.death();
        manager.beginTransaction().remove(name).commitAllowingStateLoss();
        url.death();
        manager.beginTransaction().remove(url).commitAllowingStateLoss();
    }

    @Override
    public MayoralFamily getMayoralFamily() {
        return MayoralFamily.REPOSITORY;
    }

    @Override
    public Class<?> getCitizenClass() {
        return RepositoryFragment.class;
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
