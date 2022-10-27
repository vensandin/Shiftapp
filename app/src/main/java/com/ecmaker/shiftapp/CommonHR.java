package com.ecmaker.shiftapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.Gravity;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

public class CommonHR {

    //去除格式化
    public static String getBackFormat(String num, String symbol){
        String result_str = "";
        if(num == null){
            return result_str;
        }
        if("YYYYmm".equals(symbol) || "YYYYmmdd".equals(symbol)){
            result_str = num.replace("/", "");

        }else if("西元年月".equals(symbol) || "西元年月日".equals(symbol) || "民國年月".equals(symbol) || "民國年月日".equals(symbol)){
            result_str = num.replace("年", "");
            result_str = result_str.replace("月", "");
            result_str = result_str.replace("日", "");

        }else if("currency".equals(symbol)){
            result_str = num.replace(",", "");

        }else if("HHss".equals(symbol) || "HHmm".equals(symbol) || "hms".equals(symbol)) {
            result_str = num.replace(":", "");

        }

        return result_str;
    }

    //格式化
    public static String getFormat(String num, String symbol) {
        String result_str = "";
        if(num == null){
            return result_str;
        }
        if ("YYYYmm".equals(symbol)) {
            if (num.length() >= 3) {
                result_str = num.substring(0, num.length() - 2) + "/" + num.substring(num.length() - 2);
            } else {
                result_str = num;
            }
        } else if ("YYYYmmdd".equals(symbol)) {
            if (num.length() >= 5) {
                result_str = num.substring(0, num.length() - 4) + "/" + num.substring(num.length() - 4, num.length() - 2) + "/" + num.substring(num.length() - 2);
            } else {
                result_str = num;
            }
        } else if ("西元年月".equals(symbol)) {
            if (num.length() >= 3) {
                result_str = num.substring(0, num.length() - 2) + "年" + num.substring(num.length() - 2) + "月";
            } else {
                result_str = num;
            }
        } else if ("西元年月日".equals(symbol)) {
            if (num.length() >= 5) {
                result_str = num.substring(0, num.length() - 4) + "年" + num.substring(num.length() - 4, num.length() - 2) + "月" + num.substring(num.length() - 2) + "日";
            } else {
                result_str = num;
            }
        } else if ("民國年月".equals(symbol)) {
            if (num.length() >= 3) {
                result_str = num.substring(0, num.length() - 2) + "年" + num.substring(num.length() - 2) + "月";
            } else {
                result_str = num;
            }
        } else if ("民國年月日".equals(symbol)) {
            if (num.length() >= 5) {
                result_str = num.substring(0, num.length() - 4) + "年" + num.substring(num.length() - 4, num.length() - 2) + "月" + num.substring(num.length() - 2) + "日";
            } else {
                result_str = num;
            }
        } else if ("currency".equals(symbol)) {
            if (num.indexOf(".") > 0) {
                String point = num.substring(num.indexOf("."));
                String start = num.substring(0, num.indexOf("."));
                int count = start.length() / 3;
                for (int i = 0; i < count; i++) {
                    result_str = "," + start.substring(start.length() - 3) + result_str;
                    start = start.substring(0, start.length() - 3);
                }
                if (start.length() > 0) {
                    result_str = start + result_str;
                } else {
                    result_str = result_str.substring(1);
                }
                result_str = result_str + point;

            } else if (num.length() > 3) {
                String start = num;
                int count = start.length() / 3;
                for (int i = 0; i < count; i++) {
                    result_str = "," + start.substring(start.length() - 3) + result_str;
                    start = start.substring(0, start.length() - 3);
                }
                if (start.length() > 0) {
                    result_str = start + result_str;
                } else {
                    result_str = result_str.substring(1);
                }

            } else {
                result_str = num;
            }
        } else if ("HHss".equals(symbol) || "HHmm".equals(symbol)) {
            if (num.length() >= 3) {
                result_str = num.substring(0, num.length() - 2) + ":" + num.substring(num.length() - 2);
            } else {
                result_str = num;
            }
        } else if ("HHmmss".equals(symbol)){
            if (num.length() == 6) {
                result_str = num.substring(0, 2) + ":" + num.substring(2,4)+ ":" + num.substring(4,6);
            } else {
                result_str = num;
            }
        }else{
            result_str = num;
        }

        return result_str;
    }

    public static String add0(String num, int format){
        int x = num.indexOf(".");
        String before = num;
        String after = "";
        if(x > -1){
            before = num.substring(0,x);
            after = num.substring(x);
        }
        while (before.length() < format){
            before = "0" + before;
        }
        return before + after;
    }

    public static String addN0(String num, int format){
        int x = num.indexOf(".");
        String before = num;
        String after = "";
        if(x > -1){
            before = num.substring(0,x+1);
            after = num.substring(x+1);
        }
        while (after.length() < format){
            after = after + "0";
        }
        return before + after;
    }

