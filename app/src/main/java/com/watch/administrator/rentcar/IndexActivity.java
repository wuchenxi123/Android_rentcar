package com.watch.administrator.rentcar;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.model.Orderlist;
import com.company.rentcar.model.RentOrderView;
import com.company.rentcar.model.User;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class IndexActivity extends TabActivity {
    private final static String TAG="IndexActivity";
    public static String userIdApp="";
    public static List<Orderlist> list;
    String flag="";
    private TabWidget mTabWidget;
    CarInfo carInfo;
    RentOrderView rentOrderView;
    User user;

    String userId3 = "";
    String orderId = "";

//    String carLpnum="";
//    String carRentPrice="";
//    String presentPrice="";
//    String orderPrice="";
//    String rentTime="";
//    String predictReturnTime="";

    private String carOnUseState = "";
    private String url = ConnData.returnCar;
    private Map<String, String> mMap;// 存储网络请求提交的参数
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);//这里使用了上面创建的xml文件（Tab页面的布局）
        Resources res = getResources(); // Resource object to get Drawables
        final TabHost tabHost = getTabHost();  // The activity TabHost

        TabHost.TabSpec spec;
        mTabWidget = tabHost.getTabWidget();


        Intent intent;  // Reusable Intent for each tab

        //第一个Tab
        intent = new Intent(this,OrderListAdapterActivity.class);//新建一个Intent用作Tab1显示的内容
        String userId1=this.getIntent().getStringExtra("userId");
//        String orderFlag=this.getIntent().getStringExtra("orderFlag");
//        switch (orderFlag){
//            case "1":{
//
//
//            }
//        }
        list=(List<Orderlist>) this.getIntent().getSerializableExtra("orderlist");

        Log.i(TAG, userId1);
//        Log.i(TAG,(Serializable)al+"---------");
        intent.putExtra("userId", LoginActivity.userId);
//        intent.putExtra("orderList", (Serializable)al);
        spec = tabHost.newTabSpec("tab1")//新建一个 Tab
                .setIndicator("订单历史", res.getDrawable(android.R.drawable.ic_menu_recent_history))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
        tabHost.addTab(spec);//添加进tabHost

        String isRent=this.getIntent().getStringExtra("flag");
        Log.i(TAG, isRent + "------------");
        switch (isRent){
            case "0": {
                //第二个Tab
                intent = new Intent(this, MainActivity.class);//第二个Intent用作Tab1显示的内容
                String userId2 = this.getIntent().getStringExtra("userId");
                userIdApp=userId2;
                intent.putExtra("userId",  LoginActivity.userId);
                spec = tabHost.newTabSpec("tab2")//新建一个 Tab
                        .setIndicator("租车", res.getDrawable(android.R.drawable.ic_menu_edit))//设置名称以及图标
                        .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
                tabHost.addTab(spec);//添加进tabHost
                break;
            }
            case "1":{
                carInfo= (CarInfo) this.getIntent().getSerializableExtra("carInfo");
                user= (User) this.getIntent().getSerializableExtra("driver");
                rentOrderView= (RentOrderView) this.getIntent().getSerializableExtra("rentOrderView");
                userId3 = this.getIntent().getStringExtra("userId");
                userIdApp=userId3;
                orderId = ((RentOrderView) this.getIntent().getSerializableExtra("rentOrderView")).getOrderId()+"";
                Log.i(TAG,carInfo+"---"+user+"---"+rentOrderView+"---"+userId3+"---"+orderId+"---");
                intent = new Intent(this, OrderVerifiedActivity.class);//第二个Intent用作Tab1显示的内容
                intent.putExtra("carInfo",(Serializable)carInfo);
                intent.putExtra("driver",(Serializable)user);
                intent.putExtra("rentOrderView",(Serializable)rentOrderView);
                Log.i(TAG,carInfo+"-----");
                intent.putExtra("userId",  LoginActivity.userId);
                intent.putExtra("orderId", orderId);

                spec = tabHost.newTabSpec("tab2")//新建一个 Tab
                        .setIndicator("还车", res.getDrawable(android.R.drawable.ic_menu_edit))//设置名称以及图标
                        .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
                tabHost.addTab(spec);//添加进tabHost
//                tabHost.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case mTabWidget.getChildTabViewAt(2).getId():{
//
//                            }
//                        }
//                        carUsing(orderId);
//                    }
//                });
                View v = mTabWidget.getChildTabViewAt(1);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        carUsing(orderId);
                    }
                });
                break;
            }
            case "2":{
                Intent intentRt = IndexActivity.this.getIntent();

                String orderid = intentRt.getStringExtra("orderid");
                String returnid = intentRt.getStringExtra("returnid");
                String orderPrice=intentRt.getStringExtra("orderPrice");
                String predictMoney = intentRt.getStringExtra("predictMoney");//已产生费用
                String overspend = intentRt.getStringExtra("overspend");//超支费用
                String carLpnum = intentRt.getStringExtra("carLpnum");
                String carRentPrice = intentRt.getStringExtra("carRentPrice");//租车费用
                String presentPrice = intentRt.getStringExtra("presentPrice");//
                String takeCartype = intentRt.getStringExtra("takeCartype");
                String returnPlace = intentRt.getStringExtra("returnPlace");
                String setReturnTime = intentRt.getStringExtra("setReturnTime");
                String driverName = intentRt.getStringExtra("driverName");
                String driverMobile = intentRt.getStringExtra("driverMobile");

                Log.i(TAG,carInfo+"---"+user+"---"+rentOrderView+"---"+userId3+"---"+orderId+"---");
                intent = new Intent(this, PaidActivity.class);//第二个Intent用作Tab1显示的内容
                intent.putExtra("orderid",orderid);
                intent.putExtra("returnid", returnid);
                intent.putExtra("orderPrice", orderPrice);
                intent.putExtra("predictMoney", predictMoney);//已产生费用
                intent.putExtra("overspend", overspend);//超支费用
                intent.putExtra("carLpnum", carLpnum);
                intent.putExtra("carRentPrice", carRentPrice);//租车费用
                intent.putExtra("presentPrice", presentPrice);//
                intent.putExtra("takeCartype", takeCartype);
                intent.putExtra("returnPlace", returnPlace);
                intent.putExtra("setReturnTime", setReturnTime);

                intent.putExtra("driverName",driverName);
                intent.putExtra("driverMobile",driverMobile);

                spec = tabHost.newTabSpec("tab2")//新建一个 Tab
                        .setIndicator("租车", res.getDrawable(android.R.drawable.ic_menu_edit))//设置名称以及图标
                        .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
                tabHost.addTab(spec);//添加进tabHost
//                tabHost.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        switch (v.getId()) {
//                            case mTabWidget.getChildTabViewAt(2).getId():{
//
//                            }
//                        }
//                        carUsing(orderId);
//                    }
//                });
                View v = mTabWidget.getChildTabViewAt(1);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3=new Intent(IndexActivity.this,MainActivity.class);
                        intent3.putExtra("userId",userId3);
                        startActivity(intent3);
                    }
                });
                break;
            }
        }


        //第三个Tab
        intent = new Intent(this,AlterPwdActivity.class);//第二个Intent用作Tab1显示的内容
        spec = tabHost.newTabSpec("tab2")//新建一个 Tab
                .setIndicator("设置", res.getDrawable(R.drawable.tools))//设置名称以及图标
                .setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx

        tabHost.addTab(spec);//添加进tabHost

        tabHost.setCurrentTab(1);//设置当前的选项卡,这里为Tab1
        //设置TabHost的背景颜色
