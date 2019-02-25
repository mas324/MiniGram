package com.example.maste.minigram.fragments;

import android.util.Log;

import com.example.maste.minigram.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostsFragment {

    @Override
    protected void query() {
        ParseQuery<Post> query = new ParseQuery<>(Post.class);
        query.include(Post.USER_KEY);
        query.addDescendingOrder(Post.CREATED_KEY);
        query.setLimit(20);
        query.whereEqualTo(Post.USER_KEY, ParseUser.getCurrentUser());
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
