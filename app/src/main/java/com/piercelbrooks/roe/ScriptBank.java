
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import com.piercelbrooks.common.Mortal;
import com.piercelbrooks.common.Registry;

import java.util.HashSet;
import java.util.TreeMap;

public class ScriptBank extends Registry<String, Script, HashSet<Script>, TreeMap<String, HashSet<Script>>> implements Mortal
{
    private static final String TAG = "ROE-ScriptBank";

    public ScriptBank()
    {
        super(new TreeMap<String, HashSet<Script>>());
        birth();
    }

    @Override
    public void birth()
    {

    }

    @Override
    public void death()
    {
        unregisterAll();
    }
}
