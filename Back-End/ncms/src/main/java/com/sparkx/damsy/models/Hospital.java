package com.sparkx.damsy.models;

import java.util.Date;

public class Hospital {

    private String id;
    private String name;
    private String district;
    private int locationX;
    private int locationY;
    private Date buildDate;
    
    public Hospital() {
    }

    /**
     * 
     * @param id
     */
    public Hospital(String id) {
        this.id = id;
    }

    /**
     * 
     * @param id
     * @param name
     * @param locationX
     * @param locationY
     * @param buildDate
     */
    public Hospital(String id, String name, int locationX, int locationY, Date buildDate) {
        this.id = id;
        this.name = name;
        this.locationX = locationX;
        this.locationY = locationY;
        this.buildDate = buildDate;
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    } 

}
