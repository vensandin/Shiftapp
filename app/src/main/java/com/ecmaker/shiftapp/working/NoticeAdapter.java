package com.ecmaker.shiftapp.working;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class NoticeAdapter  extends RecyclerView.Adapter<NoticeAdapter.ViewHolder>{
    private List<ItemsModel_Notice> listModel_notice;
    private MainActivity activity;

    public NoticeAdapter(List<ItemsModel_Notice> listModel_notice, MainActivity activity) {
        this.listModel_notice = listModel_notice;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView circle;
        private TextView content;
        private TextView date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circle = itemView.findViewById(R.id.circle);
            content = itemView.findViewById(R.id.content);
            date = itemView.findViewById(R.id.date);
        }
    }

    @NonNull
    @Override
    public NoticeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item_view, parent, false);
        return new NoticeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return listModel_notice.size();
    }
}
