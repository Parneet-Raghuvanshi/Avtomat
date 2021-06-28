package com.avtomat.androidapp.frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avtomat.androidapp.R;
import com.avtomat.androidapp.feeds.Feed_Viewholder;
import com.avtomat.androidapp.feeds.Feed_model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class frag_feed extends Fragment {

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Feed_model> options;
    FirebaseRecyclerAdapter<Feed_model, Feed_Viewholder> adapter;
    DatabaseReference databaseReference;

    public frag_feed() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_feed, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_feed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("feeds");
        databaseReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Feed_model>().setQuery(databaseReference,Feed_model.class).build();

        adapter = new FirebaseRecyclerAdapter<Feed_model, Feed_Viewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Feed_Viewholder holder, int position, @NonNull Feed_model model) {
                holder.feed_heading.setText(model.getFeedhead());
                Picasso.get().load(model.getFeeduri()).into(holder.feed_image);
            }

            @NonNull
            @Override
            public Feed_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new Feed_Viewholder(LayoutInflater.from(getContext()).inflate(R.layout.feed_layout,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        return view;
    }
}