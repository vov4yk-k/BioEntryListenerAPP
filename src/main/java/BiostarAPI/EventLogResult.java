package BiostarAPI;

import java.util.Date;

/**
 * Created by Користувач on 11.01.2017.
 */
public class EventLogResult {
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
}
