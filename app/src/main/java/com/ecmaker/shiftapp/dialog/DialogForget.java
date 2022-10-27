package com.ecmaker.shiftapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ecmaker.shiftapp.LoginActivity;
import com.ecmaker.shiftapp.R;

public class DialogForget extends Dialog {
    private boolean isError = false;

    public DialogForget(@NonNull Context context) {
        this(context, R.style.forget_dialog);
    }

    public DialogForget(@NonNull Context context, int themeResId) {
        this(context, themeResId,  new LoginActivity());
    }

    public DialogForget(@NonNull Context context, LoginActivity activity) {
        this(context, R.style.forget_dialog,  activity);
    }

    public DialogForget(@NonNull Context context, int themeResId, LoginActivity activity) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);//點擊其他區域時   true  關閉視窗  false  不關閉視窗
        setContentView(R.layout.dialog_forget);//載入layout

        Button send = findViewById(R.id.send);//寄送信件
        send.setClickable(false);
        LinearLayout errorlayout = findViewById(R.id.errorlayout);//錯誤框

        LinearLayout empidLayout = findViewById(R.id.empidLayout);//(帳號) 外框
        EditText empidEdit = findViewById(R.id.empidEdit);//(帳號) 輸入
        ImageView empidImg = findViewById(R.id.empidImg);//(帳號) 清除

        LinearLayout emailLayout = findViewById(R.id.emailLayout);//(信箱) 外框
        EditText emailEdit = findViewById(R.id.emailEdit);//(信箱) 輸入
        ImageView emailImg = findViewById(R.id.emailImg);//(信箱) 清除

        //文字輸入監聽(帳號)
        empidEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String empid = empidEdit.getText().toString();
                String email = emailEdit.getText().toString();

                if(empid.length() <= 0){
                    empidImg.setImageResource(R.drawable.background_none);
                }else if(empid.length() == 1){
                    empidImg.setImageResource(R.drawable.close);
                }

                if((empid.length() > 0) && (email.length() > 0)){
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color2));
                    send.setClickable(true);
                }else{
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color));
                    send.setClickable(false);
                }
            }
        });
        //輸入框焦點監聽(帳號)
        empidEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(isError){
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    errorlayout.removeAllViews();
                    errorlayout.setVisibility(View.GONE);

                    int dp20 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20,activity.getResources().getDisplayMetrics()));

                    int dp32 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 32,activity.getResources().getDisplayMetrics()));

                    int dp48 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 48,activity.getResources().getDisplayMetrics()));

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp48);
                    layoutParams.setMargins(dp20,dp32,dp20,dp20);
                    send.setLayoutParams(layoutParams);
                }
                if(b){
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_focused));
                }else{
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                }
            }
        });
        //眼睛圖片監聽(帳號)
        empidImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                empidEdit.setText("");
                empidImg.setImageResource(R.drawable.background_none);
            }
        });

        //文字輸入監聽(信箱)
        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String empid = empidEdit.getText().toString();
                String email = emailEdit.getText().toString();

                if(email.length() <= 0){
                    emailImg.setImageResource(R.drawable.background_none);
                }else if(email.length() == 1){
                    emailImg.setImageResource(R.drawable.close);
                }

                if((empid.length() > 0) && (email.length() > 0)){
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color2));
                    send.setClickable(true);
                }else{
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color));
                    send.setClickable(false);
                }
            }
        });
        //輸入框焦點監聽(信箱)
        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(isError){
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    errorlayout.removeAllViews();
                    errorlayout.setVisibility(View.GONE);

                    int dp20 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20,activity.getResources().getDisplayMetrics()));

                    int dp32 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 32,activity.getResources().getDisplayMetrics()));

                    int dp48 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 48,activity.getResources().getDisplayMetrics()));

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp48);
                    layoutParams.setMargins(dp20,dp32,dp20,dp20);
                    send.setLayoutParams(layoutParams);
                }
                if(b){
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_focused));
                }else{
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                }
            }
        });
        //眼睛圖片監聽(信箱)
        emailImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailEdit.setText("");
                emailImg.setImageResource(R.drawable.background_none);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailEdit.getText().toString().indexOf("@") < 0){
                    empidEdit.clearFocus();//清掉焦點
                    emailEdit.clearFocus();//清掉焦點
                    isError = true;
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_edittext2));
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_edittext2));
                    errorlayout.setVisibility(View.VISIBLE);
                    errorlayout.removeAllViews();//先清掉錯誤Layout 避免疊加

                    int dp4 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 4,activity.getResources().getDisplayMetrics()));

                    int dp10 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 10,activity.getResources().getDisplayMetrics()));

                    int dp12 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 12,activity.getResources().getDisplayMetrics()));

                    int dp20 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 20,activity.getResources().getDisplayMetrics()));

                    int dp48 = Math.round(TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 48,activity.getResources().getDisplayMetrics()));

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp48);
                    layoutParams.setMargins(dp20,dp4,dp20,dp20);
                    send.setLayoutParams(layoutParams);



                    RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(dp12,dp12);
                    Params.setMargins(0,0,dp10,0);
                    ImageView imageView = new ImageView(activity);
                    imageView.setImageDrawable(activity.getDrawable(R.drawable.wrong));
                    errorlayout.addView(imageView,Params);
                    errorlayout.setGravity(Gravity.CENTER_VERTICAL);

                    RelativeLayout.LayoutParams Params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    Params.setMargins(0,dp4,0,0);
                    TextView textView = new TextView(activity);
                    textView.setTextColor(activity.getColor(R.color.orange));
                    textView.setTextSize(14);
                    textView.setText("帳號或信箱錯誤 請重新輸入");
                    errorlayout.addView(textView,Params1);

                }else {
                    empidLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    emailLayout.setBackground(activity.getDrawable(R.drawable.background_normal));
                    isError = false;

                    onLabelClickListener.onClick();
                }
            }
        });

        //關閉(X)
        LinearLayout cls = findViewById(R.id.cls);
        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setCancelable(false);

        getWindow().getAttributes().gravity = Gravity.CENTER;//置中顯示
        getWindow().getAttributes().dimAmount = 0.5f;//背景透明度  取值範圍 0 ~ 1
    }

    public interface OnLabelClickListener {
        /**
         * 点击label
         */
        void onClick();
    }
    private DialogForget.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(DialogForget.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    //關閉視窗
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
