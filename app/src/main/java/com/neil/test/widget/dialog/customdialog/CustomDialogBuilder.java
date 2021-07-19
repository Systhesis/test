//package com.neil.test.widget.dialog.customdialog;
//
//import android.content.Context;
//
//import androidx.annotation.DrawableRes;
//import androidx.annotation.StringRes;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.neil.test.widget.dialog.customdialog.dialogcallback.CancelClick;
//import com.neil.test.widget.dialog.customdialog.dialogcallback.ConfirmClick;
//
//
///**
// * @author zhongnan1
// * @time 2021/5/11 13:58
// *
// */
//public class CustomDialogBuilder {
//    String strTitle, strMsg, strHint, strConfirm, strCancle;
//    RecyclerView.Adapter adapter;
//
//    int iconRes;
//    boolean password;//密码模式
//    boolean cancelable = false;
//    Context context;
//    CancelClick cancelClick;
//    ConfirmClick confirmClick;
//    CustomDialog customDialog;
//
//    public CustomDialogBuilder(Context context) {
//        iconRes = -1;
//        strTitle = "";
//        strMsg = "";
//        strHint = "";
//        strConfirm = "";
//        strCancle = "";
//        password = false;
//        confirmClick = null;
//        cancelClick = null;
//        this.context = context;
//    }
//
//    /**
//     * @apiNote 不调用、或传入空 则自动处理
//     * 开始构建对话框
//     */
//    public CustomDialog build() {
//        if(customDialog != null) {
//            if(customDialog.isShowing()) {
//                customDialog.cancel();
//            }
//        }
//        customDialog = new CustomDialog(this);
//        return customDialog;
//    }
//
//    public void show() {
//        if(customDialog != null) {
//            if(customDialog.isShowing()) {
//                customDialog.cancel();
//            }
//            customDialog.show();
//        }
//    }
//
//    /**
//     * @param cancelable 能否返回
//     * @apiNote 不调用、或传入空 则不显示该区域
//     */
//    public CustomDialogBuilder cancelable(boolean cancelable) {
//        this.cancelable = cancelable;
//        return this;
//    }
//
//    /**
//     * @param title 标题
//     * @apiNote 不调用、或传入空 则不显示该区域
//     */
//    public CustomDialogBuilder title(String title) {
//        this.strTitle = title;
//        return this;
//    }
//
//    public CustomDialogBuilder title(@StringRes int resId) {
//        this.strTitle = context.getString(resId);
//        return this;
//    }
//
//    public CustomDialogBuilder recyclerView(RecyclerView.Adapter adapter) {
//        this.adapter = adapter;
//
//        return this;
//    }
//
//    /**
//     * @param msg 通知内容
//     * @apiNote 不调用、或传入空 则不显示该区域
//     */
//    public CustomDialogBuilder msg(String msg) {
//        this.strMsg = msg;
//        return this;
//    }
//
//    public CustomDialogBuilder msg(@StringRes int resId) {
//        this.strMsg = context.getString(resId);
//        return this;
//    }
//
//    /**
//     * @param hint 输入框提示
//     * @apiNote 不调用、或传入空 则不显示该区域
//     */
//    public CustomDialogBuilder input(String hint) {
//        this.strHint = hint;
//        return this;
//    }
//
//    /**
//     * @param hint     输入框提示
//     * @param password 是否是密码模式
//     * @apiNote 不调用、或传入空 则不显示该区域
//     */
//    public CustomDialogBuilder input(String hint, boolean password) {
//        this.strHint = hint;
//        this.password = password;
//        return this;
//    }
//
//    /**
//     * @param icon 显示图标
//     * @apiNote 不调用、或-1 则不显示该区域
//     */
//    public CustomDialogBuilder icon(@DrawableRes int icon) {
//        this.iconRes = icon;
//        return this;
//    }
//
//    /**
//     * @apiNote 显示两个默认按钮
//     * 两个按钮
//     */
//    public CustomDialogBuilder button() {
//        this.strConfirm = context.getString(R.string.dialog_confirm);
//        this.strCancle = context.getString(R.string.dialog_cancel);
//        return this;
//    }
//
//    /**
//     * @param confirm 取消按钮
//     * @apiNote 不调用、或传入空 则不显示该区域
//     * 一个按钮
//     */
//    public CustomDialogBuilder button(String confirm) {
//        this.strConfirm = confirm;
//        return this;
//    }
//
//    public CustomDialogBuilder button(@StringRes int resId) {
//        this.strConfirm = context.getString(resId);
//        return this;
//    }
//
//    /**
//     * @param confirm 确认按钮
//     * @param cancle  取消按钮
//     * @apiNote 不调用、或传入空 则不显示该区域
//     * 两个按钮
//     */
//    public CustomDialogBuilder button(String confirm, String cancle) {
//        this.strConfirm = confirm;
//        this.strCancle = cancle;
//        return this;
//    }
//
//    public CustomDialogBuilder button(@StringRes int confirmResId, @StringRes int cancelResId) {
//        this.strConfirm = context.getString(confirmResId);
//        this.strCancle = context.getString(cancelResId);
//        return this;
//    }
//
//    /**
//     * @param confirmClick 确认按钮点击事件
//     * @apiNote 不调用、或传入空 则自动处理
//     */
//    public CustomDialogBuilder confirmClick(ConfirmClick confirmClick) {
//        this.confirmClick = confirmClick;
//        return this;
//    }
//
//    /**
//     * @param cancelClick 取消按钮点击事件
//     * @apiNote 不调用、或传入空 则自动处理
//     * 一个按钮
//     */
//    public CustomDialogBuilder cancelClick(CancelClick cancelClick) {
//        this.cancelClick = cancelClick;
//        return this;
//    }
//}
