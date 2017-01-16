package BiostarAPI;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Користувач on 11.01.2017.
 */
public class Biostar {

    private LoggedInUser loggedInUser;
    private String deviceId;


    class UserLoginRequestData {
        private String name;
        private String user_id;
        private String password;
        private String mobile_app_version;
        private String mobile_device_type;
        private String mobile_os_version;
        private String notification_token;
    }

    public Biostar(String subName, String user_id, String password) {
        loggedInUser = authClient(subName, user_id, password);
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public String getDeviceId(){
        return this.deviceId;
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

    public GetDevice getDeviceByID(String deviceID){

        GetDevice device = null;

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();

        java.net.URI uri = null;
        try {
            uri = new URIBuilder("https://api.biostar2.com/v1/devices/"+deviceID)
                    .addParameter("limit", "15").addParameter("offset", "0")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet(uri);

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        //getting
        HttpResponse response = null;
        String responseBody = null;
        try {
            response = client.execute(get, context);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            device = gson.fromJson(responseBody,
                    GetDevice.class);

            //list = searchResult.getRecords();
        }

        return  device;
    }

    public GetDevice getDevice(){
        return getDeviceByID(deviceId);
    }

    public EventLogSearchResultWithoutTotal searchLog(EventQuery eventQuery,boolean more){

        EventLogSearchResultWithoutTotal log = null;

        String query = "https://api.biostar2.com/v1/monitoring/event_log/search" + (more ? "_more" : "");

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(query);

        //set query
        StringEntity input = null;
        try {
            input = new StringEntity(gson.toJson(eventQuery));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        input.setContentType("application/json");
        post.setEntity(input);


        //cookie
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpResponse response = null;
        String responseBody = null;
        try {
            //posting
            response = client.execute(post, context);
            //response processing
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            log = gson.fromJson(responseBody,EventLogSearchResultWithoutTotal.class);
        }

        return log;
    }

    public static String getISO8601StringForDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(date);
    }

    public VerifyFingerprintResult verifyFingerprint(VerifyFingerprintOption fingerprint){

        VerifyFingerprintResult verifyFingerprintResult = null;

        String query = "POST /devices/"+deviceId+"/verify_fingerprint";

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(query);


        //ArrayList<NameValuePair> postParameters;

        //set query
        StringEntity input = null;
        try {
            input = new StringEntity(gson.toJson(fingerprint));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        input.setContentType("application/json");

        post.setEntity(input);


        //cookie
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpResponse response = null;
        String responseBody = null;
        try {
            //posting
            response = client.execute(post, context);
            //response processing
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            verifyFingerprintResult = gson.fromJson(responseBody,VerifyFingerprintResult.class);
        }
        return verifyFingerprintResult;
    }

    public ArrayList<ListUserItem> getUserList() {

        //ListUserItem[] list;

        ArrayList<ListUserItem> list = new ArrayList<ListUserItem>();

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();

        java.net.URI uri = null;
        try {
            uri = new URIBuilder("https://api.biostar2.com/v1/users")
                    .addParameter("limit", "15").addParameter("offset", "0")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet(uri);

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        //getting
        HttpResponse response = null;
        String responseBody = null;
        try {
            response = client.execute(get, context);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            UserSearchResult searchResult = gson.fromJson(responseBody,
                    UserSearchResult.class);

            list = searchResult.getRecords();
        }

        return list;
    }

    public FingerprintTemplate[] getUsersFingerprint(String userID){

        FingerprintTemplate[] fingerprintTemplate = null;

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();

        java.net.URI uri = null;

        try {
            uri = new URIBuilder("https://api.biostar2.com/v1/users/"+userID+"/fingerprint")
                    .addParameter("limit", "15").addParameter("offset", "0")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet(uri);

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        //getting
        HttpResponse response = null;
        String responseBody = null;
        try {
            response = client.execute(get, context);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
           fingerprintTemplate = gson.fromJson(responseBody,
                    FingerprintTemplate[].class);
        }

        return fingerprintTemplate;
    }

}
