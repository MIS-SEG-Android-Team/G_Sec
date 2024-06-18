package org.rmj.guanzongroup.gsecurity.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.rmj.guanzongroup.gsecurity.databinding.ActivityAuthenticationBinding;
import org.rmj.guanzongroup.gsecurity.service.GSecureMessagingService;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAuthenticationBinding binding = ActivityAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startService(new Intent(AuthenticationActivity.this, GSecureMessagingService.class));
    }
}