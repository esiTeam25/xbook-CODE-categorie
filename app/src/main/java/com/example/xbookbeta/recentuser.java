package com.example.xbookbeta;

import java.sql.Timestamp;

public class recentuser {
    private String name , image , id , msg  ;
    Object time ;

    public recentuser(String name, String image, String id, String msg , Object time ) {
        this.name = name;
        this.image = image;
        this.id = id;
        this.msg = msg;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
