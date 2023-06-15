package org.rmj.guanzongroup.gsecurity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Login;

public class Activity_SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        MessageBox messageBox = new MessageBox(this);
        messageBox.initDialog();

        MaterialTextView progresstext = findViewById(R.id.progresstext);
        LinearProgressIndicator progressIndicator = findViewById(R.id.progressbar);

        progresstext.setText("Initializing Data");

        ValueAnimator animator = ValueAnimator.ofInt(0,100);
        animator.setDuration(5);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                progressIndicator.setProgress((Integer) animation.getAnimatedValue(), true);
                progressIndicator.setIndicatorDirection(LinearProgressIndicator.INDICATOR_DIRECTION_START_TO_END);
            }
        });
        animator.start();

        progresstext.setText("Finished");

        messageBox.setTitle("G-Sec App");
        messageBox.setMessage("This application is intended for dashboard of Guanzon Admins.");
        messageBox.setPositiveButton("Continue", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                Intent intent = new Intent(Activity_SplashScreen.this, Activity_Login.class);
                startActivity(intent);
            }
        });

        messageBox.show();
    }
}