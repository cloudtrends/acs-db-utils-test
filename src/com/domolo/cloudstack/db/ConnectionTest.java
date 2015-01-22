package com.domolo.cloudstack.db;

import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.cloud.cloudstack.tc.database.UserVmTcDao;
import com.cloud.cloudstack.tc.database.UserVmTcDaoImpl;
import com.cloud.cloudstack.tc.database.UserVmTcDetailDaoImpl;
import com.cloud.cloudstack.tc.database.UserVmTcDetailVO;
import com.cloud.cloudstack.tc.database.UserVmTcVO;
import com.cloud.utils.db.DB;
import com.cloud.utils.db.GenericQueryBuilder;
import com.cloud.utils.db.QueryBuilder;
import com.cloud.utils.db.TransactionLegacy;
import com.cloud.utils.db.SearchCriteria.Op;
import com.cloud.vm.VirtualMachine.State;



/**
 * GenericQueryBuilder : runtime
 * GenericSearchBuilder : load time
 * 
 * http://projects.spring.io/spring-framework/
 * http://techidiocy.com/annotation-config-vs-component-scan-spring-core/#comment-1144
 * @author Administrator
 *
 */

@Named
@Service
//@ComponentScan(basePackageClasses = {HostDaoImpl.class,UserVmTcDaoImpl.class},useDefaultFilters = false)
@Configuration
public class ConnectionTest {
    private static final Logger s_logger = Logger.getLogger(ConnectionTest.class);
    
    //@Inject
    @Autowired
    UserVmTcDaoImpl _userVmTcDao;
    
    @Autowired
    UserVmTcDetailDaoImpl _userVmTcDetailDao;
    
    
    public static void main(String[] argv){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ConnectionTest cust = (ConnectionTest)context.getBean("connectionTest");
        cust.run();
    }
    @DB
    public void run(){
        TransactionLegacy txn = TransactionLegacy.open("ClusterHeartbeat");
        PreparedStatement pstmt = null;
        try {
            if(true){
                UserVmTcDetailVO tcDetail = new UserVmTcDetailVO();
                tcDetail.setGuestUserId(5);
                tcDetail.setState(State.Running);
                tcDetail.setVmBandwidth(3);
                tcDetail.setVmId(10);
                _userVmTcDetailDao.persist(tcDetail);
            }
            if(true){
                UserVmTcVO entity = new UserVmTcVO();
                entity.setGuestUserId(10);
                State state = State.Running;
                entity.setState(state);
                entity.setTcType(10);
                entity.setTotalBandwidth(100);
                _userVmTcDao.persist(entity);
                QueryBuilder<UserVmTcVO> sc = QueryBuilder.create(UserVmTcVO.class);
                List<UserVmTcVO> list_hosts = null;//
                list_hosts = sc.list();
                Iterator<UserVmTcVO> it_hosts = list_hosts.iterator();
                UserVmTcVO hvo = null;
                while(it_hosts.hasNext()){
                    hvo = it_hosts.next();
                    hvo.setTotalBandwidth(99);
                    _userVmTcDao.update(hvo.getId(), hvo);
                    //System.out.println(hvo.getName());
                    s_logger.info("name="+hvo.getTotalBandwidth()+"  ="+hvo.getGuestUserId() +" "+hvo.getId());
                }
            }
            if(true){
                QueryBuilder<HostVO> sc = QueryBuilder.create(HostVO.class);
                s_logger.info(sc.entity().getId());
                //sc.join(name, builder, joinField1, joinField2, joinType)
                
                sc.and(sc.entity().getId(), Op.EQ, 4 );
                
                List<HostVO> list_hosts = null;//
                list_hosts = sc.list();
                
                HostVO hvo = sc.find();
                s_logger.info("name="+hvo.getName()+"  ="+hvo.getGuid() +" "+hvo.getId()+ " "+hvo.getResourceState());
                
                Iterator<HostVO> it_hosts = list_hosts.iterator();
                while(it_hosts.hasNext()){
                    hvo = it_hosts.next();
                    //System.out.println(hvo.getName());
                    s_logger.info("name="+hvo.getName()+"  ="+hvo.getGuid() +" "+hvo.getId());
                }
                
            }
            if (false){
                //GenericQueryBuilder<HostVO, Long> sc = GenericQueryBuilder.create(HostVO.class, Long.class);
                //HostVO entity = CountSearch.entity();
                //sc.select(null, FUNC.COUNT, null, null).where(entity.getType(), Op.EQ, Host.Type.Routing);
                //sc.and(entity.getCreated(), Op.LT, new Date());
                //Long count = sc.find();    
            }
            
            
            
            //txn.start();
            //pstmt = txn.prepareAutoCloseStatement("insert into cloud.test(fld_int, fld_long, fld_string) values(?, ?, ?)");
            //pstmt.setInt(1, fldInt);
            //pstmt.setLong(2, fldLong);
            //pstmt.setString(3, fldString);

            //pstmt.executeUpdate();
            //txn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
