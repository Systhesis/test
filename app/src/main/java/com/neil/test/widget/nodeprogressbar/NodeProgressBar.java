package com.neil.test.widget.nodeprogressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.neil.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: lllddd
 * created on: 2020/7/1 21:10
 * description:
 */
public class NodeProgressBar extends View {
    // 自定义View宽度
    private int mWidth;
    // 自定义View高度
    private int mHeight;

    // 节点个数
    private int mNodeCount;
    // 节点宽度
    private int mNodeWidth;
    // 节点高度
    private int mNodeHeight;
    // 未到达节点的资源id
    private int mNodeUnreached;
    // 已经到达节点的资源id
    private int mNodeSucceed;
    // 已完成节点的资源id
    private int mNodeFailed;
    // 失败节点的资源id
    private int mNodeReached;

    private int mCenterSucceed;
    // 节点大小比例，用于处理成功/失败节点比到达/未到达节点大的情况
    private float mNodeRatio;

    // 上方文字是否生效
    private boolean mTopTxtEnable;
    // 上方文字大小
    private int mTopTxtSize;
    // 上方文字颜色
    private int mTopTxtColor;
    // 上方文字距离节点的距离
    private int mTopTxtGap;
    // 上方文字的样式
    private int mTopTxtStyle;


    private boolean mCenterReachedTxtEnable;

    private int mCenterReachedTxtSize;

    private int mCenterReachedTxtColor;

    private boolean mCenterUnReachedTxtEnable;

    private int mCenterUnReachedTxtSize;

    private int mCenterUnReachedTxtColor;

    // 下方文字是否生效
    private boolean mBottomTxtEnable;
    // 下方文字的大小
    private int mBottomTxtSize;
    // 下方文字的颜色
    private int mBottomTxtColor;
    // 下方文字距离节点的距离
    private int mBottomTxtGap;
    // 下方文字的样式
    private int mBottomTxtStyle;

    // 下方提示文字的颜色（reach的节点）
    private int mBottomReachedTxtColor;
    // 相仿提示文字的样式（reach的节点使用）
    private int mBottomReachedTxtStyle;

    // 连接线的宽度
    private int mLineWidth;
    // 已到达的连接线颜色
    private int mReachedLineColor;
    // 未到达的连接线的颜色
    private int mUnreachedLineColor;

    // 节点区域横向宽度
    private int mRegionWidth;

    // 上方文字画笔
    private Paint mPaintTopTxt;
    // 底部文字画笔
    private Paint mPaintBottomTxt;
    // 底部提示文字的画笔
    private Paint mPaintBottomReachedTxt;
    // 节点画笔
    private Paint mPaintNode;
    private Paint mPaintCenterSucceedNode;
    // 未到达连线画笔
    private Paint mPaintUnreachedLine;
    // 已到达连线画笔
    private Paint mPaintReachedLine;

    private Paint mPaintCenterReachedTxt;

    private Paint mPaintCenterUnReachedTxt;

    // 未到达节点Bitmap
    private Bitmap mNodeUnreachedBitmap;
    // 成功节点Bitmap
    private Bitmap mNodeSucceedBitmap;
    // 已到达节点Bitmap
    private Bitmap mNodeReachedBitmap;
    // 未完成节点Bitmap
    private Bitmap mNodeFailedBitmap;

    private Bitmap mCenterSucceedBitmap;

    // 上方文字的中心坐标列表
    private List<Location> mTopTxtLocationList;
    // 中间节点的中心文字坐标列表
    private List<Location> mNodeLocationList;
    // 下方文字的中心坐标列表
    private List<Location> mBottomTxtLocationList;

    private List<Node> mNodeList = new ArrayList<>();

    public NodeProgressBar(Context context) {
        this(context, null);
    }

