package com.bruceewu.pinsetter;


import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    public static void show(String content) {
        Toast toast = Toast.makeText(App.Companion.getInstance(), content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
