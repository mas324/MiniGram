package com.example.maste.minigram.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maste.minigram.Post;
import com.example.maste.minigram.PostAdapter;
import com.example.maste.minigram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsFragment extends Fragment {

    @BindView(R.id.postsContain)
    RecyclerView recyclerView;
    List<Post> posts;
    PostAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        posts = new ArrayList<>();
        adapter = new PostAdapter(getContext(), posts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        query();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void query() {
        ParseQuery<Post> query = new ParseQuery<>(Post.class);
        query.include(Post.USER_KEY);
        query.setLimit(20);
        query.orderByDescending(Post.CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e != null) {
                    Log.e("GramQuery", e.getLocalizedMessage());
                    return;
                }
                posts.addAll(objects);
                adapter.notifyDataSetChanged();
                for (Post p :
                        posts) {
                    Log.d("GramPosts", p.toString());
                }
            }
        });
    }
}
