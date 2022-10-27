package com.ecmaker.shiftapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ecmaker.shiftapp.R;

public class DialogUtil {
    private Dialog dialog;
    private View inflate;

    public DialogUtil(Context context, int resource){
        //自定義dialog顯示佈局
        inflate = LayoutInflater.from(context).inflate(resource, null);
        //自定義dialog顯示風格
        dialog = new Dialog(context, R.style.DialogRight);
        //彈窗點擊周圍空白處彈出層自動消失彈窗消失(false時爲點擊周圍空白處彈出層不自動消失)
        dialog.setCanceledOnTouchOutside(false);
        //將佈局設置給Dialog
        dialog.setContentView(inflate);
        //背景不可點擊
        dialog.setCancelable(true);
        //獲取當前Activity所在的窗體
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.RIGHT;
        wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(wlp);
        dialog.show();
    }

    public Dialog showRightDialog() {
        return dialog;
    }

    public <T extends View> T getViewById(int resource) {
        return inflate.findViewById(resource);
    }

    //關閉dialog時調用
    public void close() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
