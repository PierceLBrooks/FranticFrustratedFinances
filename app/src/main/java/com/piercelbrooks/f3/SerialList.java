
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

    public int getPopulation()
    {
        return population;
    }

    public void setPopulation(int population)
    {
        this.population = population;
        Log.d(TAG, "New population: "+population);
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
        boolean check = true;
        SerialList<T> deserialization = getNew();
        SerialListMember[] enumeration = SerialListMember.values();
        ArrayList<SerialListMember> members = new ArrayList<>();
        T parent = getSerial();
        Serial child;
        for (i = 0; i != enumeration.length; ++i)
        {
            members.add(enumeration[i]);
        }
        for (i = 0; i != source.size(); ++i)
        {
            Log.d(TAG, "Data: "+source.get(i).trim());
            if (check)
            {
                check = false;
                if (!source.get(i).trim().equalsIgnoreCase(getIdentifier()))
                {
                    Log.e(TAG, "Incorrect identifier ("+source.get(i).trim()+")!");
                    return null;
                }
                source.remove(i);
                --i;
                continue;
            }
            if (i+1 >= source.size())
            {
                break;
            }
            for (int j = 0; j != members.size(); ++j)
            {
                if (source.get(i).trim().equalsIgnoreCase(members.get(j).name()))
                {
                    source.remove(i);
                    switch (members.get(j))
                    {
                        case POPULATION:
                            if (deserialization.getPopulation() < 0)
                            {
                                deserialization.setPopulation(Integer.parseInt(source.get(i).trim()));
                                source.remove(i);
                                --i;
                            }
                            break;
                        case SERIALS:
                            for (int k = 0; k != deserialization.getPopulation(); ++k)
                            {
                                child = parent.getDeserialization(source);
                                if (child != null)
                                {
                                    if (child.getSerialClass().isAssignableFrom(parent.getSerialClass()))
                                    {
                                        deserialization.add((T)child);
                                    }
                                    else
                                    {
                                        Log.w(TAG, "Cast failure at index: "+deserialization.size());
                                    }
                                }
                                else
                                {
                                    Log.w(TAG, "Null child at index: "+deserialization.size());
                                }
                            }
                            break;
                        default:
                            Log.e(TAG, "Unhandled member ("+members.get(j).name()+")!");
                            break;
                    }
                    members.remove(j);
                    break;
                }
            }
            if (deserialization.size() >= deserialization.getPopulation())
            {
                break;
            }
            if (source.isEmpty())
            {
                break;
            }
        }
        for (int j = 0; j != i; ++j)
        {
            if (source.isEmpty())
            {
                break;
            }
            source.remove(0);
        }
        return deserialization;
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

    @Override
    public String toString()
    {
        List<String> serialization = getSerialization();
        String result = "";
        for (int i = 0; i != serialization.size(); ++i)
        {
            result += serialization.get(i);
            if (i != serialization.size()-1)
            {
                result += "\n";
            }
        }
        return result;
    }
}
