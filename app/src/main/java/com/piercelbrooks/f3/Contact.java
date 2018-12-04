package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Contact implements Serial<ContactMember>
{
    private static final String TAG = "F3-Contact";

    private Ledger owner;
    private String address;

    public Contact()
    {
        this(null);
    }

    public Contact(Ledger owner)
    {
        this(owner, "");
    }

    public Contact(Ledger owner, String address)
    {
        this.owner = owner;
        this.address = address;
    }

    public void setOwner(Ledger owner)
    {
        this.owner = owner;
    }

    public Ledger getOwner()
    {
        return owner;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Contact.class;
    }

    @Override
    public Serial<ContactMember> getDeserialization(List<String> source)
    {
        int i;
        Contact deserialization = new Contact(owner);
        String[] assignment;
        String data;
        for (i = 0; i != source.size(); ++i)
        {
            if (i == 0)
            {
                continue;
            }
            data = source.get(i).trim();
            assignment = data.split("=");
            if (assignment.length == 2)
            {
                if (assignment[0].trim().equalsIgnoreCase(ContactMember.ADDRESS.name()))
                {
                    deserialization.setAddress(assignment[1].trim());
                    break;
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
        return "Contact";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        ContactMember[] members = ContactMember.values();
        ContactMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == ContactMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(ContactMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(ContactMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        switch (member)
        {
            case ADDRESS:
                serialization.add(address);
                break;
            default:
                return null;
        }
        serialization.add(0, getMemberIdentifier(member));
        return serialization;
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
