package com.avtomat.androidapp.frags;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.avtomat.androidapp.R;
import com.avtomat.androidapp.coursesall.Course_Model;
import com.avtomat.androidapp.coursesall.Course_Viewholder;
import com.avtomat.androidapp.coursesall.Course_detail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class frag_home extends Fragment {

    SearchView searchView;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Course_Model> options;
    FirebaseRecyclerAdapter<Course_Model, Course_Viewholder> adapter;
    ImageView dropdown_btn;
    boolean drop = false;
    RelativeLayout dropdown_body;
    CardView card_choose,card_platform;
    ImageView cross_btn;
    String custom_filter= "NULL";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_home, container, false);

        searchView = view.findViewById(R.id.search_view);
        dropdown_btn = view.findViewById(R.id.dropdown_btn);
        dropdown_body = view.findViewById(R.id.dropdown_body);
        cross_btn = view.findViewById(R.id.cross_btn_card);
        card_choose = view.findViewById(R.id.card_choosed);
        card_platform = view.findViewById(R.id.card_platform);

        cross_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_choose.setVisibility(View.GONE);
                custom_filter = "NULL";
            }
        });

        card_platform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_choose.setVisibility(View.VISIBLE);
                dropdown_body.setVisibility(View.GONE);
                custom_filter = "courselength";
                drop = false;
            }
        });

        dropdown_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drop){
                    dropdown_body.setVisibility(View.GONE);
                    drop = false;
                }
                else {
                    dropdown_body.setVisibility(View.VISIBLE);
                    drop = true;
                }
            }
        });

        recyclerView = view.findViewById(R.id.recyclerview_home);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses");
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Course_Model>().setQuery(databaseReference,Course_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Course_Model, Course_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Course_Viewholder holder, int position, @NonNull Course_Model model) {
                holder.course_status.setVisibility(View.GONE);
                holder.course_name.setText(model.getCoursename());
                holder.course_type.setText(model.getCoursetype());
                holder.course_users.setText(model.getCourseusers());
                holder.course_length.setText(model.getCourselength());
                Picasso.get().load(model.getCourseuri()).into(holder.course_image);
                holder.maincard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Course_detail.class);
                        intent.putExtra("courseid",model.getCourseid());
                        intent.putExtra("coursename",model.getCoursename());
                        intent.putExtra("coursedetail",model.getCoursedetail());
                        intent.putExtra("courseuri",model.getCourseuri());
                        intent.putExtra("courseusers",model.getCourseusers());
                        intent.putExtra("coursetime",model.getCourselength());
                        intent.putExtra("courserating",model.getCourserating());
                        startActivity(intent);
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseSearch(newText);
                return false;
            }
        });

        return view;
    }

    private void FirebaseSearch(String newText) {

        String query = newText.toLowerCase();
        Query fquery;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("courses");
        databaseReference.keepSynced(true);

        if (custom_filter.equals("NULL")){
            fquery = databaseReference.orderByChild("coursetype").startAt(query).endAt(query+"\uf8ff");
        }
        else {
            fquery = databaseReference.orderByChild(custom_filter).startAt(query).endAt(query+"\uf8ff");
        }


        options = new FirebaseRecyclerOptions.Builder<Course_Model>().setQuery(fquery,Course_Model.class).build();

        adapter = new FirebaseRecyclerAdapter<Course_Model, Course_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Course_Viewholder holder, int position, @NonNull Course_Model model) {
                holder.course_status.setVisibility(View.GONE);
                holder.course_name.setText(model.getCoursename());
                holder.course_type.setText(model.getCoursetype());
                holder.course_users.setText(model.getCourseusers());
                holder.course_length.setText(model.getCourselength());
                Picasso.get().load(model.getCourseuri()).into(holder.course_image);
                holder.maincard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), Course_detail.class);
                        intent.putExtra("courseid",model.getCourseid());
                        intent.putExtra("coursename",model.getCoursename());
                        intent.putExtra("coursedetail",model.getCoursedetail());
                        intent.putExtra("courseuri",model.getCourseuri());
                        intent.putExtra("courseusers",model.getCourseusers());
                        intent.putExtra("coursetime",model.getCourselength());
                        intent.putExtra("courserating",model.getCourserating());
                        startActivity(intent);
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

    }
}