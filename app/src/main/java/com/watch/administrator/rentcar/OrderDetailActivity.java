package com.watch.administrator.rentcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import com.company.rentcar.model.CompleteOrderDetail;
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailActivity extends FragmentActivity {
    private final static String TAG="OrderDetailActivity";
    private TextView rentcardate,rentcarplace,returncardate,
            returncarplace,ensuremoney,sendcar,takecar,rentcarcost,rentcar,driverPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        rentcardate=(TextView)findViewById(R.id.rentcardate);
        rentcarplace=(TextView)findViewById(R.id.rentcarplace);
        returncardate=(TextView)findViewById(R.id.returncardate);
        returncarplace=(TextView)findViewById(R.id.returncarplace);
        ensuremoney=(TextView)findViewById(R.id.ensuremoney);
        sendcar=(TextView)findViewById(R.id.sendcar);
        takecar=(TextView)findViewById(R.id.takecar);
        rentcarcost=(TextView)findViewById(R.id.rentcarcost);
        rentcar=(TextView)findViewById(R.id.rentcar);
        driverPerson=(TextView)findViewById(R.id.driverPerson);
        init();
    }
    public void init(){
        Intent intent=OrderDetailActivity.this.getIntent();
        CompleteOrderDetail orderDetail= (CompleteOrderDetail) intent.getSerializableExtra("CompleteOrderDetail");
        Log.i(TAG, orderDetail.getCarLpnum() + "---");
        rentcardate.setText(orderDetail.getRentTakeTime().substring(0,10));
        rentcarplace.setText(orderDetail.getRentPlace());
        returncardate.setText(orderDetail.getReturnReTime().substring(0,10));
        returncarplace.setText(orderDetail.getReturnPlace());
        ensuremoney.setText(orderDetail.getInsurePrice() + " 元");
        sendcar.setText(orderDetail.getCarTakeType()==1?"50元（送车上门）":"0元（送达指定地点）");
        takecar.setText(orderDetail.getReturnType()==1?"50元（上门取车）":"0元（送达指定地点）");
        rentcarcost.setText(orderDetail.getOrderPrice()+" 元");
        rentcar.setText(orderDetail.getCarBand()+"  "+orderDetail.getCarLpnum());
        driverPerson.setText(orderDetail.getDrivername()+"  "+orderDetail.getDriverMobie());
    }

}
