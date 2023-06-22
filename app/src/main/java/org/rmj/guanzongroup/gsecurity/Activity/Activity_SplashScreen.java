package org.rmj.guanzongroup.gsecurity.Activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Login;
import org.rmj.guanzongroup.gsecurity.R;

public class Activity_SplashScreen extends AppCompatActivity {
    private ActivityResultLauncher<Intent> poLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        AppConfigPreference.getInstance(Activity_SplashScreen.this).setProductID("gRider");

        MessageBox messageBox = new MessageBox(this);

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

        messageBox.initDialog();
        messageBox.setTitle("G-Sec App");
        messageBox.setMessage("This application is intended for dashboard of Guanzon Admins.");
        messageBox.setPositiveButton("Continue", new MessageBox.DialogButton() {
            @Override
            public void OnButtonClick(View view, AlertDialog dialog) {
                //launch activity launcher with result
                poLogin.launch(new Intent(Activity_SplashScreen.this, Activity_Login.class));
                dialog.dismiss();
            }
        });
        messageBox.show();
        //initiate activity launcher
        InitActivityResultLaunchers();
    }
    private void InitActivityResultLaunchers(){
        //instantiate activity launcher
        poLogin = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) { //no return
                startActivity(new Intent(Activity_SplashScreen.this, Activity_Dashboard.class));
                finish();
            } else if (result.getResultCode() == RESULT_CANCELED) { //cancelled or closed activity
                finish();
            }
        });
    }
}