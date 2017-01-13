package BiostarAPI;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Користувач on 12.01.2017.
 */
public class GetDevice {
    private DeviceChildrenItem[] children;
    private SimpleDeviceGroup device_group;
    private DeviceType device_type;
    private long id;
    private DeviceLanInfo lan;
    private String mode;
    private String name;
    private String status;
    private SimpleDoor[] used_by_doors;
    private DeviceWlanInfo wlan;

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
