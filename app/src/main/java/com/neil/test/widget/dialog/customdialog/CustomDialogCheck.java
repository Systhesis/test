package com.neil.test.widget.dialog.customdialog;
/**
 * @author zhongnan1
 * @time 2021/5/11 13:59
 *
 */
public class CustomDialogCheck {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0 || str.trim().length() == 0 || str.equalsIgnoreCase("null");
    }

    public static String isNull(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String isNull(String str, final String defaul) {
        return isEmpty(str) ? defaul : str;
    }

    public static boolean isNullObj(Object o) {
        return o == null;
    }


}
