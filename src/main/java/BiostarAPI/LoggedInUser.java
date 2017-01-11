package BiostarAPI;

import org.apache.http.cookie.Cookie;

/**
 * Created by Користувач on 11.01.2017.
 */
public class LoggedInUser {
    String account_id;
    String email;
    String name;
    String password_strength_level;
    CloudPermission[] permissions;
    String photo;
    CloudRole[] roles;
    String status;
    int unread_notification_count;
    SimpleUserGroup user_group;
    String user_id;
    Cookie cookie;

    public void setCookie(Cookie cookie){
        this.cookie = cookie;
    }

    public Cookie getCookie(){
        return this.cookie;
    }
}
