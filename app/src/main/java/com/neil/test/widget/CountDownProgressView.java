package com.neil.test.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jd.jdx.srs.app.rbd.R;


/**
 * Created by mahongping  圆形倒计时
 */

public class CountDownProgressView extends View {
    private static final String TAG = "CountDownProgress";
    // Color.parseColor("#00CCFF")
    private static int defaultCircleSolideColor = Color.BLUE;
    private static int defaultCircleStrokeColor = Color.WHITE;
    private static int defaultCircleStrokeWidth = 8;
    private static int defaultCircleRadius = 40;
    private static int progressColor = Color.RED;
    private static int progressWidth = 6;
    private static int smallCircleSolideColor = Color.parseColor("#ff2b37");// Color.BLACK;
    private static int smallCircleStrokeColor = Color.parseColor("#e3e3e3");//Color.WHITE;
    private static float smallCircleStrokeWidth = 2;
    private static float smallCircleRadius = 8;
    private static int textColor = Color.parseColor("#ff2b37");// Color.BLACK;
    private static float textSize = 33;
    private static Paint defaultCriclePaint;
    private static Paint progressPaint;
    private static Paint smallCirclePaint;
    private static Paint smallCircleSolidePaint;
    private static Paint textPaint;
    private static float currentAngle;
    private static String textDesc;
    private long countdownTime;
    private static int mStartSweepValue = -90;
    private static RectF arcRect;
    private CountDownTimer timer = null;
    private boolean pause = false;
    private String prefix = "s";
    OnCountdownFinishListener countdownFinishListener = null;

    public CountDownProgressView(Context context) {
        super(context);
    }

