package com.domolo.cloudstack.db;

import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;










import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;





import com.cloud.utils.db.DB;
import com.cloud.utils.db.QueryBuilder;
import com.cloud.utils.db.TransactionLegacy;



/**
 * http://projects.spring.io/spring-framework/
 * http://techidiocy.com/annotation-config-vs-component-scan-spring-core/#comment-1144
 * @author Administrator
 *
 */

//@ComponentScan(basePackageClasses = {HostDaoImpl.class},useDefaultFilters = false)
@Configuration
public class ConnectionTest {

    public static void main(String[] argv){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");        
        ConnectionTest ct = new ConnectionTest();
        ct.run();
    }
    @DB
    public void run(){
        TransactionLegacy txn = TransactionLegacy.open("ClusterHeartbeat");
        PreparedStatement pstmt = null;
        try {
            QueryBuilder<HostVO> sc = QueryBuilder.create(HostVO.class);
            
            List<HostVO> list_hosts = sc.list();
            Iterator<HostVO> it_hosts = list_hosts.iterator();
            while(it_hosts.hasNext()){
                HostVO hvo = it_hosts.next();
                System.out.println(hvo.getName());
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
