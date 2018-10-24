
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasicFragment extends Fragment implements Citizen {
    private static final String TAG = "PLB-BasicFragment";

    protected abstract @LayoutRes int getInflationResource();
    protected abstract void createView(View view);
    protected abstract void onBirth();
    protected abstract void onDeath();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getInflationResource(), container, false);
        createView(view);
        return view;
    }

    @Override
    public Family getFamily() {
        return Family.FRAGMENT;
    }

    @Override
    public void birth() {
        Governor.getInstance().register(this);
        onBirth();
    }

    @Override
    public void death() {
        onDeath();
        Governor.getInstance().unregister(this);
    }
}
