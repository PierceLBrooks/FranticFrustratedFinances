
// Author: Pierce Brooks

package com.piercelbrooks.roe;

import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Mortal;

import java.util.concurrent.atomic.AtomicInteger;

public class Script implements Mortal
{
    private static final String TAG = "ROE-Script";
    private static final String TEST_SCRIPT = "\ndef foo\n\treturn \"bar\"\nend\n";
    private static final String TEST_SCRIPT_ENTRY = "foo";

    private static Ruby ruby;
    private static AtomicInteger instances;

    private String body;
    private String entry;

    public Script()
    {
        initialize(TEST_SCRIPT, TEST_SCRIPT_ENTRY);
    }

    public Script(String body, String entry)
    {
        initialize(body, entry);
    }

    private void initialize(String body, String entry)
    {
        if (ruby == null)
        {
            ruby = new Ruby();
            ruby.birth();
        }
        if (instances == null)
        {
            instances = new AtomicInteger(0);
        }
        birth();
        this.body = body+"\n";
        this.entry = entry;
    }

    public String run()
    {
        return Ruby.getInstance().run(body, entry);
    }

    @Override
    public void birth()
    {
        instances.incrementAndGet();
    }

    @Override
    public void death()
    {
        if (instances.decrementAndGet() == 0)
        {
            Governor.getInstance().assassinate(ruby);
            ruby = null;
        }
    }
}
