package com.watch.administrator.rentcar;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/3/9.
 */
public class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyAppalication.getInstance().addActivity(BaseActivity.this);
    }

}