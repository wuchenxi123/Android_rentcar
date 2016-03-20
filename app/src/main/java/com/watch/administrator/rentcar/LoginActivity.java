package com.watch.administrator.rentcar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private final static String TAG="LoginActivity";
    private Button register_button, login_button;
    private EditText login_phone, login_password;
    private TextView forget_password;
    private Map<String,String> map;
    ProgressDialog progressDialog;
    private String loginState = "";
    public static String userId;
    private String url = ConnData.URL;
    static CarInfo car=new CarInfo();
    public static List<Orderlist> ol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        register_button = (Button) findViewById(R.id.regist_button);
        login_button = (Button) findViewById(R.id.login_button);

        login_phone= (EditText) findViewById(R.id.login_phone);
        login_password = (EditText) findViewById(R.id.login_password);

        login_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (login_phone.getText().toString().equals(null) || login_phone.getText().toString().isEmpty()) {
                    login_password.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.regist_button: {
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, RegisterActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case R.id.login_button: {
                        String userTel = login_phone.getText().toString().trim();
                        String password = login_password.getText().toString().trim();

                        if (userTel.isEmpty() || userTel.equals(null)) {
                            Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                        } else if (password.isEmpty() || password.equals(null)) {
                            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                        } else {
                            login(userTel, password);
                        }
                        break;
                    }

                }
            }
        };
        login_button.setOnClickListener(listener);
        register_button.setOnClickListener(listener);

        forget_password= (TextView) findViewById(R.id.login_forget_text);

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, AlterPwdActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login(String userTel, String password) {

        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);//设置读取进度条在屏幕中是否可点击，false表不可以点击
        progressDialog.setIndeterminate(false);
        progressDialog.setMessage("正在登陆...");
        progressDialog.show();

        map=new HashMap<String,String>();
        map.put("method", "login");
        map.put("userAccount", userTel);
        map.put("userPassword", password);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(2000);
                    progressDialog.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                loginResponse();
            }
        }.start();
    }

    public void loginResponse(){
        //请求服务
        Log.i("Url", url + "------------");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectPostRequest jsonObjectPostRequest=new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response == null) {

                        Toast.makeText(LoginActivity.this, "没有得到数据",
                                Toast.LENGTH_SHORT).show();

                    } else {
                        loginState = response.getString("loginState");

                        switch (loginState) {
                            case "200":
                                Toast.makeText(LoginActivity.this, "登录成功",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this,IndexActivity.class);
                                userId=response.getString("userId");
                                String orderStatus=response.getString("orderStatus");
                                Log.i(TAG, "---" + orderStatus + "---");
                                switch (orderStatus){
                                    //租车
                                    case "0":{
                                        Log.i(TAG, "============");
                                        intent.putExtra("flag", 0 + "");
                                        break;
                                    }
                                    //还车
                                    case "1":{
                                        JSONObject user= response.getJSONObject("driver");
                                        User driver=parseUserJSON(user);
                                        Log.i(TAG,driver+"------");

                                        JSONObject car= response.getJSONObject("carInfo");
                                        CarInfo carInfo=parseCarInfoJSON(car);
                                        Log.i(TAG,carInfo+"----");

                                        JSONObject rentOrderjs= response.getJSONObject("rentOrderView");
                                        RentOrderView rentOrderView=parseRentOrderViewJSON(rentOrderjs);
                                        Log.i(TAG, carInfo + "----" + driver + "------" + rentOrderView);

                                        intent.putExtra("carInfo", (Serializable) carInfo);
                                        intent.putExtra("driver",(Serializable)driver);
                                        intent.putExtra("rentOrderView",(Serializable)rentOrderView);

                                        intent.putExtra("flag", 1 + "");
                                        break;
                                    }
                                }

                                intent.putExtra("userId",userId);
                                JSONArray carlistarray=response.getJSONArray("orderlist");
                                intent.putExtra("orderFlag",1);
                                ol=new Orderlist().parseJSON(carlistarray);
                                intent.putExtra("orderlist",(Serializable)ol);
//                                intent.putExtra("carlist",(Serializable)ol);
                                Log.i(TAG, userId + "============");
                                startActivity(intent);
                                finish();
                                break;
                            case "10":
                                Toast.makeText(LoginActivity.this, "未知错误",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case "20":
                                Toast.makeText(LoginActivity.this, "密码为空",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case "30":
                                Toast.makeText(LoginActivity.this,
                                        "用户账号输入不规范", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case "40":
                                Toast.makeText(LoginActivity.this,
                                        "用户账号为空", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case "50":
                                Toast.makeText(LoginActivity.this, "密码错误",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case "60":
                                Toast.makeText(LoginActivity.this,
                                        "账号不存在，手机号未注册", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case "70":
                                Toast.makeText(LoginActivity.this,
                                        "账号不存在，用户名不存在", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            default:
                                break;
                        }

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();
            }
        }, map);
        requestQueue.add(jsonObjectPostRequest);
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == -1) {
                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0) {
                Toast.makeText(LoginActivity.this, "服务器正忙", Toast.LENGTH_SHORT).show();
            }
        }
    };

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
