package com.asoluter.litest.Objects;

import java.io.Serializable;

public class AnsObject implements Serializable {
    private int cont_id;
    private int test_id;
    private int ans_id;

    public int getCont_id() {
        return cont_id;
    }

    public void setCont_id(int cont_id) {
        this.cont_id = cont_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public int getAns_id() {
        return ans_id;
    }

    public void setAns_id(int ans_id) {
        this.ans_id = ans_id;
    }

    public AnsObject(int cont_id, int test_id, int ans_id) {
        this.cont_id = cont_id;

        this.test_id = test_id;
        this.ans_id = ans_id;
    }
}
