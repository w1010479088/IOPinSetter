package com.bruceewu.pinsetter;

import android.app.smdt.SmdtManager;
import android.content.Context;

public class PinSDKHelper {
    private final SmdtManager manager;

    public PinSDKHelper(Context context) {
        manager = SmdtManager.create(context);
        if (manager == null) {
            LogUtils.Companion.log("PinSdk init fail");
        } else {
            LogUtils.Companion.log("PinSdk init success");
        }
    }

    public boolean get(int num) {
        try {
            int readValue = manager.smdtReadExtrnalGpioValue(num);
            LogUtils.Companion.log(String.format("PinSdk get %s  = %s", String.valueOf(num), String.valueOf(readValue)));
            return readValue == 1;
        } catch (Exception ex) {
            LogUtils.Companion.log("get Error = " + ex.getMessage());
            return false;
        }
    }

    public void set(int num, boolean high) {
        try {
            int setResult = manager.smdtSetExtrnalGpioValue(num, high);
            LogUtils.Companion.log(String.format("PinSdk set %s  = %s , result = %s", String.valueOf(num), String.valueOf(high), String.valueOf(setResult)));
        } catch (Exception ex) {
            LogUtils.Companion.log("set Error = " + ex.getMessage());
        }
    }
}
