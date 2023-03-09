package com.haoyue.svhlauncher.utils;

import android.os.Build;
import android.view.View;
import android.view.Window;

public class DialogUtils {

    public static void clearFocusNotAle(Window window) {
        window.clearFlags(8);
    }

    public static void focusNotAle(Window window) {
        window.setFlags(8, 8);
    }

    public static void hideNavigationBar(final Window window) {
        window.getDecorView().setSystemUiVisibility(2);
        window.getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            public void onSystemUiVisibilityChange(int systemUiVisibility) {
                if (Build.VERSION.SDK_INT >= 19) {
                    systemUiVisibility = (0x706 | 0x1000);
                }
                else {
                    systemUiVisibility = (0x706 | 0x1);
                }
                window.getDecorView().setSystemUiVisibility(systemUiVisibility);
            }
        });
    }
}
