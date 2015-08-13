package com.asoluter.litest.Objects;

public class TypingObject {

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