package com.avtomat.androidapp.mycourses;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avtomat.androidapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Mycourse_progress_viewholder extends RecyclerView.ViewHolder {

    public TextView maintext;
    public TextView desctext;
    public ImageView dropicon;
    public boolean expanded;
    public RelativeLayout second_card;
    public TextView video_link;
    public CardView arlab_btn,quiz_btn;
    public CircleImageView module_status;
    public YouTubePlayerView youTubePlayerView;

    public Mycourse_progress_viewholder(@NonNull View itemView) {
        super(itemView);

        maintext = itemView.findViewById(R.id.main_text);
        desctext = itemView.findViewById(R.id.desc_text);
        dropicon = itemView.findViewById(R.id.drop_icon);
        expanded = false;
        second_card = itemView.findViewById(R.id.second_card);
        video_link = itemView.findViewById(R.id.module_video_link);
        arlab_btn = itemView.findViewById(R.id.arlab_btn);
        quiz_btn = itemView.findViewById(R.id.quiz_btn);
        module_status = itemView.findViewById(R.id.module_status_image);
        youTubePlayerView = itemView.findViewById(R.id.youtubeplayer);
    }
}
