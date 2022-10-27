package com.ecmaker.shiftapp.working;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;


public class NoticePopupWindow {

    public NoticePopupWindow(int x, int y, List<ItemsModel_Notice> list_notice ,MainActivity mainActivity){
        PopupWindow popupWindow = new PopupWindow(mainActivity);
        View popupView = LayoutInflater.from(mainActivity).inflate(R.layout.notification_popupwindow,null);

        RecyclerView recyclerview = popupView.findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));//行數、上下滑動
        NoticeAdapter noticeAdapter = new NoticeAdapter(list_notice,mainActivity);
        recyclerview.setAdapter(noticeAdapter);

        LinearLayout moreLayout = popupView.findViewById(R.id.moreLayout);
        moreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLabelClickListener.onClick();
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
        void onClick();
    }
    private NoticePopupWindow.OnLabelClickListener onLabelClickListener;
    public void setOnLabelClickListener(NoticePopupWindow.OnLabelClickListener onLabelClickListener) {
        this.onLabelClickListener = onLabelClickListener;
    }

    public void setColor(MainActivity mainActivity, CardView carv, TextView text){
        carv.setCardBackgroundColor(mainActivity.getColor(R.color.button_blue4));
        text.setTextColor(mainActivity.getColor(R.color.white));
    }
}
