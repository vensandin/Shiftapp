package com.ecmaker.shiftapp.partner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ecmaker.shiftapp.MainActivity;
import com.ecmaker.shiftapp.R;

import java.util.List;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder>{
    private List<ItemsModel_Travel> list;
    private MainActivity activity;

    public TravelAdapter(List<ItemsModel_Travel>  list, MainActivity activity) {
        this.list = list;
        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView number;
        TextView startDate;
        TextView endDate;
        TextView time;
        TextView place;
        TextView event;
        ImageView arrow;
        LinearLayout ll_note;
        TextView note;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            time = itemView.findViewById(R.id.time);
            place = itemView.findViewById(R.id.place);
            event = itemView.findViewById(R.id.event);
            arrow = itemView.findViewById(R.id.arrow);
            ll_note = itemView.findViewById(R.id.ll_note);
            note = itemView.findViewById(R.id.note);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public TravelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_travel_item, parent, false);
        return new TravelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravelAdapter.ViewHolder holder, int position) {
        ItemsModel_Travel model = list.get(position);
        holder.number.setText(model.getNumber());
        holder.startDate.setText(model.getStartDate());
        holder.endDate.setText(model.getEndDate());
        holder.time.setText(model.getTime());
        holder.place.setText(model.getPlace());
        holder.event.setText(model.getEvent());
        boolean isOpen = model.getIsOpen();
        if(isOpen){
            holder.ll_note.setVisibility(View.VISIBLE);
            holder.note.setText(model.getNote());
        }else{
            holder.ll_note.setVisibility(View.GONE);
            holder.note.setText("");
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean tempIsOpen = model.getIsOpen();
                if(tempIsOpen){
                    model.setIsOpen(false);
                    holder.ll_note.setVisibility(View.GONE);
                    holder.note.setText("");
                    holder.arrow.setImageResource(R.drawable.salary_down_8);
                }else{
                    model.setIsOpen(true);
                    holder.ll_note.setVisibility(View.VISIBLE);
                    holder.note.setText(model.getNote());
                    holder.arrow.setImageResource(R.drawable.salary_up_8);
                }
            }
            
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
