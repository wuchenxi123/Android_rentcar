package com.company.rentcar.daoIml;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.model.CompleteOrderDetail;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;
import com.watch.administrator.rentcar.MainActivity;
import com.watch.administrator.rentcar.OrderDetailActivity;
import com.watch.administrator.rentcar.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderMange {
    private Map<String, Object> mMap;// 存储网络请求提交的参数
    private String url = ConnData.orderUrl;
    private String carState;
private static String TAG="OrderMange";
    public CompleteOrderDetail Send(final Map<String, String> mMap, final Context context) {
        final CompleteOrderDetail[] order = {null};
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    String showOrderDetailState = response.getString("showOrderDetailState");
                    Log.i("registerState----", showOrderDetailState);

                    switch (showOrderDetailState) {
                        case "200":

                            JSONObject detail=response.getJSONObject("CompleteOrderDetail");
                            CompleteOrderDetail orderDetail=CompleteOrderDetail.parseCompleteOrderDetailJSON(detail);
                            order[0] =orderDetail;
                        case "10":

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);
        return order[0];
    }
}
