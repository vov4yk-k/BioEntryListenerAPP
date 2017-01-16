package BiostarAPI;

/**
 * Created by Користувач on 16.01.2017.
 */
public class VerifyFingerprintOption {
    private String security_level;//Verify success criteria('DEFAULT', 'LOWER', 'LOW', 'NORMAL', 'HIGH', 'HIGHER'),
    private String template0;//fingerprint template base64 string,
    private String template1;//(base64, optional): fingerprint template base64 string

    public VerifyFingerprintOption(String security_level, String template0, String template1) {
        this.security_level = security_level;
        this.template0 = template0;
        this.template1 = template1;
    }

    public VerifyFingerprintOption(String template0, String template1) {
        this.security_level ="DEFAULT";
        this.template0 = template0;
        this.template1 = template1;
    }
}
