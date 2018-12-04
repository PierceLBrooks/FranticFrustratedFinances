
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public abstract class SerialList <T extends Serial> extends ArrayList<T> implements Serial<SerialListMember>
{
    private static final String TAG = "F3-SerialList";

    private int population;

    public abstract SerialList<T> getNew();
    public abstract T getSerial();

    public SerialList()
    {
        super();
        population = -1;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return SerialList.class;
    }

    @Override
    public Serial<SerialListMember> getDeserialization(List<String> source)
    {
        int i;
        SerialList<T> deserialization = getNew();
        T parent = getSerial();
        Serial child;
        for (i = 0; i != source.size(); ++i)
        {
            if (i == 0)
            {
                continue;
            }
            if (deserialization.getPopulation() < 0)
            {
                deserialization.setPopulation(Integer.parseInt(source.get(i)));
            }
            else
            {
                if (size() >= deserialization.getPopulation())
                {
                    break;
                }
                child = parent.getDeserialization(source);
                if (child.getSerialClass().isAssignableFrom(parent.getSerialClass()))
                {
                    deserialization.add((T)child);
                }
                else
                {
                    Log.w(TAG, "Cast failure at index: "+size());
                }
            }
        }
        for (int j = 0; j != i; ++j)
        {
            source.remove(0);
        }
        return deserialization;
    }

    @Override
    public String getIdentifier()
    {
        return "SerialList";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        SerialListMember[] members = SerialListMember.values();
        SerialListMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == SerialListMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(SerialListMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(SerialListMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        serialization.add(getMemberIdentifier(member));
        switch (member)
        {
            case POPULATION:
                serialization.add(""+size());
                break;
            case SERIALS:
                for (int i = 0; i != size(); ++i)
                {
                    Utilities.add(serialization, get(i).getSerialization());
                }
                break;
            default:
                return null;
        }
        return serialization;
    }

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
    }
}
