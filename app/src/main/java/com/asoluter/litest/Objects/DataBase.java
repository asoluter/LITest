package com.asoluter.litest.Objects;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataBase implements Serializable {
    private ArrayList<Integer> cont_cont_id;
    private ArrayList<String> cont_name;
    private ArrayList<String> cont_ending;

    private ArrayList<Integer> test_cont_id;
    private ArrayList<Integer> test_test_id;
    private ArrayList<String> test_name;
    private ArrayList<String> test_quest;

    private ArrayList<Integer> ans_test_id;
    private ArrayList<Integer> ans_id;
    private ArrayList<String> ans_text;

    public ArrayList<Integer> getCont_cont_id() {
        return cont_cont_id;
    }

    public void setCont_cont_id(ArrayList<Integer> cont_cont_id) {
        this.cont_cont_id = cont_cont_id;
    }

    public ArrayList<String> getCont_name() {
        return cont_name;
    }

    public void setCont_name(ArrayList<String> cont_name) {
        this.cont_name = cont_name;
    }

    public ArrayList<String> getCont_ending() {
        return cont_ending;
    }

    public void setCont_ending(ArrayList<String> cont_ending) {
        this.cont_ending = cont_ending;
    }

    public ArrayList<Integer> getTest_cont_id() {
        return test_cont_id;
    }

    public void setTest_cont_id(ArrayList<Integer> test_cont_id) {
        this.test_cont_id = test_cont_id;
    }

    public ArrayList<Integer> getTest_test_id() {
        return test_test_id;
    }

    public void setTest_test_id(ArrayList<Integer> test_test_id) {
        this.test_test_id = test_test_id;
    }

    public ArrayList<String> getTest_name() {
        return test_name;
    }

    public void setTest_name(ArrayList<String> test_name) {
        this.test_name = test_name;
    }

    public ArrayList<String> getTest_quest() {
        return test_quest;
    }

    public void setTest_quest(ArrayList<String> test_quest) {
        this.test_quest = test_quest;
    }

    public ArrayList<Integer> getAns_test_id() {
        return ans_test_id;
    }

    public void setAns_test_id(ArrayList<Integer> ans_test_id) {
        this.ans_test_id = ans_test_id;
    }

    public ArrayList<Integer> getAns_id() {
        return ans_id;
    }

    public void setAns_id(ArrayList<Integer> ans_id) {
        this.ans_id = ans_id;
    }

    public ArrayList<String> getAns_text() {
        return ans_text;
    }

    public void setAns_text(ArrayList<String> ans_text) {
        this.ans_text = ans_text;
    }
}
