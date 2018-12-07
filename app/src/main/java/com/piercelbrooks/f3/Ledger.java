
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import android.util.Log;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;
import com.piercelbrooks.common.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ledger implements Persistable<LedgerMember> {
    public class ContactList extends SerialList<Contact> {
        public ContactList() {
            super();
        }

        @Override
        public SerialList<Contact> getNew() {
            return new ContactList();
        }

        @Override
        public Contact getSerial() {
            return new Contact();
        }

        @Override
        public String getIdentifier() {
            return LedgerMember.CONTACTS.name();
        }

        public List<String> getAddresses() {
            ArrayList<String> addresses = new ArrayList<>();
            for (int i = 0; i != size(); ++i) {
                addresses.add(get(i).getAddress());
            }
            Collections.sort(addresses);
            return addresses;
        }
    }

    private static final String TAG = "F3-Ledger";
    private static Ledger current = null;

    private String name;
    private Action targetAction;
    private Contact targetContact;
    private DateTime targetDateTime;
    private Event targetEvent;
    private ContactList contacts;

    public Ledger() {
        this("");
    }

    public Ledger(String name) {
        this.name = name;
        this.targetAction = new Action(this);
        this.targetContact = new Contact(this, "");
        this.targetDateTime = new DateTime();
        this.targetEvent = null;
        this.contacts = new ContactList();
    }

    public static String getPath() {
        return ((MainApplication)Governor.getInstance().getCitizen(Family.APPLICATION)).getDataPath();
    }

    public static void setCurrent(Ledger current) {
        if (Ledger.current != null) {
            Ledger.current.save(getPath()+"ledgers"+File.separator+Ledger.current.getName().trim()+".dat");
        }
        Ledger.current = current;
        Citizen municipality = Governor.getInstance().getCitizen(Family.MUNICIPALITY);
        if (municipality.getCitizenClass().isInstance(Accountant.class)) {
            ((Accountant)municipality).setLedger(current);
        }
        if (Ledger.current != null) {
            Ledger.current.load(getPath()+"ledgers"+File.separator+Ledger.current.getName().trim()+".dat");
        }
    }

    public static Ledger getCurrent() {
        return current;
    }

    public void setName(String name) {
        this.name = name.trim();
        Log.d(TAG, "New name: "+this.name);
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

    public ContactList getContacts() {
        return contacts;
    }

    public boolean setContacts(ContactList contacts) {
        if (contacts == null) {
            this.contacts = new ContactList();
            return false;
        }
        this.contacts = contacts;
        return true;
    }

    public boolean copy(Ledger other) {
        if (other == null) {
            return false;
        }
        setName(other.getName());
        setContacts(other.getContacts());
        Log.d(TAG, "Contacts: "+contacts.toString());
        return true;
    }

    @Override
    public boolean save(String path) {
        ArrayList<String> ledgers = new ArrayList<>();
        String[] split = path.split(File.separator);
        String name = null;
        if (split.length != 0) {
            split = split[split.length-1].split("\\.");
            if (split.length != 0) {
                name = split[0];
            }
        }
        if (name == null) {
            name = this.name;
        }
        name = name.trim();
        if (!name.equals(this.name)) {
            Log.w(TAG, "Saving with different name!");
        }
        if (Utilities.read(getPath()+"ledgers.dat", ledgers)) {
            String ledger;
            boolean check = true;
            for (int i = 0; i != ledgers.size(); ++i) {
                ledger = ledgers.get(i).trim();
                if (ledger.isEmpty()) {
                    ledgers.remove(i);
                    --i;
                    continue;
                }
                if (check) {
                    if (ledger.equals(name)) {
                        check = false;
                    }
                }
                ledgers.set(i, ledger);
            }
            if (check) {
                ledgers.add(name);
                Log.i(TAG, "New ledger ("+name+")!");
            }
        } else {
            ledgers.clear();
            ledgers.add(name);
            Log.i(TAG, "New ledger ("+name+")!");
        }
        if (Utilities.write(getPath()+"ledgers.dat", ledgers)) {
            return Utilities.write(path, getSerialization());
        } else {
            Log.e(TAG, "Could not update ledgers!");
        }
        return false;
    }

    @Override
    public boolean load(String path) {
        ArrayList<String> data = new ArrayList<>();
        if (Utilities.read(path, data)) {
            if (!copy((Ledger)getDeserialization(data))) {
                Utilities.delete(path);
                return false;
            }
            return true;
        }
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
        boolean check = true;
        Ledger deserialization = new Ledger();
        LedgerMember[] members = LedgerMember.values();
        LedgerMember member;
        String data;
        for (i = 0; i != source.size(); ++i) {
            data = source.get(i).trim();
            Log.d(TAG, "Data: "+data);
            if (check) {
                check = false;
                if (!data.equalsIgnoreCase(getIdentifier())) {
                    Log.e(TAG, "Incorrect identifier ("+data+")!");
                    return null;
                }
                source.remove(i);
                --i;
                continue;
            }
            for (int j = 0; j != members.length; ++j) {
                member = members[j];
                if (data.equalsIgnoreCase(member.name())) {
                    Log.d(TAG, "Member: "+member.toString()+" ("+data+")");
                    switch (member) {
                        case NAME:
                            if (i+1 != source.size()) {
                                deserialization.setName(source.get(i+1).trim());
                                ++i;
                                source.remove(i);
                                --i;
                                source.remove(i);
                                --i;
                            } else {
                                Log.e(TAG, "Malformed name!");
                                return null;
                            }
                            break;
                        case CONTACTS:
                            if (!deserialization.setContacts((ContactList)contacts.getDeserialization(source))) {
                                Log.e(TAG, "Malformed contacts!");
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
            if (source.isEmpty())
            {
                break;
            }
        }
        for (int j = 0; j != i; ++j) {
            if (source.isEmpty()) {
                break;
            }
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
        switch (member) {
            case NAME:
                serialization.add(getMemberIdentifier(member));
                serialization.add(name);
                break;
            case CONTACTS: {
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
