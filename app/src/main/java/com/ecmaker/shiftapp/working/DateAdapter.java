package com.ecmaker.shiftapp.working;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class DateAdapter  extends RecyclerView.Adapter<DateAdapter.ViewHolder>{
    private List<ItemsModel_Date> itemsModel_date;
    private MainActivity activity;

    public DateAdapter(List<ItemsModel_Date> itemsModel_date, MainActivity activity) {
        this.itemsModel_date = itemsModel_date;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView week;
        private TextView day;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            week = itemView.findViewById(R.id.week);
            day = itemView.findViewById(R.id.day);
        }
    }

    @NonNull
    @Override
    public DateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.date_item_view, parent, false);
        return new DateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateAdapter.ViewHolder holder, int position) {
        String week = itemsModel_date.get(position).getWeek();
        String day = itemsModel_date.get(position).getDay();
        holder.week.setText(week);
        holder.day.setText(day);
        if(position == 1){
            holder.cardView.setCardBackgroundColor(activity.getColor(R.color.button_blue2));
            holder.week.setTextColor(activity.getColor(R.color.white));
            holder.day.setTextColor(activity.getColor(R.color.white));
        }else{
            holder.cardView.setCardBackgroundColor(activity.getColor(R.color.white));
            holder.week.setTextColor(activity.getColor(R.color.black));
            holder.day.setTextColor(activity.getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return itemsModel_date.size();
    }
}
