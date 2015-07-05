package com.pingbits.itchack;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;
import com.pingbits.greendao.DaoMaster;
import com.pingbits.greendao.DaoSession;
import com.pingbits.greendao.OrdderDao;
import com.pingbits.greendao.OrderDao;

public class MyApplication extends Application {

    public OrdderDao orderDao;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "mydb2", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession mDaoSession = daoMaster.newSession();
        orderDao = mDaoSession.getOrdderDao();

        Parse.initialize(this, "9B6neGd9ycrpOjK3CBBcb87QrzysZWlRAnnTcmnL", "2OxOuaCtaryzNlm6zTDHeZXS3haRHGgWL7l7fJXm");
//        ParseInstallation.getCurrentInstallation().saveInBackground();
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }

    public OrdderDao getOrderDao() {
        return orderDao;
    }
}
