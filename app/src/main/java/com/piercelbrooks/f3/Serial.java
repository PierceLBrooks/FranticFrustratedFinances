
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import java.util.List;

public interface Serial <T extends Enum<T>>
{
    public Serial<T> deserialize(List<String> source);
    public String getIdentifier();
    public List<String> getSerialization();
    public String getMemberIdentifier(T member);
    public List<String> getMemberSerialization(T member);
}
