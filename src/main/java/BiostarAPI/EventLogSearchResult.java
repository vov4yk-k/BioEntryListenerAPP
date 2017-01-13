package BiostarAPI;

/**
 * Created by Користувач on 12.01.2017.
 */
public class EventLogSearchResult {
    private String message;
    private EventLogResult[] records;
    private String status_code;
    private long total;
    private final int SUCCEED_EVENT_TYPE_CODE = 4865;

    public void showLog(){
        for (EventLogResult curLog:records) {
            System.out.println(curLog.toString());
        }
    }

    public EventLogResult[] getRecords() {
        return records;
    }

    public EventLogResult getLastLog(){
        EventLogResult log = records[0];
        return log;
    }

    public boolean authenticated(){
        return getLastLog().getEventTypeCode() == SUCCEED_EVENT_TYPE_CODE;
    }
}
