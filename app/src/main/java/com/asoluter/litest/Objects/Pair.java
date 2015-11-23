package com.asoluter.litest.Objects;

import java.io.Serializable;

public class Pair implements Serializable {
    private Object object;
    private Object object1;

    public Pair(Object first, Object second) {
        this.object = first;
        this.object1 = second;
    }

    public Object getSecond() {
        return object1;
    }

    public void setSecond(Object object1) {
        this.object1 = object1;
    }

    public Object getFirst() {
        return object;
    }

    public void setFirst(Object object) {
        this.object = object;
    }
}
