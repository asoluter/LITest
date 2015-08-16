package com.asoluter.litest.Objects;

import java.io.Serializable;

public class TypingObject implements Serializable {

    private Object object;
    private String type;

    public TypingObject(String type,Object object){
        this.object=object;
        this.type=type;
    }

    public Object getObject() {
        return object;
    }

    public String getType() {
        return type;
    }
}