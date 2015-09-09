package com.asoluter.litest.Tests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestsCover {
    public static ArrayList<Integer> cont_id;
    public static ArrayList<Integer> test_id;
    public static ArrayList<String> quests;
    public static ArrayList<Integer> ans_id;

    public static ArrayList<String> getContests(){

        return Tests.getDataBase().getCont_name();
    }

    public static ArrayList<String> getTests(int position){
        ArrayList<String> tests=new ArrayList<String>();
        test_id=new ArrayList<Integer>();
        quests=new ArrayList<String>();

        for(int i=0;i<Tests.getDataBase().getTest_cont_id().size();i++){
            if(Tests.getDataBase().getTest_cont_id().get(i)
                    .equals(Tests.getDataBase().getCont_cont_id().get(position))){
                test_id.add(Tests.getDataBase().getTest_test_id().get(i));
                tests.add(Tests.getDataBase().getTest_name().get(i));
                quests.add(Tests.getDataBase().getTest_quest().get(i));
            }
        }

        return tests;
    }
}
