
// Author: Pierce Brooks

package com.piercelbrooks.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;

public class Governor extends ContextWrap
{
    private static final String TAG = "PLB-Governor";
    private static Governor instance = null;

    private Registry<Family, Citizen, HashSet<Citizen>, HashMap<Family, HashSet<Citizen>>> citizens;

    public Governor(Context context)
    {
        super(context);
        this.citizens = new Registry<>(new HashMap<Family, HashSet<Citizen>>());
    }

    public Citizen getCitizen(@NonNull Family family)
    {
        return citizens.get(family);
    }

    public boolean register(@Nullable Citizen citizen)
    {
        if (citizen == null)
        {
            return false;
        }
        return citizens.register(citizen.getFamily(), citizen);
    }

    public boolean unregister(@Nullable Citizen citizen)
    {
        if (citizen == null)
        {
            return false;
        }
        return citizens.unregister(citizen.getFamily(), citizen);
    }

    public void run(@Nullable Runnable runnable)
    {
        new Runner(runnable).start();
    }

    public void assassinate(@Nullable Mortal victim)
    {
        new Assassin(victim).start();
    }

    public static Governor getInstance()
    {
        return instance;
    }

    @Override
    public Family getFamily()
    {
        return Family.GOVERNOR;
    }

    @Override
    public void birth()
    {
        if (instance != null)
        {
            Utilities.throwSingletonException(TAG);
            return;
        }
        instance = this;
        register(this);
    }

    @Override
    public void death()
    {
        if (instance != this)
        {
            Utilities.throwSingletonException(TAG);
            return;
        }
        instance = null;
        unregister(this);
    }
}
