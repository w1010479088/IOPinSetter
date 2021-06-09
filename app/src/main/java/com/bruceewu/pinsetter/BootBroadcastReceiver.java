package com.bruceewu.pinsetter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

public class BootBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(ACTION, intent.getAction())) {
            PinSDKHolder.INSTANCE.init();
        }
    }
}
