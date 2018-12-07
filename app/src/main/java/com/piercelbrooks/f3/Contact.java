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
        Log.d(TAG, "New address: "+this.address);
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
        ContactMember[] members = ContactMember.values();
        ContactMember member;
        String data;
        for (i = 0; i != source.size(); ++i)
        {
            data = source.get(i).trim();
            Log.d(TAG, "Data: "+data);
            if (i == 0)
            {
                if (!data.equalsIgnoreCase(getIdentifier()))
                {
                    Log.e(TAG, "Incorrect identifier ("+data+")!");
                    return null;
                }
                continue;
            }
            for (int j = 0; j != members.length; ++j)
            {
                member = members[j];
                if (data.equalsIgnoreCase(member.name()))
                {
                    Log.d(TAG, "Member: "+member.toString()+" ("+data+")");
                    switch (member)
                    {
                        case ADDRESS:
                            if (i + 1 != source.size())
                            {
                                deserialization.setAddress(source.get(i+1).trim());
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            }
                            else
                            {
                                Log.e(TAG, "Malformed address!");
                                return null;
                            }
                            break;
                        default:
                            Log.e(TAG, "Unhandled member ("+data+")!");
                            return null;
                    }
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
