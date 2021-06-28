package com.avtomat.androidapp.feeds;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avtomat.androidapp.R;

public class Feed_Viewholder extends RecyclerView.ViewHolder {

    public TextView feed_heading;
    public ImageView feed_image;


    public Feed_Viewholder(@NonNull View itemView) {
        super(itemView);

        feed_heading = itemView.findViewById(R.id.feed_heading);
        feed_image = itemView.findViewById(R.id.feed_image);
    }
}
