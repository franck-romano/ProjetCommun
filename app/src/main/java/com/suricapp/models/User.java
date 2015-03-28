package com.suricapp.models;
import com.orm.SugarRecord;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by maxence on 17/03/15.
 */
public class User extends SugarRecord<User> implements Serializable{

    /**
     *
     CREATE TABLE IF NOT EXISTS `d_user` (
     `user_id` int(11) NOT NULL AUTO_INCREMENT,
     `user_pseudo` varchar(60) NOT NULL,
     `user_password` varchar(60) NOT NULL,
     `user_name` varchar(60) NOT NULL,
     `user_first_name` varchar(60) NOT NULL,
     `user_date_creation` date NOT NULL,
     `user_birthday` date DEFAULT NULL,
     `user_situation_fr_fr` varchar(60) DEFAULT NULL,
     `user_city` varchar(60) DEFAULT NULL,
     `user_picture` varchar(60) DEFAULT NULL,
     `user_phone` varchar(20) DEFAULT NULL,
     `user_email` varchar(100) NOT NULL,
     `user_is_premium` tinyint(1) NOT NULL DEFAULT '0',
     PRIMARY KEY (`user_id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;
     */

    private int user_id_web;
    private String user_pseudo;
    private String user_password;
    private String user_name;
    private String user_first_name;
    private Date user_date_creation;
    private Date user_birthday;
    private String user_situation_fr_fr;
    private String user_city;
    private String user_picture;
    private String user_phone;
    private String user_email;
    private int user_is_premium;


    public User(int user_id_web, String user_pseudo, String user_password, String user_name,
                String user_first_name, Date user_date_creation, Date user_birthday,
                String user_situation_fr_fr, String user_city, String user_picture, String user_phone,
                String user_email, int user_is_premium) {
        this.user_id_web = user_id_web;
        this.user_pseudo = user_pseudo;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_first_name = user_first_name;
        this.user_date_creation = user_date_creation;
        this.user_birthday = user_birthday;
        this.user_situation_fr_fr = user_situation_fr_fr;
        this.user_city = user_city;
        this.user_picture = user_picture;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_is_premium = user_is_premium;
    }

    public int getUser_id() {
        return user_id_web;
    }

    public void setUser_id(int user_id_web) {
        this.user_id_web = user_id_web;
    }

    public ArrayList<NameValuePair> objectToNameValuePair()
    {
        ArrayList<NameValuePair> followersDetails = new ArrayList<>();
        followersDetails.add(new BasicNameValuePair("user_pseudo",this.getUser_pseudo()));
        followersDetails.add(new BasicNameValuePair("user_password",this.getUser_password()));
        followersDetails.add(new BasicNameValuePair("user_name",""+""+this.getUser_name()));
        followersDetails.add(new BasicNameValuePair("user_first_name",""+this.getUser_first_name()));
        followersDetails.add(new BasicNameValuePair("user_date_creation",""+this.getUser_date_creation()));
        followersDetails.add(new BasicNameValuePair("user_birthday",""+this.getUser_birthday()));
        followersDetails.add(new BasicNameValuePair("user_situation_fr_fr",""+this.getUser_situation_fr_fr()));
        followersDetails.add(new BasicNameValuePair("user_city",""+this.getUser_city()));
        followersDetails.add(new BasicNameValuePair("user_phone",""+this.getUser_phone()));
        followersDetails.add(new BasicNameValuePair("user_email",""+this.getUser_email()));
        followersDetails.add(new BasicNameValuePair("user_picture",""+this.getUser_picture()));
        followersDetails.add(new BasicNameValuePair("user_is_premium",""+this.getUser_is_premium()));
        return followersDetails;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("pseudo").append(getUser_pseudo());
        sb.append("pass").append(getUser_password());
        sb.append("picture").append(getUser_picture());
        return sb.toString();
    }

    public User() {
    }

    public User(String user_pseudo, String user_password, String user_name, String user_first_name,
                Date user_date_creation, Date user_birthday, String user_situation_fr_fr,
                String user_city, String user_picture, String user_phone, String user_email,
                int user_is_premium) {
        this.user_pseudo = user_pseudo;
        this.user_password = user_password;
        this.user_name = user_name;
        this.user_first_name = user_first_name;
        this.user_date_creation = user_date_creation;
        this.user_birthday = user_birthday;
        this.user_situation_fr_fr = user_situation_fr_fr;
        this.user_city = user_city;
        this.user_picture = user_picture;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_is_premium = user_is_premium;
    }

    public String getUser_pseudo() {
        return user_pseudo;
    }

    public void setUser_pseudo(String user_pseudo) {
        this.user_pseudo = user_pseudo;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_first_name() {
        return user_first_name;
    }

    public void setUser_first_name(String user_first_name) {
        this.user_first_name = user_first_name;
    }

    public Date getUser_date_creation() {
        return user_date_creation;
    }

    public void setUser_date_creation(Date user_date_creation) {
        this.user_date_creation = user_date_creation;
    }

    public Date getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_situation_fr_fr() {
        return user_situation_fr_fr;
    }

    public void setUser_situation_fr_fr(String user_situation_fr_fr) {
        this.user_situation_fr_fr = user_situation_fr_fr;
    }

    public String getUser_city() {
        return user_city;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public String getUser_picture() {
        return user_picture;
    }

    public void setUser_picture(String user_picture) {
        this.user_picture = user_picture;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getUser_is_premium() {
        return user_is_premium;
    }

    public void setUser_is_premium(int user_is_premium) {
        this.user_is_premium = user_is_premium;
    }
}
