package com.domolo.cloudstack.db;

/**
 */
public interface OwnedBy {
    /**
     * @return account id that owns this object.
     */
    long getAccountId();
}
