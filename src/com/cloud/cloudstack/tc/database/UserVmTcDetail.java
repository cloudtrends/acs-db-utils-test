package com.cloud.cloudstack.tc.database;

import com.cloud.utils.fsm.StateObject;
import com.cloud.vm.VirtualMachine.State;
import com.domolo.cloudstack.db.Identity;
import com.domolo.cloudstack.db.InternalIdentity;

public interface UserVmTcDetail extends StateObject<State>, Identity, InternalIdentity {
}
