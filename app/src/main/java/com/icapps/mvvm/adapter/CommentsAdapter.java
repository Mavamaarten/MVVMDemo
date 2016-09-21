package com.icapps.mvvm.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.icapps.mvvm.R;
import com.icapps.mvvm.databinding.LayoutRowCommentBinding;
import com.icapps.mvvm.model.Comment;

import java.util.List;


/**
 * Created by maartenvangiel on 20/09/16.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private final List<Comment> comments;

    public CommentsAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LayoutRowCommentBinding binding;

        ViewHolder(LayoutRowCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutRowCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_row_comment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.binding.setComment(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}
