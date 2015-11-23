package com.asoluter.litest.Objects;

import java.io.Serializable;
import java.sql.Date;

public class RegData implements Serializable {
    private String userName;
    private String userLogin;
    private String userPassword;
    private String userMail;
    private Date userBirth;

    public RegData(String userName, String userLogin, String userPassword, String userMail, Date userBirth) {
        this.userName = userName;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.userMail = userMail;
        this.userBirth = userBirth;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public Date getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = userBirth;
    }
}
