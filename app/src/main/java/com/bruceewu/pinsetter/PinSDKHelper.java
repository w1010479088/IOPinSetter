package com.bruceewu.pinsetter;

import android.content.Context;

public class PinSDKHelper {
    private final IGPIO mIOControl;

    public PinSDKHelper(Context context) {
        mIOControl = IGPIO.Companion.newInstance(context);
    }

    public boolean get(int num) {
        try {
            return mIOControl.read(num);
        } catch (Exception ex) {
            logError(ex);
            return false;
        }
    }

    public void set(int num, boolean high) {
        try {
            mIOControl.set(num, high);
        } catch (Exception ex) {
            logError(ex);
        }
    }

    private void logError(Exception ex) {
        LogUtils.Companion.log("set Error = " + ex.getMessage());
    }
}
