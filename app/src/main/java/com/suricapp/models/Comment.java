package com.suricapp.models;
import com.orm.SugarRecord;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by maxence on 16/03/15.
 */
public class Comment extends SugarRecord<Comment>{

    /**
     * --

     CREATE TABLE IF NOT EXISTS `d_comment` (
     `comment_id` int(11) NOT NULL,
     `comment_date` date NOT NULL,
     `comment_content_fr_fr` text NOT NULL,
     `comment_message_id_fk` int(11) NOT NULL,
     `comment_user_id_fk` int(11) NOT NULL,
     PRIMARY KEY (`comment_id`),
     KEY `comment_message_id_fk` (`comment_message_id_fk`,`comment_user_id_fk`),
     KEY `comment_user_id_fk` (`comment_user_id_fk`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    private Date comment_date;
    private String comment_content_fr_fr;
    private Message comment_message_id_fk;
    private User comment_user_id_fk;

    public ArrayList<NameValuePair> objectToNameValuePair()
    {
        ArrayList<NameValuePair> followersDetails = new ArrayList<>();
        followersDetails.add(new BasicNameValuePair("comment_date",this.getComment_date().toString()));
        followersDetails.add(new BasicNameValuePair("comment_content_fr_fr",this.getComment_content_fr_fr()));
        followersDetails.add(new BasicNameValuePair("comment_message_id_fk",this.getComment_message_id_fk().getId().toString()));
        followersDetails.add(new BasicNameValuePair("comment_user_id_fk",this.getComment_user_id_fk().getId().toString()));
        return followersDetails;
    }

    public Comment() {
    }

    public Comment(Date comment_date, String comment_content_fr_fr, Message comment_message_id_fk, User comment_user_id_fk) {
        this.comment_date = comment_date;
        this.comment_content_fr_fr = comment_content_fr_fr;
        this.comment_message_id_fk = comment_message_id_fk;
        this.comment_user_id_fk = comment_user_id_fk;
    }

    public Date getComment_date() {
        return comment_date;
    }

    public void setComment_date(Date comment_date) {
        this.comment_date = comment_date;
    }

    public String getComment_content_fr_fr() {
        return comment_content_fr_fr;
    }

    public void setComment_content_fr_fr(String comment_content_fr_fr) {
        this.comment_content_fr_fr = comment_content_fr_fr;
    }

    public Message getComment_message_id_fk() {
        return comment_message_id_fk;
    }

    public void setComment_message_id_fk(Message comment_message_id_fk) {
        this.comment_message_id_fk = comment_message_id_fk;
    }

    public User getComment_user_id_fk() {
        return comment_user_id_fk;
    }

    public void setComment_user_id_fk(User comment_user_id_fk) {
        this.comment_user_id_fk = comment_user_id_fk;
    }
}
