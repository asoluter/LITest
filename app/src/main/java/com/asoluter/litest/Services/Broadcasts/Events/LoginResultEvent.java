package com.asoluter.litest.Services.Broadcasts.Events;

import java.io.Serializable;

public class LoginResultEvent implements Serializable {
    private boolean loggedIn;

    public LoginResultEvent(boolean loggedIn){
        this.loggedIn=loggedIn;
    }

    public boolean getLogin(){
        return loggedIn;
    }
}
