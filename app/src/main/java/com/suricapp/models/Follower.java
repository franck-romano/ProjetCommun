package com.suricapp.models;
import com.orm.SugarRecord;

/**
 * Created by maxence on 17/03/15.
 */
public class Follower extends SugarRecord<Follower>{

    /**
     * CREATE TABLE IF NOT EXISTS `d_follower` (
     `followers_id_followers` int(11) NOT NULL,
     `followers_id_user_fk` int(11) NOT NULL,
     PRIMARY KEY (`followers_id_followers`),
     KEY `followers_id_user_fk` (`followers_id_user_fk`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    private User followers_id_followers;
    private User followers_id_user_fk;

    public Follower() {
    }

    public Follower(User followers_id_followers, User followers_id_user_fk) {
        this.followers_id_followers = followers_id_followers;
        this.followers_id_user_fk = followers_id_user_fk;
    }

    public User getFollowers_id_followers() {
        return followers_id_followers;
    }

    public void setFollowers_id_followers(User followers_id_followers) {
        this.followers_id_followers = followers_id_followers;
    }

    public User getFollowers_id_user_fk() {
        return followers_id_user_fk;
    }

    public void setFollowers_id_user_fk(User followers_id_user_fk) {
        this.followers_id_user_fk = followers_id_user_fk;
    }
}
