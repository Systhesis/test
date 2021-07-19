//package com.neil.test.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.PixelFormat;
//import android.graphics.Rect;
//import android.graphics.RectF;
//import android.graphics.Typeface;
//import android.graphics.drawable.Drawable;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.annotation.Nullable;
//import androidx.core.content.res.ResourcesCompat;
//
//import com.jd.jdx.srs.app.rbd.R;
//import com.jd.jdx.srs.util.DisplayTypedValueUtil;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//
///**
// * 自定义数字键盘
// * @author zhongnan1
// * @time 2021/4/26 17:29
// *
// */
//public class NumberKeyboardView extends View {
//    private final static String TAG = "NumberKeyboardView";
//    /**
//     * 列
//     */
//    private static final int TOTAL_COL = 3;
//    /**
//     * 行
//     */
//    private static final int TOTAL_ROW = 4;
//
//    private Paint grayBgPaint, yellowBgPaint, linePaint, bgPaint;
//    private Paint mNumPaint, mConfirmPaint;
//    private Typeface mTypeface;
//    private int mViewWidth; // 键盘宽度
//    private int mViewHeight; // 键盘高度
//    private float mCellWidth, mCellHeight; // 单元格宽度、高度
//    private float numberPadding, numberSize, confirmSize;
//    private Bitmap numNormalBg, numPressedBg, numDeleteBg, confirmNormalBg, confirmPressedBg;
//    private int numNormalResId, numPressedResId, numDeleteResId, confirmNormalResId, confirmPressedResId;
//    private final float defaultNumPadding = 20;
//    private final float defaultNumSize = 40;
//    private final float defaultConfirmSize = 42;
//
//    private final String deleteStr = "删除";
//    private final String confirmStr = "确认";
//
//    private Row[] rows = new Row[TOTAL_ROW];
//
//
//
//    public NumberKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
//
//    public interface CallBack {
//        void clickNum(String num);// 回调点击的数字
//        void deleteNum();// 回调删除
//        void resetNum(); //重置
//        void confirmNum(); //点击确认
//    }
//
//    private CallBack mCallBack;// 回调
//
//    public void setCallBack(CallBack callBack) {
//        mCallBack = callBack;
//    }
//
//    public NumberKeyboardView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//    }
//
//    public NumberKeyboardView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberKeyboardView);
//        numberPadding = typedArray.getDimensionPixelSize(R.styleable.NumberKeyboardView_numberPadding, DisplayTypedValueUtil.dip2px(context, defaultNumPadding));
//        numberSize = typedArray.getDimensionPixelSize(R.styleable.NumberKeyboardView_numberSize, DisplayTypedValueUtil.sp2px(context, defaultNumSize));
//        confirmSize = typedArray.getDimensionPixelSize(R.styleable.NumberKeyboardView_confirmSize, DisplayTypedValueUtil.sp2px(context, defaultConfirmSize));
//        numNormalResId = typedArray.getResourceId(R.styleable.NumberKeyboardView_numberNormalBackground, R.drawable.default_number_normal_background);
//        numPressedResId = typedArray.getResourceId(R.styleable.NumberKeyboardView_numberPressedBackground, R.drawable.default_number_pressed_background);
//        numDeleteResId = typedArray.getResourceId(R.styleable.NumberKeyboardView_numberDeleteBackground, R.drawable.number_delete);
//
//        confirmNormalResId = typedArray.getResourceId(R.styleable.NumberKeyboardView_confirmNormalBackground, R.drawable.confirm_normal_background);
//        confirmPressedResId = typedArray.getResourceId(R.styleable.NumberKeyboardView_confirmPressedBackground, R.drawable.confirm_click_background);
//        typedArray.recycle();
//        init(context);
//
//    }
//
//    public NumberKeyboardView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        //drawLine(canvas);
//        for (int i = 0; i < TOTAL_ROW; i++) {
//            if (rows[i] != null) {
//                rows[i].drawCells(canvas);
//            }
//        }
//    }
//
//    /**
//     * 画6条直线
//     * @param canvas
//     */
//    private void drawLine(Canvas canvas) {
//        canvas.drawLine(0, 0, mViewWidth, 0, linePaint);
//        canvas.drawLine(0, mCellHeight, mViewWidth, mCellHeight, linePaint);
//        canvas.drawLine(0, mCellHeight * 2, mViewWidth, mCellHeight * 2, linePaint);
//        canvas.drawLine(0, mCellHeight * 3, mViewWidth, mCellHeight * 3, linePaint);
//        canvas.drawLine(mCellWidth, 0, mCellWidth, mViewHeight, linePaint);
//        canvas.drawLine(mCellWidth * 2, 0, mCellWidth * 2, mViewHeight, linePaint);
//
//
//    }
//
//    /**
//     * 初始化画笔
//     * @param
//     */
//    private void init(Context context) {
//        String fontPath = "fonts/JDLangZhengTi.TTF";
//        mTypeface = Typeface.createFromAsset(context.getAssets(), fontPath);
//
//        mNumPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mNumPaint.setColor(Color.WHITE);
//        mNumPaint.setTypeface(mTypeface);
//
//        mConfirmPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mConfirmPaint.setColor(Color.WHITE);
//        mConfirmPaint.setTypeface(mTypeface);
//
//        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        linePaint.setTextSize(1.0f);
//        linePaint.setColor(0x90000000);
//
//        grayBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        grayBgPaint.setStyle(Paint.Style.FILL);
//        grayBgPaint.setColor(Color.parseColor("#e9e9e9"));
//
//        yellowBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        yellowBgPaint.setStyle(Paint.Style.FILL);
//        yellowBgPaint.setColor(Color.YELLOW);
//
//        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        initDate();
//    }
//
//    private void initDate() {
//        fillDate();
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//        mViewWidth = w;
//        mViewHeight = h;
//        mCellWidth = (mViewWidth - (TOTAL_COL - 1) * numberPadding) / TOTAL_COL;
//        mCellHeight = (mViewHeight - (TOTAL_ROW - 1) * numberPadding) / TOTAL_ROW;
//        mNumPaint.setTextSize(numberSize);
//        mConfirmPaint.setTextSize(confirmSize);
//
////        Drawable drawable = ResourcesCompat.getDrawable(getResources(), numNormalResId, null);
////        numNormalBg = drawableToBitmap(drawable);
////        Drawable drawable1 = ResourcesCompat.getDrawable(getResources(), numPressedResId, null);
////        numPressedBg = drawableToBitmap(drawable1);
//
//        numNormalBg = BitmapFactory.decodeResource(getResources(), numNormalResId);
//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), numPressedResId, null);
//        numPressedBg = drawableToBitmap(drawable);
//        numDeleteBg = BitmapFactory.decodeResource(getResources(), numDeleteResId);
//
//        confirmNormalBg = BitmapFactory.decodeResource(getResources(), confirmNormalResId);
//        confirmPressedBg = BitmapFactory.decodeResource(getResources(), confirmPressedResId);
//
//
//
//    }
//
//    private Cell mClickCell = null;
//    private float mDownX;
//    private float mDownY;
//
//    /*
//     *
//     * 触摸事件为了确定点击位置的数字
//     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mDownX = event.getX();
//                mDownY = event.getY();
//                int col = -1;
//                int row = -1;
//                for(int i = 0; i < TOTAL_COL; i ++) {
//                    if(i * (numberPadding + mCellWidth) <= mDownX &&
//                        mDownX <= (i* (numberPadding + mCellWidth) + mCellWidth )) {
//                        col = i;
//                    }
//                }
//                for(int j = 0; j < TOTAL_ROW; j ++) {
//                    if(j * (numberPadding + mCellHeight) <= mDownY &&
//                            mDownY <= (j* (numberPadding + mCellHeight) + mCellHeight )) {
//                        row = j;
//                    }
//                }
//                Log.e(TAG, "onTouchEvent: col-" + col + ", row-" + row);
//                measureClickCell(col, row);
//                break;
//            case MotionEvent.ACTION_UP:
//                if (mClickCell != null) {
//                    // 在抬起后把状态置为默认
//                    rows[mClickCell.i].cells[mClickCell.j].state = State.DEFAULT_NUM;
//                    mClickCell = null;
//                    invalidate();
//                }
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    /**
//     * 测量点击单元格
//     * @param col 列
//     * @param row 行
//     */
//    private void measureClickCell(int col, int row) {
//        if (col >= TOTAL_COL || row >= TOTAL_ROW) {
//            return;
//        }
//        if(col == - 1 || row == -1) {
//            return;
//        }
//        if (rows[row] != null) {
//            mClickCell = new Cell(rows[row].cells[col].num, rows[row].cells[col].state, rows[row].cells[col].i,
//                    rows[row].cells[col].j);
//            rows[row].cells[col].state = State.CLICK_NUM;
//            if (deleteStr.equals(rows[row].cells[col].num)) {
//                if(mCallBack != null) {
//                    mCallBack.deleteNum();
//                }
//
//            } else if(confirmStr.equals(rows[row].cells[col].num)){
//                if(mCallBack != null) {
//                    mCallBack.confirmNum();
//                }
//            } else {
//                if(mCallBack != null) {
//                    mCallBack.clickNum(rows[row].cells[col].num);
//                }
//            }
//            invalidate();
//        }
//    }
//
//    /**
//     * 组 以一行为一组
//     */
//    private class Row {
//        public int j;
//
//        Row(int j) {
//            this.j = j;
//        }
//
//        // 一行3个单元格
//        public Cell[] cells = new Cell[TOTAL_COL];
//
//        public void drawCells(Canvas canvas) {
//            for (int i = 0; i < cells.length; i++) {
//                if (cells[i] != null) {
//                    cells[i].drawSelf(canvas);
//                }
//            }
//
//        }
//    }
//
//    // 单元格
//    private class Cell {
//        public String num;
//        public State state;
//        /**
//         * i = 行 j = 列
//         */
//        public int i;
//        public int j;
//
//        public Cell(String num, State state, int i, int j) {
//            super();
//            this.num = num;
//            this.state = state;
//            this.i = i;
//            this.j = j;
//        }
//
//        // 绘制一个单元格 如果颜色需要自定义可以修改
//        public void drawSelf(Canvas canvas) {
//            Rect bgRect = new Rect(0,0,(int)mCellWidth,(int)mCellHeight);
//            RectF bgRectF = new RectF((float) (mCellWidth * j + numberPadding * j), (float) (mCellHeight * i + numberPadding * i),
//                    (float) (mCellWidth * (j + 1) + numberPadding * j), (float) (mCellHeight * (i + 1) + numberPadding * i));
//            switch (state) {
//
//                case CLICK_NUM:
//
//                    if(confirmStr.equals(num)) {
//                        canvas.drawBitmap(confirmPressedBg, bgRect, bgRectF, bgPaint);
//                    }else {
//                        canvas.drawBitmap(numPressedBg, bgRect, bgRectF, bgPaint);
//                    }
//
//                    // 绘制数字
//                    Log.e(TAG, "drawSelf: CLICK_NUM");
//                    if(deleteStr.equals(num)) {
//                        canvas.drawBitmap(numDeleteBg, (float) ((j + 0.5) * mCellWidth + j * numberPadding - numDeleteBg.getWidth() / 2),
//                                (float) ((i + 0.5) * mCellHeight + i * numberPadding - numDeleteBg.getHeight() / 2), grayBgPaint);
//                    }else {
//                        if(confirmStr.equals(num)) {
//                            Paint.FontMetrics fontMetrics = mConfirmPaint.getFontMetrics();
//                            float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
//                            //float baseline = bgRectF.centerY() + distance;
//                            canvas.drawText(num, (float) ((j + 0.5) * mCellWidth + j * numberPadding - mConfirmPaint.measureText(num) / 2),
//                                    (float) ((i + 0.5) * mCellHeight + i * numberPadding + distance), //mConfirmPaint.measureText(num, 0, 1) / 2
//                                    mConfirmPaint);
//                        } else {
//                            canvas.drawText(num, (float) ((j + 0.5) * mCellWidth + j * numberPadding - mNumPaint.measureText(num) / 2),
//                                    (float) ((i + 0.5) * mCellHeight + i * numberPadding + mNumPaint.measureText(num, 0, 1) / 2),
//                                    mNumPaint);
//                        }
//
//                    }
//
//                    break;
//                case DEFAULT_NUM:
//                    if (deleteStr.equals(num)) {
//                        canvas.drawBitmap(numNormalBg, bgRect, bgRectF, bgPaint);
//                        // 绘制删除图片
//                        canvas.drawBitmap(numDeleteBg, (float) ((j + 0.5) * mCellWidth + j * numberPadding - numDeleteBg.getWidth() / 2),
//                                (float) ((i + 0.5) * mCellHeight + i * numberPadding - numDeleteBg.getHeight() / 2), grayBgPaint);
//                    } else if(confirmStr.equals(num)) {
//                        canvas.drawBitmap(confirmNormalBg, bgRect, bgRectF, bgPaint);
//                        // 绘制数字
//                        Paint.FontMetrics fontMetrics = mConfirmPaint.getFontMetrics();
//                        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
//                        canvas.drawText(num, (float) ((j + 0.5) * mCellWidth + j * numberPadding - mConfirmPaint.measureText(num) / 2),
//                                (float) ((i + 0.5) * mCellHeight + i * numberPadding + distance), //mConfirmPaint.measureText(num, 0, 1) / 2
//                                mConfirmPaint);
//                    }else {
//                        canvas.drawBitmap(numNormalBg, bgRect, bgRectF, bgPaint);
//                        // 绘制数字
//                        canvas.drawText(num, (float) ((j + 0.5) * mCellWidth + j * numberPadding - mNumPaint.measureText(num) / 2),
//                                (float) ((i + 0.5) * mCellHeight + i * numberPadding + mNumPaint.measureText(num, 0, 1) / 2),
//                                mNumPaint);
//                    }
//                default:
//                    break;
//            }
//
//        }
//    }
//
//    /**
//     *  cell的state
//     */
//    private enum State {
//        DEFAULT_NUM, CLICK_NUM;
//    }
//
//    private List<String> numKeys = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
//
//    /**
//     * 填充数字
//     */
//    private void fillDate() {
//        int postion = 0;
//        for (int i = 0; i < TOTAL_ROW; i++) {
//            rows[i] = new Row(i);
//            for (int j = 0; j < TOTAL_COL; j++) {
//                if (i == 3 && j == 0) {
//                    rows[i].cells[j] = new Cell(deleteStr, State.DEFAULT_NUM, i, j);
//                } else if (i == 3 && j == 2) {
//                    rows[i].cells[j] = new Cell(confirmStr, State.DEFAULT_NUM, i, j);
//                } else {
//                    rows[i].cells[j] = new Cell(numKeys.get(postion), State.DEFAULT_NUM, i, j);
//                    postion++;
//                }
//            }
//        }
//        //bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.keyboard_backspace);
//    }
//
//    /**
//     * 随机键盘
//     * @param isRandom
//     */
//    public void setRandomKeyBoard(boolean isRandom) {
//        if (isRandom) {
//            Collections.shuffle(numKeys);
//            initDate();
//            invalidate();
//        }
//    }
//
//    public Bitmap drawableToBitmap(Drawable drawable) {
//        // 取 drawable 的长宽
//        int w = (int)mCellWidth;//drawable.getIntrinsicWidth();
//        int h = (int)mCellHeight;//drawable.getIntrinsicHeight();
//
//        // 取 drawable 的颜色格式
//        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
//            : Bitmap.Config.RGB_565;
//        // 建立对应 bitmap
//        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
//        // 建立对应 bitmap 的画布
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, w, h);
//        // 把 drawable 内容画到画布中
//        drawable.draw(canvas);
//        return bitmap;
//    }
//
//}
