package com.ecmaker.shiftapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ecmaker.shiftapp.LoginActivity;
import com.ecmaker.shiftapp.R;

public class DialogSend  extends Dialog {
    int count = 31;
    LinearLayout send;
    TextView send_text;
    ImageView change_image;
    Handler aHandler;
    Context myContext;

    public DialogSend(@NonNull Context context) {
        this(context, R.style.forget_dialog);
    }

    public DialogSend(@NonNull Context context, int themeResId) {
        this(context, themeResId,  new LoginActivity(), null);
    }

    public DialogSend(@NonNull Context context, LoginActivity activity) {
        this(context, R.style.forget_dialog,  activity, null);
    }

    public DialogSend(@NonNull Context context, LoginActivity activity, DialogForget dialogForget) {
        this(context, R.style.forget_dialog,  activity, dialogForget);
    }

    protected DialogSend(@NonNull Context context, int themeResId, LoginActivity activity, DialogForget dialogForget) {
        super(context, themeResId);
        setCanceledOnTouchOutside(false);//點擊其他區域時   true  關閉視窗  false  不關閉視窗
        setContentView(R.layout.dialog_send);//載入layout
        myContext = context;

        //重新寄送
        send = findViewById(R.id.send);
        send.setClickable(false);
        send_text = findViewById(R.id.send_text);
        change_image = findViewById(R.id.change_image);

        //Handler 單線訊息佇列模式的一套執行緒訊息機制
        aHandler = new Handler();
        //將Runnable添加到消息隊列中
        aHandler.post(runnable);

        //上一頁
        LinearLayout back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        //關閉(X)
        LinearLayout cls = findViewById(R.id.cls);
        cls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                dialogForget.dismiss();
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
    private DialogSend.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(DialogSend.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    //關閉視窗
    @Override
    public void dismiss() {
        super.dismiss();
        //關閉時清掉執行緒中的Runnable
        if (aHandler != null) {
            aHandler.removeCallbacks(runnable);
        }
    }

    final Runnable runnable = new Runnable() {
        public void run() {
            // TODO Auto-generated method stub

            if (count > 0) {
                //動態旋轉
                Animation startRotateAnimation = AnimationUtils.loadAnimation(myContext.getApplicationContext(), R.anim.rotate_animation);
                change_image.startAnimation(startRotateAnimation);

                send_text.setText(Integer.toString(count-1)+"後重新寄送");
                send.setBackground(getContext().getDrawable(R.drawable.background_button_color));
                send.setClickable(false);
                send.setOnClickListener(null);
                count--;
                //設定間隔的時間(1000=1秒)
                send.postDelayed(runnable, 1000);
            }else{
                //圖片空
                change_image.setImageResource(R.drawable.background_none);

                send_text.setText("重新寄送");
                send.setBackground(getContext().getDrawable(R.drawable.background_button_color2));
                send.setClickable(true);
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onLabelClickListener.onClick();
                    }
                });
            }
        }
    };

}
