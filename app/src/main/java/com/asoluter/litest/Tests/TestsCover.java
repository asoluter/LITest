package com.asoluter.litest.Tests;

import android.os.Bundle;

import com.asoluter.litest.Objects.Strings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TestsCover {
    public static ArrayList<Integer> cont_id;
    public static ArrayList<Integer> test_id;
    public static ArrayList<String> quests;



    public static ArrayList<String> getContests(){

        return Tests.getDataBase().getCont_name();
    }

    public static ArrayList<String> getTests(int contestPosition){
        ArrayList<String> tests=new ArrayList<String>();
        test_id=new ArrayList<Integer>();
        quests=new ArrayList<String>();

        for(int i=0;i<Tests.getDataBase().getTest_cont_id().size();i++){
            if(Tests.getDataBase().getTest_cont_id().get(i)
                    .equals(Tests.getDataBase().getCont_cont_id().get(contestPosition))){
                test_id.add(Tests.getDataBase().getTest_test_id().get(i));
                tests.add(Tests.getDataBase().getTest_name().get(i));
                quests.add(Tests.getDataBase().getTest_quest().get(i));
            }
        }

        return tests;
    }

    public static Bundle getAnsvers(int testPosition){
        ArrayList<String> ansvers=new ArrayList<String>();
        ArrayList<Integer> ans_id=new ArrayList<Integer>();

        for(int i=0;i<Tests.getDataBase().getAns_test_id().size();i++){
            if(Tests.getDataBase().getAns_test_id().get(i)
                    .equals(test_id.get(testPosition))){
                ans_id.add(Tests.getDataBase().getAns_id().get(i));
                ansvers.add(Tests.getDataBase().getAns_text().get(i));
            }
        }

        Bundle bundle=new Bundle();
        bundle.putStringArrayList(Strings.ANSVERS,ansvers );
        bundle.putIntegerArrayList(Strings.ANS_ID,ans_id);
        return bundle;
    }
}
