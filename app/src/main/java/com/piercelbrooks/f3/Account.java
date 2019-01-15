
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Account implements Serial<AccountMember>, LedgerProperty
{
    private static final String TAG = "F3-Account";

    private Ledger owner;
    private String address;
    private String password;

    public Account()
    {
        this(null);
    }

    public Account(Ledger owner)
    {
        this.owner = owner;
        this.address = "";
        this.password = "";
    }

    @Override
    public void setOwner(Ledger owner)
    {
        this.owner = owner;
    }

    @Override
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

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Account.class;
    }

    @Override
    public Serial<AccountMember> getDeserialization(List<String> source)
    {
        int i;
        Account deserialization = new Account(owner);
        AccountMember[] members = AccountMember.values();
        AccountMember member;
        String data;
        for (i = 0; i < source.size(); ++i)
        {
            data = source.get(i).trim();
            if (data.equals(getIdentifier()))
            {
                for (int j = 0; j < i; ++j)
                {
                    source.remove(0);
                    if (source.isEmpty())
                    {
                        Log.e(TAG, "Too few data from source!");
                        return null;
                    }
                }
                break;
            }
        }
        for (i = 0; i < source.size(); ++i)
        {
            data = source.get(i).trim();
            Log.d(TAG, "Data: "+data);
            if (i == 0)
            {
                if (!data.equals(getIdentifier()))
                {
                    Log.e(TAG, "Incorrect identifier ("+data+")!");
                    return null;
                }
                continue;
            }
            for (int j = 0; j != members.length; ++j)
            {
                member = members[j];
                if (data.equals(member.name()))
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
                        case PASSWORD:
                            if (i + 1 != source.size())
                            {
                                deserialization.setPassword(source.get(i+1).trim());
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            }
                            else
                            {
                                Log.e(TAG, "Malformed password!");
                                return null;
                            }
                            break;
                        default:
                            Log.e(TAG, "Unhandled member ("+data+")!");
                            return null;
                    }
                    break;
                }
            }
            if ((!deserialization.getAddress().isEmpty()) && (!deserialization.getPassword().isEmpty()))
            {
                break;
            }
        }
        for (int j = 0; j < i; ++j)
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
        return "Account";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        AccountMember[] members = AccountMember.values();
        AccountMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == AccountMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(AccountMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(AccountMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        switch (member)
        {
            case ADDRESS:
                serialization.add(""+address);
                break;
            case PASSWORD:
                serialization.add(""+password);
                break;
            default:
                return null;
        }
        serialization.add(0, getMemberIdentifier(member));
        return serialization;
    }
}
