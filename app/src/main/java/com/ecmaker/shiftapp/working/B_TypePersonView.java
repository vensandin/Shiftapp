package com.ecmaker.shiftapp.working;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.ecmaker.shiftapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class B_TypePersonView extends PersonViewHolders {
    public TextView tv_name;
    public TextView tv_position;
    public TextView tv_hours;

    public B_TypePersonView(@NonNull @NotNull View itemView) {
        super(itemView);
        tv_name = itemView.findViewById(R.id.name);
        tv_position = itemView.findViewById(R.id.position);
        tv_hours = itemView.findViewById(R.id.hours);
    }

    @Override
    public void bindViewHolder(HashMap<String, Object> hashMap) {
        tv_name.setText((String)hashMap.get("name"));
        tv_position.setText((String)hashMap.get("position"));
        tv_hours.setText((String)hashMap.get("hours"));
    }
}
