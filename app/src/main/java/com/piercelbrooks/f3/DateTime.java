
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DateTime implements Persistable<DateTimeMember>
{
    private static final String TAG = "F3-DateTime";

    private int day;
    private int month;
    private int year;

    public DateTime()
    {
        set();
    }

    public DateTime(@Nullable DateTime other)
    {
        set(other);
    }

    public DateTime(@Nullable DateTime other, int change, @NonNull DateTimeMember member) {
        this(other);
        if (!set(change, member))
        {
            set();
        }
    }

    public void set()
    {
        set(-1, -1, -1);
    }

    public void set(@Nullable DateTime other)
    {
        if (other == null)
        {
            return;
        }
        set(other.getYear(), other.getMonth(), other.getDay());
    }

    public void set(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public boolean set(int change, @NonNull DateTimeMember member)
    {
        switch (member)
        {
            case NONE:
                break;
            case YEAR:
                year = change;
                break;
            case MONTH:
                month = change;
                break;
            case DAY:
                day = change;
                break;
            default:
                return false;
        }
        return true;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return DateTime.class;
    }

    @Override
    public Serial<DateTimeMember> getDeserialization(List<String> source)
    {
        int i;
        DateTime deserialization = new DateTime();
        DateTimeMember[] enumeration = DateTimeMember.values();
        ArrayList<DateTimeMember> members = new ArrayList<>();
        String[] assignment;
        String data;
        for (i = 0; i != enumeration.length; ++i)
        {
            members.add(enumeration[i]);
        }
        for (i = 0; i < source.size(); ++i)
        {
            data = source.get(i).trim();
            if (i == 0)
            {
                if (!data.equals(getIdentifier()))
                {
                    Log.e(TAG, "Incorrect identifier ("+data+")!");
                    return null;
                }
                continue;
            }
            assignment = data.split("=");
            if (assignment.length == 2)
            {
                for (int j = 0; j != members.size(); ++j)
                {
                    if (assignment[0].trim().equals(members.get(j).name()))
                    {
                        if (!deserialization.set(Integer.parseInt(assignment[1].trim()), members.get(j)))
                        {
                            Log.w(TAG, "Failed to deserialize member: \""+members.get(j).name()+"\"");
                        }
                        members.remove(j);
                        break;
                    }
                }
                if (members.isEmpty())
                {
                    break;
                }
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
    public String getIdentifier()
    {
        return "DateTime";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        DateTimeMember[] members = DateTimeMember.values();
        DateTimeMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == DateTimeMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(DateTimeMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(DateTimeMember member)
    {
        int value;
        ArrayList<String> serialization = new ArrayList<>();
        serialization.add(getMemberIdentifier(member));
        switch (member)
        {
            case YEAR:
                value = year;
                break;
            case MONTH:
                value = month;
                break;
            case DAY:
                value = day;
                break;
            default:
                value = -1;
                break;
        }
        serialization.add(""+value);
        return serialization;
    }

    @Override
    public boolean save(String path)
    {
        List<String> serialization = getSerialization();
        ArrayList<String> output = new ArrayList<>();
        String data = "";
        for (int i = 0; i != serialization.size(); ++i)
        {
            data += serialization.get(i);
            if ((i-1)%2 == 0)
            {
                data += "=";
            }
            else
            {
                output.add(data);
                data = "";
            }
        }
        return Utilities.write(path, output);
    }

    @Override
    public boolean load(String path)
    {
        ArrayList<String> input = new ArrayList<>();
        if (!Utilities.read(path, input))
        {
            return false;
        }
        set((DateTime)getDeserialization(input));
        return true;
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
