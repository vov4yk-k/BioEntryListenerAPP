package BiostarAPI;

/**
 * Created by Користувач on 16.01.2017.
 */
public class VerifyFingerprintResult {
    String message;
    String status_code;
    boolean verify_result;

    public String toString(){
        return message+" "+status_code+" "+verify_result;
    }
}
