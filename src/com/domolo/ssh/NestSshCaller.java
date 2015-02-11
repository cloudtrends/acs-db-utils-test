package com.domolo.ssh;

import java.io.IOException;
import java.util.logging.Level;

import com.cloud.utils.Pair;
import com.cloud.utils.ssh.SSHCmdHelper;
import com.cloud.utils.ssh.SshHelper;
import com.trilead.ssh2.DebugLogger;
import com.trilead.ssh2.SCPClient;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.log.Logger;

public class NestSshCaller {

    public static void main(String[] argv){
        NestSshCaller nsc = new NestSshCaller();
        nsc.run();
    }
    
    private void initHostVrEnv() throws JSchException, IOException {
        JSch jsch = new JSch();

        String user = "root";
        int port = 3922;
        String privateKey = "/root/.ssh/id_rsa.cloud";

        jsch.addIdentity(privateKey);

        com.jcraft.jsch.Session session = jsch.getSession(user, _profile.vrIp, port);



        //JSch jSch = new JSch();
        //com.jcraft.jsch.Session session = jSch.getSession("new", "sdf.org");

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        Channel channel = session.openChannel("shell");

        Expect expect = new ExpectBuilder()
                .withOutput(channel.getOutputStream())
                .withInputs(channel.getInputStream(), channel.getExtInputStream())
                .withEchoOutput(System.out)
                .withEchoInput(System.err)
                        //        .withInputFilters(removeColors(), removeNonPrintable())
                .withExceptionOnFailure()
                .build();
        // try-with-resources is omitted for simplicity
        channel.connect();
        expect.expect(contains("(yes/no)"));
        expect.sendLine("yes");

        /**
        String ipAddress = expect.expect(regexp("Trying (.*)\\.\\.\\.")).group(1);
        System.out.println("Captured IP: " + ipAddress);
        expect.expect(contains("login:"));
        expect.sendLine("new");
        expect.expect(contains("(Y/N)"));
        expect.send("N");
        expect.expect(regexp(": $"));

        */
        // finally is omitted
        channel.disconnect();
        session.disconnect();
        expect.close();

    }


    
    
    private static class TrileadDebug implements DebugLogger {
        public void log(int level, String className, String message) {
            System.out.println(message);
        }
    }
    
    public void run(){
        SSHCmdHelper ssh_helper = new SSHCmdHelper();
        String ip = "172.27.0.15";
        int port = 22;
        String username  ="root";
        String password = "password";
        
        String vr_ip = "169.254.2.175";
        
        
        String command = "ssh -T -i /root/.ssh/id_rsa.cloud " + vr_ip + " -p 3922 <<EOA \n ls \n EOA\n";
        Pair<Boolean, String> p=null;
        try {
            p = SshHelper.sshExecute(ip, port, username, null, password, command);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Boolean bb = p.first();
        String ss = p.second();
        System.out.println(ss);
        if(true){
            return;
        }
        com.trilead.ssh2.Connection conn = SSHCmdHelper.acquireAuthorizedConnection(ip, port, username, password);
        //DebugLogger logger = new com.trilead.ssh2.();
        TrileadDebug logger = new TrileadDebug();
        boolean enable = true;
        conn.enableDebugging(enable, logger);
        try {
            Session sess = conn.openSession();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        conn.close();
        
        
    }
}
