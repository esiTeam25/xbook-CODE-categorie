package com.example.xbookbeta;

import java.util.Date;

public class onechat {
    String senderid , receiverid , msg , date  ;
   // public Date date ;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public onechat(String senderid, String receiverid, String msg , String date ) {
        this.senderid = senderid;
        this.receiverid = receiverid;
        this.msg = msg;
        this.date = date ;
    }

    public onechat() {
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getReceiverid() {
        return receiverid;
    }

    public void setReceiverid(String receiverid) {
        this.receiverid = receiverid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

