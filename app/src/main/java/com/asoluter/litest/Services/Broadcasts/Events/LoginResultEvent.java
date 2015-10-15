package com.asoluter.litest.Services.Broadcasts.Events;

import java.io.Serializable;

public class LoginResultEvent implements Serializable {
    private boolean loggedIn;
    private boolean connected;

    public LoginResultEvent(boolean loggedIn,boolean connected){
        this.loggedIn=loggedIn; this.connected=connected;
    }

    public boolean isLogin(){
        return loggedIn;
    }

    public boolean isConnected() {
        return connected;
    }
}
