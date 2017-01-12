package BiostarAPI;

/**
 * Created by Користувач on 12.01.2017.
 */
public class EventLogSearchResult {
    String message;
    EventLogResult[] records;
    String status_code;
    long total;

    public void showLog(){
        for (EventLogResult curLog:records) {
            System.out.println(curLog.toString());
        }
    }

    public EventLogResult getLastLog(){
        EventLogResult log = null;
        return log;
    }
}
