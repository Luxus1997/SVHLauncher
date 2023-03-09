package com.haoyue.svhlauncher.daobean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class RegisterDao extends AbstractDao<Register, Long> {

    public static final String TABLENAME = "register";

    public RegisterDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public RegisterDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, (AbstractDaoSession) daoSession);
    }

    public static void createTable(Database database, boolean b) {
        String s;
        if (b) {
            s = "IF NOT EXISTS ";
        } else {
            s = "";
        }
        database.execSQL("CREATE TABLE " + s + TABLENAME + " ("
                + "\"id\" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + "\"recordid\" TEXT,"
                + "\"devicernd\" TEXT,"
                + "\"name\" TEXT,"
                + "\"gender\" TEXT,"
                + "\"age\" TEXT,"
                + "\"formname\" TEXT);"
        );
    }

    public static void dropTable(Database database, boolean b) {
        StringBuilder append = new StringBuilder().append("DROP TABLE ");
        String s;
        if (b) {
            s = "IF EXISTS ";
        } else {
            s = "";
        }
        database.execSQL(append.append(s).append("\"register\"").toString());
    }

    @Override
    protected final void bindValues(SQLiteStatement sqLiteStatement, Register register) {
        sqLiteStatement.clearBindings();
        Long id = register.getId();
        if (id != null) {
            sqLiteStatement.bindLong(1, (long) id);
        }
        String recordid = register.getRecord_id();
        if (recordid != null) {
            sqLiteStatement.bindString(2, recordid);
        }
        String devicernd = register.getDevicernd();
        if (devicernd != null) {
            sqLiteStatement.bindString(3, devicernd);
        }
        String name = register.getName();
        if (name != null) {
            sqLiteStatement.bindString(4, name);
        }
        String gender = register.getGender();
        if (gender != null) {
            sqLiteStatement.bindString(5, gender);
        }
        String age = register.getAge();
        if (age != null) {
            sqLiteStatement.bindString(6, age);
        }
        String formname = register.getFormname();
        if (formname != null) {
            sqLiteStatement.bindString(7, formname);
        }
    }

    @Override
    protected final void bindValues(DatabaseStatement databaseStatement, Register register) {
        databaseStatement.clearBindings();
        Long id = register.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id);
        }
        String recordid = register.getRecord_id();
        if (recordid != null) {
            databaseStatement.bindString(2, recordid);
        }
        String devicernd = register.getDevicernd();
        if (devicernd != null) {
            databaseStatement.bindString(3, devicernd);
        }
        String name = register.getName();
        if (name != null) {
            databaseStatement.bindString(4, name);
        }
        String gender = register.getGender();
        if (gender != null) {
            databaseStatement.bindString(5, gender);
        }
        String age = register.getAge();
        if (age != null) {
            databaseStatement.bindString(6, age);
        }
        String formname = register.getFormname();
        if (formname != null) {
            databaseStatement.bindString(7, formname);
        }
    }

    public Long getKey(Register register) {
        if (register != null) {
            return register.getId();
        }
        return null;
    }

    public boolean hasKey(Register register) {
        return register.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public Register readEntity(Cursor cursor, int n) {
        Long id;
        if (cursor.isNull(n + 0)) {
            id = null;
        } else {
            id = cursor.getLong(n + 0);
        }
        String recordid;
        if (cursor.isNull(n + 1)) {
            recordid = null;
        } else {
            recordid = cursor.getString(n + 1);
        }
        String devicernd;
        if (cursor.isNull(n + 2)) {
            devicernd = null;
        } else {
            devicernd = cursor.getString(n + 2);
        }
        String name;
        if (cursor.isNull(n + 3)) {
            name = null;
        } else {
            name = cursor.getString(n + 3);
        }
        String gender;
        if (cursor.isNull(n + 4)) {
            gender = null;
        } else {
            gender = cursor.getString(n + 4);
        }
        String age;
        if (cursor.isNull(n + 5)) {
            age = null;
        } else {
            age = cursor.getString(n + 5);
        }
        String formname;
        if (cursor.isNull(n + 6)) {
            formname = null;
        } else {
            formname = cursor.getString(n + 6);
        }
        return new Register(id, recordid, devicernd, name, gender, age, formname);
    }

    public void readEntity(Cursor cursor, Register register, int n) {
        Long id;
        if (cursor.isNull(n + 0)) {
            id = null;
        } else {
            id = cursor.getLong(n + 0);
        }
        register.setId(id);
        String recordid;
        if (cursor.isNull(n + 1)) {
            recordid = null;
        } else {
            recordid = cursor.getString(n + 1);
        }
        register.setRecord_id(recordid);
        String devicernd;
        if (cursor.isNull(n + 2)) {
            devicernd = null;
        } else {
            devicernd = cursor.getString(n + 2);
        }
        register.setDevicernd(devicernd);
        String name;
        if (cursor.isNull(n + 3)) {
            name = null;
        } else {
            name = cursor.getString(n + 3);
        }
        register.setName(name);
        String gender;
        if (cursor.isNull(n + 4)) {
            gender = null;
        } else {
            gender = cursor.getString(n + 4);
        }
        register.setGender(gender);
        String age;
        if (cursor.isNull(n + 5)) {
            age = null;
        } else {
            age = cursor.getString(n + 5);
        }
        register.setAge(age);
        String formname;
        if (cursor.isNull(n + 6)) {
            formname = null;
        } else {
            formname = cursor.getString(n + 6);
        }
        register.setFormname(formname);
    }

    public Long readKey(Cursor cursor, int n) {
        if (cursor.isNull(n + 0)) {
            return null;
        }
        return cursor.getLong(n + 0);
    }

    @Override
    protected final Long updateKeyAfterInsert(Register register, long n) {
        register.setId(n);
        return n;
    }

    public static class Properties {

        public static final Property id;

        public static final Property recordid;
        public static final Property devicernd;
        public static final Property name;
        public static final Property gender;
        public static final Property age;
        public static final Property formname;

        static {
            id = new Property(0, Long.class, "id", true, "id");

            recordid = new Property(1, String.class, "recordid", false, "recordid");
            devicernd = new Property(2, String.class, "devicernd", false, "devicernd");
            name = new Property(3, String.class, "name", false, "name");
            gender = new Property(4, String.class, "gender", false, "gender");
            age = new Property(5, String.class, "age", false, "age");
            formname = new Property(6, String.class, "formname", false, "formname");
        }
    }

}