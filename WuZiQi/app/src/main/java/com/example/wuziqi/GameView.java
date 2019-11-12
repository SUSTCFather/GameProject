package com.example.wuziqi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主类
 * Created by LGL on 2016/5/15.
 */
public class GameView extends View {

    //线条数量
    private static final int MAX_LINE = 10;

    //胜利棋子数量
    private static final int MAX_COUNT_IN_LINE = 5;

    //线条的宽度
    private int mPanelWidth;

    //线条的高度
    private float mLineHeight;

    //画笔
    private Paint mPaint = new Paint();

    //黑棋子
    private Bitmap mBlack;

    //白棋子
    private Bitmap mWhite;

    //是否游戏结束
    private boolean mIsGameOver;

    //赢家
    private boolean mIsWhiteWin;

    //比例，棋子的大小是高的四分之三
    private float rowSize = 3 * 1.0f / 4;

    //存储用户点击的坐标
    private ArrayList<Point> mWhiteArray = new ArrayList<>();
    private ArrayList<Point> mBlackArray = new ArrayList<>();

    //标记，是执黑子还是白子 ,白棋先手
    private boolean mIsWhite = true;

    public GameView(Context context){
        this(context,null);
    }

    /**
     * 构造方法
     *
     * @param context
     * @param attrs
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //测试颜色
        //setBackgroundColor(0x44ff0000);
        initPaint();
        initBitmap();
    }

    /**
     * 初始化棋子
     */
    private void initBitmap() {
        //拿到图片资源
        mBlack = BitmapFactory.decodeResource(getResources(), R.drawable.stone_black);
        mWhite = BitmapFactory.decodeResource(getResources(), R.drawable.stone_white);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        //设置颜色
        mPaint.setColor(Color.BLACK);
        //设置线条宽度
        mPaint.setStrokeWidth(3);
        //抗锯齿
        mPaint.setAntiAlias(true);
        //设置防抖动
        mPaint.setDither(true);
        //设置Style
        mPaint.setStyle(Paint.Style.STROKE);
    }

