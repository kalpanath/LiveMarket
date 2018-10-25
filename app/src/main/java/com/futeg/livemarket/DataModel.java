package com.futeg.livemarket;


public class DataModel {


    String name;
    String version;
    String date;


    public DataModel(String name, String version, String date) {
        this.name = name;
        this.version = version;
        this.date = date;
        //  this.image=image;
    }


    public String getName() {
        return name;
    }


    public String getVersion() {
        return version;
    }


    public String getDate() {
        return date;
    }
}