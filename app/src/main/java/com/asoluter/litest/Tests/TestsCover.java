package com.asoluter.litest.Tests;

import android.os.Bundle;

import com.asoluter.litest.Objects.Strings;

import java.util.ArrayList;
import java.util.Collections;

public class TestsCover {
    public static ArrayList<Integer> cont_id;
    public static ArrayList<Integer> test_id;
    public static ArrayList<String> quests;


    public static ArrayList<String> getContests(){

        return Tests.getDataBase().getCont_name();
    }

    public static ArrayList<String> getTests(int contestPosition){
        ArrayList<String> tests= new ArrayList<>();
        test_id=new ArrayList<>();
        quests=new ArrayList<>();

        ArrayList<Integer> testContestIds = Tests.getDataBase().getTest_cont_id();
        for(int i=0;i<testContestIds.size();i++){
            Integer testContestId = testContestIds.get(i);
            ArrayList<Integer> contestContestIds = Tests.getDataBase().getCont_cont_id();
            Integer contestContestId = contestContestIds.get(contestPosition);
            if(testContestId.equals(contestContestId)){
                test_id.add(Tests.getDataBase().getTest_test_id().get(i));
                tests.add(Tests.getDataBase().getTest_name().get(i));
                quests.add(Tests.getDataBase().getTest_quest().get(i));
            }
        }

        return tests;
    }

    public static Bundle getAnswers(int testPosition){
        ArrayList<String> answers=new ArrayList<>();
        ArrayList<Integer> ans_id=new ArrayList<>();

        for(int i=0;i<Tests.getDataBase().getAns_test_id().size();i++){
            if(Tests.getDataBase().getAns_test_id().get(i)
                    .equals(test_id.get(testPosition))){
                ans_id.add(Tests.getDataBase().getAns_id().get(i));
                answers.add(Tests.getDataBase().getAns_text().get(i));
            }
        }

        Collections.reverse(ans_id);
        Collections.reverse(answers);

        Bundle bundle=new Bundle();
        bundle.putStringArrayList(Strings.ANSWERS,answers );
        bundle.putIntegerArrayList(Strings.ANS_ID,ans_id);
        return bundle;
    }


}
