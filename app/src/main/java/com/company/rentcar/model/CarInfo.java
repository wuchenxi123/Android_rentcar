package com.company.rentcar.model;

import android.util.Log;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/7.
 */
public class CarInfo implements Serializable {
    private int carId; // 自增ID
    private String updateTime;// 车辆信息更新时间
    private String carBand;// 车辆类型（品牌）
    private String carColor;//
    private float carPrice;// 购入价格
    private Integer userId;// 车主信息 外键
    private String carLpnum;// 车牌号
    private String carPictUrl;// 车辆图片地址
    private String carContent;// 车辆备注
    private String carPurTime;// 购车时间
    private double carRentPri;// 租车单价
    private int carFlag;// 车辆租用标识0：未租出，1：已租出
    private int spFlag;// 新车审批状态 0：未审批 ，1：审批通过
    private int carTaketype;// 取车方式表示 判断要不要送车上门费 0 送车上门 （默认）1 公司地点取
    private double insurePrice;
    private double scsmPrice;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCarBand() {
        return carBand;
    }

    public void setCarBand(String carBand) {
        this.carBand = carBand;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public float getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(float carPrice) {
        this.carPrice = carPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCarLpnum() {
        return carLpnum;
    }

    public void setCarLpnum(String carLpnum) {
        this.carLpnum = carLpnum;
    }

    public String getCarPictUrl() {
        return carPictUrl;
    }

    public void setCarPictUrl(String carPictUrl) {
        this.carPictUrl = carPictUrl;
    }

    public String getCarContent() {
        return carContent;
    }

    public void setCarContent(String carContent) {
        this.carContent = carContent;
    }

    public String getCarPurTime() {
        return carPurTime;
    }

    public void setCarPurTime(String carPurTime) {
        this.carPurTime = carPurTime;
    }

    public double getCarRentPri() {
        return carRentPri;
    }

    public void setCarRentPri(double carRentPri) {
        this.carRentPri = carRentPri;
    }



    public int getCarFlag() {
        return carFlag;
    }

    public void setCarFlag(int carFlag) {
        this.carFlag = carFlag;
    }

    public int getSpFlag() {
        return spFlag;
    }

    public void setSpFlag(int spFlag) {
        this.spFlag = spFlag;
    }

    public double getInsurePrice() {
        return insurePrice;
    }

    public void setInsurePrice(double insurePrice) {
        this.insurePrice = insurePrice;
    }

    public double getScsmPrice() {
        return scsmPrice;
    }

    public void setScsmPrice(double scsmPrice) {
        this.scsmPrice = scsmPrice;
    }

    public int getCarTaketype() {
        return carTaketype;
    }

    public void setCarTaketype(int carTaketype) {
        this.carTaketype = carTaketype;
    }

    public static CarInfo parseCarInfoJSON(JSONObject carJs) {
        CarInfo car = new CarInfo();
        try {
            car.setCarId(carJs.getInt("carId"));
            car.setCarLpnum(carJs.getString("carLpnum"));
            car.setCarPictUrl(carJs.getString("carPictUrl"));
            car.setCarRentPri(carJs.getDouble("carRentPri"));
            car.setInsurePrice(carJs.getDouble("insurePrice"));
            car.setScsmPrice(carJs.getDouble("scsmPrice"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return car;
    }
}
