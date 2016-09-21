package com.icapps.mvvm.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.icapps.mvvm.R;
import com.icapps.mvvm.databinding.ActivityCommentsBinding;
import com.icapps.mvvm.viewmodel.CommentsActivityViewModel;

import cz.kinst.jakub.viewmodelbinding.ViewModelActivity;
import cz.kinst.jakub.viewmodelbinding.ViewModelBindingConfig;

/**
 * Created by maartenvangiel on 20/09/16.
 */

public class CommentsActivity extends ViewModelActivity<ActivityCommentsBinding, CommentsActivityViewModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Post details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public ViewModelBindingConfig<CommentsActivityViewModel> getViewModelBindingConfig() {
        return new ViewModelBindingConfig<>(R.layout.activity_comments, CommentsActivityViewModel.class);
    }
}
