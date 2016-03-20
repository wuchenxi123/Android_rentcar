package com.watch.administrator.rentcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends BaseActivity {

    private String user_phone,user_vcode,user_password,insure_password,idcard,user_license;
    private EditText phoneEt,vcodeEt,passwordEt,insurePwdEt,idcardEt,licenseEt;
    private Button getVcode,registerButton;
    private String registerState="";
    private Map<String, Object> mMap;// 存储网络请求提交的参数
    private String url = ConnData.URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phoneEt= (EditText) findViewById(R.id.u_phone);
        vcodeEt= (EditText) findViewById(R.id.vcode);
        passwordEt= (EditText) findViewById(R.id.register_password);
        insurePwdEt= (EditText) findViewById(R.id.ensuer_pwd);
        idcardEt= (EditText) findViewById(R.id.idcard);
        licenseEt= (EditText) findViewById(R.id.drivelicense);

        getVcode=(Button)findViewById(R.id.getvcode);
        registerButton=(Button)findViewById(R.id.register_finish);

        init();

        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button bt= (Button) v;
                switch (v.getId()){
                    case R.id.getvcode:{
                        Toast.makeText(RegisterActivity.this,"短信发送成功！",Toast.LENGTH_SHORT).show();
                        String code=Double.toString((int)(Math.random()*9000)+1000).substring(0,4);
                        vcodeEt.setText(code);
                        break;
                    }
                    case R.id.register_finish:{
                        Send();
                        break;
                    }
                }
            }
        };

        getVcode.setOnClickListener(listener);
        registerButton.setOnClickListener(listener);
    }
    public void init(){
        insurePwdEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //获得焦点处理
                }
                else {
                    //失去焦点处理
                    if(!passwordEt.getText().toString().trim().equals(insurePwdEt.getText().toString().trim())){
                        passwordEt.setText("");
                        insurePwdEt.setText("");
                        Toast.makeText(RegisterActivity.this,"密码前后输入不一致",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void Send() {

        user_phone = phoneEt.getText().toString().trim();
        user_vcode=vcodeEt.getText().toString().trim();
        user_password=vcodeEt.getText().toString().trim();
        idcard=idcardEt.getText().toString().trim();
        user_license=licenseEt.getText().toString().trim();

        mMap = new HashMap<String, Object>();
        mMap.put("method","register");

        mMap.put("userMobile", user_phone);
        mMap.put("userVerifyCode",user_vcode);
        mMap.put("userPassword",user_password);
        mMap.put("userIDCard",idcard);
        mMap.put("userLicense",user_license);
        // 请求服务
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectPostRequest jsonObjectPostRequest = new JsonObjectPostRequest(
                url, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    registerState = response.getString("registerState");
                    Log.i("registerState----", registerState);
                    // Toast.makeText(RegisterActivity.this,
                    // "codeSMS"+codeSMS,
                    // Toast.LENGTH_SHORT).show();
                    // userBean.setCodeSMS(codeSMS);

                    switch (registerState) {
                        case "200":
                            Toast.makeText(RegisterActivity.this,
                                    "注册成功！", Toast.LENGTH_SHORT)
                                    .show();
                            // 将codeSMS传递到SetPassWordActivity
                            // Intent codeintent=new
                            // Intent(getBaseContext(),SetPassWordActivity.class);
                            // codeintent.putExtra("codeSMS", codeSMS);
                            String userId= response.getString("userId");
                            Log.i("userId------", userId);
                            Intent intent = new Intent(
                                    RegisterActivity.this,
                                    MainActivity.class);
                            intent.putExtra("userId",userId);
                            startActivity(intent);

                            break;
                        case "10":
                            Toast.makeText(RegisterActivity.this,
                                    "密码不符合规则", Toast.LENGTH_SHORT)
                                    .show();
                            break;
                        case "30":
                            Toast.makeText(RegisterActivity.this,
                                    "注册用户失败",
                                    Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterActivity.this, "获取失败，请检查网络",
                        Toast.LENGTH_SHORT).show();

            }
        }, mMap);
        requestQueue.add(jsonObjectPostRequest);

    }


}
