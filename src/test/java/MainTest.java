import BiostarAPI.*;
import Sniff.SnifferThread;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Користувач on 13.01.2017.
 */
public class MainTest {
    @Test
    public void testSniffer() throws Exception {
        //assertEquals();
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

    @Test
    public void testBiostar() throws Exception {
        Biostar biostar = new Biostar("complex","HR","12345qwerty");

        GetDevice device = biostar.getDeviceByID("539332191");
        System.out.println(device.getPort());
        System.out.println(device.getIP());

        EventQuery eventQuery = new EventQuery();
        eventQuery.setDevice_id("539332191");


        //EventLogSearchResultWithoutTotal log = biostar.searchLog(eventQuery,false);
        //log.showLog();
        //boolean next = true;
        //while(next){

            Calendar date = Calendar.getInstance();
            Date startDate = new java.util.Date(date.getTimeInMillis() - 90000000);
            Date curDate = new java.util.Date(date.getTimeInMillis());
            String[] dateArray ={Biostar.getISO8601StringForDate(startDate),Biostar.getISO8601StringForDate(curDate)};
            eventQuery.setDatetime(dateArray);

            System.out.println(startDate+ " - "+curDate);

            EventLogSearchResultWithoutTotal nextLog = biostar.searchLog(eventQuery,false);
            //nextLog.showLog();
            EventLogResult[] records = nextLog.getRecords();
            Arrays.sort(records);
            //next = nextLog.isNext();
        //}
        assertEquals(1,1);
    }

    @Test
    public void testBioSniffer() throws Exception {
        Biostar biostar = new Biostar("complex","HR","12345qwerty");
        biostar.setDeviceId("539332191");

        SnifferThread snifferThread = new SnifferThread(0,true,biostar);
        snifferThread.start();

    }

}