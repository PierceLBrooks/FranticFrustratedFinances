
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.support.annotation.NonNull;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class DateTime implements Serial<DateTimeMember>
{
    private int day;
    private int month;
    private int year;

    public DateTime()
    {
        set();
    }

    public DateTime(@NonNull DateTime other)
    {
        this(other.getYear(), other.getMonth(), other.getDay());
    }

    public DateTime(int year, int month, int day)
    {
        set(year, month, day);
    }

    public DateTime(@NonNull DateTime other, int change, @NonNull DateTimeMember member)
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
    public void deserialize(String source)
    {

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
}
