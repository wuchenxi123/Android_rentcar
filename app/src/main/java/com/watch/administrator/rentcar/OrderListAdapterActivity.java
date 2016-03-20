package com.watch.administrator.rentcar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.daoIml.OrderMange;
import com.company.rentcar.model.CompleteOrderDetail;
import com.company.rentcar.model.Orderlist;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.OnItemClickListener;

public class OrderListAdapterActivity extends AppCompatActivity {

    private final static String TAG="OrderListAdapterActivity";
    List<Orderlist> listObj;
    private String[] orderId;
    private String[] rentTime;
    private String[] rentDays;
    private String[] orderPrice;
    List<Map<String, Object>> listItems;
    private String url = ConnData.orderUrl;
    private String carState;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
//        listObj=  (List<Orderlist>) this.getIntent().getSerializableExtra("orderList");

        listObj=LoginActivity.ol;
        Log.i(TAG, LoginActivity.ol.toString());
        orderId=new String[listObj.size()];
        rentTime=new String[listObj.size()];
        rentDays=new String[listObj.size()];
        orderPrice=new String[listObj.size()];
        for(int i=0;i<listObj.size();i++){
            orderId[i]= String.valueOf(listObj.get(i).getOrderid());
        }
        for(int i=0;i<listObj.size();i++){
            rentTime[i]= String.valueOf(listObj.get(i).getRentTime().substring(0,10));
        }
        for(int i=0;i<listObj.size();i++){

            rentDays[i]= String.valueOf(listObj.get(i).getRentdays());
        }
        for(int i=0;i<listObj.size();i++){
            orderPrice[i]= String.valueOf(listObj.get(i).getOrderPrice());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderadapter_main);
        // 创建一个List集合，List集合的元素是Map
        listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < listObj.size(); i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("rentTime", rentTime[i]);
            listItem.put("rentDays", rentDays[i]);
            listItem.put("orderPrice", orderPrice[i]);
            listItems.add(listItem);
        }
        Log.i(TAG,listItems.toString()+"---");
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.order_item,
                new String[] { "rentTime", "rentDays" , "orderPrice"},
                new int[] { R.id.rentTime, R.id.rentDays , R.id.orderPrice});
        ListView list = (ListView) findViewById(R.id.orderlist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器
        list.setOnItemClickListener(new OnItemClickListener() {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Map<String,String> map=new HashMap<String,String>();
                map.put("method","showOrderDetail");
                map.put("orderid", orderId[position]);
                showDetail(map);
                //设置当前Activity的结果码

            }



        });
        // 为ListView的列表项的选中事件绑定事件监听器

    }

    public void showDetail(Map<String, String> mMap) {
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    String showOrderDetailState = response.getString("showOrderDetailState");
                    Log.i(TAG, showOrderDetailState);

                    switch (showOrderDetailState) {
                        case "200":
                            Toast.makeText(OrderListAdapterActivity.this, "查询成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(OrderListAdapterActivity.this,OrderDetailActivity.class);
                            JSONObject detail=response.getJSONObject("CompleteOrderDetail");
                            CompleteOrderDetail orderDetail=CompleteOrderDetail.parseCompleteOrderDetailJSON(detail);
                            intent.putExtra("CompleteOrderDetail", (Serializable) orderDetail);
                            startActivity(intent);
                            break;
                        case "10":
                            Log.i(TAG,"+++++++++++++++");
                            Toast.makeText(OrderListAdapterActivity.this, "查询失败",
                                    Toast.LENGTH_SHORT).show();
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
                Toast.makeText(OrderListAdapterActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();
            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);
    }
}
