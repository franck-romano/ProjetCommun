package com.suricapp.models;
import com.orm.SugarRecord;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by maxence on 17/03/15.
 */
public class Follower{

    /**
     * CREATE TABLE IF NOT EXISTS `d_follower` (
     `followers_id_followers` int(11) NOT NULL,
     `followers_id_user_fk` int(11) NOT NULL,
     PRIMARY KEY (`followers_id_followers`),
     KEY `followers_id_user_fk` (`followers_id_user_fk`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    private int followers_id_followers;
    private int followers_id_user_fk;

    public Follower() {
    }

    public Follower(int followers_id_followers, int followers_id_user_fk) {
        this.followers_id_followers = followers_id_followers;
        this.followers_id_user_fk = followers_id_user_fk;
    }

    public int getFollowers_id_followers() {
        return followers_id_followers;
    }

    public void setFollowers_id_followers(int followers_id_followers) {
        this.followers_id_followers = followers_id_followers;
    }

    public int getFollowers_id_user_fk() {
        return followers_id_user_fk;
    }

    public void setFollowers_id_user_fk(int followers_id_user_fk) {
        this.followers_id_user_fk = followers_id_user_fk;
    }

    public ArrayList<NameValuePair> objectToNameValuePair()
    {
        ArrayList<NameValuePair> followersDetails = new ArrayList<>();
        followersDetails.add(new BasicNameValuePair("followers_id_followers",""+this.getFollowers_id_followers()));
        followersDetails.add(new BasicNameValuePair("followers_id_user",""+this.getFollowers_id_user_fk()));
        return followersDetails;
    }
}
