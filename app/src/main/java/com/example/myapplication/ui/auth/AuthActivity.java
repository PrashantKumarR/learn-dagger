package com.example.myapplication.ui.auth;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.RequestManager;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityAuthBinding;
import com.example.myapplication.model.User;
import com.example.myapplication.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";
    @Inject
    Drawable logo;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;
    ActivityAuthBinding binding;

    private AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth);
        viewModel = ViewModelProviders.of(this, providerFactory).get(AuthViewModel.class);
        binding.loginButton.setOnClickListener(this);
        setLogo();
        subscribeObservers();
    }

    private void setLogo() {
        requestManager.load(logo)
                .into(binding.loginLogo);
    }

    private void subscribeObservers() {
        viewModel.observeUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> authUserResource) {
                if (authUserResource != null) {
                    switch (authUserResource.status) {
                        case LOADING: {
                            showProgressBar(true);
                            break;
                        }
                        case AUTHENTICATED: {
                            showProgressBar(false);
                            Log.d(TAG, "onChanged:  LOGIN SUCCESS: " + authUserResource.data.getEmail());
                            break;
                        }
                        case ERROR: {
                            showProgressBar(false);
                            Toast.makeText(AuthActivity.this,
                                    authUserResource.message + "\n Did you enter a number between 1 and 10?",
                                    Toast.LENGTH_LONG);
                            break;
                        }
                        case NOT_AUTHENTICATED: {
                            showProgressBar(false);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void showProgressBar(boolean isVisisble) {
        if (isVisisble) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                attemptLogin();
                break;
        }
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(binding.userIdInput.getText().toString())) {
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(binding.userIdInput.getText().toString()));
    }
}
