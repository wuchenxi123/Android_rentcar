package com.watch.administrator.rentcar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.company.rentcar.model.CarInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.AdapterView.OnItemClickListener;
import static android.widget.AdapterView.OnItemSelectedListener;

public class SimpleAdapterActivity extends AppCompatActivity {

    private final static String TAG="SimpleAdapterActivity";
    List<CarInfo> listObj;
    private String[] ids;
    private String[] carBand;
    private String[] carLpnum;
    private String[] carRentPri;
    private String[] insurePrice;
    private String[] scsmPrice;
    List<Map<String, Object>> listItems;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        listObj=  (List<CarInfo>) this.getIntent().getSerializableExtra("allCar");
        Log.i(TAG,this.getIntent().getSerializableExtra("allCar")+"---------");
        ids=new String[listObj.size()];
        carBand=new String[listObj.size()];
        carLpnum=new String[listObj.size()];
        carRentPri=new String[listObj.size()];
        insurePrice=new String[listObj.size()];
        scsmPrice=new String[listObj.size()];
        for(int i=0;i<listObj.size();i++){
            ids[i]= String.valueOf(listObj.get(i).getCarId());
        }
        for(int i=0;i<listObj.size();i++){

            Log.i(TAG,listObj.get(i).getCarBand()+"-------");
            carBand[i]= String.valueOf(listObj.get(i).getCarBand());
        }
        for(int i=0;i<listObj.size();i++){
            carLpnum[i]= String.valueOf(listObj.get(i).getCarLpnum());
        }
        for(int i=0;i<listObj.size();i++){
            carRentPri[i]= String.valueOf(listObj.get(i).getCarRentPri());
        }
        for(int i=0;i<listObj.size();i++){
            insurePrice[i]= String.valueOf(listObj.get(i).getInsurePrice());
        }
        for(int i=0;i<listObj.size();i++){
            scsmPrice[i]= String.valueOf(listObj.get(i).getScsmPrice());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simpleadapter_main);
        // 创建一个List集合，List集合的元素是Map
        listItems =
                new ArrayList<Map<String, Object>>();
        for (int i = 0; i < ids.length; i++)
        {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("id", ids[i]);
            listItem.put("carBand", carBand[i]);
            listItem.put("carLpnum", carLpnum[i]);
            listItem.put("carRentPri", carRentPri[i]);
            listItem.put("insurePrice", insurePrice[i]);
            listItem.put("scsmPrice", scsmPrice[i]);
            listItems.add(listItem);
        }
        // 创建一个SimpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
                R.layout.simple_item,
                new String[] { "id", "carBand" , "carLpnum","carRentPri","insurePrice","scsmPrice"},
                new int[] { R.id.id, R.id.carBand , R.id.carLpnum,R.id.carRentPri,R.id.insurePrice,R.id.scsmPrice });
        ListView list = (ListView) findViewById(R.id.mylist);
        // 为ListView设置Adapter
        list.setAdapter(simpleAdapter);

        // 为ListView的列表项的单击事件绑定事件监听器
        list.setOnItemClickListener(new OnItemClickListener() {
            // 第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SimpleAdapterActivity.this, carBand[position],
                        Toast.LENGTH_SHORT).show();
                CarInfo car=new CarInfo();
                car.setCarId(Integer.parseInt(ids[position]));
                car.setCarBand(carBand[position]);
                car.setCarLpnum(carLpnum[position]);
                car.setCarRentPri(Double.parseDouble(carRentPri[position]));
                car.setInsurePrice(Double.parseDouble(insurePrice[position]));
                car.setScsmPrice(Double.parseDouble(scsmPrice[position]));
                listItems.removeAll(listItems);
                listItems.clear();
                listObj.removeAll(listObj);
                listObj.clear();
                //将数据放入Bundle对象中
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("car",car);
//                bundle.putString("og","123");
                Intent intent=SimpleAdapterActivity.this.getIntent();
                intent.putExtra("car", car);
                intent.setClass(SimpleAdapterActivity.this, MainActivity.class);
                //设置当前Activity的结果码
                SimpleAdapterActivity.this.setResult(1, intent);
                SimpleAdapterActivity.this.finish();
//                startActivity(intent);

            }



        });
        // 为ListView的列表项的选中事件绑定事件监听器

    }
}
