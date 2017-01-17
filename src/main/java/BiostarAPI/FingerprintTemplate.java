package BiostarAPI;

import sun.misc.BASE64Encoder;

/**
 * Created by Користувач on 16.01.2017.
 */
public class FingerprintTemplate {
    Object is_prepare_for_duress; //(//object, optional): 협박 당할 때를 대비한 지문 정보,
    Object template0; //(object, optional): 지문 템플릿 패턴 정보 (base64 encoding string),
    Object template1; //(object, optional): 지문 템플릿 패턴 정보 (base64 encoding string)

    public String getTemplate0() {
        return (String)template0;
    }

    public String getTemplate1() {
        return (String)template1;
    }
}
