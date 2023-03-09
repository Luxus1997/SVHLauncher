package android_serialport_api;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private static final String TAG = "SerialPort";

    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    static {
        try {
            System.loadLibrary("svhs_port");
        } catch (Exception localException) {
            Log.e(TAG, localException.getLocalizedMessage(), localException);
        }
    }

    // JNI
    private native static FileDescriptor open(String path, int baudrate, int flags);

    public boolean SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {
        Log.e(TAG, "SerialPort >>> device.getAbsolutePath() ==" + device.getAbsolutePath());
        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            Log.e(TAG, "no access permission");
            try {
                Process su = Runtime.getRuntime().exec("/system/bin/su");
                String cmd = "chmod 777 " + device.getAbsolutePath() + "\n" + "exit\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw new SecurityException();
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        } else {
            Log.e(TAG, "have access permission");
        }
        System.out.println(device.getAbsolutePath() + "==============================");
        mFd = open(device.getAbsolutePath(), baudrate, flags);
        Log.e(TAG, "SerialPort >>> mFd ==" + mFd);
        if (mFd == null) {
            Log.e(TAG, "native open returns null");
            throw new IOException();
        }
        mFileInputStream = new FileInputStream(mFd);
        mFileOutputStream = new FileOutputStream(mFd);
        return true;
    }

    // Getters and setters
    public InputStream getInputStream() {
        return mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return mFileOutputStream;
    }

    public native void close();

}