package com.cloud.cloudstack.tc.database;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cloud.host.Status;
import com.cloud.utils.db.GenericDao;
import com.cloud.utils.db.StateMachine;
import com.cloud.vm.VirtualMachine.Event;
import com.cloud.vm.VirtualMachine.State;


@Entity
@Table(name="user_vm_tc_detail")
public class UserVmTcDetailVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "vm_id")
    private long vmId;
    
    @Column(name = "vm_tc_id")
    private long vmTcId; // while connected with tc child class id , should less than 1000
    
    @Column(name = "guest_user_id")
    private long guestUserId;
    
    @Column(name = "vm_bandwidth")
    private long vmBandwidth;
    
    @Column(name=GenericDao.REMOVED_COLUMN)
    protected Date removed;
    
    /**
     * Note that state is intentionally missing the setter.  Any updates to
     * the state machine needs to go through the DAO object because someone
     * else could be updating it as well.
     */
    @Enumerated(value=EnumType.STRING)
    @StateMachine(state=State.class, event=Event.class)
    @Column(name="state", updatable=true, nullable=false, length=32)
    protected State state = null;
    
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVmId() {
        return vmId;
    }

    public void setVmId(long vmId) {
        this.vmId = vmId;
    }

    public long getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(long guestUserId) {
        this.guestUserId = guestUserId;
    }

    public long getVmBandwidth() {
        return vmBandwidth;
    }

    public void setVmBandwidth(long vmBandwidth) {
        this.vmBandwidth = vmBandwidth;
    }

    public Date getRemoved() {
        return removed;
    }

    public void setRemoved(Date removed) {
        this.removed = removed;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    
    
    
    
}
