
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BasicFragment <T extends Enum<T>> extends Fragment implements Mayor<T> {
    private static final String TAG = "PLB-BaseFrag";

    public BasicFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createView(view);
    }

    @Override
    public Family getFamily() {
        return Family.MAYOR;
    }

    @Override
    public Municipality<T> getMunicipality() {
        Activity activity = getActivity();
        if (activity instanceof  Municipality) {
            return (Municipality<T>)activity;
        }
        return null;
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
