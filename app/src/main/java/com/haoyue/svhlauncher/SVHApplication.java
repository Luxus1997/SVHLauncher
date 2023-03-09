package com.haoyue.svhlauncher;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.haoyue.svhlauncher.daobean.DaoMaster;
import com.haoyue.svhlauncher.daobean.DaoSession;
import com.haoyue.svhlauncher.serialport.SerialPortUtil;
import com.haoyue.svhlauncher.utils.SvhVolume;

import java.io.File;

public class SVHApplication extends Application {

    private static Context mContext;
    private static SVHApplication instance;
    private SvhVolume svhVolume;

    private SerialPortUtil serialPortUtil;
    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SVHLuncher/";

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = this;

        svhVolume = new SvhVolume(mContext);
        CrashHandler.getInstance().init(this);
        serialPortUtil = new SerialPortUtil();
        serialPortUtil.openSerialPort();

        setupDatabase();
        createFile();
    }

    public static SVHApplication getInstance() {
        return SVHApplication.instance;
    }

    public SerialPortUtil getSerialPortUtil() {
        return serialPortUtil;
    }

    public SvhVolume getSvhVolume() {
        return this.svhVolume;
    }

    public void SvhClick() {
        svhVolume.playMusic(R.raw.click_music);
    }

    private static DaoSession daoSession;

    public static DaoSession getDaoInstant() {
        return SVHApplication.daoSession;
    }

    private void setupDatabase() {
        SVHApplication.daoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "physicals.db", null).getWritableDatabase()).newSession();
    }

    public void deleSQL() {
        DaoMaster.dropAllTables(SVHApplication.daoSession.getDatabase(), true);
        DaoMaster.createAllTables(SVHApplication.daoSession.getDatabase(), true);
    }

    public void createFile() {
        File file = new File(PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

}
