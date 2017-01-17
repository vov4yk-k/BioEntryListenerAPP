package UT;

import BiostarAPI.EventLogSearchResult;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.List;

/**
 * Created by Користувач on 13.01.2017.
 */
public class UTConnection {
    //http://localhost:8080/LegoPartBonus/services/hs/wsbio/log

    class UserLoginRequestData {
        private String name;
        private String user_id;
        private String password;
        private String mobile_app_version;
        private String mobile_device_type;
        private String mobile_os_version;
        private String notification_token;
    }

    public void setData(EventLogSearchResult eventLogSearchResult){

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("Complex","159753".toCharArray());
                //return new PasswordAuthentication(login,password.toCharArray());
            }
        });

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://localhost:8080/LegoPartBonus/hs/wsbio/log");

        //usr data
        /*UserLoginRequestData userLogin = new UserLoginRequestData();
        userLogin.name = "complex";
        userLogin.user_id = "HR";
        userLogin.password = "12345qwerty";*/

        try {
            StringEntity input = new StringEntity(gson.toJson(eventLogSearchResult));
            input.setContentType("application/json");
            post.setEntity(input);

            //cookie
            HttpClientContext context = HttpClientContext.create();
            CookieStore cookieStore = new BasicCookieStore();
            context.setCookieStore(cookieStore);

            //posting
            HttpResponse response = client.execute(post, context);


            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                System.out.println("ok");
            }
            /*responseData = gson.fromJson(responseBody, LoggedInUser.class);
            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("bs-cloud-session-id")) {
                    responseData.setCookie(cookie);
                }
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return responseData;
    }

    public static String getAuthantication(String username, String password) {
        String namePass = username + ":" + password;
        String auth = new String(Base64.encode(namePass.getBytes()));
        return auth;
    }
}
