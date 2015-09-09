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
}