
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import android.support.annotation.NonNull;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Utilities;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class Ruby implements Citizen {
    private static final String TAG = "ROE-Ruby";
    private static Ruby instance = null;

    private ScriptEngineManager manager;
    private ScriptEngine engine;
    private ScriptContext context;

    public Ruby()
    {

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
        Governor.getInstance().register(this);
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName("jruby");
        context = engine.getContext();
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
        Governor.getInstance().unregister(this);
    }

    @Override
    public Family getFamily()
    {
        return Family.SINGLETON;
    }

    public ScriptContext getContext()
    {
        return context;
    }

    public Object evaluate(@NonNull String script) throws ScriptException
    {
        boolean success = true;
        Object result = null;
        try
        {
            result = engine.eval(script, context);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            success = false;
            result = null;
        }
        if (!success)
        {
            throw new ScriptException();
        }
        return result;
    }

    public static Ruby getInstance()
    {
        return instance;
    }
}