    //取得系統時間
    public static String getTime(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("HHmm");
        String time = formatter.format(new Date());
        return time;
    }

    //取得系統日期
    public static String getToday(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyyMMdd");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    //取得系統年
    public static String getYear(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    //取得系統月
    public static String getMonth(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("MM");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    //取得系統日
    public static String getDate(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("dd");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    //取星期
    public static String getWeek(){
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("E");
        String ctime = formatter.format(new Date());
        return ctime;
    }

    //取得傳入值的年份
    public static String getYear(String str){
        if(str.length()==0){
            return "";
        }
        String symbol = "yyyyMMdd";
        if(str.length()==6){
            symbol = "yyyyMM";
        }
        SimpleDateFormat format = new SimpleDateFormat(symbol);
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("yyyy");
        String ctime = formatter.format(date);
        return ctime;
    }

    //取得傳入值的月份
    public static String getMonth(String str){
        if(str.length()==0){
            return "";
        }
        String symbol = "yyyyMMdd";
        if(str.length()==6){
            symbol = "yyyyMM";
        }
        SimpleDateFormat format = new SimpleDateFormat(symbol);
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("MM");
        String ctime = formatter.format(date);
        return ctime;
    }

    //取得傳入值的日期
    public static String getDate(String str){
        if(str.length()==0 || str.length()==6){
            return "";
        }
        String symbol = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(symbol);
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("dd");
        String ctime = formatter.format(date);
        return ctime;
    }

    //取得傳入值的小時
    public static String getHH(String str){
        if(str.length()==0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("HHss");
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("HH");
        String ctime = formatter.format(date);
        return ctime;
    }

    //取得傳入值的分鐘
    public static String getmm(String str){
        if(str.length()==0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("HHss");
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("ss");
        String ctime = formatter.format(date);
        return ctime;
    }
    //取傳入值的星期
    public static String getWeek(String str){
        if(str.length()==0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("E");
        String ctime = formatter.format(date);
        return ctime;
    }

    //取得傳入值時間+n小時
    public static String addHour(String str,int hour){
        if(str.length()==0){
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("HHss");
        Date date = null;
        try {
            date = format.parse(str);// 將字串轉換為日期
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //设置生效时间为一小时后
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制

        SimpleDateFormat formatter;
        formatter = new SimpleDateFormat ("HHss");
        String time = formatter.format(cal.getTime());
        return time;
    }

    //取得傳入值日期+n個月
    public static String addMonth(String date,int m) throws ParseException {
        if(date.length()==0){
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dt = sdf.parse(date);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);
        rightNow.add(Calendar.MONTH, m);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    //取得月份英文名稱
    public static String getEMonth(int m){
        String mm = "";
        if(m == 1) {
            mm = "January";
        }else if(m == 2) {
            mm = "February";
        }else if(m == 3) {
            mm = "March";
        }else if(m == 4) {
            mm = "April";
        }else if(m == 5) {
            mm = "May";
        }else if(m == 6) {
            mm = "June";
        }else if(m == 7) {
            mm = "July";
        }else if(m == 8) {
            mm = "August";
        }else if(m == 9) {
            mm = "September";
        }else if(m == 10) {
            mm = "October";
        }else if(m == 11) {
            mm = "November";
        }else if(m == 12) {
            mm = "December";
        }
        return mm;
    }

    //月份英文名稱縮寫
    public static String getEMonth_simple(int m){
        String mm = "";
        if(m == 1) {
            mm = "Jan";
        }else if(m == 2) {
            mm = "Feb";
        }else if(m == 3) {
            mm = "Mar";
        }else if(m == 4) {
            mm = "Apr";
        }else if(m == 5) {
            mm = "May";
        }else if(m == 6) {
            mm = "Jun";
        }else if(m == 7) {
            mm = "Jul";
        }else if(m == 8) {
            mm = "Aug";
        }else if(m == 9) {
            mm = "Sep";
        }else if(m == 10) {
            mm = "Oct";
        }else if(m == 11) {
            mm = "Nov";
        }else if(m == 12) {
            mm = "Dec";
        }
        return mm;
    }

    //取每月星期
    public static Vector<String> getWeektDate(){
        Vector<String> vDate = new Vector<>();
        int endDD = 31;

        LocalDate date = LocalDate.now();
        int year = date.getYear();//本年
        int month = date.getMonthValue();//本月
        int today = date.getDayOfMonth();//本日
        date = date.minusDays(today - 1);
        DayOfWeek weekday = date.getDayOfWeek();
        int wek = weekday.getValue();//本月第一天的星期

        if(month == 2){
            if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                endDD = 29;
            }else {
                endDD = 28;
            }
        }else if(month == 4 || month == 6 || month == 9 || month == 11){
            endDD = 30;
        }
        //一每月天數去排星期
        for(int i = 0; i < endDD; i++) {
            if(wek == 1) {
                vDate.add("Mon");//一
                wek++;
            }
            if(wek == 2) {
                vDate.add("Tue");//二
                wek++;
            }
            if(wek == 3) {
                vDate.add("Wed");//三
                wek++;
            }
            if(wek == 4) {
                vDate.add("Thu");//四
                wek++;
            }
            if(wek == 5) {
                vDate.add("Fri");//五
                wek++;
            }
            if(wek == 6) {
                vDate.add("Sat");//六
                wek++;
            }
            if(wek == 7) {
                vDate.add("Sun");//日
                wek = 1;
            }
        }
        return vDate;
    }

    //取每月星期
    public static Vector<String> getWeektDate(String yyyy,String mm){
        Vector<String> vDate = new Vector<>();
        int endDD = 31;

        if(mm.length() == 1){
            mm = "0" + mm;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String yyyymmdd = yyyy+"-"+mm+"-01";

        LocalDate date = LocalDate.parse(yyyymmdd,formatter);
        int year = date.getYear();//本年
        int month = date.getMonthValue();//本月
        int today = date.getDayOfMonth();//本日
        date = date.minusDays(today - 1);
        DayOfWeek weekday = date.getDayOfWeek();
        int wek = weekday.getValue();//本月第一天的星期

        if(month == 2){
            if((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)){
                endDD = 29;
            }else {
                endDD = 28;
            }
        }else if(month == 4 || month == 6 || month == 9 || month == 11){
            endDD = 30;
        }
        //一每月天數去排星期
        for(int i = 0; i < endDD; i++) {
            if(wek == 1) {
                vDate.add("Mon");//一
                wek++;
            }
            if(wek == 2) {
                vDate.add("Tue");//二
                wek++;
            }
            if(wek == 3) {
                vDate.add("Wed");//三
                wek++;
            }
            if(wek == 4) {
                vDate.add("Thu");//四
                wek++;
            }
            if(wek == 5) {
                vDate.add("Fri");//五
                wek++;
            }
            if(wek == 6) {
                vDate.add("Sat");//六
                wek++;
            }
            if(wek == 7) {
                vDate.add("Sun");//日
                wek = 1;
            }
        }
        return vDate;
    }

    //取月底
    public static String getLastDate(String yyyy,String mm){
        Vector<String> vDate = new Vector<>();
        String m = mm;
        m = String.valueOf(Integer.parseInt(m));
        String endDD = "31";
        int YYYY = Integer.parseInt(yyyy);
        if("2".equals(m) || "02".equals(m)){
            if((YYYY % 4 == 0 && YYYY % 100 != 0) || (YYYY % 400 == 0)){
                endDD = "29";
            }else {
                endDD = "28";
            }
        }else if("4".equals(m) || "6".equals(m) || "9".equals(m) || "11".equals(m) || "04".equals(m) || "06".equals(m) || "09".equals(m)){
            endDD = "30";
        }

        if(m.length()==1){
            endDD = yyyy+"0"+m+endDD;
        }else{
            endDD = yyyy+m+endDD;
        }
        return endDD;
    }

    //取兩個傳入日期之間所有日期
    public static Vector<String> getBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        // 声明保存日期集合
        Vector<String> list = new Vector<String>();
        if(startTime.length()==0 || endTime.length()==0){
            return list;
        }
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.DATE, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
    //Base64轉Bitmap
    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string.split(",")[0], Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    //bitmap轉Drawable(畫圓形圖像)
    public static Drawable createRoundImageWithBorder(Bitmap bitmap, Context context){
        //原图宽度
        int bitmapWidth = bitmap.getWidth();
        //原图高度
        int bitmapHeight = bitmap.getHeight();
        //边框宽度 pixel
        int borderWidthHalf = 20;

        //转换为正方形后的宽高
        int bitmapSquareWidth = Math.min(bitmapWidth,bitmapHeight);

        //最终图像的宽高
        int newBitmapSquareWidth = bitmapSquareWidth+borderWidthHalf;

        Bitmap roundedBitmap = Bitmap.createBitmap(newBitmapSquareWidth,newBitmapSquareWidth,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(roundedBitmap);
        int x = borderWidthHalf + bitmapSquareWidth - bitmapWidth;
        int y = borderWidthHalf + bitmapSquareWidth - bitmapHeight;

        //裁剪后图像,注意X,Y要除以2 来进行一个中心裁剪
        canvas.drawBitmap(bitmap, x/2, y/2, null);
        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidthHalf);
        borderPaint.setColor(Color.WHITE);

        //添加边框
        canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, newBitmapSquareWidth/2, borderPaint);

        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(),roundedBitmap);
        roundedBitmapDrawable.setGravity(Gravity.CENTER);
        roundedBitmapDrawable.setCircular(true);
        return roundedBitmapDrawable;
    }

    //取得語言
    public static String getLocale(){
        String language = Locale.getDefault().getLanguage();
        String country = Locale.getDefault().getCountry();
        language = language.toUpperCase();//語言
        country = country.toUpperCase();//地區
        return country;
    }
}
