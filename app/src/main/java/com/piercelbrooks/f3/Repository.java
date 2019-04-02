
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Repository implements SerialComparable<RepositoryMember, Repository>, LedgerProperty
{
    public class RepositoryComparator extends SerialComparator<RepositoryMember, Repository>
    {
        public RepositoryComparator()
        {
            super();
        }
    }

    private static final String TAG = "F3-Repo";

    private Ledger owner;
    private String name;
    private String url;

    public Repository()
    {
        this((Ledger)null);
    }

    public Repository(Ledger owner)
    {
        this(owner, "", "");
    }

    public Repository(Ledger owner, String name)
    {
        this(owner, name, "");
        this.url = "";
    }

    public Repository(Ledger owner, String name, String url)
    {
        this.owner = owner;
        this.name = name;
        this.url = url;
    }

    public Repository(Repository repository)
    {
        this(repository.getOwner(), repository.getName(), repository.getURL());
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

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setURL(String url)
    {
        this.url = url;
    }

    public String getURL()
    {
        return url;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Repository.class;
    }

    @Override
    public Serial<RepositoryMember> getDeserialization(List<String> source)
    {
        int i;
        Repository deserialization = new Repository(owner);
        RepositoryMember[] members = RepositoryMember.values();
        RepositoryMember member;
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
                        case NAME:
                            if (i + 1 != source.size())
                            {
                                deserialization.setName(source.get(i+1).trim());
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            }
                            else
                            {
                                Log.e(TAG, "Malformed name!");
                                return null;
                            }
                            break;
                        case URL:
                            if (i + 1 != source.size())
                            {
                                deserialization.setURL(source.get(i+1).trim());
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            }
                            else
                            {
                                Log.e(TAG, "Malformed URL!");
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
            if ((!deserialization.getName().isEmpty()) && (!deserialization.getURL().isEmpty()))
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
        return "Repository";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        RepositoryMember[] members = RepositoryMember.values();
        RepositoryMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == RepositoryMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(RepositoryMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(RepositoryMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        switch (member)
        {
            case NAME:
                serialization.add(""+name);
                break;
            case URL:
                serialization.add(""+url);
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

    @Override
    public int compareTo(Repository other)
    {
        return name.compareTo(other.getName());
    }

    @Override
    public SerialComparator<RepositoryMember, Repository> getComparator()
    {
        return new RepositoryComparator();
    }
}
