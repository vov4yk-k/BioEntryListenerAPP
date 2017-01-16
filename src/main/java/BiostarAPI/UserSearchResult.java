package BiostarAPI;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Vova on 02.01.2017.
 */
public class UserSearchResult {
    String message;
    ListUserItem [] records;
    String status_code;
    long total;

    public ArrayList<ListUserItem> getRecords() {
        ArrayList<ListUserItem> list = new ArrayList<ListUserItem>(Arrays.asList(this.records));
        return list;
    }
}
