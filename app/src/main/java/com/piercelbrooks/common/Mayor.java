
// Author: Pierce Brooks

package com.piercelbrooks.common;

public interface Mayor <T extends Enum<T>> extends Citizen
{
    public T getMayoralFamily();
}
