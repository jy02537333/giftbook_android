package com.zxw.giftbook.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**   界面，软键盘工具
 * Created by Administrator on 2016/11/10.
 */

public class KeyboardTools {
    /**
     *  软件盘收起
     * @param context
     */
    public static void anastole(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0) ;
        }
    }
}
