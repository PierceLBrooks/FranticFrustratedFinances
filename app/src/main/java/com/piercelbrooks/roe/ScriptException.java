
// Author: Pierce Brooks

package com.piercelbrooks.roe;

public class ScriptException extends RuntimeException
{
    public ScriptException()
    {
        super();
    }

    public ScriptException(String message)
    {
        super(message);
    }

    public ScriptException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
