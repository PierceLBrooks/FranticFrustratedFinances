
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;

public abstract class Tests
{
    private static final String TAG = "Tests";

    public interface Test <T>
    {
        public boolean test(T subject);
        public void testBefore(int phase, T subject);
        public void testAfter(int phase, T subject);
    }

    public static abstract class SerialTest <T extends Serial> implements Test<T>
    {
        public static String getPath()
        {
            String path = ((MainApplication)Governor.getInstance().getCitizen(Family.APPLICATION)).getDataPath();
            path += "test";
            return path;
        }

        @Override
        public void testBefore(int phase, T subject)
        {
            Log.d(TAG, ""+subject);
            switch (phase)
            {
                case 0:
                    Log.d(TAG, "Saving...");
                    break;
                case 1:
                    Log.d(TAG, "Loading...");
                    break;
            }
        }

        @Override
        public void testAfter(int phase, T subject)
        {
            switch (phase)
            {
                case 0:
                    Log.d(TAG, "Saved!");
                    break;
                case 1:
                    Log.d(TAG, "Loaded!");
                    break;
            }
            Log.d(TAG, ""+subject);
        }
    }

    public static class DateTimeTest extends SerialTest<DateTime>
    {
        @Override
        public boolean test(DateTime subject)
        {
            for (int i = 0; i != 2; ++i)
            {
                testBefore(i, subject);
                switch (i)
                {
                    case 0:
                        subject.set(0, 0, 0);
                        if (!subject.save(getPath()))
                        {
                            return false;
                        }
                        break;
                    case 1:
                        subject.set(1, 1, 1);
                        if (!subject.load(getPath()))
                        {
                            return false;
                        }
                        break;
                }
                testAfter(i, subject);
            }
            return true;
        }
    }

    public static boolean testDateTime()
    {
        DateTime serial = new DateTime();
        DateTimeTest test = new DateTimeTest();
        return test.test(serial);
    }
}