//        tabHost.setBackgroundColor(Color.argb(150, 22, 70, 150));
        tabHost.setBottom(Gravity.BOTTOM);


    }

    private void carUsing(final String orderId) {

        mMap = new HashMap<String, String>();
        mMap.put("method", "carOnUse");

        mMap.put("orderid", orderId);
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    carOnUseState = response.getString("carOnUseState");
                    Log.i(TAG, carOnUseState + "===========");
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (carOnUseState) {
                        case "200":
                            Toast.makeText(IndexActivity.this,
                                    "获取车辆信息成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            Log.i(TAG,"==============+");

                            String carLpnum=response.getString("carLpnum");
                            String carRentPrice=response.getString("carRentPrice");
                            String presentPrice=response.getString("presentPrice");
                            String orderPrice=response.getString("orderPrice");
                            String rentTime=response.getString("rentTime");
                            String predictReturnTime=response.getString("predictReturnTime");
                            Log.i(TAG, carLpnum + "----" + presentPrice + "------" + orderPrice + "---" + rentTime + "===" + predictReturnTime);

                            Intent intent = new Intent(
                                    IndexActivity.this,
                                    ReturnActivity.class);
                            intent.putExtra("carLpnum",carLpnum);
                            intent.putExtra("carRentPrice",carRentPrice);
                            intent.putExtra("presentPrice",presentPrice);
                            intent.putExtra("orderPrice",orderPrice);

                            intent.putExtra("rentTime", rentTime);
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("predictReturnTime", predictReturnTime);
                            startActivity(intent);
                            break;
                        case "20":
                            Toast.makeText(IndexActivity.this,
                                    "获取使用中车辆失败", Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IndexActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // return true;//返回真表示返回键被屏蔽掉
            creatDialog();// 创建弹出的Dialog
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹出提示退出对话框
     */
    private void creatDialog() {
        new AlertDialog.Builder(this)
                .setMessage("确定退出租车?")
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                MyAppalication.getInstance().exit();
                            }
                        })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}
