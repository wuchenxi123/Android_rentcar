package com.watch.administrator.rentcar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaidActivity extends BaseActivity {
    private final static String TAG = "ReturnAcceptActivity";
    private String orderid,returnid,predictMoney,carLpnum,carRentPrice,
            presentPrice,takeCartype,returnPlace,setReturnTime,overspend,orderPrice,driverName,driverMobile;
    private EditText currentcartype,returndate;
    private TextView price,t_overSpendprice,t_sumprice,returntype,returnaddress,driver,driverphone;
    private Button phoneinquiry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paid);
        returntype=(TextView)findViewById(R.id.returntype);
        returnaddress=(TextView)findViewById(R.id.returnaddress);
        price=(TextView)findViewById(R.id.price);
        t_overSpendprice=(TextView)findViewById(R.id.t_overSpendprice);
        t_sumprice=(TextView)findViewById(R.id.t_sumprice);
        driver=(TextView)findViewById(R.id.driver);
        driverphone=(TextView)findViewById(R.id.driverphone);

        currentcartype=(EditText)findViewById(R.id.currentcartype);
        returndate=(EditText)findViewById(R.id.returndate);

        phoneinquiry=(Button)findViewById(R.id.phoneinquiry);
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
                }
            }
        };
        phoneinquiry.setOnClickListener(listenerBt);
    }
    private void init(){
        Intent intent = PaidActivity.this.getIntent();

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
        driverName = intent.getStringExtra("driverName");
        driverMobile = intent.getStringExtra("driverMobile");

        currentcartype.setText(carLpnum);
        returntype.setText("0".equals(takeCartype)?"上门还车":"指定地点还车");
        returnaddress.setText(returnPlace);

        price.setText(carRentPrice+" /天");
        t_overSpendprice.setText(overspend);
        t_sumprice.setText(Float.parseFloat(overspend)+Float.parseFloat(orderPrice)+"");

        driver.setText(driverName);
        driverphone.setText(driverMobile);

        returndate.setText(setReturnTime);

        Log.i(TAG, orderid + "---" + returnid + "----" + predictMoney + "----" +
                overspend + "---" + carLpnum + "==" + carRentPrice + "==" + presentPrice +
                "--" + takeCartype + "---" + returnPlace + "===" + setReturnTime);
    }
}
