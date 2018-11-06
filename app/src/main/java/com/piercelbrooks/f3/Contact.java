package com.piercelbrooks.f3;

public class Contact
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
}
