package com.piercelbrooks.f3;

import java.util.List;

public class Contact implements Persistable<ContactMember>
{
    private Ledger owner;
    private String address;

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
    public boolean save(String path)
    {
        return false;
    }

    @Override
    public boolean load(String path)
    {
        return false;
    }

    @Override
    public Serial<ContactMember> deserialize(List<String> source)
    {
        return null;
    }

    @Override
    public String getIdentifier()
    {
        return null;
    }

    @Override
    public List<String> getSerialization()
    {
        return null;
    }

    @Override
    public String getMemberIdentifier(ContactMember member)
    {
        return null;
    }

    @Override
    public List<String> getMemberSerialization(ContactMember member)
    {
        return null;
    }
}
