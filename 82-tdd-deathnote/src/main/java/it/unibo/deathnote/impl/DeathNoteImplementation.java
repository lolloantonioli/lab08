package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImplementation implements DeathNote {

    private Map<String, Victim> names;
    private String lastName;
    private long time;

    private static final int CAUSE_TIME = 40;
    private static final int DETAILS_TIME = 6040;
    private static final String MSG_NUMBER = "Invalid number";
    private static final String MSG_CAUSE = "No names written or cause is null";
    private static final String MSG_DETAILS = "No names written or details are null";
    private static final String MSG_NAME = "The provided name is not written in this death note";

    public DeathNoteImplementation() {
        names = new HashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException(MSG_NUMBER);
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        names.put(name, new Victim());
        lastName = name;
        time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (names.size() == 0 || cause == null) {
            throw new IllegalStateException(MSG_CAUSE);
        }

        if (System.currentTimeMillis() - time <= CAUSE_TIME) {
            names.get(lastName).setDeathCause(cause);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if (names.size() == 0 || details == null) {
            throw new IllegalStateException(MSG_DETAILS);
        }

        if (System.currentTimeMillis() - time <= DETAILS_TIME) {
            names.get(lastName).setDeathDeatils(details);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        if (!names.containsKey(name)) {
            throw new IllegalArgumentException(MSG_NAME);
        } 

        return names.get(name).getCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!names.containsKey(name)) {
            throw new IllegalArgumentException(MSG_NAME);
        } 

        return names.get(name).getDetails();
    }

    @Override
    public boolean isNameWritten(final String name) {
        return names.containsKey(name);
    }

    private static class Victim {

        private String deathCause = "";
        private String deathDetails = "";

        public String getCause() {
            return deathCause;
        }

        public String getDetails() {
            return deathDetails;
        }

        public void setDeathCause(final String deathCause) {
            this.deathCause = deathCause;
        }

        public void setDeathDeatils(final String deathDetails) {
            this.deathDetails = deathDetails;
        }
    
    }

}
