package com.icapps.mvvm.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.icapps.mvvm.MVPApplication;
import com.icapps.mvvm.adapter.CommentsAdapter;
import com.icapps.mvvm.databinding.ActivityCommentsBinding;
import com.icapps.mvvm.model.Comment;
import com.icapps.mvvm.model.Post;
import com.icapps.mvvm.model.PostRepository;

import java.util.ArrayList;
import java.util.List;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by maartenvangiel on 21/09/16.
 */

public class CommentsActivityViewModel extends ViewModel<ActivityCommentsBinding> implements SwipeRefreshLayout.OnRefreshListener {
    public final ObservableField<Post> post = new ObservableField<>();
    public final ObservableInt commentCount = new ObservableInt();

    private List<Comment> comments;
    private CommentsAdapter commentsAdapter;
    private PostRepository postRepository;
    private Subscription subscription;

    @Override
    public void onViewModelCreated() {
        super.onViewModelCreated();
        postRepository = ((MVPApplication) getActivity().getApplication()).getPostRepository();

        post.set(getActivity().getIntent().getParcelableExtra("post"));

        comments = new ArrayList<>();
        loadData();
    }

    @Override
    public void onViewAttached(boolean firstAttachment) {
        super.onViewAttached(firstAttachment);

        getBinding().contentView.setOnRefreshListener(this);
        commentsAdapter = new CommentsAdapter(comments);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);

        getBinding().rcvComments.setLayoutManager(layoutManager);
        getBinding().rcvComments.setAdapter(commentsAdapter);
        getBinding().rcvComments.setNestedScrollingEnabled(false);
    }

    private void loadData() {
        getBinding().contentView.setRefreshing(true);
        subscription = postRepository.getPostComments(post.get().getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> {
                    comments.clear();
                    comments.addAll(c);
                    commentsAdapter.notifyDataSetChanged();

                    commentCount.set(comments.size());
                    getBinding().contentView.setRefreshing(false);
                }, throwable -> {
                    Toast.makeText(getContext(), "Error loading comments", Toast.LENGTH_LONG).show();
                    getBinding().contentView.setRefreshing(false);
                });
    }

    @Override
    public void onViewModelDestroyed() {
        super.onViewModelDestroyed();
        if (subscription != null) subscription.unsubscribe();
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}
