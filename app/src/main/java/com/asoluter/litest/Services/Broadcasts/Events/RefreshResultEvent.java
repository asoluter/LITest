package com.asoluter.litest.Services.Broadcasts.Events;

import java.io.Serializable;

public class RefreshResultEvent implements Serializable {
    private boolean status;

    public RefreshResultEvent(boolean status){
        this.status=status;
    }

    public boolean getStatus(){
        return status;
    }
}
