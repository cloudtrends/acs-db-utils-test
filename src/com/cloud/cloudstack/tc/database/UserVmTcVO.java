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

import com.cloud.utils.db.GenericDao;
import com.cloud.utils.db.StateMachine;
import com.cloud.vm.VirtualMachine.Event;
import com.cloud.vm.VirtualMachine.State;


@Entity
@Table(name="user_vm_tc")
public class UserVmTcVO implements UserVmTc{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "guest_user_id")
    private long guestUserId;
    
    @Column(name = "total_bandwidth")
    private long totalBandwidth;
    
    @Column(name = "tc_type")
    private long tcType;
    
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

    @Override
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGuestUserId() {
        return guestUserId;
    }

    public void setGuestUserId(long guestUserId) {
        this.guestUserId = guestUserId;
    }

    public long getTotalBandwidth() {
        return totalBandwidth;
    }

    public void setTotalBandwidth(long totalBandwidth) {
        this.totalBandwidth = totalBandwidth;
    }

    public long getTcType() {
        return tcType;
    }

    public void setTcType(long tcType) {
        this.tcType = tcType;
    }

    public Date getRemoved() {
        return removed;
    }

    public void setRemoved(Date removed) {
        this.removed = removed;
    }



    @Override
    public String getUuid() {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
}
