package com.example.wuziqi;

import android.app.Activity;
import android.os.Process;

import java.util.Stack;

public class ActivityManager {
    private static ActivityManager instance = null;

    private Stack<Activity> activityStack;

    private ActivityManager() {
        this.activityStack = new Stack<>();
    }

    public static ActivityManager getInstance() {
        if(instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    public Activity getActivity(Class<?> clazz) {
        for (Activity activity : activityStack) {
            if (clazz.equals(activity.getClass())) {
                return activity;
            }
        }
        return null;
    }

    public Activity getCurrentActivity() {
        if (!activityStack.isEmpty()) {
            return activityStack.peek();
        }
        return null;
    }

    /**
     * 获取所有 Activity
     */
    public Stack<Activity> getAllActivityStacks() {
        return activityStack;
    }

    /**
     * 结束指定的 Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 结束指定类名的 Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束除当前传入以外所有 Activity
     */
    public void finishOthersActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (!activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有 Activity
     */
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            finishActivity(activity);
        }
    }

    /**
     * 退出 app 时调用
     */
    public void exitApp() {
        try {
            finishAllActivity();
            Process.killProcess(Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
