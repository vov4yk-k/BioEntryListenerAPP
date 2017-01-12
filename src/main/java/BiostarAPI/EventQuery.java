package BiostarAPI;

/**
 * Created by Користувач on 11.01.2017.
 */
public class EventQuery {
    String[] datetime;
    long[] device_id;
    String[] event_type_code;
    long limit;
    long offset;
    String[] user_id;

    public void setDatetime(String[] datetime) {
        this.datetime = datetime;
    }

    public void setDevice_id(long[] device_id) {
        this.device_id = device_id;
    }

    public void setDevice_id(String device_id) {
        long id = Long.parseLong(device_id);
        long[] devices = {id};
        this.device_id = devices;
    }

    public void setEvent_type_code(String[] event_type_code) {
        this.event_type_code = event_type_code;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setUser_id(String[] user_id) {
        this.user_id = user_id;
    }
}
