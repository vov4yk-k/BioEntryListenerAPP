import BiostarAPI.*;
import Sniff.SnifferThread;
import UT.UTConnection;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Main {

    public static void main(String[] args) {
        //testSniffer();
        //testBiostar();
        //testBioSniffer();
        //testVerifyFingerprint();
        testUT();
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

        EventQuery eventQuery = new EventQuery();
        eventQuery.setDevice_id("539332191");


        EventLogSearchResultWithoutTotal log = biostar.searchLog(eventQuery,false);
        log.showLog();
        boolean next = true;
        while(next){

            Calendar date = Calendar.getInstance();
            Date startDate = new java.util.Date(date.getTimeInMillis() - 3000);
            Date curDate = new java.util.Date(date.getTimeInMillis());
            String[] dateArray ={Biostar.getISO8601StringForDate(startDate),Biostar.getISO8601StringForDate(curDate)};
            eventQuery.setDatetime(dateArray);

            System.out.println(startDate+ " - "+curDate);

            EventLogSearchResultWithoutTotal nextLog = biostar.searchLog(eventQuery,false);
            nextLog.showLog();
            //next = nextLog.isNext();
        }
        System.out.println("end");

    }

    public static void testVerifyFingerprint(){
        Biostar biostar = new Biostar("complex","HR","12345qwerty");
        ArrayList<ListUserItem> userList = biostar.getUserList();
        biostar.setDeviceId("539332191");
        for (ListUserItem user: userList) {

            System.out.println(user.getName());

            FingerprintTemplate[] fingerprintTemplate = biostar.getUsersFingerprint(user.getUser_id());

            if(fingerprintTemplate == null) continue;

            FingerprintTemplate fingerprint = fingerprintTemplate[0];
            VerifyFingerprintOption fingerprintOption = new VerifyFingerprintOption(fingerprint.getTemplate0(),fingerprint.getTemplate1());
            VerifyFingerprintResult verifyFingerprintResponse = biostar.verifyFingerprint(fingerprintOption);
            System.out.println(verifyFingerprintResponse.toString());

        }

    }

    public static void testBioSniffer(){
        Biostar biostar = new Biostar("complex","HR","12345qwerty");
        biostar.setDeviceId("539332191");

        SnifferThread snifferThread = new SnifferThread(0,true,biostar);
        snifferThread.start();
    }

    public static void testUT() {

        Biostar biostar = new Biostar("complex","HR","12345qwerty");

        EventQuery eventQuery = new EventQuery();
        eventQuery.setDevice_id("539332191");
        Calendar date = Calendar.getInstance();
        Date startDate = new java.util.Date(date.getTimeInMillis() - 30000000);
        Date curDate = new java.util.Date(date.getTimeInMillis());
        String[] dateArray ={Biostar.getISO8601StringForDate(startDate),Biostar.getISO8601StringForDate(curDate)};
        eventQuery.setDatetime(dateArray);
        EventLogSearchResultWithoutTotal nextLog = biostar.searchLog(eventQuery,false);

        UTConnection utConnection = new UTConnection();
        utConnection.setData(nextLog);
    }
}
