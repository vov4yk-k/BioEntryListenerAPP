import Sniff.SnifferThread;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Main {
    public static void main(String[] args) {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();

        SnifferThread snifferThread = new SnifferThread(0,true);
        snifferThread.start();

    }
}
