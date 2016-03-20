package com.company.rentcar.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Administrator on 2016/3/15.
 */
public class StringSub {
    public Calendar sub(String str) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = GregorianCalendar.getInstance();
        try {
            calendar.setTime(sdf.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
    public long countDays(String date1,String end1){


        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date= null;
        Date end= null;
        try {
            date = sdf.parse(date1);
            end=sdf.parse(end1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String begin=sdf.format(date);
        // String begin=date;
        //  String end=sdf.format(new Date());  //系统日期
        //	 Date passDate=simpleDateFormat.parse(defaultDate);

        //间隔天数
        long days=0;
        try {

//					days = (sdf.parse(end).getTime()-sdf.parse(begin).getTime())/(24*60*60*1000);
//					long re=(sdf.parse(end).getTime()-sdf.parse(begin).getTime())%(24*60*60*1000);
            days = (end.getTime()-sdf.parse(begin).getTime())/(24*60*60*1000);
            long re=(end.getTime()-sdf.parse(begin).getTime())%(24*60*60*1000);
            if(re!=0){
                days=days+1;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return days;



    }
}
