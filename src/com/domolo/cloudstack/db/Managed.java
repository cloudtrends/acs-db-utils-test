package com.domolo.cloudstack.db;


public interface Managed {
    public enum ManagedState {
        Managed,
        PrepareUnmanaged,
        Unmanaged,
        PrepareUnmanagedError
    };

}
