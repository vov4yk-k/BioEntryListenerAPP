package BiostarAPI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Date;

/**
 * Created by Користувач on 11.01.2017.
 */
class EventLogResult {
    Date datetime;
    SimpleDevice device;
    SimpleDoor door;
    EventType event_type;
    long id;
    long index;
    String level;
    Date server_datetime;
    String type;
    SimpleUser user;
    SimpleUserGroup user_group;

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(datetime);
        stringBuilder.append(" ");
        stringBuilder.append(device);
        stringBuilder.append(" ");
        stringBuilder.append(door);
        stringBuilder.append(" ");
        stringBuilder.append(event_type);
        stringBuilder.append(" ");
        stringBuilder.append(id);
        stringBuilder.append(" ");
        stringBuilder.append(index);
        stringBuilder.append(" ");
        stringBuilder.append(server_datetime);
        stringBuilder.append(" ");
        stringBuilder.append(type);
        stringBuilder.append(" ");
        stringBuilder.append(user_group);
        return stringBuilder.toString();
    }
}
