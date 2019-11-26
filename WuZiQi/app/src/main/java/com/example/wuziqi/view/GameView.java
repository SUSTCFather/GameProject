package com.example.wuziqi.view;

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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.wuziqi.Constant;
import com.example.wuziqi.R;
import com.example.wuziqi.bean.GameData;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏主类
 * Created by LGL on 2016/5/15.
 */
public class GameView extends View {

    private static final String TAG = "GameView";

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

    //比例，棋子的大小是高的四分之三
    private float chessRatio = 0.75f;

    private GameData mGameData;

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
        mGameData = new GameData();
        initPaint();
        initBitmap();
    }

    /**
     * 初始化棋子
     */
    private void initBitmap() {
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

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //拿到宽和高的最小值，也就是宽
        int width = Math.min(widthSize, heightSize);

        //根据测量模式细节处理
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            width = heightSize;
        } else if (heightMode == MeasureSpec.UNSPECIFIED) {
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
        int mWhiteWidth = (int) (mLineHeight * chessRatio);

        //修改棋子大小
        mWhite = Bitmap.createScaledBitmap(mWhite, mWhiteWidth, mWhiteWidth, false);
        mBlack = Bitmap.createScaledBitmap(mBlack, mWhiteWidth, mWhiteWidth, false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawPieces(canvas);
    }

    /**
     * 绘制棋子的方法
     *
     * @param canvas
     */
    private void drawPieces(Canvas canvas) {

        for(Point whitePoint : mGameData.getWhiteArray()) {
            canvas.drawBitmap(mWhite, (whitePoint.x + (1 - chessRatio) / 2) * mLineHeight,
                    (whitePoint.y + (1 - chessRatio) / 2) * mLineHeight, null);
        }

        for(Point blackPoint : mGameData.getBlackArray()) {
            canvas.drawBitmap(mBlack, (blackPoint.x + (1 - chessRatio) / 2) * mLineHeight,
                    (blackPoint.y + (1 - chessRatio) / 2) * mLineHeight, null);
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
                if (mGameData.containsPoint(p)) {
                    return false;
                }
                Log.e(TAG,p.toString());
                //判断如果是白子就存白棋集合，反之则黑棋集合
                if (mGameData.getColor() == Constant.WHITE) {
                    mGameData.getWhiteArray().add(p);
                } else {
                    mGameData.getBlackArray().add(p);
                }

                //刷新
                invalidate();

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


//    /**
//     * 再来一局
//     */
//    public void RestartGame() {
//        mWhiteArray.clear();
//        mBlackArray.clear();
//        mIsGameOver = false;
//        mIsWhiteWin = false;
//        invalidate();
//    }
}
