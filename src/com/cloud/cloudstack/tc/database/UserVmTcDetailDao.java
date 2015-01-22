package com.cloud.cloudstack.tc.database;

import com.cloud.utils.db.GenericDao;
import com.cloud.utils.fsm.StateDao;
import com.domolo.cloudstack.db.Host;
import com.domolo.cloudstack.db.HostVO;
import com.domolo.cloudstack.db.Status;


public interface UserVmTcDetailDao extends GenericDao<UserVmTcDetailVO, Long>, StateDao<Status, Status.Event, UserVmTcDetail> {

}
