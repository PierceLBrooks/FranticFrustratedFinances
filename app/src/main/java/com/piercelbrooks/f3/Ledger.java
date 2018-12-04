
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Ledger implements Persistable<LedgerMember> {
    private class ContactList extends SerialList<Contact> {
        @Override
        public SerialList<Contact> getNew() {
            return new ContactList();
        }

        @Override
        public Contact getSerial() {
            return new Contact();
        }
    }

    private static Ledger current = null;

    private String name;
    private Action targetAction;
    private Contact targetContact;
    private DateTime targetDateTime;
    private Event targetEvent;

    public Ledger() {
        this("");
    }

    public Ledger(String name) {
        setCurrent(this);
        this.name = name;
        this.targetAction = new Action(this);
        this.targetContact = new Contact(this, "");
        this.targetDateTime = new DateTime();
        this.targetEvent = null;
    }

    public static void setCurrent(Ledger current) {
        Ledger.current = current;
        Citizen municipality = Governor.getInstance().getCitizen(Family.MUNICIPALITY);
        if (municipality.getCitizenClass().isInstance(Accountant.class)) {
            ((Accountant)municipality).setLedger(current);
        }
    }

    public static Ledger getCurrent() {
        return current;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setTargetAction(Action targetAction) {
        this.targetAction = targetAction;
        if (targetAction != null) {
            targetAction.setOwner(this);
        }
    }

    public Action getTargetAction() {
        return targetAction;
    }

    public void setTargetContact(Contact targetContact) {
        this.targetContact = targetContact;
        if (targetContact != null) {
            targetContact.setOwner(this);
        }
    }

    public Contact getTargetContact() {
        return targetContact;
    }

    public void setTargetDateTime(DateTime targetDateTime) {
        this.targetDateTime = targetDateTime;
    }

    public DateTime getTargetDateTime() {
        return targetDateTime;
    }

    public Event getTargetEvent() {
        return targetEvent;
    }

    public void setTargetEvent(Event targetEvent) {
        this.targetEvent = targetEvent;
        if (targetEvent != null) {
            targetEvent.setOwner(this);
        }
    }

    @Override
    public boolean save(String path) {
        return false;
    }

    @Override
    public boolean load(String path) {
        return false;
    }

    @Override
    public Class<?> getSerialClass()
    {
        return Ledger.class;
    }

    @Override
    public Serial<LedgerMember> getDeserialization(List<String> source) {
        int i;
        Ledger deserialization = new Ledger();
        for (i = 0; i != source.size(); ++i) {
            if (i == 0) {
                continue;
            }
        }
        for (int j = 0; j != i; ++j) {
            source.remove(0);
        }
        return deserialization;
    }

    @Override
    public String getIdentifier() {
        return "Ledger";
    }

    @Override
    public List<String> getSerialization() {
        ArrayList<String> serialization = new ArrayList<>();
        LedgerMember[] members = LedgerMember.values();
        LedgerMember member;
        serialization.add(getIdentifier());
        for (int i = 0; i != members.length; ++i) {
            member = members[i];
            if (member == LedgerMember.NONE) {
                continue;
            }
            Utilities.add(serialization, getMemberSerialization(member));
        }
        return serialization;
    }

    @Override
    public String getMemberIdentifier(LedgerMember member) {
        return member.name();
    }

    @Override
    public List<String> getMemberSerialization(LedgerMember member) {
        ArrayList<String> serialization = new ArrayList<String>();
        serialization.add(getMemberIdentifier(member));
        switch (member) {
            case CONTACTS: {
                    ContactList contacts = new ContactList();
                    contacts.add(targetContact);
                    Utilities.add(serialization, contacts.getSerialization());
                }
                break;
            default:
                return null;
        }
        return serialization;
    }

    @Override
    public String toString() {
        List<String> serialization = getSerialization();
        String result = "";
        for (int i = 0; i != serialization.size(); ++i) {
            result += serialization.get(i);
            if (i != serialization.size()-1) {
                result += "\n";
            }
        }
        return result;
    }
}
