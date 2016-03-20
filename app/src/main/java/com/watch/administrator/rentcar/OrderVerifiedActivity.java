package com.watch.administrator.rentcar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */

public class OrderVerifiedActivity extends Activity {
    private final static String TAG = "OrderVerifiedActivity";
    private EditText takeplace,taketime;
    private TextView totalrentprice,totalinsureprice,delivercar,totalprice,licensenumber,contactnumber;
    private Button phoneinquiry,paid;

    CarInfo carInfo;
    RentOrderView rentOrderView;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_verified);//这里使用了上面创建的xml文件（Tab页面的布局）
        init();

        takeplace= (EditText) findViewById(R.id.takeplace);
        taketime= (EditText) findViewById(R.id.taketime);

        takeplace.setText(rentOrderView.getRentPlace());
        taketime.setText(rentOrderView.getRentTakeTime());

        totalrentprice= (TextView) findViewById(R.id.totalrentprice);
        totalinsureprice=(TextView) findViewById(R.id.totalinsureprice);
        delivercar= (TextView) findViewById(R.id.delivercar);
        totalprice=(TextView) findViewById(R.id.totalprice);
        licensenumber=(TextView) findViewById(R.id.licensenumber);
        contactnumber=(TextView) findViewById(R.id.contactnumber);

        totalrentprice.setText(carInfo.getCarRentPri()+"");
        totalinsureprice.setText(carInfo.getInsurePrice()+"");
        delivercar.setText(carInfo.getScsmPrice()+"");
        totalprice.setText(rentOrderView.getOrderPrice()+"");
        licensenumber.setText(carInfo.getCarLpnum());
        contactnumber.setText(user.getUserMobile());

        phoneinquiry= (Button) findViewById(R.id.phoneinquiry);
        paid= (Button) findViewById(R.id.paid);

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
                    case R.id.paid:{
                        paid.setEnabled(false);
                        break;
                    }
                }
            }
        };
        phoneinquiry.setOnClickListener(listener);
        paid.setOnClickListener(listener);

    }

    private void init(){
        Intent intent=OrderVerifiedActivity.this.getIntent();
        carInfo= (CarInfo) intent.getSerializableExtra("carInfo");
        user= (User) intent.getSerializableExtra("driver");
        rentOrderView= (RentOrderView) intent.getSerializableExtra("rentOrderView");

        Log.i(TAG,carInfo+"==="+user+"[[[[["+rentOrderView+"=======");

    }
}