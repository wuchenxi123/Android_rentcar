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
import com.company.rentcar.url.ConnData;
import com.company.rentcar.util.JsonObjectPostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReturnEnsuringActivity extends BaseActivity {
    private final static String TAG = "ReturnEnsuringActivity";
    private String orderid,returnid,predictMoney,carLpnum,carRentPrice,
            presentPrice,takeCartype,returnPlace,setReturnTime,overspend,orderPrice;
    private EditText currentcarlpnum,returntype,returnaddress;
    private TextView t_currentprice,t_predictprice,rtRentPrice,returnaccepted_time;
    private Button phonereturn,next_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_info_ensuring);
        currentcarlpnum=(EditText)findViewById(R.id.currentcarlpnum);
        returntype=(EditText)findViewById(R.id.returntype);
        returnaddress=(EditText)findViewById(R.id.returnaddress);

        rtRentPrice= (TextView) findViewById(R.id.price);
        t_currentprice=(TextView)findViewById(R.id.t_currentprice);
        t_predictprice=(TextView)findViewById(R.id.t_predictprice);
        returnaccepted_time=(TextView)findViewById(R.id.returnaccepted_time);

        phonereturn=(Button)findViewById(R.id.phonereturn);
        next_bt=(Button)findViewById(R.id.next_bt);
        init();

        View.OnClickListener listenerBt=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.phonereturn:{
                        Intent in2 = new Intent();
                        in2.setAction(Intent.ACTION_CALL);
                        in2.setData(Uri.parse("tel:18734917692"));
                        startActivity(in2);
                        break;
                    }
                    case R.id.next_bt:{
                        Intent intentNt = new Intent(
                                ReturnEnsuringActivity.this,
                                ReturnAcceptActivity.class);
                        intentNt.putExtra("orderid",orderid);
                        intentNt.putExtra("returnid", returnid);
                        intentNt.putExtra("orderPrice", orderPrice);
                        intentNt.putExtra("predictMoney", predictMoney);//已产生费用
                        intentNt.putExtra("overspend", overspend);//超支费用
                        intentNt.putExtra("carLpnum", carLpnum);
                        intentNt.putExtra("carRentPrice", carRentPrice);//租车费用
                        intentNt.putExtra("presentPrice", presentPrice);//
                        intentNt.putExtra("takeCartype", takeCartype);
                        intentNt.putExtra("returnPlace", returnPlace);
                        intentNt.putExtra("setReturnTime", setReturnTime);
                        startActivity(intentNt);
                        break;
                    }
                }
            }
        };
        phonereturn.setOnClickListener(listenerBt);
        next_bt.setOnClickListener(listenerBt);
    }
    private void init(){
        Intent intent = ReturnEnsuringActivity.this.getIntent();

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

        currentcarlpnum.setText(carLpnum);
        returntype.setText("0".equals(takeCartype)?"上门还车":"指定地点还车");
        returnaddress.setText(returnPlace);
        rtRentPrice.setText(carRentPrice+" /天");
        t_currentprice.setText(presentPrice);
        t_predictprice.setText(Float.parseFloat(overspend)+Float.parseFloat(orderPrice)+"");
        returnaccepted_time.setText(setReturnTime);

        Log.i(TAG, orderid + "---" + returnid + "----" + predictMoney + "----" +
                overspend + "---" + carLpnum + "==" + carRentPrice + "==" + presentPrice +
                "--" + takeCartype + "---" + returnPlace + "===" + setReturnTime);
    }

}
