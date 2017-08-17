package com.example.administrator.rentcar.activity;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.administrator.rentcar.entity.DaoMaster;
import com.example.administrator.rentcar.entity.DaoSession;


/*
 * Copyright @2017 甘肃诚诚网络技术有限公司 All rights reserved.
 * 甘肃诚诚网络技术有限公司 专有/保密源代码,未经许可禁止任何人通过任何
 * 渠道使用、修改源代码.
 * 日期 2017/8/14 16:19
 */

/**
 * @company: 甘肃诚诚网络技术有限公司
 * @project: RentCar
 * @package: com.example.administrator.rentcar.activity
 * @version: V1.0
 * @author: 张吉岗
 * @date: 2017/8/14 16:19
 * @description: <p>
 * <p>
 * </p>
 */

public class MyApplication extends Application {
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase database;
    private DaoSession session;
    private static MyApplication instance;


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mHelper = new DaoMaster.DevOpenHelper(this, "test_db");
        database = mHelper.getWritableDatabase();
        session = new DaoMaster(database).newSession();
    }

    public DaoSession getSession() {
        return session;
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }
}
