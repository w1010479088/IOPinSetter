package com.bruceewu.pinsetter;

import android.app.smdt.SmdtManager;

public class PinSDKHelper {
    private final SmdtManager manager;

    public PinSDKHelper() {
        manager = SmdtManager.create(App.Companion.getInstance());
        if (manager == null) {
            LogUtils.Companion.log("PinSdk init fail");
        } else {
            LogUtils.Companion.log("PinSdk init success");
        }
    }

    public boolean get(int num) {
        try {
            return manager.smdtReadExtrnalGpioValue(num) == 1;
        } catch (Exception ex) {
            LogUtils.Companion.log(ex.getMessage());
        }
        return false;
    }

    public boolean set(int num, boolean high) {
        try {
            return manager.smdtSetExtrnalGpioValue(num, high) == 1;
        } catch (Exception ex) {
            LogUtils.Companion.log(ex.getMessage());
        }
        return false;
    }
}
