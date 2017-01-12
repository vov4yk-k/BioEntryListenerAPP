package Sniff;

import BiostarAPI.Biostar;
import BiostarAPI.EventQuery;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Sniffer implements PacketReceiver {

    private DefaultListModel ljep;
    private boolean displayhex;
    private int pknum;
    InetAddress sourceIP;
    Biostar biostar;

    /* Constructor */
    public Sniffer(DefaultListModel jep, boolean hex) {
        ljep = jep;
        displayhex = hex;
        pknum = 0;
    }

    public Sniffer(boolean hex, InetAddress IP) {
        displayhex = hex;
        pknum = 0;
        sourceIP = IP;
    }

    public Sniffer(boolean hex, InetAddress sourceIP, Biostar biostar) {
        this(hex, sourceIP);
        this.biostar =  biostar;
    }

    /* Returns a hex string representation of a byte value.*/
    String byteToHex(byte b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] array = {hexDigit[(b >> 4) & 0x0f], hexDigit[b & 0x0f]};
        return new String(array);
    }

    /* Converts a byte array into a string.*/
    String bytesToString(byte[] array) {
        int x = 0;
        StringBuffer str = new StringBuffer();
        if (displayhex == true) {
            for (int k = 0; k < array.length; k++) {
                if (x < 9) {
                    str.append(" 0x" + byteToHex(array[k]));
                    ++x;
                } else {
                    x = 0;
                    str.append(" 0x" + byteToHex(array[k]) + "\r\n");
                }
            }
            return str.toString();
        } else {
            return new String(array);
        }
    }

    /* Function that is passed to packets by pcap.*/
    public void receivePacket(Packet packet) {
        if (packet.data.length > 0)//&& packet.dst_port == 59181)
        {
            try {
                parsePackets(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void parsePackets(Packet p) throws IOException {
        //if (p == null || p == Packet.EOF)
        //    return;

        //if (!(p instanceof TCPPacket))
        //    continue;

        TCPPacket tcpPacket = (TCPPacket) p;

        InetAddress addr = tcpPacket.src_ip;

        if(addr.equals(sourceIP)) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Date date = new java.util.Date();
            System.out.println(tcpPacket.src_ip + ": " + tcpPacket.src_port + " --> " + tcpPacket.dst_ip + ": " + tcpPacket.dst_port + "    " + dateFormat.format(date));
        }

        if (biostar!=null){

        }
    }

    private void showLogBiostar(){

    }
}
