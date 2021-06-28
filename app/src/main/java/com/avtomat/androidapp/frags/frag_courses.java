package com.avtomat.androidapp.frags;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avtomat.androidapp.R;
import com.avtomat.androidapp.coursesall.Course_Model;
import com.avtomat.androidapp.coursesall.Course_Viewholder;
import com.avtomat.androidapp.coursesall.Course_detail;
import com.avtomat.androidapp.mycourses.Mycourse_detail;
import com.avtomat.androidapp.mycourses.Mycourses_Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class frag_courses extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Mycourses_Model> options;
    FirebaseRecyclerAdapter<Mycourses_Model, Course_Viewholder> adapter;
    DatabaseReference databaseReference;

    public frag_courses() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_courses, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_mycourses);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("mycourses").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Mycourses_Model>().setQuery(databaseReference,Mycourses_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Mycourses_Model, Course_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Course_Viewholder holder, int position, @NonNull Mycourses_Model model) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("courses");
                dbref.keepSynced(true);
                dbref.child(model.getCourseid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Course_Model course_model = snapshot.getValue(Course_Model.class);
                        holder.course_status.setVisibility(View.VISIBLE);
                        holder.course_next_icon.setVisibility(View.GONE);
                        holder.course_name.setText(course_model.getCoursename());
                        holder.course_type.setText(course_model.getCoursetype());
                        holder.course_users.setText(course_model.getCourseusers());
                        holder.course_length.setText(course_model.getCourselength());
                        holder.course_status.setText(model.getStatus()+"%");
                        Picasso.get().load(course_model.getCourseuri()).into(holder.course_image);
                        holder.maincard.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getContext(), Mycourse_detail.class);
                                intent.putExtra("courseid",model.getCourseid());
                                intent.putExtra("coursename",course_model.getCoursename());
                                intent.putExtra("coursedetail",course_model.getCoursedetail());
                                intent.putExtra("courseuri",course_model.getCourseuri());
                                intent.putExtra("courseusers",course_model.getCourseusers());
                                intent.putExtra("coursetime",course_model.getCourselength());
                                intent.putExtra("courserating",course_model.getCourserating());
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @NonNull
            @Override
            public Course_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Course_Viewholder(LayoutInflater.from(getContext()).inflate(R.layout.course_card,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        return view;
    }
}