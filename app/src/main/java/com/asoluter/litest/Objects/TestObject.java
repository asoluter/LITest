package com.asoluter.litest.Objects;

import java.io.Serializable;

public class TestObject implements Serializable {
    private String s;

    public TestObject(String s){
        this.s=s;
    }

    public String getS(){
        return s;
    }
}

