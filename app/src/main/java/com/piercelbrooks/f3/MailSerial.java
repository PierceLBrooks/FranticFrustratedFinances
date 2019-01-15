
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import com.piercelbrooks.common.Utilities;
import com.piercelbrooks.roe.Mail;

import java.util.ArrayList;
import java.util.List;

public class MailSerial extends Mail implements Serial<MailSerialMember>
{
    private static final String TAG = "F3-MailSerial";

    public MailSerial(Mail other)
    {
        super(other);
    }

    @Override
    public Class<?> getSerialClass()
    {
        return MailSerial.class;
    }

    @Override
    public String getIdentifier()
    {
        return "MailSerial";
    }

    @Override
    public List<String> getSerialization()
    {
        ArrayList<String> serialization = new ArrayList<>();
        MailSerialMember[] members = MailSerialMember.values();
        MailSerialMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i)
        {
            member = members[i];
            if (member == MailSerialMember.NONE)
            {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(MailSerialMember member)
    {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(MailSerialMember member)
    {
        String serialization = "";
        switch (member)
        {
            case DATE:
                serialization += "";
                break;
            case SUBJECT:
                serialization += getSubject();
                break;
            case CONTENT:
                for (int i = 0; i != getContent().size(); ++i)
                {
                    serialization += getContent().get(i);
                    serialization += "\n";
                }
                break;
            case FROM:
                for (int i = 0; i != getFrom().size(); ++i)
                {
                    serialization += getFrom().get(i).getAddress();
                    serialization += "\n";
                }
                break;
            case TO:
                for (int i = 0; i != getTo().size(); ++i)
                {
                    serialization += getTo().get(i).getAddress();
                    serialization += "\n";
                }
                break;
        }
        if (!serialization.isEmpty())
        {
            ArrayList<String> result = new ArrayList<>();
            result.add(getMemberIdentifier(member));
            Utilities.add(result, Utilities.toString(serialization.split("\n")));
            return result;
        }
        return null;
    }

    @Override
    public Serial getDeserialization(List source)
    {
        Utilities.throwUnimplementedException("MailSerial::getDeserialization");
        return null;
    }
}
