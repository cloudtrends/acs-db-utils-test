package com.domolo.xenserver.resource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import javax.naming.ConfigurationException;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcException;

import com.cloud.hypervisor.xenserver.resource.CitrixResourceBase;
import com.cloud.hypervisor.xenserver.resource.XenServerConnectionPool;
import com.cloud.vm.DomainRouterVO;
import com.xensource.xenapi.Connection;
import com.xensource.xenapi.Host;
import com.xensource.xenapi.PIF;
import com.xensource.xenapi.Pool;
import com.xensource.xenapi.Session;
import com.xensource.xenapi.Types.XenAPIException;
import com.xensource.xenapi.VIF;
import com.xensource.xenapi.VM;

/**
 * vm_instance table instance_name filed is the label name of vm.
 * @author Administrator
 *
 */
public class VifResource extends CitrixResourceBase{
    
    public static void main(String[] argv){
        String host_ip = "172.27.0.15";
        String username = "root";
        String password = "password";
        
        VifResource vr = new VifResource(host_ip, username, password);
        try {
            int bandwidth = 100;
            Map<String,String> hostInfo = new HashMap<String,String>();
            hostInfo.put("vm_name_label", "i-2-6-VM");
            hostInfo.put("mac_addr", "02:00:48:38:00:01");
            vr.setBandwidth(bandwidth, hostInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static final Logger s_logger = Logger.getLogger(VifResource.class);
    protected XenServerConnectionPool _connPool;
    String hostIp = "";
    String username = ""; 
    Queue<String> pass = null;
    public VifResource(String hostIp,String username,String password){
        _connPool = XenServerConnectionPool. getInstance();
        this.hostIp = hostIp;
        this.username = username;
        pass= new LinkedList<String>();
        pass.add(password);
    }
    
    
    public String getVRResidentIp(DomainRouterVO vrVo){
        Connection conn = null;
        String hypervisorIp = "";
        String vm_label = vrVo.getInstanceName();
        VM vm = null;
        conn = _connPool.getConnect(hostIp, username, pass);
        if(null == conn){
            return hypervisorIp;
        }
        Set<VM> set_vm;
        try {
            set_vm = VM.getByNameLabel(conn, vm_label);
            Iterator<VM> it_vm = set_vm.iterator();
            while(it_vm.hasNext()){
                VM vm_tmp = it_vm.next();
                String tmp_label = vm_tmp.getNameLabel(conn); 
                if(vm_label.equals(tmp_label)){
                    vm = vm_tmp;
                    break;
                }
            }
            Host host = vm.getResidentOn(conn);
            hypervisorIp = host.getAddress(conn);
            s_logger.info("vm resident on hypervisor :"+hypervisorIp);
        } catch (XenAPIException | XmlRpcException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        conn.dispose();
        return hypervisorIp;
    }
    
    public void setBandwidth(int bandwidth, Map<String, String> hostInfo) throws Exception{
        Connection conn = null;
        String mac_addr = hostInfo.get("mac_addr");
        String vm_label = hostInfo.get("vm_name_label");
        
        conn = _connPool.getConnect(hostIp, username, pass);
        if( conn == null ) {
            throw new Exception("ezCloud: Can not create connection to " + hostIp);
        }
        VM vm = null;
        Set<VM> set_vm = VM.getByNameLabel(conn, vm_label);
        Iterator<VM> it_vm = set_vm.iterator();
        while(it_vm.hasNext()){
            VM vm_tmp = it_vm.next();
            String tmp_label = vm_tmp.getNameLabel(conn); 
            if(vm_label.equals(tmp_label)){
                vm = vm_tmp;
                break;
            }
        }
        if(null == vm ){
            return;
        }
        
        Set<VIF> set_vif = vm.getVIFs(conn);
        Iterator<VIF> it_vif= set_vif.iterator();
        VIF vif = null;
        while(it_vif.hasNext()){
            VIF vif_tmp = it_vif.next();
            String vm_mac_addr = vif.getMAC(conn);
            if( mac_addr.equals(vm_mac_addr) ){
                vif = vif_tmp;
                break;
            }
        }
        if(null == vif){
            return;
        }
        if("ratelimit".equals(vif.getQosAlgorithmType(conn))){
            s_logger.info("ezCloud: find the mac addr="+mac_addr);
            Map<String,String> mss_paras = new HashMap<String,String>();
            mss_paras.put("kbps", String.valueOf(bandwidth * 1024 / 8));
            vif.setQosAlgorithmParams(conn, mss_paras);
        }
        s_logger.info("ezCloud done for QoS to vm:" + vm_label);
        conn.dispose();
    }
    /**
     *  host uuid , username password
     * @throws Exception 
     *  
     *  
     * 
     */
    public void getVif() throws Exception{
        //Connection conn = getConnection();
        Connection conn = null;
        //Connection conn = ConnPool.getConnect(_host.ip, _username, _password);
        String hostIp = "172.27.0.15";
        String username = "root";
        Queue<String> pass= new LinkedList<String>();
        pass.add("password");
        conn = _connPool.getConnect(hostIp, username, pass);
        if( conn == null ) {
            throw new Exception("Can not create connection to " + _host.ip);
        }
        try {
            Host.Record hostRec = null;
            try {
                Set<Host> list_hosts = Host.getAll(conn);
                Iterator<Host> it_host = list_hosts.iterator();
                while(it_host.hasNext()){
                    Host host = it_host.next();
                    s_logger.info("host:"+host.getAddress(conn));
                    s_logger.info("host:"+host.getHostname(conn));
                    s_logger.info("host:"+host.getUuid(conn));
                    PIF pif = host.getManagementIface(conn);
                    s_logger.info("host:"+pif.getDevice(conn));
                    
                    Set<VM> set_vm = VM.getAll(conn);
                    Iterator<VM> it_vm = set_vm.iterator();
                    while(it_vm.hasNext()){
                        VM vm = it_vm.next();
                        String uuid = vm.getUuid(conn);
                        s_logger.info("vm name label:"+vm.getNameLabel(conn) + " uuid=" + uuid );
                        //s_logger.info("vm:"+vm.get);
                        VIF.Record vifr = new VIF.Record();
                        Set<VIF> set_vif = vm.getVIFs(conn);
                        Iterator<VIF> it_vif= set_vif.iterator();
                        while(it_vif.hasNext()){
                            VIF vif = it_vif.next();
                            s_logger.info("vif:"+vif.getMAC(conn));
                            Map<String,String> mss = vif.getQosAlgorithmParams(conn);
                            Iterator<String> is = mss.keySet().iterator();
                            while(is.hasNext()){
                                String s = is.next();
                                s_logger.info("qos:"+s + "  : " + mss.get(s));
                            }
                            //vif.setQosAlgorithmParams(c, algorithmParams);
                        }
                    }
                    
                }
                if(false){
                    Host host = Host.getByUuid(conn, _host.uuid);
                    hostRec = host.getRecord(conn);
                    Pool.Record poolRec = Pool.getAllRecords(conn).values().iterator().next();
                    _host.pool = poolRec.uuid;    
                }
            } catch (Exception e) {
                throw new ConfigurationException("Can not get host information from " + _host.ip);
            }
            if(false){
                if (!hostRec.address.equals(_host.ip)) {
                    String msg = "Host " + _host.ip + " seems be reinstalled, please remove this host and readd";
                    s_logger.error(msg);
                    throw new ConfigurationException(msg);
                }    
            }
            
        } finally {
            try {
                Session.logout(conn);
            } catch (Exception e) {
            }
        }



    }

}
