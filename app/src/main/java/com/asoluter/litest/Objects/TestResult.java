package com.asoluter.litest.Objects;

import java.util.ArrayList;

public class TestResult {
    private ArrayList<Integer> tests;
    private int testN;

    public TestResult(int testsCount,int testN){
        this.testN=testN;
        this.tests=new ArrayList<Integer>(testsCount);
    }

    public ArrayList<Integer> getTests() {
        return tests;
    }

    public int getTestN() {
        return testN;
    }
}
