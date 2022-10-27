package com.ecmaker.shiftapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

public class DialogDeleteWorking extends Dialog {
    public DialogDeleteWorking(@NonNull Context context) {
        this(context, R.style.forget_dialog);
    }

    public DialogDeleteWorking(@NonNull Context context, int themeResId) {
        this(context, themeResId, new MainActivity());
    }

    public DialogDeleteWorking(@NonNull Context context, MainActivity mainActivity) {
        this(context, R.style.forget_dialog, mainActivity);
    }

    public DialogDeleteWorking(@NonNull Context context, int themeResId, MainActivity mainActivity) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);//點擊其他區域時   true  關閉視窗  false  不關閉視窗
        setContentView(R.layout.dialog_delete_working);//載入layout

        Button send = findViewById(R.id.send);
        send.setClickable(false);
        CheckBox check1 = findViewById(R.id.check1);
        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color2));
                    send.setClickable(true);
                    //刪除班別
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            onLabelClickListener.onClick();
                        }
                    });
                }else{
                    send.setBackground(getContext().getDrawable(R.drawable.background_button_color));
                    send.setClickable(false);
                    send.setOnClickListener(null);
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
    private DialogDeleteWorking.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(DialogDeleteWorking.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    //關閉視窗
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
