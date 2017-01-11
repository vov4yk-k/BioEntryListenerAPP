package BiostarAPI;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
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
import java.util.List;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Biostar {

    private LoggedInUser loggedInUser;

    class UserLoginRequestData {
        private String name;
        private String user_id;
        private String password;
        private String mobile_app_version;
        private String mobile_device_type;
        private String mobile_os_version;
        private String notification_token;
    }

    Biostar(String subName, String user_id, String password) {
        loggedInUser = authClient(subName, user_id, password);
    }

    public LoggedInUser authClient(String subName, String user_id, String password) {

        //resp
        LoggedInUser responseData = null;

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://api.biostar2.com/v1/login");

        //usr data
        UserLoginRequestData userLogin = new UserLoginRequestData();
        userLogin.name = "complex";
        userLogin.user_id = "HR";
        userLogin.password = "12345qwerty";

        try {
            StringEntity input = new StringEntity(gson.toJson(userLogin));
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
            responseData = gson.fromJson(responseBody, LoggedInUser.class);
            List<Cookie> cookies = context.getCookieStore().getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("bs-cloud-session-id")) {
                    responseData.setCookie(cookie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseData;

    }

}
