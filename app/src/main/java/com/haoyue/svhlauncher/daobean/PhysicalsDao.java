package com.haoyue.svhlauncher.daobean;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

public class PhysicalsDao extends AbstractDao<Physicals, Long> {

    public static final String TABLENAME = "physicals";

    public PhysicalsDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public PhysicalsDao(DaoConfig daoConfig, DaoSession daoSession) {
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
                + "\"heightCm\" Double,"
                + "\"weightKg\" Double,"
                + "\"htBMI\" Double,"
                + "\"high_pressure\" Double,"
                + "\"low_pressure\" Double,"
                + "\"heart_rate\" Double,"
                + "\"temp7\" Double,"
                + "\"temp8\" Double,"
                + "\"temp9\" Double,"
                + "\"temp10\" Double,"
                + "\"temp11\" Double,"
                + "\"temp12\" Double);"
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
        database.execSQL(append.append(s).append("\"physicals\"").toString());
    }

    @Override
    protected final void bindValues(SQLiteStatement sqLiteStatement, Physicals physicals) {
        sqLiteStatement.clearBindings();
        Long id = physicals.getId();
        if (id != null) {
            sqLiteStatement.bindLong(1, (long) id);
        }
        Double heightCm = physicals.getHeightCm();
        if (heightCm != null) {
            sqLiteStatement.bindDouble(2, heightCm);
        }
        Double weightKg = physicals.getWeightKg();
        if (weightKg != null) {
            sqLiteStatement.bindDouble(3, weightKg);
        }
        Double htBMI = physicals.getHtBMI();
        if (htBMI != null) {
            sqLiteStatement.bindDouble(4, htBMI);
        }
        Double high_pressure = physicals.getHigh_pressure();
        if (high_pressure != null) {
            sqLiteStatement.bindDouble(5, high_pressure);
        }
        Double low_pressure = physicals.getLow_pressure();
        if (low_pressure != null) {
            sqLiteStatement.bindDouble(6, low_pressure);
        }
        Double heart_rate = physicals.getHeart_rate();
        if (heart_rate != null) {
            sqLiteStatement.bindDouble(7, heart_rate);
        }
        Double temp7 = physicals.getTemp7();
        if (temp7 != null) {
            sqLiteStatement.bindDouble(8, temp7);
        }
        Double temp8 = physicals.getTemp8();
        if (temp8 != null) {
            sqLiteStatement.bindDouble(9, temp8);
        }
        Double temp9 = physicals.getTemp9();
        if (temp9 != null) {
            sqLiteStatement.bindDouble(10, temp9);
        }
        Double temp10 = physicals.getTemp10();
        if (temp10 != null) {
            sqLiteStatement.bindDouble(11, temp10);
        }
        Double temp11 = physicals.getTemp11();
        if (temp11 != null) {
            sqLiteStatement.bindDouble(12, temp11);
        }
        Double temp12 = physicals.getTemp12();
        if (temp12 != null) {
            sqLiteStatement.bindDouble(13, temp12);
        }
    }

