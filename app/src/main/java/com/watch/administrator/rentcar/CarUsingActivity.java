package com.watch.administrator.rentcar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Handler;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.model.RentOrderView;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/3/9.
 */
public class CarUsingActivity extends FragmentActivity {
    public final static String TAG="CarUsingActivity";
    String userId="";

    CalThread calThread;
    static final String UPPER_NUM = "upper";

    String getOrdersState="";
    private Map<String, Object> mMap;// 存储网络请求提交的参数
    private String url = ConnData.carInfo;

    List<RentOrderView> al=new ArrayList<RentOrderView>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_using);
        userId=CarUsingActivity.this.getIntent().getStringExtra("userId");

        calThread = new CalThread();
        // 启动新线程
        calThread.start();
    }

    // 定义一个线程类
    class CalThread extends Thread
    {
        public Handler mHandler;
        public void run()
        {
            Looper.prepare();
            mHandler = new Handler()
            {
                // 定义处理消息的方法
                @Override
                public void handleMessage(Message msg)
                {
                    if(msg.what == 0x123)
                    {
                        int upper = msg.getData().getInt(UPPER_NUM);
                        getOrders(upper);
                    }
                }
            };
            Looper.loop();
        }
    }

    // 为按钮的点击事件提供事件处理方法
    public void init()
    {
        // 创建消息
        Message msg = new Message();
        msg.what = 0x123;
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM ,
                Integer.parseInt(userId));
        msg.setData(bundle);
        // 向新线程中的Handler发送消息
        calThread.mHandler.sendMessage(msg);
    }

    public void getOrders(final int userId){
        mMap = new HashMap<String, Object>();
        mMap.put("method","getOrders");

        mMap.put("userid", userId);
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    getOrdersState = response.getString("getOrdersState");
                    Log.i(TAG, getOrdersState);
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (getOrdersState) {
                        case "200":
                            Toast.makeText(CarUsingActivity.this,
                                    "获取成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            al = parseJSON(response.getJSONArray("orders"));
                            Intent intent = new Intent(
                                    CarUsingActivity.this,
                                    UsingCarActivity.class);
                            intent.putExtra("userId", userId);
                            startActivity(intent);

                            break;
                        case "30":
                            Toast.makeText(CarUsingActivity.this,
                                    "注册用户失败",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CarUsingActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

    private List<RentOrderView> parseJSON(JSONArray items) {
        List<RentOrderView> allOrder=null;
        try {
            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                RentOrderView order = new RentOrderView();
                order.setOrderId(item.getInt("orde rId"));
                order.setOrderPrice(item.getDouble("orderPrice"));
                order.setOrderStatus(item.getInt("orderStatus"));
                order.setRentTakeTime(item.getString("rentTakeTime"));
//                order.setRen

                allOrder.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }

        return allOrder;
    }
}