    /**
     * 测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //获取高宽值
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int hightSize = MeasureSpec.getSize(heightMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);

        //拿到宽和高的最小值，也就是宽
        int width = Math.min(widthSize, hightSize);

        //根据测量模式细节处理
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = hightSize;
        } else if (hightMode == MeasureSpec.UNSPECIFIED) {
            width = widthSize;
        }

        //设置这样就是一个正方形了
        setMeasuredDimension(width, width);

    }

    /**
     * 测量大小
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //拿到宽
        mPanelWidth = w;
        //分割
        mLineHeight = mPanelWidth * 1.0f / MAX_LINE;

        //棋子宽度
        int mWhiteWidth = (int) (mLineHeight * rowSize);

        //修改棋子大小
        mWhite = Bitmap.createScaledBitmap(mWhite, mWhiteWidth, mWhiteWidth, false);
        mBlack = Bitmap.createScaledBitmap(mBlack, mWhiteWidth, mWhiteWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);
        drawPieces(canvas);

        //绘制完成之后判断是否胜利
        checkWin();
    }

    /**
     * 判断是否胜利
     */
    private void checkWin() {

        //判断白棋是否有五个相同的棋子相连
        boolean mWhiteWin = checkFiveLine(mWhiteArray);
        //判断黑棋是否有五个相同的棋子相连
        boolean mBlackWin = checkFiveLine(mBlackArray);

        //只要有一个胜利，游戏就结束
        if (mWhiteWin || mBlackWin) {

            mIsGameOver = true;

            mIsWhiteWin = mWhiteWin;

            Toast.makeText(getContext(), mIsWhiteWin ? "黑棋胜利" : "白棋胜利", Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * //判断棋子是否有五个相同的棋子相连
     *
     * @param points
     * @return
     */
    private boolean checkFiveLine(List<Point> points) {

        //遍历棋子
        for (Point p : points) {
            //拿到棋盘上的位置
            int x = p.x;
            int y = p.y;

            /**
             * 四种情况胜利，横，竖，左斜，右斜
             */

            //横
            boolean win = checkHorizontal(x, y, points);
            if (win) return true;
            //竖
            win = checkVertical(x, y, points);
            if (win) return true;
            //左斜
            win = checkLeft(x, y, points);
            if (win) return true;
            //右斜
            win = checkRight(x, y, points);
            if (win) return true;

        }
        return false;
    }

    /**
     * 判断横向的棋子
     *
     * @param x
     * @param y
     * @param points
     */
    private boolean checkHorizontal(int x, int y, List<Point> points) {

        //棋子标记，记录是否有五个  =1是因为自身是一个
        int count = 1;

        //左
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x - i, y))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        //右
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x + i, y))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        return false;
    }


    /**
     * 判断纵向的棋子
     *
     * @param x
     * @param y
     * @param points
     */
    private boolean checkVertical(int x, int y, List<Point> points) {

        //棋子标记，记录是否有五个  =1是因为自身是一个
        int count = 1;

        //上
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x, y - i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        //下
        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x, y + i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        return false;
    }

    /**
     * 判断左斜向的棋子
     *
     * @param x
     * @param y
     * @param points
     */
    private boolean checkLeft(int x, int y, List<Point> points) {

        //棋子标记，记录是否有五个  =1是因为自身是一个
        int count = 1;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x - i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x + i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        return false;
    }

    /**
     * 判断右斜向的棋子
     *
     * @param x
     * @param y
     * @param points
     */
    private boolean checkRight(int x, int y, List<Point> points) {

        //棋子标记，记录是否有五个  =1是因为自身是一个
        int count = 1;

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x - i, y - i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        for (int i = 1; i < MAX_COUNT_IN_LINE; i++) {
            //如果有
            if (points.contains(new Point(x + i, y + i))) {
                count++;
            } else {
                break;
            }
        }

        //有五个就为true
        if (count == MAX_COUNT_IN_LINE) {

            return true;
        }

        return false;
    }


    /**
     * 绘制棋子的方法
     *
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < mWhiteArray.size(); i++) {
            //获取白棋子的坐标
            Point whitePoint = mWhiteArray.get(i);
            canvas.drawBitmap(mBlack, (whitePoint.x + (1 - rowSize) / 2) * mLineHeight, (whitePoint.y + (1 - rowSize) / 2) * mLineHeight, null);
        }

        for (int i = 0; i < mBlackArray.size(); i++) {
            //获取黑棋子的坐标
            Point blackPoint = mBlackArray.get(i);
            canvas.drawBitmap(mWhite, (blackPoint.x + (1 - rowSize) / 2) * mLineHeight, (blackPoint.y + (1 - rowSize) / 2) * mLineHeight, null);
        }
    }

    /**
     * 绘制棋盘的方法
     *
     * @param canvas
     */
    private void drawLine(Canvas canvas) {
        //获取高宽
        int w = mPanelWidth;
        float lineHeight = mLineHeight;

        //遍历，绘制线条
        for (int i = 0; i < MAX_LINE; i++) {
            //横坐标
            int startX = (int) (lineHeight / 2);
            int endX = (int) (w - lineHeight / 2);

            //纵坐标
            int y = (int) ((0.5 + i) * lineHeight);

            //绘制横
            canvas.drawLine(startX, y, endX, y, mPaint);
            //绘制纵
            canvas.drawLine(y, startX, y, endX, mPaint);
        }

    }

    /**
     * 触摸事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //游戏结束的话不接收点击事件
        if (mIsGameOver) {

            return false;
        }

        switch (event.getAction()) {
            //按下事件
            case MotionEvent.ACTION_DOWN:

                int x = (int) event.getX();
                int y = (int) event.getY();

                //封装成一个Point
                Point p = getValidPoint(x, y);

                //判断当前这个点是否有棋子了
                if (mWhiteArray.contains(p) || mBlackArray.contains(p)) {

                    //点击不生效
                    return false;
                }

                //判断如果是白子就存白棋集合，反之则黑棋集合
                if (mIsWhite) {
                    mWhiteArray.add(p);
                } else {
                    mBlackArray.add(p);
                }

                //刷新
                invalidate();

                //改变值
                mIsWhite = !mIsWhite;

                break;
        }

        return true;
    }


    /**
     * 不能重复点击
     *
     * @param x
     * @param y
     * @return
     */
    private Point getValidPoint(int x, int y) {

        return new Point((int) (x / mLineHeight), (int) (y / mLineHeight));
    }

    //系统状态
    private static final String INSTANCE = "instace";
    //游戏结束
    private static final String INSTANCE_GAMEOVER = "instace_gameover";
    //白棋子
    private static final String INSTANCE_WHITE_ARRAY = "instace_white_array";
    //黑棋子
    private static final String INSTANCE_BLACK_ARRAY = "instace_black_arrayr";

    /**
     * 存储状态
     *
     * @return
     */
    @Override
    protected Parcelable onSaveInstanceState() {

        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE, super.onSaveInstanceState());
        bundle.putBoolean(INSTANCE_GAMEOVER, mIsGameOver);
        bundle.putParcelableArrayList(INSTANCE_WHITE_ARRAY, mWhiteArray);
        bundle.putParcelableArrayList(INSTANCE_BLACK_ARRAY, mBlackArray);
        return bundle;
    }

    /**
     * 重新运行
     *
     * @param state
     */
    @Override
    protected void onRestoreInstanceState(Parcelable state) {

        //取值
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            mIsGameOver = bundle.getBoolean(INSTANCE_GAMEOVER);
            mWhiteArray = bundle.getParcelableArrayList(INSTANCE_WHITE_ARRAY);
            mBlackArray = bundle.getParcelableArrayList(INSTANCE_BLACK_ARRAY);

            //调用
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    /**
     * 再来一局
     */
    public void RestartGame() {
        mWhiteArray.clear();
        mBlackArray.clear();
        mIsGameOver = false;
        mIsWhiteWin = false;
        invalidate();
    }
}
