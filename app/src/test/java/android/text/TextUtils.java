package android.text;

import android.support.annotation.Nullable;

/**
 * Created by stokey on 2018/6/17.
 */

public class TextUtils {
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
