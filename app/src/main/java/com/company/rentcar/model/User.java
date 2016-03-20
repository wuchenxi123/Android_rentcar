package com.company.rentcar.model;

import java.io.Serializable;
/**
 * Created by Administrator on 2016/3/5.
 */
public class User implements Serializable {
    private Integer userId;// 自增id
    private String userName;// 用户名（姓名/企业名）
    private String userPassword;// 用户密码（md5 32位散列值）
    private String userMobile;// 用户手机号（可用于用户登录）
    private String userIDCard;//身份号
    private String userRelname;//用户真实姓名
    private String regtime;//用户注册时间
    private int userType;// 0：管理员； 1：客服； 2：司机； 3：普通用户
    private String userLicense;
    private float userRemainder;// 可用余额
    private String userVerifyCode;// 用户密码验证码（结合用户密码验证）
    public Integer getUserId(Integer userId) {
        return this.userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserMobile() {
        return userMobile;
    }
    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
    public String getUserIDCard() {
        return userIDCard;
    }
    public void setUserIDCard(String userIDCard) {
        this.userIDCard = userIDCard;
    }
    public String getRegtime() {
        return regtime;
    }
    public void setRegtime(String regtime) {
        this.regtime = regtime;
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
    public String getUserLicense() {
        return userLicense;
    }
    public void setUserLicense(String userLicense) {
        this.userLicense = userLicense;
    }
    public String getUserVerifyCode() {
        return userVerifyCode;
    }
    public void setUserVerifyCode(String userVerifyCode) {
        this.userVerifyCode = userVerifyCode;
    }
    public String getUserRelname() {
        return userRelname;
    }
    public void setUserRelname(String userRelname) {
        this.userRelname = userRelname;
    }
}
