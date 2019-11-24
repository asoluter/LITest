package com.asoluter.litest.Tests;

import com.asoluter.litest.Objects.DataBase;

public class Tests {
    private static DataBase dataBase;

    public synchronized static DataBase getDataBase() {
        return dataBase;
    }

    public synchronized static void setDataBase(DataBase dataBase) {
        Tests.dataBase = dataBase;
    }

    public synchronized static int getTestIdFromContest(int rel_id, int cont_id){
        int k=-1;
        int kc=dataBase.getTest_cont_id().size();
        for(int i=0;i<kc;i++){
            if(dataBase.getTest_cont_id().get(i).equals(cont_id)){
                k++;
                if(k==rel_id){
                    return dataBase.getTest_test_id().get(i);
                }
            }
        }
        return 0;
    }
}