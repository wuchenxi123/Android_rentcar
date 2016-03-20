package com.company.rentcar.url;

public interface ConnData {
	public static String ip = "192.168.1.101";
//	public static String ip = "10.10.1.42";
	public static String URL="http://"+ip+":8080/rent_car/user.do";
	public static String carInfo="http://"+ip+":8080/rent_car/rent.do";
	public static String returnCar="http://"+ip+":8080/rent_car/return.do";
	public static String orderUrl="http://"+ip+":8080/rent_car/order.do";
//	public static String getcarInfo="http://121.40.50.7:8080/wuLiuServer/carInfo.do";
//	public static String UserLogin = "http://"+ip+":8080/wuLiuServer/user.do?method=login";
//	public static String validateMobile = "http://"+ip+":8080/wuLiuServer/user.do?method=validateMobile";
//	public static String UserRegister = "http://"+ip+":8080/wuLiuServer/user.do?method=register";
//	public static String reValidateMobile = "http://"+ip+":8080/wuLiuServer/user.do?method=reValidateMobile";
//	public static String resetUserPassword = "http://"+ip+":8080/wuLiuServer/user.do?method=reValidateMobile";
	
	
}