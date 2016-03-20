package com.watch.administrator.rentcar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.model.CarInfo;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity{


    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    DatePicker datePicker;
    TimePicker timePicker;

    private static final String TAG = "MainActivity";
    private EditText choosecar;
    private TextView rentCarPrice, rentPricetv;
    int index = 1;
    String[] items = {};
    private Map<String, String> mMap = null;// 存储网络请求提交的参数
    private String url = ConnData.carInfo;
    private String carState = "";

    String userId;
    private List<CarInfo> allCar = new ArrayList();
    List<CarInfo> al;

    private Spinner takeWay;
    private Spinner sendAddress;
    private boolean isAutoSelect = true;
    private boolean isAutoSelect1 = true;
    String takeWayStr = "";
    String sendAddressStr = "";
    String[] take = {"上门取车", "送到指定地点"};
    String[] send = {"公司", "长沙火车站", "长沙火车南站", "长沙机场", "长沙市天心区韶山路88号"};
    private EditText rentdays;
    private EditText show;

    private TextView rentCar_price;
    private TextView rent_price_tv;

    private TextView insure_price;
    private TextView insure_price_tv;
    private CheckBox insure_price_cb;
    private TextView buyInsure;

    private TextView csm_price;
    private TextView csm_price_tv;

    private TextView sum_price;
    private TextView sum_price_tv;
    boolean checked;


    CarInfo carInfo;
    int takeCartype;
    int sum=0;

    private Button submit;
    private Button phonebooking;

    private String rentCarSata="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datePicker = (DatePicker)
                findViewById(R.id.datePicker);
        timePicker = (TimePicker)
                findViewById(R.id.timePicker);
        takeWay = (Spinner) findViewById(R.id.takeplace);
        rentdays = (EditText) findViewById(R.id.rentdays);
        sendAddress = (Spinner) findViewById(R.id.takeaddress);

        // 获取当前的年、月、日、小时、分钟
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        hour = c.get(Calendar.HOUR);
        minute = c.get(Calendar.MINUTE);

        takeWay.setSelection(0, true);
        sendAddress.setSelection(0, true);
        takeWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                if (take[position].trim().toString().equals("上门取车")) {
                    sendAddressStr = "公司";
                    takeWay.setVisibility(View.VISIBLE);
                    sendAddress.setSelection(0, false);
                    sendAddress.setVisibility(View.INVISIBLE);
                    sendAddress.setEnabled(false);
                } else {
                    sendAddress.setEnabled(true);
                    sendAddress.setSelection(1, true);
                    sendAddress.setVisibility(View.VISIBLE);
                }
                takeCartype = takeWay.getSelectedItemPosition() == 0 ? 0 : 1;
                setVisible(carInfo, takeCartype);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendAddressStr = send[position].trim().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Log.i(TAG, takeCartype + "-------" + sendAddressStr);


        choosecar = (EditText) findViewById(R.id.choosecar);


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.choosecar: {
                        getAllCar();
                        break;
                    }
                }
            }
        };
        choosecar.setOnClickListener(listener);


        // 初始化DatePicker组件，初始化时指定监听器
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year
                    , int month, int day) {
                MainActivity.this.year = year;
                MainActivity.this.month = month;
                MainActivity.this.day = day;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
            }
        });
        timePicker.setEnabled(true);
        // 为TimePicker指定监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view
                    , int hourOfDay, int minute) {
                MainActivity.this.hour = hourOfDay;
                MainActivity.this.minute = minute;
                // 显示当前日期、时间
                showDate(year, month, day, hour, minute);
            }
        });


        rentCar_price = (TextView) findViewById(R.id.rentCar_price);
        rent_price_tv = (TextView) findViewById(R.id.rent_prices_tv);

        insure_price = (TextView) findViewById(R.id.insure_price);
        insure_price_tv = (TextView) findViewById(R.id.insure_price_tv);
        insure_price_cb = (CheckBox) findViewById(R.id.insure_price_cb);
        buyInsure = (TextView) findViewById(R.id.buyInsure);

        csm_price = (TextView) findViewById(R.id.scsm_price);
        csm_price_tv = (TextView) findViewById(R.id.scsm_price_tv);

        sum_price= (TextView) findViewById(R.id.sum_price);
        sum_price_tv= (TextView) findViewById(R.id.sum_price_tv);

        insure_price_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checked = isChecked;
                sum = setVisible(carInfo, takeCartype);
                Log.i(TAG, sum + "============");
            }
        });

        phonebooking= (Button) findViewById(R.id.phonebooking);
        submit= (Button) findViewById(R.id.submit);
        userId = this.getIntent().getStringExtra("userId");
        Log.i(TAG, userId + "=======+++++++++========");
        View.OnClickListener listenerBt=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.phonebooking:{
                        Intent in2 = new Intent();
                        in2.setAction(Intent.ACTION_CALL);
                        in2.setData(Uri.parse("tel:18734917692"));
                        startActivity(in2);
                        break;
                    }
                    case R.id.submit:{
                        submit(userId,takeCartype,sendAddressStr);
                        break;
                    }
                }
            }
        };
        phonebooking.setOnClickListener(listenerBt);
        submit.setOnClickListener(listenerBt);
    }

    public void getAllCar() {
        mMap = new HashMap<String, String>();
        mMap.put("method", "selectCar");

        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    carState = response.getString("carState");
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (carState) {
                        case "200":
                            Toast.makeText(MainActivity.this,
                                    "获取车辆信息成功！", Toast.LENGTH_SHORT)
                                    .show();
                            al = parseJSON(response.getJSONArray("carlist"));
                            items = new String[al.size()];
                            Log.i(TAG, al.toString() + "-------------" + items);
                            for (int i = 0; i < al.size(); i++) {
                                Log.i(TAG, al.get(i).getCarId() + "---" + al.get(i).getCarBand() + "-->" + al.get(i).getCarLpnum());
                                items[i] = (al.get(i).getCarId() + "---" + al.get(i).getCarBand() + "-->" + al.get(i).getCarLpnum()).toString();
                            }
                            Intent intent = new Intent();
                            intent.putExtra("allCar", (Serializable) al);
                            intent.setClass(MainActivity.this, SimpleAdapterActivity.class);
                            startActivityForResult(intent, 2);
                            Log.i(TAG, MainActivity.this.getIntent().getBundleExtra("og") + "_+_+_+_+_");
                            break;
                        case "30":
                            Toast.makeText(MainActivity.this,
                                    "网络中断", Toast.LENGTH_SHORT)
                                    .show();
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
                Toast.makeText(MainActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

    // 回调的方式来获取指定Activity返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        Log.i(TAG, "+_)_++++++++");

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1) {

            carInfo = (CarInfo) data.getSerializableExtra("car");
            Log.i(TAG, userId + "===" + carInfo.getCarId() + "--" + carInfo.getCarLpnum());
            Log.i(TAG, "===============+=======" + show.getText());
            Log.i(TAG, rentdays.getText().toString() + "----------");

            Log.i(TAG, takeWayStr + "----------" + sendAddressStr);
            takeCartype = takeWay.getSelectedItemPosition()==0 ? 0 : 1;
            choosecar.setText(carInfo.getCarBand() + "--" + carInfo.getCarLpnum());

            sum=setVisible(carInfo, takeCartype);
            Log.i(TAG,carInfo.getCarRentPri()+"=----=="+carInfo.getInsurePrice()+"==="+carInfo.getScsmPrice());
        }
    }

    private List<CarInfo> parseJSON(JSONArray items) {
        try {
            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                CarInfo carInfo = new CarInfo();
                carInfo.setCarId(item.getInt("carId"));
                carInfo.setCarContent(item.getString("carContent"));
                carInfo.setCarBand(item.getString("carBand"));
                carInfo.setCarColor(item.getString("carColor"));
                carInfo.setCarLpnum(item.getString("carLpnum"));

                carInfo.setCarRentPri(item.getDouble("carRentPri"));
                carInfo.setInsurePrice(item.getDouble("insurePrice"));
                carInfo.setScsmPrice(item.getDouble("scsmPrice"));

                carInfo.setCarPictUrl(item.getString("carPictUrl"));
                allCar.add(carInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.toString());
        }

        return allCar;
    }


    // 定义在EditText中显示当前日期、时间的方法
    private void showDate(int year, int month
            , int day, int hour, int minute) {
        show = (EditText) findViewById(R.id.show);
        show.setText(year + "-" + (month + 1) + "-" + day + " "
                + hour + ":" + minute);

    }

    private int setVisible(CarInfo carInfo, int type) {

        int rentPrice = (int) carInfo.getCarRentPri();
        int scsmPrice = type == 0 ? 0 : (int) carInfo.getScsmPrice();


        rentCar_price.setVisibility(View.VISIBLE);
        rent_price_tv.setVisibility(View.VISIBLE);
        rent_price_tv.setText(rentPrice + " /天");
        insure_price.setVisibility(View.VISIBLE);

        insure_price_tv.setVisibility(View.VISIBLE);
        insure_price_cb.setVisibility(View.VISIBLE);
        buyInsure.setVisibility(View.VISIBLE);

        csm_price.setVisibility(View.VISIBLE);
        csm_price_tv.setVisibility(View.VISIBLE);
        csm_price_tv.setText(scsmPrice+" /天");

        checked = insure_price_cb.isChecked();

        int insurePrice = checked ? (int) carInfo.getInsurePrice() : 0;
        insure_price_tv.setText(insurePrice+" /天");

        sum=rentPrice*Integer.parseInt(rentdays.getText().toString()) + scsmPrice + insurePrice;
        sum_price.setVisibility(View.VISIBLE);
        sum_price_tv.setVisibility(View.VISIBLE);
        sum_price_tv.setText(sum + "元");
        Log.i(TAG,sum+"==="+rentdays.getText().toString());
        return sum;
    }
    public void submit(final String userId, final int takeCartype, final String sendAddressStr) {
        Log.i(TAG,"+++++++++++++++++++++++++++++++");

        Log.i(TAG, userId + "===" + carInfo.getCarId() + "--" + carInfo.getCarLpnum());
        Log.i(TAG, "===============+=======" + show.getText());
        Log.i(TAG, rentdays.getText().toString() + "----------"+sum);

        Log.i(TAG, takeCartype + "----------" + sendAddressStr);
        final String rentPlace=takeCartype==0?"公司":sendAddressStr;
        final String rentTakeTime=show.getText().toString();
        String rentDays=rentdays.getText().toString();
        String carId=String.valueOf(carInfo.getCarId());
        final String userIds=userId;
        String insure=checked?insure_price.getText().toString():"0";
        String carTaketype=String.valueOf(takeCartype);
        final String orderPrice=String.valueOf(sum);

        mMap = new HashMap<String, String>();
        mMap.put("method","rentCar");

        mMap.put("rentPlace", rentPlace);
        mMap.put("takeTime",rentTakeTime);
        mMap.put("rentDays",rentDays);
        mMap.put("carId",carId);
        mMap.put("userId",LoginActivity.userId);
        mMap.put("carTaketype",carTaketype);
        mMap.put("orderPrice",orderPrice);
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    rentCarSata = response.getString("rentCarSata");

                    Log.i(TAG, rentCarSata+"-------------");
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (rentCarSata) {
                        case "200":
                            Toast.makeText(MainActivity.this,
                                    "租车成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            String orderid=response.getString("orderid");
                            Intent intent = new Intent(
                                    MainActivity.this,
                                    OrderAcceptActivity.class);
                            intent.putExtra("orderId",orderid);
                            intent.putExtra("userId",userIds);
                            intent.putExtra("rentPlace",rentPlace+"");
                            intent.putExtra("rentTakeTime",rentTakeTime);
                            intent.putExtra("carLpnum",carInfo.getCarLpnum());
                            intent.putExtra("carRentPri",carInfo.getCarRentPri()+"");
                            intent.putExtra("insurePrice",carInfo.getInsurePrice()+"");
                            intent.putExtra("scsmPrice",takeCartype==0?"0":carInfo.getScsmPrice()+"");

                            intent.putExtra("orderPrice", orderPrice);

                            startActivity(intent);
                            finish();
                            break;
                        case "10":
                            Toast.makeText(MainActivity.this,
                                    "租车失败！", Toast.LENGTH_SHORT)
                                    .show();
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
                Toast.makeText(MainActivity.this, "获取失败，请检查网络",
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
//                                MyAppalication.getInstance().exit();
                                Intent intent=new Intent();
                                intent.setClass(MainActivity.this,IndexActivity.class);
                                startActivity(intent);
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
