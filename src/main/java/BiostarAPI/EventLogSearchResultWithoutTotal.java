package BiostarAPI;

public class EventLogSearchResultWithoutTotal {
    boolean is_next;
    String message;
    EventLogResult[] records;
    String status_code;
    long total;

    public void showLog(){
        for (EventLogResult curLog:records) {
            System.out.println(curLog.toString());
        }
    }
    public boolean isNext(){
        return is_next;
    }
}
