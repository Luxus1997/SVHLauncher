package com.haoyue.svhlauncher.utils;

import android.content.Context;
import android.content.pm.PackageManager;

public final class DeviceUtils {

    /**
     * 检验设备是否有摄像头
     */
    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            // this device has a camera1
            return true;
        } else {
            // no camera1 on this device
            return false;
        }
    }
}