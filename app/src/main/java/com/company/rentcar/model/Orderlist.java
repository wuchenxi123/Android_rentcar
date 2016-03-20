package com.company.rentcar.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/17.
 */
public class Orderlist implements Serializable {
    private int orderid;
    private String rentTime;
    private int rentdays;
    private float orderPrice;

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getRentTime() {
        return rentTime;
    }

    public void setRentTime(String rentTime) {
        this.rentTime = rentTime;
    }

    public int getRentdays() {
        return rentdays;
    }

    public void setRentdays(int rentdays) {
        this.rentdays = rentdays;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }

    public List<Orderlist> parseJSON(JSONArray items) {
        List<Orderlist> allList = new ArrayList<Orderlist>();
        try {
            for (int i = 0; i < items.length(); i++) {

                JSONObject item = items.getJSONObject(i);
                Orderlist ol = new Orderlist();
                ol.setOrderid(item.getInt("orderid"));
                ol.setOrderPrice((float) item.getDouble("orderPrice"));
                ol.setRentdays(item.getInt("rentdays"));
                ol.setRentTime(item.getString("rentTime"));
                allList.add(ol);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allList;
    }
}
