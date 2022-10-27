package com.ecmaker.shiftapp;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


public class MonthlyCalendar {
    private int title_year;

    public MonthlyCalendar(int x, int y, int year, int month, MainActivity mainActivity){
        title_year = year;
        PopupWindow popupWindow = new PopupWindow(mainActivity);
        View popupView = LayoutInflater.from(mainActivity).inflate(R.layout.monthly_calendar_popupwindow,null);

        TextView year_tv = popupView.findViewById(R.id.year);
        year_tv.setText(String.valueOf(year));

        LinearLayout leftYear = popupView.findViewById(R.id.leftYear);
        LinearLayout rightYear = popupView.findViewById(R.id.rightYear);

        leftYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_year = title_year - 1;
                year_tv.setText(String.valueOf(title_year));
            }
        });
        rightYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title_year = title_year + 1;
                year_tv.setText(String.valueOf(title_year));
            }
        });

        CardView carv1 = popupView.findViewById(R.id.carv1);
        CardView carv2 = popupView.findViewById(R.id.carv2);
        CardView carv3 = popupView.findViewById(R.id.carv3);
        CardView carv4 = popupView.findViewById(R.id.carv4);
        CardView carv5 = popupView.findViewById(R.id.carv5);
        CardView carv6 = popupView.findViewById(R.id.carv6);
        CardView carv7 = popupView.findViewById(R.id.carv7);
        CardView carv8 = popupView.findViewById(R.id.carv8);
        CardView carv9 = popupView.findViewById(R.id.carv9);
        CardView carv10 = popupView.findViewById(R.id.carv10);
        CardView carv11 = popupView.findViewById(R.id.carv11);
        CardView carv12 = popupView.findViewById(R.id.carv12);

        if(month == 1){
            TextView text1 = popupView.findViewById(R.id.text1);
            setColor(mainActivity,carv1,text1);
        }else if(month == 2){
            TextView text2 = popupView.findViewById(R.id.text2);
            setColor(mainActivity,carv2,text2);
        }else if(month == 3){
            TextView text3 = popupView.findViewById(R.id.text3);
            setColor(mainActivity,carv3,text3);
        }else if(month == 4){
            TextView text4 = popupView.findViewById(R.id.text4);
            setColor(mainActivity,carv4,text4);
        }else if(month == 5){
            TextView text5 = popupView.findViewById(R.id.text5);
            setColor(mainActivity,carv5,text5);
        }else if(month == 6){
            TextView text6 = popupView.findViewById(R.id.text6);
            setColor(mainActivity,carv6,text6);
        }else if(month == 7){
            TextView text7 = popupView.findViewById(R.id.text7);
            setColor(mainActivity,carv7,text7);
        }else if(month == 8){
            TextView text8 = popupView.findViewById(R.id.text8);
            setColor(mainActivity,carv8,text8);
        }else if(month == 9){
            TextView text9 = popupView.findViewById(R.id.text9);
            setColor(mainActivity,carv9,text9);
        }else if(month == 10){
            TextView text10 = popupView.findViewById(R.id.text10);
            setColor(mainActivity,carv10,text10);
        }else if(month == 11){
            TextView text11 = popupView.findViewById(R.id.text11);
            setColor(mainActivity,carv11,text11);
        }else if(month == 12){
            TextView text12 = popupView.findViewById(R.id.text12);
            setColor(mainActivity,carv12,text12);
        }

        carv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"01");
                popupWindow.dismiss();
            }
        });
        carv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"02");
                popupWindow.dismiss();
            }
        });
        carv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"03");
                popupWindow.dismiss();
            }
        });
        carv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"04");
                popupWindow.dismiss();
            }
        });
        carv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"05");
                popupWindow.dismiss();
            }
        });
        carv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"06");
                popupWindow.dismiss();
            }
        });
        carv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"07");
                popupWindow.dismiss();
            }
        });
        carv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"08");
                popupWindow.dismiss();
            }
        });
        carv9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"09");
                popupWindow.dismiss();
            }
        });
        carv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"10");
                popupWindow.dismiss();
            }
        });
        carv11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"11");
                popupWindow.dismiss();
            }
        });
        carv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick(String.valueOf(title_year),"12");
                popupWindow.dismiss();
            }
        });

        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(popupView, Gravity.NO_GRAVITY,x,y);
    }

    public interface OnLabelClickListener {
        /**
         * 点击label
         */
        void onClick(String year,String month);
    }
    private MonthlyCalendar.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(MonthlyCalendar.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    public void setColor(MainActivity mainActivity, CardView carv, TextView text){
        carv.setCardBackgroundColor(mainActivity.getColor(R.color.button_blue4));
        text.setTextColor(mainActivity.getColor(R.color.white));
    }
}
