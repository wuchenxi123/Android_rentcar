package com.company.rentcar.daoIml;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonArrayPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CarInfoMange {
    private Map<String, Object> mMap;// 存储网络请求提交的参数
    private String url = ConnData.URL;
    private String carState;

    private ArrayList<CarInfo> allCar;

    public ArrayList<CarInfo> getAllCar(final Context context) {

        mMap = new HashMap<String, Object>();

        // 请求服务
        return null;
    }
}
