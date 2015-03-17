package com.suricapp.models;
import com.orm.SugarRecord;

/**
 * Created by maxence on 17/03/15.
 */
public class Point extends SugarRecord<Point> {

    /**
     * CREATE TABLE IF NOT EXISTS `d_point` (
     `point_id` int(11) NOT NULL AUTO_INCREMENT,
     `point_latitude` double NOT NULL,
     `point_longitude` double NOT NULL,
     PRIMARY KEY (`point_id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
     */

    private double point_latitude;
    private double point_longitude;

    public Point() {
    }

    public Point(double point_latitude, double point_longitude) {
        this.point_latitude = point_latitude;
        this.point_longitude = point_longitude;
    }

    public double getPoint_latitude() {
        return point_latitude;
    }

    public void setPoint_latitude(double point_latitude) {
        this.point_latitude = point_latitude;
    }

    public double getPoint_longitude() {
        return point_longitude;
    }

    public void setPoint_longitude(double point_longitude) {
        this.point_longitude = point_longitude;
    }
}
