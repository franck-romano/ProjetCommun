package com.suricapp.models;
import com.orm.SugarRecord;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

/**
 * Created by maxence on 16/03/15.
 */
public class Category extends SugarRecord<Category>{

    /**
     *
     CREATE TABLE IF NOT EXISTS `d_category` (
     `category_id` int(11) NOT NULL,
     `category_label` varchar(100) NOT NULL,
     `category_description_fr_fr` text NOT NULL,
     PRIMARY KEY (`category_id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    private String category_label;
    private String category_description;

    public ArrayList<NameValuePair> objectToNameValuePair()
    {
        ArrayList<NameValuePair> followersDetails = new ArrayList<>();
        followersDetails.add(new BasicNameValuePair("category_label",this.getCategory_label()));
        followersDetails.add(new BasicNameValuePair("category_description",this.getCategory_description()));
        return followersDetails;
    }

    public Category() {
    }

    public Category(String category_label, String category_description) {
        this.category_label = category_label;
        this.category_description = category_description;
    }

    public String getCategory_description() {
        return category_description;
    }

    public void setCategory_description(String category_description) {
        this.category_description = category_description;
    }

    public String getCategory_label() {
        return category_label;
    }

    public void setCategory_label(String category_label) {
        this.category_label = category_label;
    }
}
