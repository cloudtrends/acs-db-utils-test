package com.domolo.cloudstack.db;


import com.cloud.utils.SerialVersionUID;

/**
 * Thrown by the state machine when there is no transition from one state
 * to another.
 *
 */
public class NoTransitionException extends Exception {
    
    private static final long serialVersionUID = SerialVersionUID.NoTransitionException;
    
    public NoTransitionException(String msg) {
        super(msg);
    }
}
