package com.wq.demo.widget;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by qiangwang on 7/21/17.
 */

public class CustomToast {
    private static Toast mToast;
    public static void show(Context context, String msg){
        if(mToast != null){
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
