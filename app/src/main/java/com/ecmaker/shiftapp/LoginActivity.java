package com.ecmaker.shiftapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ecmaker.shiftapp.dialog.DialogForget;
import com.ecmaker.shiftapp.dialog.DialogSend;

public class LoginActivity extends AppCompatActivity {
    private boolean eyes = false;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login);//登入
        LinearLayout errorlayout = findViewById(R.id.errorlayout);//錯誤框

        LinearLayout uidLayout = findViewById(R.id.uidLayout);//(帳號) 外框
        EditText uidEdit = findViewById(R.id.uidEdit);//(帳號) 輸入
        ImageView uidImg = findViewById(R.id.uidImg);//(帳號) 清除

        LinearLayout pwdLayout = findViewById(R.id.pwdLayout);//(密碼) 外框
        EditText pwdEdit = findViewById(R.id.pwdEdit);//(密碼) 輸入
        ImageView pwdImg = findViewById(R.id.pwdImg);//(密碼) 眼睛

        //文字輸入監聽(帳號)
        uidEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String test = uidEdit.getText().toString();
                String test2 = pwdEdit.getText().toString();

                if(test.length() <= 0){
                    uidImg.setImageResource(R.drawable.background_none);
                }else if(test.length() == 1){
                    uidImg.setImageResource(R.drawable.close);
                }

                if(test.length() > 0 && test2.length() > 0){
                    login.setBackground(getDrawable(R.drawable.background_button_color2));
                    login.setEnabled(true);
                }else{
                    login.setBackground(getDrawable(R.drawable.background_button_color));
                    login.setEnabled(false);
                }
            }
        });
        //輸入框焦點監聽(帳號)
        uidEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(isError){
                    uidLayout.setBackground(getDrawable(R.drawable.background_normal));
                    pwdLayout.setBackground(getDrawable(R.drawable.background_normal));
                    errorlayout.removeAllViews();
                }
                if(b){
                    uidLayout.setBackground(getDrawable(R.drawable.background_focused));
                }else{
                    uidLayout.setBackground(getDrawable(R.drawable.background_normal));
                }
            }
        });
        //眼睛圖片監聽(帳號)
        uidImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uidEdit.setText("");
                uidImg.setImageResource(R.drawable.background_none);
            }
        });

        pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隱藏文字
        //文字輸入監聽(密碼)
        pwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String test = uidEdit.getText().toString();
                String test2 = pwdEdit.getText().toString();

                if(test2.length() <= 0){
                    pwdImg.setImageResource(R.drawable.background_none);
                }else if(test2.length() == 1){
                    pwdImg.setImageResource(R.drawable.eyes_c);
                }

                if(test.length() > 0 && test2.length() > 0){
                    login.setBackground(getDrawable(R.drawable.background_button_color2));
                    login.setEnabled(true);
                }else{
                    login.setBackground(getDrawable(R.drawable.background_button_color));
                    login.setEnabled(false);
                }
            }
        });
        //輸入框焦點監聽(密碼)
        pwdEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(isError){
                    uidLayout.setBackground(getDrawable(R.drawable.background_normal));
                    pwdLayout.setBackground(getDrawable(R.drawable.background_normal));
                    errorlayout.removeAllViews();
                }
                if(b){
                    pwdLayout.setBackground(getDrawable(R.drawable.background_focused));
                }else{
                    pwdLayout.setBackground(getDrawable(R.drawable.background_normal));
                }
            }
        });
        //眼睛圖片監聽(密碼)
        pwdImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(eyes){
                    pwdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());//隱藏文字
                    eyes = false;
                }else{
                    pwdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//顯示文字
                    eyes = true;
                }
            }
        });

        //忘記密碼
        TextView forget = findViewById(R.id.forget);
        forget.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下劃線
        forget.getPaint().setAntiAlias(true);//抗鋸齒
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogForget dialogForget = new DialogForget(LoginActivity.this,LoginActivity.this);
                dialogForget.show();
                dialogForget.setOnLabelClickListener(new DialogForget.OnLabelClickListener() {
                    @Override
                    public void onClick() {
                        Log.e("DialogForget", "OnLabelClick");
                        //寄送API
                        {
                            DialogSend dialogSend = new DialogSend(LoginActivity.this,LoginActivity.this,dialogForget);
                            dialogSend.show();
                            dialogSend.setOnLabelClickListener(new DialogSend.OnLabelClickListener() {
                                @Override
                                public void onClick() {
                                    Log.e("DialogSend", "OnLabelClick");
                                    //寄送API
                                }
                            });
                        }
                    }
                });
            }
        });

        //登入
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uidEdit.getText().toString().length() == 1 && pwdEdit.getText().toString().length() == 1){
                    uidEdit.clearFocus();//清掉焦點
                    pwdEdit.clearFocus();//清掉焦點
                    isError = true;
                    uidLayout.setBackground(getDrawable(R.drawable.background_edittext2));
                    pwdLayout.setBackground(getDrawable(R.drawable.background_edittext2));
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
                    ImageView imageView = new ImageView(LoginActivity.this);
                    imageView.setImageDrawable(getDrawable(R.drawable.wrong));
                    errorlayout.addView(imageView,Params);

                    TextView textView = new TextView(LoginActivity.this);
                    textView.setTextColor(getColor(R.color.red));
                    textView.setTextSize(16);
                    textView.setText("帳號或密碼錯誤 請重新輸入");
                    errorlayout.addView(textView);

                }else {
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

}