package com.avtomat.androidapp.detailcourse;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avtomat.androidapp.R;

public class Detail_Viewholder extends RecyclerView.ViewHolder {

    public TextView maintext;
    public TextView desctext;
    public ImageView dropicon;
    public boolean expanded;
    public RelativeLayout second_card;

    public Detail_Viewholder(@NonNull View itemView) {
        super(itemView);

        maintext = itemView.findViewById(R.id.main_text);
        desctext = itemView.findViewById(R.id.desc_text);
        dropicon = itemView.findViewById(R.id.drop_icon);
        expanded = false;
        second_card = itemView.findViewById(R.id.second_card);

    }
}
