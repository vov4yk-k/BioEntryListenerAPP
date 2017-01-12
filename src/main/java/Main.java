import BiostarAPI.*;
import Sniff.SnifferThread;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Main {

    public static void main(String[] args) {
        //testSniffer();
        testBiostar();
    }

    public static void testSniffer(){
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        InetAddress ip = null;
        try {
            ip = InetAddress.getByName("192.168.88.124");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        SnifferThread snifferThread = new SnifferThread(0,true,ip);
        snifferThread.start();
    }



    public static void testBiostar(){
        Biostar biostar = new Biostar("complex","HR","12345qwerty");
        GetDevice device = biostar.getDeviceByID("539332191");
        System.out.println(device.getPort());
        System.out.println(device.getIP());
    }
}
