package com.avtomat.androidapp.coursesall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.avtomat.androidapp.Dashboard;
import com.avtomat.androidapp.R;
import com.avtomat.androidapp.detailcourse.Detail_Model;
import com.avtomat.androidapp.detailcourse.Detail_Viewholder;
import com.avtomat.androidapp.mycourses.MyCourse_progress_model;
import com.bumptech.glide.Glide;
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

public class Course_detail extends AppCompatActivity {

    ImageView backbtn;
    TextView coursename;
    TextView short_detail;
    BlurImageView course_image;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Detail_Model> options;
    FirebaseRecyclerAdapter<Detail_Model, Detail_Viewholder> adapter;
    DatabaseReference dbref;
    String course_name,course_detail,course_id,course_uri,courseusers,coursetime,courserating;
    TextView course_users,course_time,course_rating;
    MaterialButton enroll_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_course_detail);

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
        short_detail = findViewById(R.id.short_detail_text);
        short_detail.setText(course_detail);
        course_image = findViewById(R.id.image_course_main);
        Glide.with(Course_detail.this).load(course_uri).into(course_image);
        course_image.setBlur(2);

        course_users = findViewById(R.id.course_users);
        course_users.setText(courseusers);
        course_time = findViewById(R.id.course_time);
        course_time.setText(coursetime);
        course_rating = findViewById(R.id.course_rating);
        course_rating.setText(courserating+"%");
        enroll_btn = findViewById(R.id.enroll_btn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        enroll_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Course_detail.this);
                builder.setTitle("Confirm");
                builder.setMessage("Do You Want to Enroll in this course.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //enrolled.
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("mycourses").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(course_id);
                                databaseReference.child("courseid").setValue(course_id);
                                databaseReference.child("status").setValue("0");
                                DatabaseReference dbref_module = FirebaseDatabase.getInstance().getReference().child("courses").child(course_id).child("body");
                                dbref_module.keepSynced(true);
                                dbref_module.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                            MyCourse_progress_model model = dataSnapshot.getValue(MyCourse_progress_model.class);
                                            databaseReference.child("modulestatus").child(model.getModuleid()).child("moduleid").setValue(model.getModuleid());
                                            databaseReference.child("modulestatus").child(model.getModuleid()).child("arlabstatus").setValue("400");
                                            databaseReference.child("modulestatus").child(model.getModuleid()).child("quizstatus").setValue("400");
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        recyclerView = findViewById(R.id.recyclerview_detail);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dbref = FirebaseDatabase.getInstance().getReference().child("courses").child(course_id).child("details");
        dbref.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Detail_Model>().setQuery(dbref,Detail_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Detail_Model, Detail_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Detail_Viewholder holder, int position, @NonNull Detail_Model model) {
                holder.maintext.setText(model.getDesctext());
                holder.desctext.setText(model.getMaintext());
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
            }

            @NonNull
            @Override
            public Detail_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Detail_Viewholder(LayoutInflater.from(getApplicationContext()).inflate(R.layout.course_detail,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}