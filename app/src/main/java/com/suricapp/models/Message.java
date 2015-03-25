package com.suricapp.models;
import com.orm.SugarRecord;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by maxence on 17/03/15.
 */
public class Message extends SugarRecord<Message>
{

    /**
     * CREATE TABLE IF NOT EXISTS `d_message` (
     `message_id` int(11) NOT NULL AUTO_INCREMENT,
     `message_title_fr_fr` varchar(100) NOT NULL,
     `message_content_fr_fr` varchar(100) NOT NULL,
     `message_nb_like` int(11) NOT NULL,
     `message_nb_unlike` int(11) NOT NULL,
     `message_nb_view` int(11) NOT NULL,
     `message_nb_report` int(11) NOT NULL,
     `message_status_fr_fr` varchar(60) NOT NULL,
     `message_date` date NOT NULL,
     `message_id_point_fk` int(11) NOT NULL,
     `message_id_categorie_fk` int(11) NOT NULL,
     `message_id_user_fk` int(11) NOT NULL,
     PRIMARY KEY (`message_id`),
     KEY `message_id_categorie_fk` (`message_id_categorie_fk`,`message_id_user_fk`),
     KEY `message_id_user_fk` (`message_id_user_fk`),
     KEY `message_id_point_fk` (`message_id_point_fk`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
     */

    private int message_id;
    private String message_title_fr_fr;
    private String message_content_fr_fr;
    private int message_nb_like;
    private int message_nb_unlike;
    private int message_nb_view;
    private int message_nb_report;
    private String message_status_fr_fr;
    private Timestamp message_date;
    private double message_latitude;
    private double message_longitude;
    private int message_id_category_fk;
    private int message_id_user_fk;

    public ArrayList<NameValuePair> objectToNameValuePair()
    {
        ArrayList<NameValuePair> followersDetails = new ArrayList<>();
        followersDetails.add(new BasicNameValuePair("message_id",""+this.getMessage_id()));
        followersDetails.add(new BasicNameValuePair("message_title_fr_fr",this.getMessage_title_fr_fr()));
        followersDetails.add(new BasicNameValuePair("message_content_fr_fr",this.getMessage_content_fr_fr()));
        followersDetails.add(new BasicNameValuePair("message_nb_like",""+""+this.getMessage_nb_like()));
        followersDetails.add(new BasicNameValuePair("message_nb_unlike",""+this.getMessage_nb_unlike()));
        followersDetails.add(new BasicNameValuePair("message_nb_report",""+this.getMessage_nb_report()));
        followersDetails.add(new BasicNameValuePair("message_status_fr_fr",""+this.getMessage_status_fr_fr()));
        followersDetails.add(new BasicNameValuePair("message_date",""+this.getMessage_date()));
        followersDetails.add(new BasicNameValuePair("message_latitude",""+this.getMessage_latitude()));
        followersDetails.add(new BasicNameValuePair("message_longitude",""+this.getMessage_longitude()));
        followersDetails.add(new BasicNameValuePair("message_date",""+this.getMessage_date()));
        followersDetails.add(new BasicNameValuePair("message_id_category_fk",""+this.getMessage_id_category_fk()));
        followersDetails.add(new BasicNameValuePair("message_id_user_fk",""+this.getMessage_id_user_fk()));
        return followersDetails;
    }

    public Message() {
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id_) {
        this.message_id = message_id;
    }



    public Message(String message_title_fr_fr, String message_content_fr_fr, int message_nb_like,
                   int message_nb_unlike, int message_nb_view, int message_nb_report,
                   String message_status_fr_fr, Timestamp message_date, int message_id_web_category_fk,
                   int message_id_web_user_fk) {
        this.message_title_fr_fr = message_title_fr_fr;
        this.message_content_fr_fr = message_content_fr_fr;
        this.message_nb_like = message_nb_like;
        this.message_nb_unlike = message_nb_unlike;
        this.message_nb_view = message_nb_view;
        this.message_nb_report = message_nb_report;
        this.message_status_fr_fr = message_status_fr_fr;
        this.message_date = message_date;
        this.message_id_category_fk = message_id_web_category_fk;
        this.message_id_user_fk = message_id_web_user_fk;
    }

