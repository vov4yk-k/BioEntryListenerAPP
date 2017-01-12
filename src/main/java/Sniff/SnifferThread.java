package Sniff;

import BiostarAPI.Biostar;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;
import java.net.InetAddress;

/**
 * Created by Користувач on 11.01.2017.
 */
public class SnifferThread extends Thread {
    private int ldevice;
    private DefaultListModel ljep;
    private boolean type;
    private NetworkInterface[] devices;
    private JpcapCaptor jpcap;
    private InetAddress srcIP;
    private Biostar biostar;

    /* Constructor*/
    public SnifferThread (int device, DefaultListModel jep, boolean type)
    {
        ldevice = device;
        ljep = jep;
        this.type = type;
    }

    public SnifferThread (int device, boolean type, InetAddress srcIP)
    {
        ldevice = device;
        this.type = type;
        this.srcIP = srcIP;
    }

    public SnifferThread (int device, boolean type, Biostar biostar)
    {
        this.ldevice = device;
        this.type = type;
        this.srcIP = biostar.getDevice().getIP();
        this.biostar = biostar;
    }

    /*
     * Kills the infinite loop that the pcap library goes into while sniffing packets.
     * I declare this method syncronized to force java to treat it as a critical section
     * (forces only one thread at a time to be able to access it).
     */
    public synchronized void stopthread()
    {
        if(jpcap!=null)
        {
            jpcap.breakLoop();
        }
    }

    /* The threads run method (where all the action happens).*/
    public void run()
    {
        try
        {
            devices = JpcapCaptor.getDeviceList();
            jpcap = JpcapCaptor.openDevice(devices[ldevice], 2000, false, 20);
            //jpcap.loopPacket(-1,new Sniffer(ljep,type));
            jpcap.loopPacket(-1,new Sniffer(type, srcIP, biostar));
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
