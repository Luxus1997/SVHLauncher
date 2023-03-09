package com.haoyue.svhlauncher.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class IActivity extends Activity {

    public Activity P;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.e("IActivity", ">>> onCreate:");
        this.P = this;
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.e("IActivity", ">>> onDestroy:");
    }
}