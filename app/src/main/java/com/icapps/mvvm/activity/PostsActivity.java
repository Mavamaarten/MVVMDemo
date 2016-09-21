package com.icapps.mvvm.activity;

import com.icapps.mvvm.R;
import com.icapps.mvvm.databinding.ActivityPostsBinding;
import com.icapps.mvvm.viewmodel.PostsActivityViewModel;

import cz.kinst.jakub.viewmodelbinding.ViewModelActivity;
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;

public class PostsActivity extends ViewModelActivity<ActivityPostsBinding, PostsActivityViewModel> {

    @Override
    public ViewModelBindingConfig<PostsActivityViewModel> getViewModelBindingConfig() {
        return new ViewModelBindingConfig<>(R.layout.activity_posts, PostsActivityViewModel.class);
    }
}
