
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import java.util.Comparator;

public class SerialComparator <T extends Enum<T>, U extends Serial<T> & Comparable<U>> implements Comparator<U>
{
    public SerialComparator()
    {

    }

    @Override
    public int compare(U left, U right)
    {
        if ((left == null) || (right == null))
        {
            if (left == right)
            {
                return 0;
            }
            if (left == null)
            {
                return -1;
            }
            if (right == null)
            {
                return 1;
            }
        }
        return left.compareTo(right);
    }
}
