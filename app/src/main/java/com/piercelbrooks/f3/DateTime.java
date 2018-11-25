
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

    public DateTime(@Nullable DateTime other, int change, @NonNull DateTimeMember member)
    {
        this(other);
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
                set();
                break;
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
    public Serial<DateTimeMember> deserialize(List<String> source)
    {
        DateTime deserialization = new DateTime();
        String[] assignment;
        String data;
        for (int i = 0; i != source.size(); ++i)
        {
            if (i == 0)
            {
                continue;
            }
            data = source.get(i).trim();
            assignment = data.split("=");
            if (assignment.length == 2)
            {
                Log.d(TAG, ""+assignment);
            }
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
        set((DateTime)deserialize(input));
        return true;
    }

    @Override
    public String toString() {
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
