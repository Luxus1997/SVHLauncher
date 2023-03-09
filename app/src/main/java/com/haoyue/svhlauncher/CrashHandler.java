package com.haoyue.svhlauncher;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import com.haoyue.svhlauncher.utils.ToastUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String FILE_NAME = "SVHLuncher";
    private static final String FILE_SUFFIX = ".log";
    private static final String PATH;
    private static final String TAG = "CrashHandler";
    private static CrashHandler mInstance;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;
    private SimpleDateFormat simpleDateFormat;

    static {
        PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SVHLuncher/";
        CrashHandler.mInstance = new CrashHandler();
    }

    public CrashHandler() {
        this.simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    private void dumpDeviceInfo(PrintWriter printWriter) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        printWriter.println("App Version:" + packageInfo.versionName + "_" + packageInfo.versionCode);
        printWriter.println("Android OS Version:" + Build.VERSION.RELEASE + "_" + Build.VERSION.SDK_INT);
        printWriter.println("Vender:" + Build.MANUFACTURER);
        printWriter.println("Mode:" + Build.MODEL);
        printWriter.println("CPU API" + Build.CPU_ABI);
    }

    private void dumpExceptionToSDcard(final Throwable t) {
        File file = new File(CrashHandler.PATH);
        if (!file.exists()) {
            file.mkdir();
        }
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        File file1 = new File(CrashHandler.PATH + FILE_NAME + format + FILE_SUFFIX);
        try {
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file1)));
            printWriter.println(format);
            dumpDeviceInfo(printWriter);
            t.printStackTrace(printWriter);
            printWriter.close();
        } catch (Exception ex) {
            Log.e(TAG, "写入失败" + ex);
        }
    }

    public static CrashHandler getInstance() {
        return CrashHandler.mInstance;
    }

    private boolean handleException(final Throwable t) {
        if (t == null) {
            return false;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                ToastUtils.showToast(mContext, mContext.getString(R.string.error_exit));
                Looper.loop();
            }
        }).start();
        dumpExceptionToSDcard(t);
        return true;
    }

    public void init(Context context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = context.getApplicationContext();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable t) {
        if (!handleException(t) && mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, t);
            return;
        }
        try {
            Thread.sleep(500L);
            Process.killProcess(Process.myPid());
            System.exit(1);
        } catch (InterruptedException ex) {
            Log.e(TAG, "error:", ex);
        }
    }

}