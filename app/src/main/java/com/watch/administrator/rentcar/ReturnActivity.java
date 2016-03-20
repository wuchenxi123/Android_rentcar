package com.watch.administrator.rentcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;
import com.company.rentcar.util.StringSub;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class ReturnActivity extends FragmentActivity {
    private final static String TAG = "ReturnActivity";
    public static long countDays=0;
    private int year;
    private int month;
    private int day;
    private TextView licensenumber, totalprice, deliveryplace, returndateShow, presentPrice, rentTime;
    private Spinner returntype, returnaddress;
    private Button submit, phonereturn;
    private DatePicker returndate;

    String takeWayStr = "";
    String sendAddressStr = "";
    int takeCartype=0;
    int returnPrice = 0;
    String[] take = {"上门还车", "送到指定地点"};
    String[] send = {"公司", "长沙火车站", "长沙火车南站", "长沙机场", "长沙市天心区韶山路88号"};
    CarInfo carInfo;

    String rentTimeStr;
    String orderid;
    String carRentPrice;
    String predictReturnTime;
    String returnPlace;
    String setReturnTime;

    String carLpnum;
    String presentPriceStr;
    String orderPrice;

    private Map<String,String> mMap;
    private String returnCarState = "";
    private String url = ConnData.returnCar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_car);
        licensenumber = (TextView) findViewById(R.id.licensenumber);
        totalprice = (TextView) findViewById(R.id.totalprice);
        presentPrice = (TextView) findViewById(R.id.presentPrice);
        deliveryplace = (TextView) findViewById(R.id.deliveryplace);
        returndateShow = (TextView) findViewById(R.id.returndateShow);
        rentTime = (TextView) findViewById(R.id.rentTime);

        returndate = (DatePicker) findViewById(R.id.returndate);

        returntype = (Spinner) findViewById(R.id.returntype);
        returnaddress = (Spinner) findViewById(R.id.returnaddress);

        returntype.setSelection(0, true);
        returnaddress.setSelection(0, true);
        submit = (Button) findViewById(R.id.submit);
        phonereturn = (Button) findViewById(R.id.phonereturn);


        returntype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                if (take[position].trim().toString().equals("上门还车")) {
                    sendAddressStr = "公司";
                    returntype.setVisibility(View.VISIBLE);
                    returnaddress.setSelection(0, false);
                    returnaddress.setVisibility(View.INVISIBLE);
                    returnaddress.setEnabled(false);
                    returnPrice = 0;
                    deliveryplace.setText(0 + " 元");
                } else {
                    returnaddress.setEnabled(true);
                    returnaddress.setSelection(1, true);
                    returnaddress.setVisibility(View.VISIBLE);
                    returnPrice = 50;
                    deliveryplace.setText(50 + " 元");
                }
                takeCartype = returntype.getSelectedItemPosition() == 0 ? 0 : 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        returnaddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendAddressStr = send[position].trim().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.i(TAG, takeCartype + "-------" + sendAddressStr);


        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.phonereturn: {
                        Intent in2 = new Intent();
                        in2.setAction(Intent.ACTION_CALL);
                        in2.setData(Uri.parse("tel:18734917692"));
                        startActivity(in2);
                        break;
                    }
                    case R.id.submit: {
                        setReturnTime=returndateShow.getText().toString()+" 00:00";
                        submit();
                        break;
                    }
                }
            }
        };
        phonereturn.setOnClickListener(listener);
        submit.setOnClickListener(listener);

    }


    private void init() throws ParseException {
        Intent intent = ReturnActivity.this.getIntent();
        orderid=intent.getStringExtra("orderId");
        carRentPrice=intent.getStringExtra("carRentPrice");
        returnPlace=intent.getStringExtra("carRentPrice");



        carLpnum = intent.getStringExtra("carLpnum");
        presentPriceStr = intent.getStringExtra("presentPrice");
        orderPrice = intent.getStringExtra("orderPrice");
        rentTimeStr = intent.getStringExtra("rentTime");
        predictReturnTime = intent.getStringExtra("predictReturnTime");
        Log.i(TAG, carLpnum + "----" + presentPriceStr + "------" + orderPrice + "---" + rentTimeStr + "===" + predictReturnTime);

        licensenumber.setText(carLpnum);
        totalprice.setText("预计产生:"+orderPrice+" 元");
        presentPrice.setText("已产生:"+presentPriceStr+" 元");
        rentTime.setText(rentTimeStr);
        returndateShow.setText(predictReturnTime);
        Log.i(TAG, predictReturnTime);
        Calendar calendar = new StringSub().sub(predictReturnTime);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
        // 初始化DatePicker组件，初始化时指定监听器
        returndate.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                ReturnActivity.this.year = year;
                ReturnActivity.this.month = month;
                ReturnActivity.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day);
            }
        });

    }

    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month, int day) {
        returndateShow.setText(year + "-" + (month + 1) + "-" + day);
    }

    public void submit() {
        Log.i(TAG,"+++++++++++++++++++++++++++++++");

        Log.i(TAG, rentTimeStr + "===" + orderid + "--" + carRentPrice+"---"+predictReturnTime+"--"+returnPlace+"---"+setReturnTime+"--"+takeCartype);

        returnPlace=takeCartype==0?"公司":sendAddressStr;
        mMap = new HashMap<String, String>();
        mMap.put("method","returnCar");

        mMap.put("rentTakeTime", rentTimeStr);
        mMap.put("orderid",orderid);
        mMap.put("carRentPrice",carRentPrice);
        mMap.put("predictReturnTime",predictReturnTime);
        mMap.put("returnPlace",returnPlace);
        mMap.put("setReturnTime",setReturnTime);
        mMap.put("returnType",takeCartype+"");
        mMap.put("carLpnum",carLpnum);
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                Log.i(TAG,takeCartype+"-----------+"+response.getString("overSpendState")+"==="+response.optString("returnCarState")+"===="+response.toString());
                    returnCarState = response.optString("returnCarState");

                    Log.i(TAG, returnCarState+"-------------");

                    switch (returnCarState) {
                        case "300":
                            countDays=new StringSub().countDays(rentTimeStr,setReturnTime);
                            Toast.makeText(ReturnActivity.this,
                                    "还车成功！", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(
                                    ReturnActivity.this,
                                    ReturnEnsuringActivity.class);

                            String overSpendState=response.getString("overSpendState");

                            String predictMoney="0";
                            String overspend="0";
                            switch (overSpendState){
                                case "0":{
                                    predictMoney="0";
                                    overspend="0";
                                    break;
                                }
                                case "1":{
                                    predictMoney=response.getString("predictMoney");
                                    overspend=response.getString("overspend");
                                    break;
                                }
                            }
                            String rtorderid=response.getString("orderid");
                            String returnid=response.getString("returnid");

                            intent.putExtra("orderid",rtorderid);
                            intent.putExtra("orderPrice",orderPrice);
                            intent.putExtra("returnid",returnid);
                            intent.putExtra("predictMoney",predictMoney);
                            intent.putExtra("overspend",overspend);
                            intent.putExtra("carLpnum",carLpnum);
                            intent.putExtra("carRentPrice",carRentPrice);
                            intent.putExtra("presentPrice",presentPriceStr);

                            intent.putExtra("takeCartype",takeCartype+"");
                            intent.putExtra("returnPlace",returnPlace);

                            intent.putExtra("setReturnTime",setReturnTime);

                            startActivity(intent);
                            break;
                        case "30":
                            Toast.makeText(ReturnActivity.this,
                                    "设置还车时间出错！", Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        case "20":
                            Toast.makeText(ReturnActivity.this,
                                    "还车失败！", Toast.LENGTH_SHORT)
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
                Toast.makeText(ReturnActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);
    }

}