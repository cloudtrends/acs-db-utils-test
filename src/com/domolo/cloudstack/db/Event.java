package com.domolo.cloudstack.db;


import java.util.Date;



public interface Event extends ControlledEntity, Identity, InternalIdentity {
    public enum State {
        Created,
        Scheduled,
        Started,
        Completed;
    }

    String getType();
    State getState();

    String getDescription();
    Date getCreateDate();
    long getUserId();
    int getTotalSize();
    String getLevel();
    long getStartId();
    String getParameters();
    boolean getArchived();
}