    public Message(int message_id_web, String message_title_fr_fr, String message_content_fr_fr,
                   int message_nb_like, int message_nb_unlike, int message_nb_view,
                   int message_nb_report, String message_status_fr_fr, Timestamp message_date,
                   int message_id_web_category_fk, int message_id_web_user_fk) {
        this.message_id = message_id_web;
        this.message_title_fr_fr = message_title_fr_fr;
        this.message_content_fr_fr = message_content_fr_fr;
        this.message_nb_like = message_nb_like;
        this.message_nb_unlike = message_nb_unlike;
        this.message_nb_view = message_nb_view;
        this.message_nb_report = message_nb_report;
        this.message_status_fr_fr = message_status_fr_fr;
        this.message_date = message_date;
        this.message_id_category_fk = message_id_web_category_fk;
        this.message_id_user_fk = message_id_web_user_fk;
    }

    public String getMessage_title_fr_fr() {
        return message_title_fr_fr;
    }

    public void setMessage_title_fr_fr(String message_title_fr_fr) {
        this.message_title_fr_fr = message_title_fr_fr;
    }

    public String getMessage_content_fr_fr() {
        return message_content_fr_fr;
    }

    public void setMessage_content_fr_fr(String message_content_fr_fr) {
        this.message_content_fr_fr = message_content_fr_fr;
    }

    public int getMessage_nb_like() {
        return message_nb_like;
    }

    public void setMessage_nb_like(int message_nb_like) {
        this.message_nb_like = message_nb_like;
    }

    public int getMessage_nb_unlike() {
        return message_nb_unlike;
    }

    public void setMessage_nb_unlike(int message_nb_unlike) {
        this.message_nb_unlike = message_nb_unlike;
    }

    public int getMessage_nb_view() {
        return message_nb_view;
    }

    public void setMessage_nb_view(int message_nb_view) {
        this.message_nb_view = message_nb_view;
    }

    public int getMessage_nb_report() {
        return message_nb_report;
    }

    public void setMessage_nb_report(int message_nb_report) {
        this.message_nb_report = message_nb_report;
    }

    public String getMessage_status_fr_fr() {
        return message_status_fr_fr;
    }

    public void setMessage_status_fr_fr(String message_status_fr_fr) {
        this.message_status_fr_fr = message_status_fr_fr;
    }

    public Timestamp getMessage_date() {
        return message_date;
    }

    public void setMessage_date(Timestamp message_date) {
        this.message_date = message_date;
    }

    public double getMessage_latitude() {
        return message_latitude;
    }

    public Message(int message_id_web, String message_title_fr_fr, String message_content_fr_fr,
                   int message_nb_like, int message_nb_unlike, int message_nb_view,
                   int message_nb_report, String message_status_fr_fr, Timestamp message_date,
                   double message_latitude, double message_longitude, int message_id_web_category_fk,
                   int message_id_web_user_fk) {
        this.message_id = message_id_web;
        this.message_title_fr_fr = message_title_fr_fr;
        this.message_content_fr_fr = message_content_fr_fr;
        this.message_nb_like = message_nb_like;
        this.message_nb_unlike = message_nb_unlike;
        this.message_nb_view = message_nb_view;
        this.message_nb_report = message_nb_report;
        this.message_status_fr_fr = message_status_fr_fr;
        this.message_date = message_date;
        this.message_latitude = message_latitude;
        this.message_longitude = message_longitude;
        this.message_id_category_fk = message_id_web_category_fk;
        this.message_id_user_fk = message_id_web_user_fk;
    }

    public double getMessage_longitude() {

        return message_longitude;
    }

    public void setMessage_longitude(double message_longitude) {
        this.message_longitude = message_longitude;
    }

    public void setMessage_latitude(double message_latitude) {
        this.message_latitude = message_latitude;
    }

    public int getMessage_id_category_fk() {
        return message_id_category_fk;
    }

    public void setMessage_id_category_fk(int message_id_web_category_fk) {
        this.message_id_category_fk = message_id_web_category_fk;
    }

    public int getMessage_id_user_fk() {
        return message_id_user_fk;
    }

    public void setMessage_id_user_fk(int message_id_web_user_fk) {
        this.message_id_user_fk = message_id_web_user_fk;
    }
}
