package com.haoyue.svhlauncher.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.haoyue.svhlauncher.activity.ADActivity;

public class BootBroadcastReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            intent = new Intent(context, ADActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}