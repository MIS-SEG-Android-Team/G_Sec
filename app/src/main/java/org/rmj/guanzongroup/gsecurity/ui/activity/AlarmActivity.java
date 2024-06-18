package org.rmj.guanzongroup.gsecurity.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolCache;
import org.rmj.guanzongroup.gsecurity.databinding.ActivityAlarmBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AlarmActivity extends AppCompatActivity {

    private ActivityAlarmBinding binding;

    private MediaPlayer mediaPlayer;

    @Inject
    PatrolCache patrolCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        playAlarmSound();

        binding.startPatrolButton.setOnClickListener(view -> {
            patrolCache.setPatrolStarted(true);
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            mediaPlayer.stop();
        });

        binding.patrolLaterButton.setOnClickListener(view -> {
            mediaPlayer.stop();
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    private void playAlarmSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_clock_old); // Replace R.raw.alarm_sound with your alarm sound resource
        mediaPlayer.setLooping(true); // Set looping to true for continuous play
        mediaPlayer.start(); // Start playing the alarm sound
    }
}