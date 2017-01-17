package BiostarAPI;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Користувач on 16.01.2017.
 */
public class VerifyFingerprintResult {
    String message;
    String status_code;
    boolean verify_result;

    public String toString(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return "Message:"+message+", Status:"+status_code+", Result:"+verify_result+"  "+dateFormat.format(date);
    }
}
