package com.icapps.mvvm.viewmodel;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.icapps.mvvm.MVPApplication;
import com.icapps.mvvm.activity.CommentsActivity;
import com.icapps.mvvm.adapter.PostsAdapter;
import com.icapps.mvvm.databinding.ActivityPostsBinding;
import com.icapps.mvvm.model.Post;
import com.icapps.mvvm.model.PostRepository;

import java.util.ArrayList;
import java.util.List;

import cz.kinst.jakub.viewmodelbinding.ViewModel;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by maartenvangiel on 20/09/16.
 */

public class PostsActivityViewModel extends ViewModel<ActivityPostsBinding> implements SwipeRefreshLayout.OnRefreshListener, PostsAdapter.PostsAdapterListener {
    private final List<Post> posts = new ArrayList<>();
    private PostsAdapter postsAdapter;
    private Subscription subscription;
    private PostRepository postRepository;

    @Override
    public void onViewModelCreated() {
        super.onViewModelCreated();
        postRepository = ((MVPApplication) (getActivity().getApplication())).getPostRepository();
        loadData();
    }

    private void loadData() {
        getBinding().contentView.setRefreshing(true);
        subscription = postRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(p -> {
                    posts.clear();
                    posts.addAll(p);
                    postsAdapter.notifyDataSetChanged();
                    getBinding().contentView.setRefreshing(false);
                }, throwable -> {
                    getBinding().contentView.setRefreshing(false);
                    Toast.makeText(PostsActivityViewModel.this.getContext(), "Error loading data", Toast.LENGTH_LONG).show();
                });
    }

    @Override
    public void onViewAttached(boolean firstAttachment) {
        super.onViewAttached(firstAttachment);
        getBinding().contentView.setOnRefreshListener(this);

        postsAdapter = new PostsAdapter(this, posts);
        getBinding().rcvPosts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        getBinding().rcvPosts.setAdapter(postsAdapter);
        postsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onViewModelDestroyed() {
        super.onViewModelDestroyed();
        subscription.unsubscribe();
    }

    @Override
    public void onPostClicked(Post post, int position, View titleView, View bodyView) {
        Intent intent = new Intent(getContext(), CommentsActivity.class);
        intent.putExtra("post", post);
        getActivity().startActivity(intent);
    }
}
