package com.watch.administrator.rentcar;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/3/9.
 */
public class MyAppalication extends Application {
    // 存放activity的集合
    private ArrayList<Activity> list = new ArrayList<Activity>();
    private static MyAppalication instance;

    public MyAppalication() {
    }

    /**
     * 利用单例模式获取MyAppalication实例
     *
     * @return
     */
    public static MyAppalication getInstance() {
        if (null == instance) {
            instance = new MyAppalication();
        }
        return instance;
    }

    /**
     * 添加activity到list集合
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        list.add(activity);

    }

    /**
     * 退出集合所有的activity
     */
    public void exit() {
        try {
            for (Activity activity : list) {
                activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
