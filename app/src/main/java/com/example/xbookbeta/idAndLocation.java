package com.example.xbookbeta;

public class idAndLocation {
    String key ;
    Double distance ;
    public idAndLocation(String id , Double distance){
        this.key=id ;
        this.distance = distance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key =key ;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
