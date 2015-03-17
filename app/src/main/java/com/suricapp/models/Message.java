package com.suricapp.models;
import com.orm.SugarRecord;

import java.sql.Date;

/**
 * Created by maxence on 17/03/15.
 */
public class Message extends SugarRecord<Message> {

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

    private String message_title_fr_fr;
    private String message_content_fr_fr;
    private int message_nb_like;
    private int message_nb_unlike;
    private int message_nb_view;
    private int message_nb_report;
    private String message_status_fr_fr;
    private Date message_date;
    private Point message_id_point_fk;
    private Category message_id_category_fk;
    private User message_id_user_fk;

    public Message() {
    }

    public Message(String message_title_fr_fr, String message_content_fr_fr, int message_nb_like, int message_nb_unlike, int message_nb_view, int message_nb_report, String message_status_fr_fr, Date message_date, Point message_id_point_fk, Category message_id_category_fk, User message_id_user_fk) {
        this.message_title_fr_fr = message_title_fr_fr;
        this.message_content_fr_fr = message_content_fr_fr;
        this.message_nb_like = message_nb_like;
        this.message_nb_unlike = message_nb_unlike;
        this.message_nb_view = message_nb_view;
        this.message_nb_report = message_nb_report;
        this.message_status_fr_fr = message_status_fr_fr;
        this.message_date = message_date;
        this.message_id_point_fk = message_id_point_fk;
        this.message_id_category_fk = message_id_category_fk;
        this.message_id_user_fk = message_id_user_fk;
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

    public Date getMessage_date() {
        return message_date;
    }

    public void setMessage_date(Date message_date) {
        this.message_date = message_date;
    }

    public Point getMessage_id_point_fk() {
        return message_id_point_fk;
    }

    public void setMessage_id_point_fk(Point message_id_point_fk) {
        this.message_id_point_fk = message_id_point_fk;
    }

    public Category getMessage_id_category_fk() {
        return message_id_category_fk;
    }

    public void setMessage_id_category_fk(Category message_id_category_fk) {
        this.message_id_category_fk = message_id_category_fk;
    }

    public User getMessage_id_user_fk() {
        return message_id_user_fk;
    }

    public void setMessage_id_user_fk(User message_id_user_fk) {
        this.message_id_user_fk = message_id_user_fk;
    }
}
