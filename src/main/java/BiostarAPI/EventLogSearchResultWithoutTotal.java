package BiostarAPI;

public class EventLogSearchResultWithoutTotal extends EventLogSearchResult {
    boolean is_next;
    //String message;
    //EventLogResult[] records;
    //String status_code;
    //long total;


    public boolean isNext(){
        return is_next;
    }
}