    public NodeProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NodeProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NodeProgressBar);

        mNodeCount = ta.getInt(R.styleable.NodeProgressBar_nodeCount, 0);
        mNodeWidth = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_nodeWidth, 0);
        mNodeHeight = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_nodeHeight, 0);
        mNodeUnreached = ta.getResourceId(R.styleable.NodeProgressBar_nodeUnreached, R.drawable.node_unreached);
        mNodeSucceed = ta.getResourceId(R.styleable.NodeProgressBar_nodeSucceed, R.drawable.node_unreached);
        mNodeFailed = ta.getResourceId(R.styleable.NodeProgressBar_nodeFailed, R.drawable.node_unreached);
        mNodeReached = ta.getResourceId(R.styleable.NodeProgressBar_nodeReached, R.drawable.node_unreached);
        mCenterSucceed = ta.getResourceId(R.styleable.NodeProgressBar_centerSucceed, R.drawable.ic_init_suc);
        mNodeRatio = ta.getFloat(R.styleable.NodeProgressBar_nodeRatio, 1.0F);

        mTopTxtEnable = ta.getBoolean(R.styleable.NodeProgressBar_topTxtEnable, false);
        mTopTxtSize = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_topTxtSize, 0);
        mTopTxtColor = ta.getColor(R.styleable.NodeProgressBar_topTxtColor, Color.TRANSPARENT);
        mTopTxtGap = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_topTxtGap, 0);
        mTopTxtStyle = ta.getInteger(R.styleable.NodeProgressBar_topTxtStyle, TxtStyle.BOLD);

        mCenterReachedTxtEnable = ta.getBoolean(R.styleable.NodeProgressBar_centerReachedTxtEnable, false);
        mCenterReachedTxtSize = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_centerReachedTxtSize, 0);
        mCenterReachedTxtColor = ta.getColor(R.styleable.NodeProgressBar_centerReachedTxtColor, Color.TRANSPARENT);

        mCenterUnReachedTxtEnable = ta.getBoolean(R.styleable.NodeProgressBar_centerUnReachedTxtEnable, false);
        mCenterUnReachedTxtSize = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_centerUnReachedTxtSize, 0);
        mCenterUnReachedTxtColor = ta.getColor(R.styleable.NodeProgressBar_centerUnReachedTxtColor, Color.TRANSPARENT);

        mBottomTxtEnable = ta.getBoolean(R.styleable.NodeProgressBar_bottomTxtEnable, false);
        mBottomTxtSize = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_bottomTxtSize, 0);
        mBottomTxtColor = ta.getColor(R.styleable.NodeProgressBar_bottomTxtColor, Color.TRANSPARENT);
        mBottomTxtGap = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_bottomTxtGap, 0);
        mBottomTxtStyle = ta.getInteger(R.styleable.NodeProgressBar_bottomTxtStyle, TxtStyle.COMMON);

        mBottomReachedTxtColor = ta.getColor(R.styleable.NodeProgressBar_bottomReachedTxtColor, Color.TRANSPARENT);
        mBottomReachedTxtStyle = ta.getInteger(R.styleable.NodeProgressBar_bottomReachedTxtStyle, TxtStyle.COMMON);

        mLineWidth = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_lineWidth, 0);
        mReachedLineColor = ta.getColor(R.styleable.NodeProgressBar_reachedLineColor, Color.TRANSPARENT);
        mUnreachedLineColor = ta.getColor(R.styleable.NodeProgressBar_unreachedLineColor, Color.TRANSPARENT);

        mRegionWidth = ta.getDimensionPixelSize(R.styleable.NodeProgressBar_regionWidth, 0);

        ta.recycle();

        configBitmaps(context);
        configPaints();
    }

    /**
     * 配置画笔
     */
    private void configPaints() {
        // 上方文字画笔属性设置
        configTopTxtPaint();
        // 下方文字画笔属性设置
        configBottomTxtPaint();
        // 下方提示文字画笔属性设置
        configBottomWarnTxtPaint();

        configCenterReachedTxtPaint();
        configCenterUnReachedTxtPaint();

        // 节点画笔属性设置
        configNodePaint();
        // 未到达连接线画笔属性设置
        configUnreachedLinePaint();
        // 已到达连接线画笔属性设置
        configReachedLinePaint();
    }

    /**
     * 已到达连接线画笔属性设置
     */
    private void configReachedLinePaint() {
        mPaintReachedLine = new Paint();
        mPaintReachedLine.setColor(mReachedLineColor);
        mPaintReachedLine.setStrokeWidth(mLineWidth);
        mPaintReachedLine.setStyle(Paint.Style.FILL);
        mPaintReachedLine.setAntiAlias(true);
    }

    /**
     * 未到达连接线画笔属性设置
     */
    private void configUnreachedLinePaint() {
        mPaintUnreachedLine = new Paint();
        mPaintUnreachedLine.setColor(mUnreachedLineColor);
        mPaintUnreachedLine.setStrokeWidth(mLineWidth);
        mPaintUnreachedLine.setStyle(Paint.Style.FILL);
        mPaintUnreachedLine.setAntiAlias(true);
    }

    /**
     * 节点画笔属性设置
     */
    private void configNodePaint() {
        mPaintNode = new Paint();
        mPaintNode.setAntiAlias(true);
        mPaintCenterSucceedNode = new Paint();
        mPaintCenterSucceedNode.setAntiAlias(true);
    }

    /**
     * 下方提示文字画笔属性设置
     */
    private void configBottomWarnTxtPaint() {
        mPaintBottomReachedTxt = new Paint();
        mPaintBottomReachedTxt.setTextSize(mBottomTxtSize);
        mPaintBottomReachedTxt.setColor(mBottomReachedTxtColor);
        mPaintBottomReachedTxt.setTextAlign(Paint.Align.CENTER);
        mPaintBottomReachedTxt.setAntiAlias(true);
        if (TxtStyle.COMMON == mBottomReachedTxtStyle) {
            mPaintBottomReachedTxt.setTypeface(Typeface.DEFAULT);
        } else if (TxtStyle.BOLD == mBottomReachedTxtStyle) {
            mPaintBottomReachedTxt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else if (TxtStyle.ITALIC == mBottomReachedTxtStyle) {
            mPaintBottomReachedTxt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }

    /**
     * 下方文字画笔属性设置
     */
    private void configBottomTxtPaint() {
        mPaintBottomTxt = new Paint();
        mPaintBottomTxt.setTextSize(mBottomTxtSize);
        mPaintBottomTxt.setColor(mBottomTxtColor);
        mPaintBottomTxt.setTextAlign(Paint.Align.CENTER);
        mPaintBottomTxt.setAntiAlias(true);
        if (TxtStyle.COMMON == mBottomTxtStyle) {
            mPaintBottomTxt.setTypeface(Typeface.DEFAULT);
        } else if (TxtStyle.BOLD == mBottomTxtStyle) {
            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else if (TxtStyle.ITALIC == mBottomTxtStyle) {
            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }

    private void configCenterReachedTxtPaint() {
        mPaintCenterReachedTxt = new Paint();
        mPaintCenterReachedTxt.setTextSize(mCenterReachedTxtSize);
        mPaintCenterReachedTxt.setColor(mCenterReachedTxtColor);
        mPaintCenterReachedTxt.setTextAlign(Paint.Align.CENTER);
        mPaintCenterReachedTxt.setAntiAlias(true);
//        if (TxtStyle.COMMON == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.DEFAULT);
//        } else if (TxtStyle.BOLD == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        } else if (TxtStyle.ITALIC == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
//        }
    }

    private void configCenterUnReachedTxtPaint() {
        mPaintCenterUnReachedTxt = new Paint();
        mPaintCenterUnReachedTxt.setTextSize(mCenterUnReachedTxtSize);
        mPaintCenterUnReachedTxt.setColor(mCenterUnReachedTxtColor);
        mPaintCenterUnReachedTxt.setTextAlign(Paint.Align.CENTER);
        mPaintCenterUnReachedTxt.setAntiAlias(true);
//        if (TxtStyle.COMMON == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.DEFAULT);
//        } else if (TxtStyle.BOLD == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
//        } else if (TxtStyle.ITALIC == mBottomTxtStyle) {
//            mPaintBottomTxt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
//        }
    }

    /**
     * 上方文字画笔属性设置
     */
    private void configTopTxtPaint() {
        mPaintTopTxt = new Paint();
        mPaintTopTxt.setTextSize(mTopTxtSize);
        mPaintTopTxt.setColor(mTopTxtColor);
        mPaintTopTxt.setTextAlign(Paint.Align.CENTER);
        mPaintTopTxt.setAntiAlias(true);
        if (TxtStyle.COMMON == mTopTxtStyle) {
            mPaintTopTxt.setTypeface(Typeface.DEFAULT);
        } else if (TxtStyle.BOLD == mTopTxtStyle) {
            mPaintTopTxt.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        } else if (TxtStyle.ITALIC == mTopTxtStyle) {
            mPaintTopTxt.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        }
    }

    /**
     * 配置bitmap
     *
     * @param context
     */
    private void configBitmaps(Context context) {
        Drawable unreachedDraw = ContextCompat.getDrawable(context, mNodeUnreached);
        Drawable succeedDraw = ContextCompat.getDrawable(context, mNodeSucceed);
        Drawable reachedDraw = ContextCompat.getDrawable(context, mNodeReached);
        Drawable failedDraw = ContextCompat.getDrawable(context, mNodeFailed);
        mNodeUnreachedBitmap = drawableToBitmap(unreachedDraw);
        mNodeSucceedBitmap = drawableToBitmap(succeedDraw);
        mNodeReachedBitmap = drawableToBitmap(reachedDraw);
        mNodeFailedBitmap = drawableToBitmap(failedDraw);
        mCenterSucceedBitmap = BitmapFactory.decodeResource(context.getResources(), mCenterSucceed);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
//        if (mTopTxtEnable && mBottomTxtEnable) {// 上下文字都展示
//            mHeight = mTopTxtSize + mTopTxtGap + mNodeHeight + mBottomTxtGap + mBottomTxtSize;
//        } else if (mTopTxtEnable) {// 仅上方文字展示
//            mHeight = mTopTxtSize + mTopTxtGap + mNodeHeight;
//        } else if (mBottomTxtEnable) {// 仅下方文字展示
//            mHeight = mNodeHeight + mBottomTxtGap + mBottomTxtSize;
//        } else {// 不展示上下文字
//            mHeight = mNodeHeight;
//        }
        // 上线各加1dp的余量，防止个别情况下展示不全
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mNodeCount <= 0 || mNodeList.size() != mNodeCount || mNodeList.isEmpty()) {
            return;
        }

        // 初始化位置列表
        initLocationLists();

        // 测量坐标
        measureLocations();

        // 绘制上方文字
        if (mTopTxtEnable) {
            for (int i = 0; i < mNodeCount; i++) {
                Node node = mNodeList.get(i);
                if (TextUtils.isEmpty(node.topTxt)) {
                    continue;
                }
                Paint.FontMetrics metrics = mPaintTopTxt.getFontMetrics();
                int x = mTopTxtLocationList.get(i).x;
                int y = (int) (mTopTxtLocationList.get(i).y + Math.abs(mPaintTopTxt.ascent() + mPaintTopTxt.descent() / 2));
                canvas.drawText(node.topTxt, x, y, mPaintTopTxt);
            }
        }

        // 绘制连线
        for (int i = 0; i < mNodeCount; i++) {
            Node node = mNodeList.get(i);

            if (i == mNodeCount - 1) {
                break;
            }

            int x1 = mNodeLocationList.get(i).x;
            int y1 = mNodeLocationList.get(i).y;
            int x2 = mNodeLocationList.get(i + 1).x;
            int y2 = mNodeLocationList.get(i + 1).y;
            if (Node.LineState.UNREACHED == node.nodeAfterLineState) {
                canvas.drawLine(x1, y1, x2, y2, mPaintUnreachedLine);
            } else if (Node.LineState.REACHED == node.nodeAfterLineState) {
                canvas.drawLine(x1, y1, x2, y2, mPaintReachedLine);
            } else {
                canvas.drawLine(x1, y1, x2, y2, mPaintUnreachedLine);
            }
        }

        // 绘制节点
        for (int i = 0; i < mNodeCount; i++) {
            Node node = mNodeList.get(i);
            int x = mNodeLocationList.get(i).x;
            int y = mNodeLocationList.get(i).y;
            if (Node.NodeState.UNREACHED == node.nodeState) {
                //画图
                Rect rect = new Rect(0, 0, mNodeUnreachedBitmap.getWidth(), mNodeUnreachedBitmap.getHeight());
                RectF rectF = new RectF(x - mNodeRatio * mNodeWidth / 2, y - mNodeRatio * mNodeHeight / 2, x + mNodeRatio * mNodeWidth / 2, y + mNodeRatio * mNodeHeight / 2);
                canvas.drawBitmap(mNodeUnreachedBitmap, rect, rectF, mPaintNode);
                //画中心文字
                Paint.FontMetrics fontMetrics = mPaintCenterUnReachedTxt.getFontMetrics();
                float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
                float baseline = rectF.centerY() + distance;
                canvas.drawText(node.centerTxt, x, y + baseline / 4, mPaintCenterUnReachedTxt);
            } else if (Node.NodeState.SUCCEED == node.nodeState) {
                Rect rect = new Rect(0, 0, mNodeSucceedBitmap.getWidth(), mNodeSucceedBitmap.getHeight());
                RectF rectF = new RectF(x - mNodeRatio * mNodeWidth / 2, y - mNodeRatio * mNodeHeight / 2, x + mNodeRatio * mNodeWidth / 2, y + mNodeRatio * mNodeHeight / 2);
                canvas.drawBitmap(mNodeSucceedBitmap, rect, rectF, mPaintNode);
                //画中心的勾勾
                canvas.drawBitmap(mCenterSucceedBitmap, x - mCenterSucceedBitmap.getWidth() / 2.0f, y - mCenterSucceedBitmap.getHeight() / 2.0f, mPaintCenterSucceedNode);
            } else if (Node.NodeState.FAILED == node.nodeState) {
                Rect rect = new Rect(0, 0, mNodeFailedBitmap.getWidth(), mNodeFailedBitmap.getHeight());
                RectF rectF = new RectF(x - 1.0F * mNodeWidth / 2, y - 1.0F * mNodeHeight / 2, x + 1.0F * mNodeWidth / 2, y + mNodeHeight / 2);
                canvas.drawBitmap(mNodeFailedBitmap, rect, rectF, mPaintNode);
            } else if (Node.NodeState.REACHED == node.nodeState) {
                Rect rect = new Rect(0, 0, mNodeReachedBitmap.getWidth(), mNodeReachedBitmap.getHeight());
                RectF rectF = new RectF(x - 1.0F * mNodeWidth / 2, y - 1.0F * mNodeHeight / 2, x + 1.0F * mNodeWidth / 2, y + 1.0F * mNodeHeight / 2);
                canvas.drawBitmap(mNodeReachedBitmap, rect, rectF, mPaintNode);
                //画中心文字
                Paint.FontMetrics fontMetrics = mPaintCenterReachedTxt.getFontMetrics();
                float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
                float baseline = rectF.centerY() + distance;
                canvas.drawText(node.centerTxt, x, y + baseline / 4, mPaintCenterReachedTxt);
            }
        }

        // 绘制下方文字
        if (mBottomTxtEnable) {
            for (int i = 0; i < mNodeCount; i++) {
                Node node = mNodeList.get(i);
                if (TextUtils.isEmpty(node.bottomTxt)) {
                    continue;
                }
                int x = mBottomTxtLocationList.get(i).x;
                int y = (int) (mBottomTxtLocationList.get(i).y + Math.abs(mPaintBottomTxt.ascent() + mPaintBottomTxt.descent() / 2));
                if (Node.NodeState.REACHED != node.nodeState) {
                    canvas.drawText(node.bottomTxt, x, y, mPaintBottomTxt);
                } else {
                    canvas.drawText(node.bottomTxt, x, y, mPaintBottomReachedTxt);
                }
            }
        }
    }

    private void initLocationLists() {
        if (mTopTxtLocationList != null) {
            mTopTxtLocationList.clear();
        } else {
            mTopTxtLocationList = new ArrayList<>();
        }

        if (mNodeLocationList != null) {
            mNodeLocationList.clear();
        } else {
            mNodeLocationList = new ArrayList<>();
        }

        if (mBottomTxtLocationList != null) {
            mBottomTxtLocationList.clear();
        } else {
            mBottomTxtLocationList = new ArrayList<>();
        }
    }

    /**
     * 测量元素的中心坐标
     */
    private void measureLocations() {
        if (mNodeCount == 1) {
            // 上方文字的中心坐标
            if (mTopTxtEnable) {
                Location topTxtLoc = new Location();
                topTxtLoc.x = mWidth / 2;
                topTxtLoc.y = mTopTxtSize / 2;
                mTopTxtLocationList.add(topTxtLoc);
            }

            // 节点的中心坐标
            if (mTopTxtEnable) {
                Location nodeLoc = new Location();
                nodeLoc.x = mWidth / 2;
                nodeLoc.y = mTopTxtSize + mTopTxtGap + mNodeHeight / 2;
                mNodeLocationList.add(nodeLoc);
            } else {
                Location nodeLoc = new Location();
                nodeLoc.x = mWidth / 2;
                nodeLoc.y = mNodeHeight / 2;
                mNodeLocationList.add(nodeLoc);
            }

            // 下方文字的中心坐标
            if (mTopTxtEnable && mBottomTxtEnable) {
                Location bottomTxtLoc = new Location();
                bottomTxtLoc.x = mWidth / 2;
                bottomTxtLoc.y = mTopTxtSize + mTopTxtGap + mNodeHeight + mBottomTxtGap + mBottomTxtSize / 2;
                mBottomTxtLocationList.add(bottomTxtLoc);
            } else if (mBottomTxtEnable) {
                Location bottomTxtLoc = new Location();
                bottomTxtLoc.x = mWidth / 2;
                bottomTxtLoc.y = mNodeHeight + mBottomTxtGap + mBottomTxtSize / 2;
                mBottomTxtLocationList.add(bottomTxtLoc);
            }
            return;
        }

        int space = (mWidth - mRegionWidth * mNodeCount) / (mNodeCount - 1);
        for (int i = 0; i < mNodeCount; i++) {
            // 上方文字的中心坐标
            if (mTopTxtEnable) {
                Location topTxtLoc = new Location();
                topTxtLoc.x = mRegionWidth / 2 + i * space + i * mRegionWidth;
                topTxtLoc.y = mTopTxtSize / 2;
                mTopTxtLocationList.add(topTxtLoc);
            }

            // 节点的中心坐标
            if (mTopTxtEnable) {
                Location nodeLoc = new Location();
                nodeLoc.x = mRegionWidth / 2 + i * space + i * mRegionWidth;
                nodeLoc.y = mTopTxtSize + mTopTxtGap + mNodeHeight / 2;
                mNodeLocationList.add(nodeLoc);
            } else {
                Location nodeLoc = new Location();
                nodeLoc.x = mRegionWidth / 2 + i * space + i * mRegionWidth;
                nodeLoc.y = mNodeHeight / 2;
                mNodeLocationList.add(nodeLoc);
            }

            // 下方文字的中心坐标
            if (mTopTxtEnable && mBottomTxtEnable) {
                Location bottomTxtLoc = new Location();
                bottomTxtLoc.x = mRegionWidth / 2 + i * space + i * mRegionWidth;
                bottomTxtLoc.y = mTopTxtSize + mTopTxtGap + mNodeHeight + mBottomTxtGap + mBottomTxtSize / 2;
                mBottomTxtLocationList.add(bottomTxtLoc);
            } else if (mBottomTxtEnable) {
                Location bottomTxtLoc = new Location();
                bottomTxtLoc.x = mRegionWidth / 2 + i * space + i * mRegionWidth;
                bottomTxtLoc.y = mNodeHeight + mBottomTxtGap + mBottomTxtSize / 2;
                mBottomTxtLocationList.add(bottomTxtLoc);
            }
        }
    }

    /**
     * 上方文字是否生效
     *
     * @param mTopTxtEnable
     */
    public void setTopTxtEnable(boolean mTopTxtEnable) {
        this.mTopTxtEnable = mTopTxtEnable;
        invalidate();
    }

    /**
     * 下方文字是否生效
     *
     * @param mBottomTxtEnable
     */
    public void setBottomTxtEnable(boolean mBottomTxtEnable) {
        this.mBottomTxtEnable = mBottomTxtEnable;
        invalidate();
    }

    /**
     * 设置节点信息
     *
     * @param mNodeList
     */
    public void setNodeList(@NonNull List<Node> mNodeList) {
        this.mNodeList = mNodeList;
        this.mNodeCount = mNodeList.size();
        invalidate();
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = (int)mNodeWidth;//drawable.getIntrinsicWidth();
        int h = (int)mNodeHeight;//drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
            : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 中心坐标
     */
    private static class Location {
        int x;
        int y;
    }

    /**
     * 节点对象
     */
    public static class Node {
        public interface NodeState {
            int UNREACHED = 1;
            int SUCCEED = 2;
            int REACHED = 3;
            int FAILED = 4;
        }

        public interface LineState {
            int REACHED = 0;
            int UNREACHED = 1;
        }

        public int id;

        // 节点上方文字
        public String topTxt;

        public String centerTxt;
        // 节点下方文字
        public String bottomTxt;
        // 节点状态
        public int nodeState;
        // 节点后连线状态
        public int nodeAfterLineState;
    }

    /**
     * 字体
     */
    public interface TxtStyle {
        int COMMON = 0;
        int BOLD = 1;
        int ITALIC = 2;
    }
}
