
// Author: Pierce Brooks

package com.piercelbrooks.f3;

public interface SerialComparable <T extends Enum<T>, U extends Serial<T> & Comparable<U>> extends Serial<T>, Comparable<U>
{
    public SerialComparator<T, U> getComparator();
}
