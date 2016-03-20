package com.company.rentcar.model;

import java.io.Serializable;
import java.util.Date;
/**
 * Created by Administrator on 2016/3/10.
 */
public class RentOrderView implements Serializable {
    private int orderId;//订单号
    private int userId;//客户ID
    private Double orderPrice;//订单价格
    private int orderStatus;//订单状态
    private String userRelname;//客户真实姓名
    private String userMobile;//客户手机号
    private String userIdcard;//客户身份证号
    private int userType;//客户类型 3普通用户 0管理员1客服2司机
    //private String userLisence;
    private float userRemainder;//用户余额
    private int rentId;//租车信息ID
    private String rentLpnum;//租用车牌号
    private String rentCtype;//租车类型
    private String rentPlace;//租车地点
    private String rentTakeTime;//取车时间
    private int senddriverId;//送车司机ID
    private int rentDays;//租车天数
    private int returnId;//还车信息ID



    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Double getOrderPrice() {
        return orderPrice;
    }
    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }
    public int getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
    public String getUserRelname() {
        return userRelname;
    }
    public void setUserRelname(String userRelname) {
        this.userRelname = userRelname;
    }
    public String getUserMobile() {
        return userMobile;
    }
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
    public String getUserIdcard() {
        return userIdcard;
    }
    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard;
    }
    public int getUserType() {
        return userType;
    }
    public void setUserType(int userType) {
        this.userType = userType;
    }
    public float getUserRemainder() {
        return userRemainder;
    }
    public void setUserRemainder(float userRemainder) {
        this.userRemainder = userRemainder;
    }
    public int getRentId() {
        return rentId;
    }
    public void setRentId(int rentId) {
        this.rentId = rentId;
    }
    public String getRentLpnum() {
        return rentLpnum;
    }
    public void setRentLpnum(String rentLpnum) {
        this.rentLpnum = rentLpnum;
    }
    public String getRentCtype() {
        return rentCtype;
    }
    public void setRentCtype(String rentCtype) {
        this.rentCtype = rentCtype;
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
    public int getSenddriverId() {
        return senddriverId;
    }
    public void setSenddriverId(int senddriverId) {
        this.senddriverId = senddriverId;
    }
    public int getRentDays() {
        return rentDays;
    }
    public void setRentDays(int rentDays) {
        this.rentDays = rentDays;
    }
    public int getReturnId() {
        return returnId;
    }
    public void setReturnId(int returnId) {
        this.returnId = returnId;
    }

}

