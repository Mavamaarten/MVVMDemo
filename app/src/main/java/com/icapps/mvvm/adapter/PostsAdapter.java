package com.icapps.mvvm.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.icapps.mvvm.R;
import com.icapps.mvvm.databinding.LayoutRowPostBinding;
import com.icapps.mvvm.model.Post;

import java.util.List;


/**
 * Created by maartenvangiel on 20/09/16.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private final List<Post> posts;
    private PostsAdapterListener listener;

    public PostsAdapter(PostsAdapterListener listener, List<Post> posts) {
        this.listener = listener;
        this.posts = posts;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LayoutRowPostBinding binding;
        TextView postTitle;
        TextView postBody;

        ViewHolder(LayoutRowPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutRowPostBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_row_post, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.binding.setPost(post);
        holder.itemView.setOnClickListener(v -> {
            listener.onPostClicked(post, position, holder.postTitle, holder.postBody);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public interface PostsAdapterListener {
        void onPostClicked(Post post, int position, View titleView, View bodyView);
    }
}
