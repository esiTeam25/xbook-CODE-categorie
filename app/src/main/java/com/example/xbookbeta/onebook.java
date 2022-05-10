package com.example.xbookbeta;

public class onebook {
    String userid , bookimage , title , categorie , distance  , key ;
    Double latitude , longitude ;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public onebook(String userid , String bookimage, String title, String categorie, Double latitude , Double longitude ) {
    this.userid = userid;
        this.bookimage = bookimage;
        this.title = title;
        this.categorie = categorie;
this.latitude = latitude;
    this.longitude = longitude ;

    }
    public onebook(String userid , String bookimage, String title, String categorie, Double latitude , Double longitude , String key) {
        this.userid = userid;
        this.bookimage = bookimage;
        this.title = title;
        this.categorie = categorie;
        this.latitude = latitude;
        this.longitude = longitude ;
        this.key = key ;

    }
    public onebook( String bookimage, String title, String categorie, Double latitude , Double longitude) {
        this.userid = userid;
        this.bookimage = bookimage;
        this.title = title;
        this.categorie = categorie;
        this.latitude = latitude;
        this.longitude = longitude ;
    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBookimage() {
        return bookimage;
    }

    public void setBookimage(String bookimage) {
        this.bookimage = bookimage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
