
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.Script;

import java.util.ArrayList;
import java.util.List;

public class Action implements SerialComparable<ActionMember, Action>, LedgerProperty
{
    public class ActionComparator extends SerialComparator<ActionMember, Action>
    {
        public ActionComparator()
        {
            super();
        }
    }

    private static final String TAG = "F3-Action";

    private Ledger owner;
    private String name;
    private Script script;

    public Action()
    {
        this(null, "", null);
    }

    public Action(Ledger owner)
    {
        this(owner, "", null);
    }

    public Action(Ledger owner, String name)
    {
        this(owner, name, null);
    }

    public Action(Ledger owner, String name, Script script)
    {
        this.owner = owner;
        this.name = name;
        this.script = script;
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

    public void setScript(Script script)
    {
        this.script = script;
    }

    public Script getScript()
    {
        return script;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Contact.class;
    }

    @Override
    public Serial<ActionMember> getDeserialization(List<String> source)
    {
        int i;
        Action deserialization = new Action(owner);
        ActionMember[] members = ActionMember.values();
        ActionMember member;
        String data;
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
                        case SCRIPT:
                            if (i + 1 != source.size())
                            {
                                deserialization.setScript(new Script(source.get(i+1).trim(), deserialization.getName()));
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            }
                            else
                            {
                                Log.e(TAG, "Malformed script!");
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
            if ((!deserialization.getName().isEmpty()) && (deserialization.getScript() != null))
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
        return "Contact";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        ActionMember[] members = ActionMember.values();
        ActionMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == ActionMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(ActionMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(ActionMember member)
    {
        ArrayList<String> serialization = new ArrayList<>();
        switch (member)
        {
            case NAME:
                serialization.add(name);
                break;
            case SCRIPT:
                serialization.add(script.getBody());
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
    public int compareTo(Action other)
    {
        return name.compareTo(other.getName());
    }

    @Override
    public SerialComparator<ActionMember, Action> getComparator()
    {
        return new ActionComparator();
    }
}
