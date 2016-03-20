package com.company.rentcar.model;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/18.
 */
public class CompleteOrderDetail implements Serializable{
    private int  orderId;
    private String createString;
    private int  userId ;
    private int orderStatus;
    private float orderPrice;
    private String drivername;
    private String driverMobie;
    private String driverIDcard;
    private String driverLicense;
    private String carBand;
    private String carColor;
    private String carLpnum;
    private String carPictUrl;
    private float carRentPri;
    private int   carTakeType;
    private float insurePrice;
    private float scsmPrice;
    private String rentPlace;
    private String rentTakeTime;
    private int rentDays;
    private String returnPlace;
    private String returnReTime;
    private int returnType;
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getCreateString() {
        return createString;
    }
    public void setCreateString(String createString) {
        this.createString = createString;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
    public float getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(float orderPrice) {
        this.orderPrice = orderPrice;
    }
    public String getDrivername() {
        return drivername;
    }
    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }
    public String getDriverMobie() {
        return driverMobie;
    }
    public void setDriverMobie(String driverMobie) {
        this.driverMobie = driverMobie;
    }
    public String getDriverIDcard() {
        return driverIDcard;
    }
    public void setDriverIDcard(String driverIDcard) {
        this.driverIDcard = driverIDcard;
    }
    public String getDriverLicense() {
        return driverLicense;
    }
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
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
    public float getCarRentPri() {
        return carRentPri;
    }
    public void setCarRentPri(float carRentPri) {
        this.carRentPri = carRentPri;
    }
    public int getCarTakeType() {
        return carTakeType;
    }
    public void setCarTakeType(int carTakeType) {
        this.carTakeType = carTakeType;
    }
    public float getInsurePrice() {
        return insurePrice;
    }
    public void setInsurePrice(float insurePrice) {
        this.insurePrice = insurePrice;
    }
    public float getScsmPrice() {
        return scsmPrice;
    }
    public void setScsmPrice(float scsmPrice) {
        this.scsmPrice = scsmPrice;
    }
    public String getRentPlace() {
        return rentPlace;
    }
    public void setRentPlace(String rentPlace) {
        this.rentPlace = rentPlace;
    }
    public String getRentTakeTime() {
        return rentTakeTime;
    }
    public void setRentTakeTime(String rentTakeTime) {
        this.rentTakeTime = rentTakeTime;
    }
    public int getRentDays() {
        return rentDays;
    }
    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }
    public String getReturnPlace() {
        return returnPlace;
    }
    public void setReturnPlace(String returnPlace) {
        this.returnPlace = returnPlace;
    }
    public String getReturnReTime() {
        return returnReTime;
    }
    public void setReturnReTime(String returnReTime) {
        this.returnReTime = returnReTime;
    }
    public int getReturnType() {
        return returnType;
    }
    public void setReturnType(int returnType) {
        this.returnType = returnType;
    }
    public static CompleteOrderDetail parseCompleteOrderDetailJSON(JSONObject detail) {
        CompleteOrderDetail orderDetail = new CompleteOrderDetail();
        try {
            orderDetail.setRentTakeTime(detail.getString("rentTakeTime"));
            orderDetail.setRentPlace(detail.getString("rentPlace"));
            orderDetail.setReturnReTime(detail.getString("returnReTime"));
            orderDetail.setReturnPlace(detail.getString("returnPlace"));
            orderDetail.setInsurePrice((float) detail.getDouble("insurePrice"));
            orderDetail.setScsmPrice((float) detail.getDouble("scsmPrice"));
            orderDetail.setCarTakeType(detail.getInt("carTakeType"));

            orderDetail.setOrderPrice((float) detail.getDouble("orderPrice"));
            orderDetail.setCarBand(detail.getString("carBand"));
            orderDetail.setCarLpnum(detail.getString("carLpnum"));
            orderDetail.setDrivername(detail.getString("drivername"));
            orderDetail.setDriverMobie(detail.getString("driverMobie"));
            return orderDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderDetail;
    }
}
