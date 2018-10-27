
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
    private static final String TAG = "PLB-BasicFrag";

    protected abstract @LayoutRes int getInflationResource();
    protected abstract void createView(@NonNull View view);
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
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createView(view);
    }

    @Override
    public Family getFamily() {
        return Family.FRAGMENT;
    }

    @Override
    public void birth() {
        Mayor.getInstance().register(this);
        onBirth();
    }

    @Override
    public void death() {
        onDeath();
        Mayor.getInstance().unregister(this);
    }
}
