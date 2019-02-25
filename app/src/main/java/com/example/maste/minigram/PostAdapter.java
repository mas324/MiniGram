package com.example.maste.minigram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        Log.d("GramAdapt", "Made it this far");
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("GramHold", "Made it this far");
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_post, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder viewHolder, int i) {
        Post post = posts.get(i);
        Log.d("GramBind", post.toString());
        viewHolder.name.setText(post.getUser().getUsername());
        viewHolder.description.setText(post.getDesc());
        if (post.getImg() != null) {
            Glide.with(context).load(post.getImg().getUrl()).into(viewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.desc)
        TextView description;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
