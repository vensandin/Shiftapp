package com.ecmaker.shiftapp;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

public class EditTextConfirmDialog extends Dialog {

    TextView tvTitle;
    ImageView cancel;

    public String getMemo(){
        EditText editText = findViewById(R.id.edittext);
        return String.valueOf(editText.getText());
    }

    public EditTextConfirmDialog(@NonNull Context context) {
        this(context, R.style.slide_confirm_dialog, "","");
    }

    public EditTextConfirmDialog(@NonNull Context context, String title, String slidetext) {
        this(context, R.style.slide_confirm_dialog, title,slidetext);
    }

    protected EditTextConfirmDialog(@NonNull Context context, int theme, String title, String text) {
        super(context, theme);
        setCanceledOnTouchOutside(false);//點擊其他區域時   true  關閉視窗  false  不關閉視窗
        setContentView(R.layout.dialog_input_text);//載入layout
        tvTitle = findViewById(R.id.title);
        tvTitle.setText(title);
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        //getWindow().getAttributes().gravity = Gravity.CENTER;//置中顯示
        //getWindow().getAttributes().dimAmount = 0.5f;//背景透明度  取值範圍 0 ~ 1

        CardView confirm = findViewById(R.id.confirm);
        TextView confirmText = findViewById(R.id.confirmText);
        confirmText.setText(text);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onSuccess();
            }
        });
    }

    private EditTextConfirmDialog.OnLabelClickListener onLabelClickListener;

    public interface OnLabelClickListener {
        void onSuccess();
    }

    public void setOnLabelClickListener(EditTextConfirmDialog.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    //關閉視窗
    @Override
    public void dismiss() {
        super.dismiss();
    }
}
