package com.watch.administrator.rentcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.company.rentcar.model.Orderlist;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReturnAcceptActivity extends BaseActivity {
    private final static String TAG = "ReturnAcceptActivity";
    private String orderid,returnid,predictMoney,carLpnum,carRentPrice,
            presentPrice,takeCartype,returnPlace,setReturnTime,overspend,orderPrice;
    private EditText returntype,returnaddress;
    private TextView licensenumber,totalprice,returnaccepted_time,deliveryplace,remain;
    private Button phoneinquiry,pay;

    private String payOverSpendState="";
    private Map<String, Object> mMap;// 存储网络请求提交的参数
    private String url = ConnData.returnCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_accepted);
        returntype=(EditText)findViewById(R.id.returntype);
        returnaddress=(EditText)findViewById(R.id.returnaddress);

        licensenumber=(TextView)findViewById(R.id.licensenumber);
        totalprice=(TextView)findViewById(R.id.totalprice);
        returnaccepted_time=(TextView)findViewById(R.id.returnaccepted_time);
        deliveryplace=(TextView)findViewById(R.id.deliveryplace);
        remain=(TextView)findViewById(R.id.remain);

        phoneinquiry=(Button)findViewById(R.id.phoneinquiry);
        pay=(Button)findViewById(R.id.pay);
        init();
        View.OnClickListener listenerBt=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.phoneinquiry:{
                        Intent in2 = new Intent();
                        in2.setAction(Intent.ACTION_CALL);
                        in2.setData(Uri.parse("tel:18734917692"));
                        startActivity(in2);
                        break;
                    }
                    case R.id.pay:{
                        pay();
                        break;
                    }
                }
            }
        };
        phoneinquiry.setOnClickListener(listenerBt);
        pay.setOnClickListener(listenerBt);
    }
    private void init(){
        Intent intent = ReturnAcceptActivity.this.getIntent();

        orderid = intent.getStringExtra("orderid");
        returnid = intent.getStringExtra("returnid");
        orderPrice=intent.getStringExtra("orderPrice");
        predictMoney = intent.getStringExtra("predictMoney");//已产生费用
        overspend = intent.getStringExtra("overspend");//超支费用
        carLpnum = intent.getStringExtra("carLpnum");
        carRentPrice = intent.getStringExtra("carRentPrice");//租车费用
        presentPrice = intent.getStringExtra("presentPrice");//
        takeCartype = intent.getStringExtra("takeCartype");
        returnPlace = intent.getStringExtra("returnPlace");
        setReturnTime = intent.getStringExtra("setReturnTime");

        String remainMoney="0".equals(takeCartype)?"0":"50";
        licensenumber.setText(carLpnum);
        returntype.setText("0".equals(takeCartype)?"上门还车":"指定地点还车");
        returnaddress.setText(returnPlace);

        totalprice.setText("已支付："+orderPrice+" "+"未支付："+overspend);
        returnaccepted_time.setText(setReturnTime);
        deliveryplace.setText(remainMoney + " 元");
        remain.setText(remainMoney);

        Log.i(TAG, orderid + "---" + returnid + "----" + predictMoney + "----" +
                overspend + "---" + carLpnum + "==" + carRentPrice + "==" + presentPrice +
                "--" + takeCartype + "---" + returnPlace + "===" + setReturnTime);
    }
    public void pay() {

        mMap = new HashMap<String, Object>();
        mMap.put("method","payReturn");

        mMap.put("orderid", orderid);
        mMap.put("userid", LoginActivity.userId);
        mMap.put("orderPrice", orderPrice);
        mMap.put("overSpend", overspend);
        mMap.put("carLpnum",carLpnum);
        Log.i(TAG,ReturnActivity.countDays+"");
        mMap.put("rentDays",ReturnActivity.countDays+"");
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    payOverSpendState = response.getString("payOverSpendState");

                    Log.i(TAG, payOverSpendState+"--------");

                    switch (payOverSpendState) {
                        case "200":
                            String driverName = response.getString("driverName");
                            String driverMobile = response.getString("driverMobile");
                            Toast.makeText(ReturnAcceptActivity.this,
                                    "支付成功！", Toast.LENGTH_SHORT)
                                    .show();
                            Intent intent = new Intent(
                                    ReturnAcceptActivity.this,
                                    IndexActivity.class);
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

                            intent.putExtra("userId",IndexActivity.userIdApp);
                            intent.putExtra("driverName",driverName);
                            intent.putExtra("driverMobile",driverMobile);

                            intent.putExtra("flag",2+"");
                            JSONArray carlistarray=response.getJSONArray("orderlist");
                            LoginActivity.ol=new Orderlist().parseJSON(carlistarray);
                            startActivity(intent);
                            break;
                        case "20":
                            Toast.makeText(ReturnAcceptActivity.this,
                                    "支付失败", Toast.LENGTH_SHORT)
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
                Toast.makeText(ReturnAcceptActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

}
