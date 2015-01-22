package com.domolo.cloudstack.db;




/**
 * ControlledEntity defines an object for which the access from an
 * access must inherit this interface.
 *
 */
public interface ControlledEntity extends OwnedBy, PartOf {
    public enum ACLType {
        Account,
        Domain
    }

}
