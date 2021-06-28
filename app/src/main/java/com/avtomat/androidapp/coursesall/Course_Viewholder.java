package com.avtomat.androidapp.coursesall;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avtomat.androidapp.R;

public class Course_Viewholder extends RecyclerView.ViewHolder{

    public TextView course_name;
    public TextView course_type;
    public TextView course_users;
    public TextView course_length;
    public TextView course_status;
    public ImageView course_next_icon;
    public ImageView course_image;
    public RelativeLayout maincard;
    public ImageView next_btnicon;

    public Course_Viewholder(@NonNull View itemView) {
        super(itemView);

        course_name = itemView.findViewById(R.id.course_name);
        course_type = itemView.findViewById(R.id.course_type);
        course_users = itemView.findViewById(R.id.course_users);
        course_length = itemView.findViewById(R.id.course_length);
        course_next_icon = itemView.findViewById(R.id.course_next_icon);
        course_image = itemView.findViewById(R.id.course_image);
        maincard = itemView.findViewById(R.id.main_card);
        course_status = itemView.findViewById(R.id.status_text);
        next_btnicon = itemView.findViewById(R.id.course_next_icon);
    }
}
