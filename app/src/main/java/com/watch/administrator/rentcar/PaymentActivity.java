package com.watch.administrator.rentcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.model.RentOrderView;
import com.company.rentcar.model.User;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */

public class PaymentActivity extends Activity {
    private final static String TAG = "PaymentActivity";
    String userId = "";
    String orderId = "";
    String userRemainder = "";
    private CheckBox useremaining;
    private boolean check = false;
    private RadioButton alipay;
    private RadioButton wechat;

    private boolean alipay_check = false;
    private boolean wechat_check = false;

    private Map<String, String> mMap;
    private String payOrderState = "";
    private String url = ConnData.carInfo;

    private Button payButton;

    String orderPrice = "";
    private TextView paymoney;

    private RadioGroup payWay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);//这里使用了上面创建的xml文件（Tab页面的布局）
        useremaining = (CheckBox) findViewById(R.id.useremaining);

        alipay = (RadioButton) findViewById(R.id.alipay);
        wechat = (RadioButton) findViewById(R.id.wechat);


        paymoney = (TextView) findViewById(R.id.paymoneys);
        alipay_check = alipay.isChecked();
        wechat_check = wechat.isChecked();
        init();

        if (Float.parseFloat(userRemainder) > 0) {
            useremaining.setVisibility(View.VISIBLE);

            useremaining.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {

                        if (Float.parseFloat(userRemainder) > Float.parseFloat(orderPrice)) {
                            paymoney.setText(0+"");
                        } else {
                            paymoney.setText(String.valueOf(Float.parseFloat(orderPrice)-Float.parseFloat(userRemainder)));
                        }
                    }else{
                            paymoney.setText(orderPrice);
                    }
                    check=isChecked;
                }
            });
        }else{
            Toast.makeText(PaymentActivity.this,
                    "余额不足请充值", Toast.LENGTH_SHORT)
                    .show();
            useremaining.setVisibility(View.INVISIBLE);
            useremaining.setEnabled(false);
        }

        payWay= (RadioGroup) findViewById(R.id.payWay);
        payWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.alipay) {
                    Toast.makeText(PaymentActivity.this, "您选择的是支付宝", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentActivity.this, "您选择的是微信", Toast.LENGTH_SHORT).show();
                }
            }
        });
        payButton = (Button) findViewById(R.id.pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.pay: {
                        pay(orderId, check, alipay_check || wechat_check || check);
                    }
                }
            }
        });

        Log.i(TAG, userId + "===" + orderId + check);

    }

    private void pay(String orderId, boolean check, boolean payWay) {

        mMap = new HashMap<String, String>();
        mMap.put("method", "payOrder");

        mMap.put("orderid", orderId);
        mMap.put("payflag", check ? 1 + "" : 0 + "");
//        mMap.put("payflag", 1+"");
//        if (!payWay) {
//            Toast.makeText(PaymentActivity.this,
//                    "请选择支付方式或选择支付余额！", Toast.LENGTH_SHORT)
//                    .show();
//        }
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    payOrderState = response.getString("payOrderState");
                    Log.i(TAG, payOrderState + "===========");
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (payOrderState) {
                        case "200":
                            Toast.makeText(PaymentActivity.this,
                                    "支付成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            Log.i(TAG,"==============+");

                            String orderId = response.getString("orderid");

                            JSONObject user= response.getJSONObject("driver");
                            User driver=parseUserJSON(user);
                            Log.i(TAG,driver+"------");

                            JSONObject car= response.getJSONObject("carinfo");
                            CarInfo carInfo=parseCarInfoJSON(car);
                            Log.i(TAG,carInfo+"----");

                            JSONObject rentOrderjs= response.getJSONObject("rentOrderView");
                            RentOrderView rentOrderView=parseRentOrderViewJSON(rentOrderjs);
                            Log.i(TAG, carInfo + "----" + driver + "------" + rentOrderView);

                            Intent intent = new Intent(
                                    PaymentActivity.this,
                                    IndexActivity.class);
                            intent.putExtra("carInfo",(Serializable)carInfo);
                            intent.putExtra("driver",(Serializable)driver);
                            intent.putExtra("rentOrderView",(Serializable)rentOrderView);

                            intent.putExtra("userId",  LoginActivity.userId);
                            intent.putExtra("orderId", orderId);
                            intent.putExtra("flag",1+"");
                            startActivity(intent);

                            break;
                        case "10":
                            Toast.makeText(PaymentActivity.this,
                                    "支付失败", Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        case "20":
                            Toast.makeText(PaymentActivity.this,
                                    "余额不足，支付失败", Toast.LENGTH_SHORT)
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
                Toast.makeText(PaymentActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

    private void init() {
        Intent intent = PaymentActivity.this.getIntent();

        userId = intent.getStringExtra("userId");
        orderId = intent.getStringExtra("orderId");
        userRemainder = intent.getStringExtra("userRemainder");
        orderPrice = intent.getStringExtra("orderPrice");
        paymoney.setText(orderPrice);
        Log.i(TAG, userRemainder + "==============");

    }


    private User parseUserJSON(JSONObject userJs) {
        User user = new User();
        try {
                user.setUserId(userJs.getInt("userId"));
                user.setUserName(userJs.getString("userName"));
                user.setUserMobile(userJs.getString("userMobile"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }

        return user;
    }

    private CarInfo parseCarInfoJSON(JSONObject carJs) {
        CarInfo car = new CarInfo();
        try {
            car.setCarId(carJs.getInt("carId"));
            car.setCarLpnum(carJs.getString("carLpnum"));
            car.setCarPictUrl(carJs.getString("carPictUrl"));
            car.setCarRentPri(carJs.getDouble("carRentPri"));
            car.setInsurePrice(carJs.getDouble("insurePrice"));
            car.setScsmPrice(carJs.getDouble("scsmPrice"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }

        return car;
    }

    private RentOrderView parseRentOrderViewJSON(JSONObject orderJs) {
        RentOrderView order = new RentOrderView();
        try {
           order.setOrderId(orderJs.getInt("orderId"));
            order.setRentPlace(orderJs.getString("rentPlace"));
            order.setRentTakeTime(orderJs.getString("rentTakeTime"));
            order.setOrderPrice(orderJs.getDouble("orderPrice"));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }

        return order;
    }
}