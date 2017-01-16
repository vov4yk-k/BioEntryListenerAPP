package BiostarAPI;


/**
 * Created by Vova on 02.01.2017.
 */
public class ListUserItem {
    ListUserItemAccessGroup[] access_groups;
    long card_count;
    String email;
    long fingerprint_count;
    long last_modify;
    String name;
    boolean password_exist;
    String phone_number;
    boolean photo_exist;
    boolean pin_exist;
    String status;
    SimpleUserGroup user_group;
    String user_id;

    @Override
    public String toString(){
        return this.name+" ,"+this.user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getName() {
        return name;
    }
}
