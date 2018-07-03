package com.imagine.bd.hayvenapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SigninResponse implements Serializable {
    public SigninResponse(ArrayList<UserInfo> alluserlist, String msg) {
        this.alluserlist = alluserlist;
        this.msg = msg;
    }

    ArrayList<UserInfo> alluserlist = new ArrayList<>();
    private String msg = "";
    private UserInfo user=new UserInfo();


    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }


    public ArrayList<UserInfo> getAlluserlist() {
        return alluserlist;
    }

    public void setAlluserlist(ArrayList<UserInfo> alluserlist) {
        this.alluserlist = alluserlist;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
