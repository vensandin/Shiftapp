package com.ecmaker.shiftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PinCodeActivity extends AppCompatActivity {
    private boolean eyes = false;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);

        TextView connection = findViewById(R.id.connection);//聯絡我們
        connection.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下劃線
        connection.getPaint().setAntiAlias(true);//抗鋸齒

        Button next = findViewById(R.id.next);//下一步
        LinearLayout errorlayout = findViewById(R.id.errorlayout);//錯誤框

        LinearLayout pinCodeLayout = findViewById(R.id.pinCodeLayout);//外框
        EditText pinCodeEdit = findViewById(R.id.pinCodeEdit);//pin code 輸入
        ImageView pinCodeImg = findViewById(R.id.pinCodeImg);//眼睛

        pinCodeEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隱藏文字
        //文字輸入監聽
        pinCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String pincode = pinCodeEdit.getText().toString();

                if(pincode.length() <= 0){
                    pinCodeImg.setImageResource(R.drawable.background_none);
                }else if(pincode.length() == 1){
                    pinCodeImg.setImageResource(R.drawable.eyes_c);
                }

                if(pincode.length()>=6){
                    next.setBackground(getDrawable(R.drawable.background_button_color2));
                    next.setEnabled(true);

                }else{
                    next.setBackground(getDrawable(R.drawable.background_button_color));
                    next.setEnabled(false);
                }
            }
        });
        //輸入框焦點監聽
        pinCodeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(isError){
                    pinCodeLayout.setBackground(getDrawable(R.drawable.background_normal));
                    errorlayout.removeAllViews();
                }
                if(b){
                    pinCodeLayout.setBackground(getDrawable(R.drawable.background_focused));
                }else{
                    pinCodeLayout.setBackground(getDrawable(R.drawable.background_normal));
                }
            }
        });
        //眼睛圖片監聽
        pinCodeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eyes){
                    pinCodeEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隱藏文字
                    eyes = false;
                }else{
                    pinCodeEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//顯示文字
                    eyes = true;
                }
            }
        });

        //下一步
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinCodeEdit.getText().toString().length() >= 10){
                    pinCodeEdit.clearFocus();//清掉焦點
                    isError = true;
                    pinCodeLayout.setBackground(getDrawable(R.drawable.background_edittext2));
                    errorlayout.removeAllViews();//先清掉錯誤Layout 避免疊加

                    //單位:10dp
                    int dp10 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10,getResources().getDisplayMetrics()));

                    //單位:20dp
                    int dp20 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20,getResources().getDisplayMetrics()));

                    //畫錯誤訊息
                    RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(dp20,dp20);
                    Params.setMargins(0,0,dp10,0);
                    ImageView imageView = new ImageView(PinCodeActivity.this);
                    imageView.setImageDrawable(getDrawable(R.drawable.wrong));
                    errorlayout.addView(imageView,Params);

                    TextView textView = new TextView(PinCodeActivity.this);
                    textView.setTextColor(getColor(R.color.orange));
                    textView.setTextSize(16);
                    textView.setText("授權碼錯誤 請重新輸入");
                    errorlayout.addView(textView);

                }else {
                    Intent intent = new Intent(PinCodeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}