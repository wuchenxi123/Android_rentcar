package com.watch.administrator.rentcar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class OrderAcceptActivity extends AppCompatActivity {
    private final static String TAG="OrderAcceptActivity";
    private EditText takeplace,taketime,choosecar;
    private TextView totalrentprice,totalinsureprice,delivercartotalprice,totalprice;

    private Button phoneinquiry,downpayment;

    String orderId;
    String userId;
    String rentPlace;
    String rentTakeTime;
    String carLpnum;
    String carRentPri;
    String insurePrice;
    String scsmPrice;
    String orderPrice;

    private Map<String, String> mMap;
    private String getRemainderStatus = "";
    private String url = ConnData.URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_accepted);
        init();
        takeplace= (EditText) findViewById(R.id.takeplace);
        taketime= (EditText) findViewById(R.id.taketime);
        choosecar=(EditText) findViewById(R.id.choosecar);

        takeplace.setText(rentPlace);
        taketime.setText(rentTakeTime);
        choosecar.setText(carLpnum);

        totalrentprice= (TextView) findViewById(R.id.totalrentprice);
        totalinsureprice=(TextView) findViewById(R.id.totalinsureprice);
        delivercartotalprice=(TextView) findViewById(R.id.delivercar);
        totalprice= (TextView) findViewById(R.id.totalprice);
        totalrentprice.setText(carRentPri+"元");
        totalinsureprice.setText(insurePrice+"元");
        delivercartotalprice.setText(scsmPrice+"元");
        totalprice.setText(orderPrice+"元");

        phoneinquiry= (Button) findViewById(R.id.phoneinquiry);
        downpayment= (Button) findViewById(R.id.downpayment);

        View.OnClickListener listener=new View.OnClickListener() {
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
                    case R.id.downpayment:{
                        getRemainder();
                        break;
                    }
                }
            }
        };
        phoneinquiry.setOnClickListener(listener);
        downpayment.setOnClickListener(listener);
    }
    //update tb_car set car_flag=0 WHERE car_id=1;
    private void init(){
        Intent intent=OrderAcceptActivity.this.getIntent();
        orderId=intent.getStringExtra("orderId");
        userId=intent.getStringExtra("userId");
        Log.i(TAG,orderId+"-------------"+userId);
        rentPlace=intent.getStringExtra("rentPlace");
        rentTakeTime=intent.getStringExtra("rentTakeTime");
        carLpnum=intent.getStringExtra("carLpnum");
        carRentPri=intent.getStringExtra("carRentPri");
        insurePrice=intent.getStringExtra("insurePrice");
        scsmPrice=intent.getStringExtra("scsmPrice");
        Log.i(TAG, carRentPri + "-=---" + insurePrice + "---" + scsmPrice + "----" + rentPlace);
        orderPrice=intent.getStringExtra("orderPrice");

    }
    private void getRemainder(){
        mMap = new HashMap<String, String>();
        mMap.put("method", "getRemainder");

        mMap.put("userId", LoginActivity.userId);

        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    getRemainderStatus = response.getString("getRemainderStatus");
                    Log.i(TAG, getRemainderStatus + "===========");
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (getRemainderStatus) {
                        case "200":
                            Toast.makeText(OrderAcceptActivity.this,
                                    "余额获取成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            String userRemainder = response.getString("userRemainder");
                            Log.i(TAG, userRemainder + "-------------");
                            Intent intent = new Intent();
                            intent.putExtra("userRemainder",userRemainder);
                            intent.putExtra("orderId",orderId);
                            intent.putExtra("userId",  LoginActivity.userId);
                            intent.putExtra("orderPrice", orderPrice);
                            intent.setClass(OrderAcceptActivity.this, PaymentActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "30":
                            Toast.makeText(OrderAcceptActivity.this,
                                    "获取用户账户余额失败", Toast.LENGTH_SHORT)
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
                Toast.makeText(OrderAcceptActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }

}
