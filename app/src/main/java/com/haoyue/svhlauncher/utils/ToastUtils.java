package com.haoyue.svhlauncher.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.haoyue.svhlauncher.R;
import com.haoyue.svhlauncher.SVHApplication;

public class ToastUtils {

    private static Toast mToast;

    public static void showToast(Context context, String text) {
        if (ToastUtils.mToast == null) {
            ToastUtils.mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }
        View inflate = LayoutInflater.from(SVHApplication.getInstance()).inflate(R.layout.toast, null);
        ((TextView) inflate.findViewById(R.id.toast)).setText(text);
        ToastUtils.mToast.setView(inflate);
        ToastUtils.mToast.show();
    }

    public static boolean isEmpty(String s) {
        return s == null || "".equalsIgnoreCase(s.trim()) || "null".equalsIgnoreCase(s.trim());
    }

}