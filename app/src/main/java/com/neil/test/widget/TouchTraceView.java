package com.neil.test.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhongnan1
 * @time 2021/7/10 11:43
 *
 */
public class TouchTraceView extends View {
    Context mContext;
    private Paint line_paint, text_paint, countPaint;
    int screenW, screenH;

    private int paintColor = Color.RED;
    Map<Integer, TouchPoint> pointMap;
    float back_x1, back_y1, back_x2, back_y2;

    private Path mPath;
    private float mPosX, mPosY;
    private Paint mPaint;
    public TouchTraceView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //app = ;//作用仅仅是获取默认屏幕的长和宽
        this.screenH = h;
        this.screenW = w;
        pointMap = new HashMap<>();
        initPaint();

    }

    private void initPaint() {
        mPath = new Path();
        line_paint = new Paint();
        line_paint.setAntiAlias(true);
        line_paint.setColor(paintColor);
        line_paint.setStrokeWidth(3);
        text_paint = new Paint();
        text_paint.setAntiAlias(true);
        text_paint.setColor(paintColor);
        text_paint.setTextSize(30);
        countPaint = new Paint();
        countPaint.setAntiAlias(true);
        countPaint.setColor(paintColor);
        countPaint.setTextSize(60);
        mPaint = new Paint();
        mPaint.setColor(paintColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int num = pointMap.size();
        if (num == 0) {
            clearDraw(canvas);
            return;
        }
        for (Map.Entry<Integer, TouchPoint> entry : pointMap.entrySet()) {
            TouchPoint point = entry.getValue();
            canvas.drawLine(0, point.y, getWidth(), point.y, line_paint);
            canvas.drawLine(point.x, 0, point.x, getHeight(), line_paint);
            if (num == 1) {
                String text = "(" + point.x + ", " + point.y + ")";
                canvas.drawText(text, screenW / 2 - text_paint.measureText(text) / 2, screenH - text_paint.getTextSize(), text_paint);
            } else {
                canvas.drawText(String.valueOf(pointMap.size()), screenW / 2, screenH - countPaint.getTextSize() , countPaint);
            }
        }
        canvas.drawPath(mPath, mPaint);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int index = event.getActionIndex();
        int id = event.getPointerId(index);
        int pointerIndex = event.findPointerIndex(id);
        int pointerCount = event.getPointerCount();
        int historySize = event.getHistorySize();
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_POINTER_DOWN:
                //pointMap.put(pointerIndex, new TouchPoint(event.getX(pointerIndex), event.getY(pointerIndex)));
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //pointMap.remove(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int h = 0; h < historySize; h++) {
                    for (int p = 0; p < pointerCount; p++) {
                        pointMap.put(p, new TouchPoint(event.getHistoricalX(p, h), event.getHistoricalY(p, h)));
                    }
                }
                for (int p = 0; p < pointerCount; p++) {
                    pointMap.put(p, new TouchPoint(event.getX(p), event.getY(p)));
                }
                mPath.quadTo(mPosX, mPosY, x, y);

                break;
            case MotionEvent.ACTION_DOWN:
                pointMap.put(0, new TouchPoint(event.getX(pointerIndex), event.getY(pointerIndex)));
                back_x1 = event.getX();
                back_y1 = event.getY();
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                back_x2 = event.getX();
                back_y2 = event.getY();
                if (Math.abs(back_x1 - back_x2) > screenW / 2 && Math.abs(back_y1 - back_y2) > screenH / 2) {
                    callOnClick();
                }
                pointMap.clear();
                mPath.reset();
                break;
            default:
                break;
        }
        mPosX = x;
        mPosY = y;
        if (event.getPointerCount() == 0) {
            pointMap.clear();
        }
        invalidate();
        return true;
    }

    static class TouchPoint {
        public float x = 0;
        public float y = 0;

        TouchPoint(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    void clearDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        canvas.drawColor(Color.WHITE);
    }
}