    public CountDownProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CountDownProgressView);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.CountDownProgressView_default_circle_solide_color) {
                defaultCircleSolideColor = typedArray.getColor(attr, defaultCircleSolideColor);
            } else if (attr == R.styleable.CountDownProgressView_default_circle_stroke_color) {
                defaultCircleStrokeColor = typedArray.getColor(attr, defaultCircleStrokeColor);
            } else if (attr == R.styleable.CountDownProgressView_default_circle_stroke_width) {
                defaultCircleStrokeWidth = (int) typedArray.getDimension(attr, defaultCircleStrokeWidth);
            } else if (attr == R.styleable.CountDownProgressView_default_circle_radius) {
                defaultCircleRadius = (int) typedArray.getDimension(attr, defaultCircleRadius);
            } else if (attr == R.styleable.CountDownProgressView_progress_color) {
                progressColor = typedArray.getColor(attr, progressColor);
            } else if (attr == R.styleable.CountDownProgressView_progress_width) {
                progressWidth = (int) typedArray.getDimension(attr, progressWidth);
            } else if (attr == R.styleable.CountDownProgressView_small_circle_solide_color) {
                smallCircleSolideColor = typedArray.getColor(attr, smallCircleSolideColor);
            } else if (attr == R.styleable.CountDownProgressView_small_circle_stroke_color) {
                smallCircleStrokeColor = typedArray.getColor(attr, smallCircleStrokeColor);
            } else if (attr == R.styleable.CountDownProgressView_small_circle_stroke_width) {
                smallCircleStrokeWidth = (int) typedArray.getDimension(attr, smallCircleStrokeWidth);
            } else if (attr == R.styleable.CountDownProgressView_small_circle_radius) {
                smallCircleRadius = (int) typedArray.getDimension(attr, smallCircleRadius);
            } else if (attr == R.styleable.CountDownProgressView_text_color) {
                textColor = typedArray.getColor(attr, textColor);
            } else if (attr == R.styleable.CountDownProgressView_text_size) {
                textSize = (int) typedArray.getDimension(attr, textSize);
            }
        }
        typedArray.recycle();
        setPaint(context);
        arcRect = new RectF(0, 0, defaultCircleRadius * 2, defaultCircleRadius * 2);
    }

    private void setPaint(Context context) {
        //默认圆
        defaultCriclePaint = new Paint();
        defaultCriclePaint.setAntiAlias(true);//抗锯齿
        defaultCriclePaint.setDither(true);//防抖动
        defaultCriclePaint.setStyle(Paint.Style.STROKE);
        defaultCriclePaint.setStrokeWidth(defaultCircleStrokeWidth);
        defaultCriclePaint.setColor(defaultCircleStrokeColor);//这里先画边框的颜色，后续再添加画笔画实心的颜色
        //默认圆上面的进度弧度
        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setDither(true);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setColor(progressColor);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷样式
        //进度上面的小圆
        smallCirclePaint = new Paint();
        smallCirclePaint.setAntiAlias(true);
        smallCirclePaint.setDither(true);
        smallCirclePaint.setStyle(Paint.Style.STROKE);
        smallCirclePaint.setStrokeWidth(smallCircleStrokeWidth);
        smallCirclePaint.setColor(smallCircleStrokeColor);
        //画进度上面的小圆的实心画笔（主要是将小圆的实心颜色设置成白色）
        smallCircleSolidePaint = new Paint();
        smallCircleSolidePaint.setAntiAlias(true);
        smallCircleSolidePaint.setDither(true);
        smallCircleSolidePaint.setStyle(Paint.Style.FILL);
        smallCircleSolidePaint.setColor(smallCircleSolideColor);

        //文字画笔
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
//        textPaint.setTypeface(ResourcesCompat.getFont(context, R.font.ping_fang_light_e));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        setPaint();
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        //画默认圆
        canvas.drawCircle(defaultCircleRadius, defaultCircleRadius, defaultCircleRadius, defaultCriclePaint);

        //画进度圆弧
        //currentAngle = getProgress()*1.0f/getMax()*360;
        canvas.drawArc(arcRect, mStartSweepValue, 360 * currentAngle, false, progressPaint);
        //画中间文字
        //   String text = getProgress()+"%";
        //获取文字的长度的方法
        float textWidth = textPaint.measureText(textDesc);
        float textHeight = (textPaint.descent() + textPaint.ascent()) / 2;
        canvas.drawText(textDesc, defaultCircleRadius - textWidth / 2, defaultCircleRadius - textHeight, textPaint);

        //画小圆
        float currentDegreeFlag = 360 * currentAngle;
        float smallCircleX = 0, smallCircleY = 0;
        float hudu = (float) Math.abs(Math.PI * currentDegreeFlag / 180);//Math.abs：绝对值 ，Math.PI：表示π ， 弧度 = 度*π / 180
        smallCircleX = (float) Math.abs(Math.sin(hudu) * defaultCircleRadius + defaultCircleRadius);
        smallCircleY = (float) Math.abs(defaultCircleRadius - Math.cos(hudu) * defaultCircleRadius);
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius, smallCirclePaint);
        canvas.drawCircle(smallCircleX, smallCircleY, smallCircleRadius - smallCircleStrokeWidth, smallCircleSolidePaint);//画小圆的实心

        canvas.restore();

    }

    /**
     * 如果该View布局的宽高开发者没有精确的告诉，则需要进行测量，如果给出了精确的宽高则我们就不管了
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize;
        int heightSize;
        int strokeWidth = Math.max(defaultCircleStrokeWidth, progressWidth);
        if (widthMode != MeasureSpec.EXACTLY) {
            widthSize = getPaddingLeft() + defaultCircleRadius * 2 + strokeWidth + getPaddingRight();
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            heightSize = getPaddingTop() + defaultCircleRadius * 2 + strokeWidth + getPaddingBottom();
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    ValueAnimator animator;

    //属性动画
    public void startCountDownTime(final OnCountdownFinishListener countdownFinishListener) {
        setClickable(false);
        this.countdownFinishListener = countdownFinishListener;
        animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setDuration(countdownTime);
        animator.setInterpolator(new LinearInterpolator());//匀速
        animator.setRepeatCount(0);//表示不循环，-1表示无限循环
        //值从0-1.0F 的动画，动画时长为countdownTime，ValueAnimator没有跟任何的控件相关联，那也正好说明ValueAnimator只是对值做动画运算，而不是针对控件的，我们需要监听ValueAnimator的动画过程来自己对控件做操作
        //添加监听器,监听动画过程中值的实时变化(animation.getAnimatedValue()得到的值就是0-1.0)
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /**
                 * 这里我们已经知道ValueAnimator只是对值做动画运算，而不是针对控件的，因为我们设置的区间值为0-1.0f
                 * 所以animation.getAnimatedValue()得到的值也是在[0.0-1.0]区间，而我们在画进度条弧度时，设置的当前角度为360*currentAngle，
                 * 因此，当我们的区间值变为1.0的时候弧度刚好转了360度
                 */
                currentAngle = (float) animation.getAnimatedValue();
                //       Log.e("currentAngle",currentAngle+"");
                invalidate();//实时刷新view，这样我们的进度条弧度就动起来了
            }
        });
        //开启动画
        animator.start();
        timer = new PausableCountDownTimer(countdownTime + 1000, 1000).start();
    }

    public void setCountdownTime(long countdownTime) {
        this.countdownTime = countdownTime + 1000;
        //textDesc = countdownTime / 1000 + "″";
        textDesc = this.countdownTime / 1000 + prefix;
        invalidate();
    }

    public long getCountdownTime() {
        return countdownTime;
    }

    public void setCountdownTime(long countdownTime, float textSize, int defaultCircleRadius) {
        this.countdownTime = countdownTime;
        textDesc = countdownTime / 1000 + prefix;
        if (textSize != 0) {
            com.jd.jdx.srs.app.rbd.widget.CountDownProgressView.textSize = textSize;
        }
        if (defaultCircleRadius != 0) {
            com.jd.jdx.srs.app.rbd.widget.CountDownProgressView.defaultCircleRadius = defaultCircleRadius;
        }
    }

    public interface OnCountdownFinishListener {
        void countdownFinished();
    }

    /*  public void cancel (final OnCountdownFinishListener countdownFinishListener){
          if(countdownFinishListener != null){
              countdownFinishListener.countdownFinished();
          }
          timer.cancel();
          timer.onFinish();
      }*/
    public void cancel() {
        if (timer != null) {
            timer.cancel();
        }
        if (animator != null) {
            animator.cancel();
        }
    }

    private class PausableCountDownTimer extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public PausableCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //         Log.e("time",countdownTime+"");
            countdownTime = countdownTime - 1000;
            //textDesc = countdownTime/1000 + "″";
            textDesc = countdownTime / 1000 + prefix;
            //countdownTime = countdownTime-1000;
            //Log.e("time", countdownTime + prefix);
            //刷新view
            invalidate();
        }

        @Override
        public void onFinish() {
            Log.d(TAG, "onFinish: ");
            textDesc = 0 + prefix;
            //textDesc = "时间到";
            //同时隐藏小球
            smallCirclePaint.setColor(getResources().getColor(android.R.color.transparent));
            smallCircleSolidePaint.setColor(getResources().getColor(android.R.color.transparent));
            //刷新view
            invalidate();
            //倒计时结束的时候，需要通过自定义接口通知UI去处理其他业务逻辑
            if (countdownFinishListener != null) {
                countdownFinishListener.countdownFinished();
            }
            if (countdownTime > 0) {
                setClickable(true);
            } else {
                setClickable(false);
            }
        }
    }
    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
        if(pause){
            if(animator.isRunning()){
                animator.pause();
            }

            timer.cancel();
        }else{
            if(animator.isPaused()){
                animator.resume();
            }
            timer = new PausableCountDownTimer(countdownTime + 1000, 1000).start();
        }
    }
}


