package BiostarAPI;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Користувач on 12.01.2017.
 */
public class GetDevice {
    DeviceChildrenItem[] children;
    SimpleDeviceGroup device_group;
    DeviceType device_type;
    long id;
    DeviceLanInfo lan;
    String mode;
    String name;
    String status;
    SimpleDoor[] used_by_doors;
    DeviceWlanInfo wlan;

    public String getPort(){
        return lan.device_port;
    }

    public InetAddress getIP(){
        InetAddress ip = null;
        try {
            ip = InetAddress.getByName(lan.ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
       return ip;
    }
}
