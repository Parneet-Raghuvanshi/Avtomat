package com.avtomat.androidapp.mycourses;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avtomat.androidapp.R;
import com.avtomat.androidapp.coursesall.Course_detail;
import com.avtomat.androidapp.detailcourse.Detail_Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgabrielfreitas.core.BlurImageView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.squareup.picasso.Picasso;

public class Mycourse_detail extends AppCompatActivity {

    ImageView backbtn;
    TextView coursename,course_progress_text;
    BlurImageView course_image;
    String course_name,course_detail,course_id,course_uri,courseusers,coursetime,courserating;
    RecyclerView recyclerView;
    ProgressBar course_progressBar;
    DatabaseReference dbref;
    FirebaseRecyclerOptions<MyCourse_progress_model> options;
    FirebaseRecyclerAdapter<MyCourse_progress_model,Mycourse_progress_viewholder> adapter;
    DataSnapshot dataSnapshot;
    MaterialButton enroll_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mycourse_detail);

        Intent intent = getIntent();
        course_name = intent.getStringExtra("coursename");
        course_id = intent.getStringExtra("courseid");
        course_detail = intent.getStringExtra("coursedetail");
        course_uri = intent.getStringExtra("courseuri");
        courserating = intent.getStringExtra("courserating");
        coursetime = intent.getStringExtra("coursetime");
        courseusers = intent.getStringExtra("courseusers");

        backbtn = findViewById(R.id.back_btn);
        coursename = findViewById(R.id.course_name);
        coursename.setText(course_name);
        course_image = findViewById(R.id.image_course_main);
        Picasso.get().load(course_uri).into(course_image);
        course_image.setBlur(2);
        enroll_btn = findViewById(R.id.enroll_btn);

        course_progress_text = findViewById(R.id.course_progress_text);

        course_progressBar = findViewById(R.id.course_progressbar);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Mycourse_detail.this);
                builder.setTitle("Information");
                builder.setMessage("You have not completed this course yet.")
                        .setCancelable(false)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        DatabaseReference dbref2 = FirebaseDatabase.getInstance().getReference().child("mycourses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(course_id);
        dbref2.keepSynced(true);
        dbref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("status").getValue().toString().equals("0")){
                    course_progress_text.setText(snapshot.child("status").getValue().toString()+"% Completed");
                    course_progressBar.setProgress(Integer.valueOf(snapshot.child("status").getValue().toString())+1);
                }
                else {
                    course_progress_text.setText(snapshot.child("status").getValue().toString()+"% Completed");
                    course_progressBar.setProgress(Integer.valueOf(snapshot.child("status").getValue().toString()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference dbref1 = FirebaseDatabase.getInstance().getReference().child("mycourses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(course_id).child("modulestatus");
        dbref1.keepSynced(true);
        dbref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataSnapshot = snapshot;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.recyclerview_detail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dbref = FirebaseDatabase.getInstance().getReference().child("courses").child(course_id).child("body");
        dbref.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<MyCourse_progress_model>().setQuery(dbref,MyCourse_progress_model.class).build();

        adapter = new FirebaseRecyclerAdapter<MyCourse_progress_model, Mycourse_progress_viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Mycourse_progress_viewholder holder, int position, @NonNull MyCourse_progress_model model) {
                holder.maintext.setText(model.getMaintext());
                holder.desctext.setText(model.getDesctext());
                holder.dropicon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.expanded){
                            holder.second_card.setVisibility(View.GONE);
                            holder.expanded = false;
                        }
                        else {
                            holder.second_card.setVisibility(View.VISIBLE);
                            holder.expanded = true;
                        }
                    }
                });

                holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(YouTubePlayer youTubePlayer) {
                        youTubePlayer.loadVideo(model.getYoutubeid(),0);
                        youTubePlayer.pause();
                    }
                });

                holder.video_link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(model.getYoutubelink());
                        startActivity(new Intent(intent.ACTION_VIEW,uri));
                    }
                });

                holder.quiz_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Uri uri = Uri.parse(model.getQuizlink());
                        startActivity(new Intent(intent.ACTION_VIEW,uri));
                    }
                });

                Module_status_model module_status_model = dataSnapshot.child(model.getModuleid()).getValue(Module_status_model.class);
                if (module_status_model.getArlabstatus().equals("200") && module_status_model.getQuizstatus().equals("200")){
                    holder.module_status.setImageDrawable(getDrawable(R.color.green));
                }
            }

            @NonNull
            @Override
            public Mycourse_progress_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Mycourse_progress_viewholder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.course_progress,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}