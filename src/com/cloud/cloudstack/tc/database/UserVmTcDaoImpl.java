package com.cloud.cloudstack.tc.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.inject.Named;
import javax.persistence.TableGenerator;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.cloud.utils.db.DB;
import com.cloud.utils.db.GenericDaoBase;
import com.cloud.utils.db.TransactionLegacy;
import com.cloud.utils.exception.CloudRuntimeException;
import com.cloud.vm.VirtualMachine.State;
import com.domolo.cloudstack.db.Host;
import com.domolo.cloudstack.db.Host.Type;
import com.domolo.cloudstack.db.HostDao;
import com.domolo.cloudstack.db.HostVO;
import com.domolo.cloudstack.db.ResourceState;
import com.domolo.cloudstack.db.RunningHostCountInfo;
import com.domolo.cloudstack.db.Status;
import com.domolo.cloudstack.db.Status.Event;



//@Service
@Named
@Repository
@Component
@Local(value = {UserVmTcDao.class})
@DB
@TableGenerator(name = "host_req_sq", table = "user_vm_tc", pkColumnName = "id", valueColumnName = "sequence", allocationSize = 1)
public class UserVmTcDaoImpl extends GenericDaoBase<UserVmTcVO, Long> implements UserVmTcDao {

    @Override
    public boolean updateState(Status currentState, Event event,
            Status nextState, UserVmTc vo, Object data) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    @DB
    public UserVmTcVO persist(UserVmTcVO host) {
        //FIXME may be database is not cloud but ez  cloud.
        final String InsertSequenceSql = "  INSERT INTO `cloud`.`user_vm_tc` (`guest_user_id`, `total_bandwidth`, `tc_type`, `state`) VALUES (?, ?, ?, ?);";
        TransactionLegacy txn = TransactionLegacy.currentTxn();
        txn.start();
        UserVmTcVO dbHost = super.persist(host);
        txn.commit();
        return dbHost;
        /**
        try {
            PreparedStatement pstmt = txn.prepareAutoCloseStatement(InsertSequenceSql);
            //pstmt.setLong(1, dbHost.getId());
            pstmt.setLong(1, host.getGuestUserId());
            pstmt.setLong(2, host.getTotalBandwidth());
            pstmt.setLong(3, host.getTcType());
            pstmt.setString(4, host.getState().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new CloudRuntimeException("Unable to persist the sequence number for this host");
        }
        */
        //return dbHost;
    }
    
}
