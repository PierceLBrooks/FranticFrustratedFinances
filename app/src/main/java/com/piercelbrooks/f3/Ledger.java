
// Author: Pierce Brooks

package com.piercelbrooks.f3;

import com.piercelbrooks.common.Citizen;
import com.piercelbrooks.common.Family;
import com.piercelbrooks.common.Governor;

import java.util.List;

public class Ledger implements Persistable<LedgerMember> {
    private static Ledger current = null;

    private String name;
    private Action targetAction;
    private Contact targetContact;
    private DateTime targetDateTime;
    private Event targetEvent;

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
    public Serial<LedgerMember> getDeserialization(List<String> source) {
        return null;
    }

    @Override
    public String getIdentifier() {
        return null;
    }

    @Override
    public List<String> getSerialization() {
        return null;
    }

    @Override
    public String getMemberIdentifier(LedgerMember member) {
        return null;
    }

    @Override
    public List<String> getMemberSerialization(LedgerMember member) {
        return null;
    }
}
