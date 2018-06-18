package com.stokey.androidunittest.util;

import android.text.TextUtils;

/**
 *
 * @author stokey
 * @date 2018/6/17
 */

public class LoginUtil {
    public static boolean checkLoginName(String name) {
        if (! TextUtils.isEmpty(name) && name.length() > 4) {
            return true;
        }
        return false;
    }

    public static boolean checkLoginPsw(String psw) {
        // TODO 未增加复杂逻辑
        if (! TextUtils.isEmpty(psw) && psw.length() > 6) {
            return true;
        }
        return false;
    }
}