    @Override
    protected final void bindValues(DatabaseStatement databaseStatement, Physicals physicals) {
        databaseStatement.clearBindings();
        Long id = physicals.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id);
        }
        Double heightCm = physicals.getHeightCm();
        if (heightCm != null) {
            databaseStatement.bindDouble(2, heightCm);
        }
        Double weightKg = physicals.getWeightKg();
        if (weightKg != null) {
            databaseStatement.bindDouble(3, weightKg);
        }
        Double htBMI = physicals.getHtBMI();
        if (htBMI != null) {
            databaseStatement.bindDouble(4, htBMI);
        }
        Double high_pressure = physicals.getHigh_pressure();
        if (high_pressure != null) {
            databaseStatement.bindDouble(5, high_pressure);
        }
        Double low_pressure = physicals.getLow_pressure();
        if (low_pressure != null) {
            databaseStatement.bindDouble(6, low_pressure);
        }
        Double heart_rate = physicals.getHeart_rate();
        if (heart_rate != null) {
            databaseStatement.bindDouble(7, heart_rate);
        }
        Double temp7 = physicals.getTemp7();
        if (temp7 != null) {
            databaseStatement.bindDouble(8, temp7);
        }
        Double temp8 = physicals.getTemp8();
        if (temp8 != null) {
            databaseStatement.bindDouble(9, temp8);
        }
        Double temp9 = physicals.getTemp9();
        if (temp9 != null) {
            databaseStatement.bindDouble(10, temp9);
        }
        Double temp10 = physicals.getTemp10();
        if (temp10 != null) {
            databaseStatement.bindDouble(11, temp10);
        }
        Double temp11 = physicals.getTemp11();
        if (temp11 != null) {
            databaseStatement.bindDouble(12, temp11);
        }
        Double temp12 = physicals.getTemp12();
        if (temp12 != null) {
            databaseStatement.bindDouble(13, temp12);
        }
    }

    public Long getKey(Physicals physicals) {
        if (physicals != null) {
            return physicals.getId();
        }
        return null;
    }

    public boolean hasKey(Physicals physicals) {
        return physicals.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }

    public Physicals readEntity(Cursor cursor, int n) {
        Long id;
        if (cursor.isNull(n + 0)) {
            id = null;
        } else {
            id = cursor.getLong(n + 0);
        }
        Double heightCm;
        if (cursor.isNull(n + 1)) {
            heightCm = null;
        } else {
            heightCm = cursor.getDouble(n + 1);
        }
        Double weightKg;
        if (cursor.isNull(n + 2)) {
            weightKg = null;
        } else {
            weightKg = cursor.getDouble(n + 2);
        }
        Double htBMI;
        if (cursor.isNull(n + 3)) {
            htBMI = null;
        } else {
            htBMI = cursor.getDouble(n + 3);
        }
        Double high_pressure;
        if (cursor.isNull(n + 4)) {
            high_pressure = null;
        } else {
            high_pressure = cursor.getDouble(n + 4);
        }
        Double low_pressure;
        if (cursor.isNull(n + 5)) {
            low_pressure = null;
        } else {
            low_pressure = cursor.getDouble(n + 5);
        }
        Double heart_rate;
        if (cursor.isNull(n + 6)) {
            heart_rate = null;
        } else {
            heart_rate = cursor.getDouble(n + 6);
        }
        Double temp7;
        if (cursor.isNull(n + 7)) {
            temp7 = null;
        } else {
            temp7 = cursor.getDouble(n + 7);
        }
        Double temp8;
        if (cursor.isNull(n + 8)) {
            temp8 = null;
        } else {
            temp8 = cursor.getDouble(n + 8);
        }
        Double temp9;
        if (cursor.isNull(n + 9)) {
            temp9 = null;
        } else {
            temp9 = cursor.getDouble(n + 9);
        }
        Double temp10;
        if (cursor.isNull(n + 10)) {
            temp10 = null;
        } else {
            temp10 = cursor.getDouble(n + 10);
        }
        Double temp11;
        if (cursor.isNull(n + 11)) {
            temp11 = null;
        } else {
            temp11 = cursor.getDouble(n + 11);
        }
        Double temp12;
        if (cursor.isNull(n + 12)) {
            temp12 = null;
        } else {
            temp12 = cursor.getDouble(n + 12);
        }
        return new Physicals(id, heightCm, weightKg, htBMI, high_pressure, low_pressure, heart_rate, temp7, temp8, temp9, temp10, temp11, temp12);
    }

    public void readEntity(Cursor cursor, Physicals physicals, int n) {
        Long id;
        if (cursor.isNull(n + 0)) {
            id = null;
        } else {
            id = cursor.getLong(n + 0);
        }
        physicals.setId(id);
        Double heightCm;
        if (cursor.isNull(n + 1)) {
            heightCm = null;
        } else {
            heightCm = cursor.getDouble(n + 1);
        }
        physicals.setHeightCm(heightCm);
        Double weightKg;
        if (cursor.isNull(n + 2)) {
            weightKg = null;
        } else {
            weightKg = cursor.getDouble(n + 2);
        }
        physicals.setWeightKg(weightKg);
        Double htBMI;
        if (cursor.isNull(n + 3)) {
            htBMI = null;
        } else {
            htBMI = cursor.getDouble(n + 3);
        }
        physicals.setHtBMI(htBMI);
        Double high_pressure;
        if (cursor.isNull(n + 4)) {
            high_pressure = null;
        } else {
            high_pressure = cursor.getDouble(n + 4);
        }
        physicals.setHigh_pressure(high_pressure);
        Double low_pressure;
        if (cursor.isNull(n + 5)) {
            low_pressure = null;
        } else {
            low_pressure = cursor.getDouble(n + 5);
        }
        physicals.setLow_pressure(low_pressure);
        Double heart_rate;
        if (cursor.isNull(n + 6)) {
            heart_rate = null;
        } else {
            heart_rate = cursor.getDouble(n + 6);
        }
        physicals.setHeart_rate(heart_rate);
        Double temp7;
        if (cursor.isNull(n + 7)) {
            temp7 = null;
        } else {
            temp7 = cursor.getDouble(n + 7);
        }
        physicals.setTemp7(temp7);
        Double temp8;
        if (cursor.isNull(n + 8)) {
            temp8 = null;
        } else {
            temp8 = cursor.getDouble(n + 8);
        }
        physicals.setTemp8(temp8);
        Double temp9;
        if (cursor.isNull(n + 9)) {
            temp9 = null;
        } else {
            temp9 = cursor.getDouble(n + 9);
        }
        physicals.setTemp9(temp9);
        Double temp10;
        if (cursor.isNull(n + 10)) {
            temp10 = null;
        } else {
            temp10 = cursor.getDouble(n + 10);
        }
        physicals.setTemp10(temp10);
        Double temp11;
        if (cursor.isNull(n + 11)) {
            temp11 = null;
        } else {
            temp11 = cursor.getDouble(n + 11);
        }
        physicals.setTemp11(temp11);
        Double temp12;
        if (cursor.isNull(n + 12)) {
            temp12 = null;
        } else {
            temp12 = cursor.getDouble(n + 12);
        }
        physicals.setTemp12(temp12);
    }

    public Long readKey(Cursor cursor, int n) {
        if (cursor.isNull(n + 0)) {
            return null;
        }
        return cursor.getLong(n + 0);
    }

    @Override
    protected final Long updateKeyAfterInsert(Physicals physicals, long n) {
        physicals.setId(n);
        return n;
    }

    public static class Properties {

        public static final Property id;

        public static final Property heightCm;
        public static final Property weightKg;
        public static final Property htBMI;
        public static final Property high_pressure;
        public static final Property low_pressure;
        public static final Property heart_rate;
        public static final Property temp7;
        public static final Property temp8;
        public static final Property temp9;
        public static final Property temp10;
        public static final Property temp11;
        public static final Property temp12;

        static {
            id = new Property(0, Long.class, "id", true, "id");

            heightCm = new Property(1, Double.class, "heightCm", false, "heightCm");
            weightKg = new Property(2, Double.class, "weightKg", false, "weightKg");
            htBMI = new Property(3, Double.class, "htBMI", false, "htBMI");
            high_pressure = new Property(4, Double.class, "high_pressure", false, "high_pressure");
            low_pressure = new Property(5, Double.class, "low_pressure", false, "low_pressure");
            heart_rate = new Property(6, Double.class, "heart_rate", false, "heart_rate");
            temp7 = new Property(7, Double.class, "temp7", false, "temp7");
            temp8 = new Property(8, Double.class, "temp8", false, "temp8");
            temp9 = new Property(9, Double.class, "temp9", false, "temp9");
            temp10 = new Property(10, Double.class, "temp10", false, "temp10");
            temp11 = new Property(11, Double.class, "temp11", false, "temp11");
            temp12 = new Property(12, Double.class, "temp12", false, "temp12");
        }
    }
}