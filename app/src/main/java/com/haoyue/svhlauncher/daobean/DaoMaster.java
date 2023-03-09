package com.haoyue.svhlauncher.daobean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

public class DaoMaster extends AbstractDaoMaster {

    public static final int SCHEMA_VERSION = 1;

    public DaoMaster(SQLiteDatabase sqLiteDatabase) {
        this((Database)new StandardDatabase(sqLiteDatabase));
    }

    public DaoMaster(Database database) {
        super(database, SCHEMA_VERSION);
        registerDaoClass(PhysicalsDao.class);
        registerDaoClass(RegisterDao.class);
    }

    public static void createAllTables(Database database, boolean b) {
        PhysicalsDao.createTable(database, b);
        RegisterDao.createTable(database, b);
    }

    public static void dropAllTables(Database database, boolean b) {
        PhysicalsDao.dropTable(database, b);
        RegisterDao.dropTable(database, b);
    }

    public static DaoSession newDevSession(Context context, String s) {
        return new DaoMaster(new DevOpenHelper(context, s).getWritableDb()).newSession();
    }

    @Override
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    @Override
    public DaoSession newSession(IdentityScopeType identityScopeType) {
        return new DaoSession(db, identityScopeType, daoConfigMap);
    }

    public static class DevOpenHelper extends OpenHelper {

        public DevOpenHelper(Context context, String s) {
            super(context, s);
        }

        public DevOpenHelper(Context context, String s, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, s, cursorFactory);
        }

        @Override
        public void onUpgrade(Database database, int n, int n2) {
            Log.i("greenDAO", "Upgrading schema from version " + n + " to " + n2 + " by dropping all tables");
            DaoMaster.dropAllTables(database, true);
            ((OpenHelper)this).onCreate(database);
        }
    }

    public abstract static class OpenHelper extends DatabaseOpenHelper {

        public OpenHelper(Context context, String s) {
            super(context, s, 2);
        }

        public OpenHelper(Context context, String s, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, s, cursorFactory, 2);
        }

        @Override
        public void onCreate(Database database) {
            Log.i("greenDAO", "Creating tables for schema version 2");
            DaoMaster.createAllTables(database, true);
        }
    }

}