
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Event implements Persistable<EventMember>
{
    private Ledger owner;
    private final DateTime dateTime;
    private final int index;

    public Event(Ledger owner, DateTime dateTime, int index)
    {
        this.owner = owner;
        this.dateTime = dateTime;
        this.index = index;
    }

    public void setOwner(Ledger owner)
    {
        this.owner = owner;
    }

    public Ledger getOwner()
    {
        return owner;
    }

    public DateTime getDateTime()
    {
        return dateTime;
    }

    public int getIndex()
    {
        return index;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Event.class;
    }

    @Override
    public Serial<EventMember> getDeserialization(List<String> source)
    {
        return null;
    }

    @Override
    public String getIdentifier()
    {
        return "Event";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        EventMember[] members = EventMember.values();
        EventMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == EventMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(EventMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(EventMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        serialization.add(getMemberIdentifier(member));
        switch (member)
        {
            case INDEX:
                serialization.add(""+index);
                break;
            case DATE_TIME:
                Utilities.add(serialization, dateTime.getSerialization(), "\t");
                break;
        }
        return serialization;
    }

    @Override
    public boolean save(String path)
    {
        return false;
    }

    @Override
    public boolean load(String path)
    {
        return false;
    }
}
