package com.haoyue.svhlauncher.utils;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.text.DecimalFormat;

public class StringUtils {

    public static final String UTF_8 = "utf-8";

    public static String formatFileSize(long len) {
        return formatFileSize(len, false);
    }

    public static String formatFileSize(long len, boolean keepZero) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        DecimalFormat decimalFormat2 = new DecimalFormat("#.0");
        if (len < 1024L) {
            return String.valueOf(len + "B");
        }
        if (len < 10240L) {
            return String.valueOf(100L * len / 1024L / 100.0f) + "KB";
        }
        if (len < 102400L) {
            return String.valueOf(10L * len / 1024L / 10.0f) + "KB";
        }
        if (len < 1048576L) {
            return String.valueOf(len / 1024L) + "KB";
        }
        if (len < 10485760L) {
            if (keepZero) {
                return String.valueOf(decimalFormat.format(100L * len / 1024L / 1024L / 100.0f)) + "MB";
            }
            return String.valueOf(100L * len / 1024L / 1024L / 100.0f) + "MB";
        }
        else if (len < 104857600L) {
            if (keepZero) {
                return String.valueOf(decimalFormat2.format(10L * len / 1024L / 1024L / 10.0f)) + "MB";
            }
            return String.valueOf(10L * len / 1024L / 1024L / 10.0f) + "MB";
        }
        else {
            if (len < 1073741824L) {
                return String.valueOf(len / 1024L / 1024L) + "MB";
            }
            return String.valueOf(100L * len / 1024L / 1024L / 1024L / 100.0f) + "GB";
        }
    }

    public static CharSequence getHighLightText(String content, int color, int start, int end) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        if (start < 0) {
            start = 0;
        }
        if (end > content.length()) {
            end = content.length();
        }
        SpannableString spannableString = new SpannableString(content);
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, 33);
        return spannableString;
    }

    public static boolean isEmpty(String value) {
        return value == null || "".equalsIgnoreCase(value.trim()) || "null".equalsIgnoreCase(value.trim());
    }

    public static boolean isEquals(String... array) {
        String s = null;
        for (int i = 0; i < array.length; ++i) {
            String s2 = array[i];
            if (isEmpty(s2) || (s != null && !s2.equalsIgnoreCase(s))) {
                return false;
            }
            s = s2;
        }
        return true;
    }

    public static String stringReplace(String str) {
        return str.replace("\"", "");
    }

}