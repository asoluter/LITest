package com.asoluter.litest.Objects;

import java.io.Serializable;

public class RegData implements Serializable {
    private String userName;
    private String userPassword;
    private String userMail;
    private String userInfo;

    public RegData(String userName, String userPassword, String userMail, String userInfo) {

        this.userName = userName;
        this.userPassword = userPassword;
        this.userMail = userMail;
        this.userInfo = userInfo;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public String getUserMail() {
        return userMail;
    }
}
