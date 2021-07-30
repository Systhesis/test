//package com.neil.test.widget.dialog.dialog.customdialog;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.graphics.Color;
//import android.os.Handler;
//import android.text.Spannable;
//import android.text.SpannableString;
//import android.text.style.ForegroundColorSpan;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.jd.jdx.srs.ui.R;
//
//
///**
// * 自定义对话框
// * @author zhongnan1
// * @time 2021/5/8 14:18
// *
// */
//public class CustomDialog extends Dialog {
//
//    protected CustomDialogBuilder mBuilder;
//    protected int tickTime = 0;
//    protected Handler tickTimeHandler;
//    protected TextView confirm;
//    CustomDialog(CustomDialogBuilder builder) {
//        super(builder.context, R.style.customDialog);
//        this.mBuilder = builder;
//        this.tickTime = builder.tickSeconds + 1;
//        setContentView(R.layout.dialog_custom_dialog);
//        setCancelable(builder.cancelable);//是否可以通过返回键关闭
//        setCanceledOnTouchOutside(builder.cancelable);//是否可以点击外面关闭
////        getWindow().setBackgroundDrawableResource(R.color.T_all);
//        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        findViewById(R.id.dia_root).setOnClickListener(null);
//        if (builder.cancelable) {
//            findViewById(R.id.dia_root).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    clear();
//                }
//            });
//        }
//
//        //标题
//        TextView title = findViewById(R.id.dia_title);
//        //View lineTop = findViewById(R.id.dia_line_top);
//        if (CustomDialogCheck.isEmpty(builder.strTitle)) {
//            title.setVisibility(View.GONE);
//            //lineTop.setVisibility(View.GONE);
//        } else {
//            title.setVisibility(View.VISIBLE);
//            //lineTop.setVisibility(View.VISIBLE);
//            title.setText(builder.strTitle);
//        }
//
//        //关闭按钮
//        LinearLayout close = findViewById(R.id.dia_close);
//        close.setOnClickListener((v) -> {
//            clear();
//        });
//
//        //recyclerView
//        RecyclerView recyclerView = findViewById(R.id.dia_recycler);
//        if(CustomDialogCheck.isNullObj(builder.adapter)) {
//            recyclerView.setVisibility(View.GONE);
//        }else {
//            GridLayoutManager layoutManager  = new GridLayoutManager(this.getContext(),2);
//
//            recyclerView.setAdapter(builder.adapter);
//            recyclerView.setLayoutManager(layoutManager);
//            recyclerView.setVisibility(View.VISIBLE);
//            builder.adapter.notifyDataSetChanged();
//        }
//
//
//        //图标
//        ImageView icon = findViewById(R.id.dia_icon);
//        if (builder.iconRes == -1) {
//            icon.setVisibility(View.GONE);
//        } else {
//            icon.setVisibility(View.VISIBLE);
//            icon.setImageResource(builder.iconRes);
//        }
//        //通知内容
//        TextView msg = findViewById(R.id.dia_msg);
//        if (CustomDialogCheck.isEmpty(builder.strMsg)) {
//            msg.setVisibility(View.GONE);
//        } else {
//            msg.setText(builder.strMsg);
//            msg.setVisibility(View.VISIBLE);
//        }
////        //输入框
////        EditText edt = findViewById(R.id.dia_edt);
////        if (CustomDialogCheck.isEmpty(builder.strHint)) {
////            edt.setVisibility(View.GONE);
////        } else {
////            edt.setVisibility(View.VISIBLE);
////            edt.setHint(builder.strHint);
////            edt.setText("");
////            edt.setTransformationMethod(builder.password ? PasswordTransformationMethod.getInstance()
////                    : HideReturnsTransformationMethod.getInstance());
////        }
//        //取消、确认按钮:至少要显示一个
//        TextView cancel = findViewById(R.id.dia_cancel);
//        confirm = findViewById(R.id.dia_confirm);
//        View lineBottom = findViewById(R.id.dia_line_bottom);
//        if (!CustomDialogCheck.isEmpty(builder.strCancle) && !CustomDialogCheck.isEmpty(builder.strConfirm)) {
//            cancel.setVisibility(View.VISIBLE);
//            lineBottom.setVisibility(View.VISIBLE);
//            cancel.setText(builder.strCancle);
//            confirm.setText(builder.strConfirm);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (builder.cancelClick == null) {
//                        clear();
//                    } else {
//                        builder.cancelClick.onCancelClick(CustomDialog.this);
//                    }
//                }
//            });
//        } else {
//            cancel.setVisibility(View.GONE);
//            lineBottom.setVisibility(View.GONE);
//            confirm.setText(CustomDialogCheck.isNull(builder.strConfirm, "确定"));
//        }
//        confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (builder.confirmClick == null) {
//                    clear();
//                } else {
//                    //builder.confirmClick.onConfirmClick(CustomDialogCheck.isNull(edt.getText().toString()), CustomDialog.this);
//                    builder.confirmClick.onConfirmClick(CustomDialog.this);
//                }
//            }
//        });
//    }
//
//    /**
//     * @apiNote 可以通过返回键、点击外面关闭
//     */
//    public static CustomDialogBuilder builder(Context context) {
//        return new CustomDialogBuilder(context);
//    }
//
//    /**
//     * @param msg 通知内容
//     * @apiNote 可以通过返回键、点击外面关闭
//     * 为了调用简单，集成通知内容
//     */
//    public static CustomDialogBuilder builder(Context context, String msg) {
//        return new CustomDialogBuilder(context).msg(msg);
//    }
//
//    /**
//     * @param cancelable 是否可以通过返回键、点击外面关闭
//     */
//    public static CustomDialogBuilder builder(Context context, boolean cancelable) {
//        return new CustomDialogBuilder(context).cancelable(cancelable);
//    }
//
//    /**
//     * @apiNote 隐藏并释放资源
//     */
//    private void clear() {
//        cancel();
//    }
//
//    @Override
//    public void cancel() {
//        if(tickTimeHandler != null) {
//            tickTimeHandler.removeMessages(1);
//        }
//        super.cancel();
//    }
//
//    @Override
//    public void show() {
//        if(mBuilder.enableTick) {
//            tickTimeHandler = new Handler(msg -> {
//                tickTime --;
//                if(tickTime > 0) {
//                    SpannableString spannableString = new SpannableString(mBuilder.strConfirm + "(" + tickTime + "s)");
//                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FFF51616"));
//                    spannableString.setSpan(colorSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//                    confirm.setText(spannableString);
//                    tickTimeHandler.sendEmptyMessageDelayed(1, 1000);
//                } else {
//                    if(mBuilder.tickTimeListener != null) {
//                        mBuilder.tickTimeListener.onTickTimeEnded(this);
//                    }
//                }
//                return true;
//            });
//            tickTimeHandler.sendEmptyMessage(1);
//        }
//        super.show();
//    }
//}
