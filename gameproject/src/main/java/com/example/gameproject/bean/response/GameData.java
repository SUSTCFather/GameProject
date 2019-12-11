package com.example.gameproject.bean.response;

import com.example.gameproject.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.gameproject.Constant.MAX_COUNT_IN_LINE;

public class GameData {
    private int hallId;

    private GamePlayer blackPlayer;

    private GamePlayer whitePlayer;

    private List<Point> blackPoints;

    private List<Point> whitePoints;

    private int winner;

    public GameData(int hallId) {
        this.hallId = hallId;
        this.winner = -1;
        this.blackPoints = new ArrayList<>();
        this.whitePoints = new ArrayList<>();
    }

    public GameData() {

    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }

    public GamePlayer getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(GamePlayer blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public GamePlayer getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(GamePlayer whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public List<Point> getBlackPoints() {
        return blackPoints;
    }

    public void setBlackPoints(List<Point> blackPoints) {
        this.blackPoints = blackPoints;
    }

    public List<Point> getWhitePoints() {
        return whitePoints;
    }

    public void setWhitePoints(List<Point> whitePoints) {
        this.whitePoints = whitePoints;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    /**
     * 添加新的点
     * @param point
     * @param color
     */
    public void addPoint(Point point, int color) {
        if(containsPoint(point)) {
            System.out.println("发现重复点"+point.toString());
            return;
        }
        if(color == Constant.BLACK) {
            blackPoints.add(point);
        }else {
            whitePoints.add(point);
        }
    }

    /**
     * 判断是否胜利
     */
    public void checkWin() {
        //判断白棋是否有五个相同的棋子相连
        boolean mWhiteWin = checkFiveLine(whitePoints);
        if(mWhiteWin) {
            winner = Constant.WHITE;
            return;
        }
        //判断黑棋是否有五个相同的棋子相连
        boolean mBlackWin = checkFiveLine(blackPoints);
        if(mBlackWin) {
            winner = Constant.BLACK;
        }
    }

    /**
     * 判断棋子是否有五个相同的棋子相连
     * @param points
     * @return
     */
    private boolean checkFiveLine(List<Point> points) {

        //遍历棋子
        for (Point p : points) {
            //拿到棋盘上的位置
            int x = p.getX();
            int y = p.getY();
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

    private boolean containsPoint(Point point) {
        return whitePoints.contains(point) || blackPoints.contains(point);
    }
}
